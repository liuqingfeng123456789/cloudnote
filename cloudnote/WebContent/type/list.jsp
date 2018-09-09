<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="col-md-9">
	<div class="data_list">
		<div class="data_list_title">
			<span class="glyphicon glyphicon-list"></span>&nbsp;类型列表
			<span class="noteType_add">
				<button class="btn btn-sm btn-success" type="button" id="addBtn"  data-toggle="modal" data-target="#myModal" >添加类别</button>
			</span>
		 </div>
		<div id="mydiv">
		<c:if test="${resultinfo.code == 0 }">
			<h2 id="myH2">${resultinfo.msg }</h2>
		</c:if>
		<c:if test="${resultinfo.code == 1 }">
		 	<table id="mytable" class="table table-hover table-striped ">
		 		<tbody id="tbody">
			 		<tr>
			 			<th>编号</th>
			 			<th>类型</th>
			 			<th>操作</th>
			 		</tr>
			 		<c:forEach items="${resultinfo.result}" var="item">
				 		<tr id="tr_${item.typeid}">
				 			<td>${item.typeid }</td>
				 			<td>${item.typename }</td>
				 			<td>
					 			<button class="btn btn-primary" type="button" onclick="updateType(${item.typeid},'${item.typename }')">修改</button>&nbsp;
					 			<button class="btn btn-danger del" type="button" onclick="deleteType(${item.typeid})">删除</button>
				 			</td>
				 		</tr>
					</c:forEach>	
				</tbody>
			</table>
		</c:if>
		</div>	
	</div>
</div>		


<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					类型管理
				</h4>
			</div>
			<div class="modal-body">
				<form id="myform">
					<div class="form-group">
					<input type="hidden" class="form-control" id="typeId" >
					    <label for="typeName">类型名称</label>
					     <input type="text" class="form-control" id="typeName" placeholder="类型名称">
					 </div>
				</form>  
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭
				</button>
				<button type="button" class="btn btn-primary" id="saveBtn">
					保存
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

<script type="text/javascript" src="statics/js/noteType.js"></script>
