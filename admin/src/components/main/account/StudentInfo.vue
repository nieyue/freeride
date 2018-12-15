<!--学生信息管理 -->
<template>
    <div class="body-wrap" style="width:60%;margin:auto">
     <div>
        <h2 style="margin-left:35%;width:30%;text-align:center;display:inline-block;">学生信息</h2>
    </div>
		 <!--修改 -->
    <Form ref="updateStudentInfo" :model="updateStudentInfo" :label-width="100" label-position="right"  :rules="updateStudentInfoRules">
        <FormItem prop="examinationAccount" label="报考账号:">
          <Input type="text" v-model="updateStudentInfo.examinationAccount" placeholder="报考账号">
          </Input>
        </FormItem>
        <FormItem prop="examinationPasword" label="报考密码:">
          <Input type="text" v-model="updateStudentInfo.examinationPasword" placeholder="报考密码">
          </Input>
        </FormItem>
        <FormItem prop="recommender" label="推荐人:">
          <Input type="text" v-model="updateStudentInfo.recommender" placeholder="推荐人">
          </Input>
        </FormItem>
        <FormItem prop="directorUnit" label="报送主管单位:">
          <Input type="text" v-model="updateStudentInfo.directorUnit" placeholder="报送主管单位">
          </Input>
        </FormItem>
        <FormItem prop="totalCost" label="报送单位收费金额:">
          <InputNumber  :max="10000000000" :min="0" :step="0.01"  style="width:180px" 
          v-model="updateStudentInfo.unitCharge" placeholder="报送单位收费金额">
          </InputNumber >
        </FormItem>
        <FormItem prop="cerQueryWebsite" label="证书查询认证网址:">
          <Input type="text" v-model="updateStudentInfo.cerQueryWebsite" placeholder="证书查询认证网址">
          </Input>
        </FormItem>
        <FormItem prop="link" label="链接:">
          <Input type="text" v-model="updateStudentInfo.link" placeholder="链接">
          </Input>
        </FormItem>
        <FormItem prop="inputTeacher" label="录入老师姓名:">
          <Input type="text" v-model="updateStudentInfo.inputTeacher" placeholder="录入老师姓名">
          </Input>
        </FormItem>
        <FormItem  label="入学年份:">
          <DatePicker type="year"  v-model="updateStudentInfo.enrollmentYear" placeholder="入学年份" style="width: 300px"></DatePicker>
        </FormItem>
        <FormItem prop="examinationSchool" label="报考院校:">
          <Input type="text" v-model="updateStudentInfo.examinationSchool" placeholder="报考院校">
          </Input>
        </FormItem>
        <FormItem prop="examinationPoint" label="考点:">
          <Input type="text" v-model="updateStudentInfo.examinationPoint" placeholder="考点">
          </Input>
        </FormItem>
        <FormItem prop="sectionType" label="科类:">
          <Input type="text" v-model="updateStudentInfo.sectionType" placeholder="科类">
          </Input>
        </FormItem>
        <FormItem prop="learnMethod" label="学习形式:">
          <Input type="text" v-model="updateStudentInfo.learnMethod" placeholder="学习形式">
          </Input>
        </FormItem>
        <FormItem prop="recruiter" label="代理（招生员）:">
          <Input type="text" v-model="updateStudentInfo.recruiter" placeholder="代理（招生员）">
          </Input>
        </FormItem>
        <FormItem prop="regPlace" label="注册地:">
          <Input type="text" v-model="updateStudentInfo.regPlace" placeholder="注册地">
          </Input>
        </FormItem>
        <FormItem prop="regMoney1" label="第1年注册(金额):">
          <InputNumber  :max="10000000000" :min="0" :step="0.01"  style="width:180px" 
          v-model="updateStudentInfo.regMoney1" placeholder="第1年注册(金额)">
          </InputNumber >
        </FormItem>
        <FormItem prop="regMoney2" label="第2年注册(金额):">
          <InputNumber  :max="10000000000" :min="0" :step="0.01"  style="width:180px" 
          v-model="updateStudentInfo.regMoney2" placeholder="第2年注册(金额)">
          </InputNumber >
        </FormItem>
        <FormItem prop="regMoney3" label="第3年注册(金额):">
          <InputNumber  :max="10000000000" :min="0" :step="0.01"  style="width:180px" 
          v-model="updateStudentInfo.regMoney3" placeholder="第3年注册(金额)">
          </InputNumber >
        </FormItem>
        <FormItem  label="毕业时间:">
          <DatePicker type="datetime"  v-model="updateStudentInfo.graduationDate" placeholder="毕业时间" style="width: 300px"></DatePicker>
        </FormItem>
        <FormItem prop="mark" label="备注:">
          <Input type="textarea" v-model="updateStudentInfo.mark" placeholder="备注">
          </Input>
        </FormItem>
      </Form>
      <div slot='footer'>
        <Button type='primary' :loading='updateLoading' size="large"  @click='updateSure' style="width:40%;margin-left:30%;">
          <span v-if="!updateLoading">保存</span>
          <span v-else>Loading...</span>
        </Button>
      </div>
    <!--修改end -->
    </div>
</template>
<script>
export default {
  name: 'StudentInfo',
  data () {
    return {
        params:{
            startNum:1,//初始化个数
            currentPage:1,//当前页
            pageNum:1,//获取的第几个开始
            pageSize:10,//每页的个数
            total:0//总数
        },
			//修改参数
			updateLoading:false,
			updateStudentInfoRules: {
                examinationAccount: [
                    {required: true, message: '报考账号必填', trigger: 'blur'}
                    ]
                },
			studentInfoList:[],
			updateStudentInfo:{
                unitCharge:0,
                regMoney1:0,
                regMoney2:0,
                regMoney3:0,
             },
    }
  },
  methods: {
	//获取列表
   getList () {
     /**
     * 获取列表
     * $this  vue组件
     * p.countUrl 数量url
     * p.listUrl 列表url
     * p.data 返回列表
     */
    this.params.accountId=JSON.parse(this.$route.params.pathParams).accountId
     this.axiosbusiness.getList(this,{
       countUrl:'/studentInfo/count',
       listUrl:'/studentInfo/list',
       data:'studentInfoList',
       success:()=>{
           if(this.studentInfoList.length>0){
               this.updateStudentInfo=this.studentInfoList[0]
           }
           
       }
     },this.params)
    },
		//修改确定
    updateSure () {
     this.updateStudentInfo.accountId=JSON.parse(this.$route.params.pathParams).accountId
     this.updateStudentInfo.enrollmentYear=this.utils.getYear(this.updateStudentInfo.enrollmentYear)
     this.updateStudentInfo.graduationDate=this.utils.getTime(this.updateStudentInfo.graduationDate)
    if(this.studentInfoList.length<=0){
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
      ref:'updateStudentInfo',
      url:'/studentInfo/add',
      requestObject:'updateStudentInfo',
      loading:'updateLoading',
    })
    }else{
         /**
     * 修改
     * $this  vue组件
     * p.ref 验证
     * p.url 修改url
     * p.requestObject 请求参数对象
     * p.loading loading
     * p.showModel 界面模型显示隐藏
     */
    this.axiosbusiness.update(this,{
      ref:'updateStudentInfo',
      url:'/studentInfo/update',
      requestObject:'updateStudentInfo',
      loading:'updateLoading',
       success:()=>{}
    })
    }
    
 
    },

  },
  created () {
         this.getList()
  },
  mounted () {

  }
}
</script>
