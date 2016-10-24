package servlet;

import dao.MessageDao;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by homie on 20.10.16.
 */
@WebServlet("/sendMessage")
public class SendMessageServlet extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        int from = Integer.parseInt(req.getParameter("from-message"));
        int to = Integer.parseInt(req.getParameter("to-message"));
        String message = req.getParameter("message_text");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String datetimestamp = dateFormat.format(Calendar.getInstance().getTime());

        MessageDao messageDao = new MessageDao();
        messageDao.sendMessage(from, to, message, datetimestamp);

        String backUri = req.getParameter("backURI");
        resp.sendRedirect(backUri);
    }
}
