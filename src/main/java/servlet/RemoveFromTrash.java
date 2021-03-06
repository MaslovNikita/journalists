package servlet;

import dao.MessageDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Remove message which are marked how deleted
 */
public class RemoveFromTrash extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        String messageId = req.getParameter("message_id");
        int intMessageId;
        try {
            intMessageId = Integer.parseInt(messageId);
        } catch (NumberFormatException e){
            intMessageId = -1;
        }
        String isIncoming = req.getParameter("is_incoming");
        boolean boolIsIncoming;
        if (isIncoming.equals("true")){
            boolIsIncoming = true;
        } else {
            boolIsIncoming = false;
        }
        MessageDao messageDao = new MessageDao();
        messageDao.removeFromTrash(intMessageId,boolIsIncoming);

        String backUri = req.getParameter("backUri");
        resp.sendRedirect(backUri);
    }
}
