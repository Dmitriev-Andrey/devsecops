package com.dmitriev.andrey.devsecops.db.entities;

public class Scanning {

    private final Long id;
    private final String name;
    private final Status status;


    public Scanning(Long id, String name, Status status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }
}
