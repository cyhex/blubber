package com.b14h.services;

import com.b14h.model.Store;

public class NotificationService {

    public static boolean getParent(){
        return false;
    }

    public static boolean getChild(){
        return false;
    }

    public static boolean getStore(){
        return Store.getInstance().isNotification();
    }

}
