package com.nieyue.task;

import com.nieyue.bean.Address;
import com.nieyue.bean.Config;
import com.nieyue.bean.Trip;
import com.nieyue.service.AddressService;
import com.nieyue.service.ConfigService;
import com.nieyue.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Configuration
public class AddressTask {
    @Autowired
    TripService tripService;
    @Autowired
    ConfigService configService;
    @Autowired
    AddressService addressService;
    List<Address> addressList=new ArrayList<>();


    //每天早上6点到晚上9点，每隔5分钟执行一次
   @Scheduled(cron="0 0/5 6-21 * * ?")
    public void perSeconds(){
        addressList=addressService.simplelist(null);
        if(addressList.size()<=0){
            return;
        }
        List<Address> alc = new ArrayList<>();;
        List<Address> alm = new ArrayList<>();
        for (int i = 0; i <addressList.size(); i++) {
            Address address = addressList.get(i);
            if(address.getType()==1){
                alc.add(address);
            }else if(address.getType()==2){
                alm.add(address);
            }
        }

        List<Config> cl = configService.simplelist(null);
        if(cl.size()==1){
            Config config = cl.get(0);
            Integer perSecondsMinNum = config.getPerSecondsMinNum();
            Integer perSecondsMaxNum = config.getPerSecondsMaxNum();
            int number = new Random().nextInt(perSecondsMaxNum - perSecondsMinNum) + perSecondsMinNum;

            for(int i = 0; i < number; i++) {
                Trip trip=new Trip();
                trip.setType(2);
                //7天
               // trip.setStartDate(new Date(new Date().getTime()+1000*60*60*(new Random().nextInt(7*24-1)+1)));
                //今天
                trip.setStartDate(new Date(new Date().getTime()+1000*60*60*(new Random().nextInt(24-new Date().getHours()-1))));
                trip.setUpdateDate(new Date());
                trip.setCreateDate(new Date());
                trip.setAccountId(1000l);
                Address alca = alc.get(new Random().nextInt(alc.size()));
                trip.setStartAddress(alca.getCity()+alca.getAddress());
                Address alma = alm.get(new Random().nextInt(alm.size()));
                while(alma.getAddress().equals(alca.getAddress())){
                    alma = alm.get(new Random().nextInt(alm.size()));
                }
                trip.setEndAddress(alma.getCity()+alma.getAddress());
                //trip.setMiddleAddress(alma.getCity()+alma.getAddress());
                int persionnumber=new Random().nextInt(3)+1;
                trip.setPersonNumber(persionnumber);
                tripService.add(trip);

            }
        }
    }
    //每天早上6点执行一次
    @Scheduled(cron="0 0 6 * * ?")
    public void predayStart(){
        addressList=addressService.simplelist(null);
        if(addressList.size()<=0){
            return;
        }
        List<Address> alc = new ArrayList<>();;
        List<Address> alm = new ArrayList<>();
        for (int i = 0; i <addressList.size(); i++) {
            Address address = addressList.get(i);
            if(address.getType()==1){
                alc.add(address);
            }else if(address.getType()==2){
                alm.add(address);
            }
        }

        List<Config> cl = configService.simplelist(null);
        if(cl.size()==1){
            Config config = cl.get(0);
            Integer perdayStartMinNum = config.getPerdayStartMinNum();
            Integer perdayStartMaxNum = config.getPerdayStartMaxNum();
            int number = new Random().nextInt(perdayStartMaxNum - perdayStartMinNum) + perdayStartMinNum;

            for(int i = 0; i < number; i++) {
                Trip trip=new Trip();
                trip.setType(2);
                //7天
               // trip.setStartDate(new Date(new Date().getTime()+1000*60*60*(new Random().nextInt(7*24-1)+1)));
                trip.setStartDate(new Date(new Date().getTime()+1000*60*60*(new Random().nextInt(24-new Date().getHours()-1))));
                trip.setUpdateDate(new Date());
                trip.setCreateDate(new Date());
                trip.setAccountId(1000l);
                Address alca = alc.get(new Random().nextInt(alc.size()));
                trip.setStartAddress(alca.getCity()+alca.getAddress());
                Address alma = alm.get(new Random().nextInt(alm.size()));
                while(alma.getAddress().equals(alca.getAddress())){
                    alma = alm.get(new Random().nextInt(alm.size()));
                }
                trip.setEndAddress(alma.getCity()+alma.getAddress());
                //trip.setMiddleAddress(alma.getCity()+alma.getAddress());
                int persionnumber=new Random().nextInt(3)+1;
                trip.setPersonNumber(persionnumber);
                tripService.add(trip);

            }
        }
    }



}
