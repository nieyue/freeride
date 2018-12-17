var business={
	//域名路径
	domanurl:'http://localhost:8080',
    /**
     * 验证规则
     */
    userVerification:{
        username:/(^[\u4E00-\u9FA5]{2,16}$)|(^[a-zA-Z\/ ]{2,16}$)/,//中文或英文2-16位
        merDiscount:/^(0\.(0[1-9]{1}|[1-9]\d?)|1(\.0{1,2})?)$/,//0.01-1.00之间
        scale:/^[0]$|^(0\.(0[1-9]{1}|[1-9]\d?)|1(\.0{1,2})?)$/,//0-1.00之间 ，比例
        merPrice:/(^[+]?[1-9]\d*(\.\d{1,2})?$)|(^[+]?[0]{1}(\.\d{1,2})?$)/,//商品价格正则,大于0最多两位小数
        catNum:/^\+?[1-9][0-9]*$/,// 非零正整数
        postNum:/^\+?[0-9][0-9]*$/,// 含零正整数
        nicename: /^[^\s]{1,10}$/,// 1-10位,不包含空格。
        signature:/^[^\s]{1,15}$/,// 1-15位,不包含空格.
        email:/^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+(\.[a-zA-Z]{2,3})+$/, // 邮箱
        phone:/^1[0-9]{10}$/, // 手机
        identity:/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/, // 15位和18位身份证号码
        password:/^[0-9_a-zA-Z]{6,20}$/ // 数字、字母、下划线，6-20长度
    },
	//ajax
	ajax:function(param){
        $.ajax({
            url:business.domanurl+param.url,
            data:param.data||{},
            async:param.async==false?false:true,
            dataType:'json',//服务器返回json格式数据
            type:'post',//HTTP请求类型
            timeout:10000,//超时时间设置为10秒；
            xhrFields: {withCredentials: true},
            success: function(data){
                business.myLoadingToast(data.msg)
                if(data.code==200){
                    if(typeof param.success=='function'){
                        param.success(data);
                    }
                }else{
                    if(typeof param.fail=='function'){
                        param.fail(data);
                    }
				}
            }
        })
	},
    /**
     * 判断是否滑动到底部
     */
    isScrollBottom:function(element){
        var isscrollbottom=false;
        var scrollTop = $(window).scrollTop();
        var scrollHeight = $(document).height();
        var elementHeight = $(element).height();
        var windowHeight = $(window).height();
        if((scrollTop + windowHeight >= scrollHeight-20)||(elementHeight<=windowHeight)){
            isscrollbottom=true;
            // console.log((scrollTop + windowHeight ==
            // scrollHeight)||(elementHeight<=windowHeight))
        }
        return isscrollbottom;
    },
    /**
     * 实现慢事件执行的toast
     */
    myPrevToast : function(value,fn,motion) {
        // 如果存在，remove
        if(document.querySelector("#prevToastWarp")){
            document.querySelector("#prevToastValue").innerText=value;
            if(motion=="add"){
                $("#prevToastWarp").fadeIn();
                $("#prevToastWarp").attr("display","block");
            }else if(motion=="remove"){
                setTimeout(function() {
                    $("#prevToastWarp").fadeOut('slow');
                    $("#prevToastWarp").attr("display","none");
                }, 1000);
            }
            if(typeof fn=='function'){
                fn();
            }
            return;
        }
        $("body")
            .append(
                "<div id='prevToastWarp' style='display:none;position:fixed;width:100%;height:100%;top:0;left:0; z-index:999999999'><div id='prevToast' style='color:#fff;background-color:#000;text-align:center;line-height:30px;border:1px solid black;border-radius:5px;min-height:30px;margin:-15px -50px;top:50%;left:50%;position:fixed;'><canvas id='prevloading'  height='30px' width='30px' style='display:inline-block;margin-bottom:-10px;' >您的浏览器不支持html5</canvas>"
                +"<span id='prevToastValue'>"+ value +"</span>&nbsp;&nbsp; </div></div>");
        if(typeof fn=='function'){
            fn();
        }
    },
    //快速loading
    myLoadingToast:function(value, fn){
        $("body")
            .append(
                "<div id='loadingToast' style='display:none;color:#fff;background-color:black;text-align:center;line-height:30px;border:1px solid black;border-radius:5px;min-height:30px;max-width:200px;padding:0 10px;margin:-5px -"+value.length*7+"px;top:50%;left:50%;position:fixed;z-index:999999999'>"
                + value + "</div>");
        $("#loadingToast").fadeIn();
        setTimeout(function() {
            $("#loadingToast").fadeOut('slow');
            $("#loadingToast").remove();
            if(typeof fn=='function'){
                fn();
            }
        }, 1000);
    },
	//返回
	goback:function(){
		history.back()
	},
	//注册
	goregister:function(){
		location.href="register.html";
	},
	//登录
	gologin:function(){
		location.href="login.html";
	},
	//导航
	gonav:function(param,b){

		//需要验证
		if(b){
            if(!sessionStorage.getItem("account")){
                sessionStorage.clear()
                location.href="login.html"
                return;
            }

			business.ajax({
                url:'/account/islogin',
                success:function(){
                    location.href=param+".html"
                }
            })
			return;
		}
	    location.href=param+".html"

	},
    /**
     * 获取当前url的参数
     *
     */
    getQueryString: function (name)
    {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null)return  decodeURIComponent(r[2]); return null;
        //if(r!=null)return  unescape(r[2]); return null;
    },
};

