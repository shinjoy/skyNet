<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> -->
<!--[if IE 6]><html lang="ko" class="no-js old ie6"><![endif]-->
<!--[if IE 7]><html lang="ko" class="no-js old ie7"><![endif]-->
<!--[if IE 8]><html lang="ko" class="no-js old ie8"><![endif]-->
<!--[if IE 9]><html lang="ko" class="no-js ie9"><![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--><html lang="ko" class="no-js modern"><!--<![endif]-->

 <% request.setCharacterEncoding("utf-8"); %>
<%
 response.setHeader("Cache-Control","no-cache"); 
 response.setHeader("Pragma","no-cache"); 
 response.setDateHeader("Expires",0); 
%> 

<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="Content-Script-Type" content="text/javascript" />
	<meta http-equiv="Content-Style-Type" content="text/css" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>SkyNet</title>
	
>

	<script type="text/javascript" src="/lib/jquery/jquery-2.1.1.js"></script>
	<script type="text/javascript" src="/lib/jquery/jquery.form.js"></script>


<style>
	body { padding:20px; background-color:#fff; }
	h1 {font-weight:bold;font-size:14px;}
	h2 {font-weight:bold;font-size:14px; margin-bottom:5px; }
	ol.my-form li {
		list-style:decimal;
		font-weight:bold;
		font-size:16px;
		margin-left:30px;
	}
	.div-title { border-bottom:1px solid #000; font-size:30px; font-weight:bold; margin-top:100px; color : red ;}
	.api { min-width:1100px; clear:both; margin-top:20px; }
	.api dt { width:200px; }
	.api .in { float:left; }
	.api .out { float:left; width:600px; }
	.api .in dt { clear:left; float:left; font-size:12px; }
	.api .in dd { float:left; font-size:12px; font-weight:normal; }
	.result-view { border:1px solid #aaa; padding:10px; clear:both; word-break:break-all; font-size:12px; font-weight:normal; line-height:20px; }
	.real { color:#f40; min-height:26px; }
	input { width:264px !important; }
</style>

<script>

	$(document).ready(function() {
		/* 폼 ajax전송 : http://malsup.com/jquery/form/#ajaxForm */
		var options = {
			//target :		'#user-join-result',
			beforeSubmit :	formCheck,
			success :		formSuccess
		};
		$('#join').ajaxForm(options);
		$('#login').ajaxForm(options);
		$('#find_pw').ajaxForm(options);
		$('#find_id').ajaxForm(options);
		
		
		$('#logout').ajaxForm(options);
		$('#drop').ajaxForm(options);
		$('#user_info').ajaxForm(options);
		$('#user_view').ajaxForm(options);
		
		$('#project_list').ajaxForm(options);
		$('#project_View').ajaxForm(options);
		$('#bbs_list').ajaxForm(options);
		$('#bbs_edit_do').ajaxForm(options);
		$('#bbs_view').ajaxForm(options);
		$('#bbs_comment_edit_do').ajaxForm(options);
		

		$('#data_room').ajaxForm(options);
		$('#data_edit_do').ajaxForm(options);
		$('#data_delete').ajaxForm(options);
		$('#data_room_view').ajaxForm(options);
		
		
		
		
		$('#todo_list').ajaxForm(options);
		$('#photo_upload').ajaxForm(options);
		$('#file_delete').ajaxForm(options);
		
		
		$('#form_list').ajaxForm(options);
		$('#form_edit_do').ajaxForm(options);
		$('#form_delete').ajaxForm(options);
	
		
	});
	
	function formCheck(formData, jqForm, options) {
		$("#"+resultDiv+"-result").html("");
		return true; 
	}
	function formSuccess(responseText, statusText, xhr, $form) {
		//alert('status: ' + statusText + '\n\nresponseText: \n' + responseText );
		var json = JSON.parse(responseText);
		try {
			$("#"+resultDiv+"-result").html(responseText);
		} catch (e) {
            alert(json.message); 
		}
	}

	var resultDiv;
	function formSubmit(div) {
		resultDiv = div;
	}

</script>

</head>
<body>

<h1 class="ad_title">SkyNet API</h1>

<div class="div-title">
	회원관리
</div>

 <ol class="my-form">
<%-- 	<li class="api">
		<c:set var="url" value="join"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<h1>회원가입</h1>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userId</dt>	<dd><input type="text" name="userId" placeholder="userId" class="bb"></input></dd>
				<dt>userName</dt><dd><input type="text" name="userName" placeholder="userName" class="bb"></input></dd>
				<dt>birthday</dt><dd><input type="text" name="birthday" placeholder="나이" class="bb"></input></dd>
				<dt>gender</dt><dd><input type="text" name="gender" placeholder="gender" class="bb"></input></dd>
				<dt>osType</dt><dd><input type="text" name="osType" placeholder="osType" class="bb"></input></dd>
				<dt>phoneNumber</dt><dd><input type="text" name="phoneNumber" placeholder="페이스북키" class="bb"></input></dd>
				<dt>pushKey</dt><dd><input type="text" name="pushKey" placeholder="pushKey" class="bb"></input></dd>
				<dt>deviceName</dt><dd><input type="text" name="deviceName" placeholder="deviceName" class="bb"></input></dd>
				<dt>deviceId</dt><dd><input type="text" name="deviceId" placeholder="deviceId" class="bb"></input></dd>
				<dt>osVersion</dt><dd><input type="text" name="osVersion" placeholder="osVersion" class="bb"></input></dd>
				<dt>appVersion</dt><dd><input type="text" name="appVersion" placeholder="appVersion" class="bb"></input></dd>
				<dt>loginFacebook</dt><dd><input type="text" name="loginFacebook" placeholder="페이스북 로그인 1" class="bb"></input></dd>
				<dt>usePushservice</dt><dd><input type="text" name="usePushservice" placeholder="푸시사용 1 안함 0" class="bb"></input></dd>
				<dt>&nbsp;</dt>	<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</li>
	 --%>
	<li class="api">
		<c:set var="url" value="login"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<h1>모바일 로그인</h1>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userId</dt>	<dd><input type="text" name="userId" placeholder="userId" class="bb"></input></dd>
			
				<dt>userPw</dt><dd><input type="text" name="userPw" placeholder="userPw" class="bb"></input></dd>
				<dt>&nbsp;</dt>	<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</li>
		
	<li class="api">
		<c:set var="url" value="find_pw"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<h1>비밀번호찾기</h1>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userId</dt>	<dd><input type="text" name="userId" placeholder="userId" class="bb"></input></dd>
			
				<dt>userPhone</dt><dd><input type="text" name="userPhone" placeholder="userPhone" class="bb"></input></dd>
				<dt>&nbsp;</dt>	<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</li>
		<li class="api">
		<c:set var="url" value="find_id"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<h1>아이디찾기</h1>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
			
				<dt>userPhone</dt><dd><input type="text" name="userPhone" placeholder="userPhone" class="bb"></input></dd>
				<dt>&nbsp;</dt>	<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</li>
<%-- 		
	<li class="api">
		<c:set var="url" value="logout"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<h1>모바일 로그아웃</h1>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userId</dt>	<dd><input type="text" name="userId" placeholder="userId" class="bb"></input></dd>
			
				<dt>&nbsp;</dt>	<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</li> --%>
	
<div class="div-title">
	메인화면
</div>
	<li class="api">
		<c:set var="url" value="project_list"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<h1>프로젝트 리스트</h1>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
			
				<dt>page</dt><dd><input type="text" name="page" placeholder="page" class="bb"></input></dd>
				<dt>userId</dt><dd><input type="text" name="userId" placeholder="userId" class="bb"></input></dd>
				<dt>&nbsp;</dt>	<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</li>
<li class="api">
		<c:set var="url" value="project_View"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<h1>프로젝트 보기</h1>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
			
				<dt>projectSeq</dt><dd><input type="text" name="projectSeq" placeholder="projectSeq" class="bb"></input></dd>
				<dt>userId</dt><dd><input type="text" name="userId" placeholder="userId" class="bb"></input></dd>
				<dt>&nbsp;</dt>	<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</li>
<div class="div-title">
	게시판
</div>	

	
	<li class="api">
		<c:set var="url" value="bbs_list"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<h1>게시판 리스트</h1>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userId</dt>	<dd><input type="text" name="userId" placeholder="userId" class="bb"></input></dd>
				<dt>bbsType</dt>	<dd><input type="text" name="bbsType" placeholder="bbsType" class="bb"></input></dd>
				<dt>bbsType2</dt>	<dd><input type="text" name="bbsType2" placeholder="bbsType2" class="bb"></input></dd>
				<dt>projectSeq</dt>	<dd><input type="text" name="projectSeq" placeholder="projectSeq" class="bb"></input></dd>
				<dt>page</dt>	<dd><input type="text" name="page" placeholder="page" class="bb"></input></dd>
				<dt>sort</dt>	<dd><input type="text" name="sort" placeholder="sort" class="bb"></input></dd>
				<dt>colName</dt>	<dd><input type="text" name="colName" placeholder="colName" class="bb"></input></dd>
				<dt>keyword</dt>	<dd><input type="text" name="keyword" placeholder="keyword" class="bb"></input></dd>
			
				<dt>&nbsp;</dt>	<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</li>
	<li class="api">
		<c:set var="url" value="bbs_view"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<h1>게시판 상세보기</h1>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userId</dt>	<dd><input type="text" name="userId" placeholder="userId" class="bb"></input></dd>
				<dt>bbsSeq</dt>	<dd><input type="text" name="bbsSeq" placeholder="bbsSeq" class="bb"></input></dd>
				<dt>page</dt>	<dd><input type="text" name="page" placeholder="page" class="bb"></input></dd>
			
				<dt>&nbsp;</dt>	<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</li>
	<li class="api">
		<c:set var="url" value="bbs_edit_do"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<h1>게시판 등록</h1>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userId</dt>	<dd><input type="text" name="userId" placeholder="userId" class="bb"></input></dd>
				<dt>bbsSeq</dt>	<dd><input type="text" name="bbsSeq" placeholder="bbsSeq" class="bb"></input></dd>
				<dt>projectSeq</dt>	<dd><input type="text" name="projectSeq" placeholder="projectSeq" class="bb"></input></dd>
				<dt>bbsType</dt>	<dd><input type="text" name="bbsType" placeholder="bbsType" class="bb"></input></dd>
				<dt>bbsType2</dt>	<dd><input type="text" name="bbsType2" placeholder="bbsType2" class="bb"></input></dd>
				<dt>bbsTitle</dt>	<dd><input type="text" name="bbsTitle" placeholder="bbsTitle" class="bb"></input></dd>
				<dt>bbsContents</dt>	<dd><input type="text" name="bbsContents" placeholder="bbsContents" class="bb"></input></dd>
				<dt>sendSms</dt>	<dd><input type="text" name="sendSms" placeholder="sendSms" class="bb"></input></dd>
				<dt>grade</dt>	<dd><input type="text" name="grade" placeholder="grade" class="bb"></input></dd>
				<dt>fileName</dt>	<dd><input type="file" name="fileName" placeholder="fileName" class="bb"></input></dd>
				<dt>fileName</dt>	<dd><input type="file" name="fileName" placeholder="fileName" class="bb"></input></dd>
			
			
				<dt>&nbsp;</dt>	<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</li>
	<li class="api">
		<c:set var="url" value="bbs_delete"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<h1>게시판 삭제</h1>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userId</dt>	<dd><input type="text" name="userId" placeholder="userId" class="bb"></input></dd>
				<dt>bbsSeq</dt>	<dd><input type="text" name="bbsSeq" placeholder="bbsSeq" class="bb"></input></dd>
				<dt>bbsCommentSeq</dt>	<dd><input type="text" name="bbsCommentSeq" placeholder="bbsCommentSeq" class="bb"></input></dd>
			
			
				<dt>&nbsp;</dt>	<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</li>
	<li class="api">
		<c:set var="url" value="bbs_comment_edit_do"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<h1>댓글 등록</h1>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userId</dt>	<dd><input type="text" name="userId" placeholder="userId" class="bb"></input></dd>
				<dt>bbsSeq</dt>	<dd><input type="text" name="bbsSeq" placeholder="bbsSeq" class="bb"></input></dd>
				<dt>bbsCommentSeq</dt>	<dd><input type="text" name="bbsCommentSeq" placeholder="bbsCommentSeq" class="bb"></input></dd>
				<dt>bbsContents</dt>	<dd><input type="text" name="bbsContents" placeholder="bbsContents" class="bb"></input></dd>
			
				<dt>&nbsp;</dt>	<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</li>
	
	
	
</ol> 
<div class="div-title">
	자료실
</div>	

	
	<li class="api">
		<c:set var="url" value="data_room"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<h1>자료실 리스트</h1>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userId</dt>	<dd><input type="text" name="userId" placeholder="userId" class="bb"></input></dd>
				<dt>dataType</dt>	<dd><input type="text" name="dataType" placeholder="dataType" class="bb"></input></dd>
				
				<dt>projectSeq</dt>	<dd><input type="text" name="projectSeq" placeholder="projectSeq" class="bb"></input></dd>
				<dt>page</dt>	<dd><input type="text" name="page" placeholder="page" class="bb"></input></dd>
			
				<dt>&nbsp;</dt>	<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</li>
	<li class="api">
		<c:set var="url" value="data_room_view"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<h1>자료실 상세보기</h1>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userId</dt>	<dd><input type="text" name="userId" placeholder="userId" class="bb"></input></dd>
				<dt>dataSeq</dt>	<dd><input type="text" name="dataSeq" placeholder="dataSeq" class="bb"></input></dd>
				
				<dt>&nbsp;</dt>	<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</li>
	<li class="api">
		<c:set var="url" value="data_edit_do"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<h1>자료실 등록</h1>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userId</dt>	<dd><input type="text" name="userId" placeholder="userId" class="bb"></input></dd>
				<dt>dataSeq</dt>	<dd><input type="text" name="dataSeq" placeholder="dataSeq" class="bb"></input></dd>
				<dt>projectSeq</dt>	<dd><input type="text" name="projectSeq" placeholder="projectSeq" class="bb"></input></dd>
				<dt>dataType</dt>	<dd><input type="text" name="dataType" placeholder="dataType" class="bb"></input></dd>
			
				<dt>dataTitle</dt>	<dd><input type="text" name="dataTitle" placeholder="dataTitle" class="bb"></input></dd>
				<dt>dataContents</dt>	<dd><input type="text" name="dataContents" placeholder="dataContents" class="bb"></input></dd>
				<dt>dataFileName</dt>	<dd><input type="text" name="dataFileName" placeholder="dataFileName" class="bb"></input></dd>
				
			
			
				<dt>&nbsp;</dt>	<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</li>
	<li class="api">
		<c:set var="url" value="data_delete"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<h1>자료실 삭제</h1>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>userId</dt>	<dd><input type="text" name="userId" placeholder="userId" class="bb"></input></dd>
				<dt>dataSeq</dt>	<dd><input type="text" name="dataSeq" placeholder="dataSeq" class="bb"></input></dd>
				
			
				<dt>&nbsp;</dt>	<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</li>
	
</ol> 
<div class="div-title">
	파일업로드
</div>
<ol class="my-form">
	<li class="api">
		<c:set var="url" value="photo_upload"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go" enctype="multipart/form-data">
			<h1>이미지등록</h1>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>photo</dt>		<dd><input type="file" name="photo" placeholder="photo" class="bb"></input></dd>
				<dt>path</dt>		<dd><input type="text" name="path" placeholder="path" class="bb" value="profile"></input></dd>
			
				<dt>&nbsp;</dt>		<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					{"message":"사진이 등록되었습니다.","result":true,"path":"/files/profile/201509/","photo":"aa47dde63331b4015e629b7788ff6e9a.jpg"} <br>
					{"message":"e.getMessage().","result":false}
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</li>
	<li class="api">
		<c:set var="url" value="file_delete"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go" >
			<h1>파일삭제</h1>
			<h2 class="page-title">/m/${url}.go</h2>
			<dl class="in">
				<dt>photo</dt>		<dd><input type="text" name="photo" placeholder="photo" class="bb"></input></dd>
				
			
				<dt>&nbsp;</dt>		<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					{"message":"사진이 등록되었습니다.","result":true,"path":"/files/profile/201509/","photo":"aa47dde63331b4015e629b7788ff6e9a.jpg"} <br>
					{"message":"e.getMessage().","result":false}
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</li>
</ol>

<div class="div-title">
	todo
</div>
<ol class="my-form">
	<li class="api">
		<c:set var="url" value="todo_list"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<h1>todoList</h1>
			<h2 class="page-title">/admin/${url}.go</h2>
			<dl class="in">
				<dt>startday</dt>	<dd><input type="text" name="startday" placeholder="startday" class="bb"></input></dd>
				<dt>endday</dt>	<dd><input type="text" name="endday" placeholder="endday" class="bb"></input></dd>
				
			
				<dt>&nbsp;</dt>	<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</li>
</ol>	

<div class="div-title">
	서식자료실
</div>
<ol class="my-form">
	<li class="api">
		<c:set var="url" value="form_list"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go" >
			<h1>서식자료실 리스트</h1>
			<h2 class="page-title">/admin/${url}.go</h2>
			<dl class="in">
				<dt>formType</dt>	<dd><input type="text" name="formType" placeholder="formType" class="bb"></input></dd>
				<dt>keyword</dt>	<dd><input type="text" name="keyword" placeholder="keyword" class="bb"></input></dd>
				<dt>page</dt>	<dd><input type="text" name="page" placeholder="page" class="bb"></input></dd>
				
			
				<dt>&nbsp;</dt>	<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</li>
	<li class="api">
		<c:set var="url" value="form_edit_do"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go" enctype="multipart/form-data">
			<h1>서식자료실 등록/수정</h1>
			<h2 class="page-title">/admin/${url}.go</h2>
			<dl class="in">
				<dt>formType</dt>	<dd><input type="text" name="formType" placeholder="formType" class="bb"></input></dd>
				<dt>formSeq</dt>	<dd><input type="text" name="formSeq" placeholder="formSeq" class="bb"></input></dd>
				<dt>formTitle</dt>	<dd><input type="text" name="formTitle" placeholder="formTitle" class="bb"></input></dd>
				<dt>formFileName</dt>	<dd><input type="file" name="formFileName" placeholder="formFileName" class="bb"></input></dd>
		
				<dt>&nbsp;</dt>	<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</li>
	<li class="api">
		<c:set var="url" value="form_delete"></c:set>
		<form method="post" id="${url}" name="${url}Form" action="/m/${url}.go">
			<h1>서식자료실 삭제</h1>
			<h2 class="page-title">/admin/${url}.go</h2>
			<dl class="in">
				
				<dt>formSeq</dt>	<dd><input type="text" name="formSeq" placeholder="formSeq" class="bb"></input></dd>
			
				<dt>&nbsp;</dt>	<dd><button type="submit" onclick="formSubmit('${url}');" class="bb round green" style="width:274px;">확인</button></dd>
			</dl>
			<div class="out">
				<div class="result-view">
					
				</div>
				<div id="${url}-result" class="result-view real"></div>
			</div>
			<div class="clear"></div>
		</form>
	</li>
</ol>	

</body>
</html>