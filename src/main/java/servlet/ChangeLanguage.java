package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.Locale;

/**
 * Change language of application
 */
public class ChangeLanguage extends HttpServlet {

    private static int count = 0;

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req,resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        String chosenLang = req.getParameter("btn");
        Cookie cookie;
        switch (chosenLang){
            case "ru_RU":
                cookie = new Cookie("lang","ru_RU");
                cookie.setPath("/Journalist.ru");
                resp.addCookie(cookie);
                break;
            case "en_GB":
                cookie = new Cookie("lang","en_GB");
                cookie.setPath("/Journalist.ru");
                resp.addCookie(cookie);
                break;
            default:
                cookie = new Cookie("lang","default");
                cookie.setPath("/Journalist.ru");
                resp.addCookie(cookie);
                break;
        }
        String backURI = req.getParameter("backURI");
        resp.sendRedirect(backURI);
    }
}
