package pairtrading;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DBConnection;

/**
 * Servlet implementation class FindStocksServlet
 */
@WebServlet("/FindStocksServlet")
public class FindStocksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindStocksServlet() {
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
		DBConnection db = (DBConnection) request.getServletContext().getAttribute("database");
		String a = request.getParameter("act");
		String i = request.getParameter("pvalueindex");
		int index = (i == null) ? 0 : Integer.parseInt(i.toString());
		String pvalueindex = MStockPair.PVALUE_COLUMNS[index];
		ArrayList<MStockPair> pairs = new ArrayList<MStockPair>();
		if (a.equals("Common Pairs")) {

		}
		else if (a.equals("Test Permutations")) {
			double pthreshold = Double.parseDouble(request.getParameter("pvalueindex"));
			String composite_ticker = request.getParameter("composite");
			pairs = StockPairUtils.getStockPairs(db, composite_ticker, request.getSession().getAttribute("teamID").toString(), pvalueindex);
		}
		request.setAttribute("results", pairs);
		request.setAttribute("pvalueindex", i);
		RequestDispatcher dispatch = request.getRequestDispatcher("findstocks.jsp");
		dispatch.forward(request, response);
	}
}
