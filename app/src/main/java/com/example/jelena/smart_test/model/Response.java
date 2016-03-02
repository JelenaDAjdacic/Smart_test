package com.example.jelena.smart_test.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {


    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("tasks ")
    @Expose
    private List<Tasks> tasks = new ArrayList<Tasks>();

    /**
     *
     * @return
     * The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The tasks
     */
    public List<Tasks> getTasks() {
        return tasks;
    }

    /**
     *
     * @param tasks
     * The tasks
     */
    public void setTasks(List<Tasks> tasks) {
        this.tasks = tasks;
    }

}