<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<html>
<head>
	<title>登录</title>
	<meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/common/typica-login.css">
	<style type="text/css">
		.control-group{border-bottom:0px;}
	</style>
    <script src="${ctxStatic}/common/backstretch.min.js"></script>
     <script src="${ctxStatic}/md5/md5.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
		/* 	$.backstretch([
 		      "${ctxStatic}/images/bg1.jpg", 
 		      "${ctxStatic}/images/bg2.jpg",
 		      "${ctxStatic}/images/bg3.jpg"
 		  	], {duration: 10000, fade: 2000}); */
			
			$("#loginForm").validate({
				rules: {
					validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
				},
				messages: {
					username: {required: "请填写用户名."},password: {required: "请填写密码."},
					validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					error.appendTo($("#loginError").parent());
				} 
			});
 		  	
 			$("#password").keyup(function(){
 		  		$("#hiddenPassword").val(hex_md5(hex_md5($("#password").val())));
 		  	});
		});
		// 如果在框架中，则跳转刷新上级页面
		if(self.frameElement && self.frameElement.tagName=="IFRAME"){
			parent.location.reload();
		}
	</script>
</head>
<body>
<%--     <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="${ctx}"><img src="${ctxStatic}/images/logo.png" alt="Jeesite Admin" style="height:40px;"></a>
        </div>
      </div>
    </div> --%>

    <div class="container">
		<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
		<%String error = (String) request.getAttribute("error");%>
		<div id="messageBox" class="alert alert-error <%=error==null?"hide":""%>"><button data-dismiss="alert" class="close">×</button>
			<c:if test="${error!=null}"><label id="loginError" class="error"><%=error==null?"":error%></label></c:if>
		</div>
        <div id="login-wraper">
            <form id="loginForm"  class="form login-form" action="${ctx}/login" method="post">
                <fieldset><legend><span style="color:#08c;">系统登陆</span></legend></fieldset>
                <div class="body">
					<div class="control-group">
						<div class="controls">
							<input type="text" id="username" name="userName" class="required email" value="${userName}" placeholder="登录名">
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<input type="password" id="password"  class="required " rangelength="8,16" placeholder="密码"/>
							<input type="hidden" name="password" id="hiddenPassword"/> 
						</div>
					</div>
					<c:if test="${isValidateCodeLogin}">
						<div class="validateCode">
							<label for="validateCode">验证码：</label>
							<tags:validateCode name="validateCode" inputCssStyle="margin-bottom:0;"/>
						</div>
					</c:if>
                </div>
                <div class="footer">
                    <label class="checkbox inline">
                        <input type="checkbox" id="rememberMe" name="rememberMe"> <span style="color:#08c;">记住我</span>
                    </label>
                    <input class="btn btn-primary" type="submit" value="登 录"/>
                </div>
            </form>
        </div>
    </div>
    <footer class="white navbar-fixed-bottom">
    	<jsp:useBean id="copyrightNow" class="java.util.Date" />
		Copyright &copy; 2015-${fns:getDate('yyyy')} 江苏喔来科技有限公司
    </footer>
  </body>
</html>