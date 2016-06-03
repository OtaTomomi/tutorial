<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規投稿</title>
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
	<form action = "posting" method = "post"><br />
		<div class = "form-area">
			件名<textarea name = "subject" cols = "100" rows = "1" class = "subject-box">
			</textarea>
			本文<textarea name = "text" cols = "100" rows = "10" class = "text-box">
			</textarea>
			カテゴリー<textarea name = "category" cols = "100" rows = "1" class = "category-box">
			</textarea>
			<input type = "submit" value = "投稿">
		</div>
	</form>
<a href = "home">戻る</a>
</body>
</html>