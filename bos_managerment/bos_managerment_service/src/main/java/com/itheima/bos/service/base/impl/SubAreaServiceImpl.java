package com.itheima.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.SubAreaRepository;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.SubAreaService;

/**  
 * ClassName:SubAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 10:03:06 AM <br/>       
 */
@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService{
    @Autowired
    private SubAreaRepository subAreaRepository;
    @Override
    public void save(SubArea model) {
          subAreaRepository.save(model);
    }
    @Override
    public List<SubArea> findAll() {
          
        return subAreaRepository.findAll();
    }
    @Override
    public Page<SubArea> pageQuery(Pageable pageable) {
          
        return subAreaRepository.findAll(pageable);
    }

}
  
