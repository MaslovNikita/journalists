package servlet;

import dao.UserDao;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Search profile by criterion
 */
public class SearchProfile extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        int gender;
        try {
            gender = Integer.parseInt(req.getParameter("gender"));
        } catch (NumberFormatException e){
            gender = -1;
        }

        UserDao userDao = new UserDao();
        User searchType = new User();
        searchType.setName(name);
        searchType.setSurname(surname);
        searchType.setGender((short)gender);
        List<User> foundUsers = userDao.searchUsers(searchType);
        req.setAttribute("foundedUsers",foundUsers);
        req.setAttribute("searchName",name);
        req.setAttribute("searchSurname",surname);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/searchProfile");
        dispatcher.forward(req,resp);
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/searchProfile");
        dispatcher.forward(req,resp);
    }
}
