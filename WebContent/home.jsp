<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ホーム</title>
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

<div class = "header" >
メニュー
<a href = "posting">新規投稿画面</a>
<c:if test = "${ loginUser.branchId == 7 && loginUser.positionId == 1 }">
	<a href = "usercontroll">ユーザー管理画面</a>
</c:if>
<c:if test = "${ loginUser.branchId != 7 || loginUser.positionId != 1 }">
<FONT color = "#bababa">ユーザー管理画面</FONT>
</c:if>
<a href = "logout">ログアウト</a>
</div>

<br />
<div class = "search">
<form action = "home" method = "get">

	カテゴリを指定して検索<br />
	<textarea name = "category" cols = "20" rows = "1" class = "categrysearch-box"><c:out value = "${category }" /></textarea><br />
	日にちを指定して検索<br />



	<select name = "beginYear" >
		<c:forEach items = "${ years }"  var = "year">
			<option value = "${ year }"
				<c:if test = "${ year == beginDate.year }">
				 selected
				</c:if>
				><c:out value = "${ year }"  />
			</option>
		</c:forEach>
	</select>年

	<select name = "beginMonth" >
			<c:forEach items = "${ months }"  var = "month">
				<option value = "${ month }"
				<c:if test = "${ month == beginDate.month}">
				 selected
				</c:if>
				><c:out value = "${ month }"  />
				</option>
			</c:forEach>
	</select>月

	<select name = "beginDay" >
			<c:forEach items = "${ days }"  var = "day">
				<option value = "${ day }"
				<c:if test = "${ day == beginDate.day }">
				 selected
				</c:if>
				><c:out value = "${ day }"  />
				</option>
			</c:forEach>
	</select>日

	～
	<select name = "endYear" >
			<c:forEach items = "${ years }"  var = "year">
				<option value = "${ year }"<c:if test = "${ year == endDate.year }">
				 selected
				</c:if>>
				<c:out value = "${ year }"  />
				</option>
			</c:forEach>
	</select>年

	<select name = "endMonth" >
			<c:forEach items = "${ months }"  var = "month">
				<option value = "${ month }"
				<c:if test = "${month == endDate.month}">
				 selected
				</c:if>><c:out value = "${ month }"  />
				</option>
			</c:forEach>
	</select>月

	<select name = "endDay" >
			<c:forEach items = "${ days }"  var = "day">
				<option value = "${ day }"
				<c:if test = "${ day == endDate.day }">
				 selected
				</c:if>><c:out value = "${ day }"  />
				</option>
			</c:forEach>
	</select>日
	<input type = "hidden" value = "1" name = "search">
	<input type = "submit" value = "検索">

</form>

<form action = "home" method = "post">
		<input type = "hidden" value = "1" name = "reset">
		<input type = "submit" value = "検索リセット">
</form>


</div>

<br />
<div class = "tables">
	<c:forEach items = "${ userMessages }" var = "userMessage" >
	<table border = "1" >
		<tr>
			<td>件名：<c:out value = "${ userMessage.subject }" /></td>
		</tr>
		<tr>
			<td>本文：<br /><pre><c:out value = "${ userMessage.text }" /></pre></td>
		</tr>
		<tr>
			<td>カテゴリー：<c:out value = "${ userMessage.category }" /></td>
		</tr>
		<tr>
			<td>投稿日時：<fmt:formatDate value = "${ userMessage.insertDate }"
			pattern = "yyyy/MM/dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td>投稿者：<c:out value = "${ userMessage.userName }" />
				<c:if test = "${ loginUser.branchId == 7 && loginUser.positionId == 2 }">
					<form action = "home" method = "post"><br />
						<input type = "hidden" value ="${ userMessage.id }" name = "deleteMessageId">
						<input type = "submit" value = "投稿削除"
						onClick = "return confirm('本当にこの投稿を削除してよろしいですか？')">
					</form>
				</c:if>
				<c:if test = "${ loginUser.positionId != 2 }">
					<c:if test = "${ loginUser.positionId == 3 }">
						<c:forEach items = "${ users }" var = "user">
							<c:if test = "${ userMessage.userName == user.name }">
								<c:if test = "${ loginUser.branchId == user.branchId && user.positionId ==4}">
							 		<form action = "home" method = "post"><br />
										<input type = "hidden" value ="${ userMessage.id }" name = "deleteMessageId">
										<input type = "submit" value = "投稿削除"
										onClick = "return confirm('本当にこの投稿を削除してよろしいですか？')">
									</form>
							 	</c:if>
							</c:if>
						</c:forEach>
					</c:if>
					<c:if test = "${ userMessage.userName == loginUser.name }">
						<form action = "home" method = "post"><br />
							<input type = "hidden" value ="${ userMessage.id }" name = "deleteMessageId">
							<input type = "submit" value = "投稿削除"
							onClick = "return confirm('本当にこの投稿を削除してよろしいですか？')">
						</form>
					 </c:if>
				</c:if>
				</td>
			</tr>
		<c:forEach items = "${ userComments }" var = "userComment" >
			<c:if test = "${ userMessage.id == userComment.messageId }" >
			<tr>
				<td>
					<div class = comment>
						<div class = commentText>
							<pre><c:out value = "${ userComment.commentText }" /></pre><br />
						</div>
						<div class = commentName>
							<c:out value = "${ userComment.commentName }" /><br />
						</div>
						<div class = commentInsertDate>
							<fmt:formatDate value = "${ userComment.commentInsertDate }"
							pattern = "yyyy/MM/dd HH:mm:ss"/>
						</div>

							<c:if test = "${ loginUser.branchId == 7 && loginUser.positionId == 2 }">
								<form action = "home" method = "post"><br />
									<input type = "hidden" value ="${ userComment.commentId }" name = "deleteCommentId">
									<input type = "submit" value = "コメント削除"
									onClick = "return confirm('本当にこのコメントを削除してよろしいですか？')">
								</form>
							</c:if>
							<c:if test = "${loginUser.positionId != 2 }">
								<c:if test = "${ loginUser.positionId == 3 }">
									<c:forEach items = "${ users }" var = "user">
										<c:if test = "${ userComment.commentName == user.name }">
											<c:if test = "${ loginUser.branchId == user.branchId
											 && user.positionId ==4}">
							 					<form action = "home" method = "post"><br />
													<input type = "hidden" value ="${ userComment.commentId }" name = "deleteCommentId">
													<input type = "submit" value = "コメント削除"
													onClick = "return confirm('本当にこのコメントを削除してよろしいですか？')">
												</form>
							 				</c:if>
										</c:if>
									</c:forEach>
								</c:if>
								<c:if test = "${ userComment.commentName == loginUser.name }">
									<form action = "home" method = "post"><br />
										<input type = "hidden" value ="${ userComment.commentId }" name = "deleteCommentId">
										<input type = "submit" value = "コメント削除"
										onClick = "return confirm('本当にこのコメントを削除してよろしいですか？')">
									</form>
								 </c:if>
							</c:if>
					</div>
			</c:if>
		</c:forEach>
		<tr>
			<td>

			<form action = "home" method = "post"><br />
				<div class = "comment-area">
					コメント投稿:<br />
					<textarea name = "commentText" cols = "70" rows = "5" class = "comment-box"></textarea>
					<input type = "hidden" value ="${ userMessage.id }" name = messageId>
					<input type = "submit" value = "コメント投稿">
				</div>
			</form>
			</td>
				</tr>


</table>

	<br />

	</c:forEach>
</div>
<c:if test = "${ userMessages.size() == 0 }">
検索に一致する投稿はありません
</c:if>



</body>
</html>