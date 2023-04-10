package com.example.cuddly_octo_sniffle.data;

public class Room_Information {

    public String room_id;
    public String room_name;
    public String room_type;
    public String room_occupant_name;
    public String room_description;
    //public String room_capacity;


    // Constructor
    public Room_Information(String room_id, String room_name, String room_type,
                            String room_occupant_name, String room_description) {
        this.room_id = room_id;
        this.room_name = room_name;
        this.room_type = room_type;
        this.room_occupant_name = room_occupant_name;
        this.room_description = room_description;
    }

    // Getters and Setters
    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getRoom_occupant_name() {
        return room_occupant_name;
    }

    public void setRoom_occupant_name(String room_occupant_name) {
        this.room_occupant_name = room_occupant_name;
    }

    public String getRoom_description() {
        return room_description;
    }

    public void setRoom_description(String room_description) {
        this.room_description = room_description;
    }
}
