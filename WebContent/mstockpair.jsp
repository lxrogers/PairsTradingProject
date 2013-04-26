<%@include file="template/header.jsp" %>
<%
	String t1 = request.getParameter("t1");
	String t2 = request.getParameter("t2");
	Rengine re = (Rengine)(request.getServletContext().getAttribute("rengine"));
	System.out.println("1");
%>

<header id="navtop">
		<a href="index.jsp" class="logo fleft">
			<h1><%=t1%>-<%= t2%>&nbsp;&nbsp;&nbsp;&nbsp;</h1>
		</a>
		<nav class="float:middle">
			<ul>
				<li><a href="#navprices" class="arrow">Prices</a></li>
				<li><a href="#navratio" class="arrow">Ratios</a></li>
			</ul>
			<ul>
				<li><a href="#navteam" class="arrow">Our team</a></li>
				<li><a href="#navteam" class="arrow">Our team</a></li>
			</ul>
			
		</nav>
	</header>
<script>
		var data1 = new Array(<%
			
			double[] prices = MStockPair.getHistoricalPrices(t1, re);
			System.out.println(prices.length);
			for (int i = 0; i < prices.length - 1; i++ ) {
				out.print(prices[i] + ",");
			}
			out.print(prices[prices.length - 1]);
		%>);
	
		var data2 = new Array(<%
			
			double[] prices2 = MStockPair.getHistoricalPrices(t2, re);
			for (int i = 0; i < prices2.length - 1; i++ ) {
				out.print(prices2[i] + ",");
			}
			out.print(prices2[prices2.length - 1]);
		%>);
		
		var ratiodata = new Array(<%
				
				double[] ratios = MStockPair.getHistoricalRatio(t1, t2, re);
				for (int i = 0; i < ratios.length - 1; i++ ) {
					out.print(ratios[i] + ",");
					System.out.println(ratios[i]);
				}
				out.print(ratios[ratios.length - 1]);
			%>);
</script>
<script src="Chart.js"></script>


<article id="navprices" >
	<h2>Prices</h2>
	<canvas id="canvas" height="450" width="1000" style="transition: opacity 200ms ease-in-out;"></canvas><br><br>
</article>
<article id="navratio" >
<h2>Price Ratio</h2>
</article>
<canvas id="canvas1" height="450" width="1000" style="transition: opacity 200ms ease-in-out;"></canvas><br><br>
<canvas id="canvas2" height="450" width="1000" style="transition: opacity 200ms ease-in-out;"></canvas>


	<script>
		
	
		var lineChartData = {
			labels : [<% 
				for (int i = 0; i < prices.length ; i++) {
					out.println("\"\",");
				}
			out.println("\"\"");
			%>],
			datasets : [
				{
					fillColor : "rgba(220,220,220,0.5)",
					strokeColor : "rgba(220,220,220,1)",
					pointColor : "rgba(220,220,220,1)",
					pointStrokeColor : "#fff",
					data : data1,
				},
				{
					
					fillColor : "rgba(171,197,215,0.5)",
					strokeColor : "rgba(151,187,205,1)",
					pointColor : "rgba(151,187,205,1)",
					pointStrokeColor : "#fff",
					data : data2,
				}
			],

		};
		var ratioChartData = {
				labels : [<% 
							for (int i = 0; i < prices.length ; i++) {
								out.println("\"\",");
							}
						out.println("\"\"");
						%>],
				datasets : [
					{
						fillColor : "rgba(171,197,215,0.5)",
						strokeColor : "rgba(151,187,205,1)",
						pointColor : "rgba(151,187,205,1)",
						pointStrokeColor : "#fff",
						data : ratiodata,
					},
				]

			};

	var myLine = new Chart(document.getElementById("canvas").getContext("2d")).Line(lineChartData);
	var myLine2 = new Chart(document.getElementById("canvas1").getContext("2d")).Line(ratioChartData);
	//var myLine3 = new Chart(document.getElementById("canvas2").getContext("2d")).Line(lineChartData);

	</script>


<%@include file="template/footer.jsp"%>