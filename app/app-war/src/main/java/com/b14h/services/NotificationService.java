package com.b14h.services;

import com.b14h.model.Child;
import com.b14h.model.Parent;
import com.b14h.model.Store;

public class NotificationService {

    public static boolean getParent(Parent parent){
        parent.setNotification(false);
        DbService.ofy().save().entity(parent).now();
        boolean v = parent.isNotification();
        return v;
    }

    public static boolean getChild(Child child){
        child.setNotification(false);
        DbService.ofy().save().entity(child).now();
        boolean v = child.isNotification();
        return v;
    }

    public static boolean getStore(Store store){
        store.setNotification(false);
        DbService.ofy().save().entity(store).now();
        boolean v = store.isNotification();
        return v;
    }

}
