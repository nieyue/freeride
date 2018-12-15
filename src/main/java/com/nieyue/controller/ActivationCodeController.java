package com.nieyue.controller;

import com.nieyue.bean.ActivationCode;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import com.nieyue.service.ActivationCodeService;
import com.nieyue.util.MyDom4jUtil;
import com.nieyue.util.StateResultList;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 激活码控制类
 * @author yy
 *
 */
@Api(tags={"activationCode"},value="激活码",description="激活码管理")
@RestController
@RequestMapping("/activationCode")
public class ActivationCodeController extends BaseController<ActivationCode,Long> {
	@Resource
	private ActivationCodeService activationCodeService;
	
	/**
	 * 激活码分页浏览
	 * @param orderName 商品排序数据库字段
	 * @param orderWay 商品排序方法 asc升序 desc降序
	 * @return
	 */
	@ApiOperation(value = "激活码列表", notes = "激活码分页浏览")
	@ApiImplicitParams({
	  @ApiImplicitParam(name="isUsered",value="是否使用，1未使用，2已使用",dataType="int", paramType = "query"),
	  @ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query"),
	  @ApiImplicitParam(name="createDate",value="创建时间",dataType="date-time", paramType = "query"),
	  @ApiImplicitParam(name="pageNum",value="页头数位",dataType="int", paramType = "query",defaultValue="1"),
	  @ApiImplicitParam(name="pageSize",value="每页数目",dataType="int", paramType = "query",defaultValue="10"),
	  @ApiImplicitParam(name="orderName",value="排序字段",dataType="string", paramType = "query",defaultValue="updateDate"),
	  @ApiImplicitParam(name="orderWay",value="排序方式",dataType="string", paramType = "query",defaultValue="desc")
	  })
	@RequestMapping(value = "/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ActivationCode>> list(
			@RequestParam(value="isUsered",required=false)Integer isUsered,
			@RequestParam(value="accountId",required=false)Long accountId,
			@RequestParam(value="createDate",required=false)Date createDate,
			@RequestParam(value="pageNum",defaultValue="1",required=false)int pageNum,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="orderName",required=false,defaultValue="updateDate") String orderName,
			@RequestParam(value="orderWay",required=false,defaultValue="desc") String orderWay)  {
		Wrapper<ActivationCode> wrapper=new EntityWrapper<>();
		Map<String,Object> map=new HashMap<>();
		map.put("is_usered", isUsered);
		map.put("account_id", accountId);
		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		//大于等于
		wrapper.andNew().ge("create_date",createDate);
		StateResultList<List<ActivationCode>> rl = super.list(pageNum, pageSize, orderName, orderWay,wrapper);
			return rl;
	}
	/**
	 * 激活码修改
	 * @return
	 */
	@ApiOperation(value = "激活码修改", notes = "激活码修改")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ActivationCode>> update(@ModelAttribute ActivationCode activationCode,HttpSession session)  {
		activationCode.setUpdateDate(new Date());
		StateResultList<List<ActivationCode>> u = super.update(activationCode);
		return u;
	}
	/**
	 * 激活码增加
	 * @return 
	 */
	@ApiOperation(value = "激活码增加", notes = "激活码增加")
	@RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ActivationCode>> add(@ModelAttribute ActivationCode activationCode, HttpSession session) {
		activationCode.setUpdateDate(new Date());
		StateResultList<List<ActivationCode>> a = super.add(activationCode);
		return a;
	}
	/**
	 * 激活码删除
	 * @return
	 */
	@ApiOperation(value = "激活码删除", notes = "激活码删除")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="activationCodeId",value="激活码ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<ActivationCode>> delete(@RequestParam("activationCodeId") Long activationCodeId,HttpSession session)  {
		StateResultList<List<ActivationCode>> d = super.delete(activationCodeId);
		return d;
	}
	/**
	 * 激活码浏览数量
	 * @return
	 */
	@ApiOperation(value = "激活码数量", notes = "激活码数量查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name="isUsered",value="是否使用，1未使用，2已使用",dataType="int", paramType = "query"),
			@ApiImplicitParam(name="accountId",value="账户id",dataType="long", paramType = "query"),
			@ApiImplicitParam(name="createDate",value="创建时间",dataType="date-time", paramType = "query"),
	})
	@RequestMapping(value = "/count", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody StateResultList<List<Integer>> count(
			@RequestParam(value="isUsered",required=false)Integer isUsered,
			@RequestParam(value="accountId",required=false)Long accountId,
			@RequestParam(value="createDate",required=false)Date createDate,
			HttpSession session)  {
		Wrapper<ActivationCode> wrapper=new EntityWrapper<>();
		Map<String,Object> map=new HashMap<>();
		map.put("is_usered", isUsered);
		map.put("account_id", accountId);
		wrapper.allEq(MyDom4jUtil.getNoNullMap(map));
		//大于等于
		wrapper.andNew().ge("create_date",createDate);
		StateResultList<List<Integer>> c = super.count(wrapper);
		return c;
	}
	/**
	 * 激活码单个加载
	 * @return
	 */
	@ApiOperation(value = "激活码单个加载", notes = "激活码单个加载")
	@ApiImplicitParams({
		  @ApiImplicitParam(name="activationCodeId",value="激活码ID",dataType="long", paramType = "query",required=true)
		  })
	@RequestMapping(value = "/load", method = {RequestMethod.GET,RequestMethod.POST})
	public  StateResultList<List<ActivationCode>> loadActivationCode(@RequestParam("activationCodeId") Long activationCodeId,HttpSession session)  {
		 StateResultList<List<ActivationCode>> l = super.load(activationCodeId);
		 return l;
	}
	
}
