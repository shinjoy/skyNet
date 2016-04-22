var todo = {
	getList : function(frm) {
		var param = {
			keyword :frm.keyword.value,	
			startDay:frm.startDate.value,
			endDay:frm.endDate.value
			
		};

		$.ajax({
			type:"POST",
			url:"/admin/todo_list.go",
			data:param,
			dataType:"json",
		
			success:function(json) {
				var list = json.list;
				var beforelist = json.beforeList;
				var str = '';
				var beforestr='';
				frm.startDate.value=json.startDay;
				frm.endDate.value=json.endDay;
				if (list.length > 0) {
					
					for(var i=0;i<list.length;i++){
						str+= ' <tr onclick="todo.popUpTodo('+list[i].todoSeq+');"> ';
						str+= ' 	<td class="text-left"> '+list[i].projectName + '</td>';
						str+= ' 	<td class="text-left"> '+list[i].module + '</td>';
						str+= ' 	<td class="text-left"> '+list[i].process + '</td>';
						str+= ' 	<td class="text-left"> '+list[i].todo + '</td>';
						str+= ' 	<td class="text-left"> '+list[i].todoPart + '</td>';
						str+= ' 	<td class="text-left"> '+list[i].todoStartday + '</td>';
						str+= ' 	<td class="text-left"> '+list[i].todoEndday + '</td>';
						str+= ' 	<td class="text-left"> '+list[i].todoStatusTxt + '</td>';
						str+= ' 	<td class="text-left"> '+list[i].todoComment + '</td>';
						str+= ' 	<td class="text-left"> '+list[i].todoFinishday + '</td>';
				
						str+= ' </tr> ';
					}
					
				} else {
					str = '<tr><td colspan="10" class="list-cell empty">일정이 없습니다.</td></tr>';
				}
				if (beforelist.length > 0) {
					
					for(var i=0;i<beforelist.length;i++){
						beforestr+= ' <tr onclick="todo.popUpTodo('+beforelist[i].todoSeq+');"> ';
						beforestr+= ' 	<td class="text-left"> '+beforelist[i].projectName + '</td>';
						beforestr+= ' 	<td class="text-left"> '+beforelist[i].module + '</td>';
						beforestr+= ' 	<td class="text-left"> '+beforelist[i].process + '</td>';
						beforestr+= ' 	<td class="text-left"> '+beforelist[i].todo + '</td>';
						beforestr+= ' 	<td class="text-left"> '+beforelist[i].todoPart + '</td>';
						beforestr+= ' 	<td class="text-left"> '+beforelist[i].todoStartday + '</td>';
						beforestr+= ' 	<td class="text-left"> '+beforelist[i].todoEndday + '</td>';
						beforestr+= ' 	<td class="text-left"> '+beforelist[i].todoStatusTxt + '</td>';
						beforestr+= ' 	<td class="text-left"> '+beforelist[i].todoComment + '</td>';
						beforestr+= ' 	<td class="text-left"> '+beforelist[i].todoFinishday + '</td>';
					
						beforestr+= ' </tr> ';
					}
					
				} else {
					beforestr = '<tr><td class="list-cell empty">일정이 없습니다.</td></tr>';
				}
				$(".now-list").html(str);
				$(".before-list").html(beforestr);
				
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
	reloadPage : function(){
		//document.location.reload();
		todo.getList(dataForm);
	},
	popUpTodo : function(todoSeq){
		
		pop.openWindow("/admin/todo_edit.go?todoSeq="+todoSeq, 'popup', 400, 550, 'yes', 'yes');
	},
	todoEdit : function(frm){
		
		if(frm.todoStatus.value==2){
			if(frm.todoFinishday.value==''){
				alert("완료일을 선택해 주세요.");
				return false;
			}
		}
	
		var param = {
				todoSeq : frm.todoSeq.value,
				todoStatus : frm.todoStatus.value,
				todoComment : frm.todoComment.value,
				todoFinishday : frm.todoFinishday.value
				
			};

			$.ajax({
			    type:"POST",
			    url:"/admin/todo_edit_do.go",
				data:param,
			    dataType:"json",
			
			    success:function(json) {
			    	alert(json.message);
			    	if(json.result){
			    		window.opener.todo.reloadPage();
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
};