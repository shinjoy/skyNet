<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/m/head.jsp" %>

<script type="text/javascript">
	$(document).ready(function() {
		files.edit(fileEditForm);
		/*
		//if (true) {
		if (utils.isMobile()) {
			$(".bbs-tools").css("display","table");
			$(".bbs-tools-pc").css("display","none");
		} else {
			$(".bbs-tools").css("display","none");
			$(".bbs-tools-pc").css("display","block");
		}
		*/
		var options = {
			//target :		'#user-join-result',
			beforeSubmit :	files.formCheck,
			success :		files.formSuccess
		};
		$('#fileEditForm').ajaxForm(options);
	});
</script>

<style>
</style>

</head>
<body>

<form method="post" name="fileEditForm" id="fileEditForm" class="bbs-edit-form" enctype="multipart/form-data" action="/proc/file_upload.go">
<input type="hidden" name="userId" value="${USER_ID}">
<input type="hidden" name="projectSeq" value="${projectSeq}">
<input type="hidden" name="dataSeq" value="${dataSeq}">
<header>
	<ul>
		<li class="top-left">
			<a href="/m/files_list.go?projectSeq=${projectSeq}">
				<img src="/images/ic_top_back_arrow.png">
				<span class="top-menu">목록</span>
			</a>
		</li>
		<li class="top-center">
			&nbsp;
		</li>
		<li class="top-right">
			<button type="button" class="btn-txt" onclick="files.filesSave(this.form,'m');">등록</button>
		</li>
	</ul>
</header>

<article class="bbs-edit-article-files">

	<div class="bbs-edit">
		<p class="contents">
			<textarea name="contents" placeholder="내용을 입력해주세요."></textarea>
		</p>
	
		<ul class="file-add">
		</ul>
	</div>
	
	<!--
	<ul class="bbs-tools">
		<li><button type="button" onclick="util.getPhoto();"><img src="/images/btn_picture.png"></button></li>
		<li><button type="button" onclick="util.getCamera();"><img src="/images/btn_camera.png"></button></li>
		<li><button type="button" onclick="util.getMovie();"><img src="/images/btn_movie.png"></button></li>
	</ul>
	-->
	<ul class="bbs-tools">
		<li>
			<input type="file" name="photo"  class="file" style="cursor:pointer;" onchange="document.getElementById('upload-btn').click();">
			<button type="submit" id="upload-btn" class="btn-hide"></button>
		</li>
	</ul>

</article>

</form>

</body>
</html>