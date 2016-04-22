<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/header.jsp"  %>



<script type="text/javascript">

$(function() {
    $.datepicker.setDefaults( $.datepicker.regional[ "ko" ] );
    $( ".datepicker" ).datepicker(
        {
        	dateFormat: 'yy-mm-dd',
 	       showButtonPanel: true
        }
    );
});
</script>

<style>
.select-search { border:1px solid #ddd; padding-bottom:4px;}
</style>


</head>

<body>

	<article class="popup">

		<header>
			 <h1>일정 관리</h1>
		</header>
	
		<div class="contents-main">
			<form method="post" name="popForm" onsubmit="return todo.todoEdit(this); return false;">
			<input type="hidden" name="todoSeq" value="${todo.todoSeq}">
				<dl class="edit">
					<dt style="width:80px;">MODULE</dt>
					<dd>${todo.module }</dd>
					<dt style="width:80px;">PROCESS</dt>
					<dd>${todo.process }</dd>
					<dt style="width:80px;">TODO</dt>
					<dd>${todo.todo }</dd>
					<dt style="width:80px;">PART</dt>
					<dd>${todo.todoPart }</dd>
					<dt style="width:80px;">작업내용</dt>
					<dd><textarea style="width:300px; height:100px;" name="todoComment">${todo.todoComment}</textarea></dd>
					<dt style="width:80px;">작업상태</dt>
					<dd>
						<select	name="todoStatus">
							<option value="0" ${todo.todoStatus=='0' ? 'selected=/"selected/"' : '' }>진행예정</option>
							<option value="1" ${todo.todoStatus=='1' ? 'selected=/"selected/"' : '' }>진행중</option>
							<option value="2" ${todo.todoStatus=='2' ? 'selected=/"selected/"' : '' }>진행완료</option>
							<option value="3" ${todo.todoStatus=='3' ? 'selected=/"selected/"' : '' }>보류</option>
							<option value="4" ${todo.todoStatus=='4' ? 'selected=/"selected/"' : '' }>취소</option>
						</select>
					</dd>
					<dt style="width:80px;">완료일</dt>
					<dd><input type="text" class="itext datepicker" style="width:200px;" name="todoFinishday" value="${todo.todoFinishday}"></dd>
				</dl>
				
				<div class="tool-bar">
					<button type="submit" class="btn btn-blue" style="width:100px;">저장</button>
				</div>
			</form>
		</div>

	</article>

</body>
</html>