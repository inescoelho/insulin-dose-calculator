<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insulin Calculator</title>

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
	</div>
	</nav>

	<div class="btn-group-vertical col-xs-offset-4" role="group" aria-label="...">
		<a class="btn btn-default" href="StandardInsulinSensitivity.jsp" role="button">Mealtime insulin dose - Standard Insulin Sensitivity</a>
		<a class="btn btn-default" href="PersonalInsulinSensitivity.jsp" role="button">Mealtime insulin dose - Personal Insulin Sensitivity</a>
		<a class="btn btn-default" href="BackgroundInsulinDose.jsp" role="button">Background Insulin Dose</a>
	</div>


	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>