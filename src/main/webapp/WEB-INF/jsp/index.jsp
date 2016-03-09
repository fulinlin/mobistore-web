<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/include/taglib.jsp"%>

<html>
<head>
    <meta name="decorator" content="demo" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
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
                    <iframe id="iframepage" src="c/" 
                        frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>
                </div>
            </div>
            <div class="right">
                <h4>开发技术</h4>
                <div class="my-row">
                    <span class="my-col1">前端:</span>
                    <span class="my-col2">AngularJS2.0, Ionic2.0</span>
                </div>
                <div class="my-row">
                    <span class="my-col1">后端:</span>
                    <span class="my-col2">SpringMVC, Hibernate, MySQL</span>
                </div>
                <br />
                
                <h4>联系方式</h4>
                <div class="my-row">
                    <div class="my-col1">QQ:</div>
                    <div class="my-col2">462826</div>
                </div>
                <div class="my-row">
                    <div class="my-col1">Email:</div>
                    <div class="my-col2">
                        <a href="mailto:462826@qq.com">462826@qq.com</a>
                    </div>
                </div>
                <br />
                
                <h4>微信扫二维码访问</h4>
                <div class="my-row">
                    <div class="my-col2">
                        <img src="${ctx}/static/img/bar.png">
                    </div>
                </div>
            
            </div>
        </div>
    </div>
</body>
</html>