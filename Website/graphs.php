<?php
	/* Database connection settings */
	$host = 'localhost';
	$user = 'root';
	$pass = '';
	$db = 'db';
	$mysqli = new mysqli($host,$user,$pass,$db) or die($mysqli->error);

	$data1 = '';
	$data2 = '';

	//query to get data from the table
	$sql = "SELECT * FROM `datasets` ";
    $result = mysqli_query($mysqli, $sql);

	//loop through the returned data
	while ($row = mysqli_fetch_array($result)) {

		$data1 = $data1 . '"'. $row['data2'].'",';
		$data2 = $data2 . '"'. $row['data1'] .'",';
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
    			var LINECHART = $('#lineModal'); 
window.myLineChart=new Chart(LINECHART, 
{ 
type: 'line',
    options: {
    elements: {
         point:{
            radius: 0
                }
    },
    scales: {
        xAxes: [{
            display: true,
            ticks: {
                autoSkip: true,
                maxTicksLimit: 2,
                maxRotation: 0,
                minRotation: 0
            },
            gridLines: {
                display: false
            }
        }],
        yAxes: [{
            ticks: {
                suggestedmax: 13000,
                suggestedmin: 13000
            },
            display: true,
            gridLines: {
                display: false
            }
        }]
    },
    legend: {
        display: legendState
    }
},
data: {
    labels: modalChartDates[0],
    datasets: [
        {
            label: "Asset Price",
            fill: true,
            lineTension: 0.2,
            backgroundColor: "transparent",
            borderColor: "rgba(134, 77, 217, 0.57)",
            pointBorderColor: "rgba(134, 77, 217, 0.57)",
            pointHoverBackgroundColor: "rgba(134, 77, 217, 0.57)",
            borderCapStyle: 'butt',
            borderDash: [],
            borderDashOffset: 0.0,
            borderJoinStyle: 'miter',
            borderWidth: 2,
            pointBackgroundColor: "#fff",
            pointBorderWidth: 0,
            pointHoverRadius: 3,
            pointHoverBorderColor: "#fff",
            pointHoverBorderWidth: 3,
            pointRadius: 0,
            pointHitRadius: 5,
            data: modalChartData[0],
            spanGaps: false
        },
      {
            label: "Moving Average",
            fill: true,
            lineTension: 0.2,
            backgroundColor: "transparent",
            borderColor: "rgba(75, 75, 75, 0.7)",
            pointBorderColor: "rgba(75, 75, 75, 0.7)",
            pointHoverBackgroundColor: "rgba(75, 75, 75, 0.7)",
            borderCapStyle: 'butt',
            borderDash: [],
            borderDashOffset: 0.0,
            borderJoinStyle: 'miter',
            borderWidth: 2,
            pointBackgroundColor: "#fff",
            pointBorderWidth: 0,
            pointHoverRadius: 3,
            pointHoverBorderColor: "#fff",
            pointHoverBorderWidth: 3,
            pointRadius: 0,
            pointHitRadius: 5,
            data: modalMovingAverageData[0],
            spanGaps: false
        }

    ]
}
});
for(let i=0;i<10;i++){
     myLineChart.data.dataset.push({
            label: "item "+i,
            fill: true,
            lineTension: 0.2,
            backgroundColor: "transparent",
            borderColor: "rgba(75, 75, 75, 0.7)",
            pointBorderColor: "rgba(75, 75, 75, 0.7)",
            pointHoverBackgroundColor: "rgba(75, 75, 75, 0.7)",
            borderCapStyle: 'butt',
            borderDash: [],
            borderDashOffset: 0.0,
            borderJoinStyle: 'miter',
            borderWidth: 2,
            pointBackgroundColor: "#fff",
            pointBorderWidth: 0,
            pointHoverRadius: 3,
            pointHoverBorderColor: "#fff",
            pointHoverBorderWidth: 3,
            pointRadius: 0,
            pointHitRadius: 5,
            data: modalMovingAverageData[0],
            spanGaps: false
        });
 }
 //Use the window object to update myLineChart
 window.myLineChart.update();
			</script>
	    </div>
	    
	</body>
</html>