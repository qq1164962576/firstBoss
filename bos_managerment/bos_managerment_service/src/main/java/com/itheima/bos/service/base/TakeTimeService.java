package com.itheima.bos.service.base;

import java.util.List;

import com.itheima.bos.domain.base.TakeTime;

/**  
 * ClassName:TakeTimeService <br/>  
 * Function:  <br/>  
 * Date:     Nov 7, 2017 10:00:40 AM <br/>       
 */
public interface TakeTimeService {

    List<TakeTime> findAll();

    void save(TakeTime model);

}
  
