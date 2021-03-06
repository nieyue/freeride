package com.nieyue.bean;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 配置
 * @author yy
 *
 */
@Data
@ApiModel(value="配置",description="配置")
@TableName("config_tb")
public class Config implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 配置id
	 */
	@ApiModelProperty(value="配置id")
	@TableId("config_id")
	private Long configId;
	/**
	 * 平台名称
	 */
	@ApiModelProperty(value="平台名称")
	private String platformName;
	/**
	 * 平台联系电话
	 */
	@ApiModelProperty(value="平台联系电话")
	private String servicePhone;
	/**
	 * 平台联系qq
	 */
	@ApiModelProperty(value="平台联系qq")
	private String serviceQq;
	/**
	 * 免费发布次数
	 */
	@ApiModelProperty(value="免费发布次数")
	private Integer freeNumber;
	/**
	 * 每天首次最少发布数量
	 */
	@ApiModelProperty(value="每天首次最少发布数量")
	private Integer perdayStartMinNum;
	/**
	 * 每天首次最大发布数量
	 */
	@ApiModelProperty(value="每天首次最大发布数量")
	private Integer perdayStartMaxNum;
	/**
	 * 分隔多少秒发布
	 */
	@ApiModelProperty(value="分隔多少秒发布")
	private Integer perSeconds;
	/**
	 * 分隔秒最少发布数量
	 */
	@ApiModelProperty(value="分隔秒最少发布数量")
	private Integer perSecondsMinNum;
	/**
	 * 分隔秒最大发布数量
	 */
	@ApiModelProperty(value="分隔秒最大发布数量")
	private Integer perSecondsMaxNum;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value="创建时间",example="创建时间")
	private Date createDate;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value="更新时间",example="更新时间")
	private Date updateDate;
}
