package board.service;

import static board.utils.CloseableUtil.*;
import static board.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import board.beans.Comment;
import board.beans.UserComment;
import board.dao.CommentDao;
import board.dao.UserCommentDao;

public class CommentService {

	public void register(Comment comment){


		Connection connection = null;
		try{
			connection = getConnection();

			CommentDao commentDao = new CommentDao();
			commentDao.insert(connection, comment);

			commit(connection);
		} catch (RuntimeException e){
			rollback(connection);
			throw e;
		} catch (Error e){
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

	public List<UserComment> getComment(){

		Connection connection = null;
		try{
			connection = getConnection();

			UserCommentDao userCommentDao = new UserCommentDao();
			List<UserComment> ret = userCommentDao.getUserComment(connection);


			commit(connection);

			return ret;
		}catch (RuntimeException e){
			rollback(connection);
			throw e;
		} catch (Error e){
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}
	public void deleteComment(int id){

		Connection connection = null;
		try{
			connection = getConnection();


			CommentDao commentDao = new CommentDao();
			commentDao.deleteComment(connection, id);

			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e){
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}

	}



}
