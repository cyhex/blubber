package com.b14h.controllers;

import com.b14h.model.Store;
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

public class NotificationTest {

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    private NotificationServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter response_writer;
    private Map<String, String> parameters;

    @Before
    public void setUp() throws Exception {
        helper.setUp();

        Store store = Store.getInstance();
        store.setNotification(true);
        DbService.ofy().save().entity(store).now();

        parameters = new HashMap<String, String>();
        parameters.put("role","STORE");
        servlet = new NotificationServlet();
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
    public void testGetNotification() throws Exception {
        servlet.doGet(request, response);
        String expected = "true";
        Assert.assertEquals(expected, response_writer.toString());
    }

    @Test
    public void testGetNoNotification() throws Exception {
        Store store = Store.getInstance();
        store.setNotification(false);
        DbService.ofy().save().entity(store).now();
        servlet.doGet(request, response);
        String expected = "false";
        Assert.assertEquals(expected, response_writer.toString());
    }

}