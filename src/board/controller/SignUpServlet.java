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
import board.service.BranchService;
import board.service.PositionService;
import board.service.UserService;

@WebServlet(urlPatterns = {"/signup"})
public class SignUpServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
	ServletException {


		List<Branch> branches = new BranchService().getBranch();
		request.setAttribute("branches", branches);
		List<Position> positions = new PositionService().getPosition();
		request.setAttribute("positions", positions);

		request.getRequestDispatcher("signup.jsp").forward(request, response);


	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
	ServletException{
		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();

		User newUser = new User();
		newUser.setLoginId(request.getParameter("loginId"));
		newUser.setPassword(request.getParameter("password"));
		newUser.setName(request.getParameter("name"));
		newUser.setBranchId(Integer.parseInt(request.getParameter("branchId")));
		newUser.setPositionId(Integer.parseInt(request.getParameter("positionId")));
		newUser.setUseable(Boolean.valueOf(true));

		if(isValid(request, messages) == true){
			new UserService().register(newUser);

			response.sendRedirect("usercontroll");
		} else {
			session.setAttribute("newUser", newUser);
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("signup");
		}
	}
	private boolean isValid(HttpServletRequest request, List<String>
	messages){


		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		String passwordCheck = request.getParameter("passwordCheck");
		String name = request.getParameter("name");
		int branchId = Integer.parseInt(request.getParameter("branchId"));
		int positionId = Integer.parseInt(request.getParameter("positionId"));
		User user = new UserService().getCheckLoginId(loginId);


		if(user != null){
			messages.add("ログインIDがすでに使用されています");

		}

		if (StringUtils.isEmpty(loginId) == true){
			messages.add("ログインIDを入力してください");

		}
		if (loginId.length() < 6 || 20 < loginId.length()){
			messages.add("ログインIDは6文字以上20文字以下で入力してください");

		}
		if (!loginId.matches("^[a-zA-Z0-9]+$")){
			messages.add("ログインIDは半角英数字で入力してください");

		}
		if (StringUtils.isEmpty(password) == true){
			messages.add("パスワードを入力してください");

		}
		if (password.length() < 6 || 255 < loginId.length()){
			messages.add("パスワードは6文字以上255文字以下で入力してください");

		}
		if (!password.equals(passwordCheck)){
			messages.add("パスワードが確認用パスワードと一致していません");

		}
		//下のif文いらなさそう
		if (!loginId.matches("^[a-zA-Z0-9 -/:-@--]+$")){
			messages.add("パスワードは半角文字で入力してください");

		}
		if (name.length() > 10){
			messages.add("名称は10文字以下で入力してください");
		}
		if (StringUtils.isEmpty(name) == true){
			messages.add("名称を入力してください");
		}
		if (branchId == 0){
			messages.add("支店名を選択してください");

		}
		if (positionId == 0){
			messages.add("部署・役職名を選択してください");

		}
		if (branchId == 7 && positionId == 3){
			messages.add("本社配属は人事総務部もしくは情報管理部です");

		}
		if (branchId == 7 && positionId == 4){
			messages.add("本社配属は人事総務部もしくは情報管理部です");

		}
		if (positionId == 1 && branchId != 7){
			messages.add("人事総務部は本社配属です");

		}
		if (positionId == 2 && branchId != 7){
			messages.add("情報管理部は本社配属です");

		}
		if (messages.size() == 0){
			return true;
		} else {
			return false;
		}
	}


}
