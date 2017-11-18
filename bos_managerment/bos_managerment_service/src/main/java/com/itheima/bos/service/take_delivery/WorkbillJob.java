package com.itheima.bos.service.take_delivery;


import com.itheima.bos.dao.take_delivery.WorkBillRepository;
import com.itheima.bos.domain.take_delivery.WorkBill;
import com.ithiema.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkbillJob {
    @Autowired
    private WorkBillRepository workBillRepository;

    public void sendEmail(){
        List<WorkBill> list = workBillRepository.findAll();

        String emailBody = "编号\t取件状态\t订单备注\t快递员姓名<br/>";

        for (WorkBill workBill : list) {
            emailBody += emailBody + workBill.getId()+"\t"
                    +workBill.getPickstate()+"\t"
                    +workBill.getRemark()+"\t"
                    +workBill.getCourier().getName()+"<br/>";
        }
        MailUtils.sendMail("4lr@store.com","工单信息统计",emailBody);
    }
}
