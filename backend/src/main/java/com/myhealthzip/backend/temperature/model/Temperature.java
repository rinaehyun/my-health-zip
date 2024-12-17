package com.myhealthzip.backend.temperature.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "temperatures")
public class Temperature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer temperatureId;

    @Column(name = "user_id", nullable = true)
    private Integer userId;

    @Column(name = "temperature", nullable = false)
    private Double temperature;

    @Column(name = "created_at", nullable = false)
    private Instant createdTime;

    // Constructor
    public Temperature() { /* constructor without any fields */ }

    public Temperature(Integer temperatureId, Integer userId, Double temperature, Instant createdTime) {
        this.temperatureId = temperatureId;
        this.userId = userId;
        this.temperature = temperature;
        this.createdTime = createdTime;
    }

    // Comparisons
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Temperature that = (Temperature) o;
        return Objects.equals(temperatureId, that.temperatureId) && Objects.equals(userId, that.userId) && Objects.equals(temperature, that.temperature) && Objects.equals(createdTime, that.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(temperatureId, userId, temperature, createdTime);
    }

    // Print
    @Override
    public String toString() {
        return "Temperature{" +
                "temperatureId=" + temperatureId +
                ", userId=" + userId +
                ", temperature=" + temperature +
                ", createdTime=" + createdTime +
                '}';
    }
}
