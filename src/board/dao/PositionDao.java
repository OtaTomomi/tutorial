package board.dao;

import static board.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import board.beans.Position;
import board.exception.SQLRuntimeException;


public class PositionDao {

	public List<Position> getPosition(Connection connection, int num){

		PreparedStatement ps = null;

		try{
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM positions");
			sql.append(" ORDER BY id LIMIT " + num + ";");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<Position> ret = toPositionList(rs);
			return ret;
		} catch (SQLException e){
			throw new SQLRuntimeException(e);

		} finally {
			close(ps);
		}

	}
	private List<Position> toPositionList(ResultSet rs)
			throws SQLException{

			List<Position> ret = new ArrayList<Position>();
			try{
				while (rs.next()){
					int id = rs.getInt("id");
					String name = rs.getString("name");

					Position position = new Position();
					position.setId(id);
					position.setName(name);

					ret.add(position);
				}
				return ret;

			} finally {
				close(rs);
			}
		}

}
