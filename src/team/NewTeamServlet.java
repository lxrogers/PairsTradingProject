package team;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBConnection;

/**
 * Servlet implementation class NewTeamServlet
 */
@WebServlet("/NewTeamServlet")
public class NewTeamServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewTeamServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String create_status = "failed";

		DBConnection db = (DBConnection) request.getServletContext().getAttribute("database");
		String teamID = request.getParameter("teamID");
		String password = request.getParameter("password");
		String confirm = request.getParameter("confirmpassword");
		if (TeamManager.teamExists(db, teamID)) {
			create_status = "alreadyexists";
		}
		else if (!password.equals(confirm)) {
			create_status = "passworderror";
		}
		else {
			TeamManager.addTeam(db, teamID, password);
			if (TeamManager.teamExists(db, teamID)) {
				create_status = "success";
				request.getSession().setAttribute("teamID", teamID);
			}
		}
		request.setAttribute("create_status", create_status);
		RequestDispatcher dispatch = request.getRequestDispatcher("newteam.jsp");
		dispatch.forward(request, response);

	}

}
