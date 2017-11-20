<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Result</title>

<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<%@ page import="edu.dei.qcs.voter.Result"%>
<%@ page import="java.util.concurrent.ConcurrentHashMap"%>

</head>
<body>

<nav class="navbar navbar-default" role="navigation">
	<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="index.jsp">Insulin Dose Calculator</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			
			<ul class="nav navbar-nav navbar-right">
			
				<li><a href="StandardInsulinSensitivity.jsp">Standard Insulin Sensitivity</a></li>
				<li><a href="PersonalInsulinSensitivity.jsp">Personal Insulin Sensitivity</a></li>
				<li><a href="BackgroundInsulinDose.jsp">Background Insulin Dose</a></li>
			
			</ul>
		</div>
		<!-- /.navbar-collapse -->
		<!-- /.container-fluid -->
	</div>
	</nav>

	<h2>
		${errortext}
	</h2>

	<div class="container-fluid">
		<h1>${title}</h1>
	
		<h4>
			${text}
			<span class="label label-default">${value}</span>
		</h4>
		
		<div>
			<a class="btn btn-default" data-toggle="collapse" href="#TechnicalInformation" aria-expanded="false" aria-controls="TechnicalInformation">
			  Technical Information
			</a>
			<div class="collapse" id="TechnicalInformation">
			  <div class="well">
			  	<%
			  			Result result = (Result) request.getAttribute("result");
			   			int number = result.getNumberValidWS();

						out.print("Number of Webservices: " + number);
					%>
			  	
			   	<ul class="list-group">
			    	<li class='list-group-item'>
			   			<table class="table">
					   		<%
					   			ConcurrentHashMap<String, Integer> WSresults = result.getAllResults();
								String value;
			
									for (String key : WSresults.keySet())
									{
										if (WSresults.get(key) >= 0)
										{
											value = Integer.toString(WSresults.get(key));
										}
										else
										{
											value = "Timeout";
										}
										out.print("<tr><th>" + key + "</th><th>" + value + "</th></tr>");
									}	
							%>
				   		</table>				
					</li>
				</ul>
			  </div>
			</div>
		</div>
	
	<c:if test="${requestScope.multipleResults == true}">
	<div class="container-fluid">
		<h1>${title2}</h1>
	
		<h4>
			${text2}
			<span class="label label-default">${value2}</span>
		</h4>
		
		<div>
			<a class="btn btn-default" data-toggle="collapse" href="#TechnicalInformation2" aria-expanded="false" aria-controls="TechnicalInformation2">
			  Technical Information
			</a>
			<div class="collapse" id="TechnicalInformation2">
			  <div class="well">
			  	<%
			  			result = (Result) request.getAttribute("result2");
			   			number = result.getNumberValidWS();

						out.print("Number of Webservices: " + number);
					%>
			   	<ul class="list-group">
			   		<li class='list-group-item'>
			   			<table class="table">
					   		<%
					   			WSresults = result.getAllResults();
			
									for (String key : WSresults.keySet())
									{
										if (WSresults.get(key) >= 0)
										{
											value = Integer.toString(WSresults.get(key));
										}
										else
										{
											value = "Timeout";
										}
										out.print("<tr><th>" + key + "</th><th>" + value + "</th></tr>");
									}	
							%>
						</table>
				   	</li>				
				</ul>
			  </div>
			</div>
		</div>
	</c:if>
	
	</div>
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="bootstrap/js/bootstrap.min.js"></script>
	
</body>
</html>