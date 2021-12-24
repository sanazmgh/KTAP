package tweet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import user.User;

import javax.xml.stream.events.DTD;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
import java.util.LinkedList;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class Tweet {
    static private final Logger logger = LogManager.getLogger(Tweet.class);
    /**
     * comments are counted as tweets
     */

    private String TweetID;
    private String UserID;
    private Date date;
    private String RetweetID;
    private Date RetweetDate;
    private String text;
    private boolean Commented;
    private LinkedList<String> Comments = new LinkedList<>(); //List of tweets ID
    private LinkedList<String> Likes = new LinkedList<>(); //List of Users ID
    private LinkedList<String> Retweets = new LinkedList<>(); //List of Tweets ID
    private transient static String LastTweetID = "0";
    private transient static LinkedList<Tweet> TweetsList = new LinkedList<>();
    private transient static final File gsonFolder = new File("Gson tweets");
    private transient static final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    public Tweet (String UserID, String text , Date date , String retweetID, Date retweetDate , boolean commented)
    {
        this.UserID = UserID;
        this.TweetID = Long.toString(Long.parseLong(LastTweetID) + 1);
        LastTweetID = Long.toString(Long.parseLong(LastTweetID) + 1);
        this.text = text;
        this.date = date;
        this.RetweetID = retweetID;
        this.RetweetDate = retweetDate;
        this.Commented = commented;
    }

    public String getTweetID() {
        return TweetID;
    }

    public String getUserID() {
        return this.UserID;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public LinkedList<String > getComments() {
        return Comments;
    }

    public void setComments(Tweet  comment) {
        logger.info("this tweet : " + comment.getTweetID() + " commented under this tweet : " + this.TweetID);
        this.getComments().add(comment.getTweetID());
        TweetsList.add(comment);

        Tweet.UpdateTweets(comment);
        if(User.FindUser(this.UserID) != null)
            User.UpdateUsers(User.FindUser(this.UserID));
    }

    public LinkedList<String> getLikes() {
        return Likes;
    }

    public void setLikes(String ID) {
        logger.info("this user : " + ID + " Liked this tweet : " + this.TweetID);
        this.Likes.add(ID);
        Tweet.UpdateTweets(this);
    }

    public LinkedList<String> getRetweets() {
        return Retweets;
    }

    public void setRetweets(String ID) {
        logger.info("this user : " + ID + " retweeted this tweet : " + this.TweetID);
        this.Retweets.add(ID);

        Tweet.UpdateTweets(this);
        if(User.FindUser(this.UserID) != null)
            User.UpdateUsers(User.FindUser(this.UserID));
    }

    public String getRetweetID() { return RetweetID; }

    public Date getRetweetDate() {
        return RetweetDate;
    }

    public boolean isCommented() {
        return Commented;
    }

    static public void LoadTweets()
    {
        try
        {
            for (File file : gsonFolder.listFiles())
            {
                FileReader fileReader = new FileReader(file.getCanonicalPath());
                Tweet tweet = gson.fromJson(fileReader, Tweet.class);
                TweetsList.add(tweet);
                logger.warn("This tweet : " + tweet.TweetID + " added to tweets list" );
                LastTweetID = Long.toString(Math.max(Long.parseLong(LastTweetID), Long.parseLong(tweet.getTweetID())));
                logger.warn("This tweet : " + tweet.TweetID + " loaded successfully.");
            }
        }

        catch (Exception e)
        {
            logger.error("Failed to load tweets from Gson file");
            System.err.println("failed to load tweets");
        }
    }

    static public void UpdateTweets(Tweet tweet)
    {
        try {
            File file = new File("./Gson tweets/" + tweet.getTweetID());
            FileWriter fileWriter = new FileWriter(file.getCanonicalPath());
            gson.toJson(tweet , fileWriter);
            fileWriter.flush();
            logger.warn("This tweet : " + tweet.TweetID + " updated.");
        }

        catch (Exception e)
        {
            logger.error("This tweet : " + tweet.TweetID + " failed to update.");
            System.err.println("failed to update tweets");
        }
    }

    public static Tweet FindTweet (String ID)
    {
        for(Tweet tweet : TweetsList)
            if(tweet.getTweetID().equals(ID))
                return tweet;

        logger.debug("Couldn't find any tweet with this ID : " + ID);
        return null;
    }

    public void Liked(String ID)
    {
        if (this.getLikes().contains(ID))
            return;

        this.setLikes(ID);
        User user = User.FindUser(ID);

        for(String followerID : user.getFollowers())
        {
            User follower = User.FindUser(followerID);
            follower.setTimelineTweets(this.getTweetID());
        }
    }

    public void Disliked(String ID)
    {
        if(this.getLikes().contains(ID))
        {
            logger.info("This user : " + ID + " disliked this tweet : " + this.TweetID);
            this.Likes.remove(ID);
            Tweet.UpdateTweets(this);
        }
    }

    public static Tweet NewTweet(String UserID , String text , Date date , String retweetID , Date retweetDate , boolean commented)
    {
        Tweet tweet = new Tweet(UserID , text , date , retweetID , retweetDate , commented);

        if(retweetID == null)
        {
            User user = User.FindUser(UserID);
            user.setTweets(tweet.TweetID);

            for(String followerID : user.getFollowers())
            {
                User follower = User.FindUser(followerID);
                follower.setTimelineTweets(tweet.getTweetID());
            }
        }

        TweetsList.add(tweet);
        logger.warn("This tweet : " + tweet.TweetID + " added to tweets list" );
        Tweet.UpdateTweets(tweet);

        return tweet;
    }

    public void Retweet(User user)
    {
        Date date = new Date();
        Tweet tweet = NewTweet(this.UserID, this.text , this.date , user.getID() , date , false);
        this.Retweets.add(tweet.getTweetID());

        user.setTweets(this.TweetID);
        for(String followerID : user.getFollowers())
        {
            User follower = User.FindUser(followerID);
            follower.setTimelineTweets(tweet.getTweetID());
        }
    }
}