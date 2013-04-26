<%@include file="template/header.jsp" %>
<h1>New Team</h1>
<%
	Object cs = request.getAttribute("create_status");
	String create_status = "default";
	if (cs != null) {
		create_status = cs.toString();
	}
	if (create_status.equals("success")) {
		out.println("<h1>Success!</h1>");
	}
	else {
		if (create_status.equals("alreadyexists")) {
			out.println("<h1>A team with that name already exists</h1>");
		}
		if (create_status.equals("failed")) {
			out.println("<h1>failed</h1>");
		}
		if (create_status.equals("passworderror")) {
				out.println("<h1>Passwords do not match</h1>");
		}
		%><form action="NewTeamServlet" method="post">
			Team Name:<br> <input type="text" name="teamID"><br>
			Password:<br> <input type ="password" name="password"><br>
			Confirm Password:<br> <input type ="password" name="confirmpassword"><br>
			<br><input type="submit" value="Create New Team">
		</form><%
		
		
	}%>
<%@include file="template/footer.jsp"%>