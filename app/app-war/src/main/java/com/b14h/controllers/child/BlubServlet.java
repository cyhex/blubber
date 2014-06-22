package com.b14h.controllers.child;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.b14h.model.Child;
import com.b14h.model.Task;
import com.b14h.model.Task.TaskStatus;
import com.b14h.services.DbService;
import com.google.gson.Gson;

public class BlubServlet extends HttpServlet {

	private static final long serialVersionUID = -362518367869950917L;

	/**
	 * Returns the 3 numbers of blubs as a json object.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		int ownedBlubs = Child.getInstance().getBlubs();

		int unconfirmedBlubs = 0;
		List<Task> closedTasks = DbService.ofy().load().type(Task.class)
				.filter("status ==", TaskStatus.CLOSED).list();
		for (Task closedTask : closedTasks) {
			unconfirmedBlubs += closedTask.getCredit();
		}

		int nonEarnedBlubs = 0;
		List<Task> openTasks = DbService.ofy().load().type(Task.class)
				.filter("status ==", TaskStatus.OPEN).list();
		for (Task openTask : openTasks) {
			nonEarnedBlubs += openTask.getCredit();
		}
		
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		resultMap.put("ownedBlubs", ownedBlubs);
		resultMap.put("unconfirmedBlubs", unconfirmedBlubs);
		resultMap.put("nonEarnedBlubs", nonEarnedBlubs);
		
		Gson json = new Gson();
		resp.setContentType("application/json; charset=UTF-8");
		resp.getWriter().write(json.toJson(resultMap));
	}
}
