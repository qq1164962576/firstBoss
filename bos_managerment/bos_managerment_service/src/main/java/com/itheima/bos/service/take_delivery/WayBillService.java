package com.itheima.bos.service.take_delivery;

import com.itheima.bos.domain.take_delivery.WayBill;

import java.util.List;

/**  
 * ClassName:WayBillService <br/>  
 * Function:  <br/>  
 * Date:     Nov 13, 2017 10:41:00 AM <br/>       
 */
public interface WayBillService {

    void save(WayBill model);

    List<WayBill> findAll();
}
  
