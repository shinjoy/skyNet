var project = {
	getList : function(frm,page) {
		var param = {
				page:page
			};

			$.ajax({
			    type:"POST",
			    url:"/proc/project_list.go",
				data:param,
			    dataType:"json",
			
			    success:function(json) {
					var list = json.list;
					var str = '';
					if (list.length > 0) {
						for(var i=0;i<list.length;i++){
							str+='<li>';
							str+='	<dl>';
							str+='		<dt class="title" onclick="document.location.href=\'/m/project_view.go?projectSeq='+list[i].projectSeq+'\';">';
							str+='			<span class="project-name">'+list[i].projectName+'</span>';
							str+='			<span class="project-part">('+list[i].companyName+')</span>';
							str+='			<p class="info">';
							str+='				<span class="project-period">'+list[i].projectStartDay+'~'+list[i].projectEndDay+'</span>';
							str+='			</p>';
							str+='		</dt>';
							//str+='		<dd class="date">'+list[i].projectRegDate.substring(0,10)+'</dd>';
							str+='		<dd class="arrow" onclick="document.location.href=\'/m/project_view.go?projectSeq='+list[i].projectSeq+'\';">';
							str+='			<span class="project-name">'+list[i].progressPercent+'%</span>';
							str+='			<img src="/images/ic_list_arrow.png">';
							str+='		</dd>';
							str+='	</dl>';
							str+='</li>';
						}
						
					} else {
						str = '<li class="list-cell empty">프로젝트가 없습니다.</li>';
					}
			        $(".project-list").html(str);
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
	view : function(projectSeq) {
		var param = {
			projectSeq : projectSeq
		};
		

		$.ajax({
		    type:"POST",
		    url:"/proc/project_view.go",
			data:param,
		    dataType:"json",
		
		    success:function(json) {
				var project = json.project;
				$(".top-center").html(project.projectName);
				
				if (project.designPercent != undefined) {
					if (project.designPercent > -1) {
						$("#design-part").css("display","block");
						$("#design-part .progress").css("width",project.designPercent+"%");
						$("#design-part .precent").html(project.designPercent);
					} else {
						$("#design-part").css("display","none");
					}
				}
				if (project.iosPercent != undefined) {
					if (project.iosPercent > -1) {
						$("#ios-part").css("display","block");
						$("#ios-part .progress").css("width",project.iosPercent+"%");
						$("#ios-part .precent").html(project.iosPercent);
					} else {
						$("#ios-part").css("display","none");
					}
				}
				if (project.andPercent != undefined) {
					if (project.andPercent > -1) {
						$("#android-part").css("display","block");
						$("#android-part .progress").css("width",project.andPercent+"%");
						$("#android-part .precent").html(project.andPercent);
					} else {
						$("#android-part").css("display","none");
					}
				}
				if (project.webPercent != undefined) {
					if (project.webPercent > -1) {
						$("#web-part").css("display","block");
						$("#web-part .progress").css("width",project.webPercent+"%");
						$("#web-part .precent").html(project.webPercent);
					} else {
						$("#web-part").css("display","none");
					}
				}
				if (project.serverPercent != undefined) {
					if (project.serverPercent > -1) {
						$("#server-part").css("display","block");
						$("#server-part .progress").css("width",project.serverPercent+"%");
						$("#server-part .precent").html(project.serverPercent);
					} else {
						$("#server-part").css("display","none");
					}
				}
				if (project.pcPercent != undefined) {
					if (project.pcPercent > -1) {
						$("#pc-part").css("display","block");
						$("#pc-part .progress").css("width",project.pcPercent+"%");
						$("#pc-part .precent").html(project.pcPercent);
					} else {
						$("#pc-part").css("display","none");
					}
				}
				
				$(".bug-count").html("요청:"+json.bugingCount+" 완료:"+json.bugfinishCount);
				$(".require-count").html("요청:"+json.requireingCount+"  완료:"+json.requirefinishCount);
				if(json.userType==1){
					$(".notice-count").html(json.noticeCount);
				}
				if(json.newCount>0){
					$(".new-count").html("new");
				}
				var list = json.list;
				if (list.length>0) {
					var str = '';
					for (var i=0; i<list.length; i++) {
						str += '<li>';
						str += '	<dl onclick="location.href=\'tel:07086728083\'">';
						//str += '		<dt onclick="location.href="tel:'+list[i].userPhone+';">';
						str += '		<dt class="icon">';
						str += '			<img src="/images/ic_worker.png">';
						str += '		</dt>';
						str += '		<dt class="name">';
						str += '			<span class="man-name">'+list[i].userPosition+' | '+list[i].userName+'</span>';
						str += '		</dt>';
						str += '		<dd class="info">';
						str += '			<span class="man-position">('+list[i].userGroup+')</span>';
						str += '		</dd>';
						//str += '		<dd onclick="location.href="tel:'+list[i].userPhone+';">';
						str += '		<dd class="arrow">';
						str += '			<img src="/images/ic_list_arrow.png">';
						str += '		</dd>';
						str += '	</dl>';
						str += '</li>';
					}
					$(".man-list").html(str);
				} else {
					$(".man-list").html('<li class="empty">담당자가 없습니다.</li>');
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
	bbsedit : function(frm) {
		var param = {
			bbsSeq:frm.bbsSeq.value,
			projectSeq:frm.projectSeq.value
		
		};
		

		$.ajax({
		    type:"POST",
		    url:"/proc/bbs_edit.go",
			data:param,
		    dataType:"json",
		
		    success:function(json) {
		    	var bbs = json.bbs;
				var fileList = json.fileList;

				frm.bbsType.value = bbs.bbsType;
				frm.contents.value = bbs.bbsContents;
				
				var str = ' ';
				var photostr=' ';
				if(fileList != undefined){
					for(var i =0;i<fileList.length;i++){
						photostr+='	<p id="img_'+i+'">';
						photostr+='		<input type="hidden" name="photo" value="'+fileList[i].fileName+'">';
						photostr+='		'+fileList[i].fileName+' ';
						photostr+='		<button type="button" class="file-delete" onclick="bbs.fileDelete('+fileList[i].fileSeq+','+fileList[i].bbsSeq+',\''+fileList[i].fileName+'\,'+i+');">X</button><br> ';
						photostr+=' </p> ';
					}
				}
				$(".file-add").append(photostr);
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
	edit : function(frm) {
		var param = {
			bbsSeq:frm.bbsSeq.value,
			bbsCommentSeq:frm.bbsCommentSeq.value,
			bbsContents:frm.bbsContents.value,
			commentStatus :frm.commentStatus.value
		};
		$.ajax({
			type:"POST",
			url:"/proc/bbs_comment_edit_do.go",
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
	menu : function(bbsSeq) {
		
	},
	tabList : function(frm, tab) {
		frm.bbsType.value=tab; 
		$(".tab li").removeClass("active");
		$("#tab"+tab).addClass("active");
		bbs.getList(frm, 1);
	},
	tabEdit : function(frm, tab) {
		frm.bbsType.value=tab; 
		$(".tab li").removeClass("active");
		$("#tab"+tab).addClass("active");
	},
	fileDelete : function(fileSeq,bbsSeq,fileName,num){
		if(confirm("파일을 삭제하시겠습니까?")) {
			var param = {
					fileName : filename,
					bbsSeq : bbsSeq,
					fileSeq : fileSeq
				
			};
			$.ajax({
				type:"POST",
				url:"/proc/file_delete.go",
				dataType:"json",
				data:param,
				success:function(json){
				
					$("#img_"+num).remove();
				
				}
			}); 
		}
	},
	fileupload :function(){
		var options = {
				//target :		'#user-join-result',
				beforeSubmit :	formCheck,
				success :		formSuccess
		};
		
		$('#bbsEditForm').ajaxForm(options);
		
		
	},
	formCheck : function() {
		return true; 
	},
	formSuccess : function (responseText, statusText, xhr, $form) {
		//alert('status: ' + statusText + '\n\nresponseText: \n' + responseText );
		var idx = 0;
		if (bbsEditForm.photo != undefined) {
			idx = bbsEditForm.photo.length + 1;
		};
		var json = JSON.parse(responseText);
		try {
			if(json.photo!=""){
				var str = '';
				str += '<li id="img_'+idx+'">';
				str += '	'+ json.path +'/'+ json.photo +' ';
				str += '		<button type="button" class="cancel-btn" onclick="bbs.fileDelete(0,0,\''+ json.path +'/'+ json.photo +'\','+idx+')">X</button>';
				str += '	</div>';
				str += '	<input type="hidden" name="photo" value="'+ json.path +'/'+ json.photo +'">';
				str += '</li>';
				$(".file-add").append(str);
			}
		} catch (e) {
			pop.openAlert('',json.message); 
		}
	},
	showHideSearch : function() {
		if ($(".search").height() < 39) {
			$(".search").animate({height:39},300);
		} else {
			$(".search").animate({height:0},200);
		}
	}
};