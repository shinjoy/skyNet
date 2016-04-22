var projectMenu = {
	getProgress : function(frm) {
		var param = {
				projectSeq : frm.projectSeq.value
		};

		$.ajax({
			type:"POST",
			url:"/proc/project_view.go",
			data:param,
			dataType:"json",
		
			success:function(json) {
				var project = json.project;
				var projectComment = json.projectComment;
				frm.commentSeq.value=projectComment.commentSeq;
				$(".project_name").html(project.projectName);
				
				if (project.designPercent != undefined) {
					if (project.designPercent > -1) {
						$("#design-part").css("display","block");
						$("#design-part .progress").css("width",project.designPercent);
						$("#design-part .precent").html(project.designPercent);
						$("#designComment").val(projectComment.designComment);
					} else {
						$("#design-part").css("display","none");
						
					}
					
				}
				if (project.iosPercent != undefined) {
					if (project.iosPercent > -1) {
						$("#ios-part").css("display","block");
						$("#ios-part .progress").css("width",project.iosPercent);
						$("#ios-part .precent").html(project.iosPercent);
						$("#iosComment").val(projectComment.iosComment);
					} else {
						$("#ios-part").css("display","none");
					
					}
				}
				if (project.andPercent != undefined) {
					if (project.andPercent > -1) {
						$("#android-part").css("display","block");
						$("#android-part .progress").css("width",project.andPercent);
						$("#android-part .precent").html(project.andPercent);
						$("#andComment").val(projectComment.andComment);
					} else {
						$("#android-part").css("display","none");
						
					}
				}
				if (project.webPercent != undefined) {
					if (project.webPercent > -1) {
						$("#web-part").css("display","block");
						$("#web-part .progress").css("width",project.webPercent);
						$("#web-part .precent").html(project.webPercent);
						$("#webComment").val(projectComment.webComment);
					} else {
						$("#web-part").css("display","none");
						
					}
				}
				if (project.serverPercent != undefined) {
					if (project.serverPercent > -1) {
						$("#server-part").css("display","block");
						$("#server-part .progress").css("width",project.serverPercent);
						$("#server-part .precent").html(project.serverPercent);
						$("#serverComment").val(projectComment.serverComment);
					} else {
						$("#server-part").css("display","none");
					
					}
					
				}
				if (project.pcPercent != undefined) {
					if (project.pcPercent > -1) {
						$("#pc-part").css("display","block");
						$("#pc-part .progress").css("width",project.pcPercent);
						$("#pc-part .precent").html(project.pcPercent);
						$("#pcComment").val(projectComment.pcComment);
					} else {
						$("#pc-part").css("display","none");
					
					}
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
	
	saveComment : function(frm){
		
	
	
		var param = {
				projectSeq : frm.projectSeq.value,
				commentSeq : frm.commentSeq.value,
				designComment : frm.designComment.value,
				iosComment : frm.iosComment.value,
				andComment : frm.andComment.value,
				webComment : frm.webComment.value,
				serverComment : frm.serverComment.value,
				pcComment : frm.pcComment.value
				
			};

			$.ajax({
			    type:"POST",
			    url:"/admin/comment_edit_do.go",
				data:param,
			    dataType:"json",
			
			    success:function(json) {
			    	alert(json.message);
			    	if(json.result){
			    		document.location.reload();
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
	getList : function(frm,page) {
		var param = {
			bbsType:frm.bbsType.value,//1요청2버그
			bbsType2:frm.bbsType2.value,//빈값 둘다,0전체 1내부공개
			projectSeq:frm.projectSeq.value,
			page:page,
			sort:frm.sort.value,
			colName:frm.colName.value,
			keyword:frm.keyword.value,
		};

		$.ajax({
		    type:"POST",
		    url:"/proc/bbs_list.go",
			data:param,
		    dataType:"json",
		
		    success:function(json) {
				var list = json.list;

				$(".project_name").html(json.project.projectName);
				var strpaging='';
				frm.bbsType.value = json.bbsType;
				frm.projectSeq.value = json.projectSeq;
				var str = '';
				if (list.length > 0) {
					for(var i=0;i<list.length;i++){
						str+='<tr onclick="link.go(\'/admin/bbs_view.go?projectSeq='+frm.projectSeq.value+'&bbsSeq='+list[i].bbsSeq+'&page=1\');"> ';
						str+='	<td class="text-left"> ';
						str+='	'+list[i].bbsSeq ;
						str+='	</td> ';
						str+='	<td class="text-left"> ';
						if (list[i].statusTxt == '대기') {
							str+=' 		<span class="status-box status0">'+list[i].statusTxt+'</span>';
				    	} else if (list[i].statusTxt == '접수') {
							str+=' 		<span class="status-box status1">'+list[i].statusTxt+'</span>';
				    	} else if (list[i].statusTxt == '진행') {
							str+=' 		<span class="status-box status2">'+list[i].statusTxt+'</span>';
				    	} else if (list[i].statusTxt == '완료') {
							str+=' 		<span class="status-box status4">'+list[i].statusTxt+'</span>';
				    	} else if (list[i].statusTxt == '보류') {
							str+=' 		<span class="status-box status3">'+list[i].statusTxt+'</span>';
				    	} else if (list[i].statusTxt == '취소') {
							str+=' 		<span class="status-box status5">'+list[i].statusTxt+'</span>';
				    	}
					
						//str+='			<p class="contents" onclick="link.go(\'/m/bbs_view.go?projectSeq='+frm.projectSeq.value+'&bbsSeq='+list[i].bbsSeq+'&page=1\');">';
						str+='	</td> ';
						str+='	<td class="text-left"> ';
						str+='				'+list[i].bbsType2Txt+'';
						str+='	</td> ';
						str+='	<td class="text-left"> ';
						str+='				'+list[i].bbsTypeTxt+'';
						str+='	</td> ';
						str+='	<td class="text-left"> ';
						str+=' '+list[i].bbsTitle;
					
						str+=' ('+list[i].commentCount+') ';
						str+='	</td> ';
						str+=' <td class="text-left"> ';
						str+='			'+list[i].userName;
						str+=' </td> ';
						str+='	<td class="text-left"> ';
						str+='	 '+list[i].bbsRegDate.substr(0,16);
						str+='	</td> ';
						str+='</tr> ';
					}
					
				} else {
					str = '<tr><td colspan="6">검색된 게시글이 없습니다.</td></tr>';
					  
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
	listOrder : function(colname, sort) {
		searchForm.colName.value = colname;
		searchForm.sort.value = sort;
		projectMenu.getList(searchForm, 1);
		var cSort ="asc";
		var point ="▼";
		if(sort=='asc'){
			cSort="desc";
			point="▲";
		}
		
		var str = "<button type='button' class='"+colname+"' onclick=\"projectMenu.listOrder('"+colname+"','"+cSort+"');\">"+point+"</button>";
		$("#"+colname).html(str);
		
		
	},
	
	view : function(frm,page) {
		var param = {
			projectSeq	: frm.projectSeq.value,
			bbsSeq		: frm.bbsSeq.value,
			page		: page,
		};
		

		$.ajax({
		    type:"POST",
		    url:"/proc/bbs_view.go",
			data:param,
		    dataType:"json",
		
		    success:function(json) {
				var bbs = json.bbs;
				var commentlist = json.list;
				var fileList = json.fileList;
				$(".project_name").html(json.project.projectName);
				var str = ' <dt>';
				if (bbs.statusTxt == '대기') {
					str+=' 		<span class="status-box status0">'+bbs.statusTxt+'</span>';
				} else if (bbs.statusTxt == '접수') {
					str+=' 		<span class="status-box status1">'+bbs.statusTxt+'</span>';
				} else if (bbs.statusTxt == '진행') {
					str+=' 		<span class="status-box status2">'+bbs.statusTxt+'</span>';
				} else if (bbs.statusTxt == '완료') {
					str+=' 		<span class="status-box status4">'+bbs.statusTxt+'</span>';
				} else if (bbs.statusTxt == '보류') {
					str+=' 		<span class="status-box status3">'+bbs.statusTxt+'</span>';
				} else if (bbs.statusTxt == '취소') {
					str+=' 		<span class="status-box status5">'+bbs.statusTxt+'</span>';
				}

				str+=' </dt> ';
				str+=' <dd> ';
				
				str+='	<p class="info">['+bbs.bbsTypeTxt+'] '+bbs.userName+' | '+bbs.bbsRegDate.substr(0,16)+'</p> ';
				str+='	<p >'+bbs.bbsTitle+'</p> ';
				str+='	<p >'+bbs.bbsContents.split("\n").join("<br>")+'</p> ';
				str+='	<p class="images"> ';
				if(fileList.length >0){
					for(var i =0;i<fileList.length;i++) {
						var file = fileList[i].fileName;
						var filename= file.substr(file.lastIndexOf('/')+1);
						if(fileList[i].fileExt=='img')	{
							str+='	<img src="/files'+file+'"><br> ';
						}
						str+=' <a href="/fileDownload.go?fileName='+file+'" class="download"><img src="/images/ic_file_download_black_18dp_1x.png">'+filename+'</a>';
					}
				}
				str+='	</p> ';
				if(bbs.userId==userForm.userId.value){
					str+='	<div class="bbs-menu"> ';
					
					str+='		<button type="button" class="bbs-btn" onclick="bbs.goBbsEdit(pageForm,'+bbs.bbsSeq+',\'admin\');">수정</button> ';
					str+='		<button type="button" class="bbs-btn" onclick="bbs.onBbsDelete('+bbs.bbsSeq+',0,pageForm,\'admin\');">삭제</button> ';
					str+='	</div> ';
				}
				str+='	</dd> ';
				
				var commentstr='';	
		        if(commentlist.length>0){
		        	for(var i=0;i<commentlist.length;i++){
		        		commentstr+=' <li> ';
		        		commentstr+=' <dl class="bbs"> ';
		        		commentstr+='	 <dt><span class="step-box step'+commentlist[i].rAnswerStatus+'"><img src="/images/ic_reply.png"><br>'+commentlist[i].rAnswerStatusTxt+'</span></dt> ';
		        		commentstr+='	 <dd> ';
		        		//commentstr+='		<p class="contents">['+commentlist[i].rAnswerStatusTxt+']</p> ';
		        		commentstr+='		<p class="info">'+commentlist[i].userName+' | '+commentlist[i].regDate.substr(0,16)+'</p> ';
		        		commentstr+='		<p>'+commentlist[i].bbsContents;
		        		var dateSet='';
		        		if(commentlist[i].rAnswerStatusTxt=='접수'){
		        			commentstr+='	[예상 시작일 : '+bbs.bbsStartday+']';
		        			dateSet=bbs.bbsStartday;
		        		}
		        		if(commentlist[i].rAnswerStatusTxt=='진행'){
		        			commentstr+='	[예상 완료일 : '+bbs.bbsEndday+']';
		        			dateSet=bbs.bbsEndday;
		        		}
		        		commentstr+='		</p> ';
		        		if(commentlist[i].userId==userForm.userId.value){
			        		commentstr+='	<div class="bbs-menu"> ';
							commentstr+='		<button type="button" class="bbs-btn" onclick="bbs.commentSetContext(bbsEditForm,'+commentlist[i].rAnswerStatus+','+commentlist[i].bbsCommentSeq+',\''+commentlist[i].bbsContents+'\',\''+dateSet+'\');">수정</button> ';
							commentstr+='		<button type="button" class="bbs-btn" onclick="bbs.onBbsDelete('+bbs.bbsSeq+','+commentlist[i].bbsCommentSeq+',bbsEditForm,\'admin\');">삭제</button> ';
			        		commentstr+='	</div> ';
		        		}
		        		commentstr+='	 </dd> ';
		        		commentstr+='	</dl> ';
		        		commentstr+=' </li> ';
		        	}
		        }else{
		        	commentstr = '<li class="list-cell empty">댓글이 없습니다.</li>';
		        }
				
				$(".bbs").html(str);
				$(".comment-list").html(commentstr);
		        //$(".paging-block").html(json.paging);
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

	getFileList : function(frm,page) {
		var param = {
			dataType	: frm.dataType.value,
			projectSeq	: frm.projectSeq.value,
			keyword		: frm.keyword.value,
			page		: page
		};

		$.ajax({
		    type:"POST",
		    url:"/proc/data_room.go",
			data:param,
		    dataType:"json",
		
		    success:function(json) {
				var list = json.list;

				$(".project_name").html(json.project.projectName);

				var str = '';
				var strpaging='';
				if (list.length > 0) {
					for(var i=0;i<list.length;i++){
						str+='	<tr>';
						str+='	<td >';
						str+='  	<input type="checkbox" name="downdataSeq" id="downdataSeq'+i+'" class="downdataSeq" value="'+list[i].dataFileName+'">';
						str+='	</td>';
				
						str+='	<td class="text-left">';
						str+='  '+list[i].dataSeq;
						str+='	</td>';
						/*str+='	<td>';
						str+='  '+list[i].dataType;
						str+='	</td>';*/
						str+='	<td class="text-left" onclick="link.go(\'/admin/files_view.go?projectSeq='+frm.projectSeq.value+'&dataSeq='+list[i].dataSeq+'&page=1\');">';
						str+='		'+list[i].dataContents;
						str+='	</td>';
						str+='	<td class="text-left">';
						str+='		'+list[i].userName
						str+='	</td>';
						str+='	<td class="text-left">';
						str+=' '+ list[i].dataRegDate.substr(0,10);
						str+='	</td>';
						str+='</tr>';
					}
				
				} else {
					str = '<tr><td colspan="7">검색된 자료가 없습니다.</td></tr>';
					
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
	downloadchk: function(frm,index){
		if(index==undefined){
			index=0;
		}
		projectMenu.download(frm,index);
	},
	download : function(frm,index) {
	
		var obj =document.getElementById("downdataSeq"+index);
		if(obj){
			index++;
			if(obj.checked){
				var fileName= 
				location.href="/fileDownload.go?fileName="+obj.value;
				setTimeout(function(){
					projectMenu.downloadchk(frm,index)},1000);
			}else{
				projectMenu.downloadchk(frm,index);
			}
			
		}
		return false;
		
	},
	Fileview : function(frm,page) {
		var param = {
			projectSeq	: frm.projectSeq.value,
			dataSeq		: frm.dataSeq.value,
			page		: page,
		};
		

		$.ajax({
		    type:"POST",
		    url:"/proc/data_room_view.go",
			data:param,
		    dataType:"json",
		
		    success:function(json) {
				var data = json.data;
				var contents = '';
				contents += data.dataContents;
				contents += "<br><br>";
				contents += "file : "+data.dataFileName;

				$(".project_name").html(json.project.projectName);
				$(".files .contents").html(data.dataContents);
				$(".files .info").html(data.userName+' | '+data.dataRegDate.substr(0,10));
				var str ="";
				var strdot ="";
				if(data.userId==userForm.userId.value){
					strdot+='		<button type="button" class="bbs-btn" onclick="files.goFileEdit(pageForm,'+data.dataSeq+',\'admin\');">수정</button> ';
					strdot+='		<button type="button" class="bbs-btn" onclick="files.onfilesDelete(pageForm,'+data.dataSeq+',\'admin\');">삭제</button> ';
				}
				str+=' <img src="/images/ic_inbox.png" class="icon" onclick="location.href=\'/fileDownload.go?fileName='+data.dataFileName+'\'"> ';
			
				
				$(".bbs-menu").html(strdot);
				$(".filebutton").html(str);
				
				
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
	

	
	AdminBbsgetList : function(frm,page) {
		var param = {
			projectSeq:frm.projectSeq.value,
			page:page,
			sort:frm.sort.value,
			colName:frm.colName.value,
			keyword:frm.keyword.value,
		};

		$.ajax({
		    type:"POST",
		    url:"/proc/admin_bbs_list.go",
			data:param,
		    dataType:"json",
		
		    success:function(json) {
				var list = json.list;

				$(".project_name").html(json.project.projectName);
				var strpaging='';
				//frm.bbsType.value = json.bbsType;
				frm.projectSeq.value = json.projectSeq;
				var str = '';
				if (list.length > 0) {
					for(var i=0;i<list.length;i++){
						
						str+='<tr onclick="link.go(\'/admin/admin_bbs_view.go?projectSeq='+frm.projectSeq.value+'&bbsSeq='+list[i].bbsSeq+'&page=1\');"> ';
						str+='	<td class="text-left"> ';
						str+='	'+list[i].bbsSeq ;
						str+='	</td> ';
					
						str+='	<td class="text-left"> ';
						str+=' '+list[i].bbsTitle;
					
						str+=' ('+list[i].commentCount+') ';
						str+='	</td> ';
						str+=' <td class="text-left"> ';
						str+='			'+list[i].userName;
						str+=' </td> ';
						str+='	<td class="text-left"> ';
						str+='	 '+list[i].bbsRegDate.substr(0,16);
						str+='	</td> ';
						str+='</tr> ';
						
					}
					
					
				} else {
					str = '<tr><td colspan="4">검색된 게시글이 없습니다.</td></tr>';
					 
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
	AdminBbslistOrder : function(colname, sort) {
		searchForm.colName.value = colname;
		searchForm.sort.value = sort;
		projectMenu.AdminBbsgetList(searchForm, 1);
		var cSort ="asc";
		var point ="▼";
		if(sort=='asc'){
			cSort="desc";
			point="▲";
		}
		
		var str = "<button type='button' class='"+colname+"' onclick=\"projectMenu.AdminBbslistOrder('"+colname+"','"+cSort+"');\">"+point+"</button>";
		$("#"+colname).html(str);
		
		
	},
	AdminBbsview : function(frm,page) {
		var param = {
			projectSeq	: frm.projectSeq.value,
			bbsSeq		: frm.bbsSeq.value,
			page		: page,
		};
		

		$.ajax({
		    type:"POST",
		    url:"/proc/admin_bbs_view.go",
			data:param,
		    dataType:"json",
		
		    success:function(json) {
		    	var bbs = json.bbs;
				var commentlist = json.list;
				var fileList = json.fileList;
				$(".project_name").html(json.project.projectName);
				var str = ' <dt>';
			
				str+=' </dt> ';
				str+=' <dd> ';
				
				str+='	<p class="info"> '+bbs.userName+' | '+bbs.bbsRegDate.substr(0,16)+'</p> ';
				str+='	<p >'+bbs.bbsTitle+'</p> ';
				str+='	<p >'+bbs.bbsContents.split("\n").join("<br>")+'</p> ';
				str+='	<p class="images"> ';
				if(fileList.length >0){
					for(var i =0;i<fileList.length;i++) {
						var file = fileList[i].fileName;
						var filename= file.substr(file.lastIndexOf('/')+1);
						if(fileList[i].fileExt=='img')	{
							str+='	<img src="/files'+file+'"><br> ';
						}
						str+=' <a href="/fileDownload.go?fileName='+file+'" class="download"><img src="/images/ic_file_download_black_18dp_1x.png">'+filename+'</a>';
					}
				}
				str+='	</p> ';
				if(bbs.userId==userForm.userId.value){
					str+='	<div class="bbs-menu"> ';
					
					str+='		<button type="button" class="bbs-btn" onclick="adminBbs.goBbsEdit(pageForm,'+bbs.bbsSeq+',\'admin\');">수정</button> ';
					str+='		<button type="button" class="bbs-btn" onclick="adminBbs.onBbsDelete('+bbs.bbsSeq+',0,pageForm,\'admin\');">삭제</button> ';
					str+='	</div> ';
				}
				str+='	</dd> ';
				
				var commentstr='';	
		        if(commentlist.length>0){
		        	for(var i=0;i<commentlist.length;i++){
		        		commentstr+=' <li> ';
		        		commentstr+=' <dl class="bbs"> ';
		        		commentstr+='	 <dt><span class="step-box step'+commentlist[i].rAnswerStatus+'"><img src="/images/ic_reply.png"><br>'+commentlist[i].rAnswerStatusTxt+'</span></dt> ';
		        		commentstr+='	 <dd> ';
		        		//commentstr+='		<p class="contents">['+commentlist[i].rAnswerStatusTxt+']</p> ';
		        		commentstr+='		<p class="info">'+commentlist[i].userName+' | '+commentlist[i].regDate.substr(0,16)+'</p> ';
		        		commentstr+='		<p>'+commentlist[i].bbsContents;
		        	
		        		commentstr+='		</p> ';
		        		if(commentlist[i].userId==userForm.userId.value){
			        		commentstr+='	<div class="bbs-menu"> ';
							commentstr+='		<button type="button" class="bbs-btn" onclick="adminBbs.commentSetContext(bbsEditForm,'+commentlist[i].bbsCommentSeq+',\''+commentlist[i].bbsContents+'\');">수정</button> ';
							commentstr+='		<button type="button" class="bbs-btn" onclick="adminBbs.onBbsDelete('+bbs.bbsSeq+','+commentlist[i].bbsCommentSeq+',bbsEditForm,\'admin\');">삭제</button> ';
			        		commentstr+='	</div> ';
		        		}
		        		commentstr+='	 </dd> ';
		        		commentstr+='	</dl> ';
		        		commentstr+=' </li> ';
		        	}
		        }else{
		        	commentstr = '<li class="list-cell empty">댓글이 없습니다.</li>';
		        }
				
				$(".bbs").html(str);
				$(".comment-list").html(commentstr);
		        //$(".paging-block").html(json.paging);
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

	
	WeekBbsgetList : function(frm,page) {
		var param = {
			projectSeq:frm.projectSeq.value,
			page:page,
			sort:frm.sort.value,
			colName:frm.colName.value,
			keyword:frm.keyword.value,
		};

		$.ajax({
		    type:"POST",
		    url:"/proc/week_bbs_list.go",
			data:param,
		    dataType:"json",
		
		    success:function(json) {
				var list = json.list;

				$(".project_name").html(json.project.projectName);
				var strpaging='';
				//frm.bbsType.value = json.bbsType;
				frm.projectSeq.value = json.projectSeq;
				var str = '';
				if (list.length > 0) {
					for(var i=0;i<list.length;i++){
						
						str+='<tr onclick="link.go(\'/admin/week_bbs_view.go?projectSeq='+frm.projectSeq.value+'&bbsSeq='+list[i].bbsSeq+'&page=1\');"> ';
						str+='	<td class="text-left"> ';
						str+='	'+list[i].bbsSeq ;
						str+='	</td> ';
					
						str+='	<td class="text-left"> ';
						str+=' '+list[i].bbsTitle;
					
						str+=' ('+list[i].commentCount+') ';
						str+='	</td> ';
						str+=' <td class="text-left"> ';
						str+='			'+list[i].userName;
						str+=' </td> ';
						str+='	<td class="text-left"> ';
						str+='	 '+list[i].bbsRegDate.substr(0,16);
						str+='	</td> ';
						str+='</tr> ';
						
					}
					
					
				} else {
					str = '<tr><td colspan="4">검색된 게시글이 없습니다.</td></tr>';
					 
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
	WeekBbslistOrder : function(colname, sort) {
		searchForm.colName.value = colname;
		searchForm.sort.value = sort;
		projectMenu.WeekBbsgetList(searchForm, 1);
		var cSort ="asc";
		var point ="▼";
		if(sort=='asc'){
			cSort="desc";
			point="▲";
		}
		
		var str = "<button type='button' class='"+colname+"' onclick=\"projectMenu.WeekBbslistOrder('"+colname+"','"+cSort+"');\">"+point+"</button>";
		$("#"+colname).html(str);
		
		
	},
	WeekBbsview : function(frm,page) {
		var param = {
			projectSeq	: frm.projectSeq.value,
			bbsSeq		: frm.bbsSeq.value,
			page		: page,
		};
		

		$.ajax({
		    type:"POST",
		    url:"/proc/week_bbs_view.go",
			data:param,
		    dataType:"json",
		
		    success:function(json) {
		    	var bbs = json.bbs;
				var commentlist = json.list;
				var fileList = json.fileList;
				$(".project_name").html(json.project.projectName);
				var str = ' <dt>';
			
				str+=' </dt> ';
				str+=' <dd> ';
				
				str+='	<p class="info"> '+bbs.userName+' | '+bbs.bbsRegDate.substr(0,16)+'</p> ';
				str+='	<p >'+bbs.bbsTitle+'</p> ';
				str+='	<p >'+bbs.bbsContents.split("\n").join("<br>")+'</p> ';
				str+='	<p class="images"> ';
				if(fileList.length >0){
					for(var i =0;i<fileList.length;i++) {
						var file = fileList[i].fileName;
						var filename= file.substr(file.lastIndexOf('/')+1);
						if(fileList[i].fileExt=='img')	{
							str+='	<img src="/files'+file+'"><br> ';
						}
						str+=' <a href="/fileDownload.go?fileName='+file+'" class="download"><img src="/images/ic_file_download_black_18dp_1x.png">'+filename+'</a>';
					}
				}
				str+='	</p> ';
				if(bbs.userId==userForm.userId.value){
					str+='	<div class="bbs-menu"> ';
					
					str+='		<button type="button" class="bbs-btn" onclick="weekBbs.goBbsEdit(pageForm,'+bbs.bbsSeq+',\'admin\');">수정</button> ';
					str+='		<button type="button" class="bbs-btn" onclick="weekBbs.onBbsDelete('+bbs.bbsSeq+',0,pageForm,\'admin\');">삭제</button> ';
					str+='	</div> ';
				}
				str+='	</dd> ';
				
				var commentstr='';	
		        if(commentlist.length>0){
		        	for(var i=0;i<commentlist.length;i++){
		        		commentstr+=' <li> ';
		        		commentstr+=' <dl class="bbs"> ';
		        		commentstr+='	 <dt><span class="step-box step'+commentlist[i].rAnswerStatus+'"><img src="/images/ic_reply.png"><br>'+commentlist[i].rAnswerStatusTxt+'</span></dt> ';
		        		commentstr+='	 <dd> ';
		        		//commentstr+='		<p class="contents">['+commentlist[i].rAnswerStatusTxt+']</p> ';
		        		commentstr+='		<p class="info">'+commentlist[i].userName+' | '+commentlist[i].regDate.substr(0,16)+'</p> ';
		        		commentstr+='		<p>'+commentlist[i].bbsContents;
		        	
		        		commentstr+='		</p> ';
		        		if(commentlist[i].userId==userForm.userId.value){
			        		commentstr+='	<div class="bbs-menu"> ';
							commentstr+='		<button type="button" class="bbs-btn" onclick="weekBbs.commentSetContext(bbsEditForm,'+commentlist[i].bbsCommentSeq+',\''+commentlist[i].bbsContents+'\');">수정</button> ';
							commentstr+='		<button type="button" class="bbs-btn" onclick="weekBbs.onBbsDelete('+bbs.bbsSeq+','+commentlist[i].bbsCommentSeq+',bbsEditForm,\'admin\');">삭제</button> ';
			        		commentstr+='	</div> ';
		        		}
		        		commentstr+='	 </dd> ';
		        		commentstr+='	</dl> ';
		        		commentstr+=' </li> ';
		        	}
		        }else{
		        	commentstr = '<li class="list-cell empty">댓글이 없습니다.</li>';
		        }
				
				$(".bbs").html(str);
				$(".comment-list").html(commentstr);
		        //$(".paging-block").html(json.paging);
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