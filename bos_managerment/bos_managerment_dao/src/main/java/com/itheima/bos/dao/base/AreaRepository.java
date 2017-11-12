package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.Area;
import java.lang.String;

/**  
 * ClassName:AreaRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 3, 2017 11:17:54 AM <br/>       
 */
public interface AreaRepository extends JpaRepository<Area , Long> {
    @Query("from Area where province like ?1 or city like ?1 or district like ?1 or postcode like ?1 or citycode like ?1 or shortcode like ?1")
    List<Area> findByQ(String q);
    Area findByProvinceAndCityAndDistrict(String province,String city,String distrct);
}
  
