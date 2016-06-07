package board.dao;

import static board.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import board.beans.UserMessage;
import board.exception.SQLRuntimeException;

public class UserMessageDao {

	public List<UserMessage> getUserMessage(Connection connection, int num){

		PreparedStatement ps = null;

		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users_messages");
			sql.append(" ORDER BY insert_date DESC limit " + num + ";");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = toUserMessageList(rs);
			return ret;
		} catch (SQLException e){
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}
	}
	public List<UserMessage> getUserMessageMatchDateAndCategory(Connection connection,String category,String beginDate,String endDate){

		PreparedStatement ps = null;

		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users_messages");
			sql.append(" WHERE category = ?");
			sql.append(" AND insert_date");
			sql.append(" BETWEEN ");
			sql.append(" ? ");
			sql.append(" AND ? ");
			sql.append(" ORDER BY insert_date DESC ;");

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1,category);
			ps.setString(2, beginDate);
			ps.setString(3, endDate);
			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = toUserMessageList(rs);
			return ret;
		} catch (SQLException e){
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}
	}

		public List<UserMessage> getUserMessageMatchDateOnly(Connection connection,String beginDate,String endDate){

			PreparedStatement ps = null;


			try{
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT * FROM users_messages");
				sql.append(" WHERE insert_date");
				sql.append(" BETWEEN ");
				sql.append(" ? ");
				sql.append(" AND ? ");
				sql.append(" ORDER BY insert_date DESC ;");



				ps = connection.prepareStatement(sql.toString());

				ps.setString(1, beginDate);
				ps.setString(2, endDate);

				ResultSet rs = ps.executeQuery();

				List<UserMessage> ret = toUserMessageList(rs);
				return ret;
			} catch (SQLException e){
				throw new SQLRuntimeException(e);

			} finally {
				close(ps);
			}
	}

	private List<UserMessage> toUserMessageList(ResultSet rs)
		throws SQLException{

		List<UserMessage> ret = new ArrayList<UserMessage>();
		try{
			while (rs.next()){
				String subject = rs.getString("subject");
				String text = rs.getString("text");
				String category = rs.getString("category");
				int id = rs.getInt("id");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				String userName = rs.getString("user_name");

				UserMessage userMessage = new UserMessage();
				userMessage.setSubject(subject);
				userMessage.setText(text);
				userMessage.setCategory(category);
				userMessage.setId(id);
				userMessage.setInsertDate(insertDate);
				userMessage.setUserName(userName);

				ret.add(userMessage);

			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
