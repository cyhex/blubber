package com.b14h.controllers.parent;

import com.b14h.model.Task;
import com.b14h.services.DbService;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskServletTest {

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    private TaskServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter response_writer;
    private Map<String, String> parameters;

    @Before
    public void setUp() throws Exception {
        helper.setUp();

        Task task = new Task();
        task.setTitle("t");
        task.setStatus(Task.TaskStatus.OPEN);
        task.setCredit(10);
        task.setDescription("asd");
        DbService.ofy().save().entity(task).now();

        parameters = new HashMap<String, String>();
        servlet = new TaskServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        response_writer = new StringWriter();

        when(request.getParameter(anyString())).thenAnswer(new Answer<String>() {
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
    public void testDoGet() throws Exception {
        servlet.doGet(request, response);
        String expected = "[{\"taskId\":1,\"title\":\"t\",\"description\":" +
                "\"asd\",\"credit\":10,\"status\":\"OPEN\"}]";

        Assert.assertEquals(expected, response_writer.toString());
    }

    @Test
    public void testDoPost() throws ServletException, IOException, EntityNotFoundException {
        parameters.put("taskId", "1");
        parameters.put("status", Task.TaskStatus.CONFIRMED.name());
        servlet.doPost(request, response);

        Task task = DbService.ofy().load().type(Task.class).id(1).now();
        Assert.assertEquals(task.getStatus(), Task.TaskStatus.CONFIRMED);
    }

    @Test
    public void testDoPut() throws ServletException, IOException, EntityNotFoundException {
        parameters.put("title", "t");
        parameters.put("description", "d");
        parameters.put("credit", "10");
        servlet.doPut(request, response);

        List<Task> tasks = DbService.ofy().load().type(Task.class).list();
        Assert.assertEquals(2, tasks.size());
    }

    @Test
    public void testDoDelete() throws ServletException, IOException, EntityNotFoundException {
        parameters.put("taskId", "1");
        servlet.doDelete(request, response);
        List<Task> tasks = DbService.ofy().load().type(Task.class).list();

        Assert.assertEquals(tasks.size(), 0);
    }
}