<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<script src="https://unpkg.com/vue@2.5.11/dist/vue.js"></script>
<title>My Preferred Shops</title>
</head>
<body>
 
  <div id="vue-shop"  class= "w3-container w3-center" >
  
  	<div class = "w3-section w3-right">
      	      <a href="https://localhost:8443/locateNearbyShops/displayShops">Nearby Shops</a>
  		      <a href="#">My preferred Shops</a>
    </div>		
  
  
  <div class="w3-row-padding w3-margin w3-center" style="width:80%">
   
   	<div v-for="item in filtered" :key="item.id" class="w3-third w3-center w3-margin-top" style="width:25%">
   		<div class="w3-card" >	
      		<div class = "w3-container w3-center">
      			<h5>{{item.name}}</h5>	
      			<img v-bind:src=item.picture style="width:80%">		
      			  <!-- remove button  -->
      		 	<div class = "w3-section">
  			   		<button class="w3-button w3-red" @click="remove(item.id)" style="height:20%">Remove</button>
      		 	</div>
   		   </div>		  
   		</div>	
   </div> 
 </div>
   
 <div class="w3-section w3-right">  
   <button class="w3-btn w3-white w3-border w3-round-large" @click="paginate('previous')" :disabled="start <= 0" >Previous</button>
   <button class="w3-btn w3-white w3-border w3-round-large" @click="paginate('next')" :disabled="limit >= total">Next</button>  
 </div>
   
 </div>
   
</body>
<script type="text/javascript">  
var vueItems = new Vue({
    el: '#vue-shop',
    data: {
        items : [],
        start: 0,
		limit: 8,
		pagination: 8,
		total: ${sessionScope['totalPrefShops']},
    },
    mounted: function(){
        <c:forEach items="${sessionScope['preferredShops']}" var="shop"> 
            this.items = this.items.concat({
                id: "${shop.id}",
                name: "${shop.name}",
                picture : "${shop.picture}"
                });             
        </c:forEach>
        this.limit = parseInt(this.pagination);
    },
    
    computed: {
      	filtered: function(){
      		return this.items.slice(this.start, this.limit)
      	}
      },

    	methods: {
    		paginate: function(direction) {
    			if(direction === 'next') {
    				this.start += parseInt(this.pagination);
    				this.limit += parseInt(this.pagination);
    			}
    			else if(direction === 'previous') {
    				this.limit -= parseInt(this.pagination);
    				this.start -= parseInt(this.pagination);
    			}
    		},
    		remove: function (id){
    			window.location.href = 'https://localhost:8443/locateNearbyShops/remove?id='+id;
    			
    		},
    	},

    	filters: {
    		paginate: function(array, start, limit) {
    			return array.slice(start, limit);
    		}
    	}
    
});
</script> 
</html>