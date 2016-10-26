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
import java.util.ArrayList;
import java.util.List;

/**
 * Provides access to table in database
 */
public class UserDao {
    private static final Logger log = LogManager.getRootLogger();
    private ConnectionPool pool = ConnectionPool.getPool();

    public UserDao() {
    }

    /**
     * Checks occupied login or free
     * @param login
     * @return true if yes, false otherwise
     */
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

    /**
     * Checks correct password or no
     * @param login
     * @param password
     * @return true if yes, false otherwise
     */
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

    /**
     * Creates new user
     * @param user
     * @param login
     * @param password
     * @return true if create, false otherwise
     */
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

    /**
     * Validates password for login
     * @param login
     * @param password
     * @return user if login and password valid
     */
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

    /**
     *
     * @param id
     * @return user by Id
     */
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

    /**
     * Update user
     * @param user user with need param
     * @return true if updated, flase otherwise
     */
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

    /**
     * Searches users.
     * @param user user which contains search criterion
     * @return
     */
    public List<User> searchUsers(User user) {
        List<User> foundUsers = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        String query = "SELECT * FROM user_info WHERE TRUE ";
        if (!user.getName().equals("") && user.getName() != null) {
            query = query + "AND name LIKE '"+user.getName()+"%' ";
        }
        if (!user.getSurname().equals("") && user.getSurname() != null) {
            query = query + "AND surname LIKE '"+user.getSurname()+"%' ";
        }
        if (user.getGender() == 1 || user.getGender() == 2) {
            query = query + "AND gender = "+user.getGender()+" ";
        }
        query +=" ORDER BY surname ASC, name ASC;";
        try {
            con = pool.takeConnection();
            stmt = con.prepareStatement(query);
            resultSet = stmt.executeQuery();
            while (resultSet.next()){
                foundUsers.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getDate("date_birthday"),
                        resultSet.getShort("gender"),
                        resultSet.getString("about_self"),
                        resultSet.getString("telephone_number"),
                        resultSet.getString("email"),
                        resultSet.getBoolean("avatar")
                        )
                );
            }
        } catch (Exception e) {
            log.error("Something is wrong in UserDAO: " + e.getLocalizedMessage());
            return foundUsers;
        } finally {
            pool.closeConnection(con, stmt, resultSet);
        }
        return foundUsers;
    }
}
