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
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="Users")
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE Users SET deleted = true WHERE user_id=?")
@Where(clause = "deleted=false")
public class User {

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="User_Id")
    private long id;

    @Column(name="First_Name",nullable=false)
    @NotNull
    @Size(min=3 , message = "User First name should have at least 3 characters")
    private String firstName;

    @Column(name="Last_Name")
    private String lastName;

    @Column(name="Phone_No",nullable=false)
    @NotNull
    @Size(min=10,max=10 ,message = "Please enter a 10 digit phone no")
    @Pattern(regexp="(^$|[0-9]{10})" , message = " Please enter a proper phone no")
    private String phoneNo;


    @Column(name="Email",unique = true,nullable=false)
    @NotEmpty
    @Email(message = "Email address is not valid !!")
    private String email;

    @OneToMany(fetch=FetchType.LAZY , mappedBy="user", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Address> address;

//    @OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.MERGE)
//    @JoinColumn(name="cart_id")
//    @JsonIgnore                         // as we dont want it to reflect into user side(show user only in cart side , dont show cart in user side)
//    private Cart cart;

    @OneToMany(fetch=FetchType.LAZY , mappedBy="users", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Orders> order;

//    @OneToMany(fetch=FetchType.LAZY , mappedBy="use", cascade = CascadeType.MERGE)
//    @JsonIgnore
//    private List<Activity> activity;

    private boolean deleted = Boolean.FALSE;

    @CreationTimestamp
    private Date created;

    @UpdateTimestamp
    private Date modified;
}
