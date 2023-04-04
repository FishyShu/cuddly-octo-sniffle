package com.example.cuddly_octo_sniffle;


//Class of Room, room has id, name, status
public class Room {
    private int id;
    private String name;
    private String status;

    public Room(int id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
