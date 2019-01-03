package com.nieyue.controller;

import com.nieyue.bean.Address;
import com.nieyue.service.AddressService;
import com.nieyue.util.MyExcel;
import com.nieyue.util.ResultUtil;
import com.nieyue.util.SnowflakeIdWorker;
import com.nieyue.util.StateResultList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * 地址控制类
 * @author yy
 *
 */
@Api(tags={"address"},value="地址",description="地址管理")
@RestController
@RequestMapping("/address")
public class AddressController extends BaseController<Address,Long> {
	@Resource
	private AddressService addressService;
	@Value("${myPugin.uploaderPath.rootPath}")
	String rootPath;
	@Value("${myPugin.uploaderPath.locationPath}")
	String locationPath;
	
	/**
	 * 地址分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "地址列表", notes = "地址分页浏览")
	@ApiImplicitParams({
	    @ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
	    @ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
	    @ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="addressId"),
	    @ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Address>> browsePagingAddress(
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="addressId") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
			StateResultList<List<Address>> rl = super.list(pageNum, pageSize, orderName, orderWay,null);
			return rl;
	}
	/**
	 * 地址修改
	 * @return
	 */
	@ApiOperation(value = "地址修改", notes = "地址修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Address>> updateAddress(
			@ModelAttribute Address address,HttpSession session)  {
		address.setUpdateDate(new Date());
		StateResultList<List<Address>> u = super.update(address);
		return u;
	}
	/**
	 * 地址增加
	 * @return 
	 */
	@ApiOperation(value = "地址增加", notes = "地址增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Address>> addAddress(
			@ModelAttribute Address address,
			HttpSession session) {
		address.setCreateDate(new Date());
		address.setUpdateDate(new Date());
		StateResultList<List<Address>> a = super.add(address);
		return a;
	}
	/**
	 * 地址删除
	 * @return
	 */
	@ApiOperation(value = "地址删除", notes = "地址删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="addressId",value="地址ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Address>> delAddress(
			@RequestParam("addressId") Long addressId,HttpSession session)  {
		StateResultList<List<Address>> d = super.delete(addressId);
		return d;
	}
	/**
	 * 地址浏览数量
	 * @return
	 */
	@ApiOperation(value = "地址数量", notes = "地址数量查询")
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			HttpSession session)  {
		StateResultList<List<Integer>> c = super.count(null);
		return c;
	}
	/**
	 * 地址单个加载
	 * @return
	 */
	@ApiOperation(value = "地址单个加载", notes = "地址单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="AddressId",value="地址ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<Address>> loadAddress(
			@RequestParam("addressId") Long addressId,HttpSession session)  {
		 StateResultList<List<Address>> l = super.load(addressId);
		 return l;
	}
	/**
	 * 导入Excel
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@RequestMapping(value = "/importExcel", method = {RequestMethod.GET,RequestMethod.POST})
	public StateResultList<List<List<List<Object>>>> importExcel(
			@RequestParam("excel") MultipartFile multipartFile,
			HttpSession	 session
	) throws IllegalStateException, IOException {
		String name="";
		name=rootPath+locationPath+"/"+ SnowflakeIdWorker.getId().toString()+multipartFile.getOriginalFilename();
		File file = new File(name);
		multipartFile.transferTo(file);
		List<List<List<Object>>> lll = MyExcel.importData(file);
		for (int i = 0; i < lll.size(); i++) {
			List<List<Object>> ll = lll.get(i);
			//数据从1行开始，0是列名
			for (int j = 1; j < lll.size(); j++) {
				List<Object> l = ll.get(j);
				for (int z = 0; z < l.size(); z++) {
					Address address = null;
					//account.set
					if(z==0){
						address=new Address();
						address.setType(1);//出发地
						address.setAddress(l.get(z).toString());
						address.setCreateDate(new Date());
						address.setUpdateDate(new Date());
						addressService.add(address);
					}else if(z==2){
						address=new Address();
						address.setType(2);//目的地
						address.setAddress(l.get(z).toString());
						address.setCreateDate(new Date());
						address.setUpdateDate(new Date());
						addressService.add(address);
					}
				}

			}
		}
		return ResultUtil.getSlefSRSuccessList(lll);
	}
}
