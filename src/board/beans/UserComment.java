package board.beans;

import java.io.Serializable;
import java.util.Date;

public class UserComment implements Serializable{
	private int commentId;
	private int messageId;
	private String commentText;
	private int userId;
	private Date commentInsertDate;
	private String commentName;

	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getCommentInsertDate() {
		return commentInsertDate;
	}
	public void setCommentInsertDate(Date commentInsertDate) {
		this.commentInsertDate = commentInsertDate;
	}
	public String getCommentName() {
		return commentName;
	}
	public void setCommentName(String commentName) {
		this.commentName = commentName;
	}

}
