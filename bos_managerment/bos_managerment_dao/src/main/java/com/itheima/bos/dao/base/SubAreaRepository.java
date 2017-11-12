package com.itheima.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import java.util.List;
import com.itheima.bos.domain.base.Area;

/**  
 * ClassName:SubAreaRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 10:05:00 AM <br/>       
 */
public interface SubAreaRepository extends JpaRepository<SubArea, Long> {
    List<SubArea> findByFixedAreaIsNull();
    @Query("from SubArea where fixedArea = ?")
    List<SubArea> findByFixedAreaId(FixedArea fixedArea);
    List<SubArea> findByArea(Area area);
}
  
