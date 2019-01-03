package com.nieyue;

import com.nieyue.bean.Address;
import com.nieyue.bean.Config;
import com.nieyue.bean.Trip;
import com.nieyue.service.AddressService;
import com.nieyue.service.ConfigService;
import com.nieyue.service.TripService;
import com.nieyue.util.DateUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import com.nieyue.service.PermissionService;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
//@EnableRedisHttpSession
//@Import({DynamicDataSourceRegister.class})
@ServletComponentScan
@EnableSwagger2
@MapperScan("com/nieyue/dao")
public class Application implements ApplicationListener<ApplicationReadyEvent> {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Bean
	public Docket createRestApi(){
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(
						new ApiInfoBuilder()
								.title("赤兔顺风车平台接口文档")
								.description("赤兔顺风车平台接口文档1.0版本")
								.version("1.0")
								.build())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.nieyue.controller"))
				.paths(PathSelectors.any())
				.build();
	}
	@Bean
	public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerCustomizer() {

		return new WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>() {
			@Override
			public void customize(ConfigurableServletWebServerFactory   container) {
				ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/home/401.html");
				ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/home/404.html");
				ErrorPage error406Page = new ErrorPage(HttpStatus.NOT_ACCEPTABLE, "/home/404.html");
				ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/home/404.html");

				container.addErrorPages( error401Page);
				container.addErrorPages( error404Page);
				container.addErrorPages( error406Page);
				container.addErrorPages( error500Page);
			}
		};
	}

	@Autowired
	PermissionService permissionService;
	@Autowired
	TripService tripService;
	@Autowired
	ConfigService configService;
	@Autowired
	AddressService addressService;
	/**
	 * 容器初始化
	 * @param event
	 */
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

		//初始化权限列表
		permissionService.initPermission();
		//每天6点到9点自动发布
		Map<String ,String> map=new ConcurrentHashMap<>();
		List<Address> al = addressService.simplelist(null);
		final int[] startnumber = {0};
		final int[] endnumber = {0};
		//首次发布
		Thread thread1=new Thread(){
			@Override
			public void run() {
				while (true){
					try {
						//一分钟检查一次
						this.sleep(60*1000);
						if(new Date().getHours()==6 && map.get(DateUtil.getImgDir()+"hours6")==null){
							map.remove(DateUtil.dateFormatSimpleDate(DateUtil.getFirstToDay(new Date(),-1),"yyyyMMdd")+"hours6");
							map.put(DateUtil.getImgDir()+"hours6","6");
							List<Config> cl = configService.simplelist(null);
							if(cl.size()==1){
								Config config = cl.get(0);
								Integer perdayStartMinNum = config.getPerdayStartMinNum();
								Integer perdayStartMaxNum = config.getPerdayStartMaxNum();
								int number = new Random().nextInt(perdayStartMaxNum - perdayStartMinNum) + perdayStartMinNum;

								for(int i = 0; i < number; i++) {
									Trip trip=new Trip();
									trip.setType(2);
									trip.setUpdateDate(new Date());
									trip.setCreateDate(new Date());
									trip.setAccountId(1000l);
									trip.setStartAddress(al.get(startnumber[0]).getAddress());
									trip.setMiddleAddress(al.get(endnumber[0]).getAddress());
								int persionnumber=new Random().nextInt(3)+1;
									trip.setPersonNumber(persionnumber);
									tripService.add(trip);
									startnumber[0]++;
									endnumber[0]++;
								}
							}
							//tripService.add()
						}else{

						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread1.start();

	}
}
