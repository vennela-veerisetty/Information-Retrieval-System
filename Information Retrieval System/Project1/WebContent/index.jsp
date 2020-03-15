<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
<script src=https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.3/Chart.min.js></script>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>

<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>var r;var resultObj;var resultObj1;var aObj1;var b;var str;var k;var l;var m;var n;</script> 
<style>
body, html {
  width: 100%;
  height: 100%;
  margin: 0;
}

.container {
  width: 100%;
  height: 100%;
}

.leftpane {
    width: 10%;
    height: 100%;
    float: left;
    background-color: white;
    border-collapse: collapse;
}

.middlepane {
    width: 50%;
    height: 100%;
    float: left;
    background-color: white;
    border-collapse: collapse;
}

.rightpane {
  width: 40%;
  height: 100%;
  position: relative;
  float: right;
  background-color: white;
  border-collapse: collapse;
}

</style>

</head>
<body>
<div class="container">
<div class="leftpane">
<h3>Filters</h3>
<h4>Topic</h4>
<input type="checkbox" name="Topic1" id="crime">Crime <br>
<input type="checkbox" name="Topic2" id="politics">Politics <br>
<input type="checkbox" name="Topic3" id="environment">Environment <br> 
<input type="checkbox" name="Topic4" id="infra">Infra <br>
<input type="checkbox" name="Topic5" id="social unrest">Social unrest <br> 
<h4>City</h4>
<input type="checkbox" name="City1" id="nyc">Newyork <br>
<input type="checkbox" name="City2" id="delhi">Delhi <br>
<input type="checkbox" name="City3" id="paris">Paris <br> 
<input type="checkbox" name="City4" id="mexico city">Mexico <br>
<input type="checkbox" name="City5" id="bangkok">Bangkok <br> 
<h4>Language</h4>
<input type="checkbox" name="Language1" id="en">English <br>
<input type="checkbox" name="Language2" id="hi">Hindi <br>
<input type="checkbox" name="Language3" id="fr">French <br> 
<input type="checkbox" name="Language4" id="es">Spanish <br>
<input type="checkbox" name="Language5" id="th">Thai <br> 

</div>
  <div class="middlepane"><br>
    <input type="text" size="70" id="searchtext" placeholder="Search..">
    &nbsp;&nbsp;&nbsp;<input type="submit" class="btn btn-primary" value="Search" onclick="myFunction(); return false;"/><br>
   <br><p id="demo"></p>
</div>
  <div class="rightpane">
  <h3>Analysis</h3>
  <select id="Analysis">
  <option value="topic">Topic</option>
  <option value="lang">Language</option>
  <option value="city">City</option>
  <option value="sentimental">Sentimental</option>
  <option value="tweet_date">Date</option>
  <option value="hashtags">Hashtags</option>
  <option value="entities.user_mentions.name">Mentions</option>
  
  </select>
  &nbsp;&nbsp;&nbsp;
  <input type="submit" class="btn btn-primary" value="Analyse" onclick="myFunction1(); return false;"/><br>
  <div id="chartContainer" style="height: 300px; width: 100%;"></div>
  <canvas id="ctx" height="200" width="300"></canvas>
  </div>
  <script>
  function myFunction() {
  newDetails = {searchtext: document.getElementById('searchtext').value,
		  crime: document.getElementById('crime').checked,
		  politics: document.getElementById('politics').checked,
		  environment: document.getElementById('environment').checked,
		  infra: document.getElementById('infra').checked,
		  socialunrest: document.getElementById('social unrest').checked,
		  nyc: document.getElementById('nyc').checked,
		  delhi: document.getElementById('delhi').checked,
		  paris: document.getElementById('paris').checked,
		  mexicocity: document.getElementById('mexico city').checked,
		  bangkok: document.getElementById('bangkok').checked,
		  en: document.getElementById('en').checked,
		  hi: document.getElementById('hi').checked,
		  fr: document.getElementById('fr').checked,
		  es: document.getElementById('es').checked,
		  th: document.getElementById('th').checked,
		  };
	  $.ajax({
		  'async': false,
		  type: "POST",
	      url: "Search",
	      data: newDetails,
	   	  success: function(result){
	   		  resultObj = JSON.parse(result);
	   		  console.log(result);
	   	  }
	  });
	 document.getElementById("demo").innerHTML = resultObj.one;
	 //clearBox('ctx');
	 var canvas = document.getElementById('ctx');
	 const context = canvas.getContext('2d');
context.clearRect(0, 0, canvas.width, canvas.height);
	 clearBox('chartContainer');
	 //clearBox('ctx');
}
  function myFunction1() {
	  var ddl = document.getElementById("Analysis");
	  var selectedValue = ddl.options[ddl.selectedIndex].value;
	  str=String(selectedValue);
  newDetails = {searchtext: document.getElementById('searchtext').value,
		  crime: document.getElementById('crime').checked,
		  politics: document.getElementById('politics').checked,
		  environment: document.getElementById('environment').checked,
		  infra: document.getElementById('infra').checked,
		  socialunrest: document.getElementById('social unrest').checked,
		  nyc: document.getElementById('nyc').checked,
		  delhi: document.getElementById('delhi').checked,
		  paris: document.getElementById('paris').checked,
		  mexicocity: document.getElementById('mexico city').checked,
		  bangkok: document.getElementById('bangkok').checked,
		  en: document.getElementById('en').checked,
		  hi: document.getElementById('hi').checked,
		  fr: document.getElementById('fr').checked,
		  es: document.getElementById('es').checked,
		  th: document.getElementById('th').checked,
		  analysis: selectedValue
		  };
  
	  $.ajax({
		  'async': false,
		  type: "POST",
	      url: "Analysis",
	      data: newDetails,
	   	  success: function(result){
	   		  aObj1 = JSON.parse(result);
	   		  console.log(aObj1);
	   	  }
	  });
	  if(str==="topic"){
			k=[{y: aObj1.crime, label: "Crime"},
				{y: aObj1.politics, label: "Politics"},
				{y: aObj1.environment, label: "Environment"},
				{y: aObj1.infra, label: "Infra"},
				{y: aObj1.socialunrest, label: "Social unrest"}
				];
			myfunction2(); 
		  } else if(str==="sentimental"){
		  k=[{y: aObj1.positive, label: "Positive"},
				{y: aObj1.neutral, label: "Neutral"},
				{y: aObj1.negative, label: "Negative"}
				];
		  myfunction2();
	  }else if(str==="city"){
		k=[{y: aObj1.nyc, label: "Nyc"},
			{y: aObj1.delhi, label: "Delhi"},
			{y: aObj1.paris, label: "Paris"},
			{y: aObj1.mexicocity, label: "Mexico city"},
			{y: aObj1.bangkok, label: "Bangkok"}
			];	
		myfunction2();
	  } else if(str==="lang"){
		k=[{y: aObj1.en, label: "English"},
			{y: aObj1.hi, label: "Hindi"},
			{y: aObj1.fr, label: "French"},
			{y: aObj1.es, label: "Spanish"},
			{y: aObj1.th, label: "Thai"}
			];	
		myfunction2();
	  }
	  else if(str==="tweet_date"){
		  myfunction3();
	  }
	  else if(str==="hashtags"){
		  myfunction3();
	  }
	  else if(str==="entities.user_mentions.name"){
		  myfunction3();
	  }
	  
	 
}


function myfunction2() {
var chart = new CanvasJS.Chart("chartContainer", {
	animationEnabled: true,
	title: {
		text: str
	},
	
	data: [{
		type: "pie",
		startAngle: 240,
		yValueFormatString: "##0.00\"%\"",
		indexLabel: "{label} {y}",
		dataPoints: k
	}]
});
chart.render();
}
function myfunction3(){
	var array=String(aObj1.st).split(',');
	var it=[1];var st=["s"];
	var i;
	for(i=0;i<array.length;i=i+2){
		st.push(array[i]);
		it.push(array[i+1]);}
	it.shift();st.shift();
	var ctx=document.getElementById("ctx");
var myLineChart = new Chart(ctx, {
    type: 'line',
    data: {
   datasets: [{
       data: it,
       label: "Tweet Count Analysis by "+str,
       borderColor: "#c45850",
       backgroundColor: 'rgba(255, 99, 132, 0.2)',
       fill: true
   }],
   labels: st

  }
});
}
function clearBox(elementID)
{
    document.getElementById(elementID).innerHTML = "";
}
</script>

</div>

</body>
</html>