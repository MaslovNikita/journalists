package dao;

import connectionpool.ConnectionPool;
import connectionpool.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by homie on 24.10.16.
 */
public class FriendsDao {

    private static final Logger log = LogManager.getRootLogger();
    private ConnectionPool pool = ConnectionPool.getPool();

    public FriendsDao(){
    }

    public List<Integer> getFriends(final int user_id){
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Integer> result = new ArrayList<>();
        try {
            connection = pool.takeConnection();
            String query = "SELECT friend_id FROM friends WHERE user_id = ?;";
            stmt = connection.prepareStatement(query);
            stmt.setInt(1,user_id);
            rs = stmt.executeQuery();
            while (rs.next()){
                result.add(rs.getInt("friend_id"));
            }
            return result;
        } catch (ConnectionPoolException | SQLException e) {
            log.log(Level.ERROR,"Error in FriendDao: "+e.getLocalizedMessage());
            return result;
        } finally {
            pool.closeConnection(connection,stmt,rs);
        }
    }

    public boolean addFriend(final int user_id, final int friend_id){
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = pool.takeConnection();
            String query = "INSERT INTO friends (user_id, friend_id) VALUES (?,?);";
            stmt = connection.prepareStatement(query);
            stmt.setInt(1,user_id);
            stmt.setInt(2,friend_id);
            return stmt.executeUpdate() > 0;
        } catch (ConnectionPoolException | SQLException e) {
            log.log(Level.ERROR,"Error in FriendDao: "+e.getLocalizedMessage());
            return false;
        } finally {
            pool.closeConnection(connection,stmt);
        }
    }

    public boolean removeFriend(final int user_id, final int friend_id){
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = pool.takeConnection();
            String query = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
            stmt = connection.prepareStatement(query);
            stmt.setInt(1,user_id);
            stmt.setInt(2,friend_id);
            return stmt.executeUpdate() > 0;
        } catch (ConnectionPoolException | SQLException e) {
            log.log(Level.ERROR,"Error in FriendDao: "+e.getLocalizedMessage());
            return false;
        } finally {
            pool.closeConnection(connection,stmt);
        }
    }

    public boolean isFriend(final int user_id,final int friend_id){
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = pool.takeConnection();
            String query = "SELECT COUNT(*) FROM friends WHERE user_id = ? AND friend_id = ?;";
            stmt = connection.prepareStatement(query);
            stmt.setInt(1,user_id);
            stmt.setInt(2,friend_id);
            rs = stmt.executeQuery();
            if (rs.next()){
                return rs.getInt(1) > 0;
            }
        } catch (ConnectionPoolException | SQLException e) {
            log.log(Level.ERROR,"Error in FriendDao: "+e.getLocalizedMessage());
            return false;
        } finally {
            pool.closeConnection(connection,stmt,rs);
        }
        return false;
    }
}
