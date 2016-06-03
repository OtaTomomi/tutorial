package board.beans;

import java.io.Serializable;
import java.util.Date;

public class UserMessage implements Serializable{
	private static final long serialVersionUID = 1L;

	private String subject;
	private String text;
	private String category;
	private int id;
	private Date insertDate;
	private String userName;

	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}



}
