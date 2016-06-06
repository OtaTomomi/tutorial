package board.service;

import static board.utils.CloseableUtil.*;
import static board.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import board.beans.User;
import board.dao.UserDao;
import board.utils.CipherUtil;

public class UserService {
	private static final int LIMIT_NUM = 1000;



	public void register (User user){

		Connection connection = null;
		try {
			connection = getConnection();

			String encPassword = CipherUtil.encrypt(user.getPassword());
			user.setPassword(encPassword);

			UserDao userDao = new UserDao();
			userDao.insert(connection, user);

			commit(connection);
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;

		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}


	public List<User> getUser(){

		Connection connection = null;
		try{
			connection = getConnection();

			UserDao userDao = new UserDao();
			List<User> ret = userDao.getUser(connection,LIMIT_NUM);

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
	public User getCheckLoginId(String loginId){

		Connection connection = null;
		try{
			connection = getConnection();

			UserDao userDao = new UserDao();
			User ret = userDao.checkLoginId(connection,loginId);

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
	public void updateUseable(User user){

		Connection connection = null;
		try{
			connection = getConnection();
			UserDao userDao = new UserDao();
			userDao.updateUseable(connection, user);

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
	public void updateUser(User user){

		Connection connection = null;
		try{
			connection = getConnection();


			String encPassword = CipherUtil.encrypt(user.getPassword());
			user.setPassword(encPassword);


			UserDao userDao = new UserDao();
			userDao.updateUser(connection, user);

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
	public void updateUserNoPassword(User user){

		Connection connection = null;
		try{
			connection = getConnection();


			UserDao userDao = new UserDao();
			userDao.updateUserNoPassword(connection, user);

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

	public User getUserInfomation(int id){

		Connection connection = null;
		try{
			connection = getConnection();

			UserDao userDao = new UserDao();
			User user = userDao.getUserInfomation(connection,id);

			commit(connection);

			return user;
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
	public void deleteUser(int id){

		Connection connection = null;
		try{
			connection = getConnection();


			UserDao userDao = new UserDao();
			userDao.deleteUser(connection, id);

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
