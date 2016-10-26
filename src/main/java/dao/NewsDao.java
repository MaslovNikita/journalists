package dao;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import connectionpool.ConnectionPool;
import connectionpool.ConnectionPoolException;
import model.FeedChannel;
import model.ItemsFeedChannel;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Provides access to table in database
 */
public class NewsDao {

    private static final Logger log = LogManager.getRootLogger();
    private ConnectionPool pool = ConnectionPool.getPool();

    /**
     * Gets list of feed channel for user with certain id
     * @param id
     * @return list of feed channel
     */
    public List<FeedChannel> getFeedsListOfUsers(final int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<FeedChannel> result = new ArrayList<>();
        try {
            connection = pool.takeConnection();
            String query = "SELECT feeds_url FROM news WHERE owner_id = ?;";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            SyndFeed feed;
            while (resultSet.next()) {
                FeedChannel feedChannel = new FeedChannel();
                SyndFeedInput input = new SyndFeedInput();
                try {
                    feed = input.build(new XmlReader(resultSet.getURL("feeds_url")));
                    feedChannel.setTitle(feed.getTitle());
                    feedChannel.setDescription(feed.getDescription());
                    feedChannel.setLink(feed.getLink());
                    feedChannel.setFeedsUrl(resultSet.getString("feeds_url"));
                    result.add(feedChannel);
                } catch (FeedException | IOException | SQLException e) {
                    log.log(Level.ERROR, "Error in NewsDao: " + e.getLocalizedMessage());
                }
            }
        } catch (ConnectionPoolException | SQLException e) {
            log.log(Level.ERROR, "Error in NewsDao: " + e.getLocalizedMessage());
        } finally {
            pool.closeConnection(connection, statement, resultSet);
        }
        return result;
    }

    /**
     * Gets feed channel by url
     * @param feedsUrl
     * @return feed channel
     */
    public FeedChannel getChannelByUrl(final String feedsUrl) {
        FeedChannel feedChannel = new FeedChannel();
        try {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(new URL(feedsUrl)));
            feedChannel.setTitle(feed.getTitle());
            feedChannel.setDescription(feed.getDescription());
            feedChannel.setLink(feed.getLink());
            feedChannel.setFeedsUrl(feedsUrl);
        } catch (FeedException | IOException e1) {
            e1.printStackTrace();
            return feedChannel;
        }
        return feedChannel;
    }

    /**
     * Gets list of items of feed by url of feed channel
     * @param feeds_url
     * @return list of items
     */
    public List<ItemsFeedChannel> getItemsOfFeed(final String feeds_url) {
        List<ItemsFeedChannel> result = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm");
        try {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(new URL(feeds_url)));
            List<SyndEntry> items = feed.getEntries();
            while (!items.isEmpty()) {
                SyndEntry entry = items.remove(0);
                ItemsFeedChannel item = new ItemsFeedChannel();
                item.setLink(entry.getLink());
                item.setTitle(entry.getTitle());
                Date datetime = entry.getPublishedDate();
                if (datetime != null) {
                    item.setDatetime(simpleDateFormat.format(datetime));
                }
                result.add(item);
            }
        } catch (FeedException | IOException e) {
            log.log(Level.ERROR, "Error in NewsDao: " + e.getLocalizedMessage());
            return result;
        }
        return result;
    }

    /**
     * Unsubscribe from news feed
     * @param ownerId
     * @param url
     */
    public void unsubscribe(final int ownerId, final String url) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.takeConnection();
            String query = "DELETE FROM news WHERE owner_id = ? AND feeds_url = ?;";
            statement = connection.prepareStatement(query);
            statement.setInt(1, ownerId);
            statement.setString(2, url);
            statement.executeUpdate();

        } catch (SQLException | ConnectionPoolException e) {
            log.log(Level.ERROR, "Error in NewsDao: " + e.getLocalizedMessage());
        } finally {
            pool.closeConnection(connection, statement);
        }
    }

    /**
     * Subscribe on news feed
     * @param ownerId
     * @param url
     */
    public void subscribe(final int ownerId, final String url) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = pool.takeConnection();
            String query = "INSERT INTO news (owner_id,feeds_url) VALUES (?,?);";
            statement = connection.prepareStatement(query);
            statement.setInt(1, ownerId);
            statement.setString(2, url);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            log.log(Level.ERROR, "Error in NewsDao: " + e.getLocalizedMessage());
        } finally {
            pool.closeConnection(connection, statement);
        }
    }

    /**
     * Checks subscribe on news feed
     * @param ownerId
     * @param url
     * @return
     */
    public boolean haveItNewsFeed(final int ownerId, final String url) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            String query = "SELECT COUNT(*) FROM news WHERE owner_id = ? AND feeds_url = ?;";
            statement = connection.prepareStatement(query);
            statement.setInt(1, ownerId);
            statement.setString(2, url);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
            return false;
        } catch (SQLException | ConnectionPoolException e) {
            log.log(Level.ERROR, "Error in NewsDao: " + e.getLocalizedMessage());
        } finally {
            pool.closeConnection(connection, statement, resultSet);
        }
        return false;
    }
}
