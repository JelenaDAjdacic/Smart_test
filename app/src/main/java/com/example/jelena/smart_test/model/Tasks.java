package com.example.jelena.smart_test.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tasks {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("tasks")
    @Expose
    private List<Task> tasks = new ArrayList<Task>();

    /**
     * @return The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return The tasks
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * @param tasks The tasks
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}