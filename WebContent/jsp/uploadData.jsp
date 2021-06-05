<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload data</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/chart.js@3.2.0/dist/chart.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col">
				<form id="myForm" action="/fonamic/" method="post"
					enctype="multipart/form-data">
					<fieldset>
						<legend>Input data</legend>
						<input type="hidden" name="action" value="upload" />
						<div class="row">
							<div class="col">
								<label for="stepTime" class="form-label">Step time size dt, s</label> 
								<input type="number" name="stepTime" step="0.00001"
									min="0.00001" max="1000" value="0.001" class="form-control"
									id="stepTime" required>
							</div>
							<div class="col">
								<label for="stepFuntionValue" class="form-label">Value of input step</label>
								<input type="number" name="stepFunctionValue"
									step="0.0001" min="0.0001" max="1000" value="1"
									class="form-control" id="stepFunctionValue" required>
							</div>

						</div>
						<div class="mb-3">
							<label for="csvFile" class="form-label">Select datafile</label> 
							<input class="form-control" type="file" name="csvFile" accept=".csv"
								id="csvFile" required>
							<p id="pInfo" hidden>ERROR TEXT</p>
						</div>

						<button type="submit" class="btn btn-primary">Upload</button>
						<button type="button" id="btnNext" class="btn btn-primary" disabled>Next</button>
					</fieldset>
				</form>
			</div>
			<div class="col">
				<canvas id="chart"></canvas>
			</div>
		</div>
	</div>

	<script>
		const myForm = document.getElementById("myForm");
		const csvFile = document.getElementById("csvFile");
		const pInfo = document.getElementById("pInfo");
		const chart = document.getElementById("chart").getContext("2d");
		const inputStep = document.getElementById("stepTime");
		const btnNext = document.getElementById("btnNext");
		
		var mainChart = drawChart([0, 1, 2, 3], [0, 0, 0, 0]);

		csvFile.addEventListener("click", changeFileClicked, false);
		function changeFileClicked(){
			pInfo.hidden = true;
			btnNext.disabled = true;
			csvFile.value = '';
		}

		myForm.addEventListener("submit", formSubmitClicked, false); 
		
		function formSubmitClicked(e){
			e.preventDefault();
			const input = csvFile.files[0];
			const reader = new FileReader();
			
			const stepSize = parseFloat(inputStep.value);

			reader.onload = function(event){
				const text = event.target.result;
				const data = getDataFromFile(text, stepSize);
				
				updateChart(mainChart, data[0], data[1]);
			};
			
			reader.readAsText(input);
		}
		
		function getDataFromFile(records, stepSize, delimiter = ','){
			const rows = records.split('\n');
			const rowsLength = rows.length;
			let xData = [];
			let yData = [];

			let item = 0.0;
			let step = 0.0;
			let row = "";
			for(let i = 0; i < rowsLength; ++i){
				xData.push(step);

				row = rows[i].split(delimiter);
				const firstRecord = row[0].trim();
				if(isNaN(firstRecord) || (firstRecord === '')){
					pInfo.innerHTML = "Error! Please, check the value at row - " + (i+1) + '.';
					pInfo.hidden = false;
					pInfo.style.color = "red";
					btnNext.disabled = true;
					
					xData = [0, 1, 2, 3];
					yData = [0, 0, 0, 0]
					break;
				}
				item = 	parseFloat(firstRecord);
				yData.push(item);

				step = Math.round((step + stepSize)*100000) / 100000;
			}
			const data = [xData, yData];
			btnNext.disabled = false;
			return data;
		}

		function drawChart(xData, yData){
			const data = {
				labels: xData,
				datasets: [{
					label: "Reference data",
					backgroundColor: "rgb(255,99,132)",
					borderColor: "rgb(255,99,132)",
					data: yData,
					pointRadius: 0,
				}]
			};
			const config = {
				type: "line",
				data,
				options: {}
			};
			var myChart = new Chart(
				chart,
				config
				);
			
			return myChart;	
		}
		
		function updateChart(chart, xData, yData){
			chart.data.labels = xData;
			chart.data.datasets[0].data = yData;

			chart.update();
		}
	
	</script>

</body>
</html>