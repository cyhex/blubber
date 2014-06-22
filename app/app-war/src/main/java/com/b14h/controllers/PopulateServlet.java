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
        
        String title = "Clean your room";
        String desc = "Don't forget to pick up your dirty socks and throw away the garbage.";
        Task task = new Task(title, desc, 50);
        task.setStatus(Task.TaskStatus.OPEN);
        DbService.ofy().save().entities(task).now();
        
        title = "Write 10 pages for your Math research project";
        desc = "Decide on a subject and read some literature. You can ask Mom for advice";
        task = new Task(title, desc, 100);
        task.setStatus(Task.TaskStatus.OPEN);
        DbService.ofy().save().entities(task).now();
        
        title = "Visit Grandma";
        desc = "Bring her a small gift, maybe some flowers or chocolates.";
        task = new Task(title, desc, 70);
        task.setStatus(Task.TaskStatus.OPEN);
        DbService.ofy().save().entities(task).now();
        
        title = "Help your brother with his Physics homework";
        desc = "He needs some help understanding the theory. Don't just give him the solution!";
        task = new Task(title, desc, 30);
        task.setStatus(Task.TaskStatus.OPEN);
        DbService.ofy().save().entities(task).now();
        
        resp.getWriter().write("OK");
    }
}
