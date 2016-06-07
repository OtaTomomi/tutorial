package board.dao;

import static board.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import board.beans.Message;
import board.beans.SplitDate;
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
	public SplitDate getMessageMinDate(Connection connection){


		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DATE_FORMAT(min(insert_date),'%Y,%c,%e') as min_date FROM messages;");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<SplitDate> ret = toUserMessageMinDate(rs);
			return ret.get(0);


		}catch (SQLException e){
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}

	}
	public List<SplitDate> toUserMessageMinDate(ResultSet rs)
			throws SQLException{
		List<SplitDate> ret = new ArrayList<SplitDate>();
		try{
			while (rs.next()){
				String minDate = rs.getString("min_date");
				String[] minFormatDate = minDate.split(",", 0);

				SplitDate date = new SplitDate();
				date.setYear(minFormatDate[0]);
				date.setMonth(minFormatDate[1]);
				date.setDay(minFormatDate[2]);

				ret.add(date);

			}
			return ret;
		} finally {
			close(rs);
		}

	}
	public SplitDate getMessageMaxDate(Connection connection){


		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DATE_FORMAT(max(insert_date),'%Y,%c,%e') as max_date FROM messages ;");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<SplitDate> ret = toUserMessageMaxDate(rs);

			return ret.get(0);


		}catch (SQLException e){
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}

	}
	public List<SplitDate> toUserMessageMaxDate(ResultSet rs)
			throws SQLException{
		List<SplitDate> ret = new ArrayList<SplitDate>();
		try{
			while (rs.next()){
				String maxDate = rs.getString("max_date");
				String[] maxFormatDate = maxDate.split(",", 0);

				SplitDate date = new SplitDate();
				date.setYear(maxFormatDate[0]);
				date.setMonth(maxFormatDate[1]);
				date.setDay(maxFormatDate[2]);
				ret.add(date);

			}
			return ret;
		} finally {
			close(rs);
		}

	}


}
