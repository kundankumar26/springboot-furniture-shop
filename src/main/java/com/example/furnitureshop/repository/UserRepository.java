package com.example.furnitureshop.repository;

import java.util.Optional;

import com.example.furnitureshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByEmpId(long empId);

    @Query(value = "SELECT * FROM users WHERE emp_id=:empId", nativeQuery = true)
    User findByEmpId(@Param(value = "empId") long empId);

    @Query(value = "SELECT emp_id FROM users WHERE username=:userName", nativeQuery = true)
    long findEmpIdByUsername(@Param(value = "userName") String username);

    @Query(value = "SELECT * FROM user_roles WHERE user_id=:userId and role_id=2", nativeQuery = true)
    Object findByUserId(@Param(value = "userId") int userId);
}
