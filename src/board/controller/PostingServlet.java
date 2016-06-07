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

import board.beans.Message;
import board.beans.User;
import board.service.MessageService;

@WebServlet(urlPatterns = { "/posting" })
public class PostingServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
	ServletException {
		request.getRequestDispatcher("posting.jsp").forward(request, response);
	}


	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();

		User user = (User) session.getAttribute("loginUser");

		Message message = new Message();
		message.setSubject(request.getParameter("subject"));
		message.setText(request.getParameter("text"));
		message.setCategory(request.getParameter("category"));
		message.setUserId(user.getId());


		if(isValid(request, messages) == true){

			new MessageService().register(message);

			response.sendRedirect("home");
		} else {
			session.setAttribute("message",message);
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("posting");
		}
	}



	private boolean isValid(HttpServletRequest request, List<String> messages){
		String subject = request.getParameter("subject");
		String text = request.getParameter("text");
		String category = request.getParameter("category");

		if(StringUtils.isBlank(subject) == true){
			messages.add("件名を入力してください");
		}
		if(50 < subject.length()){
			messages.add("件名は50字以下で入力してください");
		}
		if(StringUtils.isBlank(text) == true){
			messages.add("本文を入力してください");
		}
		if(10000 < text.length()){
			messages.add("本文は10000字以下で入力してください");
		}
		if(StringUtils.isBlank(category) == true){
			messages.add("カテゴリーを入力してください");
		}
		if(10 < category.length()){
			messages.add("カテゴリーは10字以下で入力してください");
		}
		if(messages.size() == 0){
			return true;
		} else{
			return false;
		}
	}


}
