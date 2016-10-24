package dao;

import connectionpool.ConnectionPool;
import connectionpool.ConnectionPoolException;
import model.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by homie on 04.10.16.
 */
public class UserDao {
    private static final Logger log = LogManager.getRootLogger();
    private ConnectionPool pool = ConnectionPool.getPool();

    public UserDao() {
    }

    public boolean isLoginFree(final String login) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            String query = "SELECT COUNT(*) FROM user_info WHERE login = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) == 0;
            }
        } catch (ConnectionPoolException | SQLException e) {
            log.log(Level.ERROR, "Error in UserDao: " + e.getLocalizedMessage());
        } finally {
            pool.closeConnection(connection, statement, resultSet);
        }
        return false;
    }

    public boolean isPasswordCorrect(final String login, final String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            String query = "SELECT password FROM user_info WHERE login = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String realPassword = resultSet.getString("password");
                return realPassword.equals(password);
            }
        } catch (ConnectionPoolException | SQLException e) {
            log.log(Level.ERROR, "Error in UserDao: " + e.getLocalizedMessage());
        } finally {
            pool.closeConnection(connection, statement, resultSet);
        }
        return false;
    }

    public boolean newUser(final User user, final String login, final String password) {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            String query = "INSERT INTO user_info (name, surname, date_birthday, gender, " +
                    "about_self, telephone_number, email, avatar, login, password) " +
                    "values (?,?,?,?,?,?,?,?,?,?);";
            con = pool.takeConnection();
            stmt = con.prepareStatement(query);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getSurname());
            stmt.setDate(3, user.getBirthdayDay());
            stmt.setShort(4, user.getGender());
            stmt.setString(5, user.getAboutSelf());
            stmt.setString(6, user.getTelephoneNumber());
            stmt.setString(7, user.getEmail());
            stmt.setBoolean(8, user.isAvatar());
            stmt.setString(9, login);
            stmt.setString(10, password);

            int res = stmt.executeUpdate();
            return res > 0;
        } catch (Exception e) {
            log.error("Something is wrong in UserDAO: " + e.getLocalizedMessage());
            return false;
        } finally {
            pool.closeConnection(con, stmt);
        }
    }

    public User validateUser(final String login, final String password) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM user_info WHERE login = ? AND password = ?;";
            con = pool.takeConnection();
            stmt = con.prepareStatement(query);
            stmt.setString(1, login);
            stmt.setString(2, password);
            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                User gettingUser = new User();
                gettingUser.setId(resultSet.getInt("id"));
                gettingUser.setName(resultSet.getString("name"));
                gettingUser.setSurname(resultSet.getString("surname"));
                gettingUser.setAboutSelf(resultSet.getString("about_self"));
                gettingUser.setBirthdayDay(resultSet.getDate("date_birthday"));
                gettingUser.setEmail(resultSet.getString("email"));
                gettingUser.setTelephoneNumber(resultSet.getString("telephone_number"));
                gettingUser.setGender(resultSet.getShort("gender"));
                gettingUser.setAvatar(resultSet.getBoolean("avatar"));
                return gettingUser;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("Something is wrong in UserDAO: " + e.getLocalizedMessage());
            return null;
        } finally {
            pool.closeConnection(con, stmt, resultSet);
        }
    }

    public User getUserById(final int id) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM user_info WHERE id = ?;";
            con = pool.takeConnection();
            stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                User gettingUser = new User();
                gettingUser.setId(resultSet.getInt("id"));
                gettingUser.setName(resultSet.getString("name"));
                gettingUser.setSurname(resultSet.getString("surname"));
                gettingUser.setAboutSelf(resultSet.getString("about_self"));
                gettingUser.setBirthdayDay(resultSet.getDate("date_birthday"));
                gettingUser.setEmail(resultSet.getString("email"));
                gettingUser.setTelephoneNumber(resultSet.getString("telephone_number"));
                gettingUser.setGender(resultSet.getShort("gender"));
                gettingUser.setAvatar(resultSet.getBoolean("avatar"));
                return gettingUser;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("Something is wrong in UserDAO: " + e.getLocalizedMessage());
            return null;
        } finally {
            pool.closeConnection(con, stmt, resultSet);
        }
    }

    public boolean updateUser(User user) {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            String query = "UPDATE user_info SET name = ?, surname = ?, date_birthday = ?, " +
                    "gender = ?, about_self = ?, telephone_number = ?, email = ?, avatar = ? " +
                    "WHERE id = ?";
            con = pool.takeConnection();
            stmt = con.prepareStatement(query);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getSurname());
            stmt.setDate(3, user.getBirthdayDay());
            stmt.setShort(4, user.getGender());
            stmt.setString(5, user.getAboutSelf());
            stmt.setString(6, user.getTelephoneNumber());
            stmt.setString(7, user.getEmail());
            stmt.setBoolean(8, user.isAvatar());
            stmt.setInt(9, user.getId());
            int res = stmt.executeUpdate();
            return res > 0;
        } catch (Exception e) {
            log.error("Something is wrong in UserDAO: " + e.getLocalizedMessage());
            return false;
        } finally {
            pool.closeConnection(con, stmt);
        }
    }
}
