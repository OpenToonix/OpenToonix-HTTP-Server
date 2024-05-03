package com.juansecu.opentoonix.cubes.models.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.juansecu.opentoonix.cubes.enums.ECubeItemType;

@Entity(name = "Cube_items")
@Getter
@Setter
public class CubeItemEntity {
    @Column(name = "Cube_item_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer cubeItemId;
    @Column(name = "Name", nullable = false, unique = true)
    private String name;
    @Column(name = "Type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ECubeItemType type;
    @JoinTable(
        inverseJoinColumns = @JoinColumn(name = "Cube_item_category_id"),
        joinColumns = @JoinColumn(name = "Cube_item_id"),
        name = "Cube_item_category_assigns"
    )
    @ManyToMany(cascade = CascadeType.ALL)
    private List<CubeItemCategoryEntity> categories;
    @Column(name = "Price", nullable = false)
    private int price;
    @JoinTable(
        inverseJoinColumns = @JoinColumn(name = "Cube_area_id"),
        joinColumns = @JoinColumn(name = "Cube_item_id"),
        name = "Cube_item_areas"
    )
    @ManyToMany(cascade = CascadeType.ALL)
    private List<CubeAreaEntity> areas;
    @Column(name = "Added_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date addedAt;
    @Column(name = "Updated_at", nullable = false)
    @UpdateTimestamp
    private Date updatedAt;
}
