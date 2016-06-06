package board.dao;

import static board.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import board.beans.Comment;
import board.exception.SQLRuntimeException;

public class CommentDao {
	public void insert(Connection connection, Comment comment){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO comments ( ");
			sql.append("message_id");
			sql.append(", text");
			sql.append(", update_date");
			sql.append(", user_id");
			sql.append(", insert_date");
			sql.append(") VALUE (");
			sql.append("?");
			sql.append(", ?");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(", ?");
			sql.append(", CURRENT_TIMESTAMP ");
			sql.append(");");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, comment.getMessageId());
			ps.setString(2, comment.getText());
			ps.setInt(3, comment.getUserId());


			ps.executeUpdate();
		} catch (SQLException e){
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
	public void deleteComment(Connection connection,int id){
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();

			sql.append("DELETE FROM comments");
			sql.append(" WHERE id = ? ;");


			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, id);
			ps.executeUpdate();


		}catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


}
