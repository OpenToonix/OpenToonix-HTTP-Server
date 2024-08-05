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
    @Column(name = "Cube_item_id", nullable = false)
    private int cubeItemId;
    @Column(name = "Cube_area_id", nullable = false)
    private int cubeAreaId;
    @Column(name = "Added_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date addedAt;
    @Column(name = "Updated_at", nullable = false)
    @UpdateTimestamp
    private Date updatedAt;
    @JoinColumn(
        insertable = false,
        name = "Cube_item_id",
        referencedColumnName = "Cube_item_id",
        updatable = false
    )
    @ManyToOne(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private CubeItemEntity cubeItem;
    @JoinColumn(
        insertable = false,
        name = "Cube_area_id",
        referencedColumnName = "Cube_area_id",
        updatable = false
    )
    @ManyToOne(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private CubeAreaEntity cubeArea;
}
