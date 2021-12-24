package CLI;

import ConsoleColors.ConsoleColors;
import login.Login;
import messenger.ChatBox;
import messenger.Group;
import notification.Notification;
import tweet.Tweet;
import user.User;

import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class CLI {
    static private final Logger logger = LogManager.getLogger(CLI.class);

    static final private Scanner scanner = new Scanner(System.in);

    static public void LoginResponder()
    {
        String respond ;
        boolean invalid = false;

        System.out.println(ConsoleColors.RESET + "Hi! Welcome to KTAP.");
        //Login.LoadData();

        while(!invalid) {
            invalid = true;

            System.out.println("Do you already have an account?(Yes/No)");
            respond = scanner.nextLine();
            System.out.println();

            if (respond.equals("Yes")) {
                String Username, Password;
                System.out.println("Please, Enter your username: ");
                Username = scanner.nextLine();
                System.out.println();

                System.out.println("Enter your password: ");
                Password = scanner.nextLine();
                System.out.println();

                while (!Login.ValidLogin(Username, Password)) {
                    System.out.println(ConsoleColors.RED + "Incorrect username or password!");

                    System.out.println(ConsoleColors.WHITE + "Please, enter your username: ");
                    Username = scanner.nextLine();
                    System.out.println();

                    System.out.println("Enter your password: ");
                    Password = scanner.nextLine();
                    System.out.println();

                    logger.info("User entered wrong login information");
                }

                System.out.println(ConsoleColors.GREEN + "You have signed in successfully!");
                System.out.println("===================================================================================================================");
                Login.SignIn(Username);
            }

            else if (respond.equals("No")) {
                String Name, Username, Password, DateOfBirth, VisibleDate = "No" , Email , VisibleEmail , Phone , VisiblePhone = "No" , Bio;

                System.out.println(ConsoleColors.WHITE + "You can create your account now!");
                System.out.println("Filling following parts are not optional, please fill them all.");

                System.out.println("Name:");
                Name = scanner.nextLine();
                System.out.println();

                System.out.println("Username:");
                Username = scanner.nextLine();
                System.out.println();

                while (User.FindUsername(Username) != null) {
                    logger.info("User chose an existing username.");

                    System.out.println(ConsoleColors.RED + "This username is already taken, choose another one.");
                    System.out.println("Username:");
                    Username = scanner.nextLine();
                    System.out.println();
                }

                System.out.println("Password:");
                Password = scanner.nextLine();
                System.out.println();

                System.out.println("E-mail address: ");
                Email = scanner.nextLine();
                while (User.FindEmail(Email))
                {
                    logger.info("User Chose an existing E-mail address.");

                    System.out.println(ConsoleColors.RED + "There is already an account with this E-mail address. enter another E-mail address:");
                    Email = scanner.nextLine();
                }
                System.out.println();

                System.out.println(ConsoleColors.WHITE + "Do you want your E-mail address to be visible? (Yes/No)");
                VisibleEmail = scanner.nextLine();
                while(!VisibleEmail.equals("Yes") && !VisibleEmail.equals("No"))
                {
                    System.out.println(ConsoleColors.RED + "Invalid input!");
                    System.out.println(ConsoleColors.WHITE + "Do you want your E-mail address to be visible? (Yes/No)");
                    VisibleEmail = scanner.nextLine();
                }
                System.out.println();

                System.out.println("Filling following parts are optional, you can print \"null\" in case you didn't want to answer a part");

                System.out.println("Phone number:");
                Phone = scanner.nextLine();
                System.out.println();

                if(!Phone.equals("null")) {
                    System.out.println(ConsoleColors.WHITE + "Do you want your phone number to be visible? (Yes/No)");
                    VisibleEmail = scanner.nextLine();
                    while (!VisibleEmail.equals("Yes") && !VisibleEmail.equals("No")) {
                        System.out.println(ConsoleColors.RED + "Invalid input!");
                        System.out.println(ConsoleColors.WHITE + "Do you want your phone number to be visible? (Yes/No)");
                        VisibleEmail = scanner.nextLine();
                    }
                    System.out.println();
                }

                System.out.println("Date of birth (in format YYYY/MM/DD. as an example: 2001/09/21)");
                DateOfBirth = scanner.nextLine();
                System.out.println();

                if(!DateOfBirth.equals("null")) {
                    System.out.println(ConsoleColors.WHITE + "Do you want your date of birth to be visible? (Yes/No)");
                    VisibleEmail = scanner.nextLine();
                    while (!VisibleEmail.equals("Yes") && !VisibleEmail.equals("No")) {
                        System.out.println(ConsoleColors.RED + "Invalid input!");
                        System.out.println(ConsoleColors.WHITE + "Do you want your date of birth to be visible? (Yes/No)");
                        VisibleEmail = scanner.nextLine();
                    }
                    System.out.println();
                }

                System.out.println("bio:");
                Bio = scanner.nextLine();
                System.out.println();

                System.out.println(ConsoleColors.GREEN + "Your account created successfully :). ");
                System.out.println("===================================================================================================================");

                Login.CreateAnAccount(Name, Username, Password, DateOfBirth , VisibleDate , Email , VisibleEmail , Phone, VisiblePhone ,  Bio);
            }

            else
            {
                invalid = false;
                System.out.println(ConsoleColors.RED + "Invalid command!");

                logger.info("an unknown command entered");
            }
        }
    }

    static public void HomepageResponder(User user)
    {
        String respond ;

        System.out.println(ConsoleColors.BLUE + "Choose between these options:");
        System.out.println(ConsoleColors.CYAN + """
                Profile\s
                Timeline\s
                Explorer\s
                Messenger\s
                Setting""");

        respond = scanner.nextLine();

        switch (respond) {
            case "Profile" -> ProfileResponder(user);
            case "Timeline" -> TimelineResponder(user);
            case "Explorer" -> ExplorerResponder(user);
            case "Messenger" -> MessengerResponder(user);
            case "Setting" -> SettingResponder(user);
            default -> {
                logger.info("an unknown command entered");

                System.out.println(ConsoleColors.RED + "Invalid input!");
            }
        }

        HomepageResponder(user);
    }

    public static void ProfileResponder(User user)
    {
        logger.info("this user : " + user.getID() + " entered to her/his profile");

        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        String respond;

        System.out.println(ConsoleColors.BLUE + "Choose between these options: ");
        System.out.println(ConsoleColors.CYAN + """
                    New tweet\s
                    View pre tweets\s
                    Change profile\s
                    Lists\s
                    info\s
                    Notifications\s
                    Back
                    """);

        respond = scanner.nextLine();

        switch (respond) {
            case "New tweet" -> TweetingResponder(user);
            case "View pre tweets" -> ViewTweetsResponder(user.getTweets(), user , user.getTweets().size()-1);
            case "Change profile" -> ChangeProfileResponder(user);
            case "Lists" -> ListsResponder(user);
            case "info" -> infoResponder(user);
            case "Notifications" -> NotificationsResponder(user);
            case "Back" -> {
                return;
            }
            default -> {
                logger.info("an unknown command entered");
                System.out.println(ConsoleColors.RED + "Invalid input!");
            }
        }

        ProfileResponder(user);
    }

    public static void TimelineResponder(User user)
    {
        logger.info("this user : " + user.getID() + " entered to her/his profile");
        ViewTweetsResponder(user.getTimelineTweets() , user , user.getTimelineTweets().size()-1);
    }

    public static void ExplorerResponder(User user)
    {
        logger.info("this user : " + user.getID() + " entered to her/his explorer");
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        String respond;

        System.out.println(ConsoleColors.BLUE + "Choose between these options: ");
        System.out.println(ConsoleColors.CYAN + """
                Search
                Latest tweets
                Back 
                """);

        respond = scanner.nextLine();

        if(respond.equals("Search"))
            SearchUserResponder(user);

        else if(respond.equals("Latest tweets"))
        {
            user.CreateExplorer();
            ViewTweetsResponder(user.Explorer, user, user.Explorer.size() - 1);
        }

        else if(respond.equals("Back"))
            return;

        else
        {
            logger.info("an unknown command entered");

            System.out.println(ConsoleColors.RED + "Invalid input!");
        }

        ExplorerResponder(user);
    }

    public static void MessengerResponder(User user)
    {
        logger.info("this user : " + user.getID() + " entered to her/his messenger");

        String respond;

        System.out.println("Choose between these options:");
        System.out.println("""
                Saved Messages\s
                View chats\s
                View folders\s
                Compose\s
                Back
                """);

        respond = scanner.nextLine();

        switch (respond)
        {
            case "Saved Messages" -> SavedMassagesResponder(user , user.getMessenger().getSavedMessages());
            case "View chats" -> ViewChatsResponder(user);
            case "View folders" -> ViewFoldersResponder(user);
            case "Compose" -> ComposeResponder(user);
            case "Back" ->{
                return;
            }

            default -> {
                logger.info("an unknown command entered");

                System.out.println("Invalid input!");
            }
        }
        MessengerResponder(user);
    }

    public static void SettingResponder(User user)
    {
        logger.info("this user : " + user.getID() + " entered to her/his settings");

        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        String respond;

        System.out.println(ConsoleColors.BLUE + "Choose between these options");
        System.out.println(ConsoleColors.CYAN + """
                Privacy\s
                Account's status\s
                Log out\s
                Delete account\s
                Back\s
                """);

        respond = scanner.nextLine();

        switch (respond) {
            case "Privacy" -> PrivacyResponder(user);
            case "Account's status" -> AccountsStatusResponder(user);
            case "Log out" -> LogOutResponder();
            case "Delete account" -> DeleteAccountResponder(user);
            case "Back" -> {
                return;
            }
            default -> {
                logger.info("an unknown command entered");

                System.out.println(ConsoleColors.RED + "Invalid input!");
            }
        }
        SettingResponder(user);
    }

    public static void TweetingResponder(User user)
    {
        logger.info("this user : " + user.getID() + " tweeted something.");

        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        String respond;

        System.out.println(ConsoleColors.BLUE + "Write the text you want to Tweet:");
        respond = scanner.nextLine();
        Tweet.NewTweet(user.getID() , respond , new Date() , null , null  , false);

        System.out.println(ConsoleColors.GREEN + "Your tweet uploaded successfully!");
    }

    public static void ViewTweetsResponder(LinkedList<String> List , User user ,int ind)
    {
        logger.info("this user : " + user.getID() + " is viewing a list of tweets");

        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        String respond="";
        int index = ind;

        if(index==-1)
        {
            System.out.println(ConsoleColors.BLACK + "There is nothing to show here =(");
            System.out.println(ConsoleColors.CYAN + "Back");
            respond = scanner.nextLine();

            if(respond.equals("Back"))
                return;

            else
            {
                logger.info("an unknown command entered");

                System.out.println(ConsoleColors.RED + "Invalid input!");
            }

            ViewTweetsResponder(List , user , ind);
        }

        else {
            while (true)
            {
                Tweet tweet = Tweet.FindTweet(List.get(index));
                boolean available = true;

                if(!User.FindUser(tweet.getUserID()).isActive())
                {
                    logger.warn("this user : " + user.getID() + " is viewing a tweet from a deactivated/deleted account");

                    System.out.println(ConsoleColors.RED + "viewing this item is unavailable, the user has deleted or deactivated her/his account!");
                    available = false;
                }

                if(tweet.getRetweetID() != null && available)
                {
                    if (!User.FindUser(tweet.getRetweetID()).isActive())
                    {
                        logger.warn("this user : " + user.getID() + " is viewing a tweet from a deactivated/deleted account");

                        System.out.println(ConsoleColors.RED + "viewing this item is unavailable, the user has deleted or deactivated her/his account!");
                        available = false;
                    }
                }

                if(available)
                {
                    if (tweet.getRetweetID() != null)
                        System.out.println(ConsoleColors.BLACK + "retweeted by " + User.FindUser(tweet.getRetweetID()).getUsername() +
                                " at " + tweet.getRetweetDate());

                    System.out.print(ConsoleColors.BLUE + User.FindUser(tweet.getUserID()).getUsername()  +
                            " at " + tweet.getDate());

                    System.out.println(ConsoleColors.BLUE + (tweet.isCommented() ? " commented: " : " tweeted: "));

                    System.out.println(ConsoleColors.WHITE + tweet.getText());

                    System.out.println(ConsoleColors.BLACK + (tweet.getLikes().size()) + " likes    " +
                            (tweet.getRetweets().size()) + " retweets");
                }

                System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
                System.out.println();

                System.out.println(ConsoleColors.BLUE + "Choose between these options:");

                if(index != List.size()-1)
                    System.out.println(ConsoleColors.CYAN + "Prev");

                if(index != 0)
                    System.out.println(ConsoleColors.CYAN + "Next");

                if(available)
                    System.out.println(ConsoleColors.CYAN + "More options");

                System.out.println(ConsoleColors.CYAN + "Back");

                respond = scanner.nextLine();

                if(respond.equals("Prev") && index != List.size()-1)
                    index++;

                else if(respond.equals("Next") && index != 0)
                    index--;

                else if(respond.equals("More options"))
                    ViewTweetsMoreOptions(List , user , index);

                else if(respond.equals("Back"))
                    return;

                else
                {
                    logger.info("an unknown command entered");
                    System.out.println(ConsoleColors.RED + "Invalid input!");
                    ViewTweetsResponder(List, user, ind);
                }

            }
        }
    }

    private static void ViewTweetsMoreOptions(LinkedList<String> List , User user ,int index)
    {
        logger.info("this user : " + user.getID() + " wanted more options for reacting to a tweet");

        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        String respond ;
        Tweet tweet = Tweet.FindTweet(List.get(index));

        System.out.println(ConsoleColors.BLUE + "Choose between these options:");
        System.out.println(ConsoleColors.CYAN + (tweet.getLikes().contains(user.getID())? "Dislike" : "Like"));
        System.out.println(ConsoleColors.CYAN + """
                        Retweet\s
                        Save\s
                        Forward\s
                        View Profile\s
                        View comments\s
                        Write a comment
                        """);

        if(!tweet.getUserID().equals(user.getID()))
            System.out.println(ConsoleColors.CYAN + """
                    Block\s
                    Mute\s
                    Report\s
                    """);

        System.out.println(ConsoleColors.CYAN + "Back");

        respond = scanner.nextLine();

        switch (respond) {
            case "Like" ->
                tweet.Liked(user.getID());

            case "Dislike" ->
                tweet.Disliked(user.getID());

            case "Retweet" ->
                tweet.Retweet(user);

            case "Save" -> {
                user.getMessenger().savedMessages(List.get(index));
                System.out.println(ConsoleColors.GREEN + "Your message saved successfully!");
            }

            case "Forward" -> ForwardResponder(List.get(index) , user);

            case "Block" -> {
                user.Block(tweet.getUserID());
                System.out.println(ConsoleColors.GREEN + "You Blocked the owner of this tweet.");
            }

            case "Mute" -> {
                user.Mute(tweet.getUserID());
                System.out.println(ConsoleColors.GREEN + "You muted the owner of this tweet.");
            }

            case "View Profile" -> {
                if(user.getID().equals(tweet.getUserID()))
                    infoResponder(user);

                else
                    ViewProfileResponder(user , User.FindUser(tweet.getUserID()));
            }

            case "Report" ->
                User.FindUser(tweet.getUserID()).addToReport();

            case "View comments" -> ViewTweetsResponder(tweet.getComments(), user , tweet.getComments().size()-1);

            case "Write a comment" -> WriteCommentResponder(tweet , user);

            case "Back" ->{
                return;
            }

            default -> {
                logger.info("an unknown command entered");
                System.out.println(ConsoleColors.RED + "Invalid input!");
            }
        }

        ViewTweetsMoreOptions(List , user , index);
    }

    private static void ViewProfileResponder(User user , User owner)
    {
        logger.info("this user : " + user.getID() + " is viewing someones profile");

        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        if(owner.isDeleted())
        {
            logger.warn("this user : " + user.getID() + " wants to view a deleted account.");

            String respond;

            System.out.println(ConsoleColors.BLACK + "This account has been deleted.");
            System.out.println(ConsoleColors.CYAN + "Back");
            respond = scanner.nextLine();

            if(respond.equals("Back"))
                return;

            else
            {
                logger.info("an unknown command entered");
                System.out.println(ConsoleColors.RED + "Invalid input!");
            }

            ViewProfileResponder(user , owner);
        }

        if(!owner.isActive())
        {
            logger.warn("this user : " + user.getID() + " wants to view a deactivated account");

            String respond;

            System.out.println(ConsoleColors.WHITE + "Username : " + owner.getUsername());
            System.out.println("Name : " + owner.getName());
            System.out.println();
            System.out.println(ConsoleColors.BLACK + "This account isn't active");

            System.out.println();
            System.out.println(ConsoleColors.CYAN + "Back");
            respond = scanner.nextLine();

            if(respond.equals("Back"))
                return;

            else
            {
                logger.info("an unknown command entered");
                System.out.println(ConsoleColors.RED + "Invalid input!");
            }

            ViewProfileResponder(user , owner);
        }

        if(user.getBlocked().contains(owner.getID()))
        {
            String respond;

            System.out.println(ConsoleColors.WHITE + "Username : " + owner.getUsername());
            System.out.println("Name : " + owner.getName());
            System.out.println();
            System.out.println(ConsoleColors.BLACK + "You have Blocked this user!");
            System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
            System.out.println();

            System.out.println(ConsoleColors.BLUE + "Choose between these options");
            System.out.println(ConsoleColors.CYAN + """
                    Unblock\s
                    Back
                    """);

            respond = scanner.nextLine();

            if(respond.equals("Unblock"))
            {
                user.Unblock(owner.getID());
                System.out.println(ConsoleColors.GREEN + "You unblocked this user.");
            }


            else if(respond.equals("Back"))
                return;

            else
            {
                logger.info("an unknown command entered");
                System.out.println(ConsoleColors.RED + "Invalid input!");
            }


            ViewProfileResponder(user , owner);
        }

        else if(owner.getBlocked().contains(user.getID()))
        {
            String respond;
            System.out.println(ConsoleColors.WHITE + "Username : " + owner.getUsername());
            System.out.println("Name : " + owner.getName());
            System.out.println();
            System.out.println(ConsoleColors.BLACK + "Viewing this user's profile is unavailable for you.");
            System.out.println();

            System.out.println(ConsoleColors.CYAN + "Back");
            respond = scanner.nextLine();

            if(respond.equals("Back"))
                return;

            else
            {
                logger.info("an unknown command entered");
                System.out.println(ConsoleColors.RED + "Invalid input!");
            }

            ViewProfileResponder(user , owner);
        }

        else if(owner.getPrivate() && !owner.getFollowers().contains(user.getID()))
        {
            String respond;

            if(owner.getRequested().contains(user.getID()))
                System.out.println(ConsoleColors.BLACK + "Requested");

            System.out.println(ConsoleColors.WHITE + "Username : " + owner.getUsername());
            System.out.println("Name : " + owner.getName());
            System.out.println();
            System.out.println(ConsoleColors.BLACK + "This account is private. you must follow this account to view this profile");
            System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
            System.out.println();

            System.out.println(ConsoleColors.BLUE + "Choose between these options:");

            if(!owner.getRequested().contains(user.getID()))
                System.out.println(ConsoleColors.CYAN + """
                        Follow\s
                        Block\s
                        Report
                        """);

            System.out.println(ConsoleColors.CYAN + "Back");

            respond = scanner.nextLine();

            switch (respond) {
                case "Follow":
                    FollowingResponder(user, owner);
                    break;

                case "Block":
                {
                    user.Block(owner.getID());
                    System.out.println(ConsoleColors.GREEN + "You blocked this user.");
                }
                    break;

                case "Report":
                {
                    owner.addToReport();
                    System.out.println(ConsoleColors.GREEN + "You reported this user.");
                }
                    break;

                case "Back":
                    return;

                default:
                {
                    logger.info("an unknown command entered");
                    System.out.println(ConsoleColors.RED + "Invalid input!");
                }
                    break;
            }

            ViewProfileResponder(user , owner);
        }

        else
        {
            String respond;

            if(user.getFollowings().contains(owner.getID()))
                System.out.println(ConsoleColors.GREEN + "Following");

            System.out.println(ConsoleColors.WHITE + "Username : " + owner.getUsername());
            System.out.println(ConsoleColors.WHITE + "Name : " + owner.getName());

            if(owner.isVisibleEmail())
                System.out.println(ConsoleColors.PURPLE + "E-mail : " + owner.getEmail());

            if(owner.isVisiblePhone() && owner.getPhone() != null)
                System.out.println(ConsoleColors.PURPLE + "Phone number : " +  owner.getPhone());

            if(owner.isVisibleDate() && owner.getDate() != null)
                System.out.println(ConsoleColors.PURPLE + "Date of birth : " + owner.getDate());

            System.out.println(ConsoleColors.BLUE + owner.getBio());
            System.out.println(ConsoleColors.BLACK + "Last seen " + (user.VisibleLastSeen(owner) ? owner.getLastSeen() : "recently") );
            System.out.println();
            System.out.println(ConsoleColors.BLACK + owner.getFollowers().size() + " followers     " +
                    owner.getFollowings().size() + " following       " +
                    owner.getTweets().size() + " tweets and retweets");

            System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
            System.out.println();

            System.out.println(ConsoleColors.BLUE + "Choose between these options: ");

            if(user.getFollowings().contains(owner.getID()))
                System.out.println(ConsoleColors.CYAN + "Message" + "\nUnfollow");

            else
                System.out.println(ConsoleColors.CYAN + "Follow");

            System.out.println(ConsoleColors.CYAN + """
                    Block\s
                    Report\s
                    View tweets\s
                    Back
                    """);

            respond = scanner.nextLine();

            switch (respond) {
                case "Unfollow" -> Notification.NoticeToUnfollow(user , owner);
                case "Follow" -> FollowingResponder(user , owner);
                case "Message" -> DirectResponder(user, owner);
                case "Block" -> user.Block(owner.getID());
                case "Report" -> owner.addToReport();
                case "View tweets" -> ViewTweetsResponder(owner.getTweets(), user, owner.getTweets().size() - 1);
                case "Back" -> {
                    return;
                }

                default -> System.out.println(ConsoleColors.RED + "Invalid input!");
            }
            ViewProfileResponder(user, owner);
        }
    }

    private static void ForwardResponder(String tweetID , User user)
    {
        logger.info("this user : " + user.getID() + " wants to forward something");
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();
        String respond;

        System.out.println(ConsoleColors.BLUE + "Enter an username:");
        respond = scanner.nextLine();

        User receiver = User.FindUsername(respond);

        if(receiver == null)
            System.out.println(ConsoleColors.RED + "This username doesn't exist!");

        else if(!receiver.isActive())
            System.out.println(ConsoleColors.RED + "This user has deleted or deactivated her/his account!");

        else if(!user.getFollowings().contains(receiver.getID()))
            System.out.println(ConsoleColors.RED + "You are not following this user.");

        else
        {
            System.out.println(ConsoleColors.GREEN + "Your message forwarded successfully!");
            user.getMessenger().Forward(tweetID , receiver);
        }
    }

    private static void FollowingResponder(User user , User owner )
    {
        logger.info("this user : " + user.getID() + " wants to follow someone");

        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        if(!owner.getPrivate())
        {
            Notification.NoticeToFollow(user , owner);
            System.out.println(ConsoleColors.GREEN + "You followed this account successfully!");
            return;
        }

        Notification.Request(user , owner);
        System.out.println(ConsoleColors.GREEN + "your request has been sent.");
    }

    public static void DirectResponder(User sender, User Receiver)
    {
        logger.info("this user : " + sender.getID() + " wants to send message to : " + Receiver.getID());
        ShowChatBox(sender , sender.getMessenger().CreateChatBox(Receiver));
    }

    public static void SearchUserResponder(User user)
    {
        logger.info("this user : " + user.getID() + " wants to search in explorer");

        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        String respond;

        System.out.println(ConsoleColors.BLUE + "Enter the username you are looking for or enter \"Back\". ");
        respond = scanner.nextLine();

        if(respond.equals("Back"))
            return;

        else if(User.FindUsername(respond) != null)
            if(User.FindUsername(respond).isActive())
                ViewProfileResponder(user , User.FindUsername(respond));

        else
            System.out.println(ConsoleColors.RED + "Such a username doesn't exist");

        SearchUserResponder(user);
    }

    private static void WriteCommentResponder(Tweet tweet , User user)
    {
        logger.info("this user : " + user.getID() + " wants to comment something");

        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        String respond;
        Date date = new Date();

        System.out.println(ConsoleColors.BLUE + "Write your comment here:");

        respond = scanner.nextLine();

        Tweet comment = new Tweet(user.getID() , respond , date , null , null , true);
        tweet.setComments(comment);
        user.setTweets(comment.getTweetID());
    }

    private static void ChangeProfileResponder(User user)
    {
        logger.info("this user : " + user.getID() + " wants to change its profile");

        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        String respond;

        System.out.println(ConsoleColors.BLUE + "Choose between these options:");

        System.out.println(ConsoleColors.CYAN + """
                E-mail\s
                Name\s
                Username\s
                Phone\s
                Date of birth\s
                Bio\s
                Password\s
                Back
                """);

        respond = scanner.nextLine();

        switch (respond) {
            case "E-mail" -> ChangeEmailResponder(user);
            case "Name" -> ChangeNameResponder(user);
            case "Username" -> ChangeUsernameResponder(user);
            case "Phone" -> ChangePhoneResponder(user);
            case "Date of birth" -> ChangeDateResponder(user);
            case "Bio" -> ChangeBioResponder(user);
            case "Password" -> ChangePasswordResponder(user);
            case "Back" -> {
                return;
            }
            default ->System.out.println(ConsoleColors.RED + "Invalid input!");
        }
        ChangeProfileResponder(user);
    }

    private static void ChangeEmailResponder(User user)
    {
        logger.info("this user : " + user.getID() + " wants to change its E-mail");

        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        String respond;

        System.out.println(ConsoleColors.BLUE + "Write your new E-mail address or enter \"Back\":");

        respond = scanner.nextLine();

        if(respond.equals("Back"))
            return;

        while(!user.ChangeEmail(respond))
        {
            System.out.println(ConsoleColors.RED + "This E-mail address is already taken!");
            ChangeEmailResponder(user);
        }

        boolean valid = false;
        while(!valid)
        {
            System.out.println(ConsoleColors.BLUE + "Do you want your E-mail address to be visible?(Yes/No)");
            respond = scanner.nextLine();

            if (respond.equals("Yes"))
            {
                user.setVisibleEmail(true);
                valid = true;
            }

            else if (respond.equals("No"))
            {
                user.setVisibleEmail(false);
                valid = false;
            }

            else
                System.out.println(ConsoleColors.RED + "Invalid input!");
        }

    }

    private static void ChangeNameResponder(User user)
    {
        logger.info("this user : " + user.getID() + " wants to change its name");

        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        String respond;

        System.out.println(ConsoleColors.BLUE + "Enter your name or enter \"Back\"");

        respond = scanner.nextLine();

        if(respond.equals("Back"))
            return;

        user.setName(respond);
    }

    private static void ChangeUsernameResponder(User user)
    {
        logger.info("this user : " + user.getID() + " wants to change its username");


        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        String respond;

        System.out.println(ConsoleColors.BLUE + "Write your new username address or enter \"Back\":");

        respond = scanner.nextLine();

        if(respond.equals("Back"))
            return;

        while(!user.ChangeUsername(respond))
        {
            System.out.println(ConsoleColors.RED + "This username is already taken!");
            ChangeUsernameResponder(user);
        }
    }

    private static void ChangePhoneResponder(User user)
    {
        logger.info("this user : " + user.getID() + " wants to change its phone number");


        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        String respond;

        System.out.println(ConsoleColors.BLUE + "Enter your new phone number or enter \"Back\"");

        respond = scanner.nextLine();

        if(respond.equals("Back"))
            return;

        user.setPhone(respond);

        boolean valid = false;
        while(!valid)
        {
            System.out.println(ConsoleColors.BLUE + "Do you want your phone number to be visible?(Yes/No)");
            respond = scanner.nextLine();

            if (respond.equals("Yes"))
            {
                user.setVisiblePhone(true);
                valid = true;
            }

            else if (respond.equals("No"))
            {
                user.setVisiblePhone(false);
                valid = false;
            }

            else
                System.out.println(ConsoleColors.RED + "Invalid input!");
        }
    }

    private static void ChangeBioResponder(User user)
    {
        logger.info("this user : " + user.getID() + " wants to change its bio");

        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        String respond;

        System.out.println(ConsoleColors.BLUE + "Enter your bio or enter \"Back\"");

        respond = scanner.nextLine();

        if(respond.equals("Back"))
            return;

        user.setBio(respond);
    }

    private static void ChangePasswordResponder(User user)
    {
        logger.info("this user : " + user.getID() + " wants to change its password");


        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        String respond;

        System.out.println(ConsoleColors.BLUE + "Enter your new password or enter \"Back\" ");

        respond = scanner.nextLine();

        if(respond.equals("Back"))
            return;

        user.setPassword(respond);
    }

    private static void ChangeDateResponder(User user)
    {
        logger.info("this user : " + user.getID() + " wants to change its date of birth");


        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        String respond;

        System.out.println(ConsoleColors.BLUE + "Enter your new date of birth or enter \"Back\" ");

        respond = scanner.nextLine();

        if(respond.equals("Back"))
            return;

        user.setDate(respond);

        boolean valid = false;
        while(!valid)
        {
            System.out.println(ConsoleColors.BLUE + "Do you want your date of birth to be visible?(Yes/No)");
            respond = scanner.nextLine();

            if (respond.equals("Yes"))
            {
                user.setVisibleDate(true);
                valid = true;
            }

            else if (respond.equals("No"))
            {
                user.setVisibleDate(false);
                valid = false;
            }

            else
                System.out.println(ConsoleColors.RED + "Invalid input!");
        }
    }

    private static void ListsResponder(User user)
    {
        logger.info("this user : " + user.getID() + " entered the lists");

        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();
        String respond;

        System.out.println(ConsoleColors.BLUE + "Choose between these options:");
        System.out.println(ConsoleColors.CYAN + """
                View followers\s
                View followings\s
                Black list\s
                Back
                """);

        respond = scanner.nextLine();

        switch (respond) {
            case "View followers" ->
                ViewUsersListResponder(user.getFollowers() , user);

            case "View followings" ->
                ViewUsersListResponder(user.getFollowings() , user);

            case "Black list" ->
                ViewUsersListResponder(user.getBlocked() , user);

            case "Back" -> {
                return;
            }

            default -> System.out.println(ConsoleColors.RED + "Invalid input!");
        }

        ListsResponder(user);
    }

    private static void ViewUsersListResponder(LinkedList<String> List ,User user)
    {
        logger.info("this user : " + user.getID() + " wants to view a list");

        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        String respond;
        int i = List.size()-1 ;

        if(i == -1)
        {
            System.out.println(ConsoleColors.BLUE + "Nothing to show here");
            System.out.println(ConsoleColors.CYAN + "Back");
            respond = scanner.nextLine();

            if(respond.equals("Back"))
                return;

            else
                System.out.println(ConsoleColors.RED + "Invalid input!");

            ViewUsersListResponder(List , user);
        }

        while(i<List.size())
        {
            LinkedList<String> ShowedUsers = new LinkedList<>();
            for(int j=i ; j>=0 && j>i-4 ; j--)
            {
                if (!User.FindUser(List.get(j)).isActive())
                    System.out.println(ConsoleColors.RED + "deleted or deactivated account.");

                else
                {
                    User ViewUser = User.FindUser(List.get(j));
                    ShowedUsers.add(ViewUser.getUsername());

                    if (ViewUser.getFollowings().contains(user.getID()))
                        System.out.println(ConsoleColors.BLACK + "Follows you");

                    System.out.println(ConsoleColors.RESET + "@" + ViewUser.getUsername());
                    System.out.println(ViewUser.getName());
                    System.out.println(ConsoleColors.BLUE + ViewUser.getBio());
                    System.out.println(ConsoleColors.BLACK + "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
                }
            }

            System.out.println(ConsoleColors.BLUE + "Page " + ((List.size()-i+3)/4) + "/" + (List.size()+3)/4);
            System.out.println();

            System.out.println(ConsoleColors.BLUE + "Choose between these options:");
            System.out.println(ConsoleColors.CYAN + "View Profile");

            if((List.size()-i+3)/4 != 1)
                System.out.println("Prev page");

            if((List.size()-i+3)/4 != (List.size()+3)/4)
                System.out.println("Next page");

            System.out.println("Back");

            respond = scanner.nextLine();

            if(respond.equals("View Profile"))
                ShowProfilesResponder(ShowedUsers, user);

            else if(respond.equals("Prev page") && (List.size()-i+3)/4 != 0)
                i += 4;

            else if(respond.equals("Next page") && (List.size()-i+3)/4 != (List.size()+3)/4)
                i -= 4;

            else if(respond.equals("Back"))
                return;

            else
                System.out.println(ConsoleColors.RED + "Invalid input!");

            ViewUsersListResponder(List , user);
        }
    }

    private static void ShowProfilesResponder (LinkedList<String> List ,User user)
    {
        logger.info("this user : " + user.getID() + " wants to choose a user to view its profile");

        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        String respond;

        System.out.println(ConsoleColors.BLUE + "Enter the username or enter \"Back\": ");

        respond = scanner.nextLine();

        if(respond.equals("Back"))
            return;

        if(List.contains(respond))
            ViewProfileResponder(user , User.FindUsername(respond));

        else
            System.out.println(ConsoleColors.RED + "There is no such a username in this page.");

        ShowProfilesResponder(List , user);
    }

    private static void infoResponder(User user)
    {
        logger.info("this user : " + user.getID() + " wants to view its profile");

        String respond;
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        System.out.println(ConsoleColors.RESET + "Username : " + user.getUsername());
        System.out.println("Name : " + user.getName());
        System.out.println(ConsoleColors.PURPLE + "E-mail : " + user.getEmail());

        if(user.getPhone() != null)
            System.out.println(ConsoleColors.PURPLE +"Phone number : " + user.getPhone());

        if(user.getDate() != null)
            System.out.println(ConsoleColors.PURPLE +"Date of birth : " + user.getDate());

        System.out.println(ConsoleColors.BLUE + user.getBio());

        System.out.println(ConsoleColors.BLACK + user.getFollowers().size() + " followers      " +
                user.getFollowings().size() + " followings     " + user.getTweets().size() + " Tweets, retweets and comments");

        if(user.getPrivate())
            System.out.println(ConsoleColors.BLACK + "Private account");

        System.out.println();
        System.out.println(ConsoleColors.CYAN + "Back");

        respond = scanner.nextLine();

        if(respond.equals("Back"))
            return;

        else
            System.out.println(ConsoleColors.RED + "Invalid input!");

        infoResponder(user);

    }

    private static void NotificationsResponder(User user)
    {
        logger.info("this user : " + user.getID() + " entered in notifications");

        String respond;
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        System.out.println(ConsoleColors.BLUE + "Choose between these options:");
        System.out.println(ConsoleColors.CYAN + """
                Users notifications\s
                System notification\s
                Status of requests\s
                Back
                """);
        respond = scanner.nextLine();

        switch (respond) {
            case "Users notifications":
                AnswerNotificationsResponder(user.getUsersNotifications() , user.getUsersNotifications().size()-1);
                break;

            case "System notification":
                ShowNotificationsResponder(user.getSystemNotifications() , user.getSystemNotifications().size()-1);
                break;

            case "Status of requests" :
                ShowNotificationsResponder(user.getStatusNotifications() , user.getStatusNotifications().size()-1);
                break;

            case "Back":
                return;

            default:
                System.out.println(ConsoleColors.RED + "Invalid input!");
                break;
        }

        NotificationsResponder(user);
    }

    private static void AnswerNotificationsResponder(LinkedList<Notification> notifications , int ind)
    {
        logger.info("this user wants to answer a notification");

        String respond;
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        if(ind == -1)
        {
            System.out.println(ConsoleColors.BLUE + "There is nothing to show, just an empty list of notifications =|");
            System.out.println(ConsoleColors.CYAN + "Back");
            respond = scanner.nextLine();

            if(!respond.equals("Back"))
                System.out.println(ConsoleColors.RED + "Invalid input!");

            else
                return;

            AnswerNotificationsResponder(notifications , ind);
        }


        System.out.println(ConsoleColors.RESET + notifications.get(ind).getText());
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        System.out.println(ConsoleColors.BLUE + "Choose between these options:");

        if(ind != notifications.size()-1)
            System.out.println(ConsoleColors.CYAN + "Prev");

        if(ind != 0)
            System.out.println(ConsoleColors.CYAN + "Next");

        System.out.println(ConsoleColors.CYAN + """
                    Accept\s
                    Deny and notify\s
                    Deny and don't notify\s
                    Back
                    """);

        respond = scanner.nextLine();

        if(respond.equals("Prev") && ind != notifications.size()-1)
            AnswerNotificationsResponder(notifications , ind+1);

        else if(respond.equals("Next") && ind != 0)
            AnswerNotificationsResponder(notifications , ind-1);

        else if(respond.equals("Accept"))
        {
            if(!notifications.get(ind).Accept(ind))
                System.out.println(ConsoleColors.RED + "This user has deleted her/his account");

            AnswerNotificationsResponder(notifications , notifications.size()-1);
        }

        else if(respond.equals("Deny and notify"))
        {
            if(!notifications.get(ind).Deny(ind ,true))
                System.out.println(ConsoleColors.RED + "This user has deleted her/his account");

            AnswerNotificationsResponder(notifications , notifications.size()-1);
        }


        else if(respond.equals("Deny and don't notify"))
        {
            if(!notifications.get(ind).Deny(ind ,false))
                System.out.println(ConsoleColors.RED + "This user has deleted her/his account");

            AnswerNotificationsResponder(notifications , notifications.size()-1);
        }

        else if(respond.equals("Back"))
            return;

        else
            System.out.println(ConsoleColors.RED + "Invalid input!");

        AnswerNotificationsResponder(notifications , ind);

    }

    private static void ShowNotificationsResponder(LinkedList<Notification> notifications , int ind)
    {
        logger.info("this user wants to answer a notification");

        String respond;
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        if(ind == -1)
        {
            System.out.println(ConsoleColors.BLUE + "There is nothing to show, just an empty list of notifications =|");
            System.out.println(ConsoleColors.CYAN + "Back");
            respond = scanner.nextLine();

            if(!respond.equals("Back"))
                System.out.println(ConsoleColors.RED + "Invalid input!");

            else
                return;

            ShowNotificationsResponder(notifications , ind);
        }

        for(int i=ind ; i>=0 ; i -= 4)
        {
            for(int j=i ; j>i-4 && j>=0 ; j--)
            {
                System.out.println(ConsoleColors.PURPLE + notifications.get(j).getText());
                System.out.println(ConsoleColors.BLACK + "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            }

            System.out.println(ConsoleColors.BLUE + "page " + (notifications.size()-ind+3)/4 + "/" +(notifications.size()+3)/4);

            if((notifications.size()-ind+3)/4 != 1)
                System.out.println(ConsoleColors.CYAN + "Prev");

            if((notifications.size()-ind+3)/4 != (notifications.size()+3)/4)
                System.out.println(ConsoleColors.CYAN + "Next");

            System.out.println(ConsoleColors.CYAN + "Back");

            respond = scanner.nextLine();

            if(respond.equals("Prev") && (notifications.size()-ind+3)/4 != 1)
                ShowNotificationsResponder(notifications , i+4);

            else if(respond.equals("Next") && (notifications.size()-ind+3)/4 != (notifications.size()+3)/4)
                ShowNotificationsResponder(notifications , i-4);

            else if(respond.equals("Back"))
                return;

            else
                System.out.println(ConsoleColors.RED + "Invalid input!");

            ShowNotificationsResponder(notifications , ind);
        }
    }

    private static void SavedMassagesResponder(User user , ChatBox chat)
    {
        logger.info("this user : " + user.getID() + " entered in saved messages");


        String respond;
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        int index = chat.getSeen().size()-1;
        while(true)
        {
            for(int j=Math.max(0 , index-3) ; j<=index ; j++)
            {
                System.out.println(ConsoleColors.WHITE + chat.getSeen().get(j));
                System.out.println(ConsoleColors.BLACK + "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            }

            System.out.println(ConsoleColors.BLUE + "Choose between these options:");

            if(index-4 > 0)
                System.out.println(ConsoleColors.CYAN + "Prev");

            if(index != chat.getSeen().size()-1)
                System.out.println(ConsoleColors.CYAN + "Next");

            System.out.println(ConsoleColors.CYAN + "Write a message");
            System.out.println("Back");

            respond = scanner.nextLine();

            switch (respond) {
                case "Prev":
                    index -= 4;
                    break;

                case "Next":
                    index += 4;
                    break;

                case "Write a message":
                    SaveAMessageResponder(user, chat);
                    ShowChatBox(user , chat);
                    break;

                case "Back":
                    return;

                default:
                    System.out.println(ConsoleColors.RED + "Invalid input!");
                    break;
            }
        }
    }

    private static void SaveAMessageResponder(User user,ChatBox chat)
    {
        logger.info("this user : " + user.getID() + " wants to save a message");

        String respond;
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        System.out.println(ConsoleColors.BLUE + "Write your message here or enter \"Back\":");
        respond = scanner.nextLine();

        if(respond.equals("Back"))
            return;

        chat.SaveMessage(user , respond);
    }

    private static void ViewChatsResponder(User user)
    {
        logger.info("this user : " + user.getID() + " entered in Chats");

        String respond;
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        System.out.println(ConsoleColors.BLUE + "Choose a chat box or enter \"Back\":");
        System.out.println(ConsoleColors.CYAN);

        for(ChatBox chatBox : user.getMessenger().getChats())
        {
            if(chatBox.getUnseen().size() != 0)
                System.out.println(User.FindUser(chatBox.getUserBID()).getUsername() + " (" + chatBox.getUnseen().size() +")");
        }

        for(ChatBox chatBox : user.getMessenger().getChats())
        {
            if(chatBox.getUnseen().size() == 0)
                System.out.println(User.FindUser(chatBox.getUserBID()).getUsername());
        }

        respond = scanner.nextLine();

        if(respond.equals("Back"))
            return;

        ChatBox chatBox = user.getMessenger().FindChatBox(respond);

        if(chatBox == null)
            System.out.println(ConsoleColors.RED + "Such a chat box doesn't exist");

        else
            ShowChatBox(user , chatBox);

        ViewChatsResponder(user);
    }

    private static void ViewFoldersResponder(User user)
    {
        logger.info("this user : " + user.getID() + " entered in folders");

        String respond;
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        System.out.println(ConsoleColors.BLUE + "Choose between these folders to view them:");

        for(Group group : user.getMessenger().getGPList())
            System.out.println(ConsoleColors.CYAN + group.getName());

        System.out.println(ConsoleColors.BLUE + "or, choose between these options");
        System.out.println(ConsoleColors.CYAN + """
                Add new folder\s
                Back
                """);

        respond = scanner.nextLine();
        boolean valid = false;

        if(respond.equals("Back"))
            return;

        else if(respond.equals("Add new folder")) {
            valid = true;
            CreateAFolderResponder(user);
        }

        else if(!valid)
            for(Group group : user.getMessenger().getGPList()) {
                if (group.getName().equals(respond))
                {
                    valid = true;
                    ViewAFolderResponder(user, group);
                }
            }

        else
            System.out.println(ConsoleColors.RED + "Invalid input!");

        ViewFoldersResponder(user);
    }

    private static void CreateAFolderResponder(User user)
    {
        logger.info("this user : " + user.getID() + " create a folder");


        String respond , name ;
        LinkedList<String> users = new LinkedList<>(); //List of IDs
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        System.out.println(ConsoleColors.BLUE + "You are about to create a new folder, enter anything or enter \"Back\" :");
        respond = scanner.nextLine();

        if(respond.equals("Back"))
            return;

        name = respond;

        System.out.println(ConsoleColors.BLUE + "Enter the username you wanted to be added to your folder :");

        boolean finish = false;
        while(!finish)
        {
            System.out.println(ConsoleColors.BLUE + "Username: ");
            respond = scanner.nextLine();

            User addedUser = User.FindUsername(respond);

            if(addedUser != null)
                if(user.getFollowings().contains(addedUser.getID()) && !addedUser.isActive())
                    addedUser = null;

            if(addedUser == null)
                System.out.println(ConsoleColors.RED + "Such an user doesn't exist in your followings!");

            else
                users.add(addedUser.getID());

            System.out.println(ConsoleColors.CYAN + " enter anything or enter \"Finish\"");
            respond = scanner.nextLine();

            if(respond.equals("Finish"))
                finish = true;
        }

        user.getMessenger().CreateNewFolder(name , users);
        System.out.println(ConsoleColors.GREEN + "Your folder created successfully!");
    }

    private static void ViewAFolderResponder(User user , Group group)
    {
        logger.info("this user : " + user.getID() + " wants to view a list");

        String respond;
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        System.out.println(ConsoleColors.BLUE + "Members :");
        System.out.println(ConsoleColors.PURPLE);

        for(String ID : group.getMembersID())
        {
            User member = User.FindUser(ID);

            if(member.isActive())
                System.out.println(member.getUsername());
        }

        System.out.println(ConsoleColors.BLUE + "Choose between these options:");
        System.out.println(ConsoleColors.CYAN + """
                Add a member\s
                Remove a member\s
                Delete the folder\s
                Back
                """);

        respond = scanner.nextLine();

        switch (respond) {
            case "Add a member":
                AddAMemberResponder(user, group);
                break;

            case "Remove a member":
                RemoveAMemberResponder(user, group);
                break;

            case "Delete the folder":
                DeleteFolderResponder(user, group);
                break;

            case "Back":
                return;

            default:
                System.out.println(ConsoleColors.RED + "Invalid input!");
                break;
        }

        ViewAFolderResponder(user , group);
    }

    private static void AddAMemberResponder(User user ,Group group)
    {
        logger.info("this user : " + user.getID() + " wants to add someone in a list");

        String respond;
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        System.out.println(ConsoleColors.BLUE + "Enter the username you want to add or enter \"Back\":");

        respond = scanner.nextLine();

        if(respond.equals("Back"))
            return;

        User addUser = User.FindUsername(respond);

        if(addUser == null)
            System.out.println(ConsoleColors.RED + "Such a username doesn't exist!");

        else if(!addUser.isActive())
            System.out.println(ConsoleColors.RED + "This user has deleted or deactivated her/his account!");

        else if(group.getMembersID().contains(addUser.getID()))
            System.out.println(ConsoleColors.RED + "This User is already in your folder!");

        else if(!user.getFollowers().contains(addUser.getID()))
            System.out.println(ConsoleColors.RED + "You can't add this user to your folders!");

        else
            group.addToMembers(addUser);
    }

    private static void RemoveAMemberResponder(User user ,Group group)
    {
        logger.info("this user : " + user.getID() + " wants to remove someone from a list");

        String respond;
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        System.out.println(ConsoleColors.BLUE + "Enter the username you want to delete or enter \"Back\":");

        respond = scanner.nextLine();

        if(respond.equals("Back"))
            return;

        User removeUser = User.FindUsername(respond);

        if(removeUser == null)
            System.out.println(ConsoleColors.RED + "Such a username doesn't exist");

        else if(!group.getMembersID().contains(removeUser.getID()))
            System.out.println(ConsoleColors.RED + "Couldn't find this user in your folder!");

        else
            group.RemoveAMember(removeUser);
    }

    private static void DeleteFolderResponder(User user , Group group)
    {
        logger.info("this user : " + user.getID() + " wants to delete a list");

        String respond;
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        System.out.println(ConsoleColors.BLUE + "Sure? (No/Yes)");

        respond = scanner.nextLine();

        if(respond.equals("No"))
            return;

        if(respond.equals("Yes"))
            user.getMessenger().DeleteFolder(group);

        else
            System.out.println(ConsoleColors.RED + "Invalid input!");
    }

    private static void ComposeResponder(User user)
    {
        logger.info("this user : " + user.getID() + " entered compose");

        String respond , txt;
        LinkedList<String> chats = new LinkedList<>();

        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        if(user.getFollowings().size() == 0)
        {
            System.out.println(ConsoleColors.RED + "You don't have any followings yet");
            return;
        }

        System.out.println("Choose a user or folder from below :");

        for(int i=0 ; i<user.getFollowings().size() ; i++)
        {
            if (User.FindUser(user.getFollowings().get(i)).isActive())
                System.out.println(ConsoleColors.CYAN + User.FindUser(user.getFollowings().get(i)).getUsername());

            else
                System.out.println(ConsoleColors.BLUE + "this user has deactivated or deleted his/her account");
        }

        for(int i=0 ; i<user.getMessenger().getGPList().size() ; i++)
            System.out.println(ConsoleColors.CYAN + user.getMessenger().getGPList().get(i).getName());

        boolean finish = false;

        while(!finish)
        {
            System.out.println("Enter the name:");
            respond = scanner.nextLine();

            User following = User.FindUsername(respond);
            if(following != null)
            {
                if(!user.getFollowings().contains(following.getID()))
                    following = null;
            }

            if(following == null && user.getMessenger().FindGroup(respond)==null)
                System.out.println(ConsoleColors.RED + "This name doesn't exist!");

            chats.add(respond);

            System.out.println(ConsoleColors.CYAN + "Type anything or enter \"Finish\"");
            respond = scanner.nextLine();

            if(respond.equals("Finish"))
                finish = true;
        }

        System.out.println(ConsoleColors.BLUE + "Write your message");
        respond = scanner.nextLine();

        user.getMessenger().Compose(chats , respond);
    }

    private static void ShowChatBox(User user , ChatBox chat)
    {
        logger.info("this user : " + user.getID() + " is viewing a chat box");

        String respond;
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        if(chat.getUnseen().size() != 0)
        {
            System.out.println(ConsoleColors.GREEN + chat.getUnseen().size() + " new messages.");

            for(int i=0 ; i<chat.getUnseen().size() ; i++)
            {
                System.out.println(ConsoleColors.WHITE + chat.getUnseen().get(i));
                System.out.println(ConsoleColors.BLACK + "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            }

            chat.SeenNewMessages();
            System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
            System.out.println();
            System.out.println(ConsoleColors.BLUE + "Choose between these options: ");
            System.out.println(ConsoleColors.CYAN + """
                    Write a message\s
                    Seen messages\s
                    Back
                    """);
            respond = scanner.nextLine();

            if(respond.equals("Write a message"))
                WriteNewMessageResponder(user , chat);

            else if(respond.equals("Back"))
                return;

            else if(!respond.equals("Seen messages"))
            {
                System.out.println(ConsoleColors.RED + "Invalid input!");
                //ShowChatBox(user, chat);
            }
        }

        int index = chat.getSeen().size()-1;

        if(index == -1)
        {
            System.out.println(ConsoleColors.BLUE + "There is nothing to show here.");
            System.out.println(ConsoleColors.CYAN + "Back" + "\nWrite a message");
            respond = scanner.nextLine();

            if(respond.equals("Back"))
                return;

            else if(respond.equals("Write a message"))
                WriteNewMessageResponder(user , chat);

            else
                System.out.println(ConsoleColors.RED + "Invalid input!");

            ShowChatBox(user, chat);
        }

        while(true)
        {
            for(int j=Math.max(0 , index-3) ; j<=index ; j++)
            {
                System.out.println(ConsoleColors.WHITE + chat.getSeen().get(j));
                System.out.println(ConsoleColors.BLACK + "- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            }

            System.out.println(ConsoleColors.BLUE + "Choose between these options:");

            if(index-4 > 0)
                System.out.println(ConsoleColors.CYAN + "Prev");

            if(index != chat.getSeen().size()-1)
                System.out.println(ConsoleColors.CYAN + "Next");

            System.out.println(ConsoleColors.CYAN + "Write a message");
            System.out.println("Back");

            respond = scanner.nextLine();

            switch (respond) {
                case "Prev":
                    index -= 4;
                    break;

                case "Next":
                    index += 4;
                    break;

                case "Write a message":
                    WriteNewMessageResponder(user, chat);
                    ShowChatBox(user , chat);
                    break;

                case "Back":
                    return;

                default:
                    System.out.println(ConsoleColors.RED + "Invalid input!");
                    break;
            }
        }
    }

    private static void WriteNewMessageResponder(User user , ChatBox chat)
    {
        logger.info("this user : " + user.getID() + " wants to write a new message");

        String respond;
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        System.out.println(ConsoleColors.BLUE + "Write your message here or enter \"Back\":");
        respond = scanner.nextLine();

        if(respond.equals("Back"))
            return;

        chat.sendMessage(user , respond);
    }

    private static void PrivacyResponder(User user)
    {
        logger.info("this user : " + user.getID() + " entered in privacy");

        String respond;
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        System.out.println(ConsoleColors.BLUE + "Choose between these options: ");
        System.out.println(ConsoleColors.CYAN + """
                Public/Private\s
                Visible data\s
                Last seen\s
                Change password\s
                Back
                """);

        respond = scanner.nextLine();

        switch (respond) {
            case "Public/Private":
                ChangeVisibility(user , "account" , (user.getPrivate()? "private" : "public"));
                break;
            case "Visible data":
                VisibleDataResponder(user);
                break;
            case "Last seen":
                LastSeenResponder(user);
                break;
            case "Change password":
                ChangePasswordResponder(user);
                break;
            case "Back":
                return;
            default:
                System.out.println(ConsoleColors.RED + "Invalid input!");
                break;
        }
        PrivacyResponder(user);
    }

    private static void ChangeVisibility(User user , String mode , String status)
    {
        logger.info("this user : " + user.getID() + " wants to change its visibilities");


        String respond;
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        System.out.print(ConsoleColors.BLUE + "Your " + mode + "is currently " + status);
        System.out.println();

        System.out.println("Choose between these options: ");
        System.out.println(ConsoleColors.CYAN + """
                Change
                Back
                """);
        respond = scanner.nextLine();

        if(respond.equals("Change"))
        {
            switch (mode) {
                case "account" -> user.ChangePrivate();
                case "E-mail address" -> user.ChangeVisibility(user, 0);
                case "phone number" -> user.ChangeVisibility(user, 1);
                case "date of birth" -> user.ChangeVisibility(user, 2);
            }
        }


        else if(respond.equals("Back"))
            return;

        else
            System.out.println(ConsoleColors.RED + "Invalid input!");

        ChangeVisibility(user , mode , status);
    }

    private static void VisibleDataResponder(User user)
    {
        logger.info("this user : " + user.getID() + " wants to view its visibilities");

        String respond;
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        System.out.println(ConsoleColors.BLUE + "Choose between these options: ");
        System.out.println(ConsoleColors.CYAN + """
                E-mail visibility\s
                Phone number visibility\s
                Date of birth visibility\s
                Back
                """);

        respond = scanner.nextLine();

        switch (respond) {
            case "E-mail visibility":
                ChangeVisibility(user, "E-mail address", (user.isVisibleEmail() ? "Visible" : "Invisible"));
                break;

            case "Phone number visibility":
                ChangeVisibility(user, "phone number", (user.isVisiblePhone() ? "Visible" : "Invisible"));
                break;

            case "Date of birth visibility":
                ChangeVisibility(user, "date of birth", (user.isVisibleDate() ? "Visible" : "Invisible"));
                break;

            case "Back":
                return;

            default:
                System.out.println(ConsoleColors.RED + "Invalid input!");
                break;
        }
        VisibleDataResponder(user);
    }

    private static void LastSeenResponder(User user)
    {
        logger.info("this user : " + user.getID() + " wants to change its last seen mode");

        String respond;
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        System.out.println(ConsoleColors.BLUE + "Your last seen is currently visible to " );

        if(user.getLastSeenMode() == 0)
            System.out.println("nobody.");

        if(user.getLastSeenMode() == 1)
            System.out.println("everybody.");

        if(user.getLastSeenMode() == 2)
            System.out.println("your followers");

        System.out.println("Do you tend to change it?(Yes/No)");
        respond = scanner.nextLine();

        if(respond.equals("Yes")){
            System.out.println(ConsoleColors.BLUE + "Change to (Choose between options bellow): ");

            if(user.getLastSeenMode() != 0)
                System.out.println(ConsoleColors.CYAN + "Nobody");

            else if(user.getLastSeenMode() != 1)
                System.out.println(ConsoleColors.CYAN + "Everybody");

            else if(user.getLastSeenMode() != 2)
                System.out.println(ConsoleColors.CYAN + "Followers");

            System.out.println("Back");

            respond = scanner.nextLine();

            switch (respond) {
                case "Nobody":
                    user.setLastSeenMode(0);
                    break;

                case "Everybody":
                    user.setLastSeenMode(1);
                    break;

                case "Followers":
                    user.setLastSeenMode(2);
                    break;

                case "Back":
                    return;

                default:
                    System.out.println(ConsoleColors.RED + "Invalid input!");
                    break;
            }

            LastSeenResponder(user);
        }

        else if(respond.equals("No"))
            return;

        else
            System.out.println(ConsoleColors.RED + "Invalid input!");

        LastSeenResponder(user);
    }

    private static void AccountsStatusResponder(User user)
    {
        logger.info("this user : " + user.getID() + " wants to change its account status");

        String respond;
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        System.out.println(ConsoleColors.BLUE + "Your account is currently active. Do you tend to deactivate it?(Yes/No)");
        System.out.println(ConsoleColors.BLACK + "After deactivation, your account won't be visible in other users Followings/Followers list; " +
                "\nbut as soon as you sign in again, your account will be activated again.");
        respond = scanner.nextLine();

        if(respond.equals("Yes")) {
            user.Deactivation();
            CLI.LogOutResponder();
        }

        else if(respond.equals("No"))
            return;

        else
            System.out.println(ConsoleColors.RED + "Invalid input!");

        AccountsStatusResponder(user);
    }

    private static void LogOutResponder()
    {
        logger.info("this user wants to logout");

        System.out.println(ConsoleColors.YELLOW + "Hope to see you soon =).");
        System.out.println(ConsoleColors.BLACK + "===================================================================================================================");

        CLI.LoginResponder();
    }

    private static void DeleteAccountResponder(User user)
    {
        logger.info("this user : " + user.getID() + " wants to delete its account");

        String respond;
        System.out.println(ConsoleColors.BLACK + "-------------------------------------------------------------------------------------------------------------------");
        System.out.println();

        System.out.println(ConsoleColors.YELLOW + "Nice, glad to see you leaving. Before leaving, enter your password:");
        respond = scanner.nextLine();

        if(!respond.equals(user.getPassword()))
        {
            System.out.println(ConsoleColors.RED + "Wrong password :|");
            System.out.println(ConsoleColors.CYAN + "Back" + "\nTry again");
            respond = scanner.nextLine();

            if(respond.equals("Back"))
                return;

            else if(!respond.equals("Try again"))
                System.out.println(ConsoleColors.RED + "Invalid input!");

            else
                DeleteAccountResponder(user);
        }

        System.out.println("Bye.");
        user.DeleteAccount();
        CLI.LoginResponder();
    }
}
