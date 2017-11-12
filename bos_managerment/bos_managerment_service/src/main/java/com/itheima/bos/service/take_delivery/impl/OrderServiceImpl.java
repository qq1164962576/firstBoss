package com.itheima.bos.service.take_delivery.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.AreaRepository;
import com.itheima.bos.dao.base.FixedAreaRepository;
import com.itheima.bos.dao.base.SubAreaRepository;
import com.itheima.bos.dao.take_delivery.OrderRepository;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.domain.take_delivery.Order;
import com.itheima.bos.domain.take_delivery.WorkBill;
import com.itheima.bos.service.take_delivery.OrderService;

/**  
 * ClassName:OrderServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 11, 2017 9:16:17 PM <br/>       
 */  
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private SubAreaRepository subAreaRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    @Override
    public void save(Order order) {
        //查出定区
        Area sendArea = order.getSendArea();
        if(sendArea!=null) {
            Area sendAreaDB = areaRepository.findByProvinceAndCityAndDistrict(sendArea.getProvince(), sendArea.getCity(), sendArea.getDistrict());
            Set<SubArea> subareas = sendAreaDB.getSubareas();
            for (SubArea subArea : subareas) {
                System.out.println(subArea);
            }
            order.setSendArea(sendAreaDB);
            
        }
        Area recArea = order.getRecArea();

        if(recArea!=null) {
            Area recAreaDB = areaRepository.findByProvinceAndCityAndDistrict(sendArea.getProvince(), sendArea.getCity(), sendArea.getDistrict());
            order.setRecArea(recAreaDB);
        }
        //确定订单和下单时间
        //确定收派员
        String sendAddress = order.getSendAddress();
        if(StringUtils.isNotEmpty(sendAddress)){
            Long fixedAreaId =  WebClient
                    .create("http://localhost:8180/bos-crm/webservice/customerService/findByfixedAreaId")
                    .query("address",sendAddress)
                    .get(Long.class);
            if(fixedAreaId!=null&&fixedAreaId!=0) {
                //如果定区id不为空,通过id找到定区,并获得此定去所有快递员的信息
                FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
                Set<Courier> couriers = fixedArea.getCouriers();
                Iterator<Courier> iterator = couriers.iterator();
                if(iterator.hasNext()) {
                    Courier courier = iterator.next();
                    order.setCourier(courier);
                    String numeric = RandomStringUtils.randomNumeric(32);
                    Date date = new Date();
                    order.setOrderNum(date+numeric);
                    order.setOrderType("自动分单");
                    WorkBill workBill = new WorkBill();
                    workBill.setBuildtime(new Date());
                    workBill.setCourier(courier);
                    workBill.setOrder(order);
                    workBill.setType("新");
                    workBill.setPickstate(order.getRemark());
                    System.out.println("根据customer_address订单保存成功");
                    orderRepository.save(order);
                    return;
                }
            }
        }
        Area sendAreaDB = order.getSendArea();
        if(sendAreaDB!=null) {
            List<SubArea> subareas =  subAreaRepository.findByArea(sendAreaDB);
            for (SubArea subArea : subareas) {
                String assistkeywords = subArea.getAssistKeyWords();
                String keyWords = subArea.getKeyWords();
                if(sendAddress.contains(keyWords)||sendAddress.contains(assistkeywords)) {
                    FixedArea fixedArea = subArea.getFixedArea();
                    Set<Courier> couriers = fixedArea.getCouriers();
                    if(couriers!=null) {
                        Courier courier = couriers.iterator().next();
                        order.setCourier(courier);
                        String numeric = RandomStringUtils.randomNumeric(32);
                        Date date = new Date();
                        order.setOrderNum(date+numeric);
                        order.setOrderType("自动分单");
                        WorkBill workBill = new WorkBill();
                        workBill.setBuildtime(new Date());
                        workBill.setCourier(courier);
                        workBill.setOrder(order);
                        workBill.setType("新");
                        workBill.setPickstate(order.getRemark());
                        order.getWorkBills().add(workBill);
                        System.out.println("根据分区确定courier订单保存成功");
                        orderRepository.save(order);
                        return;
                    }
                }
            }
        }
        String numeric = RandomStringUtils.randomNumeric(32);
        Date date = new Date();
        order.setOrderNum(date+numeric);
        order.setOrderType("人工分单");
        System.out.println("保存订单成功_人工分单");
        orderRepository.save(order);
        
        
    }
    
}
  
