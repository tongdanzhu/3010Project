<?php
  echo 'check23   ';
	date_default_timezone_set("America/Toronto");
	echo "The time is " . date("Y/m/d h:i:sa");
	/* Database connection settings */
	$host = 'localhost';
	$user = 'root';
	$pass = '';
	$db = 'db';
	$mysqli = new mysqli($host,$user,$pass,$db) or die($mysqli->error);

	$data1 = '';
	$data2 = '';

	//query to get data from the table
	$sql = "SELECT * FROM `light` ORDER BY date DESC";
    $result = mysqli_query($mysqli, $sql);

	//loop through the returned data
	while ($row = mysqli_fetch_array($result)) {

		$data1 = $data1 . '"'. $row['intensity'].'",';
		$data2 = $data2 . '"'. $row['date'].'",';
	}

	$data1 = trim($data1,",");
	$data2 = trim($data2,",");
?>

<!DOCTYPE html>
<html>
	<head>
    	<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.bundle.min.js"></script>
		<title>Accelerometer data</title>

		<style type="text/css">			
			body{
				font-family: Arial;
			    margin: 80px 100px 10px 100px;
			    padding: 0;
			    color: white;
			    text-align: center;
			    background: #555652;
			}

			.container {
				color: #E8E9EB;
				background: #222;
				border: #555652 1px solid;
				padding: 10px;
			}
		</style>

	</head>

	<body>	   
	    <div class="container">	
	    <h1>USE CHART.JS WITH MYSQL DATASETS</h1>       
			<canvas id="chart" style="width: 100%; height: 65vh; background: #222; border: 1px solid #555652; margin-top: 10px;"></canvas>

			<script>
				var ctx = document.getElementById("chart").getContext('2d');
				var xlabels =[<?php echo $data1;?>];
				var ylabels =[<?php echo $data2;?>];
				ylabels[0] = new Date(ylabels[0]);
				ylabels[1] = new Date(ylabels[1]);
		    	console.log(ylabels[0]);
		    	console.log(xlabels[0]);
			  
    			var myChart = new Chart(ctx, {
        		type: 'line',
		        data: {
		            labels: ylabels,
		            datasets: 
		            	[{
		            		data: [{
			            		y: xlabels[0],
		    					x: ylabels[0]},
		    					{
			            		y: xlabels[1],
		    					x: ylabels[1]},
		    					{
			            		y: xlabels[2],
		    					x: ylabels[2]},
		    					{
			            		y: xlabels[3],
		    					x: ylabels[3]},
		    					{
			            		y: xlabels[4],
		    					x: ylabels[4]},
		    					{
			            		y: xlabels[5],
		    					x: ylabels[6]},
		    					{
			            		y: xlabels[7],
		    					x: ylabels[7]},
		    					{
			            		y: xlabels[8],
		    					x: ylabels[8]},
		    					{
			            		y: xlabels[9],
		    					x: ylabels[9]},
		    					{
			            		y: xlabels[10],
		    					x: ylabels[10]},
		    					{
			            		y: xlabels[11],
		    					x: ylabels[11]},
		    					{
			            		y: xlabels[12],
		    					x: ylabels[12]},
		    					{
			            		y: xlabels[13],
		    					x: ylabels[13]},
		    					{
			            		y: xlabels[14],
		    					x: ylabels[14]},
		    					{
			            		y: xlabels[15],
		    					x: ylabels[15]
		    			}],
		                label: 'Data 1',
		                backgroundColor: 'transparent',
		                borderColor:'rgba(255,99,132)',
		                borderWidth: 3
		            }]
		        },
		     
		        options: {
		        	scales: {
		        		xAxes: [{
		        			type: 'time',
		        			time: {
		        				min: ylabels[10],
		        				max: ylabels[0],
		        				displayFormats: 
		        				{
		        					second: 'MMM D h:mm:ss a'
		        				}
		        			},
		        		}]
		        	}
		        }

		    });

			</script>
	    </div>
	    
	</body>
</html>