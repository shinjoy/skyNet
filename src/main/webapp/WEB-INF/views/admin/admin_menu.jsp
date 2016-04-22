<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">
 	$(document).ready(function() {
		
	 	$.ajax({
			type:"POST",
			url:"/admin/menu.go",
			dataType:"json",
			
			success:function(json){
				var list =json.projectList;
				var str = '';
				 str += ' <ul class="m1"> ';
				 for(var i=0;i<list.length;i++){
				
					str += ' 	<li class="menu" id="menu'+list[i].projectSeq+'"> ';
					str += ' 		<a href="#" onclick="aside.togleSub('+list[i].projectSeq+');"> ';
					str += ' 			<dl> ';
					str += ' 				<dt class="bullet"> ';
					str += ' 					<div class="bullet-image"><img src="/images/statistics.png" style=""></div> ';
					str += ' 				</dt> ';
					str += ' 				<dd class="menu-name"> ';
					str += ' 					<span>'+list[i].projectName+'</span> ';
					str += ' 				</dd> ';
					str += ' 				<dd class="arrow"> ';
					str += ' 					<img src="/images/arrow_bottom.png"> ';
					str += ' 				</dd> ';
					str += ' 			</dl> ';
					str += ' 		</a> ';
				//	str += ' </dl> ';
					str += ' 		<ul class="m2" id="u'+list[i].projectSeq+'"> ';
					str += '			 <li id="menu-sub'+list[i].projectSeq+'1"> ';
					str += ' 				<a href="/admin/progress.go?projectSeq='+list[i].projectSeq+'"> ';
					str += ' 					<dl> ';
					str += ' 						<dt class="bullet"> ';
					str += ' 							<div class="bullet-image"><img src="/images/arrow_right.png"></div> ';
					str += ' 						</dt> ';
					str += ' 						<dd class="menu-name"> ';
					str += ' 							<span>프로젝트 진행 사항</span> ';
					str += ' 						</dd> ';
					str += ' 					</dl> ';
					str += ' 				</a> ';
					str += ' 			</li> ';
					str += ' 			<li id="menu-sub'+list[i].projectSeq+'2"> ';
					str += ' 				<a href="/admin/bbs_list.go?projectSeq='+list[i].projectSeq+'"> ';
					str += ' 					<dl> ';
					str += ' 						<dt class="bullet"> ';
					str += ' 							<div class="bullet-image"><img src="/images/arrow_right.png"></div> ';
					str += ' 						</dt> ';
					str += ' 						<dd class="menu-name"> ';
					str += ' 							<span>버그/요청 게시판</span> ';
					str += ' 						</dd> ';
					str += ' 					</dl> ';
					str += ' 				</a> ';
					str += '			 </li> ';
					str += ' 			<li id="menu-sub'+list[i].projectSeq+'3"> ';
					str += ' 				<a href="/admin/files_list.go?projectSeq='+list[i].projectSeq+'"> ';
					str += '					<dl> ';
					str += '						<dt class="bullet"> ';
					str += '							<div class="bullet-image"><img src="/images/arrow_right.png"></div> ';
					str += '						</dt> ';
					str += '						<dd class="menu-name"> ';
					str += '								<span>자료실</span> ';
					str += '						</dd> ';
					str += '					</dl> ';
					str += '				</a> ';
					str += '			</li> ';
					str += '			<li id="menu-sub'+list[i].projectSeq+'4"> ';
					str += '				<a href="/admin/admin_bbs_list.go?projectSeq='+list[i].projectSeq+'"> ';
					str += '					<dl> ';
					str += '						<dt class="bullet"> ';
					str += '							<div class="bullet-image"><img src="/images/arrow_right.png"></div> ';
					str += '						</dt> ';
					str += '						<dd class="menu-name"> ';
					str += '							<span>공지 게시판</span> ';
					str += '						</dd> ';
					str += '					</dl> ';
					str += '				</a> ';
					str += '			</li> ';
					str += '			<li id="menu-sub'+list[i].projectSeq+'5"> ';
					str += '				<a href="/admin/week_bbs_list.go?projectSeq='+list[i].projectSeq+'"> ';
					str += '					<dl> ';
					str += '						<dt class="bullet"> ';
					str += '							<div class="bullet-image"><img src="/images/arrow_right.png"></div> ';
					str += '						</dt> ';
					str += '						<dd class="menu-name"> ';
					str += '							<span>금주 진행사항</span> ';
					str += '						</dd> ';
					str += '					</dl> ';
					str += '				</a> ';
					str += '			</li> ';
					str += '	</ul> ';
					str += ' </li> ';
				
		
					
																	 
					 
				 }
				str += '</ul> ';
				$("#nav").html(str);	
			}
		}); 
	}); 
</script>

		<aside id="nav">
			
		</aside>
