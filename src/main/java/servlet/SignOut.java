package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Sign out from system
 */
public class SignOut extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        session.invalidate();
        resp.sendRedirect("/Journalist.ru");
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        session.invalidate();
        resp.sendRedirect("/Journalist.ru");
    }
}
