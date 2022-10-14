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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Products")
@SQLDelete(sql = "UPDATE Products SET deleted = true WHERE product_id=?")
@Where(clause = "deleted=false")
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Product_Id")
    private long id;

    @Column(name="Product_Name",nullable=false)
    @NotEmpty
    @Size(min=3, message = "Product name should have at least 3 characters")
    private String productName;

    @Column(name="Description")
    @Size( min=5 , message = "Description should not exceed 35 characters")
    private  String description;

    @Column(name="Brand")
    @NotEmpty
    @Size(min=3 , message = "Brand name should have at least 3 characters")
    private String brand;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", category=" + category +
                '}';
    }

    @Column(name="Quantity",nullable=false)
    @Min(value = 1 , message = "min quantity cannot be less than or equals to  zero")
    private int  quantity;

    @Column(name="Price",nullable=false)
    @Min(value = 1 , message = "minimum price cannot be less than or equals to zero")
    private double price;


    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name="category_id")
    private Category category;

    @OneToMany(fetch=FetchType.LAZY ,mappedBy="product",cascade=CascadeType.ALL)
    @JsonIgnore
    private List<CartProduct> cartProduct;

    private boolean deleted = Boolean.FALSE;

    @CreationTimestamp
    private Date created_date;

    @UpdateTimestamp
    private Date modified_date;
}
