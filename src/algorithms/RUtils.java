/*
 * File: RCalls.java
 * We use this to interact with R for the main algorithm.
 */

/* Package Designation */
package algorithms;

import org.rosuda.JRI.Rengine;

/* Imports */

public class RUtils {

	public static double getStdDev(Rengine re, String date) {
		// Not yet implemented.
		return 1.0;
	}
	public static void installOUFunction(Rengine re) {
		re.eval("fn1<- function(N, c) {" +
				"i= 0" +
				"for(k in 1:N) {" +
				"x=((-1)^(k+1))*(((sqrt(2)*c)^k)/factorial(k))*gamma(k/2)" +
				"i=i+x" +
				"}" +
				"i/2" +
				"}");	

		re.eval("fn2<- function(N, a) {" +
				"i= 0" +
				"for(k in 1:N) {" +
				"x=(((sqrt(2)*a)^k)/factorial(k))*gamma(k/2)" +
				"i=i+x" +
				"}" +
				"i/2" +
				"}");
		re.eval("ou_check=function(ticker1,ticker2, start, end) {" +
				"stock1=getSymbols(ticker1, src='yahoo', from= start, to =end, auto.assign=FALSE)" +
				"stock2=getSymbols(ticker2, src='yahoo', from= start, to =end, auto.assign=FALSE)" +
				"ratio=stock1[,1] / stock2[,1]" +
				"xp=ratio[-1]" +
				"xt=ratio[-l]" +
				" tdregress <- lm(xt~xp)" +
				"coef <- coef(tdregress)" +
				"b2<-sd(resid(tdregress))" +
				"b0=coef[1]" +
				"b1=coef[2]-1" +
				"b2t=b2/sqrt(2)" +
				"c=ratio[l]" +
				"cadjs<-c*b2t" +
				"cadj<-as.double(cadjs[,1])" +
				"k1=-b0/b1" +
				"xprime=cadj+k1" +
				"secondt=fn1(170,cadj)" +
				"firstt=fn2(170,k1)" +
				"secondt/b1+firstt/b1" +
				"}");

	}

	public static void installDickeyFullerFunction(Rengine re) {
		String q ="df_test=function(ticker1,ticker2, start, end) { " +
				"stock1=getSymbols(ticker1, src='yahoo', from= start, to =end, auto.assign=FALSE)\n" +
				"stock2=getSymbols(ticker2, src='yahoo', from= start, to =end, auto.assign=FALSE)\n" +
				"ratio=stock1[,1]/stock2[,1] \n" +
				"adf.test(unclass(ratio)) \n" +
				"}";
		//System.out.println(q);
		re.eval(q);
	}
	public static void installCointegrationFunction(Rengine re) {
		String q ="cointegration_check=function(ticker1,ticker2, start, end) {" +
				" stock1=getSymbols(ticker1, src='yahoo', from= start, to =end, auto.assign=FALSE)\n" +
				"stock2=getSymbols(ticker2, src='yahoo', from= start, to =end, auto.assign=FALSE)\n" +
				"t.zoo<-merge(stock1[,4],stock2[,4],all=FALSE)\n" +
				"t<-as.data.frame(t.zoo)\n" +
				"m<-lm(stock1[,4]~stock2[,4]+0,data=t)\n" +
				"beta<-coef(m)\n" +
				"sprd<-t[,1]-beta*t[,2]\n" +
				"ht<-adf.test(sprd,alternative='stationary',k=0)\n" +
				"ht\n" +
				"}";
		//System.out.println(q);
		re.eval(q);
	}

	public static Rengine getInitializedInstance() {
		Rengine re=new Rengine (new String [] {"--vanilla"}, false, null);
		if (!re.waitForR())
		{
			System.out.println ("Cannot load R");
		}
		System.out.println(re.eval("library(zoo)"));
		System.out.println(re.eval("library(quantmod)"));
		System.out.println(re.eval("library(tseries)"));
		
		RUtils.installCointegrationFunction(re);
		RUtils.installOUFunction(re);
		RUtils.installDickeyFullerFunction(re);
		return re;
	}

}