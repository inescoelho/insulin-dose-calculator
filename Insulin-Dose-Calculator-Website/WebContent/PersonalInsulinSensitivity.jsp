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
				<li class="active"><a href="PersonalInsulinSensitivity.jsp">Personal Insulin Sensitivity</a></li>
				<li><a href="BackgroundInsulinDose.jsp">Background Insulin Dose</a></li>
			
			</ul>
		</div>
		<!-- /.navbar-collapse -->
		<!-- /.container-fluid -->
	</div>
	</nav>
	

	<form action="PersonalCalculation" class="form-horizontal" method="post">
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
	        <label class="col-xs-6 control-label">Today's Physical Activity Level</label>
	        <div class="col-xs-2">
	            <select class="form-control" id="activityLevel" name="activityLevel" onchange="validate()" min="0" max="10" required>
	           	  <option></option>
				  <option value ="0">0</option>
				  <option value ="1">1</option>
				  <option value ="2">2</option>
				  <option value ="3">3</option>
				  <option value ="4">4</option>
				  <option value ="5">5</option>
				  <option value ="6">6</option>
				  <option value ="7">7</option>
				  <option value ="8">8</option>
				  <option value ="9">9</option>
				  <option value ="10">10</option>
				</select></div>
	    </div>
	    
	    <div class="form-group">
	   		 <label class="col-xs-8 control-label"> Samples of Physical Activity Level in a given day</label>
	    </div>
	    <div class="form-group">
		     <div class="col-xs-1 col-xs-offset-1">
	            <select class="form-control" id="activityLevelSample1" name="activityLevelSample1" required onchange="makeEnable('activityLevelSample2', 'bloodSample1')" min="0" max="10">
				  <option></option>
				  <option value ="0">0</option>
				  <option value ="1">1</option>
				  <option value ="2">2</option>
				  <option value ="3">3</option>
				  <option value ="4">4</option>
				  <option value ="5">5</option>
				  <option value ="6">6</option>
				  <option value ="7">7</option>
				  <option value ="8">8</option>
				  <option value ="9">9</option>
				  <option value ="10">10</option>
				</select></div>
				
				 <div class="col-xs-1" >
	            <select class="form-control" id="activityLevelSample2" name="activityLevelSample2" required disabled onchange="makeEnable('activityLevelSample3', 'bloodSample2')" min="0" max="10">
				  <option></option>
				  <option value ="0">0</option>
				  <option value ="1">1</option>
				  <option value ="2">2</option>
				  <option value ="3">3</option>
				  <option value ="4">4</option>
				  <option value ="5">5</option>
				  <option value ="6">6</option>
				  <option value ="7">7</option>
				  <option value ="8">8</option>
				  <option value ="9">9</option>
				  <option value ="10">10</option>
				</select></div>
				
				 <div class="col-xs-1">
	            <select class="form-control"  id="activityLevelSample3" name="activityLevelSample3" disabled onchange="makeEnable('activityLevelSample4', 'bloodSample3')" min="0" max="10">
				  <option></option>
				  <option value ="0">0</option>
				  <option value ="1">1</option>
				  <option value ="2">2</option>
				  <option value ="3">3</option>
				  <option value ="4">4</option>
				  <option value ="5">5</option>
				  <option value ="6">6</option>
				  <option value ="7">7</option>
				  <option value ="8">8</option>
				  <option value ="9">9</option>
				  <option value ="10">10</option>
				</select></div>
				
				 <div class="col-xs-1" >
	            <select class="form-control" id="activityLevelSample4" name="activityLevelSample4" disabled onchange="makeEnable('activityLevelSample5', 'bloodSample4')" min="0" max="10">
				  <option></option>
				  <option value ="0">0</option>
				  <option value ="1">1</option>
				  <option value ="2">2</option>
				  <option value ="3">3</option>
				  <option value ="4">4</option>
				  <option value ="5">5</option>
				  <option value ="6">6</option>
				  <option value ="7">7</option>
				  <option value ="8">8</option>
				  <option value ="9">9</option>
				  <option value ="10">10</option>
				</select></div>
				
				 <div class="col-xs-1" >
	            <select class="form-control" id="activityLevelSample5" name="activityLevelSample5" disabled onchange="makeEnable('activityLevelSample6', 'bloodSample5')" min="0" max="10">
				  <option></option>
				  <option value ="0">0</option>
				  <option value ="1">1</option>
				  <option value ="2">2</option>
				  <option value ="3">3</option>
				  <option value ="4">4</option>
				  <option value ="5">5</option>
				  <option value ="6">6</option>
				  <option value ="7">7</option>
				  <option value ="8">8</option>
				  <option value ="9">9</option>
				  <option value ="10">10</option>
				</select></div>
				
				 <div class="col-xs-1" >
	            <select class="form-control" id="activityLevelSample6" name="activityLevelSample6" disabled onchange="makeEnable('activityLevelSample7', 'bloodSample6')" min="0" max="10">
				  <option></option>
				  <option value ="0">0</option>
				  <option value ="1">1</option>
				  <option value ="2">2</option>
				  <option value ="3">3</option>
				  <option value ="4">4</option>
				  <option value ="5">5</option>
				  <option value ="6">6</option>
				  <option value ="7">7</option>
				  <option value ="8">8</option>
				  <option value ="9">9</option>
				  <option value ="10">10</option>
				</select></div>
				
				 <div class="col-xs-1" >
	            <select class="form-control" id="activityLevelSample7" name="activityLevelSample7" disabled onchange="makeEnable('activityLevelSample8', 'bloodSample7')" min="0" max="10">
				  <option></option>
				  <option value ="0">0</option>
				  <option value ="1">1</option>
				  <option value ="2">2</option>
				  <option value ="3">3</option>
				  <option value ="4">4</option>
				  <option value ="5">5</option>
				  <option value ="6">6</option>
				  <option value ="7">7</option>
				  <option value ="8">8</option>
				  <option value ="9">9</option>
				  <option value ="10">10</option>
				</select></div>
				
				 <div class="col-xs-1" >
	            <select class="form-control" id="activityLevelSample8" name="activityLevelSample8" disabled onchange="makeEnable('activityLevelSample9', 'bloodSample8')" min="0" max="10">
				  <option></option>
				  <option value ="0">0</option>
				  <option value ="1">1</option>
				  <option value ="2">2</option>
				  <option value ="3">3</option>
				  <option value ="4">4</option>
				  <option value ="5">5</option>
				  <option value ="6">6</option>
				  <option value ="7">7</option>
				  <option value ="8">8</option>
				  <option value ="9">9</option>
				  <option value ="10">10</option>
				</select></div>
				
				 <div class="col-xs-1" >
	            <select class="form-control" id="activityLevelSample9" name="activityLevelSample9" disabled onchange="makeEnable('activityLevelSample10', 'bloodSample9')" min="0" max="10">
				  <option></option>
				  <option value ="0">0</option>
				  <option value ="1">1</option>
				  <option value ="2">2</option>
				  <option value ="3">3</option>
				  <option value ="4">4</option>
				  <option value ="5">5</option>
				  <option value ="6">6</option>
				  <option value ="7">7</option>
				  <option value ="8">8</option>
				  <option value ="9">9</option>
				  <option value ="10">10</option>
				</select></div>
				
				 <div class="col-xs-1">
	            <select class="form-control" id="activityLevelSample10" name="activityLevelSample10" disabled onchange="makeEnable('activityLevelSample10', 'bloodSample10')" min="0" max="10">
				  <option></option>
				  <option value ="0">0</option>
				  <option value ="1">1</option>
				  <option value ="2">2</option>
				  <option value ="3">3</option>
				  <option value ="4">4</option>
				  <option value ="5">5</option>
				  <option value ="6">6</option>
				  <option value ="7">7</option>
				  <option value ="8">8</option>
				  <option value ="9">9</option>
				  <option value ="10">10</option>
				</select></div>
		</div>
		
		<div class="form-group">
	   		 <label class="col-xs-8 control-label">Samples of drops in blood sugar from one unit of insulin on that day (mg/dL)</label>
	    </div>
	    <div class="form-group">
		    <div class="col-xs-1 col-xs-offset-1">
		    	<input type="number" class="form-control" id="bloodSample1" name="bloodSample1" min="15" max="100" required onchange="makeEnable('bloodSample2', 'activityLevelSample1')" onkeyup="validate()"/>
		    </div>
	 		<div class="col-xs-1">
		       <input type="number" class="form-control" id="bloodSample2" name="bloodSample2" min="15" max="100" required disabled onchange="makeEnable('bloodSample3', 'activityLevelSample2')" onkeyup="validate()"/>
		    </div>
	  		<div class="col-xs-1">
		        <input type="number" class="form-control" id="bloodSample3" name="bloodSample3" min="15" max="100" disabled onchange="makeEnable('bloodSample4', 'activityLevelSample3')" onkeyup="validate()"/>	    
		    </div>
	 		<div class="col-xs-1">
		        <input type="number" class="form-control" id="bloodSample4" name="bloodSample4" min="15" max="100" disabled onchange="makeEnable('bloodSample5', 'activityLevelSample4')" onkeyup="validate()"/>
		    </div>
			<div class="col-xs-1">
				<input type="number" class="form-control" id="bloodSample5" name="bloodSample5" min="15" max="100" disabled onchange="makeEnable('bloodSample6', 'activityLevelSample5')" onkeyup="validate()"/>
		    </div>
			<div class="col-xs-1">
			    <input type="number" class="form-control" id="bloodSample6" name="bloodSample6" min="15" max="100" disabled onchange="makeEnable('bloodSample7', 'activityLevelSample6')" onkeyup="validate()"/>
		    </div>
			<div class="col-xs-1">	 
	 	       <input type="number" class="form-control" id="bloodSample7" name="bloodSample7" min="15" max="100" disabled onchange="makeEnable('bloodSample8', 'activityLevelSample7')" onkeyup="validate()"/>
		    </div>
			<div class="col-xs-1">
		        <input type="number" class="form-control" id="bloodSample8" name="bloodSample8" min="15" max="100" disabled onchange="makeEnable('bloodSample9', 'activityLevelSample8')" onkeyup="validate()"/>
		    </div>
			<div class="col-xs-1">
			     <input type="number" class="form-control" id="bloodSample9" name="bloodSample9" min="15" max="100" disabled onchange="makeEnable('bloodSample10', 'activityLevelSample9')" onkeyup="validate()"/>
		    </div>
			<div class="col-xs-1">
		        <input type="number" class="form-control" id="bloodSample10" name="bloodSample10" min="15" max="100" disabled onchange="makeEnable('bloodSample10', 'activityLevelSample10')" onkeyup="validate()"/>
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
              makeEnable();
          });

          function validate() {
              if ($('#total_CH').val().length > 0 &&
                  $('#CH_processed').val().length > 0 &&
                  $('#blood_sugar').val().length > 0 &&
                  $('#target_blood_sugar').val().length > 0 && 
                  $('#activityLevel').val().length  > 0 && 
                  $('#activityLevelSample1').val().length  > 0 && 
                  $('#activityLevelSample2').val().length  > 0 && 
                  $('#bloodSample1').val().length  > 0 && 
                  $('#bloodSample2').val().length  > 0 ) {
                  $('#CalculateButton').prop('disabled', false);
              }
              else{
                  $('#CalculateButton').prop('disabled', true);
              }
          }

          function makeEnable(elementName, elementName2){
        	    var x=document.getElementById(elementName);
        	    var y=document.getElementById(elementName2);
				x.disabled=false;
				y.required=true;
				y.disabled=false;
				
				validate();
        	}

	</script>
	
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>