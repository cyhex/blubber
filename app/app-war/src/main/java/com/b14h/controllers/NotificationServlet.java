package com.b14h.controllers;

import static com.b14h.libs.Constants.ROLES;
import com.b14h.services.NotificationService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NotificationServlet extends HttpServlet {

    private final Gson json = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ROLES role = ROLES.valueOf(req.getParameter("role"));

        if (role == ROLES.STORE) {
            resp.getWriter().write(json.toJson(NotificationService.getStore()));
        }

        if (role == ROLES.PARENT) {
            resp.getWriter().write(json.toJson(NotificationService.getParent()));
        }

        if (role == ROLES.CHILD) {
            resp.getWriter().write(json.toJson(NotificationService.getChild()));
        }

    }


}
