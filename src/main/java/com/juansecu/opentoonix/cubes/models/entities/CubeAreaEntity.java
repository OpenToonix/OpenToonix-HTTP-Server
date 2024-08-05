package com.juansecu.opentoonix.cubes.models.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "Cube_areas")
@Getter
@Setter
public class CubeAreaEntity {
    @Column(name = "Cube_area_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer cubeAreaId;
    @Column(length = 45, name = "Name", nullable = false, unique = true)
    private String name;
    @Column(name = "Added_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date addedAt;
    @Column(name = "Updated_at", nullable = false)
    @UpdateTimestamp
    private Date updatedAt;
    @ManyToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "cubeAreas"
    )
    private List<CubeItemEntity> cubeItems;
}
