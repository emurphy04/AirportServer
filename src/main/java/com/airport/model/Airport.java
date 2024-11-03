package com.airport.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "airports")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int airport_id;

    private String airport_name;
    private String code;

    public Airport() {}

    public Airport(int airport_id, String airport_name, String code) {
        this.airport_id = airport_id;
        this.airport_name = airport_name;
        this.code = code;
    }

    public int getAirport_id() {
        return airport_id;
    }

    public void setAirport_id(int airport_id) {
        this.airport_id = airport_id;
    }

    public String getAirport_name() {
        return airport_name;
    }

    public void setAirport_name(String airport_name) {
        this.airport_name = airport_name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "airport_id=" + airport_id +
                ", airport_name='" + airport_name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}


