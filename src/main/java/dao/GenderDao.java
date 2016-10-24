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

/**
 * Created by homie on 10.10.16.
 */
public class GenderDao {
    private static final Logger log = LogManager.getRootLogger();
    private ConnectionPool pool = ConnectionPool.getPool();

    public GenderDao() {
    }

    public String getNameById(final int id){
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = pool.takeConnection();
            String query = "SELECT type_name FROM gender_type WHERE id = ?;";
            stmt = connection.prepareStatement(query);
            stmt.setInt(1,id);
            rs = stmt.executeQuery();
            if (rs.next()){
                return rs.getString("type_name");
            }
            return null;
        } catch (ConnectionPoolException | SQLException e) {
            log.log(Level.ERROR,"Error in GenderDao: "+e.getLocalizedMessage());
            return null;
        } finally {
            pool.closeConnection(connection,stmt,rs);
        }
    }

    public int getIdByName(final String typeName){
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = pool.takeConnection();
            String query = "SELECT id FROM gender_type WHERE type_name = ?;";
            stmt = connection.prepareStatement(query);
            stmt.setString(1,typeName);
            rs = stmt.executeQuery();
            if (rs.next()){
                return rs.getInt("id");
            }
            return -1;
        } catch (ConnectionPoolException | SQLException e) {
            log.log(Level.ERROR,"Error in GenderDao: "+e.getLocalizedMessage());
            return -1;
        } finally {
            pool.closeConnection(connection,stmt,rs);
        }
    }
}
