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

import board.beans.User;
import board.service.LoginServiceBBS;

@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{



		request.getRequestDispatcher("login.jsp").forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{

		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");

		LoginServiceBBS loginService = new LoginServiceBBS();
		User user = loginService.login(loginId, password);
		HttpSession session = request.getSession();


		if (user != null){
			boolean useable = user.getUseable();
			if(useable == false){
				List<String> messages = new ArrayList<String>();
				messages.add("このアカウントは停止されています。");
				session.setAttribute("loginId",loginId);
				session.setAttribute("errorMessages", messages);
				response.sendRedirect("login");
			} else {
				session.setAttribute("loginUser", user);
				response.sendRedirect("home");

			}
		} else {
			List<String> messages = new ArrayList<String>();
			messages.add("ログインに失敗しました");
			session.setAttribute("loginId",loginId);
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("login");
		}
	}

}