package com.b14h.services;

import com.b14h.model.Child;
import com.b14h.model.Parent;
import com.b14h.model.Store;

public class NotificationService {

    public static boolean getParent(Parent parent){
        boolean v = parent.isNotification();
        parent.setNotification(false);
        DbService.ofy().save().entity(parent).now();
        return v;
    }

    public static boolean getChild(Child child){
        boolean v = child.isNotification();
        child.setNotification(false);
        DbService.ofy().save().entity(child).now();
        return v;
    }

    public static boolean getStore(Store store){
        boolean v = store.isNotification();
        store.setNotification(false);
        DbService.ofy().save().entity(store).now();
        return v;
    }

}
