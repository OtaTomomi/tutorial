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

import board.beans.Branch;
import board.beans.Position;
import board.beans.User;
import board.exception.NoRowsUpdatedRuntimeException;
import board.service.BranchService;
import board.service.PositionService;
import board.service.UserService;

@WebServlet(urlPatterns = {"/usercontroll"})
public class UserControllServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
	ServletException {
		List<Branch> branches = new BranchService().getBranch();
		request.setAttribute("branches", branches);
		List<Position> positions = new PositionService().getPosition();
		request.setAttribute("positions", positions);
		List<User> users = new UserService().getUser();
		request.setAttribute("users", users);
		request.getRequestDispatcher("usercontroll.jsp").forward(request, response);

	}
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
	ServletException{


		List<String> messages =  new ArrayList<String>();
		String delete = request.getParameter("delete");
		if(delete != null){
			int userId = Integer.parseInt(request.getParameter("userId"));
			new UserService().deleteUser(userId);

			response.sendRedirect("usercontroll");

		} else {
			User useableChengeUser = getUserUserable(request);
			HttpSession session = request.getSession();


			try{
				new UserService().updateUseable(useableChengeUser);

			} catch (NoRowsUpdatedRuntimeException e) {
				messages.add("他の人によって更新されています。最新のデータを表示しました。データを確認してください");

				session.setAttribute("errorMessages", messages);
				response.sendRedirect("usercontroll");
			}

			response.sendRedirect("usercontroll");


		}


	}
	private User getUserUserable(HttpServletRequest request)throws IOException, ServletException {
		//HttpSession session = request.getSession();
		User useableChengeUser = new User();
		useableChengeUser.setId(Integer.parseInt(request.getParameter("userId")));
		useableChengeUser.setUseable(Boolean.valueOf(request.getParameter("useable")));
		return useableChengeUser;
	}

}
