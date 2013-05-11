<%@include file="template/header.jsp" %>
<h1> Find Stock Pairs </h1>
<%
	DBConnection db = (DBConnection)(request.getServletContext().getAttribute("database"));
	ArrayList<String> composites = Stock.getComposites(db);
	if (teamID.equals("")) {
		%>
		<h1>Please <a href="login.jsp">Login</a></h1>
		<%
	}
	else {
%>
		<form action="FindStocksServlet" method="post">
				Composite Index:<br>
				<select name="composite">
					<%
					for (String c : composites) {
						out.println("<option>" + c + "</option>");
					}
					%>
				</select><br>
				P-Value Time Frame:<br>
				<%
					Object iobj = request.getAttribute("pvalueindex");
					int index = (iobj == null) ? 0 : Integer.parseInt(iobj.toString());
				%>
				<select name="pvalueindex">
				  <option value='0' <%= (index == 0) ? "selected='selected'" : "" %>>Now</option>
				  <option value='1' <%= (index == 1) ? "selected='selected'" : ""%>>2012-01-01</option>
				</select><br>
			<br><input type="submit" name ="act" value="Test Permutations">&nbsp;&nbsp;&nbsp;<input type="submit" name="act" value="Common Pairs">
		</form>
		<br>
		<%
		Object r = request.getAttribute("results");
		if (r!= null) {
			//index already defined above
			ArrayList<MStockPair> pairs = (ArrayList<MStockPair>)r;
			out.println(MStockPair.getListHTMLHeader());
			for (int i = 0; i < pairs.size(); i++) {
				out.println(pairs.get(i).getListHTML(index));
			}
			//out.println("</table>");
			out.println(MStockPair.getListHTMLFooter());
		}
	}
		%>
<%@include file="template/footer.jsp"%>