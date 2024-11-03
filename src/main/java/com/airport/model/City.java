package com.airport.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int city_id;
    private String name;
    private String province;
    private int population;

    @ManyToOne
    @JoinColumn(name = "airport_id", nullable = true)
    private Airport airport;

    public City(){

    }

    public City(int city_id, String name, String province, int population, Airport airport) {
        this.city_id = city_id;
        this.name = name;
        this.province = province;
        this.population = population;
        this.airport = airport;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    @Override
    public String toString() {
        return "City{" +
                "city_id=" + city_id +
                ", name='" + name + '\'' +
                ", province='" + province + '\'' +
                ", population=" + population +
                '}';
    }
}
