package com.b14h.services;

import com.b14h.model.Child;
import com.b14h.model.Parent;
import com.b14h.model.Store;
import com.b14h.model.Task;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class DbService {
    static {
        ObjectifyService.register(Task.class);
        ObjectifyService.register(Parent.class);
        ObjectifyService.register(Child.class);
        ObjectifyService.register(Store.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }

}
