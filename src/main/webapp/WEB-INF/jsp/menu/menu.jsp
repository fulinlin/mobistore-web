<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>
<html>
<head>
	<title>菜单导航</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"> 
		$(document).ready(function() {
			$(".accordion-heading a").click(function(){
				$('.accordion-toggle i').removeClass('icon-chevron-down');
				$('.accordion-toggle i').addClass('icon-chevron-right');
				if(!$($(this).attr('href')).hasClass('in')){
					$(this).children('i').removeClass('icon-chevron-right');
					$(this).children('i').addClass('icon-chevron-down');
				}
			});
			$(".accordion-body a").click(function(){
				$("#menu li").removeClass("active");
				$("#menu li i").removeClass("icon-white");
				$(this).parent().addClass("active");
				$(this).children("i").addClass("icon-white");
			});
			$(".accordion-body a:first i").click();
		});
	</script>
</head>
<body>
	<div class="accordion" id="menu">
		<div class="accordion-group">
		    <div class="accordion-heading">
		    	<a class="accordion-toggle" data-toggle="collapse" data-parent="#menu" href="#collapse28" title=""><i class="icon-chevron-down"></i>&nbsp;系统管理</a>
		    </div>
		    <div id="collapse28" class="accordion-body collapse in">
				<div class="accordion-inner">
					<ul class="nav nav-list">
						<c:if test="${admin}">
							<li><a href="${ctx}/user/list" target="mainFrame" ><i class="icon-user"></i>&nbsp;个人客户管理</a></li>
							<li><a href="${ctx}/sys/enterprise" target="mainFrame" ><i class="icon-briefcase"></i>&nbsp;企业客户管理</a></li>
							<li><a href="${ctx}/parkinglot" target="mainFrame" ><i class="icon-road"></i>&nbsp;停车场管理</a></li>
							<li><a href="${ctx}/promotion" target="mainFrame" ><i class="icon-barcode"></i>&nbsp;优惠活动管理</a></li>
							<li><a href="${ctx}/license" target="mainFrame" ><i class="icon-star"></i>&nbsp;车牌管理</a></li>
							<li><a href="${ctx}/sysMessage" target="mainFrame" ><i class="icon-home"></i>&nbsp;推送消息管理</a></li>
						</c:if>
						<c:if test="${admin==null}">
							<li><a href="${ctx}/coupon" target="mainFrame" ><i class="icon-barcode"></i>&nbsp;优惠券管理</a></li>
							<li><a href="${ctx}/sponsorLicense" target="mainFrame" ><i class="icon-star"></i>&nbsp;赞助车牌管理</a></li>
							<li><a href="${ctx}/licenseCategory" target="mainFrame" ><i class="icon-tags"></i>&nbsp;车牌分类管理</a></li>
						</c:if>
					</ul>
				</div>
		    </div>
		</div>
	</div>
</body>
</html>
