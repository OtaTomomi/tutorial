package board.dao;

import static board.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import board.beans.UserComment;
import board.exception.SQLRuntimeException;

public class UserCommentDao {

	public List<UserComment> getUserComment(Connection connection){

		PreparedStatement ps = null;

		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users_comments");
			sql.append(" ORDER BY comment_insert_date;");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserComment> ret = toUserCommentList(rs);
			return ret;
		} catch (SQLException e){
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}
	}

	private List<UserComment> toUserCommentList(ResultSet rs)
		throws SQLException{

		List<UserComment> ret = new ArrayList<UserComment>();
		try{
			while (rs.next()){
				int commentId = rs.getInt("comment_id");
				int messageId = rs.getInt("message_id");
				String commentText = rs.getString("comment_text");
				Timestamp commentInsertDate = rs.getTimestamp("comment_insert_date");
				String commentName = rs.getString("comment_name");

				UserComment userComment = new UserComment();
				userComment.setCommentId(commentId);
				userComment.setMessageId(messageId);
				userComment.setCommentText(commentText);
				userComment.setCommentInsertDate(commentInsertDate);
				userComment.setCommentName(commentName);

				ret.add(userComment);

			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
