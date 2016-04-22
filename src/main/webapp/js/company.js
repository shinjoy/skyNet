var company = {
		getList : function(frm,page) {
			var param = {
					page:page,
					sort:frm.sort.value,
					colName:frm.colName.value,
					keyword:frm.keyword.value
				};

				$.ajax({
				    type:"POST",
				    url:"/admin/company_list.go",
					data:param,
				    dataType:"json",
				
				    success:function(json) {
						var list = json.list;
						var str = '';
						if (list.length > 0) {
							for(var i=0;i<list.length;i++){
								str+= ' <tr > ';
								str+= ' 	<td>'+list[i].companySeq + '</td>';
								str+= ' 	<td class="text-left" onclick="document.location.href=\'/admin/admin/company_view.go?companySeq='+list[i].companySeq+'\';"> '+list[i].companyName + '</td>';
								str+= ' 	<td class="text-left"> '+list[i].companyComment + '</td>';
								str+= ' 	<td>';
								str+= ' 		<button type="button" class="btn" onclick="company.popUpCompany('+list[i].companySeq+');">수정</button>';
								str+= ' 		<button type="button" class="btn" onclick="company.companyDelete('+list[i].companySeq+');">삭제</button>';
								str+= ' 	</td>';
								str+= ' </tr> ';
							}
						} else {
							str = '<tr><td class="list-cell empty">업체가 없습니다.</td></tr>';
						}
				        $(".contents-list").html(str);
				        $(".paging-block").html(json.paging);
				    },
				    error:function(xhr, status, error) {
						console.log("err:",xhr, status, error);
				    },
				    complete:function(data) {
						console.log("complete:",data);
				    }
				});
				return false;
		},	
		
		
	getView : function(frm,page) {
		var param = {
			page:page,
			colName:frm.colName.value,
			sort:frm.sort.value,
			companySeq:frm.companySeq.value
		};

		$.ajax({
			type:"POST",
			url:"/admin/company_view.go",
			data:param,
			dataType:"json",
		
			success:function(json) {
				// 담당자 리스트
				company.renderCompanyList(json.list, json.company.companyName);
				
				// 프로젝트 리스트
				company.renderProjectList(json.prolist, json.paging);
			},
			error:function(xhr, status, error) {
				console.log("err:",xhr, status, error);
			},
			complete:function(data) {
				console.log("complete:",data);
			}
		});
		return false;
	},

	renderProjectList : function(list, paging) {
		var str = '';
		if (list.length > 0) {
			
			for(var i=0;i<list.length;i++){
				str+= ' <tr onclick="projectAdmin.projectEditGo('+list[i].projectSeq+','+list[i].companySeq+');"> ';
				str+= ' 	<td class="text-left"> '+list[i].companyName + '</td>';
				str+= ' 	<td class="text-left"> '+list[i].projectName + '</td>';
				str+= ' 	<td> '+list[i].projectStartDay + '</td>';
				str+= ' 	<td> '+list[i].projectEndDay + '</td>';
				str+= ' 	<td> '+list[i].projectStatusTxt + '</td>';
				str+= ' 	<td> '+list[i].projectRegDate.substring(0,10) + '</td>';
				str+= ' </tr> ';
			}
			
		} else {
			str = '<tr><td class="list-cell empty" colspan="6">프로젝트가 없습니다.</td></tr>';
		}
		
		$(".project-list").html(str);
		$(".paging-block").html(paging);
	},

	renderCompanyList : function(list, companyName) {
		var str = '';
		$(".company-name").html(companyName);
		if (list.length > 0) {
			for(var i=0;i<list.length;i++){
				str+= ' <tr> ';
				str+= ' 	<td> '+list[i].userId + '</td>';
				str+= ' 	<td> '+list[i].userName + '</td>';
				str+= ' 	<td> '+list[i].userGroup + '</td>';
				str+= ' 	<td> '+list[i].userPosition + '</td>';
				str+= ' 	<td> '+list[i].userPhone + '</td>';
				str+= ' 	<td> '+list[i].userEmail + '</td>';
				str+= ' 	<td> '+list[i].userEct + '</td>';
				str+= ' 	<td>';
				str+= ' 		<button type="button" class="btn" onclick="company.popUp('+list[i].companySeq+',\''+list[i].userId+'\');">수정</button>';
				str+= ' 		<button type="button" class="btn" onclick="company.adminDelete(\''+list[i].userId+'\');">삭제</button>';
				str+= ' 	</td>';
				str+= ' </tr> ';
			}
		} else {
			str = '<tr><td class="list-cell empty">업체 담당자가 없습니다.</td></tr>';
		}
		$(".contents-list").html(str);
	},
	
	listOrderCompany : function(colname, sort) {
		dataForm.colName.value = colname;
		dataForm.sort.value = sort;
		company.getList(dataForm, 1);
		var cSort ="asc";
		var point ="▼";
		if(sort=='asc'){
			cSort="desc";
			point="▲";
		}
		
		var str = "<button type='button' class='"+colname+"' onclick=\"company.listOrderCompany('"+colname+"','"+cSort+"');\">"+point+"</button>";
		$("#"+colname).html(str);
	},
	listOrder : function(colname, sort) {
		dataForm.colName.value = colname;
		dataForm.sort.value = sort;
		company.getView(dataForm, 1);
		var cSort ="asc";
		var point ="▼";
		if(sort=='asc'){
			cSort="desc";
			point="▲";
		}
		
		var str = "<button type='button' class='"+colname+"' onclick=\"company.listOrder('"+colname+"','"+cSort+"');\">"+point+"</button>";
		$("#"+colname).html(str);
	},
	popUp : function(companySeq,userId){
		
		pop.openWindow("/admin/admin/company_admin.go?companySeq="+companySeq+"&userId="+userId, 'popup', 450, 480, 'yes', 'yes');
	},
	popUpCompany : function(companySeq){
		
		pop.openWindow("/admin/admin/company.go?companySeq="+companySeq, 'popup', 400, 250, 'yes', 'yes');
	},
	companyEdit : function (frm){

		if(frm.companyName.value==""){
			alert("업체명을 입력해주세요.");
			return false;
		}
	
		
		var param = {
				companyName : frm.companyName.value,
				companyComment : frm.companyComment.value,
				companySeq : frm.companySeq.value
				
			};

			$.ajax({
			    type:"POST",
			    url:"/admin/company_edit_do.go",
				data:param,
			    dataType:"json",
			
			    success:function(json) {
			    	alert(json.message);
			    	if(json.result){
			    		window.opener.company.reloadPage();
				    	window.close();
			    	}
			    },
			    error:function(xhr, status, error) {
					console.log("err:",xhr, status, error);
			    },
			    complete:function(data) {
					console.log("complete:",data);
			    }
			});
			return false;
	},
	adminEdit : function (frm){
		
		if(frm.userId.value==""){
			alert("아이디를 입력해주세요.");
			return false;
		}
		if(frm.userPw.value==""){
			alert("비밀번호를 입력해주세요.");
			return false;		
		}
		if(frm.userName.value==""){
			alert("이름을 입력해주세요.");
			return false;
		}
		
		var param = {
				userId : frm.userId.value,
				userPw : frm.userPw.value,
				userName : frm.userName.value,
				userGroup : frm.userGroup.value,
				userPosition : frm.userPosition.value,
				userPhone : frm.userPhone.value,
				companySeq : frm.companySeq.value,
				userEmail : frm.userEmail.value,
				userEct : frm.userEct.value,
				userType : frm.userType.value,
				userLevel : frm.userLevel.value,
				type : frm.type.value
				
			};

			$.ajax({
			    type:"POST",
			    url:"/admin/user_edit_do.go",
				data:param,
			    dataType:"json",
			
			    success:function(json) {
			    	alert(json.message);
			    	if(json.result){
			    		window.opener.company.reloadPage();
				    	window.close();
			    	}
			    	
			    	
			    	
			    },
			    error:function(xhr, status, error) {
					console.log("err:",xhr, status, error);
			    },
			    complete:function(data) {
					console.log("complete:",data);
			    }
			});
			return false;
		
	},
	adminDelete : function (userId){
		if(confirm("삭제하시겠습니까?")){
			var param = {
					userId : userId,
				};
	
				$.ajax({
				    type:"POST",
				    url:"/admin/user_delete_do.go",
					data:param,
				    dataType:"json",
				
				    success:function(json) {
				    	alert(json.message);
				    	document.location.reload();
				     },
				    error:function(xhr, status, error) {
						console.log("err:",xhr, status, error);
				    },
				    complete:function(data) {
						console.log("complete:",data);
				    }
				});
				return false;
		}
		
	},
	companyDelete : function (seq){
		if(confirm("삭제하시겠습니까?")){
			var param = {
					companySeq : seq,
				};
	
				$.ajax({
				    type:"POST",
				    url:"/admin/company_delete_do.go",
					data:param,
				    dataType:"json",
				
				    success:function(json) {
				    	alert(json.message);
				    	document.location.reload();
				     },
				    error:function(xhr, status, error) {
						console.log("err:",xhr, status, error);
				    },
				    complete:function(data) {
						console.log("complete:",data);
				    }
				});
				return false;
		}
		
	},
	reloadPage : function(){
		document.location.reload();//href='/admin/admin/company_view.go?companySeq='+seq+'&page=1';
	}
};