<%@ page
 	import="java.util.*,pairtrading.*,database.*,org.rosuda.JRI.*" %>
<%
	String t1 = request.getParameter("t1");
	String t2 = request.getParameter("t2");
	String d = request.getParameter("d");
	int date = (d != null) ? Integer.parseInt(d) : 0;
	Rengine re = (Rengine)(request.getServletContext().getAttribute("rengine"));
	String rDate = MStockPair.getRDateString(date);
	
%>
<script src="Chart.js"></script>
<script>
	var data1 = new Array(<%
			
			double[] prices = MStockPair.getHistoricalPrices(t1, re, rDate);
			for (int i = 0; i < prices.length - 1; i++ ) {
				out.print(prices[i] + ",");
			}
			out.print(prices[prices.length - 1]);
		%>);
	
		var data2 = new Array(<%
			
			double[] prices2 = MStockPair.getHistoricalPrices(t2, re, rDate);
			for (int i = 0; i < prices2.length - 1; i++ ) {
				out.print(prices2[i] + ",");
			}
			out.print(prices2[prices2.length - 1]);
		%>);
		
		var ratiodata = new Array(<%
				
				double[] ratios = MStockPair.getHistoricalRatio(t1, t2, re);
				for (int i = 0; i < ratios.length - 1; i++ ) {
					out.print(ratios[i] + ",");
				}
				out.print(ratios[ratios.length - 1]);
			%>);
		var lineChartData = {
				labels : [<% 
					for (int i = 0; i < prices.length ; i++) {
						out.println("\"\",");
					}
				out.println("\"\"");
				%>],
				datasets : [
					{
						fillColor : "rgba(214,24,84,0.5)",
						strokeColor : "rgba(214,24,84,.7)",
						pointColor : "rgba(214,24,84,1)",
						pointStrokeColor : "#fff",
						data : data1,
					},
					{
						
						fillColor : "rgba(171,197,215,1)",
						strokeColor : "#6495ED",
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
							fillColor : "rgba(171,197,215,1)",
							strokeColor : "#8A2BE2",
							pointColor : "rgba(151,187,205,1)",
							pointStrokeColor : "#fff",
							data : ratiodata,
						},
					]

				};

</script>