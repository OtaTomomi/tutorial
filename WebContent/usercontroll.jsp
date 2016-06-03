<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー管理画面</title>



</head>
<body>
<div class = "main-contents">
<c:if test = "${ not empty errorMessages }" >
	<div class = "errorMessages">
		<ul>
			<c:forEach items = "${ errorMessages }" var = "message">
				<li><c:out value = "${ message }" /></li>
			</c:forEach>
		</ul>
	</div>
	<c:remove var = "errorMessages" scope = "session" />
</c:if>
</div>
	<table border = "1">
		<tr>
			<th>ログインID</th><th>名称</th><th>利用制限</th>
		</tr>
		<c:forEach items = "${ users }" var = "user" >
				<tr>
					<td><c:out value = "${ user.loginId }"  /></td>
					<td>
					<form action = "edituser" method = "get">
						<input type = "hidden" name = "userId" value = "${ user.id }">
						<c:out value = "${ user.name }"  />
						<input type = "submit" value = 編集画面へ>
					</form>
					</td>
					<td>
					<form action = "usercontroll" method = "post" >
						<c:if test = "${ user.useable == true }"  >
							<input type = "hidden" name = "useable" value = "false">
							<input type = "hidden" name = "userId" value = "${ user.id }">
							<input type = "submit" name = "useableButton" value = "停止" onClick = "return confirm('本当に${ user.name }を停止してよろしいですか？')">
						</c:if>
					</form>
					<form action = "usercontroll" method = "post">
						<c:if test = "${ user.useable == false }"  >
							<input type = "hidden" name = "useable" value = "true">
							<input type = "hidden" name = "userId" value = "${ user.id }">
							<input type = "submit" name = "useableButton" value = "復活" onClick = "return confirm('本当に${ user.name }を復活させてよろしいですか？')">
						</c:if>
					</form>
					</td>
					<td>
						<form action = "usercontroll" method = "post">
						<input type = "hidden" name = "delete" value = "1">
						<input type = "hidden" name = "userId" value = "${ user.id }">
						<input type = "submit" name = "delete" value = "削除" onClick = "return confirm('本当に${ user.name }を削除してよろしいですか？')">
						</form>

					</td>
				</tr>



		</c:forEach>

	</table>

	<br />

	<a href = "signup">新規登録画面</a><br />
	<a href = "home">ホーム画面</a><br />





</body>
</html>