package com.b14h.controllers;

import com.b14h.model.Task;
import com.b14h.services.DbService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PopulateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        String title = "my Task title ";
        String desc = "Compiles JavaScript templates into functions that can be evaluated for rendering. Useful for "
                + "rendering complicated bits of HTML from JSON data sources. Template functions can both interpolate "
                + "variables, using <%= … %>, as well as execute arbitrary JavaScript code";

        for (int i = 0; i < 10; i++) {
            Task task = new Task(title, desc, 10 + 1);
            task.setStatus(Task.TaskStatus.OPEN);
            DbService.ofy().save().entities(task).now();
        }
        resp.getWriter().write("OK");
    }
}
