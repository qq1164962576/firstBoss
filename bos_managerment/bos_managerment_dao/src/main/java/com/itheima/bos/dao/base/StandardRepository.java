package com.itheima.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.domain.base.Standard;
import java.lang.String;
import java.util.List;

public interface StandardRepository extends JpaRepository<Standard, Long>{
		List<Standard> findByName(String name);
		Standard findByNameAndMaxLength(String name,Integer maxlength);
		@Query(value="select * from T_STANDARD where C_NAME=? and C_MAX_LENGTH = ?",
				nativeQuery=true)
		Standard findByNa(String name, Integer maxLength);
		@Query("from Standard where name = ? and maxLength = ?")
		Standard findByNa1(String name, Integer maxLength);
		
		List<Standard> findByNameLike(String name);
		@Modifying
		@Transactional
		@Query("update Standard set maxLength = ? where name = ?")
		void updateByName(Integer maxLength,String name);
}
