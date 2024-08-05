package com.juansecu.opentoonix.cubes.models.entities;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "Cube_item_category_assigns")
@Getter
@Setter
public class CubeItemCategoryAssignEntity {
    @Column(name = "Cube_item_category_assign_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer cubeItemCategoryAssign;
    @Column(name = "Cube_item_id", nullable = false)
    private int cubeItemId;
    @Column(name = "Cube_item_category_id", nullable = false)
    private int cubeItemCategoryId;
    @Column(name = "Assigned_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date assignedAt;
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
        name = "Cube_item_category_id",
        referencedColumnName = "Cube_item_category_id",
        updatable = false
    )
    @ManyToOne(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    private CubeItemCategoryEntity cubeItemCategory;
}
