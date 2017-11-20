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
			
				<li class="active"><a href="StandardInsulinSensitivity.jsp">Standard Insulin Sensitivity</a></li>
				<li><a href="PersonalInsulinSensitivity.jsp">Personal Insulin Sensitivity</a></li>
				<li><a href="BackgroundInsulinDose.jsp">Background Insulin Dose</a></li>
			
			</ul>
		</div>
		<!-- /.navbar-collapse -->
		<!-- /.container-fluid -->
	</div>
	</nav>

	<form action="StandardCalculation" class="form-horizontal" method="post">
	    <div class="form-group">
	        <label class="col-xs-6 control-label">Total grams of carbohydrates in the meal</label>
	        <div class="col-xs-2">
	        	<div class="input-group">
					<input type="number" class="form-control" id="total_CH" name="total_CH" min="60" max="120" required onkeyup="validate()"/>
		        	<span class="input-group-addon">g</span>
	        	</div>   
	        </div>
	    </div>
	
	    <div class="form-group">
	        <label class="col-xs-6 control-label">Total grams of carbohydrates processed by 1 unit of rapid acting insulin</label>
	        <div class="col-xs-2">
        		<div class="input-group">
	            	<input type="number" class="form-control" id="CH_processed" name="CH_processed" placeholder="12" value="12" min="10" max="15" required onkeyup="validate()"/>
		        	<span class="input-group-addon">g/unit</span>
	        	</div>
	        </div>
	    </div>
	
		<div class="form-group">
	        <label class="col-xs-6 control-label">Blood sugar measured before meal</label>
	        <div class="col-xs-2">
	        	<div class="input-group">
	            	<input type="number" class="form-control" id="blood_sugar" name="blood_sugar" min="120" max="250" required onkeyup="validate()"/>
	        		<span class="input-group-addon">mg/dL</span>
	        	</div>
	        </div>
	    </div>
	
	    <div class="form-group">
	        <label class="col-xs-6 control-label">Target blood sugar value before meal</label>
	        <div class="col-xs-2">
	        	<div class="input-group">
	            	<input type="number" class="form-control" id="target_blood_sugar" name="target_blood_sugar" min="80" max="120" required onkeyup="validate()"/>
	        		<span class="input-group-addon">mg/dL</span>
	        	</div>
	        </div>
	    </div>
	
		<div class="form-group">
	        <label class="col-xs-6 control-label">Individual Sensitivity</label>
	        <div class="col-xs-2">
	        	<div class="input-group">
	            	<input type="number" class="form-control" id="sensitivity" name="sensitivity" placeholder="50" value="50" min="15" max="100" required onkeyup="validate()"/>
	      	    	<span class="input-group-addon">mg/dL</span>
	        	</div>
	        </div>
	    </div>
	
	    <div class="form-group">
	        <div class="col-xs-5 col-xs-offset-6">
	            <button type="submit" id="CalculateButton" name="CalculateButton" class="btn btn-default">Calculate Insulin Dose</button>
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
              if ($('#total_CH').val().length > 0 &&
                      $('#CH_processed').val().length > 0 &&
                      $('#blood_sugar').val().length > 0 &&
                      $('#target_blood_sugar').val().length > 0 && 
                      $('#sensitivity').val().length > 0) {
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