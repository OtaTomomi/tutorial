<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>${ editUser.name }さんの編集画面</title>

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




<form action = "edituser" method = "post">
		<label for = "loginId" >ログインID</label>
		<input name = "loginId" value = "${ editUser.loginId }" id = "loginId" /><br />

		<label for = "name" >名称</label>
		<input name = "name" value = "${ editUser.name }"  id = "name" /><br />

		<label for = "password" >パスワード</label>
		<input name = "password"  type = "password" id = "password" /><br />

		<label for = "passwordCheck">パスワード(確認用)</label>
		<input name = "passwordCheck" type = "password" id = "passwordCheck" /><br />

		支店名
		<select name = "branchId" >
			<c:forEach items = "${ branches }" var = "branch" >
				<option value = "${ branch.id }"
					<c:if test = "${branch.id == editUser.branchId}">
					selected
					</c:if>
				><c:out value = "${ branch.name }"  />
				</option>
			</c:forEach>
		</select>

		部署・役職名
		<select name = "positionId" >
			<c:forEach items = "${ positions }" var = "position" >
				<option value = "${ position.id }"
					<c:if test = "${position.id == editUser.positionId}">
					selected
					</c:if>
				>
				<c:out value = "${ position.name }"  />

				</option>
			</c:forEach>
		</select>
		<input type = "hidden" value = "${ editUser.id }" name = "id" /><br />

		<br />

	<input type = "submit" value = "登録" /><br />
	<a href = "usercontroll">戻る</a>


</form>





</body>
</html>