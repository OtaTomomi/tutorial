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
import board.beans.SplitDate;
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

		List<UserComment> userComments = new CommentService().getComment();
		SplitDate minDate = new MessageService().getMessageMinDate();
		SplitDate maxDate = new MessageService().getMessageMaxDate();

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

		if (request.getParameter("search") != null){
			int beginYear = Integer.parseInt(request.getParameter("beginYear"));
			int beginMonth = Integer.parseInt(request.getParameter("beginMonth"));
			int beginDay = Integer.parseInt(request.getParameter("beginDay"));
			int endYear = Integer.parseInt(request.getParameter("endYear"));
			int endMonth = Integer.parseInt(request.getParameter("endMonth"));
			int endDay = Integer.parseInt(request.getParameter("endDay"));




			SplitDate searchBeginDate = new SplitDate();
			SplitDate searchEndDate = new SplitDate();
			searchBeginDate.setYear(request.getParameter("beginYear"));
			searchBeginDate.setMonth(request.getParameter("beginMonth"));
			searchBeginDate.setDay(request.getParameter("beginDay"));
			searchEndDate.setYear(request.getParameter("endYear"));
			searchEndDate.setMonth(request.getParameter("endMonth"));
			searchEndDate.setDay(request.getParameter("endDay"));
			String category = request.getParameter("category");

			if(StringUtils.isBlank(category) == true){

				List<UserMessage> userMessageMatchDateOnly = new MessageService().getMessageMatchDateOnly(beginYear, beginMonth, beginDay, endYear, endMonth, endDay);
				System.out.println(userMessageMatchDateOnly.size());
				request.setAttribute("userMessages", userMessageMatchDateOnly);
				request.setAttribute("userComments", userComments);
				request.setAttribute("years", years);
				request.setAttribute("months", months);
				request.setAttribute("days", days);
				request.setAttribute("beginDate",searchBeginDate);
				request.setAttribute("endDate",searchEndDate);
				request.getRequestDispatcher("home.jsp").forward(request, response);

			}else{
				List<UserMessage> userMessageMatchDateAndCategories = new MessageService().getMessageMatchDateAndCategory(category,beginYear, beginMonth, beginDay, endYear, endMonth, endDay);

				request.setAttribute("userMessages", userMessageMatchDateAndCategories);
				request.setAttribute("userComments", userComments);
				request.setAttribute("years", years);
				request.setAttribute("months", months);
				request.setAttribute("days", days);
				request.setAttribute("beginDate",searchBeginDate);
				request.setAttribute("endDate",searchEndDate);
				request.setAttribute("category",category);
				request.getRequestDispatcher("home.jsp").forward(request, response);
			}

		} else {
			request.setAttribute("years", years);
			request.setAttribute("months", months);
			request.setAttribute("days", days);
			request.setAttribute("beginDate", minDate);
			request.setAttribute("endDate", maxDate);
			List<UserMessage> userMessages = new MessageService().getMessage();
			List<User> users = new UserService().getUser();
			request.setAttribute("userMessages", userMessages);
			request.setAttribute("users", users);
			request.setAttribute("userComments", userComments);
			request.getRequestDispatcher("home.jsp").forward(request, response);

		}


	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();
		String reset = request.getParameter("reset");


		if (reset != null){

			response.sendRedirect("home");
		}  else {
			if(request.getParameter("deleteMessageId") != null){
				int deleteMessageId = Integer.parseInt(request.getParameter("deleteMessageId"));
				new MessageService().deleteMessage(deleteMessageId);
				response.sendRedirect("home");
			}
			if(request.getParameter("deleteCommentId") != null){
				int deleteCommentId = Integer.parseInt(request.getParameter("deleteCommentId"));
				new CommentService().deleteComment(deleteCommentId);
				response.sendRedirect("home");


			}
			if(request.getParameter("messageId") != null){

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


