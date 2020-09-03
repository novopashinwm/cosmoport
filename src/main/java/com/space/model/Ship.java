package com.space.model;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "ship")
@DynamicInsert
@DynamicUpdate
@Proxy(lazy = false)
public class Ship implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 50)
    private String planet;
    @Enumerated(EnumType.STRING)
    private ShipType shipType;
    @DateTimeFormat(pattern="yyyy.MM.dd")
    private Date prodDate;
    private Boolean isUsed = false;
    private Double speed;
    private Integer crewSize;
    private Double rating;

    public Ship() {
    }

    public Ship(String name, String planet, ShipType shipType, Date prodDate, Boolean isUsed, Double speed, Integer crewSize) {
        this.name = name;
        this.planet = planet;
        this.shipType = shipType;
        this.prodDate = prodDate;
        this.isUsed = isUsed;
        this.speed = speed;
        this.crewSize = crewSize;
        this.rating = getRating();

    }

    public Ship(String name, String planet, ShipType shipType, Date prodDate, Double speed, Integer crewSize) {
        this.name = name;
        this.planet = planet;
        this.shipType = shipType;
        this.prodDate = prodDate;
        isUsed = false;
        this.speed = getSpeed();
        this.crewSize = crewSize;
        this.rating = getRating();
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlanet() {
        return planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public Double getSpeed() {
        return speed;

    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public Double getRating() {
        double k;
        Calendar cal = Calendar.getInstance();
        cal.setTime(prodDate);
        int year = cal.get(Calendar.YEAR);
        if(!isUsed){
            k = 1;
        } else k = 0.5;
        Double firstPart = 80*speed*k;
        Double secondPart = 3019 - year +1.0;

        return Math.round(firstPart/secondPart *100) /100.0;

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "{" + id  + name + planet  + shipType +  prodDate +  isUsed +  speed +  crewSize +  rating + "}";
    }

    @PreUpdate
    protected void onUpdate() {
        this.rating = getRating();
    }

    @PrePersist
    protected void onCreate() {
        this.rating = getRating();
    }
}
