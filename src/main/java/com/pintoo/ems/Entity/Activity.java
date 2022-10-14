package com.pintoo.ems.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="Activity")
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Activity_Id")
    private long id;

    @ManyToOne(fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id")
    private User use;

    @Column(name="Action")
    private String status;


    @CreationTimestamp
    private Date created;
}
