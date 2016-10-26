package servlet;

import dao.FriendsDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Add friend to friend list
 */
public class AddFriend extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        int userId;
        try {
            userId = Integer.parseInt(req.getParameter("user_id"));
        } catch (NumberFormatException e){
            userId = -1;
        }
        int friendId;
        try {
            friendId = Integer.parseInt(req.getParameter("friend_id"));
        } catch (NumberFormatException e){
            friendId = -1;
        }
        String backUri = req.getParameter("backUri");

        FriendsDao friendsDao = new FriendsDao();
        friendsDao.addFriend(userId,friendId);
        resp.sendRedirect(backUri);
    }

}
