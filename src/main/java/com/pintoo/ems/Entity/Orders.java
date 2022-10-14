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
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="Orders")
@AllArgsConstructor
@NoArgsConstructor

@SQLDelete(sql = "UPDATE Orders SET deleted = true WHERE order_id=?")
@Where(clause = "deleted=false")
public class Orders {
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +

                '}';
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Order_Id")
    private long id;

    @OneToMany(fetch=FetchType.LAZY , mappedBy="order", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<CartProduct> cartProduct;      // no need to create an extra relationship ....one to many already setup ho chuka hai due  to cartId mentioned in cartProduct
//    table and useriD MENTIONED IN ORDERiD  , JO ek doosre se one to one mapped hai

    @ManyToOne(fetch=FetchType.EAGER , cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id")
    private User users;

    private boolean deleted = Boolean.FALSE;


    @CreationTimestamp
    private Date created_date;

    @UpdateTimestamp
    private Date modified_date;
}
