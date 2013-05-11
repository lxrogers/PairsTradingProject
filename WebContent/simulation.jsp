<%@include file="template/header.jsp" %>
<%
	//get parameters
	String t1 = request.getParameter("t1");
	String t2 = request.getParameter("t2");
	String d = request.getParameter("d");
	int date = (d == null) ? 0 : Integer.parseInt(d);

%>
<h1>Simulate <span style="color: IndianRed;"><%=t1%></span>-<span style="color: CornflowerBlue;"><%= t2%></span>&nbsp;&nbsp;&nbsp;&nbsp;</h1>




<form action="SimulationServlet" method="post">
				Start Time:<br>
				<select name="starttime">
				  <option value='1' <%= (date == 1) ? "selected='selected'" : ""%>>2012-01-01</option>
				</select><br>
				Duration:<br>
				<select name="duration">
				  <option value='0'>To Now</option>
				  <option value='50'>50 Days</option>
				</select><br>
				<input type="hidden" name="t1" value="<%=t1%>">
				<input type="hidden" name="t2" value="<%=t2%>">
			<br><input type="submit" name ="act" value="Run Simulation">&nbsp;&nbsp;&nbsp;<input type="submit" name="act" value="unused">
		</form>

<%@include file="template/footer.jsp"%>