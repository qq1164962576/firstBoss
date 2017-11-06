package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:AreaService <br/>  
 * Function:  <br/>  
 * Date:     Nov 3, 2017 11:13:05 AM <br/>       
 */
public interface AreaService {

    void save(List<Area> list);

    Page<Area> pageQuery(Pageable pageable);

    List<Area> findAll();

    List<Area> findByQ(String q);

}
  
