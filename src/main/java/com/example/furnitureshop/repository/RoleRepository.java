package com.example.furnitureshop.repository;

import java.util.List;
import java.util.Optional;

import com.example.furnitureshop.models.ERole;
import com.example.furnitureshop.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

    @Query(value = "SELECT * FROM roles", nativeQuery = true)
    List<Role> isEmptyTable();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO roles (id, name) VALUES ('1', 'ROLE_EMPLOYEE'), ('2', 'ROLE_VENDOR'), ('3', 'ROLE_ADMIN')", nativeQuery = true)
    void createRoles();
}
