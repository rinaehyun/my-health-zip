package com.myhealthzip.backend.bloodpressure.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "BloodPressures")
public class BloodPressure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bloodPressureId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "systolic")
    private Integer systolic;

    @Column(name = "diastolic")
    private Integer diastolic;

    @Column(name = "created_at", nullable = false)
    private Instant createdTime;

    // Constructor
    public BloodPressure() { /* constructor without any fields */ }

    // Comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BloodPressure that = (BloodPressure) o;
        return Objects.equals(bloodPressureId, that.bloodPressureId) && Objects.equals(userId, that.userId) && Objects.equals(systolic, that.systolic) && Objects.equals(diastolic, that.diastolic) && Objects.equals(createdTime, that.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bloodPressureId, userId, systolic, diastolic, createdTime);
    }

    // Print
    @Override
    public String toString() {
        return "BloodPressure{" +
                "bloodPressureId=" + bloodPressureId +
                ", userId=" + userId +
                ", systolic=" + systolic +
                ", diastolic=" + diastolic +
                ", createdTime=" + createdTime +
                '}';
    }
}
