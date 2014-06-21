package com.b14h.services;

import com.b14h.model.Child;
import com.b14h.model.Parent;
import com.b14h.model.Store;
import com.b14h.model.Task;

public class BlanaceService {

    /**
     * Confirm task by Parent write credits to Child
     *
     * @param task
     * @param child
     */
    public static void confirmTask(Task task, Child child) {
        task.setStatus(Task.TaskStatus.CONFIRMED);
        child.updateBlubs(task.getCredit());
        child.setNotification(true);
        DbService.ofy().save().entity(task).now();
        DbService.ofy().save().entity(child).now();
    }

    /**
     * set task as done by Child, update Prent
     *
     * @param task
     * @param parent
     */
    public static void closeTask(Task task, Parent parent) {
        task.setStatus(Task.TaskStatus.CLOSED);
        parent.setNotification(true);
        DbService.ofy().save().entity(task).now();
        DbService.ofy().save().entity(parent).now();
    }

    public static void payStore(Store store, Child child, int blubsAmount) throws Exception {

        if(child.getBlubs() < blubsAmount){
            throw new Exception("No enough blanace");
        }

        child.updateBlubs(blubsAmount*-1);
        store.setNotification(true);

        DbService.ofy().save().entity(child).now();
        DbService.ofy().save().entity(store).now();
    }


}
