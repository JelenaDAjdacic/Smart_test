package com.example.jelena.smart_test.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tasks {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("TargetDate")
    @Expose
    private String TargetDate;
    @SerializedName("DueDate")
    @Expose
    private String DueDate;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("Priority")
    @Expose
    private Integer Priority;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The TargetDate
     */
    public String getTargetDate() {
        return TargetDate;
    }

    /**
     * @param TargetDate The TargetDate
     */
    public void setTargetDate(String TargetDate) {
        this.TargetDate = TargetDate;
    }

    /**
     * @return The DueDate
     */
    public String getDueDate() {
        return DueDate;
    }

    /**
     * @param DueDate The DueDate
     */
    public void setDueDate(String DueDate) {
        this.DueDate = DueDate;
    }

    /**
     * @return The Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * @param Description The Description
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     * @return The Priority
     */
    public Integer getPriority() {
        return Priority;
    }

    /**
     * @param Priority The Priority
     */
    public void setPriority(Integer Priority) {
        this.Priority = Priority;
    }



}
