<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="col-md-9">

<div class="data_list">
	<div class="data_list_title"><span class="glyphicon glyphicon-edit"></span>&nbsp;个人中心 </div>
	<div class="container-fluid">
	  <div class="row" style="padding-top: 20px;">
	  	<div class="col-md-8">
	  		<form class="form-horizontal"  method="post" action="user?action=updateinfo" enctype="multipart/form-data" >
			  <div class="form-group">
			  	<input type="hidden" name="act" value="save">
			    <label for="nickName" class="col-sm-2 control-label" >昵称:</label>
			    <div class="col-sm-3">
			      <input class="form-control" name="nickname" id="nickname" placeholder="昵称" value="${user.nickname }">
			    </div>
			    <label for="img" class="col-sm-2 control-label">头像:</label>
			    <div class="col-sm-5">
			    	<input type="file" id="img" name="myfile">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="mood" class="col-sm-2 control-label">心情:</label>
			    <div class="col-sm-10">
			      <textarea class="form-control" name="mood" id="mood" rows="3">${user.mood }</textarea>
			    </div>
			  </div>			 
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			      <button type="submit" id="btn" class="btn btn-success" onclick="return checkform()">修改</button>&nbsp;&nbsp;<span style="color:red" id="msg"></span>
			    </div>
			  </div>
			</form>
	  	</div>
  		<div class="col-md-4"><img style="width:260px;height:200px" src="user?action=userheader&imagename=${user.head }"></div>
	  </div>
	</div>	
</div>
<script>
$('#nickname').blur(checknickname);
$('#nickname').focus(check);
function checkform(){
	$('#nickname').blur(checknickname);
	$('#nickname').focus(check);
	if(flag){
		return true;
	}
	return false;
}
		
function checknickname(){
	var flag=true;
	var nickname=$('#nickname').val();
	if(nickname==null || nickname==""){
		$('#msg').html("昵称不能为空");
		$('#btn').prop("disabled",true);
		flag=false;
		return ;
	}
	$('#msg').html("");
	$.ajax({
		url:"user",
		data:{action:"updatenickname",nickname:nickname},
		dataType:"json",
		success:function(result){
			if(result.code==1){
				$('#btn').prop("disabled",false);
				//$('#msg').html(result.msg);
			}else{
				$('#btn').prop("disabled",true);
				flag=false;
				$('#msg').html(result.msg);
			}
		}
	});
}

function check(){
	$('#btn').prop("disabled",false);
	$('#msg').html("");
}
</script>
	</div>	