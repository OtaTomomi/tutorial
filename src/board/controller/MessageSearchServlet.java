package board.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import board.beans.UserComment;
import board.beans.UserMessage;
import board.service.CommentService;
import board.service.MessageService;

@WebServlet(urlPatterns = { "/messagesearch" })
public class MessageSearchServlet extends HttpServlet{
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


		List<UserComment> userComments = new CommentService().getComment();
		request.setAttribute("userComments", userComments);
		int beginYear = Integer.parseInt(request.getParameter("beginYear"));
		int beginMonth = Integer.parseInt(request.getParameter("beginMonth"));
		int beginDay = Integer.parseInt(request.getParameter("beginDay"));
		int endYear = Integer.parseInt(request.getParameter("endYear"));
		int endMonth = Integer.parseInt(request.getParameter("endMonth"));
		int endDay = Integer.parseInt(request.getParameter("endDay"));
		String category = request.getParameter("category");

		if(StringUtils.isBlank(category) == true){

			List<UserMessage> userMessageMatchDateOnly = new MessageService().getMessageMatchDateOnly(beginYear, beginMonth, beginDay, endYear, endMonth, endDay);
			request.setAttribute("userMessages", userMessageMatchDateOnly);
			request.setAttribute("userComments", userComments);
			request.setAttribute("years", years);
			request.setAttribute("months", months);
			request.setAttribute("days", days);
			request.getRequestDispatcher("home.jsp").forward(request, response);

		}else{

			//System.out.println(request.getParameter("category"));
			List<UserMessage> userMessageMatchDateAndCategories = new MessageService().getMessageMatchDateAndCategory(category,beginYear, beginMonth, beginDay, endYear, endMonth, endDay);

			request.setAttribute("userMessages", userMessageMatchDateAndCategories);
			request.setAttribute("userComments", userComments);
			request.setAttribute("years", years);
			request.setAttribute("months", months);
			request.setAttribute("days", days);
			request.getRequestDispatcher("home.jsp").forward(request, response);

		}
	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{

		String reset = request.getParameter("reset");
		if (reset != null){
			response.sendRedirect("home");
		}
	}

}
