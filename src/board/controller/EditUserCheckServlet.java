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

import board.beans.Branch;
import board.beans.Position;
import board.beans.User;
import board.exception.NoRowsUpdatedRuntimeException;
import board.service.BranchService;
import board.service.PositionService;
import board.service.UserService;

@WebServlet(urlPatterns = { "/editusercheck" })
public class EditUserCheckServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{

	}
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{
		HttpSession session = request.getSession();

		List<String> messages =  new ArrayList<String>();
		String revision = request.getParameter("revision");

		String password = request.getParameter("password");

		User editUser = new User();
		editUser.setId(Integer.parseInt(request.getParameter("id")));
		editUser.setLoginId(request.getParameter("loginId"));
		editUser.setName(request.getParameter("name"));
		editUser.setBranchId(Integer.parseInt(request.getParameter("branchId")));
		editUser.setPositionId(Integer.parseInt(request.getParameter("positionId")));

		if(StringUtils.isBlank(password) || password.equals("/n")){
			editUser.setPassword(null);

		} else {
			editUser.setPassword(password);
		}


		List<Branch> branches = new BranchService().getBranch();
		List<Position> positions = new PositionService().getPosition();
		if(revision.equals("1")){

			request.setAttribute("editUser",editUser);
			request.setAttribute("branches", branches);
			request.setAttribute("positions", positions);
			request.getRequestDispatcher("edituser.jsp").forward(request, response);


		}else{
			try{

				if(StringUtils.isBlank(password) || password.equals("/n")){
					new UserService().updateUserNoPassword(editUser);


				} else {
					new UserService().updateUser(editUser);
				}



			} catch (NoRowsUpdatedRuntimeException e) {

				messages.add("他の人によって更新されています。最新のデータを表示しました。データを確認してください");

				session.setAttribute("errorMessages", messages);
				request.getRequestDispatcher("edituser.jsp").forward(request, response);
			}

			response.sendRedirect("usercontroll");

		}


	}
}

