<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>用户登录</title>
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">

<link rel="stylesheet" type="text/css" href="/libs/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="/libs/bootstrap/css/bootstrap-responsive.css">
<link rel="stylesheet" href="/libs/font-awesome/css/font-awesome.css">
<link rel="stylesheet" type="text/css" href="/libs/css/theme.css">
<script src="/libs/js/jquery.js" type="text/javascript"></script>

<style type="text/css">
.brand {
	font-family: georgia, serif;
}

.brand .first {
	color: #ccc;
	font-style: italic;
}

.brand .second {
	color: #fff;
	font-weight: bold;
}
</style>

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

</head>

<!--[if lt IE 7 ]> <body class="ie ie6"> <![endif]-->
<!--[if IE 7 ]> <body class="ie ie7"> <![endif]-->
<!--[if IE 8 ]> <body class="ie ie8"> <![endif]-->
<!--[if IE 9 ]> <body class="ie ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<body>
	<!--<![endif]-->

	<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">
				<ul class="nav pull-right">
				</ul>
				<a class="brand" href="index.jsp"><span class="first">喔来</span>
					<span class="second">智能停车系统</span></a>
			</div>
		</div>
	</div>

	<div class="container-fluid">

		<div class="row-fluid">
			<div class="dialog span4">
				<div class="block">
					<div class="block-heading">登&nbsp;陆</div>
					<div class="block-body">
						<form>
							<label>邮箱：</label> <input type="text" class="span12" name="email">
							<label>密码：</label> <input type="password" class="span12"
								name="password"> <a href="javascript:void(0);"
								class="btn btn-primary pull-right">登陆</a> <label
								class="remember-me"><input type="checkbox">记住我</label>
							<div class="clearfix"></div>
						</form>
					</div>
				</div>
				<!-- <p class="pull-right" style=""><a href="#" target="blank">Theme by Portnine</a></p> -->
				<p>
					<a class="reset-password" href="">忘记密码</a>
				</p>
			</div>
		</div>
	</div>
	<script src="/libs/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>

