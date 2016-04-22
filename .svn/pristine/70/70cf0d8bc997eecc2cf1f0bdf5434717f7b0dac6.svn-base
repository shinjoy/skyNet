<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/common/header.jsp"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
$(document).ready(function() {
	//aside.setActive(1,1);
});

</script>

</head>
<body>

<%@ include file="/WEB-INF/views/admin/admin_header.jsp"  %>

<section class="main-cover main-row">
	<section id="main">
		
		<%@ include file="/WEB-INF/views/admin/admin_menu.jsp"  %>

		<section id="contents">
			<header class="panel">
				 <b>■ Home</b>
			</header>
		
			<div class="contents-block">
			
				<table>
				<tr>
					<td>
						<dl class="title-box">
							<dt>
								<div class="tbl">
									<span>Recently registered member</span>
									<span class="more"><button type="button" class="btn" onclick="document.location.href='/admin/user/user.go';">more</button></span>
								</div>
							</dt>
							<dd style="background-color:#FFFFFF;">
								<ul>
									<c:choose>	
										<c:when test="${user.size() > 0}">
											<c:forEach var="it" items="${user}" varStatus="i">
												<a href="/admin/user/user_view.go?userId=${it.userId}">
												<li style="border:1px solid #ddd;width:20%; display: inline-block; margin-left: 4px; margin-top: 6px; "> 
													<c:forEach var="it2" items="${photo}" varStatus="p">
															<c:if test="${i.index eq p.index }">
					
																<c:choose>
																    <c:when test="${it2 !='' }">
					
																		<c:forEach var="it3" items="${it2.items}" varStatus="s">
					
																			
																				<div class="photo-list"
																					style="background-image:url('${it3.thumb}') "></div>
																		
																		
																		</c:forEach>
																	</c:when>
																	<c:otherwise>	
																		<div class="photo-list" style="background-image:url('/images/user_default.png')"></div> 
													 				 </c:otherwise>
													 			</c:choose>
					
															</c:if>
														</c:forEach>
															<b>${it.userName}(${it.userAge})</b>
															<br>
															${it.genderText} 
															<br>
															${it.regDate.substring(0,16)}
												
												 </li> 
												<a>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<li style="height:100px; text-align:center; border-top:1px solid #ddd; border-bottom:1px solid #ddd; padding-top:80px; "> 조회된 데이터가 없습니다.</li>
										</c:otherwise>												
									</c:choose>
									
								</ul>
								<div style="clear:both;"></div>

							</dd>
						</dl>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<dl class="title-box">
							<dt>
								<div class="tbl">
									<span>Recently registered mission</span>
									<span class="more"><button type="button" class="btn" onclick="document.location.href='/admin/mission/mission.go';">more</button></span>
								</div>
							</dt>
							<dd style="background-color:#FFFFFF;">
								<ul>
									<c:choose>	
										<c:when test="${mission.size() > 0}">
											<c:forEach var="it" items="${mission}" varStatus="i">
												<li class="imglist bbs">

													<a href="/admin/mission/mission_view.go?missionSeq=${it.missionSeq}">
														<div class="round-box" style="width:220px; padding:10px;">
															<table>
															<colgroup>
																<col width="60">
																<col width="*">
															</colgroup>
															<tr>
																
																<td>
																	<h2><b>${it.title} </b></h2>
																	<span class="good"><img src='/images/icon_userJoin.png' style="height:12px;"> ${it.countUser}</span>
																	<br> 
																
																	<span class="good"><img src='/images/icon_alarm.png' style="height:12px;">  ${fn:substring(it.missionPushTime,0,8)}</span>
																	[${it.missionPushDate1!=0 ? 'Mon':'' }
																	${it.missionPushDate2!=0 ? 'Tue':'' }
																	${it.missionPushDate3!=0 ? 'Wed':'' }
																	${it.missionPushDate4!=0 ? 'Thu':'' }
																	${it.missionPushDate5!=0 ? 'Fri':'' }
																	${it.missionPushDate6!=0 ? 'Sat':'' }
																	${it.missionPushDate7!=0 ? 'Sun':'' }]
																	<br>
																	${fn:substring(it.missionStartDate,0,16)} ~ ${fn:substring(it.missionEndDate,0,16)}
																	<br>
																	
																</td>
															</tr>
															</table>
															
															<hr>
															
															<div class="bbs-contents-preview">
																<c:choose>
																	<c:when test="${it.contentsText.length() > 70}">
																		${it.contentsText.substring(0,60)} ...
																	</c:when>
																	<c:otherwise>
																		${it.contentsText}
																	</c:otherwise>
																</c:choose>
																
															</div>
																  <div class="img-thumb">
																	   <c:forEach var="it2" items="${fileList}" varStatus="p">
																			<c:if test="${i.index eq p.index }">

																				<c:if test="${it2 !='' }">

																						<c:forEach var="it3" items="${it2.items}">
																						<%-- <c:set var="fileName" value="${fn:split(it3.thumb, '/')}" /> --%>
																								<div class="photo-talk" style="background-image:url('${it3.thumb}') "></div>
																						</c:forEach>
																					</c:if>

																			</c:if>
																		</c:forEach>
																	</div>
																	<hr>
															${fn:substring(it.regDate,0,16)}
					
														</div>
													</a>	
												</li>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<li style="height:100px; text-align:center; border-top:1px solid #ddd; border-bottom:1px solid #ddd; padding-top:80px; "> 조회된 데이터가 없습니다.</li>
										</c:otherwise>												
									</c:choose>
								</ul>
								<div style="clear:both;"></div>
							</dd>
						</dl>
					</td>
				</tr>
				</table>
			</div>
		</section>
	</section>
</section>

<%@ include file="/WEB-INF/views/admin/admin_footer.jsp"  %>

</body>
</html>