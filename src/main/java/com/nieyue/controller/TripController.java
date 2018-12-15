package com.nieyue.controller;

import com.nieyue.bean.Trip;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import com.nieyue.service.TripService;
import com.nieyue.util.MyDom4jUtil;
import com.nieyue.util.StateResultList;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 行程控制类
 * @author yy
 *
 */
@Api(tags={"trip"},value="行程",description="行程管理")
@RestController
@RequestMapping("/trip")
public class TripController extends BaseController<Trip,Long> {
	@Resource
	private TripService tripService;
	
	/**
	 * 行程分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "行程列表", notes = "行程分页浏览")
	@ApiImplicitParams({
	  @ApiImplicitParam(name="isDoor",value="是否上门接送，1是，2否",dataType="int", paramType = "query"),
	  @ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query"),
	  @ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
	  @ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
	  @ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="createDate"),
	  @ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Trip>> list(
			@RequestParam(value="isDoor",required=false)Integer isDoor,
			@RequestParam(value="accountId",required=false)Long accountId,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="createDate") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
		Wrapper<Trip> wrapper=new EntityWrapper<>();
		Map<String,Object> map=new HashMap<>();
		map.put("is_door", isDoor);
		map.put("account_id", accountId);
		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<Trip>> rl = super.list(pageNum, pageSize, orderName, orderWay,wrapper);
			return rl;
	}
	/**
	 * 行程修改
	 * @return
	 */
	@ApiOperation(value = "行程修改", notes = "行程修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Trip>> update(@ModelAttribute Trip trip,HttpSession session)  {
		trip.setUpdateDate(new Date());
		StateResultList<List<Trip>> u = super.update(trip);
		return u;
	}
	/**
	 * 行程增加
	 * @return 
	 */
	@ApiOperation(value = "行程增加", notes = "行程增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Trip>> add(@ModelAttribute Trip trip, HttpSession session) {
		trip.setUpdateDate(new Date());
		StateResultList<List<Trip>> a = super.add(trip);
		return a;
	}
	/**
	 * 行程删除
	 * @return
	 */
	@ApiOperation(value = "行程删除", notes = "行程删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="TripId",value="行程ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Trip>> delete(@RequestParam("TripId") Long tripId,HttpSession session)  {
		StateResultList<List<Trip>> d = super.delete(tripId);
		return d;
	}
	/**
	 * 行程浏览数量
	 * @return
	 */
	@ApiOperation(value = "行程数量", notes = "行程数量查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name="isDoor",value="是否上门接送，1是，2否",dataType="int", paramType = "query"),
			@ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query"),
	})
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="isDoor",required=false)Integer isDoor,
			@RequestParam(value="accountId",required=false)Long accountId,
			HttpSession session)  {
		Wrapper<Trip> wrapper=new EntityWrapper<>();
		Map<String,Object> map=new HashMap<>();
		map.put("is_door", isDoor);
		map.put("account_id", accountId);
		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		StateResultList<List<Integer>> c = super.count(wrapper);
		return c;
	}
	/**
	 * 行程单个加载
	 * @return
	 */
	@ApiOperation(value = "行程单个加载", notes = "行程单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="tripId",value="行程ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<Trip>> loadTrip(@RequestParam("tripId") Long tripId,HttpSession session)  {
		 StateResultList<List<Trip>> l = super.load(tripId);
		 return l;
	}
	
}
