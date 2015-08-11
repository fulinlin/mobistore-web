<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="decorator" content="default1" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>异常信息</title>
	
	 <style type="text/css">
		.controls select, .controls input {
			width: 400px;
		}
	</style>
</head>

<body>
	<div style="padding: 20px;">
		<H3>接口绑定</H3>
		<form action="#" class="form-inline">
			<div class="control-group">
				<!-- <label class="control-label"></label> -->
				<div class="controls" >
					<input id="token" value="b1d4163f9829453d9aeed855b02b4cbc" />
					<input id="url" value="http://101.200.189.57:9090/wolai-web" />
					<button id="boundToken" type="submit" class="btn">绑定</button>
				</div>
			</div>
		
		<H3>用户</H3>

			<div class="control-group">
				<!-- <label class="control-label"></label> -->
				<div class="controls">
					<select id="user">
						<c:forEach items="${users}" var="usr"> 
							<option value ="${usr.mobile}-${usr.password}" 
								<c:if test="${user == usr.id}">selected="selected"</c:if>
								>${usr.name} :   
								<c:if test="${usr.payType == 'PERPAID'}">现付费&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
								<c:if test="${usr.payType == 'CONFIRM_POSTPAID'}">确认后付费</c:if>
								<c:if test="${usr.payType == 'POSTPAID'}">后付费&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</c:if>
								 : ${usr.mobile} / ${usr.password} 
								</option>
						</c:forEach>
					</select>
					<button id="login" type="submit" class="btn">模拟登录</button>
				</div>
			</div>
		<H3>临时车</H3>
			<div class="control-group">
				<!-- <label class="control-label"></label> -->
				<div class="controls" >
					<input id="carToInTemp" />
					<button id="tempEnter" type="submit" class="btn">入库</button>
				</div>
			</div>

		
		<H3>场外车</H3>

			<div class="control-group">
				<!-- <label class="control-label"></label> -->
				<div class="controls" >
					<select id="carToIn">
						<c:forEach items="${carsOutList}" var="car">
							<option value ="${car.carNo}">${car.carNo}</option>
						</c:forEach>
					</select>
					<button id="enter" type="submit" class="btn">入库</button>
				</div>
			</div>

		
		<H3>场内车</H3>

			<div class="control-group">
				<!-- <label class="control-label"></label> -->
				<div class="controls" >
					<select id="carToOut">
						<c:forEach items="${carsInList}" var="car">
							<option value ="${car.carNo}">${car.carNo}
								<c:if test="${car.isPaid == true}"> - 已支付</c:if>
							</option>
						</c:forEach>
					</select>
					<button id="exit" type="submit" class="btn">出库</button>
				</div>
			</div>
	</form>
	</div>
	
	<script>
	var userToken = "";
	
	$(document).ready(function(){
		$("#boundToken").click(function(){
			var sysToken = $("#token").val();
			var url = $("#url").val();
			var data1 = {
				"url":url,
				"token":sysToken
			};
			$.ajax({
	            type: "POST",
	            url: "/wolai-web/test/bound",
	            data: JSON.stringify(data1),
				contentType: "application/json",
	            dataType: "json",
	            success: function(json){
					console.log(json);
					console.log("成功初始化新利泊端安全凭证");
	           }
	        });
			return false;
		});
		
		$("#login").click(function(){
			
			var v = $("#user").val();
			var phone = v.split('-')[0];
			var password = v.split('-')[1];
			
 			var data2 = {
            	"phone": phone, 
				"password":password
			};
			$.ajax({
	             type: "POST",
	             url: "/wolai-web/test/login",
	             data: JSON.stringify(data2),
				 contentType: "application/json",
	             dataType: "json",
	             success: function(json){
					console.log(json.token);
					userToken = json.token;
					window.location.href = newUrl(userToken);
	            }
	         });
			
			return false;
		});
		
		$("#tempEnter").click(function(){
			var carToIn = $("#carToInTemp").val();
			
			if (!carToIn) {
				alert("请填写临时车牌号！");
				return false;
			}
			
			var now = new Date().getTime();
			var data3 = {"carNo": carToIn};
			
			$.ajax({
	             type: "POST",
	             url: "/wolai-web/test/enter",
	             data: JSON.stringify(data3),
				 contentType: "application/json",
	             dataType: "json",
	             success: function(json){
					console.log(json);
					window.location.href = newUrl();   
	            }
	         });
			return false;
		});
		
		$("#enter").click(function(){
			var carToIn = $("#carToIn").val();
			
			var now = new Date().getTime();
			var data3 = {"carNo": carToIn};
			
			$.ajax({
	             type: "POST",
	             url: "/wolai-web/test/enter",
	             data: JSON.stringify(data3),
				 contentType: "application/json",
	             dataType: "json",
	             success: function(json){
					window.location.href = newUrl(); 
	            }
	         });
			return false;
		});
		
		$("#exit").click(function(){
			var carToOut = $("#carToOut").val();
			
			var now = new Date().getTime();
			var data3 = {"carNo": carToOut};
			
			$.ajax({
	             type: "POST",
	             url: "/wolai-web/test/exit",
	             data: JSON.stringify(data3),
				 contentType: "application/json",
	             dataType: "json",
	             success: function(json){
					console.log(json);
					window.location.href = newUrl(); 
	            }
	         });
			return false;
		});
		
		var newUrl = function(token) {
			if (!token) {
				token = getParam("token");
			}
			
			return window.location.href.split("?")[0] + "?r=" + new Date().getTime() + "&token=" + token;
		}
		var getParam = function(name) {
			var reg = new RegExp("(^|&|\\?)" + name + "=([^&]*)(&|$)", "i"); 
			var r = window.location.href.substr(1).match(reg);
			if (r != null) {
				return unescape(r[2]); 
			}
			return null; 
		}
	});
  </script>

</body>
</html>