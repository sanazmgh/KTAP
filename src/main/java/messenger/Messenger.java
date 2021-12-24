package messenger;

import tweet.Tweet;
import user.User;

import java.util.Date;
import java.util.LinkedList;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Messenger {
    static private final Logger logger = LogManager.getLogger(Messenger.class);

    /**
     * to put the chats in order
     */

    private transient User user;
    private ChatBox savedMessages;
    private LinkedList<ChatBox> Chats = new LinkedList<>();
    private LinkedList<Group> GPList = new LinkedList<>();

    public Messenger(User user)
    {
        this.user = user;
        savedMessages = new ChatBox(user.getID() , user.getID());
    }

    public ChatBox getSavedMessages() {
        return savedMessages;
    }

    public LinkedList<ChatBox> getChats() {
        return Chats;
    }

    public LinkedList<Group> getGPList() {
        return GPList;
    }

    public void setChats(ChatBox chats) {
        logger.info("Chat box with " + chats.getUserAID() + " and " + chats.getUserBID() + " was added to thi users messenger : " + this.user.getID());
        this.Chats.add(chats);
        User.UpdateUsers(this.user);
    }

    public void setUser(User user) {
        this.user = user;
        User.UpdateUsers(user);
    }

    public void setGPList(Group GPList) {
        this.GPList.add(GPList);
        User.UpdateUsers(user);
    }

    public void Forward (String tweetID , User user)
    {
        logger.info("this user : " + this.user.getID() + " forwarded this tweet : " + tweetID + " to this user : " + user.getID());

        String txt = "Forwarded tweet from " + User.FindUser(Tweet.FindTweet(tweetID).getUserID()).getUsername() + "\n"
                + Tweet.FindTweet(tweetID).getText();

        FindChatBox(user.getID()).sendMessage(this.user , txt);
    }

    public void savedMessages(String tweetID)
    {
        logger.info("this user : " + this.user.getID() + " saved this tweet : " + tweetID);

        String txt = "Forwarded tweet from " + User.FindUser(Tweet.FindTweet(tweetID).getUserID()).getUsername() + "\n"
                + Tweet.FindTweet(tweetID).getText();

        savedMessages.SaveMessage(this.user , txt);
    }

    public ChatBox FindChatBox(String UserB)
    {
        for(ChatBox chatBox : Chats)
            if(User.FindUser(chatBox.getUserBID()).getUsername().equals(UserB))
                return chatBox;

        return null;
    }

    public Group FindGroup(String name)
    {
        for(Group group : this.getGPList())
            if(group.getName().equals(name))
                return group;

        return null;
    }

    public void CreateNewFolder(String name , LinkedList<String> UsersID)
    {
        logger.info("a new folder with name : " + name + " was created by this user : " + this.user.getID());

        Group gp = new Group(name);
        gp.setUser(this.user);

        for(String ID : UsersID)
            gp.setMembersID(ID);

        this.setGPList(gp);
        User.UpdateUsers(user);
    }

    public void DeleteFolder(Group group)
    {
        logger.info("a folder with name : " + group.getName() + " was deleted by this user : " + this.user.getID());

        this.GPList.remove(group);
        User.UpdateUsers(user);
    }

    public ChatBox CreateChatBox(User UserB)
    {
        logger.info("a new chat box with : " + this.user.getID() + " and " + UserB.getID() + " was created " );

        if(this.user.getID().equals(UserB.getID()))
            return this.savedMessages;

        ChatBox chatBoxA = this.FindChatBox(UserB.getUsername());

        if(chatBoxA != null)
            return chatBoxA;

        chatBoxA = new ChatBox(this.user.getID() , UserB.getID());
        this.setChats(chatBoxA);

        ChatBox chatBoxB = this.FindChatBox(this.user.getUsername());
        if (chatBoxB == null) {
            chatBoxB = new ChatBox(UserB.getID(), this.user.getID());
            user.getMessenger().setChats(chatBoxB);
        }

        User.UpdateUsers(user);

        return chatBoxA;
    }

    public void Compose(LinkedList<String> chats , String txt)
    {
        logger.info("a new message to multiple users sent by : " + this.user);

        LinkedList<String> composeTo = new LinkedList<>();
        Date date = new Date();

        for (String chat : chats) {
            Group group = this.FindGroup(chat);

            if (group != null) {
                for (int j = 0; j < group.getMembersID().size(); j++) {
                    if (!composeTo.contains(group.getMembersID().get(j)))
                        composeTo.add(group.getMembersID().get(j));
                }
            }

            User user = User.FindUsername(chat);
            if (user != null) {
                if (!composeTo.contains(user.getID()))
                    composeTo.add(user.getID());
            }
        }

        for (String s : composeTo) {
            User userB = User.FindUser(s);

            if (userB.isActive()) {
                ChatBox chatBox = this.CreateChatBox(userB);
                chatBox.sendMessage(user , txt);
            }
        }
    }
}
