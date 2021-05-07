package com.example.furnitureshop.repository;

import com.example.furnitureshop.models.Order;
import com.example.furnitureshop.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FurnituresRepository extends JpaRepository<Orders, Long> {


}
