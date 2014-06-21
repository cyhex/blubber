package com.b14h.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author aabarb
 */
@Entity(name = "child")
public class Child {
    private static Child inst;

    @Id
    private Long childId;
    private int blubs;
    private boolean notification = false;

    private Child() {
        this.blubs = 0;
    }

    public static Child getInstance() {
        if (inst == null) {
            inst = new Child();
            inst.setChildId((long) 1);
        }
        return inst;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public int getBlubs() {
        return this.blubs;
    }

    public void setBlubs(int blubs) {
        this.blubs = blubs;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    public void updateBlubs(int amount) {
        this.blubs += amount;
        if (this.blubs < 0) this.blubs = 0;
    }
}
