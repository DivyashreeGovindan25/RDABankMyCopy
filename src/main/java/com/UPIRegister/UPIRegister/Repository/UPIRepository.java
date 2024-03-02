package com.UPIRegister.UPIRegister.Repository;

import com.UPIRegister.UPIRegister.Models.UPIDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UPIRepository extends JpaRepository<UPIDetails,Long> {
    @Query(value = "SELECT u FROM UPIDetails u WHERE u.upiId = :email")
    public UPIDetails getUPIWithTheProvidedUPIId(@Param("email") String email);
}
