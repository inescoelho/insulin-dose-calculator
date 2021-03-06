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

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			
			<ul class="nav navbar-nav navbar-right">
			
				<li><a href="StandardInsulinSensitivity.jsp">Standard Insulin Sensitivity</a></li>
				<li><a href="PersonalInsulinSensitivity.jsp">Personal Insulin Sensitivity</a></li>
				<li class="active"><a href="BackgroundInsulinDose.jsp">Background Insulin Dose</a></li>
			
			</ul>
		</div>
		<!-- /.navbar-collapse -->
		<!-- /.container-fluid -->
	</div>
	</nav>

	<form action="BackgroundCalculation" class="form-horizontal" method="post">
	
		<div class="form-group">
	        <label class="col-xs-6 control-label">Weight in Kilograms</label>
	        <div class="col-xs-3">
	       		<div class="input-group">
		            <input type="number" class="form-control" id="Weight" name="Weight" min="40" max="130" required onkeyup="validate()"/>
		        	<span class="input-group-addon">Kg</span>
	        	</div>
	        </div>
	       	
	    </div>
	
	    <div class="form-group">
	        <div class="col-xs-5 col-xs-offset-6">
	            <button type="submit" id="CalculateButton" name="CalculateButton" class="btn btn-default" disabled>Calculate Insulin Dose</button>
	        </div>
	    </div>
	</form>
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	
	<script  type="text/javascript">
		$(document).ready(function (){
              validate();
          });

          function validate() {
              if ($('#Weight').val().length > 0) {
                  $('#CalculateButton').prop('disabled', false);
              }
              else{
                  $('#CalculateButton').prop('disabled', true);
              }
          }

	</script>

	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>