<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>編集確認画面</title>
</head>
<body>
<c:if test = "${ not empty errorMessages }">
	<div class = "errorMessages">
		<ul>
			<c:forEach items = "${ errorMessages }" var = "message">
				<li><c:out value = "${message}" /></li>
			</c:forEach>
		</ul>
	</div>
	<c:remove var = "errorMessages" scope = "session" />
</c:if>
<form action = "editusercheck" method = "post">
		<FONT color = "	#ff0000">まだ登録は完了していません。</FONT><br />
		以下の情報で登録します。よろしいですか？<br />
		ログインID：<c:out value ="${ editUser.loginId }"/><br />
		<c:if test = "${ empty editUser.password }">
		パスワード：変更なし<br />
		</c:if>
		<c:if test = "${ !empty editUser.password }">
		パスワード：変更あり<br />
		<%--
		<c:out value = "${ editUser.password }" /><br />
		--%>
		</c:if>
		名称：<c:out value = "${ editUser.name }" /><br />
		支店名：
		<c:forEach items = "${ branches }" var = "branch" >
			<c:if test = "${branch.id == editUser.branchId}">
				<c:out value = "${ branch.name }" /><br />
			</c:if>
		</c:forEach>
		部署・役職名：
		<c:forEach items = "${positions }" var = "position" >
			<c:if test = "${position.id == editUser.positionId}">
				<c:out value = "${ position.name }" /><br />
			</c:if>
		</c:forEach>
		<br />

		<!------------------>
		<input type = "hidden" value = "${ editUser.id }" name = id />
		<input type = "hidden" value = "${ editUser.loginId }" name = loginId />
		<input type = "hidden" value = "${ editUser.password }" type = password name = password />
		<input type = "hidden" value = "${ editUser.name }" name = name />
		<input type = "hidden" value = "${ editUser.branchId }" name = branchId />
		<input type = "hidden" value = "${ editUser.positionId }" name = positionId />
		<input type = "hidden" value = "0" name = "revision" />




	<input type = "submit" value = "この内容で登録"onClick = "confirm('登録しました')"/><br />
</form>
<form action = "editusercheck" method = "post">

		<input type = "hidden" value = "${ editUser.id }" name = id />
		<input type = "hidden" value = "${ editUser.loginId }" name = loginId />
		<input type = "hidden" value = "${ editUser.password }" type = password name = password />
		<input type = "hidden" value = "${ editUser.name }" name = name />
		<input type = "hidden" value = "${ editUser.branchId }" name = branchId />
		<input type = "hidden" value = "${ editUser.positionId }" name = positionId />
		<input type = "hidden" value = "1" name = "revision" />
	<input type  = "submit" value = "編集内容を変更" /><br />
	<a href = "usercontroll">ユーザー管理画面へ戻る</a>
</form>
</body>
</html>