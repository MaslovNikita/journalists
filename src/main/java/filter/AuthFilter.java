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
 * Checks user have been authorized or no
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

        String uri = req.getRequestURI();
        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("user");

        if (user != null && user.getId() != -1) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (uri.endsWith("login") ||
                    uri.endsWith("signup") ||
                    uri.endsWith("service/auth") ||
                    uri.endsWith("service/register") ||
                    uri.endsWith("service/change_lang") ||
                    uri.endsWith("/Journalist.ru") ||
                    uri.endsWith("/Journalist.ru/") ||
                    uri.endsWith("css/mainStyle.css") ||
                    uri.endsWith("css/register.css") ||
                    uri.endsWith("images/flags/Russia.png") ||
                    uri.endsWith("images/flags/England.png")
                    ) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                resp.sendRedirect("/Journalist.ru/");
            }
        }
    }

    @Override
    public void destroy() {

    }
}
