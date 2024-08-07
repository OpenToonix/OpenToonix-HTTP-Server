package com.juansecu.opentoonix.cubes.models.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "Cube_item_categories")
@Getter
@Setter
public class CubeItemCategoryEntity {
    @Column(name = "Cube_item_category_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer cubeItemCategoryId;
    @Column(
        length = 45,
        name = "Name",
        nullable = false,
        unique = true
    )
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
        mappedBy = "categories"
    )
    private List<CubeItemEntity> cubeItems;
}
