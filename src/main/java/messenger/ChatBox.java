package messenger;

import user.User;

import java.util.Date;
import java.util.LinkedList;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ChatBox {
    static private final Logger logger = LogManager.getLogger(ChatBox.class);

    private String UserAID;
    private String UserBID;
    private LinkedList<String> Unseen = new LinkedList<>();
    private LinkedList<String > seen = new LinkedList<>();

    public ChatBox(String userAID , String userBID)
    {
        this.UserAID = userAID;
        this.UserBID = userBID;
    }

    public String getUserAID() {
        return UserAID;
    }

    public String getUserBID() {
        return UserBID;
    }

    public LinkedList<String> getUnseen() {
        return Unseen;
    }

    public LinkedList<String> getSeen() {
        return seen;
    }

    public void setUnseen(String unseen) {
        logger.info("a new message sent in the chat box of " + UserAID + " and " + UserBID);
        this.Unseen.add(unseen);

        User.UpdateUsers(User.FindUser(UserAID));
        User.UpdateUsers(User.FindUser(UserBID));
    }

    public void setSeen(String seen) {
        logger.info("a new message sent in the chat box of " + UserAID + " and " + UserBID);
        this.seen.add(seen);

        User.UpdateUsers(User.FindUser(UserAID));
        User.UpdateUsers(User.FindUser(UserBID));
    }

    public void SeenNewMessages()
    {
        logger.info("new messages of the chat box of " + UserAID + " and " + UserBID + " were seen");

        this.seen.addAll(this.getUnseen());
        this.getUnseen().clear();

        User.UpdateUsers(User.FindUser(UserAID));
        User.UpdateUsers(User.FindUser(UserBID));
    }

    public void sendMessage(User user , String txt)
    {
        Date date = new Date();
        User receiver = User.FindUser(UserBID);

        this.setSeen(user.getUsername() + " at " + date + " : " + "\n" + txt);
        receiver.getMessenger().CreateChatBox(user).setUnseen(user.getUsername() + " at " + date + " : " + "\n" + txt);
    }

    public void SaveMessage(User user , String txt)
    {
        Date date = new Date();
        this.setSeen(user.getUsername() + " at " + date + " : " + "\n" + txt);

    }
}
