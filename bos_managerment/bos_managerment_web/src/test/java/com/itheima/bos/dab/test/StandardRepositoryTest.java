package com.itheima.bos.dab.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itheima.bos.dao.base.StandardRepository;
import com.itheima.bos.domain.base.Standard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardRepositoryTest {
	@Autowired
	private StandardRepository standardRepository;
	@Test
	public void test() {
		Standard standard = new Standard();
		standard.setId(1L);
		standardRepository.delete(standard);
	}
	@Test
	public void findAll(){
		List<Standard> list = standardRepository.findAll();
		//System.out.println(list);
		for (Standard standard : list) {
			System.out.println(standard);
		}
		
	}
	@Test
	public void findByName(){
		//List<Standard> list = standardRepository.findByName("张三");
		List<Standard> list = standardRepository.findByName("李四");
		//List<Standard> list = standardRepository.findByName("张三");
		/*for(Standard standard : list){
			System.out.println(standard);
		}*/
		System.out.println(list);
	}
	@Test
	public void findByAnd(){
		Standard standard = standardRepository.findByNameAndMaxLength("张三", 100);
		List<Standard> list = standardRepository.findByName("张三");
		System.out.println(standard);
		System.out.println(list.size());
		System.out.println(list);
		standard = standardRepository.findByNa("张三", 30);
		System.out.println(standard);
		standard = standardRepository.findByNa1("张三", 100);
		System.out.println(standard);
	}
	@Test
	public void findByLike(){
		List<Standard> list = standardRepository.findByNameLike("张%");
		for (Standard standard : list) {
			System.out.println(standard);
		}
		
	}
	@Test
	public void update(){
		standardRepository.updateByName(103, "张三");
	}
}
