<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/m/head.jsp" %>

<script type="text/javascript">
	$(document).ready(function() {
		bbs.bbsedit(bbsEditForm);
		//if (true) {
		if (utils.isMobile()) {
			$(".bbs-tools").css("display","table");
			$(".bbs-tools-pc").css("display","none");
		} else {
			$(".bbs-tools").css("display","none");
			$(".bbs-tools-pc").css("display","block");
		}
		var options = {
			//target :		'#user-join-result',
			beforeSubmit :	bbs.formCheck,
			success :		bbs.formSuccess
		};
		$('#bbsEditForm').ajaxForm(options);
	});
</script>

<style>
</style>

</head>
<body>
<%@ include file="/WEB-INF/views/m/userInfo.jsp"  %>
<form method="post" name="bbsEditForm" id="bbsEditForm" class="bbs-edit-form" enctype="multipart/form-data" action="/proc/file_upload.go">
<input type="hidden" name="userId" value="${USER_ID}">
<input type="hidden" name="projectSeq" value="${projectSeq}">
<input type="hidden" name="bbsSeq" value="${bbsSeq}">
<input type="hidden" name="bbsType" value="${bbsType}">
<input type="hidden" name="bbsType2" value="${USER_TYPE==1 ? 1 : 0}">
<input type="hidden" name="type" value="${projectSeq}">
<input type="hidden" name="isThumb" value="0">
<input type="hidden" name="addThumb" value="1">
<input type="hidden" name="idx" value="0">
<header>
	<ul>
		<li class="top-left">
			<a href="/m/bbs_list.go?projectSeq=${projectSeq}&bbsType=${bbsType}">
				<img src="/images/ic_top_back_arrow.png">
				<span class="top-menu">목록</span>
			</a>
		</li>
		<li class="top-center">
			&nbsp;
		</li>
		<li class="top-right">
			<button type="button" class="btn-txt" onclick="bbs.bbsSave(this.form,'m');">등록</button>
		</li>
	</ul>
</header>

<article class="bbs-edit-article">

	
	<ul class="tab tab2">
		<li onclick="bbs.tabEdit(bbsEditForm,1);" id="tab1" ${bbsType==1 ? 'class="active"' : ''}>요청</li>
		<li onclick="bbs.tabEdit(bbsEditForm,2);" id="tab2" ${bbsType==2 ? 'class="active"' : ''}>버그</li>
	</ul>

	<div class="bbs-edit">
		<p class="title">
			<input type="text" name="bbsTitle" placeholder="제목을 입력해주세요.">
		</p>
		<p class="contents">
			<textarea name="contents" placeholder="내용을 입력해주세요."></textarea>
		</p>
	
		<ul class="file-add">
		</ul>
	</div>
	
	<ul class="bbs-tools">
		<li><button type="button" onclick="util.getPhoto();"><img src="/images/btn_picture.png"></button></li>
		<li><button type="button" onclick="util.getCamera();"><img src="/images/btn_camera.png"></button></li>
		<li><button type="button" onclick="util.getMovie();"><img src="/images/btn_movie.png"></button></li>
	</ul>
	<ul class="bbs-tools-pc">
		<li>
			<input type="file" name="photo" class="file" style="cursor:pointer;" onchange="document.getElementById('upload-btn').click();">
			<button type="submit" id="upload-btn" class="btn-hide"></button>
		</li>
	</ul>

</article>

</form>

</body>
</html>