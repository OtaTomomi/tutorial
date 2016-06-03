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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.beans.User;

@WebFilter(urlPatterns = {"/usercontroll","/signup","/edituser"})
public class UserCheckFilter implements Filter{

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if(isCanUserControll(request) == false){
			redirectToHome(request, response);
			return;
		}
		chain.doFilter(request, response);
	}

	private void redirectToHome(ServletRequest request, ServletResponse response) {
		List<String> messages = new ArrayList<String>();
		messages.add("権限がありません");
		HttpSession session = ((HttpServletRequest) request).getSession();
		session.setAttribute("errorMessages", messages);
		try {

			//request.getRequestDispatcher("home.jsp").forward(request, response);
			((HttpServletResponse) response).sendRedirect("home");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private boolean isCanUserControll(ServletRequest request) {
		User user = (User) ((HttpServletRequest) request).getSession().getAttribute("loginUser");
		int branchId = user.getBranchId();
		int positionId = user.getPositionId();
		if(branchId == 7 && positionId == 1){
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void destroy() {

	}



	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
