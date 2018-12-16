<!-- 车主管理 -->
<template>
    <div class="body-wrap">
    <div class="body-btn-wrap">
      <Button type='primary'  @click='add'>增加车主</Button>
      <div class="search-wrap">
          <!-- <Select v-model="params.roleId"  transfer class="search-wrap-input" >
            <Option v-for="item in roleList" :value="item.roleId" :key="item.roleId">{{ item.name }}</Option>
        </Select> -->
        <Input v-model="params.realname" class="search-wrap-input" placeholder="姓名"></Input>
        <Select v-model="params.status" transfer class="search-wrap-input"  placeholder="状态，全部">
            <Option v-for="item in statusParamsList" :value="item.id" :key="item.id">{{ item.value }}</Option>
        </Select>
        <Button @click="search" type="info"  >查询</Button>
      </div>
    </div>
		 <!--新增 -->
     <Modal v-model="addAccountModel"
           title="新增车主管理"
           :closable="false"
           :mask-closable="false"
           width="1000px"
    >
      <Form ref="addAccount" :model="addAccount" :label-width="100"  label-position="right"  :rules="addAccountRules">
          <FormItem prop="phone" label="手机(登录账户):">
          <Input type="text" v-model="addAccount.phone" placeholder="手机(登录账户)">
          </Input>
        </FormItem>
        <FormItem prop="password" label="密码:">
          <Input type="password" v-model="addAccount.password" placeholder="密码">
          </Input>
        </FormItem>
         <FormItem prop="realname" label="姓名:">
          <Input type="text" v-model="addAccount.realname" placeholder="姓名">
          </Input>
        </FormItem>
        <FormItem prop="sex" label="性别:">
          <Select v-model="addAccount.sex" transfer size="large" style="width:100px">
              <Option v-for="item in sexList" :value="item.id" :key="item.id">{{ item.value }}</Option>
          </Select>
        </FormItem>
        
        
        <!-- <FormItem prop="icon" label="图像(上传或者填写):">
            <my-upload :defaultUpload="addAccount.icon" @uploadList="getAddIcon"></my-upload> 
            <div>
              <Input type="text" v-model="addAccount.icon" placeholder="图像">
            </Input>
              <img :src="addAccount.icon"  style='width:30px;'alt="">
            </div>
        </FormItem> -->
        <FormItem prop="status" label="状态:">
          <Select v-model="addAccount.status" transfer size="large" style="width:100px">
              <Option v-for="item in statusList" :value="item.id" :key="item.id">{{ item.value }}</Option>
          </Select>
        </FormItem>
        <!-- <FormItem prop="roleId" label="角色:">
          <Select v-model="addAccount.roleId" transfer size="large" style="width:100px">
              <Option v-for="item in roleList" :value="item.roleId" :key="item.roleId">{{ item.name }}</Option>
          </Select>
        </FormItem> -->
      </Form>
      <div slot='footer'>
        <Button  @click='addCancel'>取消</Button>
        <Button type='primary' :loading='addLoading'>
          <span v-if="!addLoading" @click='addSure'>确定</span>
          <span v-else>Loading...</span>
        </Button>
      </div>
    </Modal>
    <!--新增end -->
		 <!--修改 -->
     <Modal v-model="updateAccountModel"
           title="修改车主管理"
           :closable="false"
           :mask-closable="false"
           width="1000px"
    >
      <Form ref="updateAccount" :model="updateAccount" :label-width="100" label-position="right"  :rules="updateAccountRules">
          <FormItem prop="phone" label="手机:">
          <Input type="text" v-model="updateAccount.phone" placeholder="手机">
          </Input>
        </FormItem>
        <!-- <FormItem prop="password" label="密码:">
          <Input type="password" v-model="updateAccount.password" placeholder="密码">
          </Input>
        </FormItem> -->
       <FormItem prop="realname" label="姓名:">
          <Input type="text" v-model="updateAccount.realname" placeholder="姓名">
          </Input>
        </FormItem>
        <FormItem prop="sex" label="性别:">
          <Select v-model="updateAccount.sex" transfer size="large" style="width:100px">
              <Option v-for="item in sexList" :value="item.id" :key="item.id">{{ item.value }}</Option>
          </Select>
        </FormItem>
        
        
        <!-- <FormItem prop="icon" label="图像(上传或者填写):">
            <my-upload :defaultUpload="addAccount.icon" @uploadList="getAddIcon"></my-upload> 
            <div>
              <Input type="text" v-model="addAccount.icon" placeholder="图像">
            </Input>
              <img :src="addAccount.icon"  style='width:30px;'alt="">
            </div>
        </FormItem> -->
        <FormItem prop="status" label="状态:">
          <Select v-model="updateAccount.status" transfer size="large" style="width:100px">
              <Option v-for="item in statusList" :value="item.id" :key="item.id">{{ item.value }}</Option>
          </Select>
        </FormItem>
        <!-- <FormItem prop="roleId" label="角色:">
          <Select v-model="updateAccount.roleId"  transfer size="large" style="width:100px">
              <Option v-for="item in roleList" :value="item.roleId" :key="item.roleId">{{ item.name }}</Option>
          </Select>
        </FormItem> -->
      </Form>
      <div slot='footer'>
        <Button  @click='updateCancel'>取消</Button>
        <Button type='primary' :loading='updateLoading'>
          <span v-if="!updateLoading" @click='updateSure'>确定</span>
          <span v-else>Loading...</span>
        </Button>
      </div>
    </Modal>
    <!--修改end -->
      <Table border :columns='accountColumns' :data='accountList' ref='table' size="small"></Table>
        <div style='display: inline-block;float: right; margin-top:10px;'>
        <Page style='margin-right:10px;' :current="params.currentPage" :total='params.total' :pageSize='params.pageSize' ref='page' :show-total='true'  @on-change='selectPage' show-elevator ></Page>
      </div>
    </div>
</template>
<script>
import twocitylist from '@/components/service/twocitylist'
export default {
  name: 'CarAccount',
  data () {
    return {
      routerPath:this.$route.path,
        params:{
            startNum:1,//初始化个数
            currentPage:1,//当前页
            pageNum:1,//获取的第几个开始
            pageSize:10,//每页的个数
            total:0//总数
        },
        //状态
        statusParamsList:[
          {id:'',value:'全部'},
          {id:0,value:'正常'},
          {id:1,value:'封禁'},
          {id:2,value:'异常'}
          ],
        //性别
        sexList:[
        {id:0,value:'未知'},
        {id:1,value:'男性'},
        {id:2,value:'女性'}
        ],
      //状态
      statusList:[
        {id:0,value:'正常'},
        {id:1,value:'封禁'},
        {id:2,value:'异常'}
        ],
			//增加参数
			addAccountModel:false,
			addLoading:false,
			addAccountRules: {
                phone: [
                    {required: true, message: '手机号为必填项', trigger: 'blur'}
                    ]
                },
			addAccount:{
        icon:'',
        identityCardsHoldImg:'',
        identityCardsFrontImg:'',
        identityCardsBackImg:''
      },
			//修改参数
			updateAccountModel:false,
			updateLoading:false,
			updateAccountRules: {
                phone: [
                    {required: true, message: '手机号为必填项', trigger: 'blur'}
                    ]
                },
			updateAccount:{},
      //删除参数
      deleteAccount:{},
      //列表
	    roleList: [],
        accountList: [],
        
	    accountColumns: [
        {
          title: '序号',
           minWidth:100,
          align:'center',
          render: (h, params) => {
            return h('span', params.index
            +(this.params.currentPage-1)*this.params.pageSize+this.params.startNum);
          }
        },
       /*  {
          title: '账户id',
          key: 'accountId',
          minWidth:100,
          align:'center'
        }, */
         {
        	title:'手机号（登录账户）',
            key:'phone',
            minWidth:100,
          align:'center'
        },
        {
        	title:'姓名',
            key:'realname',
            minWidth:100,
          align:'center'
        },
      /*    {
        	title:'图像',
            key:'icon',
            minWidth:100,
          align:'center',
          render: (h, params) => {
            return h('img', {
              attrs: {
                src: params.row.icon
              },
              style: {
                width: '45px'
              }
            })
          }
        }, */
        {
        	title:'性别',
            key:'sex',
            minWidth:100,
          align:'center',
          render: (h, params) => {
            let sexvalue="";
            this.sexList.forEach(element => {
              if(element.id==params.row.sex){
                sexvalue=element.value;
              }
            });
             return  h('span',sexvalue);
          }
        },
        {
        	title:'状态',
            key:'status',
             minWidth:100,
          align:'center',
          render: (h, params) => {
            let statusvalue="";
            this.statusList.forEach(element => {
              if(element.id==params.row.status){
                statusvalue=element.value;
              }
            });
             return  h('span',statusvalue);
          }
        },
        {
        	title:'创建时间',
          key:'createDate',
           minWidth:100,
          sortable: true,
          align:'center'
        },
        {
        	title:'最后登陆时间',
          key:'loginDate',
           minWidth:100,
          sortable: true,
          align:'center'
        },
				{
          title: '操作',
          key: 'action',
           minWidth:200,
           fixed:'right',
          align:'center',
          render: (h, params) => {
            var marginstyle="2px"
              var varhh11=  h('Button', {
                props: {
                  type: 'primary',
                  size: 'small'
                },
                style: {
                  margin: marginstyle
                },
                on: {
                  click: () => {
                    this.update(params.row)
                  }
                }
              }, '编辑');
            var varhh12=  h('Button', {
                props: {
                  type: 'error',
                  size: 'small'
                },
                style: {
                  margin: marginstyle
                },
                on: {
                  click: () => {
                    this.delete(params.row)
                  }
                }
              }, '删除');
            var varhh21=  h('Button', {
                props: {
                  type: 'info',
                  ghost:'',
                  size: 'small'
                },
                style: {
                  margin: marginstyle
                },
                on: {
                  click: () => {
                      let  pp=JSON.stringify({
                      currentPage:1,//当前页
                      accountId:params.row.accountId
                    });
                    this.$router.push('/main/integral/'+pp);
                  }
                }
              }, '积分');
              var s=h("div","");
    
			s= h("div",[
                  h("div",[
                     varhh11,
                  varhh12,
                  ]),
                  h("div",[
                    varhh21,
                  ]),
                 
                ]);
            return s;
          }
        }
      ],
    }
  },
  methods: {
    //查询
    search(){
      this.params.currentPage=1;
      this.params.pageNum =1;
      this.getList()
    },
    //分页点击
    selectPage (currentPage) {
      this.params.currentPage=currentPage;
      this.params.pageNum = (this.params.currentPage-1)*this.params.pageSize+this.params.startNum;
      this.getList()
    },
  //获取列表
   getRoleList () {

     /**
     * 获取列表
     * $this  vue组件
     * p.countUrl 数量url
     * p.listUrl 列表url
     * p.data 返回列表
     */
    this.params.pageSize=1000000;
     this.axiosbusiness.getList(this,{
       countUrl:'/role/count',
       listUrl:'/role/list',
       data:'roleList',
       success:()=>{
         
        
        let rll=this.roleList.length;
        for(let i=0;i<rll;i++){
            //如果当前管理员非车主
              if(!this.business.getIsCarAdmin()
              &&this.roleList[i].name!="车主"){
                this.roleList.splice(i,1)
              rll--;
              i--;
              }
        }
        if(this.roleList[0] &&this.roleList[0].roleId){
          this.params.roleId=this.roleList[0].roleId;
          this.addAccount.roleId=this.roleList[0].roleId;
        }
        this.getList();
       }
     },
    this.params)
    },
  //获取列表
   getList () {
     /**
     * 获取列表
     * $this  vue组件
     * p.countUrl 数量url
     * p.listUrl 列表url
     * p.data 返回列表
     */
    this.params.pageSize=10
     this.axiosbusiness.getList(this,{
       countUrl:'/account/count',
       listUrl:'/account/list',
       data:'accountList'
     },this.params)
    },
  //增加
	 add (params) {
      this.addAccountModel = true
        //初始化
        this.addAccount={
            icon:'',
            sex:0,
            status:0,
            roleId:this.roleList[0].roleId
        };
      
    },
		//增加取消
		 addCancel () {
      if (!this.addLoading) {
        this.addAccountModel = false
        this.$refs.addAccount.resetFields()
      }
    },
		//增加确定
    addSure () {
       /**
     * 增加
     * $this  vue组件
     * p.ref 验证
     * p.url 增加url
     * p.requestObject 请求参数对象
     * p.loading loading
     * p.showModel 界面模型显示隐藏
     */
    this.axiosbusiness.add(this,{
      ref:'addAccount',
      url:'/account/add',
      requestObject:'addAccount',
      loading:'addLoading',
      showModel:'addAccountModel'
    })
    },
	 update (params) {
      this.updateAccountModel = true
      this.updateAccount.accountId = params.accountId
     //获取修改实体
      this.axiosbusiness.get(this,{
          url:'/account/load?accountId='+params.accountId,
         data:'updateAccount',
         success:()=>{
         }
       })
    },
		//修改取消
		 updateCancel () {
      if (!this.updateLoading) {
        this.updateAccountModel = false
        this.$refs.updateAccount.resetFields()
      }
    },
		//修改确定
    updateSure () {
      /**
     * 修改
     * $this  vue组件
     * p.ref 验证
     * p.url 修改url
     * p.requestObject 请求参数对象
     * p.loading loading
     * p.showModel 界面模型显示隐藏
     */
    delete this.updateAccount.role;
    this.axiosbusiness.update(this,{
      ref:'updateAccount',
      url:'/account/update',
      requestObject:'updateAccount',
      loading:'updateLoading',
      showModel:'updateAccountModel'
    })
 
    },
    //删除
    delete(params){
    /**
     * 删除
     * $this  vue组件
     * p.url 修改url
     * p.requestObject 请求参数对象
     */
    this.deleteAccount={
      "accountId":params.accountId
    };
    this.axiosbusiness.delete(this,{
      url:'/account/delete',
      requestObject:'deleteAccount'
    })
    }
  },
   watch: {
      $route (to,from){
        //console.error(this.routerPath)
        this.routerPath=this.$route.path;
        this.getRoleList();
      }
    },
  created () {
    this.getRoleList();
   
      
  },
  mounted () {

  }
}
</script>
