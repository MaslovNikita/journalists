package filter;

import dao.UserDao;
import model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by homie on 18.10.16.
 */
public class UserOnPageFilter implements Filter {
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String requestUri = request.getRequestURI();

        Pattern pattern = Pattern.compile("^/Journalist.ru/id[0-9]+$");
        Matcher m = pattern.matcher(requestUri);
        if (m.matches()){
            String stringId = requestUri.substring(requestUri.lastIndexOf("id")+2);
            int idUserOnPage = Integer.parseInt(stringId);

            UserDao userDao = new UserDao();
            User userOnPage = userDao.getUserById(idUserOnPage);

            if (session != null){
                session.removeAttribute("userOnPage");
                session.setAttribute("userOnPage",userOnPage);
            }
            request.getRequestDispatcher("/").forward(servletRequest,servletResponse);
        }

        pattern = Pattern.compile("^/Journalist.ru/?$");
        m = pattern.matcher(requestUri);
        if (m.matches()){
            if (session != null){
                session.removeAttribute("userOnPage");
                session.setAttribute("userOnPage",session.getAttribute("user"));
            }
            request.getRequestDispatcher("/").forward(servletRequest,servletResponse);
        }

        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
