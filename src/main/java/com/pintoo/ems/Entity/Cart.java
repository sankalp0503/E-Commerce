package com.pintoo.ems.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="Cart")
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE Cart SET deleted = true WHERE cart_id=?")
@Where(clause = "deleted=false")
public class Cart {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Cart_Id")
    private long id;

    @OneToOne(fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id")
    private User user;


    @OneToMany(fetch=FetchType.LAZY ,mappedBy="cart",cascade=CascadeType.ALL)
    @JsonIgnore
    private List<CartProduct> cartProduct;

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }


    private boolean deleted = Boolean.FALSE;
}
