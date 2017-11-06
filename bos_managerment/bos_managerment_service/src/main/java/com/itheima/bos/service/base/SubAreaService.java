package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.SubArea;

/**  
 * ClassName:subAreaService <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 10:01:35 AM <br/>       
 */
public interface SubAreaService {

    void save(SubArea model);

    List<SubArea> findAll();

    Page<SubArea> pageQuery(Pageable pageable);

}
  
