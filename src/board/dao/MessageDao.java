package board.dao;

import static board.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import board.beans.Message;
import board.exception.SQLRuntimeException;

public class MessageDao {
	public void insert(Connection connection, Message message){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO messages ( ");
			sql.append("subject");
			sql.append(", text");
			sql.append(", category");
			sql.append(", update_date");
			sql.append(", user_id");
			sql.append(", insert_date");
			sql.append(") VALUE (");
			sql.append("?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(", ?");
			sql.append(", CURRENT_TIMESTAMP ");
			sql.append(");");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, message.getSubject());
			ps.setString(2, message.getText());
			ps.setString(3, message.getCategory());
			ps.setInt(4, message.getUserId());


			ps.executeUpdate();
		} catch (SQLException e){
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
	public void deleteMessage(Connection connection,int id){
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();

			sql.append("DELETE FROM messages");
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
