package com.example.chirag.jdonparsing.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chirag on 01-May-17.
 */

public class UserDataPost {
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private String id;
    @SerializedName("job")
    private String job;
    @SerializedName("createdAt")
    private String created;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}