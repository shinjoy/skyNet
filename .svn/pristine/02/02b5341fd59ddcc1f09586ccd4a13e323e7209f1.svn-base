var bbs = {

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

				$(".top-center").html(json.project.projectName);
				var strpaging='';
				frm.bbsType.value = json.bbsType;
				frm.projectSeq.value = json.projectSeq;
				var str = '';
				if (list.length > 0) {
					for(var i=0;i<list.length;i++){
						str+='<li class="bbs-cell"> ';
						str+='	<dl class="bbs"> ';
						str+='		<dt>';
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
						/*str+= "["+list[i].statusTxt +"]";*/
						str+='		</dt> ';
						str+='		<dd> ';
						str+='			<p class="contents" onclick="link.go(\'/m/bbs_view.go?projectSeq='+frm.projectSeq.value+'&bbsSeq='+list[i].bbsSeq+'&page=1\');">';
						str+='				['+list[i].bbsTypeTxt+']';
						str+=' '+list[i].bbsTitle;
						/*if (list[i].bbsContents.length > 50) {
							str+='				'+list[i].bbsContents.substring(0,50).split("\n").join("<br>")+'...';
						} else {
							str+='				'+list[i].bbsContents.split("\n").join("<br>");
						}*/
						str+=' ('+list[i].commentCount+') ';
						str+=' </p> ';
						str+='			<p class="info">'+list[i].userName+' | '+list[i].bbsRegDate.substr(0,16)+'</p> ';
						str+='		</dd> ';
						str+='	</dl> ';
						str+='</li> ';
					}
					
					if((parseInt(json.PERPAGE)*parseInt(json.currentPage))<parseInt(json.count)){
						
						strpaging+=' <div class="pagelist"> ';
						strpaging+=' <button type="button" onclick="bbs.getList(searchForm,'+(parseInt(json.currentPage)+1)+');">더보기 </button>';
						strpaging+=' </div>';
					}
					
					  $(".list-cell.empty").empty();
					  $(".bbs-list").append(str);
				} else {
					str = '<li class="list-cell empty">검색된 게시글이 없습니다.</li>';
					  $(".bbs-list").html(str);
				}
		      
		       $(".paging-block").html(strpaging);
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
	onSearch : function (frm,page) {
		$(".bbs-list").empty();
		bbs.getList(frm,page);
		return false;
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

				$(".top-center").html(json.project.projectName);

				//frm.bbsType.value = json.bbsType;
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
					str+='		<button type="button" class="bbs-btn" onclick="bbs.goBbsEdit(pageForm,'+bbs.bbsSeq+',\'m\');">수정</button> ';
					str+='		<button type="button" class="bbs-btn" onclick="bbs.onBbsDelete('+bbs.bbsSeq+',0,pageForm,\'m\');">삭제</button> ';
					str+='	</div> ';
				}
				str+='	</dd> ';
				
				var commentstr='';	
		        if(commentlist.length>0){
		        	for(var i=0;i<commentlist.length;i++){
		        		commentstr+=' <li> ';
		        		commentstr+=' <dl class="bbs"> ';
		        		commentstr+='	 <dt><span class="step-box"><img src="/images/ic_reply.png"></span></dt> ';
		        		commentstr+='	 <dd> ';
		        		//commentstr+='		<p class="contents">['+commentlist[i].rAnswerStatusTxt+']</p> ';
		        		commentstr+='		<p class="info"><span class="status-box-sm status'+commentlist[i].rAnswerStatus+'">'+commentlist[i].rAnswerStatusTxt+'</span> '+commentlist[i].userName+' | '+commentlist[i].regDate.substr(0,16)+'</p> ';
		        		commentstr+='		<p>'+commentlist[i].bbsContents;
		        		var dateSet='';
		        		if(commentlist[i].rAnswerStatusTxt=='접수'){
		        			commentstr+='	<br>[예상 시작일 : '+bbs.bbsStartday+']';
		        			dateSet=bbs.bbsStartday;
		        		}
		        		if(commentlist[i].rAnswerStatusTxt=='진행'){
		        			commentstr+='	<br>[예상 완료일 : '+bbs.bbsEndday+']';
		        			dateSet=bbs.bbsEndday;
		        		}
		        		commentstr+='		</p> ';
		        		if(commentlist[i].userId==userForm.userId.value){
			        		commentstr+='	<div class="bbs-menu"> ';
							commentstr+='		<button type="button" class="bbs-btn" onclick="bbs.commentSetContext(bbsEditForm,'+commentlist[i].rAnswerStatus+','+commentlist[i].bbsCommentSeq+',\''+commentlist[i].bbsContents+'\',\''+dateSet+'\');">수정</button> ';
							commentstr+='		<button type="button" class="bbs-btn" onclick="bbs.onBbsDelete('+bbs.bbsSeq+','+commentlist[i].bbsCommentSeq+',bbsEditForm,\'m\');">삭제</button> ';
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
	goBbsEdit : function(frm, seq,url) {
		var bbsType = 1;
		if (frm.bbsType != undefined) {
		
				bbsType = frm.bbsType.value;
			
		
		}
		document.location.href = '/'+url+'/bbs_edit.go?projectSeq='+frm.projectSeq.value+"&bbsSeq="+seq+"&bbsType="+bbsType;
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

				$(".top-center").html(json.project.projectName);
				frm.bbsTitle.value =bbs.bbsTitle;
				frm.contents.value = bbs.bbsContents;
			/*	if(bbs.bbsType!='1'){
					frm.bbsType.value=bbs.bbsType;
				}*/
				var str = ' ';
				var photostr=' ';
				if(fileList != undefined){
					for(var i =0;i<fileList.length;i++){
						photostr+='	<p id="img_'+i+'">';
						photostr+='		<input type="hidden" name="photo" value="'+fileList[i].fileName+'">';
						photostr+='		'+fileList[i].fileName+' ';
						photostr+='		<button type="button" class="file-delete" onclick="bbs.fileDelete('+fileList[i].bbsSeq+',\''+fileList[i].fileName+'\','+i+');">X</button><br> ';
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
	bbsSave : function(frm,url) {
		
		if(frm.bbsTitle.value==""){
			alert("제목을 입력해 주세요.");
			return false;
		}
		if(frm.contents.value==""){
			alert("내용을 입력해 주세요.");
			return false;
		}
	
		var param = {
			bbsSeq		: frm.bbsSeq.value,
			bbsTitle    : frm.bbsTitle.value,
			projectSeq	: frm.projectSeq.value,
			bbsType		: frm.bbsType.value,
			bbsType2	: frm.bbsType2.value,
			bbsContents	: frm.contents.value,
			fileName	: getList("photo").join(",")
			//sendSms		: frm.sendSms.value,
			//grade		: frm.grade.value
		};
		
		$.ajax({
		    type:"POST",
		    url:"/proc/bbs_edit_do.go",
			data:param,
		    dataType:"json",
		
		    success:function(json) {
		    	alert(json.message);
		    	if(json.result){
		    		document.location.href = "/"+url+"/bbs_list.go?projectSeq="+frm.projectSeq.value+"&bbsType="+frm.bbsType.value;
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
	bbsCommentedit : function(frm) {
		if(frm.bbsContents.value==""){
			alert("내용을 입력해주세요.");
			return false;
		}
		var param = {
			bbsSeq			:frm.bbsSeq.value,
			bbsCommentSeq	:frm.bbsCommentSeq.value,
			bbsContents		:frm.bbsContents.value,
			commentStatus 	:frm.commentStatus.value,
			dateSet : frm.dateSet.value
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
		$(".bbs-list").empty();
		bbs.getList(frm, 1);
	},
	tabEdit : function(frm, tab) {
		frm.bbsType.value=tab; 
		$(".tab li").removeClass("active");
		$("#tab"+tab).addClass("active");
	},
	fileDelete : function(bbsSeq,fileName,num){
		if(confirm("파일을 삭제하시겠습니까?")) {
			var param = {
				bbsSeq : bbsSeq,
				fileName : fileName
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
		var str = '<li class="upload-progress"><img src="/images/ajax-loader.gif"><li>';
		$(".file-add").append(str);
		return true; 
	},
	formSuccess : function (responseText, statusText, xhr, $form) {
		//alert('status: ' + statusText + '\n\nresponseText: \n' + responseText );
		var idx = $(".photo").size();
		var json = JSON.parse(responseText);
		try {
			$(".upload-progress").remove();
			if(json.photo!=""){
				var str = '';
				str += '<li id="img_'+idx+'">';
				str += '	'+ json.fileName +' ';
				str += '		<button type="button" class="del-btn" onclick="bbs.fileDelete('+bbsEditForm.bbsSeq.value+',\''+ json.path +'/'+ json.fileName +'\','+idx+')">X</button>';
				str += '	</div>';
				str += '	<input type="hidden" name="photo" class="photo" value="'+ json.path +'/'+ json.fileName +'">';
				str += '</li>';
				$(".file-add").append(str);
				
				$(".file").val("");
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
	},
	onBbsDelete : function(bbsSeq,bbsCommentSeq,frm,url) {
		if(confirm("삭제하시겠습니까?")) {
			var param = {
				bbsSeq : bbsSeq,
				bbsCommentSeq : bbsCommentSeq,
			
			};
			$.ajax({
				type:"POST",
				url:"/proc/bbs_delete.go",
				dataType:"json",
				data:param,
				success:function(json){
					alert(json.message);
					if(bbsCommentSeq>0){
						document.location.href="/"+url+"/bbs_view.go?projectSeq="+frm.projectSeq.value+"&bbsType=0&bbsSeq="+frm.bbsSeq.value;
					}else{
						document.location.href="/"+url+"/bbs_list.go?projectSeq="+frm.projectSeq.value+"&bbsType=0";
					}
					
				}
			}); 
		}
		
	},
/*	showContext : false,
	showContextMenu : function(key, event) {
		//var event = window.event;
	    var posX = event.clientX;
	    var posY = event.clientY;
	    if ($(window).width() < (posX+80)) {
	    	posX = posX - 80;
	    }
	    if ($(window).height() < (posY+55)) {
	    	posY = posY - 55;
	    }
	    var str = '';
	    str += '<ul class="context">';
	    str += '	<li onclick="bbs.goBbsEdit(pageForm,'+key+');">수정</li>';
	    str += '	<li onclick="bbs.onBbsDelete(bb,'+key+');">삭제</li>';
	    str += '</ul>';
	    $("body").append(str);
		$("ul.context").css("display","block");
		$("ul.context").css("top",posY);
		$("ul.context").css("left",posX);
		bbs.showContext = true;
		event.returnValue = false;
	},
	showContextCommentMenu : function(status,key, event,contents,dateSet) {
		//var event = window.event;
	    var posX = event.clientX;
	    var posY = event.clientY;
	    if ($(window).width() < (posX+80)) {
	    	posX = posX - 80;
	    }
	    if ($(window).height() < (posY+55)) {
	    	posY = posY - 55;
	    }
	    bbsEditForm.bbsCommentSeq.value = key;
	  
	    var str = '';
	    str += '<ul class="context">';
	    str += '	<li onclick="bbs.commentSetContext(bbsEditForm,'+status+','+key+',\''+contents+'\',\''+dateSet+'\');">수정</li>';
	    str += '	<li onclick="bbs.onBbsDelete(bbsEditForm);">삭제</li>';
	    str += '</ul>';
	    $("body").append(str);
	   
		$("ul.context").css("display","block");
		$("ul.context").css("top",posY);
		$("ul.context").css("left",posX);
		bbs.showContext = true;
		event.returnValue = false;
	},
	contextMenu : function() {
		if (document.addEventListener) {
			
			document.addEventListener('contextmenu', function(e) {
				e.preventDefault();
			}, false);
			
			document.addEventListener('click', function(e) {
				if (bbs.showContext == false) {
					$("ul.context").remove();
				}
				bbs.showContext = false;
				//e.preventDefault();
			}, false);
		} else {
			
			document.attachEvent('oncontextmenu', function() {
				window.event.returnValue = false;
			});
			
			document.attachEvent('onclick', function() {
				if (bbs.showContext == false) {
					$("ul.context").remove();
				}
				bbs.showContext = false;
				window.event.returnValue = false;
			});
		}
	},*/
	commentSetContext : function(frm,status,bbsCommentSeq,bbsContents,dateSet){
		
		frm.bbsCommentSeq.value = bbsCommentSeq;
		frm.bbsContents.value = bbsContents;
		frm.commentStatus.value = status;
		frm.dateSet.value= dateSet;
		
		
		
	},
	todoEdit : function(frm) {
		
		if(frm.workAdmin.value==""){
			alert("작업자를 선택해 주세요.");
			return false;
		}
		if(frm.dateSet.value==""){
			alert("작업일을 입력해 주세요.");
			return false;
		}
		if(frm.commentStatus.value!="1"){
			alert("접수상태에서만 등록할 수 있습니다.");
			return false;
		}
	
		var param = {
				projectSeq :frm.projectSeq.value,
				todo : frm.bbsContents.value,
				userId : frm.workAdmin.value,
				dateSet : frm.dateSet.value,
				todoPart : frm.todoPart.value
			
		};
		
		$.ajax({
		    type:"POST",
		    url:"/proc/project_todo_edit.go",
			data:param,
		    dataType:"json",
		
		    success:function(json) {
		    	alert(json.message);
		    	/*if(json.result){
		    		document.location.href = "/m/bbs_list.go?projectSeq="+frm.projectSeq.value+"&bbsType="+frm.bbsType.value;
		    	}*/
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
	setTodoForm : function(frm, obj) {
		if (obj.value == -1) {	//무관
			$("#status-select").css({"background-color":"#fff;","color":"#8a8a8a;"});
			$("#part-select").attr("disabled",true);
			$("#work-select").attr("disabled",true);
			$("#date-input").attr("disabled",true);
			$("#date-input").attr("placeholder","");
			$("#add-btn").attr("disabled",true);
		} else if (obj.value == 0) {	//대기
			$("#status-select").css({"background-color":"#ff0;","color":"#555;"});
			$("#part-select").attr("disabled",true);
			$("#work-select").attr("disabled",true);
			$("#date-input").attr("disabled",true);
			$("#date-input").attr("placeholder","");
			$("#add-btn").attr("disabled",true);
		} else if (obj.value == 1) {	//접수
			$("#status-select").css({"background-color":"#f40;","color":"#fff;"});
			$("#part-select").attr("disabled",false);
			$("#work-select").attr("disabled",false);
			$("#date-input").attr("disabled",false);
			$("#date-input").attr("placeholder","시작예정일");
			$("#add-btn").attr("disabled",false);
		} else if (obj.value == 2) {	//진행
			$("#status-select").css({"background-color":"#2f4;","color":"#fff;"});
			$("#part-select").attr("disabled",true);
			$("#work-select").attr("disabled",true);
			$("#date-input").attr("disabled",false);
			$("#date-input").attr("placeholder","완료예정일");
			$("#add-btn").attr("disabled",false);
		} else if (obj.value == 3) {	//보류
			$("#status-select").css({"background-color":"#888;","color":"#fff;"});
			$("#part-select").attr("disabled",true);
			$("#work-select").attr("disabled",true);
			$("#date-input").attr("disabled",true);
			$("#date-input").attr("placeholder","");
			$("#add-btn").attr("disabled",true);
		} else if (obj.value == 4) {	//완료
			$("#status-select").css({"background-color":"#04f;","color":"#fff;"});
			$("#part-select").attr("disabled",true);
			$("#work-select").attr("disabled",true);
			$("#date-input").attr("disabled",true);
			$("#date-input").attr("placeholder","");
			$("#add-btn").attr("disabled",true);
		}

	}

};