package com.dmitriev.andrey.devsecops.db.entities;

import java.time.LocalDateTime;

public class Scanning {

    private final Long id;
    private final String name;
    private final Status status;
    private final LocalDateTime scanTime;


    public Scanning(Long id, String name, Status status, LocalDateTime scanTime) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.scanTime = scanTime;
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

    public LocalDateTime getScanTime() {
        return scanTime;
    }
}
