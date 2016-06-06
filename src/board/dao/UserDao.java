package board.dao;

import static board.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import board.beans.User;
import board.exception.NoRowsUpdatedRuntimeException;
import board.exception.SQLRuntimeException;

public class UserDao {
	public User getUser(Connection connection, String loginId,
			String password){

		PreparedStatement ps = null;

		try{
			String sql = "SELECT * FROM users WHERE login_id = ? AND password = ?;";
			ps = connection.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true){
				return null;
			} else if ( 2 <= userList.size()) {
				//同じログインID、パスワードを持つ人が２人いないかどうか(多分)
				throw new IllegalStateException(" 2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}
	public User checkLoginId(Connection connection, String loginId){

		PreparedStatement ps = null;

		try{
			String sql = "SELECT * FROM users WHERE login_id = ? ;";
			ps = connection.prepareStatement(sql);
			ps.setString(1, loginId);

			ResultSet rs = ps.executeQuery();
			List<User> userList = toUserList(rs);
			if (userList.isEmpty() == true){
				return null;
			} else if ( 2 <= userList.size()) {
				//同じログインID、パスワードを持つ人が２人いないかどうか(多分)
				throw new IllegalStateException(" 2 <= userList.size()");
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}

	}
	public List<User> getUser(Connection connection, int num){

		PreparedStatement ps = null;

		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users");
			sql.append(" ORDER BY id LIMIT " + num + ";");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<User> ret = toUserList(rs);
			return ret;
		} catch (SQLException e){
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}

	}

	private List<User> toUserList(ResultSet rs) throws SQLException {
		List<User> ret = new ArrayList<User>();

		try{
			while (rs.next()){
				int id = rs.getInt("id");
				String loginId = rs.getString("login_id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				int branchId = rs.getInt("branch_id");
				int positionId = rs.getInt("position_id");
				boolean useable = rs.getBoolean("useable");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				Timestamp updateDate = rs.getTimestamp("update_date");

				User user = new User();
				user.setId(id);
				user.setLoginId(loginId);
				user.setPassword(password);
				user.setName(name);
				user.setBranchId(branchId);
				user.setPositionId(positionId);
				user.setUseable(useable);
				user.setInsertDate(insertDate);
				user.setUpdateDate(updateDate);

				ret.add(user);
			}
			return ret;
		} finally {
			close (rs);
		}
	}

	public void insert(Connection connection, User user){
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO users ( ");
			sql.append(" login_id");
			sql.append(", password");
			sql.append(", name");
			sql.append(", branch_id");
			sql.append(", position_id");
			sql.append(", useable");
			sql.append(", insert_date ");
			sql.append(", update_date");
			sql.append(") VALUES (");
			sql.append(" ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(");");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setInt(4, user.getBranchId());
			ps.setInt(5, user.getPositionId());
			ps.setBoolean(6, user.getUseable());

			ps.executeUpdate();
		} catch (SQLException e){
			throw new SQLRuntimeException(e);
		} finally {
			close (ps);
		}

	}
	public void updateUseable(Connection connection,User user){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append(" useable = ?");
			sql.append(", update_date = CURRENT_TIMESTAMP");
			sql.append(" WHERE");
			sql.append(" id = ?;");
			//sql.append(" AND");
			//sql.append(" update_date = ?;");

			ps = connection.prepareStatement(sql.toString());

			ps.setBoolean(1, user.getUseable());
			ps.setInt(2, user.getId());
			//ps.setTimestamp(3, new Timestamp(user.getUpdateDate().getTime()));
			int count = ps.executeUpdate();
			if (count == 0){
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
	public User getUserInfomation(Connection connection,int id){
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users WHERE id = ?;");
			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			List<User> user = toUserList(rs);

			if (user.isEmpty() == true){
				return null;
			} else if ( 2 <= user.size()) {
				//同じログインID、パスワードを持つ人が２人いないかどうか(多分)
				throw new IllegalStateException(" 2 <= userList.size()");
			} else {
				return user.get(0);
			}
		} catch (SQLException e){
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}
	}

	public void updateUser(Connection connection,User user){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();

				sql.append("UPDATE users SET");
				sql.append(" login_id = ?");
				sql.append(", password = ?");
				sql.append(", name = ?");
				sql.append(", branch_id = ?");
				sql.append(", position_id = ?");
				sql.append(", update_date = CURRENT_TIMESTAMP");
				sql.append(" WHERE");
				sql.append(" id = ?;");

				ps = connection.prepareStatement(sql.toString());

				ps.setString(1, user.getLoginId());
				ps.setString(2,user.getPassword());
				ps.setString(3, user.getName());
				ps.setInt(4, user.getBranchId());
				ps.setInt(5, user.getPositionId());
				ps.setInt(6, user.getId());


			int count = ps.executeUpdate();
			if (count == 0){
				throw new NoRowsUpdatedRuntimeException();
		}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void deleteUser(Connection connection,int id){
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();

			sql.append("DELETE FROM users");
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
	public void updateUserNoPassword(Connection connection,User user){

		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();

			sql.append("UPDATE users SET");
			sql.append(" login_id = ?");
			sql.append(", name = ?");
			sql.append(", branch_id = ?");
			sql.append(", position_id = ?");
			sql.append(", update_date = CURRENT_TIMESTAMP");
			sql.append(" WHERE");
			sql.append(" id = ?;");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getName());
			ps.setInt(3, user.getBranchId());
			ps.setInt(4, user.getPositionId());
			ps.setInt(5, user.getId());


			int count = ps.executeUpdate();
			if (count == 0){
				throw new NoRowsUpdatedRuntimeException();
		}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}



}



