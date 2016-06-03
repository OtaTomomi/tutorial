package board.dao;

import static board.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import board.beans.Table;
import board.exception.SQLRuntimeException;

public class TableDao {




	public List<Table> getTable(Connection connection, int num){

		PreparedStatement ps = null;

		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM messages_comments");
			sql.append(" ORDER BY insert_date DESC limit " + num + ";");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<Table> ret = toTableList(rs);
			return ret;
		} catch (SQLException e){
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}
	}

	private List<Table> toTableList(ResultSet rs)
		throws SQLException{

		List<Table> ret = new ArrayList<Table>();
		try{
			while (rs.next()){
				String subject = rs.getString("subject");
				String text = rs.getString("text");
				String category = rs.getString("category");
				int id = rs.getInt("id");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				String userName = rs.getString("user_name");
				String commentText = rs.getString("comment_text");
				int commentId = rs.getInt("comment_id");
				String commentName = rs.getString("comment_name");
				Timestamp commentInsertDate = rs.getTimestamp("comment_insert_date");

				Table table = new Table();
				table.setSubject(subject);
				table.setText(text);
				table.setCategory(category);
				table.setId(id);
				table.setInsertDate(insertDate);
				table.setUserName(userName);
				table.setCommentText(commentText);
				table.setCommentId(commentId);
				table.setCommentName(commentName);
				table.setCommentInsertDate(commentInsertDate);

				ret.add(table);

			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
