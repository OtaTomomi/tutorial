package board.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import board.beans.Comment;
import board.beans.User;
import board.beans.UserComment;
import board.beans.UserMessage;
import board.service.CommentService;
//import board.beans.User;
import board.service.MessageService;
import board.service.UserService;

@WebServlet(urlPatterns = { "/home" })
public class HomeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
	ServletException{

		ArrayList<Integer> years = new ArrayList<Integer>();
		for(int i = 2000 ; i < 2020 ; i ++){
			years.add(new Integer(i));
		}
		ArrayList<Integer> months = new ArrayList<Integer>();
		for(int i = 1 ; i < 13 ; i ++){
			months.add(new Integer(i));
		}
		ArrayList<Integer> days = new ArrayList<Integer>();
		for(int i = 1 ; i < 32 ; i ++){
			days.add(new Integer(i));
		}

		request.setAttribute("years", years);
		request.setAttribute("months", months);
		request.setAttribute("days", days);
		List<UserMessage> userMessages = new MessageService().getMessage();
		List<User> users = new UserService().getUser();
		request.setAttribute("userMessages", userMessages);
		request.setAttribute("users", users);
		List<UserComment> userComments = new CommentService().getComment();
		request.setAttribute("userComments", userComments);
		request.getRequestDispatcher("home.jsp").forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();

		if(request.getParameter("deleteMessageId") != null){
			int deleteMessageId = Integer.parseInt(request.getParameter("deleteMessageId"));
			new MessageService().deleteMessage(deleteMessageId);
			response.sendRedirect("home");
		} else {

			if(isValid(request, messages) == true){

				User user = (User) session.getAttribute("loginUser");

				Comment comment = new Comment();
				comment.setText(request.getParameter("commentText"));
				comment.setUserId(user.getId());
				comment.setMessageId(Integer.parseInt(request.getParameter("messageId")));
				new CommentService().register(comment);

				response.sendRedirect("home");
			} else {
				session.setAttribute("errorMessages", messages);
				response.sendRedirect("home");
			}


		}

	}



	private boolean isValid(HttpServletRequest request, List<String> messages){
		String text = request.getParameter("commentText");

		if(StringUtils.isBlank(text) == true){
			messages.add("コメントは空白のまま投稿できません");
		}
		if(500 < text.length()){
			messages.add("コメントは500字以下で入力してください");
		}
		if(messages.size() == 0){
			return true;
		} else{
			return false;
		}
	}


}


