<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/header.jsp"  %>



<script type="text/javascript">


</script>

<style>
.select-search { border:1px solid #ddd; padding-bottom:4px;}
</style>


</head>

<body>

	<article class="popup">

		<header>
			 <h1>업체 ${companySeq==0 ? '등록':'수정' }</h1>
		</header>
	
		<div class="contents-main">
			<form method="post" name="dataForm" onsubmit="return company.companyEdit(this); return false;">
			<input type="hidden" name="companySeq" value="${companySeq}">
				<dl class="edit">
					<dt style="width:50px;">업체명</dt>
					<dd><input type="text" class="itext" style="width:300px;" name="companyName" value="${company.companyName}"></dd>
					<dt style="width:50px;">비고</dt>
					<dd><textarea name="companyComment" style="width:300px; height:100px; padding:3px;">${company.companyComment}</textarea></dd>
				</dl>
				
				<div class="tool-bar">
					<button type="submit" class="btn btn-blue" style="width:100px;">저장</button>
				</div>
			</form>
		</div>

	</article>

</body>
</html>