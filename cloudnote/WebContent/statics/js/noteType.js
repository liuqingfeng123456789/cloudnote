/**
 * 添加按钮的点击事件
 */
$("#addBtn").click(function(){
	// 设置模态框的标题
	$("#myModalLabel").html("新增类型");
	// 清空表单
	$("#myform")[0].reset();
	var str="";
	$("#typeId").val(str);
	// 禁用保存按钮
	$("#saveBtn").prop("disabled",true);
});

/**
 * 类型名文本框失去焦点事件
 * 	1、得到类型名文本框的值
		如果为空，提示信息
	2、发送ajax请求，验证当前用户下是否存在该类型名
		判断code=1，成功，提示成功；code=0，提示失败
 */
//************************验证typename***************************
$("#typeName").blur(function(){
	// 得到类型名文本框的值
	var typeName = $("#typeName").val();
	var typeId=$("#typeId").val();
	console.log()
	// 判断是否为空
	if (typeName==null || typeName=="") {
		// 提示信息
		swal("类型名称不能为空！","","warning");
		return;
	}
	
	// 发送ajax请求，验证当前用户下是否存在该类型名
	$.ajax({
		url:"type",
		type:"post",
		dataType:"json",
		data:{action:"checkTypeName",typeName:typeName,typeId:typeId},
		success:function(result) {
			if (result.code == 1) { // 成功
				// 提示成功
				swal("类型名称可使用！","","success");
				// 保存按钮解禁
				$("#saveBtn").prop("disabled",false);
			} else {
				//提示失败
				swal("类型名称不可使用！","","error");
				// 禁用保存按钮
				$("#saveBtn").prop("disabled",true);
			}
		}
	});
}).focus(function(){
	// 禁用保存按钮
	$("#saveBtn").prop("disabled",true);
});
//****************************删除**********************
function deleteType (typeId) {
	swal({ 
		  title: "删除类型", 
		  text: "你确定要删除这条记录吗？", 
		  type: "warning",
		  showCancelButton: true, 
		  confirmButtonColor: "#DD6B55",
		  confirmButtonText: "确定删除！"
		}).then(function(){
			
			// 发送ajax请求，删除指定记录
			$.ajax({
				url:"type",
				type:"post",
				dataType:"json",
				data:{action:"delete",typeId:typeId},
				success:function(result) {
					if (result.code ==1 ) { // 删除成功
						swal(result.msg,"","success");
						// 删除表格的DOM操作
						remove(typeId);
						removeli(typeId);
					} else { // 删除失败
						// 提示
						swal(result.msg,"","error");
					}
				}
			});
	    });
}
//******************************移除tr****************************8
function remove(typeId){
	
	var myTable = $("#mytable");
	// 得到table的子对象（tBody）的子对象（tr）
	var trs = myTable.children().children();
	// 判断trs的长度是否大于2 （表头+一条记录）
	if (trs.length > 2 ) { // 当列表存在多条记录时
		// 得到要被删除的tr对象
		$("#tr_"+typeId).remove();
		// 移除tr对象
		
	} else { // 当列表只有一条记录时
		// 移除整个table
		myTable.remove();
		// 添加提示信息
		$("#mydiv").html("<h2 id='myH2'>暂未查询到类型数据！</h2>");
	}
	
}
function removeli(typeId){
	$('#li_'+typeId).remove();
}


//***********************添加保存********************************
function addLi(data){
	var ty=$('#typelist');
	var li = "<li id='li_"+ data.typeid +"'><a href=''><span id='sp_"+data.typeid+"'>"+data.typename+"</span><span class='badge'>0</span></a></li>";
	ty.prepend(li);
}

function updateTr(data){
	var tr = $("#tr_" + data.typeid);
	var tds = tr.children();
	var td = tds.eq(1);
	td.text(data.typename);
}
function updateLi(data){
	$('#sp_'+data.typeid).text(data.typename);
}

$('#saveBtn').click(function(){
	var typeName=$('#typeName').val();
	var typeId=$('#typeId').val();
	console.log("typeid---->"+typeId);
	console.log("typename"+typeName);
	$.ajax({
		url:"type",
		type:"post",
		dataType:"json",
		data:{action:"addtype",typeName:typeName,typeId:typeId},
		success:function(data){
			if(data.code==1){
				$("#myModal").modal("hide");
				swal(data.msg,"","success");
					if(typeId!=null && typeId.trim().length>0){
						//修改操作
						// 修改表格的行的指定单元格
						updateTr(data.result);
						// 修改li中的span的值
						updateLi(data.result);
						
					}
					else{
						//添加操作
						add(data.result);
						//左侧类型的菜单栏
						addLi(data.result);
					}
				
			}else{
				swal(data.msg,"","error");
			}
		}
		
	})
})

function add(data){
	// 创建tr对象
	var tr = "<tr id='tr_"+data.typeid+"'><td>"+data.typeid+"</td><td>"+data.typename+"</td>" 
			+"<td><button class='btn btn-primary' type='button' onclick='updateType("+data.typeid+",\""+data.typename+"\")' >修改</button>&nbsp;" 
			+"<button class='btn btn-danger del' type='button' onclick='deleteType("+data.typeid+")'>删除</button></td></tr>";

	
	// 得到表格对象
	var table = $("#mytable");
	// 判断表格是否存在
	if (table.length == 1) { // 存在表格
		// 在表格的最后追加tr对象
		table.append(tr);
	} else { // 不存在表格
		// 创建表格
		var myTable = "<table id='mytable' class='table table-hover table-striped '>" +
				"<tbody> <tr> <th>编号</th> <th>类型</th> <th>操作</th> </tr>" + tr +
				"</tbody></table>";
		// 移除h2标签
		$("#myH2").remove();
		// 在div中追加myTable
		$("#mydiv").append(myTable);
	}
}



//************************************修改操作*************************************
function updateType(typeId,typeName){
	// 打开模态框
	$("#myModal").modal("show");
	// 修改标题
	$("#myModalLabel").html("修改类型");
	// 填充表单
	// 填充类型名称的文本框
	var tdText = $("#tr_"+typeId).children().eq(1).text();
	//$("#typeName").val(typeName);
	$("#typeName").val(tdText);
	$("#typeId").val(typeId);
}

