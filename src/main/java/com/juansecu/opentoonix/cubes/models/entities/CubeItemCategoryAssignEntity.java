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
    @Column(name = "Cube_item_category_assign", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer cubeItemCategoryAssign;
    @JoinColumn(
        columnDefinition = "INTEGER UNSIGNED",
        name = "Cube_item_id",
        nullable = false
    )
    @ManyToOne(cascade = CascadeType.ALL)
    private CubeItemEntity cubeItem;
    @JoinColumn(
        columnDefinition = "INTEGER UNSIGNED",
        name = "Cube_item_category_id",
        nullable = false
    )
    @ManyToOne(cascade = CascadeType.ALL)
    private CubeItemCategoryEntity cubeItemCategory;
    @Column(name = "Added_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date addedAt;
    @Column(name = "Updated_at", nullable = false)
    @UpdateTimestamp
    private Date updatedAt;
}
