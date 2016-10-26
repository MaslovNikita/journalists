package servlet;

import dao.MessageDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Remove all messages which are marked how deleted
 */
public class ClearDeleted extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("user_id");
        int intUserId;
        try {
            intUserId = Integer.parseInt(userId);
        } catch (NumberFormatException e){
            intUserId = -1;
        }
        MessageDao messageDao = new MessageDao();
        messageDao.clearIncomingTrash(intUserId);
        messageDao.clearOutgoingTrash(intUserId);

        String backUri = req.getParameter("backUri");
        resp.sendRedirect(backUri);
    }
}
