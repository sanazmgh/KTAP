package user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import messenger.Messenger;
import notification.Notification;
import tweet.Tweet;

import java.io.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class User {
    static private final Logger logger = LogManager.getLogger(User.class);

    private String name;
    private String username;
    private String ID;
    private String password;
    private String date;
    private String Email;
    private String phone;
    private String bio;
    private int noOfReports;
    private int LastSeenMode;
    private boolean isActive;
    private boolean isDeleted;
    private boolean Private;
    private boolean VisiblePhone;
    private boolean VisibleEmail;
    private boolean VisibleDate;
    private Date LastSeen;
    private LinkedList<String> Tweets = new LinkedList<>(); //List of tweets ID
    private LinkedList<String > TimelineTweets = new LinkedList<>(); //List of tweets ID
    public  LinkedList<String> Explorer = new LinkedList<>(); //List of tweets ID
    private LinkedList<Notification> SystemNotifications = new LinkedList<>();
    private LinkedList<Notification> UsersNotifications = new LinkedList<>();
    private LinkedList<Notification> statusNotifications = new LinkedList<>();
    private LinkedList<String> Requested = new LinkedList<>(); //List of Users ID
    private LinkedList<String> Followers = new LinkedList<>(); //List of Users ID
    private LinkedList<String> Followings = new LinkedList<>(); //List of Users ID
    private LinkedList<String> Blocked = new LinkedList<>(); //List of Users ID
    private LinkedList<String> Muted = new LinkedList<>(); //List of Users ID
    private Messenger messenger;
    private transient static LinkedList<User> UsersList = new LinkedList<>();
    private transient static String LastID = "0" ;
    private transient static final File gsonFolder = new File("Gson files");
    private transient static final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    public User(String name , String username, String password , String Email )
    {
        this.name = name;
        this.username = username;
        this.password = password;
        this.Email = Email;
        this.ID = Long.toString(Long.parseLong(LastID)+1);
        LastID = Long.toString(Long.parseLong(LastID)+1);
        this.Private = false;
        this.VisibleDate = false;
        this.VisibleEmail = false;
        this.VisiblePhone = false;
        this.LastSeenMode = 0;
        this.isActive = true;
        this.isDeleted = false;
        this.messenger = new Messenger(this) ;
        UsersList.add(this);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        logger.info("this ID " + this.ID + " changed its username");

        this.username = username;
        User.UpdateUsers(this);
    }

    public String getID()
    {
        return this.ID;
    }

    public void setName(String name) {
        logger.info("this ID " + this.ID + " changed its name");
        this.name = name;
        UpdateUsers(this);
    }

    public String getName() {
        return name;
    }

    public void setDate(String date) {
        logger.info("this ID " + this.ID + " changed its date of birth");
        this.date = date;
        UpdateUsers(this);
    }

    public String getDate()
    {
        return date;
    }

    public void setPhone(String phone)
    {
        logger.info("this ID " + this.ID + " changed its phone number");
        this.phone = phone;
        UpdateUsers(this);
    }

    public String getPhone()
    {
        return this.phone;
    }

    public void setBio(String bio)
    {
        logger.info("this ID " + this.ID + " changed its bio");
        this.bio = bio;
        UpdateUsers(this);
    }

    public String getBio()
    {
        return this.bio;
    }

    public void setPassword(String password) {
        logger.info("this ID " + this.ID + " changed its password");
        this.password = password;
        UpdateUsers(this);
    }

    public String getPassword() {
        return this.password;
    }

    public void setEmail(String Email)
    {
        logger.info("this ID " + this.ID + " changed its E-mail address");
        this.Email = Email;
        UpdateUsers(this);
    }

    public String getEmail()
    {
        return this.Email;
    }

    public void setTweets(String tweetID) {
        logger.info("this ID " + this.ID + " tweeted/retweeted/commented something");
        this.Tweets.add(tweetID);
        UpdateUsers(this);
    }

    public LinkedList<String> getTweets() {
        return this.Tweets;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setActive(boolean active) {
        logger.warn("this ID " + this.ID + " active mode changed to : " + active);
        isActive = active;
        UpdateUsers(this);
    }

    public Date getLastSeen() {
        return LastSeen;
    }

    public void setLastSeen(Date lastSeen) {
        logger.info("this ID " + this.ID + "'s last seen updated.");
        LastSeen = lastSeen;
        UpdateUsers(this);
    }

    public LinkedList<Notification> getSystemNotifications() {
        return SystemNotifications;
    }

    public void setSystemNotifications(Notification systemNotifications) {
        logger.info("this ID " + this.ID + " has a new system notification");
        SystemNotifications.add(systemNotifications);
        UpdateUsers(this);
    }

    public LinkedList<Notification> getUsersNotifications() {
        return UsersNotifications;
    }

    public void setUsersNotifications(Notification usersNotifications) {
        logger.info("this ID " + this.ID + " has a new users notification");
        UsersNotifications.add(usersNotifications);
        UpdateUsers(this);
    }

    public LinkedList<Notification> getStatusNotifications() {
        return statusNotifications;
    }

    public void setStatusNotifications(Notification statusNotifications) {
        logger.info("this ID " + this.ID + " has a new status notification");
        this.statusNotifications.add(statusNotifications);
        UpdateUsers(this);
    }

    public LinkedList<String> getRequested() {
        return Requested;
    }

    public void setRequested(String requested) {
        logger.info("this ID " + this.ID + " has a new request notification");
        Requested.add(requested);
        UpdateUsers(this);
    }

    public LinkedList<String> getFollowers() {
        return Followers;
    }

    public void setFollowers(String follower) {
        logger.info("this ID " + this.ID + " has a new follower");

        this.Followers.add(follower);
        UpdateUsers(this);
    }

    public LinkedList<String> getFollowings() {
        return Followings;
    }

    public void setFollowings(String following) {
        logger.info("this ID " + this.ID + " followed someone.");
        this.Followings.add(following);
        UpdateUsers(this);
    }

    public LinkedList<String> getBlocked() {
        return Blocked;
    }

    public void setBlocked(String blocked) {
        logger.info("this ID " + this.ID + " blocked " + blocked);
        this.Blocked.add(blocked);
        UpdateUsers(this);
    }

    public LinkedList<String> getMuted() {
        return Muted;
    }

    public void setMuted(String muted) {
        logger.info("this ID " + this.ID + " muted " + muted);
        this.Muted.add(muted);
        UpdateUsers(this);
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public void setTimelineTweets(String tweetID)
    {
        logger.info("tweet with this ID" + tweetID + "added to timeline of a user with this ID " + this.ID );
        this.TimelineTweets.add(tweetID);
        UpdateUsers(this);
    }

    public LinkedList<String> getTimelineTweets()
    {
        return this.TimelineTweets;
    }

    public static LinkedList<User> getUsersList() {
        return UsersList;
    }

    public static void setUsersList(User user) {
        logger.warn("this ID " + user.ID + " added to usersList.");
        UsersList.add(user);
    }

    public boolean getPrivate() { return this.Private; }

    public void setPrivate(boolean Private) {
        logger.info("this ID " + this.ID + " changed its private mode to : " + Private);
        this.Private = Private;
        UpdateUsers(this);
    }

    public boolean isVisiblePhone() {
        return VisiblePhone;
    }

    public void setVisiblePhone(boolean visiblePhone) {
        logger.info("this ID " + this.ID + " Changed its visiblePhone mode to : " + visiblePhone);
        VisiblePhone = visiblePhone;
        UpdateUsers(this);
    }

    public boolean isVisibleEmail() {
        return VisibleEmail;
    }

    public void setVisibleEmail(boolean visibleEmail) {
        logger.info("this ID " + this.ID + " Changed its visibleEmail mode to : " + visibleEmail);
        VisibleEmail = visibleEmail;
        UpdateUsers(this);
    }

    public boolean isVisibleDate() {
        return VisibleDate;
    }

    public void setVisibleDate(boolean visibleDate) {
        logger.info("this ID " + this.ID + " Changed its visibleDate mode to : " + visibleDate);
        VisibleDate = visibleDate;
        UpdateUsers(this);
    }

    public int getLastSeenMode() {
        return LastSeenMode;
    }

    public void setLastSeenMode(int lastSeenMode) {
        logger.info("this ID " + this.ID + " Changed its LastSeen mode to : " + lastSeenMode);
        LastSeenMode = lastSeenMode;
        UpdateUsers(this);
    }

    static public void LoadUsers()
    {
        try
        {
            for (File file : gsonFolder.listFiles())
            {
                FileReader fileReader = new FileReader(file.getCanonicalPath());
                User user = gson.fromJson(fileReader, User.class);
                UsersList.add(user);
                user.getMessenger().setUser(user);
                LastID = Long.toString(Math.max(Long.parseLong(LastID), Long.parseLong(user.getID())));

                logger.warn("This ID : " + user.ID + "loaded successfully");
            }
        }

        catch (Exception e)
        {
            logger.error("Failed to load users from Gson");
            System.err.println("failed to load users");
        }
    }

    static public void UpdateUsers(User user)
    {
        try {
            File file = new File("./Gson files/" + user.getID());
            FileWriter fileWriter = new FileWriter(file.getCanonicalPath());
            gson.toJson(user , fileWriter);
            fileWriter.flush();
            logger.warn("Gson file of This ID : " + user.ID + " updated");
        }

        catch (Exception e)
        {
            logger.error("Failed to update this user : " + user.ID);
            System.err.println("failed to update user");
        }
    }

    static public User FindUser(String ID)
    {
        for(User user : UsersList)
        {
            if (user.ID.equals(ID))
                return user;
        }

        logger.debug("Couldn't find the user with this ID : " + ID);
        return null;
    }

    static public boolean FindEmail(String Email)
    {
        for(User user : UsersList)
        {
            if (user.Email.equals(Email))
                return true;
        }

        return false;
    }

    static public User FindUsername(String Username)
    {
        for(User user : UsersList)
        {
            if (user.username.equals(Username))
                return user;
        }

        logger.debug("Couldn't find any user with this username : " + Username);
        return null;
    }

    public void Block(String ID)
    {
        this.setBlocked(ID);
        this.Followers.remove(ID);
        this.Followings.remove(ID);
    }

    public void Unblock(String ID)
    {
        logger.info("This ID : " + this.ID + " unblocked : " + ID);
        this.Blocked.remove(ID);
        User.UpdateUsers(this);
    }

    public void Mute(String ID)
    {
        this.setMuted(ID);
    }

    /***
     * 0 : nobody
     * 1 : everybody
     * 2 : followers
     */
    public boolean VisibleLastSeen (User user)
    {
        if(user.getLastSeenMode() == 0)
            return false;

        else if(user.getLastSeenMode() == 1)
            return true;

        else if(user.getFollowers().contains(this.getID()))
            return true;

        return false;
    }

    public boolean ChangeEmail(String Email)
    {
        if(Email.equals(this.Email))
            return true;

        if(User.FindEmail(Email))
            return false;

        this.setEmail(Email);
        return true;
    }

    public boolean ChangeUsername(String Username)
    {
        if(Username.equals(this.username))
            return true;

        if(User.FindUsername(Username) != null)
            return false;

        this.setUsername(Username);
        return true;
    }

    public void ChangePrivate()
    {
        this.setPrivate(!this.getPrivate());
    }

    public void ChangeVisibility(User user , int mode)
    {
        if(mode == 0)
            user.setVisibleEmail(!user.VisibleEmail);
        
        if(mode == 1)
            user.setVisiblePhone(!user.VisiblePhone);

        else
            user.setVisibleDate(!user.VisibleDate);
    }

    public void addToReport()
    {
        logger.info("Some one reported a user with this ID : " + this.ID);
        this.noOfReports++;
        UpdateUsers(this);
    }

    public void Deactivation()
    {
        this.setActive(false);
    }

    public void Activation()
    {
        if(this.isActive)
            return;

        this.setActive(true);
    }

    public void DeleteAccount()
    {
        logger.info("This ID : " + this.ID + " deleted its account" );
        this.isDeleted = true;
        this.username = null;
        this.Email = null;
        this.isActive = false;
        UpdateUsers(this);
    }

    public void Follow(User user)
    {
        if(user.getFollowers().contains(this.getID()))
            return;

        if(this.isActive)
            user.setFollowers(this.getID());


        if(user.isActive)
            this.setFollowings(user.getID());
    }

    public void Unfollow(User user)
    {
        logger.info("This ID : " + this.ID + " unfollowed : " + user.ID);

        if(!user.getFollowers().contains(this.getID()))
            return;

        this.Followings.remove(user.getID());
        user.getFollowers().remove(this.getID());

        User.UpdateUsers(this);
        User.UpdateUsers(user);
    }

    public void CreateExplorer()
    {
        logger.info("Explorer of this ID : " + this.ID + " generated");

        this.Explorer.clear();

        for(User user : UsersList)
        {
            if(!user.getFollowings().contains(this.getID()) && user.isActive && !user.Private && !user.getTweets().isEmpty())
            {
                this.Explorer.add(user.getTweets().getLast());
            }
        }
        UpdateUsers(this);
    }
}
