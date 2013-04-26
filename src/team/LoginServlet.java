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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		String login_status = "failed";
		if (request.getParameter("newteam") != null) {
			RequestDispatcher dispatch = request.getRequestDispatcher("newteam.jsp");
			dispatch.forward(request, response);
			return;
		}
		DBConnection db = (DBConnection) request.getServletContext().getAttribute("database");
		String teamID = request.getParameter("teamID");
		String password = request.getParameter("password");
		if (TeamManager.teamExists(db, teamID)) {
			if (TeamManager.checkTeamPassword(db, teamID, password)) {
				login_status = "success";
				request.getSession().setAttribute("teamID", teamID);
			}
			else {
				login_status = "wrong_password";
			}
		}
		else {
			login_status = "user_not_exist";
		}
		
		request.setAttribute("login_status", login_status);
		
		RequestDispatcher dispatch = request.getRequestDispatcher("login.jsp");
		dispatch.forward(request, response);
	}

}
