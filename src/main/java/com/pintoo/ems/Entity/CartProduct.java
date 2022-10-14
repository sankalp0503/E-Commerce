package com.pintoo.ems.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import java.util.Date;

@Data
@Entity
@Table(name="CartProduct")
@AllArgsConstructor
@NoArgsConstructor

@SQLDelete(sql = "UPDATE Cart_Product SET deleted = true WHERE cart_product_id=?")
@Where(clause = "deleted=false")
//@FilterDef(name = "deletedProductFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
//@Filter(name = "deletedProductFilter", condition = "deleted = :isDeleted")
public class CartProduct {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="CartProduct_Id")
    private long id;

    @Override
    public String toString() {
        return "CartProduct{" +
                "id=" + id +
                ", reqQuantity=" + reqQuantity +
                ", price=" + price +
                '}';
    }

    @Column(name="Quantity",nullable=false)
    @Min(value = 1 ,message = "required quantity cannot be less than or equals zero ")
    private int reqQuantity;

    @Column(name="Price",nullable=false)
    @Min(value = 1 ,message = "required price cannot be less than or equals zero ")
    private double price;

    @ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name="cart_id")
    private Cart cart;

    @ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne(fetch=FetchType.EAGER , cascade = CascadeType.MERGE)
    @JoinColumn(name="order_id")
    private Orders order; //         no need to show in cartTable side order id , order table me userId se cartId se products list acess karen

    private boolean deleted = Boolean.FALSE;


    @CreationTimestamp
    private Date created_date;

    @UpdateTimestamp
    private Date modified_date;
}
