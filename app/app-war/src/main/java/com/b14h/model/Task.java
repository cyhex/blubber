package com.b14h.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author aabarb
 */
@Entity
public class Task {
    @Id
    private Long taskId;
    private String title;
    private String description;
    private int credit;
    private TaskStatus status;

    public Task() {
    }

    public Task(String title, String description, int credit) {
        this.title = title;
        this.description = description;
        this.credit = credit;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public int getCredit() {
        return this.credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public TaskStatus getStatus() {
        return this.status;
    }

    public void setStatus(TaskStatus newStatus) {
        this.status = newStatus;
    }

    public enum TaskStatus {
        OPEN,
        CLOSED,
        CONFIRMED
    }

}
