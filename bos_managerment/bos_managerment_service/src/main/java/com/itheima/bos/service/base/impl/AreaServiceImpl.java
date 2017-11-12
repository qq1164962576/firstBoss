package com.itheima.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.AreaRepository;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.AreaService;

/**  
 * ClassName:AreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 3, 2017 11:14:28 AM <br/>       
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaRepository areaRepository;
    @Override
    public void save(List<Area> list) {
        if(list.size()>0) {
        for (Area area : list) {
            areaRepository.save(area);
        }
        }
    }
    @Override
    public Page<Area> pageQuery(Pageable pageable) {
          
        return areaRepository.findAll(pageable);
    }
    @Override
    public List<Area> findAll() {
        return areaRepository.findAll();
    }
    @Override
    public List<Area> findByQ(String q) {
        q = q.toUpperCase();
        return areaRepository.findByQ("%"+q+"%");
        
    }
    @Override
    public void save(Area area) {
        areaRepository.save(area);
        
    }

}
  
