package servlet;

import dao.UserDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by homie on 19.10.16.
 */
@WebServlet("/photoUploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)
public class PhotoUpload extends HttpServlet {

    private static final String SAVE_DIR = "content" + File.separator + "images" + File.separator + "users_avatars";


    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        String appPath = req.getServletContext().getRealPath("");
        String savePath = appPath + File.separator + SAVE_DIR;
        User user = null;
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }

        HttpSession session = req.getSession(false);
        if (session != null) {
            user = (User) session.getAttribute("user");

            Part part = req.getPart("file");
            part.write(savePath + File.separator + user.getId());

            user.setAvatar(true);
            UserDao userDao = new UserDao();
            if (userDao.updateUser(user)){
                session.removeAttribute("user");
                session.setAttribute("user",user);
            }
        }

        resp.sendRedirect("/Journalist.ru/");
    }

}
