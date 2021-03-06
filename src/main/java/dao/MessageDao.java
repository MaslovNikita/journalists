package dao;

import connectionpool.ConnectionPool;
import connectionpool.ConnectionPoolException;
import model.Message;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Provides access to table in database
 */
public class MessageDao {

    private static final Logger log = LogManager.getRootLogger();
    private ConnectionPool pool = ConnectionPool.getPool();

    public MessageDao() {
    }

    /**
     * Sends message
     *
     * @param from
     * @param to
     * @param message
     * @param sendingTime
     */
    public void sendMessage(final int from, final int to, String message, String sendingTime) {
        Connection connection = null;
        Statement transaction_stmt = null;
        PreparedStatement incoming_stmt = null;
        PreparedStatement outgoing_stmt = null;
        try {
            connection = pool.takeConnection();
            transaction_stmt = connection.createStatement();
            transaction_stmt.executeUpdate("START TRANSACTION;");
            String query = "INSERT INTO incoming_message (receiver_id,sender_id,message,sending_time) " +
                    "VALUES (?,?,?,?);";

            incoming_stmt = connection.prepareStatement(query);
            incoming_stmt.setInt(1, to);
            incoming_stmt.setInt(2, from);
            incoming_stmt.setString(3, message);
            incoming_stmt.setString(4, sendingTime);
            incoming_stmt.executeUpdate();

            query = "INSERT INTO outgoing_message (receiver_id,sender_id,message,sending_time,viewed) " +
                    "VALUES (?,?,?,?,?);";
            outgoing_stmt = connection.prepareStatement(query);
            outgoing_stmt.setInt(1, to);
            outgoing_stmt.setInt(2, from);
            outgoing_stmt.setString(3, message);
            outgoing_stmt.setString(4, sendingTime);
            outgoing_stmt.setBoolean(5, false);
            outgoing_stmt.executeUpdate();

            transaction_stmt.executeUpdate("COMMIT;");
        } catch (ConnectionPoolException | SQLException e) {
            log.log(Level.ERROR, "In MessageDao: " + e.getLocalizedMessage());
        } finally {
            pool.closeConnection(connection, transaction_stmt);
            try {
                incoming_stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                outgoing_stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets message
     *
     * @param id         identifier of user
     * @param deleted    gets deleted message or no
     * @param isIncoming gets incoming or outgoing
     * @return list of messages
     */
    public List<Message> getMessages(final int id, final boolean deleted, final boolean isIncoming, final int page) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Message> resultList = new ArrayList<>();
        String distTable = isIncoming ? "incoming_message" : "outgoing_message";
        String fieldTable = isIncoming ? "receiver_id" : "sender_id";
        String limit = deleted ? "" : "LIMIT ?,11";
        int paramLimit = page < 0 ? 0 : page;
        try {
            connection = pool.takeConnection();
            String query = "SELECT * FROM " + distTable + " FORCE INDEX (PRIMARY) WHERE " + fieldTable + " = ? AND deleted = ? " +
                    "ORDER BY sending_time DESC " + limit + ";";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setBoolean(2, deleted);
            if (!deleted) {
                preparedStatement.setInt(3, paramLimit * 10);
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Date date = resultSet.getDate("sending_time");
                Time time = resultSet.getTime("sending_time");
                Long datetime = date.getTime() + time.getTime() + 10800000;
                date = new Date(datetime);
                resultList.add(new Message(
                        resultSet.getInt("receiver_id"),
                        resultSet.getInt("sender_id"),
                        resultSet.getString("message"),
                        date,
                        resultSet.getInt("id"),
                        resultSet.getBoolean("viewed"),
                        resultSet.getBoolean("deleted")
                ));
            }
        } catch (ConnectionPoolException | SQLException e) {
            log.log(Level.ERROR, "In MessageDao, getMessages: " + e.getLocalizedMessage());
            return resultList;
        } finally {
            pool.closeConnection(connection, preparedStatement, resultSet);
        }
        return resultList;
    }

    /**
     * @param receiver_id
     * @return count new incoming message
     */
    public int countNewIncomingMessage(final int receiver_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = pool.takeConnection();
            String query = "SELECT COUNT(*) FROM incoming_message WHERE receiver_id = ? AND viewed = 0 " +
                    "AND deleted = 0;";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, receiver_id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (ConnectionPoolException | SQLException e) {
            log.log(Level.ERROR, "In MessageDao, getMessages: " + e.getLocalizedMessage());
        } finally {
            pool.closeConnection(connection, preparedStatement, resultSet);
        }
        return 0;
    }

    /**
     * @param message_id
     * @param receiver_id
     * @return true if message is a incoming for user, false otherwise
     */
    public boolean isLetterIncoming(final String message_id, final int receiver_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int msg_id;
        try {
            msg_id = Integer.parseInt(message_id);
        } catch (NumberFormatException e) {
            msg_id = -1;
        }
        try {
            connection = pool.takeConnection();
            String query = "SELECT receiver_id FROM incoming_message WHERE id = ? AND deleted = 0;";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, msg_id);
            resultSet = preparedStatement.executeQuery();
            int result;
            if (resultSet.next()) {
                result = resultSet.getInt(1);
                return result == receiver_id;
            }
        } catch (ConnectionPoolException | SQLException e) {
            log.log(Level.ERROR, "In MessageDao, getMessages: " + e.getLocalizedMessage());
            return false;
        } finally {
            pool.closeConnection(connection, preparedStatement, resultSet);
        }
        return false;
    }

    /**
     * @param message_id
     * @param sender_id
     * @return true if message is a outgoing for user, false otherwise
     */
    public boolean isLetterOutgoing(final String message_id, final int sender_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int msg_id;
        try {
            msg_id = Integer.parseInt(message_id);
        } catch (NumberFormatException e) {
            msg_id = -1;
        }
        try {
            connection = pool.takeConnection();
            String query = "SELECT sender_id FROM outgoing_message WHERE id = ? AND deleted = 0;";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, msg_id);
            resultSet = preparedStatement.executeQuery();
            int result;
            if (resultSet.next()) {
                result = resultSet.getInt(1);
                return result == sender_id;
            }
        } catch (ConnectionPoolException | SQLException e) {
            log.log(Level.ERROR, "In MessageDao, getMessages: " + e.getLocalizedMessage());
            return false;
        } finally {
            pool.closeConnection(connection, preparedStatement, resultSet);
        }
        return false;
    }

    /**
     * View incoming message
     *
     * @param message_id
     */
    public void viewMessage(final int message_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = pool.takeConnection();
            String query = "UPDATE incoming_message SET viewed = 1 WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, message_id);
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            log.log(Level.ERROR, "In MessageDao, getMessages: " + e.getLocalizedMessage());
        } finally {
            pool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * Gets one message
     *
     * @param message_id message id
     * @param isIncoming if true gets from incoming otherwise outgoing
     * @return message
     */
    public Message getMessage(final String message_id, final boolean isIncoming) {
        int int_message_id;
        try {
            int_message_id = Integer.parseInt(message_id);
        } catch (NumberFormatException e) {
            int_message_id = -1;
        }
        return getMessage(int_message_id, isIncoming);
    }

    /**
     * Gets one message
     *
     * @param message_id message id
     * @param isIncoming if true gets from incoming otherwise outgoing
     * @return message
     */
    public Message getMessage(final int message_id, final boolean isIncoming) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Message readMessage = new Message();
        String destTable = isIncoming ? "incoming_message" : "outgoing_message";
        try {
            connection = pool.takeConnection();
            String query = "SELECT * FROM " + destTable + " WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, message_id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                readMessage.setId(resultSet.getInt("id"));
                readMessage.setMessage(resultSet.getString("message"));
                readMessage.setReceiverId(resultSet.getInt("receiver_id"));
                readMessage.setSenderId(resultSet.getInt("sender_id"));
                readMessage.setSendingTime(resultSet.getDate("sending_time"));
                readMessage.setViewed(resultSet.getBoolean("viewed"));
            }
            if (!readMessage.isViewed()) {
                viewMessage(message_id);
            }
            return readMessage;
        } catch (ConnectionPoolException | SQLException e) {
            log.log(Level.ERROR, "In MessageDao, getMessages: " + e.getLocalizedMessage());
        } finally {
            pool.closeConnection(connection, preparedStatement, resultSet);
        }
        return readMessage;
    }

    /**
     * Mark message how deleted
     *
     * @param message_id
     * @param isIncoming
     */
    public void delete(final int message_id, final boolean isIncoming) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String destTable = isIncoming ? "incoming_message" : "outgoing_message";
        try {
            connection = pool.takeConnection();
            String query = "UPDATE " + destTable + " SET deleted = 1 WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, message_id);
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            log.log(Level.ERROR, "In MessageDao, getMessages: " + e.getLocalizedMessage());
        } finally {
            pool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * Removes all incoming messages which are marked how deleted
     *
     * @param receiver_id
     */
    public void clearIncomingTrash(final int receiver_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = pool.takeConnection();
            String query = "DELETE FROM incoming_message WHERE deleted = 1 AND receiver_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, receiver_id);
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            log.log(Level.ERROR, "In MessageDao, getMessages: " + e.getLocalizedMessage());
        } finally {
            pool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * Removes all outgoing messages which marked how deleted
     *
     * @param sender_id
     */
    public void clearOutgoingTrash(final int sender_id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = pool.takeConnection();
            String query = "DELETE FROM outgoing_message WHERE deleted = 1 AND sender_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, sender_id);
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            log.log(Level.ERROR, "In MessageDao, getMessages: " + e.getLocalizedMessage());
        } finally {
            pool.closeConnection(connection, preparedStatement);
        }
    }

    /**
     * Removes message are marked how deleted with id equals messageId
     *
     * @param messageId
     * @param isIncoming
     */
    public void removeFromTrash(final int messageId, final boolean isIncoming) {
        String destTable = isIncoming ? "incoming_message" : "outgoing_message";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = pool.takeConnection();
            String query = "DELETE FROM " + destTable + " WHERE deleted = 1 AND id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, messageId);
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            log.log(Level.ERROR, "In MessageDao, getMessages: " + e.getLocalizedMessage());
        } finally {
            pool.closeConnection(connection, preparedStatement);
        }
    }

}
