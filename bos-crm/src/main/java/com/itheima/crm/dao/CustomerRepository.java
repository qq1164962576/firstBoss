package com.itheima.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.crm.domain.Customer;

/**  
 * ClassName:CustomerRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 6, 2017 8:46:42 AM <br/>       
 */
public interface CustomerRepository extends JpaRepository<Customer , Long>{
    List<Customer> findByFixedAreaIdIsNull();
    @Query("from Customer where fixedAreaId = ?")
    List<Customer> findCustomers2FixedArea(String FixedAreaId);
    @Modifying
    @Query("update Customer set fixedAreaId = null where fixedAreaId = ?")
    void deleteAssociated(String fixedAreaId);
    @Modifying
    @Query("update Customer set fixedAreaId = ? where id = ?")
    void associated(String fixedAreaId, Long id);
    
}
  
