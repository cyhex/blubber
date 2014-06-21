package com.b14h.controllers.child;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.b14h.model.Parent;
import com.b14h.model.Task;
import com.b14h.model.Task.TaskStatus;
import com.b14h.services.BalanceService;
import com.b14h.services.DbService;
import com.google.gson.Gson;

public class TaskServlet extends HttpServlet {

	private static final long serialVersionUID = -362518367869950917L;

	/**
	 * Get Task list
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		List<Task> tasks = DbService.ofy().load().type(Task.class)
				.filter("status ==", TaskStatus.OPEN).list();
		Gson json = new Gson();
		resp.setContentType("application/json; charset=UTF-8");
		resp.getWriter().write(json.toJson(tasks));
	}

	/**
	 * Update task
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Long taskId = Long.parseLong(req.getParameter("taskId"));

		Task task = DbService.ofy().load().type(Task.class).id(taskId).now();

		BalanceService.closeTask(task, Parent.getInstance());
		DbService.ofy().save().entities(task).now();
		resp.setStatus(HttpServletResponse.SC_OK);
	}
}
