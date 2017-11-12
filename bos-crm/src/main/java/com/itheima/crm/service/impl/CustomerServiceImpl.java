package com.itheima.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.crm.dao.CustomerRepository;
import com.itheima.crm.domain.Customer;
import com.itheima.crm.service.CustomerService;
import com.ithiema.utils.MD5Util;

/**  
 * ClassName:CustomerServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 6, 2017 8:44:58 AM <br/>       
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);

    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findUnAssociatedCustomers() {
          
        return customerRepository.findByFixedAreaIdIsNull(); 
    }

    @Override
    public List<Customer> findCustomersAssociated2FixedArea(String FixedAreaId) {
          
        return customerRepository.findCustomers2FixedArea(FixedAreaId);
    }

    @Override
    public void assignCustomers2Fixedarea(String fixedAreaId, List<Long> ids) {
        customerRepository.deleteAssociated(fixedAreaId);
        if(ids!=null&&ids.size()>0)
        for (Long id : ids) {
            customerRepository.associated(fixedAreaId,id);
        }
    }

    @Override
    public void regist(Customer customer) {
        String telephone = MD5Util.MD5_tops(customer.getTelephone());
        String password = MD5Util.MD5_tops(customer.getPassword());
        customer.setTelephone(telephone);
        customer.setPassword(password);
        customerRepository.save(customer);
    }
    
    @Override
    public void active(String telephone) {
        String telephone5 = MD5Util.MD5_tops(telephone);
        customerRepository.active(telephone5);
        
    }

    @Override
    public Customer findByTelephone(String telephone) {
        String telephone5 = MD5Util.MD5_tops(telephone);
        return customerRepository.findByTelephone(telephone5);
    }

    @Override
    public Customer login(String telephone, String password) {
        String telephone5 = MD5Util.MD5_tops(telephone);
        String password5 = MD5Util.MD5_tops(password);  
        return customerRepository.findByTelephoneAndPassword(telephone5, password5);

    }

    @Override
    public Long findfixedAreaId(String sendAdrress) {
          
        return customerRepository.findByAddress(sendAdrress);
    }

}
  
