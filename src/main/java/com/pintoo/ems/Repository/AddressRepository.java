package com.pintoo.ems.Repository;

import com.pintoo.ems.Entity.Address;
import com.pintoo.ems.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Long> {



    Optional<List<Address>> findAddressByUserId(Long userId);


    @Query("select add from Address add where add.user = :user and add.defaultAddress=true ")
    Optional<List<Address>> findDefaultAddressByUserId(User user);

    Optional<Address> findById(Long addressId);

    @Transactional
    @Modifying
    @Query("Update Address add set add.defaultAddress =false   where add.id= :addressId and add.defaultAddress=true ")
    int updateRemove(long addressId);

    @Transactional
    @Modifying
    @Query("Update Address add set add.defaultAddress =true   where add.id= :addressId and add.defaultAddress=false ")
    int updateSet(long addressId);


    //@Query("select p from Address p where p.user =: userId" )
    Page<Address> findAddressesByUserId(Long userId, Pageable pageable);

    @Query("Select p from Address p where p.addLine like %?1%"
            + "Or p.city like %?1%"
            + "Or p.postalCode like %?1%"
            + "Or p.country like %?1%")
    List<Address>  findAll(String keyword);
}
