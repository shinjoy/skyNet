var weekBbs = {

	getList : function(frm,page) {
		var param = {
			/*bbsType:frm.bbsType.value,//1요청2버그
			bbsType2:frm.bbsType2.value,//빈값 둘다,0전체 1내부공개
*/			projectSeq:frm.projectSeq.value,
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

				$(".top-center").html(json.project.projectName);
				var strpaging='';
				//frm.bbsType.value = json.bbsType;
				frm.projectSeq.value = json.projectSeq;
				var str = '';
				if (list.length > 0) {
					for(var i=0;i<list.length;i++){
						str+='<li class="bbs-cell"> ';
						str+='	<dl class="bbs"> ';
				
						str+='		<dd> ';
						str+='			<p class="contents" onclick="link.go(\'/m/week_bbs_view.go?projectSeq='+frm.projectSeq.value+'&bbsSeq='+list[i].bbsSeq+'&page=1\');">';
					
						str+=' '+list[i].bbsTitle;
					
						str+=' ('+list[i].commentCount+') ';
						str+=' </p> ';
						str+='			<p class="info">'+list[i].userName+' | '+list[i].bbsRegDate.substr(0,16)+'</p> ';
						str+='		</dd> ';
						str+='	</dl> ';
						str+='</li> ';
					}
					
					if((parseInt(json.PERPAGE)*parseInt(json.currentPage))<parseInt(json.count)){
						
						strpaging+=' <div class="pagelist"> ';
						strpaging+=' <button type="button" onclick="weekBbs.getList(searchForm,'+(parseInt(json.currentPage)+1)+');">더보기 </button>';
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
		weekBbs.getList(frm,page);
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
		    url:"/proc/week_bbs_view.go",
			data:param,
		    dataType:"json",
		
		    success:function(json) {
				var bbs = json.bbs;
				var commentlist = json.list;
				var fileList = json.fileList;

				$(".top-center").html(json.project.projectName);
				var str = '';
			
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
					str+='		<button type="button" class="bbs-btn" onclick="weekBbs.goBbsEdit(pageForm,'+bbs.bbsSeq+',\'m\');">수정</button> ';
					str+='		<button type="button" class="bbs-btn" onclick="weekBbs.onBbsDelete('+bbs.bbsSeq+',0,pageForm,\'m\');">삭제</button> ';
					str+='	</div> ';
				}
				str+='	</dd> ';
				
				var commentstr='';	
		        if(commentlist.length>0){
		        	for(var i=0;i<commentlist.length;i++){
		        		commentstr+=' <li> ';
		        		commentstr+=' <dl class="bbs"> ';
		        		//commentstr+='	 <dt><span class="step-box step'+commentlist[i].rAnswerStatus+'"><img src="/images/ic_reply.png"><br>'+commentlist[i].rAnswerStatusTxt+'</span></dt> ';
		        		commentstr+='	 <dd> ';
		        		//commentstr+='		<p class="contents">['+commentlist[i].rAnswerStatusTxt+']</p> ';
		        		commentstr+='		<p class="info">'+commentlist[i].userName+' | '+commentlist[i].regDate.substr(0,16)+'</p> ';
		        		commentstr+='		<p>'+commentlist[i].bbsContents+'</p> ';
		        		if(commentlist[i].userId==userForm.userId.value){
			        		commentstr+='	<div class="bbs-menu"> ';
			        		commentstr+='		<button type="button" class="bbs-btn" onclick="weekBbs.commentSetContext(bbsEditForm,'+commentlist[i].bbsCommentSeq+',\''+commentlist[i].bbsContents+'\',\'m\');">수정</button> ';
							commentstr+='		<button type="button" class="bbs-btn" onclick="weekBbs.onBbsDelete('+bbs.bbsSeq+','+commentlist[i].bbsCommentSeq+',bbsEditForm,\'m\');">삭제</button> ';
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
		
		document.location.href = "/"+url+"/week_bbs_edit.go?projectSeq="+frm.projectSeq.value+"&bbsSeq="+seq;
	},
	bbsedit : function(frm) {
		var param = {
			bbsSeq:frm.bbsSeq.value,
			projectSeq:frm.projectSeq.value
		};

		$.ajax({
		    type:"POST",
		    url:"/proc/week_bbs_edit.go",
			data:param,
		    dataType:"json",
		
		    success:function(json) {
		    	var bbs = json.bbs;
				var fileList = json.fileList;

				$(".top-center").html(json.project.projectName);
				frm.bbsTitle.value =bbs.bbsTitle;
				frm.contents.value = bbs.bbsContents;
				
				var str = ' ';
				var photostr=' ';
				if(fileList != undefined){
					for(var i =0;i<fileList.length;i++){
						photostr+='	<p id="img_'+i+'">';
						photostr+='		<input type="hidden" name="photo" value="'+fileList[i].fileName+'">';
						photostr+='		'+fileList[i].fileName+' ';
						photostr+='		<button type="button" class="file-delete" onclick="weekBbs.fileDelete('+fileList[i].bbsSeq+',\''+fileList[i].fileName+'\','+i+');">X</button><br> ';
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
		/*	bbsType		: frm.bbsType.value,
			bbsType2	: frm.bbsType2.value,*/
			bbsContents	: frm.contents.value,
			fileName	: getList("photo").join(",")
			//sendSms		: frm.sendSms.value,
			//grade		: frm.grade.value
		};
		
		$.ajax({
		    type:"POST",
		    url:"/proc/week_bbs_edit_do.go",
			data:param,
		    dataType:"json",
		
		    success:function(json) {
		    	alert(json.message);
		    	if(json.result){
		    		document.location.href = "/"+url+"/week_bbs_list.go?projectSeq="+frm.projectSeq.value;
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
			bbsContents		:frm.bbsContents.value
			
		};
		

		$.ajax({
		    type:"POST",
		    url:"/proc/week_bbs_comment_edit_do.go",
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
				url:"/proc/week_file_delete.go",
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
				str += '		<button type="button" class="del-btn" onclick="weekBbs.fileDelete('+bbsEditForm.bbsSeq.value+',\''+ json.path +'/'+ json.fileName +'\','+idx+')">X</button>';
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
				url:"/proc/week_bbs_delete.go",
				dataType:"json",
				data:param,
				success:function(json){
					alert(json.message);
					if(bbsCommentSeq>0){
						document.location.href="/"+url+"/week_bbs_view.go?projectSeq="+frm.projectSeq.value+"&bbsSeq="+frm.bbsSeq.value;
					}else{
						document.location.href="/"+url+"/week_bbs_list.go?projectSeq="+frm.projectSeq.value;
					}
					
				}
			}); 
		}
		
	},
	
	showContext : false,
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
	    str += '	<li onclick="weekBbs.goBbsEdit(pageForm,'+key+');">수정</li>';
	    str += '	<li onclick="weekBbs.onBbsDelete(pageForm,'+key+');">삭제</li>';
	    str += '</ul>';
	    $("body").append(str);
		$("ul.context").css("display","block");
		$("ul.context").css("top",posY);
		$("ul.context").css("left",posX);
		bbs.showContext = true;
		event.returnValue = false;
	},
	showContextCommentMenu : function(key, event,contents) {
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
	    str += '	<li onclick="weekBbs.commentSetContext(bbsEditForm,'+key+',\''+contents+'\');">수정</li>';
	    str += '	<li onclick="weekBbs.onBbsDelete(bbsEditForm);">삭제</li>';
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
			/*
			document.addEventListener('contextmenu', function(e) {
				e.preventDefault();
			}, false);
			*/
			document.addEventListener('click', function(e) {
				if (bbs.showContext == false) {
					$("ul.context").remove();
				}
				bbs.showContext = false;
				//e.preventDefault();
			}, false);
		} else {
			/*
			document.attachEvent('oncontextmenu', function() {
				window.event.returnValue = false;
			});
			*/
			document.attachEvent('onclick', function() {
				if (bbs.showContext == false) {
					$("ul.context").remove();
				}
				bbs.showContext = false;
				window.event.returnValue = false;
			});
		}
	},
	commentSetContext : function(frm,bbsCommentSeq,bbsContents){
		
		frm.bbsCommentSeq.value = bbsCommentSeq;
		frm.bbsContents.value = bbsContents;
		
		
	}

};