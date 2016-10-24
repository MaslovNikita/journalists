package servlet;

import dao.UserDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by homie on 04.10.16.
 */
public class Authorization extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        UserDao userDao = new UserDao();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (userDao.isPasswordCorrect(login, password)) {
            User user = userDao.validateUser(login,password);
            session.setAttribute("user",user);
            session.removeAttribute("isIncorrect");
            resp.sendRedirect("/Journalist.ru/");
            return;
        } else {
            session.setAttribute("isIncorrect","true");
            resp.sendRedirect("/Journalist.ru/login");
            return;
        }
    }
}
