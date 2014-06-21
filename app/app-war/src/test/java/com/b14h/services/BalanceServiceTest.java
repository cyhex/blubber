package com.b14h.services;

import com.b14h.model.Child;
import com.b14h.model.Parent;
import com.b14h.model.Store;
import com.b14h.model.Task;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BalanceServiceTest {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig());

    private Task task;

    @Before
    public void setUp() throws Exception {

        helper.setUp();
        task = new Task();
        task.setTitle("t");
        task.setStatus(Task.TaskStatus.OPEN);
        task.setCredit(1);
        task.setDescription("");
        DbService.ofy().save().entity(task).now();

    }

    @After
    public void tearDown() throws Exception {
        helper.tearDown();
    }

    @Test
    public void testSanity() throws Exception {
        Child child = Child.getInstance();
        Assert.assertEquals(0, child.getBlubs());
    }


    @Test
    public void testCloseTask() throws Exception {
        BalanceService.closeTask(task, Parent.getInstance());
        Assert.assertEquals(Task.TaskStatus.CLOSED, task.getStatus());
    }


    @Test
    public void testPayStoreNoFunds() throws Exception {
        Store store = Store.getInstance();
        Child child = Child.getInstance();
        boolean fail = false;
        try {
            BalanceService.payStore(store, child, 1000);
        } catch (Exception e) {
            fail = true;
        }
        Assert.assertEquals(true, fail);
    }

    @Test
    public void testPayStoreOkFunds() throws Exception {
        Store store = Store.getInstance();
        Child child = Child.getInstance();
        child.setBlubs(1);
        DbService.ofy().save().entity(child).now();
        BalanceService.payStore(store, child, 1);

        Assert.assertEquals(0, child.getBlubs());
    }
}