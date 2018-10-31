package com.ds.assignment1_2.servlet;

import com.ds.assignment1_2.html.HtmlPageBuilder;
import com.ds.assignment1_2.utils.Notification;
import com.ds.assignment1_2.bll.UserBLL;
import com.ds.assignment1_2.bll.UserBLLImplementation;
import com.ds.assignment1_2.dao.UserDAO;
import com.ds.assignment1_2.entity.User;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {

    private UserBLL userBLL;

    @Override
    public void init() throws ServletException {
        super.init();
        userBLL = new UserBLLImplementation(new UserDAO(new Configuration().configure().buildSessionFactory()));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Notification<User> notification = userBLL.login(request.getParameter("username"),
                request.getParameter("password"));
        if(notification.hasErrors()){
            request.getSession().setAttribute("admin", -1);
            response.sendRedirect("/");
            request.getSession().setAttribute("message", "Invalid login.");
        } else {
            User user = notification.getResult();
            if(user.getAdmin() == 1){
                request.getSession().setAttribute("admin", 1);
                response.sendRedirect("/admin");
            } else {
                request.getSession().setAttribute("admin", 0);
                response.sendRedirect("/user");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(HtmlPageBuilder.getLoginHtml(request));
    }
}
