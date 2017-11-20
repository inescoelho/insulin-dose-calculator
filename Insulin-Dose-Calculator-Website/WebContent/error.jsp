<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Result <%=session.getAttribute("title")%></title>

<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" />

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

<p>
<h1>Error</h1>
<p>
<h3>
	It was not possible to calculate the insulin dose <br>
	Please, try again!
</h3>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
</body>
</html>