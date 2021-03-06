package servlet;

import dao.NewsDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Unsubscribes from news feed
 */
public class Unsubscribe extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        int ownerId;
        try {
            ownerId = Integer.parseInt(req.getParameter("owner_id"));
        } catch (NumberFormatException e){
            ownerId = -1;
        }
        String url = req.getParameter("url");
        NewsDao newsDao = new NewsDao();
        newsDao.unsubscribe(ownerId,url);
        resp.sendRedirect(req.getParameter("backUri"));
    }
}
