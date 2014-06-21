package com.b14h.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author aabarb
 */
@Entity(name = "store")
public class Store {
    private static Store inst;

    @Id
    private Long storeId;

    private boolean notification = false;

    public static Store getInstance() {
        if (inst == null) {
            inst = new Store();
            inst.setStoreId((long) 1);
        }
        return inst;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
}
