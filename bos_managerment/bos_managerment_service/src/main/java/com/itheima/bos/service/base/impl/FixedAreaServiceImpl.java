package com.itheima.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.dao.base.FixedAreaRepository;
import com.itheima.bos.dao.base.SubAreaRepository;
import com.itheima.bos.dao.base.TakeTimeRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.FixedAreaService;

/**  
 * ClassName:FixedAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 9:31:58 PM <br/>       
 */
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    @Autowired
    private TakeTimeRepository takeTimeRepository;
    @Autowired
    private CourierRepository courierRepository;
    @Autowired
    private SubAreaRepository subAreaRepository;
    @Override
    public List<FixedArea> findAll() {

        return fixedAreaRepository.findAll();
    }
    @Override
    public void save(FixedArea model) {
         fixedAreaRepository.save(model);
    }
    @Override
    public void associationCourierToFixedArea(Long id, Long courierId, Long takeTimeId) {
        FixedArea fixedArea = fixedAreaRepository.findOne(id);
        Courier courier = courierRepository.findOne(courierId);
        TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
        fixedArea.getCouriers().add(courier);
        //courier.getFixedAreas().add(fixedArea); 
        courier.setTakeTime(takeTime);
    }
    
    //关联分区
    @Override
    public void assignSubArea2FixedArea(Long id, List<Long> subAreaIds) {
        FixedArea fixedArea = fixedAreaRepository.findOne(id);
        List<SubArea> list = subAreaRepository.findByFixedAreaId(fixedArea);
        for (SubArea subArea : list) {
            subArea.setFixedArea(null);
        }
          if(subAreaIds!=null&&subAreaIds.size()>0) {
              //获取fixedArea持久态
              for (Long subAreaid : subAreaIds) {
                  //获得分区持久太KJJJJ
                  SubArea subArea = subAreaRepository.findOne(subAreaid);
                  //关联
                  subArea.setFixedArea(fixedArea);
            }
          }
        
    }
    
    //查询未关联的分区
    @Override
    public List<SubArea> findUnAssociatedSubArea() {
         return  subAreaRepository.findByFixedAreaIsNull();
        
    }
    
    //根据id查询已经关联的分区
    @Override
    public List<SubArea> findByFixedAreaIdAssociatedSubArea(FixedArea fixedArea) {
          
        return  subAreaRepository.findByFixedAreaId(fixedArea);
    }

}
  
