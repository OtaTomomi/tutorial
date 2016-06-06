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
			件名<br />
			<textarea name = "subject" cols = "100" rows = "1" class = "class-box"><c:if test = "${ message != null }"><c:out value = "${ message.subject}" /></c:if></textarea>
			<br />
			本文<br />
			<textarea name = "text" cols = "100" rows = "10" class = "text-box"><c:if test = "${ message != null }"><c:out value = "${ message.text}" /></c:if></textarea>
			<br />
			カテゴリー<br />
			<input type = "text" name = "category"<c:if test = "${ message != null }"> value = "${ message.category}"</c:if> >
			<c:remove var = "message" scope = "session" />
			<br />
			<input type = "submit" value = "投稿">
		</div>
	</form>
<a href = "home">戻る</a>
</body>
</html>