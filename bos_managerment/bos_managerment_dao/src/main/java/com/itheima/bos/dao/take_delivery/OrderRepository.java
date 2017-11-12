package com.itheima.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itheima.bos.domain.take_delivery.Order;

/**  
 * ClassName:OrderRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 11, 2017 10:54:05 PM <br/>       
 */
public interface OrderRepository extends JpaRepository<Order, Integer > {

}
  
