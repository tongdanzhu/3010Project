var ctx = document.getElementById("chart").getContext('2d');
var ylabels =[<?php echo $intensity;?>];
var xlabels =[<?php echo $date;?>];
xlabels[0] = new Date(xlabels[0]);
xlabels[1] = new Date(xlabels[1]);
console.log(ylabels[0]);
console.log(xlabels[0]);	  
var myChart = new Chart(ctx, {
	type: 'line',
	data: {
		    	  labels: xlabels,
		        datasets: 
		       	[{
		      		data: [{
			      		x: xlabels[0],
		    				y: ylabels[0]},
		    			{
			         	x: xlabels[1],
		    				y: ylabels[1]},
		    			{
			          x: xlabels[2],
		    				y: ylabels[2]},
		    			{
			          x: xlabels[3],
		    				y: ylabels[3]},
		    			{
			          x: xlabels[4],
		    				y: ylabels[4]},
		    			{
			          x: xlabels[5],
		    				y: ylabels[6]},
		    			{
			          x: xlabels[7],
		    				y: ylabels[7]},
		    			{
			          x: xlabels[8],
		    				y: ylabels[8]},
		    			{
			          x: xlabels[9],
		    				y: ylabels[9]},
		    			{
			          x: xlabels[10],
		    				y: ylabels[10]},
		    			{
			          x: xlabels[11],
		    				y: ylabels[11]},
		    			{
			          x: xlabels[12],
		    				y: ylabels[12]},
		    			{
			          x: xlabels[13],
		    				y: ylabels[13]},
		    			{
			          x: xlabels[14],
		    				y: ylabels[14]},
		    			{
			          x: xlabels[15],
		    				y: ylabels[15]
		    			}],
							label: 'Light Intensity',
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
		        			min: xlabels[10],
		        			max: xlabels[0],
		        			displayFormats: 
		        			{
		        				second: 'MMM D h:mm:ss a'
		        			}
		        		},
		        	}]
		        }
		       }
		    });