package servlet;

import dao.MessageDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by homie on 23.10.16.
 */
public class ReplyServlet extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        int from = Integer.parseInt(req.getParameter("from-message"));
        int to = Integer.parseInt(req.getParameter("to-message"));
        String message = req.getParameter("message-text");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String datetimestamp = dateFormat.format(Calendar.getInstance().getTime());

        MessageDao messageDao = new MessageDao();
        messageDao.sendMessage(from, to, message, datetimestamp);

        resp.sendRedirect("/Journalist.ru/inbox");
    }
}
