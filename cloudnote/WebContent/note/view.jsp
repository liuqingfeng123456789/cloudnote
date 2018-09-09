<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="col-md-9">
	<div class="data_list">
		<div class="data_list_title">
				<span class="glyphicon glyphicon-cloud-upload"></span>&nbsp;发布云记
		 </div>
		<div class="container-fluid">
			<div class="container-fluid">
			  <div class="row" style="padding-top: 20px;">
			  	<div class="col-md-12">
			  		<c:if test="${empty typeList || typeList.size() < 1 }">
			  			<h2>请先添加云记类型！</h2>
			  			<h3><a href="type?action=list">添加类型</a></h3>
			  		</c:if>
			  		
			  		<c:if test="${!empty typeList && typeList.size() > 0}">
				  		<form class="form-horizontal" method="post" action="note">
				  		   <div class="form-group">
						    <label for="typeId" class="col-sm-2 control-label">类别:</label>
						    <div class="col-sm-8">
						    	<select id="typeId" class="form-control" name="typeId">
									<option value="">请选择云记类别...</option>
									<c:forEach items="${typeList }" var="item">
										<option value="${item.typeid }">${item.typename }</option>
									</c:forEach>
								</select>
						    </div>
						  </div>
						  <div class="form-group">
						  	<input type="hidden" name="noteId" value="28">
						  	<input type="hidden" name="act" value="save">
						    <label for="title" class="col-sm-2 control-label">标题:</label>
						    <div class="col-sm-8">
						      <input class="form-control" name="title" id="title" placeholder="云记标题" value="">
						    </div>
						   </div>
						  
						  <div class="form-group">
						   	<label for="noteUeditor" class="col-sm-2 control-label">内容:</label>
						    <div class="col-sm-8">
						    	<textarea id="noteUeditor" name="content"></textarea>
						    </div>
						  </div>			 
						  <div class="form-group">
						    <div class="col-sm-offset-6 col-sm-4">
						      <input type="submit" class="btn btn-primary" onclick="return saveNote();" value="保存">
								<font id="error" color="red"></font>  
						    </div>
						  </div>
						</form>
					</c:if>
			  	</div>
			  </div>
		</div>	
	</div>		
</div>
<script>
$(function(){
	//UE.getEditor('noteEditor');
	/*  var editor = new UE.ui.Editor({initialFrameHeight:'100%',initialFrameWidth:'100%'});  
     editor.render("noteEditor");   */
    
     console.log('${!empty typeList && typeList.size() > 0}');
     // 在js中想要使用el表达式，需要加引号   
     // 判断是否有类型列表
     if ('${!empty typeList && typeList.size() > 0}') {
    	 // 实例化编辑器
    	  var ue = UE.getEditor('noteUeditor');
     }
   
});
//验证
function saveNote(){
	//验证非空
	
	return true;
}
</script>

	</div>	