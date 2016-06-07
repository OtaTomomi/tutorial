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

@WebServlet(urlPatterns = { "/edituser" })
public class EditUserServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{

		if(request.getParameter("userId") != null){

			int editId = Integer.parseInt(request.getParameter("userId"));

			User editUser = new UserService().getUserInfomation(editId);

			if(editUser != null){

				request.setAttribute("editUser", editUser);

				List<Branch> branches = new BranchService().getBranch();
				request.setAttribute("branches", branches);
				List<Position> positions = new PositionService().getPosition();
				request.setAttribute("positions", positions);
				request.getRequestDispatcher("edituser.jsp").forward(request, response);


			}else{
				request.setAttribute("error", 1);
				request.getRequestDispatcher("edituser.jsp").forward(request, response);


			}

		}else{
			request.setAttribute("error", 1);
			request.getRequestDispatcher("edituser.jsp").forward(request, response);


		}

	}
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{

		List<String> messages =  new ArrayList<String>();


		HttpSession session = request.getSession();


		User editUser = getEditUser(request);

		List<Branch> branches = new BranchService().getBranch();
		List<Position> positions = new PositionService().getPosition();





		if(isValid(request, messages) == true){
			try{
			} catch (NoRowsUpdatedRuntimeException e) {
				session.removeAttribute("editUser");
				messages.add("他の人によって更新されています。最新のデータを表示しました。データを確認してください");

				session.setAttribute("errorMessages", messages);
				request.getRequestDispatcher("edituser.jsp").forward(request, response);
			}
			request.setAttribute("branches", branches);
			request.setAttribute("positions", positions);
			request.setAttribute("editUser", editUser);
			request.getRequestDispatcher("editusercheck.jsp").forward(request, response);
		} else {
			session.setAttribute("errorMessages", messages);
			request.setAttribute("branches", branches);
			request.setAttribute("positions", positions);
			request.setAttribute("editUser", editUser);
			request.getRequestDispatcher("edituser.jsp").forward(request, response);
		}

	}
	private User getEditUser(HttpServletRequest request)throws IOException, ServletException {
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

		return editUser;
	}

	private boolean isValid(HttpServletRequest request, List<String> messages){
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		String passwordCheck = request.getParameter("passwordCheck");
		String name = request.getParameter("name");
		int branchId = Integer.parseInt(request.getParameter("branchId"));
		int positionId = Integer.parseInt(request.getParameter("positionId"));
		//User user = new UserService().getCheckLoginId(loginId);


		//if(user != null){
			//messages.add("ログインIDがすでに使用されています");

		//}
		if (StringUtils.isEmpty(loginId) == true){
			messages.add("ログインIDを入力してください");

		}
		if (loginId.length() < 6 || 20 < loginId.length()){
			messages.add("ログインIDは6文字以上20文字以下で入力してください");

		}
		if (!loginId.matches("^[a-zA-Z0-9]+$")){
			messages.add("ログインIDは半角英数字で入力してください");

		}

		if ( 0 < password.length() && password.length() < 6 || 255 < loginId.length()){
			messages.add("パスワードは6文字以上255文字以下で入力してください");

		}
		if (!password.equals(passwordCheck)){
			messages.add("パスワードが確認用パスワードと一致していません");

		}
		//下のif文いらなさそう
		if (!loginId.matches("^[a-zA-Z0-9 -/:-@--]+$") && 0 < password.length() ){
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

