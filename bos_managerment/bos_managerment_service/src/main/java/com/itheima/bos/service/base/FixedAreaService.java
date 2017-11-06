package com.itheima.bos.service.base;

import java.util.List;

import com.itheima.bos.domain.base.FixedArea;

/**  
 * ClassName:FixedAreaService <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 9:30:33 PM <br/>       
 */
public interface FixedAreaService {

    List<FixedArea> findAll();

    void save(FixedArea model);

}
  
