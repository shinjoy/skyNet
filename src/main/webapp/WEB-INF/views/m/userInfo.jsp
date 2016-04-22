<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<form name="userForm">
	<input type="hidden" name="userId" value="${USER_ID}">
	<input type="hidden" name="userName" value="${USER_NAME}">
	<input type="hidden" name="userType" value="${USER_TYPE}">
	<input type="hidden" name="userCompany" value="${COMPANY_SEQ}">
</form>
