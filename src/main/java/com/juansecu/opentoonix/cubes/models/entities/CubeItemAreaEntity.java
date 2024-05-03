package com.juansecu.opentoonix.cubes.models.entities;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "Cube_item_areas")
@Getter
@Setter
public class CubeItemAreaEntity {
    @Column(name = "Cube_item_area_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer cubeItemAreaId;
    @JoinColumn(
        columnDefinition = "INTEGER UNSIGNED",
        name = "Cube_item_id",
        nullable = false
    )
    @ManyToOne(cascade = CascadeType.ALL)
    private CubeItemEntity cubeItem;
    @JoinColumn(
        columnDefinition = "INTEGER UNSIGNED",
        name = "Cube_area_id",
        nullable = false
    )
    @ManyToOne(cascade = CascadeType.ALL)
    private CubeAreaEntity cubeArea;
    @Column(name = "Added_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date addedAt;
    @Column(name = "Updated_at", nullable = false)
    @UpdateTimestamp
    private Date updatedAt;
}
