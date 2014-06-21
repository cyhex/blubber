package com.b14h.controllers;

import com.b14h.model.Task;
import com.b14h.services.DbService;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TaskServlet extends HttpServlet {

    private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    /**
     * Get Task list
     *
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {

        List<Task> tasks = DbService.ofy().load().type(Task.class).list();
        Gson json = new Gson();
        resp.setContentType("application/json; charset=UTF-8");
        resp.getWriter().write(json.toJson(tasks));
    }

    /**
     * Update task
     *
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long taskId = Long.parseLong(req.getParameter("taskId"));

        Task task = DbService.ofy().load().type(Task.class).id(taskId).now();

        task.setStatus(Task.TaskStatus.valueOf(req.getParameter("status")));
        DbService.ofy().save().entities(task).now();

        resp.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Append task
     *
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {

        Task task = new Task();
        task.setTitle(req.getParameter("title"));
        task.setDescription(req.getParameter("description"));
        task.setCredit(Integer.valueOf(req.getParameter("credit")));
        task.setStatus(Task.TaskStatus.OPEN);
        DbService.ofy().save().entities(task).now();
    }

    /**
     * Delete task
     *
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        DbService.ofy().delete().type(Task.class).id(
                Long.parseLong(req.getParameter("taskId"))
        ).now();

        resp.setStatus(HttpServletResponse.SC_OK);
    }


}
