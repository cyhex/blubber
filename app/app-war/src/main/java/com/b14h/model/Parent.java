package com.b14h.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author aabarb
 */
@Entity(name = "Parent")
public class Parent {
    private static Parent inst;

    @Id
    private Long parentId;
    private String preapprovalKey;
    private boolean notification = false;

    private Parent() {
    }

    public static Parent getInstance() {
        if (inst == null) {
            inst = new Parent();
            inst.setParentId((long) 1);
        }
        return inst;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public String getPreapprovalKey() {
        return this.preapprovalKey;
    }

    public void setPreapprovalKey(String key) {
        this.preapprovalKey = key;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

}
