package com.lenovo.smarttraffic.ui.activity;

import java.io.Serializable;

public class Parke implements Serializable {
    private Integer id;
    private String name;
    private Integer empty;
    private String address;
    private Integer distance;
    private Integer cost;
    private Integer latitude;
    private Integer longitude;

    public Parke(Integer id, String name, Integer empty, String address, Integer distance, Integer cost, Integer latitude, Integer longitude) {
        this.id = id;
        this.name = name;
        this.empty = empty;
        this.address = address;
        this.distance = distance;
        this.cost = cost;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEmpty() {
        return empty;
    }

    public void setEmpty(Integer empty) {
        this.empty = empty;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
