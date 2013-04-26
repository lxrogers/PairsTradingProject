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
				P-Value Threshold:<br>
				<select name="threshold">
				  <option>.1</option>
				  <option>.2</option>
				  <option>.3</option>
				  <option>.4</option>
				  <option>.5</option>
				  <option>.6</option>
				  <option>.7</option>
				  <option>.8</option>
				</select><br>
			<br><input type="submit" name ="act" value="Test Permutations">&nbsp;&nbsp;&nbsp;<input type="submit" name="act" value="Common Pairs">
		</form>
		<br>
		<%
		Object r = request.getAttribute("results");
		if (r!= null) {
			ArrayList<MStockPair> pairs = (ArrayList<MStockPair>)r;
			out.println(MStockPair.getListHTMLHeader());
			//out.println("<table>");
			for (int i = 0; i < pairs.size(); i++) {
				out.println(pairs.get(i).getListHTML());
			}
			//out.println("</table>");
			out.println(MStockPair.getListHTMLFooter());
		}
	}
		%>
<%@include file="template/footer.jsp"%>