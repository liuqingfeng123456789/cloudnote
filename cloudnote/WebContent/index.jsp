<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >

<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>尚云主页</title>
	<link href="statics/css/note.css" rel="stylesheet">
	<link href="statics/bootstrap/css/bootstrap.css" rel="stylesheet">
	<link href="statics/sweetalert/sweetalert2.min.css" rel="stylesheet">
	<script src="statics/js/jquery-1.11.3.js"></script>
	<script src="statics/bootstrap/js/bootstrap.js"></script>
	<script src="statics/sweetalert/sweetalert2.min.js"></script>
	<script src="statics/js/util.js"></script>
	<!-- 配置文件 -->
	<script type="text/javascript" src="statics/ueditor/ueditor.config.js"></script>
	<!-- 编辑器源码文件 -->
	<script type="text/javascript" src="statics/ueditor/ueditor.all.js"></script>
	<style type="text/css">
	  body {
	       padding-top: 60px;
	       padding-bottom: 40px;
	       background: url(statics/images/bg.gif) repeat;
	     }
	</style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" style="font-size:25px" href="http://localhost:8080/cloudnote/main">尚云笔记</a>
    </div>
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="active"><a href="index"><i class="glyphicon glyphicon-cloud"></i>&nbsp;主&nbsp;&nbsp;页</a></li>
        <li><a href="note?action=view"><i class="glyphicon glyphicon-pencil"></i>&nbsp;发表云记</a></li>
        <li><a href="type?action=list"><i class="glyphicon glyphicon-list"></i>&nbsp;类别管理</a></li>
        <li><a href="user?action=usercenter"><i class="glyphicon glyphicon-user"></i>&nbsp;个人中心</a>
       
      </li></ul>
      <form class="navbar-form navbar-right" role="search" action="http://localhost:8080/cloudnote/main">
        <div class="form-group">
          <input type="hidden" name="act" value="searchKey">
          <input type="text" name="val" class="form-control" placeholder="搜索云记">
        </div>
        <button type="submit" class="btn btn-default">查询</button>
      </form>      
    </div>
  </div>
</nav>
<div class="container">
	<div class="row-fluid">
		<div class="col-md-3">
			<div class="data_list">
				<div class="data_list_title"><span class="glyphicon glyphicon-user"></span>&nbsp;个人中心&nbsp;&nbsp;&nbsp;&nbsp;<a href="user?action=logout">退出</a></div>
				<div class="userimg">
					<img src="statics/images/${user.head }">
				</div>
				<div class="nick">我思故我在</div>
				<div class="mood">(以后的你会感谢现在努力的你)</div>
			</div>	
			<div class="data_list">
				<div class="data_list_title">
					<span class="glyphicon glyphicon-calendar">
					</span>&nbsp;云记日期 
				</div>
				
				<div>
					<ul class="nav nav-pills nav-stacked">
					 
						<li><a href="http://localhost:8080/cloudnote/main?act=searchDate&amp;val=2016%E5%B9%B408%E6%9C%88&amp;valStr=2016%E5%B9%B408%E6%9C%88">2016年08月 <span class="badge">24</span></a></li>
					 
						<li><a href="http://localhost:8080/cloudnote/main?act=searchDate&amp;val=2016%E5%B9%B407%E6%9C%88&amp;valStr=2016%E5%B9%B407%E6%9C%88">2016年07月 <span class="badge">1</span></a></li>
					 
						<li><a href="http://localhost:8080/cloudnote/main?act=searchDate&amp;val=2016%E5%B9%B406%E6%9C%88&amp;valStr=2016%E5%B9%B406%E6%9C%88">2016年06月 <span class="badge">1</span></a></li>
					 
					</ul>						
				</div>
				
			</div>		
			<div class="data_list">
				<div class="data_list_title">
					<span class="glyphicon glyphicon-list-alt">
					</span>&nbsp;云记类别 
				</div>
				
				<div>
				<c:if test="${resultinfo.code==0 }">
				暂无列表类型
				</c:if>
					<c:if test="${resultinfo.code==1 }">
					<ul id="typelist" class="nav nav-pills nav-stacked">

						<c:forEach items="${resultinfo.result }" var="item">
						
						<li id="li_${item.typeid }"><a href=""><span id="sp_${item.typeid }">${item.typename }</span><span class="badge">9</span></a></li>
						</c:forEach>
					</ul>	
					</c:if>					
				</div>
				
			</div>			
		</div>
	</div>
	
	
	<!-- 动态包含指定页面 -->
	<%-- <jsp:include page="note/list.jsp"></jsp:include> --%>
	
	<%-- <jsp:include page="user/info.jsp"></jsp:include>  --%>
	
	<c:if test="${!empty changepage }">
	<jsp:include page="${changepage }"></jsp:include> 
	</c:if>
	
	
</div>

</body>
</html>