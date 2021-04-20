package com.example.furnitureshop.security.services;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.example.furnitureshop.GlobalClassForFunctions;
import com.example.furnitureshop.exceptions.ResourceNotFoundException;
import com.example.furnitureshop.models.Order;
import com.example.furnitureshop.models.User;
import com.example.furnitureshop.repository.FurnitureRepository;
import com.example.furnitureshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;


@Service
public class FurnitureService {

    @Autowired
    private FurnitureRepository furnitureRepository;

    @Autowired
    private UserRepository userRepository;

    //GET ALL THE ORDERS
    public List<Order> getAllOrders(){
        return furnitureRepository.findAll();
    }

    //GET ORDER BY EMP ID
    public List<Order> getOrderByEmpId(long empId){
        return furnitureRepository.findUserByEmpId(empId);

//		List<Order> empOrderList = new ArrayList<Order>();
//		furnitureRepository.findAll().stream()
//		.forEach((order) -> {
//			if(order.getEmpId() == empId) {
//				System.out.print(order.getOrderId() + " ");
//				empOrderList.add(order);
//			}
//		});
//		Collections.sort(empOrderList, new GlobalClassForFunctions.SortByDate());
//		return empOrderList;
    }

    //CREATE AN ORDER
    public Order createOrder(Order order) {
        order.setRejectedByAdmin(false);

        String currentDateAndTime = GlobalClassForFunctions.getCurrentDateAndTime(System.currentTimeMillis());
        order.setOrderDate(currentDateAndTime);

        Order createdOrder = furnitureRepository.save(order);
        return createdOrder;
    }

    //GET SINGLE ORDER
    public ResponseEntity<Order> getSingleOrder(Long orderId) {
        Order order = furnitureRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        return ResponseEntity.ok(order);
    }

    //UPDATE ORDER USING PUT
//	public ResponseEntity<Order> updateOrder(Long orderId, Order newOrder){
//		Order oldOrder = furnitureRepository.findById(orderId)
//				.orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
//		oldOrder.setEmpId(newOrder.getEmpId());
//		oldOrder.setEmpName(newOrder.getEmpName());
//		oldOrder.setEmail(newOrder.getEmail());
//		oldOrder.setItemRequested(newOrder.getItemRequested());
//		oldOrder.setQty(newOrder.getQty());
//		oldOrder.setShippingAddress(newOrder.getShippingAddress());
//		oldOrder.setPhnNo(newOrder.getPhnNo());
//		Order updatedOrder = furnitureRepository.save(oldOrder);
//		return ResponseEntity.ok(updatedOrder);
//	}

    //UPDATE ORDER
    public ResponseEntity<Order> updateOrder(Long id, Map<Object, Object> orderDetails) {
        Order order = furnitureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist with id :" + id));
        orderDetails.forEach((k, v) -> {
            Field fields = ReflectionUtils.findField(Order.class, (String) k);
            assert fields != null;
            fields.setAccessible(true);
            ReflectionUtils.setField(fields, order, v);
        });
        Order updatedOrder = furnitureRepository.save(order);
        return ResponseEntity.ok(updatedOrder);
    }

    //DELETE ORDER
    public ResponseEntity<String> deleteOrder(Long orderId) {
        Order order = furnitureRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        furnitureRepository.delete(order);
        return ResponseEntity.ok("true");
    }

    //RETURN EMPLOYEE ID FOR USERNAME
    public ResponseEntity<Long> findEmpIdByUsername(String username){
        return ResponseEntity.ok(furnitureRepository.findEmpIdByUsername(username));
    }

    public ResponseEntity<User> findByEmpId(long empId) {
        return ResponseEntity.ok(userRepository.findByEmpId(empId));
    }
}
