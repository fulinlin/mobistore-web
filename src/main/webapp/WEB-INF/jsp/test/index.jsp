<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="decorator" content="default1" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>异常信息</title>
</head>

<body>
	<div style="padding: 20px;">
		<H3>接口绑定</H3>
		<form class="form-inline">
			<div class="control-group">
				<!-- <label class="control-label"></label> -->
				<div class="controls" style="margin-left: 0px;">
					<input id="token" value="${token}" />
					<button id="bounfToken" type="submit" class="btn">绑定</button>
				</div>
			</div>
		
		<H3>用户</H3>

			<div class="control-group">
				<!-- <label class="control-label"></label> -->
				<div class="controls">
					<select id="user">
						<c:forEach items="${users}" var="user">
							<option value ="${user.id}">${user.name}</option>
						</c:forEach>
					</select>
					<button id="login" type="submit" class="btn">模拟登录</button>
				</div>
			</div>
		<H3>临时车</H3>

			<div class="control-group">
				<!-- <label class="control-label"></label> -->
				<div class="controls" style="margin-left: 0px;">
					<input id="carToIn" />
					<select id="lotToIn">
						<c:forEach items="${lots}" var="lot">
							<option value ="${lot.id}">${lot.name}</option>
						</c:forEach>
					</select>
					<button id="enter" type="submit" class="btn">入库</button>
				</div>
			</div>

		
		<H3>场外车</H3>

			<div class="control-group">
				<!-- <label class="control-label"></label> -->
				<div class="controls" style="margin-left: 0px;">
					<select id="carToIn">
						<c:forEach items="${carsOutList}" var="car">
							<option value ="${car.id}">${car.carNo}</option>
						</c:forEach>
					</select>
					
					<select id="lotToIn">
						<c:forEach items="${lots}" var="lot">
							<option value ="${lot.id}">${lot.name}</option>
						</c:forEach>
					</select>
					<button id="enter" type="submit" class="btn">入库</button>
				</div>
			</div>

		
		<H3>场内车</H3>

			<div class="control-group">
				<!-- <label class="control-label"></label> -->
				<div class="controls" style="margin-left: 0px;">
					<select id="carToOut">
						<c:forEach items="${carsInList}" var="car">
							<option value ="${car.id}">${car.carNo}</option>
						</c:forEach>
					</select>
					<button type="submit" class="btn">出库</button>
				</div>
			</div>
	</form>
	</div>
	
	<script>
	$(document).ready(function(){
		var userToken = "";
		
		
		var userToken = "";
		$("#login").click(function(){
			var userId = $("#user").val();
			
 			var data = {
            	"phone": userId, 
				"password":"123456"
			};
			$.ajax({
	             type: "POST",
	             url: "/wolai-web/test/login",
	             data: JSON.stringify(data),
				 contentType: "application/json",
	             dataType: "json",
	             success: function(json){
					console.log(json.token);
					userToken = json.token;
	            }
	         });
			
			return false;
		});
		
		$("#enter").click(function(){
			var carToIn = $("#carToIn").val();
			var lotToIn = $("#lotToIn").val();
			
			var data = {
				carToIn: carToIn,
				lotToIn: lotToIn
			}
			
 			console.log(carToIn);
			console.log(lotToIn);
			
			$.ajax({
	             type: "POST",
	             url: "/wolai-web/test/enter",
	             data: JSON.stringify(data),
				 contentType: "application/json",
	             dataType: "json",
	             success: function(json){
					console.log(json.token);
					token = json.token;
					
					window.location.reload();
	            }
	         });
			return false;
		});
	});
  </script>

</body>
</html>