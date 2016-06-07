package board.service;

import static board.utils.CloseableUtil.*;
import static board.utils.DBUtil.*;

import java.sql.Connection;
//import java.util.List;
import java.util.List;

import board.beans.Message;
import board.beans.SplitDate;
import board.beans.UserMessage;
//import board.beans.UserMessage;
import board.dao.MessageDao;
import board.dao.UserMessageDao;

public class MessageService {

	public void register(Message message){


		Connection connection = null;
		try{
			connection = getConnection();

			MessageDao messageDao = new MessageDao();
			messageDao.insert(connection, message);

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
	private static final int LIMIT_NUM = 1000;

	public List<UserMessage> getMessage(){

		Connection connection = null;
		try{
			connection = getConnection();

			UserMessageDao userMessageDao = new UserMessageDao();
			List<UserMessage> ret = userMessageDao.getUserMessage(connection,LIMIT_NUM);


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
	public List<UserMessage> getMessageMatchDateAndCategory(String category,int beginYear,
			int beginMonth,int beginDay,int endYear ,int endMonth,int endDay){

		Connection connection = null;
		try{
			connection = getConnection();
			String beginDate = String.valueOf(beginYear) + "-" +
					String.valueOf(beginMonth) + "-" + String.valueOf(beginDay);
				String endDate = String.valueOf(endYear) + "-" +
						String.valueOf(endMonth) + "-" + String.valueOf(endDay) + " 23:59:59";

			UserMessageDao userMessageDao = new UserMessageDao();
			List<UserMessage> ret = userMessageDao.getUserMessageMatchDateAndCategory(connection,category,
					beginDate,endDate);


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
		public List<UserMessage> getMessageMatchDateOnly(int beginYear,
				int beginMonth,int beginDay,int endYear ,int endMonth,int endDay){
			String beginDate = String.valueOf(beginYear) + "-" +
				String.valueOf(beginMonth) + "-" + String.valueOf(beginDay);
			String endDate = String.valueOf(endYear) + "-" +
					String.valueOf(endMonth) + "-" + String.valueOf(endDay) + " 23:59:59";

			Connection connection = null;
			try{
				connection = getConnection();

				UserMessageDao userMessageDao = new UserMessageDao();
				List<UserMessage> ret = userMessageDao.getUserMessageMatchDateOnly(connection,
						beginDate,endDate);


				//commit(connection);

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
		public void deleteMessage(int id){

			Connection connection = null;
			try{
				connection = getConnection();


				MessageDao messageDao = new MessageDao();
				messageDao.deleteMessage(connection, id);

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
		public SplitDate getMessageMaxDate(){

			Connection connection = null;
			try{
				connection = getConnection();

				MessageDao messageDao = new MessageDao();
				SplitDate maxDate = messageDao.getMessageMaxDate(connection);

				commit(connection);

				return maxDate;
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
		public SplitDate getMessageMinDate(){

			Connection connection = null;
			try{
				connection = getConnection();

				MessageDao messageDao = new MessageDao();
				SplitDate minDate = messageDao.getMessageMinDate(connection);

				commit(connection);

				return minDate;
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


}
