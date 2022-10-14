package com.pintoo.ems.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Table(name="Address")
@SQLDelete(sql = "UPDATE Address SET deleted = true WHERE address_id=?")
@Where(clause = "deleted=false")
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Address_Id")
    private long id;

    @Column(name="Address_Line")
    @NotEmpty
    @Size(min=4 , message = "Address Line should have at least 4 characters")
    private String addLine;

    @Column(name="City")
    @NotEmpty
    @Size(min=3 , message = "City name should have at least 3 characters")
    private String city;

    @Column(name="Postal_Code")
    @NotEmpty
    @Size(min=6,max=6 ,message = "Please enter a 6 digit postal code no")
    @Pattern(regexp="(^$|[0-9]{6})" , message = " Please enter a proper postal code no")
    private String postalCode;

    @Column(name="Country")
    @NotEmpty
    @Size(min=3 , message = "Country name should have at least 3 characters")
    private  String country;

    @Column(name="Default_Address")
    private boolean defaultAddress= Boolean.FALSE;

    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id")
    private User user;

    private boolean deleted = Boolean.FALSE;


    @CreationTimestamp
    private Date created_date;

    @UpdateTimestamp
    private Date modified_date;


// POST -> user -> 1,  /v2/api/user/{userId}/addAddress

    // check id 1 is present in suer table or not
    // if present Address -> address request map to address entity
    // save address entity
}
