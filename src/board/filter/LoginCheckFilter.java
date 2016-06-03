package board.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import board.beans.User;

@WebFilter(urlPatterns = {"/edituser","/edituserchek",
		"/home","/messagesearch","/posting","/signup","/usercontroll"})
//@WebFilter("/*")
public class LoginCheckFilter implements Filter{

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if(isLogined(request) == false){
			redirectToLoginPage(request, response);
			return;
		}
		chain.doFilter(request, response);
	}

	private void redirectToLoginPage(ServletRequest request, ServletResponse response) {
		List<String> messages = new ArrayList<String>();
		messages.add("ログインからやり直してください");
		request.setAttribute("errorMessages", messages);
		try {

			request.getRequestDispatcher("login.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isLogined(ServletRequest request) {
		User user = (User) ((HttpServletRequest) request).getSession().getAttribute("loginUser");
		if (user == null){
			return false;
		} else {
			return true;
		}

	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
