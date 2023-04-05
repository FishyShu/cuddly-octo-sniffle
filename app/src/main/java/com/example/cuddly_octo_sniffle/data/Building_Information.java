package com.example.cuddly_octo_sniffle.data;

public class Building_Information {


    public int building_id;
    public String building_name;
    public String building_type;

    // Constructor
    public Building_Information(int building_id, String building_name,
                                String building_type) {
        this.building_id = building_id;
        this.building_name = building_name;
        this.building_type = building_type;
    }


    // Getters and Setters
    public int getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(int building_id) {
        this.building_id = building_id;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public String getBuilding_type() {
        return building_type;
    }

    public void setBuilding_type(String building_type) {
        this.building_type = building_type;
    }
}
