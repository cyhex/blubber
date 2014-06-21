package com.b14h.controllers;

import com.b14h.services.UpcService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpcServlet extends HttpServlet {

    private final Gson json = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String upc = req.getParameter("upc");
        resp.getWriter().write(UpcService.search(upc));
    }


}
