package filter;

import model.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by homie on 02.10.16.
 */
public class AuthFilter implements Filter {

    private ServletContext context;
    private static final Logger log = LogManager.getRootLogger();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

//        String uri = req.getRequestURI();
//        HttpSession session = req.getSession(true);
//
//        User user = (User) session.getAttribute("user");
//        if (user != null) {
//            if (uri.endsWith("login") || uri.endsWith("service/auth")) {
//                resp.sendRedirect("/Journalist.ru/index.jsp");
//            } else {
//                filterChain.doFilter(servletRequest, servletResponse);
//            }
//        } else {
//            if (uri.endsWith("login") || uri.endsWith("service/auth")) {
//                filterChain.doFilter(servletRequest,servletResponse);
//            } else {
//                resp.sendRedirect("/Journalist.ru/login");
//            }
//        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
