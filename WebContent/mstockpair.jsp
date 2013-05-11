<%@include file="template/header.jsp" %>

<%@include file="chartscript.jsp"%>
<header id="navtop">
		<a href="index.jsp" class="logo fleft">
			<h1><span style="color: IndianRed;"><%=t1%></span>-<span style="color: CornflowerBlue;"><%= t2%></span>&nbsp;&nbsp;&nbsp;&nbsp;</h1>
		</a>
		<nav class="float:middle">
			<ul>
				<li><a href="#navprices" class="arrow">Prices</a></li>
				<li><a href="#navratio" class="arrow">Ratios</a></li>
			</ul>
			<ul>
				<li><a href="simulation.jsp?t1=<%=t1%>&t2=<%=t2 %>&d=<%=date %>" class="arrow">Simulate</a></li>
				<li><a href="#navteam" class="arrow">Our team</a></li>
			</ul>
		</nav>
	</header>
	<div class="grid-wrap"><nav class="grid col-one-third "><ul>
				<%
					for (int i = 0; i < MStockPair.PVALUE_COLUMNS.length; i++) {
						String url = MStockPair.getPairURL(t1, t2, i);
						String s = MStockPair.PVALUE_COLUMNS[i];
				%>
				<li style="float: left; margin-right: 1em; padding-right: 1em; border-right: 1px solid #DDD; font-size: 1em;">
					<a href="<%=url%>"><%=s %></a>
				</li>
				<% } %>
			</ul></nav></div>
			<br><br>

<article id="navprices" >
	<h2>Prices</h2>
	<canvas id="pricescanvas" height="450" width="1000" style="transition: opacity 200ms ease-in-out;"></canvas><br>
</article>
<article id="navratio" style="position:relative; width:1000px; height:600px" >
<h2>Price Ratio</h2>
<div id="canvasesdiv" style="position:relative; width:1000px; height:450px">
	<canvas id="ratiocanvas" height="450" width="1000" style="z-index: 1;
			position:absolute;left:0px;top:0px;"></canvas>
	<canvas id="simulationcanvas" height="430" width="1000" style="z-index: 2;
			position:absolute;left:40px;top:5px;"></canvas>
</div>
</article>


<script>
var myLine = new Chart(document.getElementById("pricescanvas").getContext("2d")).Line(lineChartData);
var myLine2 = new Chart(document.getElementById("ratiocanvas").getContext("2d")).Line(ratioChartData);
var canvas = document.getElementById('simulationcanvas');
</script>
<%@include file="simulationscript.jsp"%>

<%@include file="template/footer.jsp"%>