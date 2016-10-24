package servlet;

import dao.UserDao;
import model.User;
import util.DateReformat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by homie on 19.10.16.
 */
public class EditProfile extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User updatingUser;
        if (session != null) {
            updatingUser = (User) session.getAttribute("user");
            try {
                String dateForBd = DateReformat.forDataBase(req.getParameter("date_birthday"));
                Date birthday = Date.valueOf(dateForBd);
                updatingUser.setBirthdayDay(birthday);
            } catch (IllegalArgumentException e) {
            }
            String name = req.getParameter("name").trim();
            String surname = req.getParameter("surname").trim();
            String email = req.getParameter("email").trim();
            String telephone = req.getParameter("telephone").trim();
            String gender = req.getParameter("gender").trim();
            String aboutSelf = req.getParameter("about_self");
            if (!name.equals("")) {
                updatingUser.setName(name);
            }
            if (!surname.equals("")) {
                updatingUser.setSurname(surname);
            }
            updatingUser.setEmail(email);
            updatingUser.setTelephoneNumber(telephone);
            updatingUser.setAboutSelf(aboutSelf);
            updatingUser.setGender((short) Integer.parseInt(gender));

            UserDao userDao = new UserDao();
            if (userDao.updateUser(updatingUser)){
                session.removeAttribute("user");
                session.setAttribute("user",updatingUser);
            }
        }
        resp.sendRedirect("/Journalist.ru/");
    }
}
