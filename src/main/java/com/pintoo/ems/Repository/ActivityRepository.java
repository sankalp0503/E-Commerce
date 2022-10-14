package com.pintoo.ems.Repository;

import com.pintoo.ems.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Address,Long> {
}
