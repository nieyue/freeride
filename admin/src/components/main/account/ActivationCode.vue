<!--激活码管理 -->
<template>
    <div class="body-wrap">
        <div class="body-btn-wrap">
            <Button type='primary'  @click='add'>批量增加</Button>
        </div>
          <Modal v-model="addActivationCodeModel"
           title="修改密码"
           :closable="false"
           :mask-closable="false"
    >
    <Form ref="addActivationCode" :model="addActivationCode" :label-width="100" label-position="right"  :rules="addActivationCodeRules">
        <FormItem prop="number" label="数目:">
          <InputNumber   :max="10000000" :min="1" :step="1" style="width:180px" v-model="addActivationCode.number"
            placeholder="数目">
          </InputNumber >
        </FormItem>
      </Form>
      <div slot='footer'>
        <Button  @click='addActivationCodeCancel'>取消</Button>
        <Button type='primary' :loading='addActivationCodeLoading'>
          <span v-if="!addActivationCodeLoading" @click='addActivationCodeSure'>确定</span>
          <span v-else>Loading...</span>
        </Button>
      </div>
    </Modal>

      <Table border  :columns='activationCodeColumns' :data='activationCodeList' ref='table' size="small"></Table>
        <div style='display: inline-block;float: right; margin-top:10px;'>
        <Page style='margin-right:10px;'  @on-page-size-change="onPageSizeChange" show-sizer :total='params.total' :pageSize='params.pageSize' ref='page' :show-total='true'   @on-change='selectPage' show-elevator ></Page>
      </div>
    </div>
    
</template>
<script>
export default {
  name: 'ActivationCode',
  data () {
    return {
        params:{
            startNum:1,//初始化个数
            currentPage:1,//当前页
            pageNum:1,//获取的第几个开始
            pageSize:10,//每页的个数
            total:0//总数
        },
        //是否使用，1未使用，2已使用
      isUseredList:[
        {id:1,value:'未使用'},
        {id:2,value:'已使用'}
        ],
        //增加激活码
			addActivationCodeModel:false,
			addActivationCodeLoading:false,
			addActivationCodeRules: {
                number: [
                    {type:'number',required: true, message: '数目必填', trigger: 'change'}
                    ],
                },
			addActivationCode:{ 
                number:1000
            },
	    activationCodeList: [],
	    activationCodeColumns: [
        {
          title: '序号',
          align:'center',
          render: (h, params) => {
            return h('span', params.index
            +(this.params.currentPage-1)*this.params.pageSize+this.params.startNum);
          }
        },
        {
          title: '激活码id',
          key: 'activationCodeId',
          align:'center'
        },
        {
        	title:'激活码',
            key:'code',
            align:'center'
        },
         {
          title:'是否使用',
          minWidth:100,
            key:'isUsered',
          align:'center',
          render: (h, params) => {
              
            let isUseredvalue="";
            this.isUseredList.forEach(element => {
              if(element.id==params.row.isUsered){
                isUseredvalue=element.value;
              }
            });
             return  h('span',isUseredvalue);
          }
        },
        {
          title:'创建时间',
          key:'createDate',
          sortable: true,
          align:'center'
        },
        {
          title:'修改时间',
          key:'updateDate',
          sortable: true,
          align:'center'
        }
      ],
    }
  },
  methods: {
    //分页点击
    selectPage (currentPage) {
      this.params.currentPage=currentPage;
      this.params.pageNum = (this.params.currentPage-1)*this.params.pageSize+this.params.startNum;
      //构造path
     let pp=JSON.stringify({
       currentPage:currentPage,
       accountId:JSON.parse(this.$route.params.pathParams).accountId,
     })
      this.$router.push(this.$route.path.substr(0,this.$route.path.indexOf(this.$route.params.pathParams))+pp);
      this.getList()
    },
    //切换每页条数时的回调，返回切换后的每页条数
    onPageSizeChange(a){
      this.params.pageSize=a;
      this.selectPage(1)
    },
  //获取列表
   getList () {
     /**
     * 获取列表
     * $this  vue组件
     * p.countUrl 数量url
     * p.listUrl 列表url
     * p.list 返回列表
     */
    this.params.accountId=JSON.parse(this.$route.params.pathParams).accountId
     this.axiosbusiness.getList(this,{
       countUrl:'/activationCode/count',
       listUrl:'/activationCode/list',
       data:'activationCodeList'
     },this.params)
    },
    ////增加激活码
        add (params) {
      this.addActivationCodeModel = true

    },
		//取消
		 addActivationCodeCancel () {
      if (!this.addActivationCodeLoading) {
        this.addActivationCodeModel = false
        this.$refs.addActivationCode.resetFields()
      }
    },
		//确定
    addActivationCodeSure () {
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
      ref:'addActivationCode',
      url:'/activationCode/addBatch',
      requestObject:'addActivationCode',
      loading:'addActivationCodeLoading',
      showModel:'addActivationCodeModel'
    })

    },
  },
      watch: {
    //当前页面参数修改动态启动
      $route (to,from){
        this.selectPage(JSON.parse(this.$route.params.pathParams).currentPage)
      }
    }, 
  created () {
     this.selectPage(JSON.parse(this.$route.params.pathParams).currentPage)
  },
  mounted () {
  }
}
</script>