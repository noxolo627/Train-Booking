package io.swagger.repository;

import io.swagger.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    @Query(value = "SELECT MAX(admin_id) FROM Admin", nativeQuery = true)
    Integer findMaxAdminId();
}
