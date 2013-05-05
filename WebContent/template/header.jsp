<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page
 	import="java.util.*,pairtrading.*,database.*,org.rosuda.JRI.*" %>
<!DOCTYPE html>

<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="en"> <![endif]-->

<!--[if gt IE 8]><!--> <html class="no-js" lang="en"> <!--<![endif]-->

<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width">
	<meta name="description" content="Designa Studio, a HTML5 / CSS3 template.">
	<meta name="author" content="Sylvain Lafitte, Web Designer, sylvainlafitte.com">
	
	<title>MS&E445 // Pairs Trading</title>
	
	<link rel="shortcut icon" type="image/x-icon" href="favicon.ico">
	<link rel="shortcut icon" type="image/png" href="favicon.png">
	
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,400,700' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" href="css/style.css">
	
	<!--[if lt IE 9]>
	<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->
</head>
<body>

<%
	Object t = session.getAttribute("teamID");
	String teamID = "";	
	if (t != null) {
		teamID = t.toString();
	}
%>
<div class="container">
<header id="navtop">
		<a href="index.jsp" class="logo fleft">
			<img src="img/logo.png" alt="Designa Studio">
		</a>
		<nav class="float:middle">
			<ul>
				<li><a href="login.jsp" class="navactive">Login</a></li>
				<li><a href="team.jsp">Team</a></li>
			</ul>
			<ul>
				<li><a href="findstocks.jsp">Find Stock Pairs</a></li>
				<li><a href="teamstocks.jsp">Our Stock Pairs</a></li>
			</ul>
			<ul>
				<li><a href="simulation.jsp">Simulation</a></li>
				<li><a href="BatchServlet">Batch</a></li>
			</ul>
		</nav>
	</header>
