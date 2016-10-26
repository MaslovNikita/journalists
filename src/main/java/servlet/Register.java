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
 * Sing up users
 */
public class Register extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        boolean redirect_back = false;
        Date birthday_date = null;
        User newUser = null;
        try {
            String date1 = req.getParameter("date_birthday");
            String ref = DateReformat.forDataBase(date1);
            birthday_date = Date.valueOf(ref);
            session.removeAttribute("isDateIncorrect");
        } catch (IllegalArgumentException e){
            session.setAttribute("isDateIncorrect","true");
            session.removeAttribute("tmpDateBirthday");
            redirect_back = true;
        }

        UserDao userDao = new UserDao();
        if (!userDao.isLoginFree(req.getParameter("login"))){
            session.setAttribute("isLoginOccupied","true");
            redirect_back = true;
        } else {
            session.removeAttribute("isLoginOccupied");
        }

        if(redirect_back){
            session.setAttribute("tmpLogin",req.getParameter("login"));
            session.setAttribute("tmpName",req.getParameter("name"));
            session.setAttribute("tmpSurname",req.getParameter("surname"));
            session.setAttribute("tmpEmail",req.getParameter("email"));
            if (birthday_date != null){
                session.setAttribute("tmpDateBirthday",req.getParameter("date_birthday"));
            }
            session.setAttribute("tmpAboutSelf",req.getParameter("about_self"));
            resp.sendRedirect("/Journalist.ru/signup");
            return;
        } else {
            newUser = new User();
            newUser.setName(req.getParameter("name"));
            newUser.setSurname(req.getParameter("surname"));
            newUser.setEmail(req.getParameter("email"));
            newUser.setBirthdayDay(birthday_date);
            newUser.setAboutSelf(req.getParameter("about_self"));
            newUser.setGender((short) 3);
            newUser.setAvatar(false);
        }

        if (userDao.newUser(newUser,req.getParameter("login"),req.getParameter("password"))){
            resp.sendRedirect("/Journalist.ru/successfully");
        } else {
            resp.sendRedirect("/Journalist.ru/unsuccessfully");
        }
    }
}
