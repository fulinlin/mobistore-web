<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<%@include file="/WEB-INF/jsp/include/head.jsp" %>

<html>
<head>
	<title>mobistore demo</title>
	<style type="text/css">

	</style>
	<script type="text/javascript"> 
		
	</script>
</head>
<body>
	<div class="main">
		<div class="header">
			<div class="line">
				<div class="left">
						移动商店
				</div>
			</div>
		</div>
		
		<div class="content">
			<div class="left">
				<h4>使用技术</h4>
				<div>
					<span class="my-col1">前端：</span>
					<span class="my-col2">Ionic, AngularJS</span>
				</div>
				<div>
					<span class="my-col1">后端：</span>
					<span class="my-col2">SpringMVC, Hibernate, MySQL</span>
				</div>
				<br />
				
				<h4>联系方式</h4>
				<div>
					<span class="my-col1">QQ：</span>
					<span class="my-col2">462826</span>
				</div>
				<div>
					<span class="my-col1">Email：</span>
					<span class="my-col2">462826@qq.com</span>
				</div>
				<br />
				
				<h4>二维码</h4>
				<img src="../static/img/bar.png">
			</div>
			<div class="right">
				<div class="iframe-container">
					<iframe id="iframepage" src="http://101.200.189.57" 
						frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>
				</div>
			</div>
		</div>
	</div>
</body>
</html>