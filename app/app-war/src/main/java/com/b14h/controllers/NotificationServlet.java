package com.b14h.controllers;

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

        String role = req.getParameter("role");
        if(role == "store"){
            resp.getWriter().write(json.toJson(NotificationService.getStore()));
        }

    }


}
