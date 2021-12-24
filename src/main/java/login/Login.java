package login;

import CLI.CLI;
import tweet.Tweet;
import user.User;

import java.util.Date;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Login {
    static private final Logger logger = LogManager.getLogger(Login.class);

    /**
     * loads data from json
     */
    static public void LoadData()
    {
        User.LoadUsers();
        Tweet.LoadTweets();
    }

    /**
     * checks the username and password from loaded data
     */
    public static boolean ValidLogin(String username, String Password) {
       User user = User.FindUsername(username);

       if (user == null)
           return false;

       else return user.getPassword().equals(Password);
    }

    public static void SignIn(String username)
    {
        logger.warn("this user with this username : " +  username + " signed in");
        User user = User.FindUsername(username);
        Date date = new Date();
        user.Activation();

        user.setLastSeen(date);
        CLI.HomepageResponder(user);
    }

    /**
     * creates a user in user.User class
     */
    public static void CreateAnAccount(String name, String username, String password, String date , String visibleDate , String email , String visibleEmail , String phone,String visiblePhone , String bio) {
        User user = new User(name, username, password, email);

        logger.warn("this user : " + user.getID() + " was created.");

        if (visibleEmail.equals("Yes"))
            user.setVisibleDate(true);

        if (!date.equals("null"))
        {
            user.setDate(date);

            if (visibleDate.equals("Yes"))
                user.setVisibleEmail(true);
        }

        if (!phone.equals("null"))
        {
            user.setPhone(phone);

            if (visiblePhone.equals("Yes"))
                user.setVisiblePhone(true);
        }

        if (!bio.equals("null"))
            user.setBio(bio);

        User.UpdateUsers(user);
        SignIn(username);
    }
}