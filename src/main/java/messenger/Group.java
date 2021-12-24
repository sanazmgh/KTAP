package messenger;

import user.User;

import java.util.LinkedList;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Group {
    static private final Logger logger = LogManager.getLogger(Group.class);

    private transient User user;
    private String Name;
    private LinkedList<String> MembersID = new LinkedList<>();

    public Group(String name)
    {
        this.Name = name;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMembersID(String newMember)
    {
        logger.info("this user : " + newMember + " was added to this group : " + this.getName());
        this.MembersID.add(newMember);
        User.UpdateUsers(user);
    }

    public LinkedList<String> getMembersID ()
    {
        return this.MembersID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
        User.UpdateUsers(user);
    }

    public void addToMembers(User user)
    {
        this.setMembersID(user.getID());
        User.UpdateUsers(user);
    }

    public void RemoveAMember(User user)
    {
        logger.info("this user : " + user.getID() + " was removed from this group : " + this.getName());
        this.MembersID.remove(user.getID());
        User.UpdateUsers(user);
    }
}
