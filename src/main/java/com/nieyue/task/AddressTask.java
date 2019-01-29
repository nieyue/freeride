package com.nieyue.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.nieyue.bean.Address;
import com.nieyue.bean.Config;
import com.nieyue.bean.Trip;
import com.nieyue.service.AddressService;
import com.nieyue.service.ConfigService;
import com.nieyue.service.TripService;
import com.nieyue.util.MyDom4jUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;

@Configuration
public class AddressTask {
    @Autowired
    TripService tripService;
    @Autowired
    ConfigService configService;
    @Autowired
    AddressService addressService;
    List<Address> addressList=new ArrayList<>();


    //每天早上6点到晚上9点，每隔10分钟执行一次
   @Scheduled(cron="0 0/10 6-21 * * ?")
    public void perSeconds(){
        List<Config> cl = configService.simplelist(null);
        if(cl.size()==1){
            Config config = cl.get(0);
            Integer perSecondsMinNum = config.getPerSecondsMinNum();
            Integer perSecondsMaxNum = config.getPerSecondsMaxNum();
            int number = new Random().nextInt(perSecondsMaxNum - perSecondsMinNum) + perSecondsMinNum;
            //随机选number个
            Wrapper<Address> wrapper=new EntityWrapper<>();
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("type", 1);//出发地
            wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
            int count = addressService.count(wrapper);
            int start=1;
            if(count<=0){
                return;
            }
            if(count<=number){
                start=1;
                number=count;
            }else{
                start=new Random().nextInt(count-number)+1;
            }
            List<Address> al = addressService.list(start, number, null, null, wrapper);

            Wrapper<Address> wrapper2=new EntityWrapper<>();
            Map<String,Object> map2=new HashMap<String,Object>();
            map2.put("type", 2);//目的地
            wrapper2.allEq(MyDom4jUtil.getNoNullMap(map2));
            int count2 = addressService.count(wrapper2);
            if(count2<=0){
                return;
            }
            int start2=1;
            if(count2<=number&&count2<=count){
                start2=1;
                number=count2;
            }else if(count2>number){
                start2=new Random().nextInt(count2-number)+1;
            }
            List<Address> al2 = addressService.list(start2, number, null, null, wrapper2);
            for(int i = 0; i < number; i++) {
                Trip trip=new Trip();
                trip.setType(2);
                //7天
               // trip.setStartDate(new Date(new Date().getTime()+1000*60*60*(new Random().nextInt(7*24-1)+1)));
                //今天
                trip.setStartDate(new Date(new Date().getTime()+1000*60*60*(new Random().nextInt(21-new Date().getHours()))));
                trip.setUpdateDate(new Date());
                trip.setCreateDate(new Date());
                trip.setAccountId(1000l);
                trip.setStartAddress(al.get(i).getAddress());
                trip.setEndAddress(al2.get(i).getAddress());
                //途径地放市
                trip.setMiddleAddress(al.get(i).getAddress());
                int persionnumber=new Random().nextInt(3)+1;
                trip.setPersonNumber(persionnumber);
                tripService.add(trip);

            }
        }
    }
    //每天早上6点执行一次
    //@Scheduled(cron="0 0 6 * * ?")
    public void predayStart(){
        List<Config> cl = configService.simplelist(null);
        if(cl.size()==1){
            Config config = cl.get(0);
            Integer perdayStartMinNum = config.getPerdayStartMinNum();
            Integer perdayStartMaxNum = config.getPerdayStartMaxNum();
            int number = new Random().nextInt(perdayStartMaxNum - perdayStartMinNum) + perdayStartMinNum;
            //随机选number个
            Wrapper<Address> wrapper=new EntityWrapper<>();
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("type", 1);//出发地
            wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
            int count = addressService.count(wrapper);
            if(count<=0){
                return;
            }
            int start=1;
            if(count<=number){
                start=1;
                number=count;
            }else{
                start=new Random().nextInt(count-number)+1;
            }
            List<Address> al = addressService.list(start, number, null, null, wrapper);

            Wrapper<Address> wrapper2=new EntityWrapper<>();
            Map<String,Object> map2=new HashMap<String,Object>();
            map2.put("type", 2);//目的地
            wrapper2.allEq(MyDom4jUtil.getNoNullMap(map2));
            int count2 = addressService.count(wrapper2);
            if(count2<=0){
                return;
            }
            int start2=1;
            if(count2<=number&&count2<=count){
                start2=1;
                number=count2;
            }else if(count2>number){
                    start2=new Random().nextInt(count2-number)+1;
            }
            List<Address> al2 = addressService.list(start2, number, null, null, wrapper2);


            for(int i = 0; i < number; i++) {
                Trip trip=new Trip();
                trip.setType(2);
                //7天
               // trip.setStartDate(new Date(new Date().getTime()+1000*60*60*(new Random().nextInt(7*24-1)+1)));
                trip.setStartDate(new Date(new Date().getTime()+1000*60*60*(new Random().nextInt(21-new Date().getHours()))));
                trip.setUpdateDate(new Date());
                trip.setCreateDate(new Date());
                trip.setAccountId(1000l);
                trip.setStartAddress(al.get(i).getAddress());
                trip.setEndAddress(al2.get(i).getAddress());
                //途径地放市
                trip.setMiddleAddress(al.get(i).getAddress());
                int persionnumber=new Random().nextInt(3)+1;
                trip.setPersonNumber(persionnumber);
                tripService.add(trip);

            }
        }
    }



}
