<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>

<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<title>Main Page</title>
</head>
<body id ="body">
<form:form method="POST" name="geolocation" action="nearbyShops" modelAttribute="location">
 <form:hidden id="long" path="x"/> 
  <form:hidden id="lat" path="y"/>
</form:form>
</body>
<script type="text/javascript">
	function currentPosition(){
		var lng=0;
		var lat=0;
		var x = document.getElementById("body");
		function showPosition(position) {
			// Get the coordinates of the current position.
	       lat = position.coords.latitude;
	       lng = position.coords.longitude;
	       document.getElementById('long').value = lng;
		   document.getElementById('lat').value = lat;
		   document.forms["geolocation"].submit();
		}
		if (navigator.geolocation) {
			  // geolocation is available
				navigator.geolocation.getCurrentPosition(showPosition);				
		} 
		else {
			  // geolocation is not supported
				x.innerHTML = "Geolocation is not supported by this browser.";			
	   }
	}
	
$(document).ready(function() {
		currentPosition();
});

</script>

</html>