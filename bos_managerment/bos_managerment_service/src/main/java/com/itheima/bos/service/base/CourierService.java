package com.itheima.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.base.Courier;

public interface CourierService {

	void save(Courier model);

	Page<Courier> pageQuery(Pageable pageable);

	void deleteById(String[] ids);

	void declineById(String[] ids);

    Page<Courier> pageQuery(Specification<Courier> specification, Pageable pageable);

}
