package com.itheima.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.Courier;

public interface CourierRepository extends JpaRepository<Courier, Long>,JpaSpecificationExecutor<Courier> {
	@Modifying
	@Query("update Courier set deltag = 1 where C_ID = ?")
	void update(long id);
	@Modifying
	@Query("update Courier set deltag = null where C_ID = ?")
	void decline(long id);
}
