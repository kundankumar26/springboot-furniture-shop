package com.example.furnitureshop.repository;

import java.util.Optional;

import com.example.furnitureshop.models.ERole;
import com.example.furnitureshop.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
