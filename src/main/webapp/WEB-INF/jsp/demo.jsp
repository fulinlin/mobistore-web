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
				<div class="iframe-container">
					<iframe id="iframepage" src="http://10.0.1.100:8100" 
						frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>
				</div>
			</div>
			<div class="right">
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
					<span class="my-col2">
						<a href="mailto:462826@qq.com">462826@qq.com</a>
					</span>
				</div>
				<br />
				
				<h4>二维码</h4>
				<img src="${ctx}/static/img/bar.png">
			
			</div>
		</div>
	</div>
</body>
</html>
