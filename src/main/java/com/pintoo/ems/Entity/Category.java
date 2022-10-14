package com.pintoo.ems.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="Category")
@AllArgsConstructor
@NoArgsConstructor

@SQLDelete(sql = "UPDATE Category SET deleted = true WHERE category_id=?")
@Where(clause = "deleted=false")
public class Category {

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Category_Id")
    private long id;

    @Column(name="Category",nullable=false)
    @NotEmpty
    @Size(min=3 , message = "Category name should have at least 3 characters")
    private String categoryName;

    @Column(name="Description")
    private  String description;

    @OneToMany(fetch=FetchType.LAZY , mappedBy="category", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Product> product;

    private boolean deleted = Boolean.FALSE;


    @CreationTimestamp
    private Date created_date;

    @UpdateTimestamp
    private Date modified_date;
}
