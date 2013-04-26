<%@include file="template/header.jsp" %>
<h1>Team Login</h1>
<%
	Object l = request.getAttribute("login_status");
	String login_status = "default";
	if (l != null) {
		login_status = l.toString();
	}
	if (login_status.equals("success")) {
		out.println("<h1>Success!</h1>");
	}
	else {
		if (login_status.equals("wrong_password")) {
			out.println("<h1>Wrong Password</h1>");
		}
		if (login_status.equals("user_not_exist")) {
			out.println("<h1>Invalid User Name</h1>");
		}
		%>
		<form action="LoginServlet" method="post">
			Team Name:<br> <input type="text" name="teamID"><br>
			Password:<br> <input type ="password" name="password"><br>
			<br><input type="submit" value="Login">
				<input type="submit" name="newteam" value="New Team" />
		</form><%
	}
 %>
<%@include file="template/footer.jsp"%>