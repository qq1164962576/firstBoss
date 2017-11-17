package com.itheima.bos.service.base.impl;


import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.base.CourierService;
@Service
@Transactional
public class CourierServiceImpl implements CourierService {
	@Autowired
	private CourierRepository courierRepository;

	@RequiresPermissions("courierAction_save")
	@Override
	public void save(Courier model) {
		courierRepository.save(model);
	}
	
	@Override
	public Page<Courier> pageQuery(Pageable pageable) {
		return courierRepository.findAll(pageable);
	}
	
	@Override
	public void deleteById(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			long id = Long.parseLong(ids[i]);
			
			courierRepository.update(id);
		}
	}
	
	@Override
	public void declineById(String[] ids) {
		for (int i = 0; i < ids.length; i++) {
			long id = Long.parseLong(ids[i]);
			
			courierRepository.decline(id);
		}
	}
	
    @Override
    public Page<Courier> pageQuery(Specification<Courier> specification, Pageable pageable) {
        return courierRepository.findAll(specification, pageable);
    }
    
    
    @Override
    public List<Courier> findAll() {

        return courierRepository.findAll();
    
    }

    @Override
    public List<Courier> findByDelTagisNull() {
          
        return courierRepository.findByDeltagIsNull();
    }

}
