package com.b14h.controllers.child;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.b14h.model.Child;
import com.b14h.model.Parent;
import com.b14h.model.Store;
import com.b14h.model.Task;
import com.b14h.model.Task.TaskStatus;
import com.b14h.services.DbService;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class TaskServletTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private TaskServlet servlet;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private StringWriter response_writer;
	private Map<String, String> parameters;

	private Task task;

	@Before
	public void setUp() throws Exception {
		helper.setUp();

		Parent parent = Parent.getInstance();
		parent.setNotification(true);
		DbService.ofy().save().entity(parent).now();

		Child child = Child.getInstance();
		child.setNotification(true);
		DbService.ofy().save().entity(child).now();

		Store store = Store.getInstance();
		store.setNotification(true);
		DbService.ofy().save().entity(store).now();

		this.task = new Task("a title", "a description", 50);
		DbService.ofy().save().entities(this.task).now();

		parameters = new HashMap<String, String>();
		parameters.put("role", "STORE");
		parameters.put("taskId", this.task.getTaskId().toString());
		servlet = new TaskServlet();
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		response_writer = new StringWriter();

		when(request.getParameter(anyString())).thenAnswer(
				new Answer<String>() {
					public String answer(InvocationOnMock invocation) {
						return parameters.get(invocation.getArguments()[0]);
					}
				});
		when(response.getWriter()).thenReturn(new PrintWriter(response_writer));
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	@Test
	public void testGetTaskList() throws Exception {
		servlet.doGet(request, response);
		String responseString = response_writer.toString();
		Assert.assertTrue("response is too short", responseString.length() >= 2);
	}

	@Test
	public void testPostTaskClose() throws Exception {
		servlet.doPost(request, response);

		Task storedTask = DbService.ofy().load().type(Task.class)
				.id(task.getTaskId()).now();

		Assert.assertTrue("status must be closed",
				storedTask.getStatus() == TaskStatus.CLOSED);
	}
}