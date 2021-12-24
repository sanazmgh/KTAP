package notification;

import user.User;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Notification {
    static private final Logger logger = LogManager.getLogger(Notification.class);

    /**
     * there are two types of notifications:
     *
     * 1. system notification
     *      a.follows you : 1
     *      b.stopped following : 2
     *
     * 2. users notification
     *      a.request to follow(accept/deny/deny without notify) : 1
     *      b.status of the requests : 2
     */

    private String SenderID;
    private String ReceiverID;
    private String Text;

    public Notification (String senderID, String receiverID, String text)
    {
        this.SenderID = senderID;
        this.ReceiverID = receiverID;
        this.Text = text;
    }

    public String getText() {
        return (User.FindUser(this.SenderID) != null ? User.FindUser(this.SenderID).getUsername() : "Deleted account") + " " + Text;
    }

    public boolean Accept(int ind)
    {

        User receiver = User.FindUser(this.ReceiverID);
        User sender = User.FindUser(this.SenderID);

        logger.info("this user : " + receiver.getID() + " accepted the request of this user : " + sender.getID());

        if(sender == null)
            return false;

        Notification senderNotification = new Notification(receiver.getID() , sender.getID() , " accepted your follow request.");
        Notification receiverNotification = new Notification(sender.getID() , receiver.getID() , " started following you.");

        receiver.getUsersNotifications().remove(ind);
        receiver.getRequested().remove(sender.getID());
        sender.setStatusNotifications(senderNotification);
        receiver.setSystemNotifications(receiverNotification);
        sender.Follow(receiver);

        User.UpdateUsers(receiver);
        User.UpdateUsers(sender);
        return true;
    }

    public boolean Deny(int ind , boolean notify)
    {
        User receiver = User.FindUser(this.ReceiverID);
        User sender = User.FindUser(this.SenderID);

        logger.info("this user : " + receiver.getID() + " denied the request of this user : " + sender.getID());


        if(sender == null)
            return false;

        receiver.getUsersNotifications().remove(ind);
        receiver.getRequested().remove(sender.getID());

        if(notify)
        {
            Notification notification = new Notification(this.ReceiverID, this.SenderID, " denied your follow request.");
            User.FindUser(this.SenderID).setStatusNotifications(notification);
        }

        User.UpdateUsers(receiver);
        User.UpdateUsers(sender);
        return true;
    }

    public static void NoticeToFollow(User sender , User receiver)
    {
        logger.info("this user : " + sender.getID() + " followed : " + receiver.getID());


        if(receiver.getFollowers().contains(sender.getID()))
            return;

        Notification receiverNotification = new Notification(sender.getID() , receiver.getID() , " started following you.");
        Notification senderNotification = new Notification(receiver.getID() , sender.getID() , " is now in your followings list" );

        receiver.setSystemNotifications(receiverNotification);
        sender.setStatusNotifications(senderNotification);
        sender.Follow(receiver);

        User.UpdateUsers(receiver);
        User.UpdateUsers(sender);
    }

    public static void NoticeToUnfollow(User sender , User receiver)
    {
        logger.info("this user : " + sender.getID() + " unfollowed : " + receiver.getID());


        if(!receiver.getFollowers().contains(sender.getID()))
            return;

        Notification notification = new Notification(sender.getID() , receiver.getID() , "stopped following you.");
        receiver.setSystemNotifications(notification);
        sender.Unfollow(receiver);

        User.UpdateUsers(receiver);
        User.UpdateUsers(sender);
    }

    public static void Request(User sender , User receiver )
    {
        logger.info("this user : " + sender.getID() + " requested to follow : " + receiver.getID());


        Notification notification = new Notification(sender.getID() , receiver.getID() , " requested to follow you.");
        receiver.setUsersNotifications(notification);
        receiver.setRequested(sender.getID());

        User.UpdateUsers(receiver);
        User.UpdateUsers(sender);
    }
}
