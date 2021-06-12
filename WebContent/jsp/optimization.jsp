<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/chart.js@3.2.0/dist/chart.min.js"></script>
	<script type="text/javascript" id="MathJax-script" async
		src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-chtml.js">
	</script>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<title>Optimization</title>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col">
				<form id="settingForm" action="/foNamic/" method="post">
					<fieldset>
						<legend>Settings of Genetic Algorithm</legend>
						<input id="hideAction" type="hidden" name="action" value="GAsettings" /> 
						<label class="form-lable">Select control object:</label>
							<div style="margin: auto;" class="row">
							<div class="col form-check">
								<input style="margin-top: 28px;" class="form-check-input" type="radio" name="controlObject" id="fo_2" value="fo2">
								<label class="form-check-label" for="fo_2">
									$$\frac{K}{a_{1}s^{1+\mu}+a_{0}s^{\mu}+1}$$
								</label>
							</div>
							<div class="col form-check">
								<input style="margin-top: 28px;" class="form-check-input" type="radio" name="controlObject" id="fo_1" value="fo1">
								<label class="form-check-label" for="fo_1">
									$$\frac{K}{a_{1}s+a_{0}s^{\mu}+1}$$
								</label>
							</div>
							<div class="col form-check">
								<input style="margin-top: 28px;" class="form-check-input" type="radio" name="controlObject" id="fo_0" value="fo0" checked>
								<label class="form-check-label" for="fo_0">
									$$\frac{K}{a_{0}s^{\mu}+1}$$
								</label>
							</div>
							</div>
							
							<label class="form-lable">Range of coefficients</label>
							<div class="row">
							<div class="col">
								<label for="kStart" class="form-label">Start value of \(K\):</label>
								<input type="number" name="kStart" step="0.0001" min="0.0001" max="10000" class="form-control" id="kStart" value="1" required>
							</div>

							<div class="col">
								<label for="kFinal" class="form-label">Final value of \(K\):</label>
								<input type="number" name="kFinal" step="0.0001" min="0.0002" max="10000" class="form-control" id="kFinal" value="50" required>
							</div>
							
							<div class="col">
								<label for="kStep" class="form-label">Precision \(K\):</label>
								<input type="number" name="kStep" step="0.00001" min="0.0001" max="100" class="form-control" id="kStep" value="0.01" required>
							</div>
							</div>
							
							<div class="row">
							<div class="col">
								<label for="muStart" class="form-label">Start value of \(\mu\):</label>
								<input type="number" name="muStart" step="0.0001" min="0.0001" max="10000" class="form-control" id="muStart" value="0.2" required>
							</div>

							<div class="col">
								<label for="muFinal" class="form-label">Final value of \(\mu\):</label>
								<input type="number" name="muFinal" step="0.0001" min="0.0002" max="10000" class="form-control" id="muFinal" value="0.8" required>
							</div>
							
							<div class="col">
								<label for="muStep" class="form-label">Precision \(\mu\):</label>
								<input type="number" name="muStep" step="0.00001" min="0.0001" max="100" class="form-control" id="muStep" value="0.001" required>
							</div>
							</div>

							<div class="row">
							<div class="col">
								<label for="a0Start" class="form-label">Start value of \(a_0\):</label>
								<input type="number" name="a0Start" step="0.0001" min="0.0001" max="10000" class="form-control" id="a0Start" value="1" required>
							</div>

							<div class="col">
								<label for="a0Final" class="form-label">Final value of \(a_0\):</label>
								<input type="number" name="a0Final" step="0.0001" min="0.0002" max="10000" class="form-control" id="a0Final" value="5" required>
							</div>
							
							<div class="col">
								<label for="a0Step" class="form-label">Precision \(a_0\):</label>
								<input type="number" name="a0Step" step="0.00001" min="0.0001" max="100" class="form-control" id="a0Step" value="0.001" required>
							</div>
							</div>

							<div class="row" id="rowExtra" hidden>
							<div class="col">
								<label for="a1Start" class="form-label">Start value of \(a_1\):</label>
								<input type="number" name="a1Start" step="0.0001" min="0.0001" max="10000" class="form-control" id="a1Start" value="0.1" required>
							</div>

							<div class="col">
								<label for="a1Final" class="form-label">Final value of \(a_1\):</label>
								<input type="number" name="a1Final" step="0.0001" min="0.0002" max="10000" class="form-control" id="a1Final" value="2" required>
							</div>
							
							<div class="col">
								<label for="a1Step" class="form-label">Precision \(a_1\):</label>
								<input type="number" name="a1Step" step="0.00001" min="0.0001" max="100" class="form-control" id="a1Step" value="0.001" required>
							</div>
							</div>
							
							<div style="margin-top: 20px;" class="row align-items-center">
								<div class="col-auto">
									<label for="generationSize" class="col-form-label">Generation size:</label>
								</div>
								<div class="col-auto">
									<input type="number" name="generationSize" step="1" min="10" max="10000" class="form-control" id="numGeneration" value="1000" required>
								</div>
								<div class="col-auto">
									<label for="numElitism" class="col-form-label">Eletism:</label>
								</div>
								<div class="col-auto">
									<input type="number" name="numElitism" step="1" min="1" max="10" class="form-control" id="numGeneration" value="5" required>
								</div>
							</div>
		
							<div style="margin-top: 20px;" class="row align-items-center">
								<div class="col-auto">
									<label class="col-form-label">Selection type:</label>
								</div>
								<div class="form-check col-auto">
								<div>
									<input class="form-check-input" type="radio" name="selectionType" id="rTournament" value="tournament" checked>
									<label for="tournament" class="form-check-label">Tournament</label>
								</div>
								</div>
								<div class="form-check col-auto">
									<input class="form-check-input" type="radio" name="selectionType" id="rRoulette" value="roulette" disabled>
									<label for="roulette" class="form-check-label">Roulette</label>
								</div>
								<div class="form-check col-auto">
									<input class="form-check-input" type="radio" name="selectionType" id="rRank" value="rank" disabled>
									<label for="rank" class="form-check-label">Rank</label>
								</div>
							</div>
							
							<ul class="nav nav-tabs">
								<li class="nav-item">
									<a class="nav-link active" >Tournament</a>
								</li>
								<li class="nav-item">
									<a class="nav-link disabled" href="">Roulette</a>
								</li>
								<li class="nav-item">
									<a class="nav-link disabled" href="">Rank</a>
								</li>	
							</ul>
							
							<div style="margin-top: 10px;" class="row align-items-center">
								<div class="col-auto">
									<label for="numSizeSubgroup" class="form-label">Subgroup size:</label>
								</div>
								<div class="col-auto">
									<input type="number" name="numSizeSubgroup" step="1" min="1" max="5" class="form-control" id="numSizeSubgroup" value="3" required>
								</div>
							</div>

							
					</fieldset>
				</form>
			</div>	
		</div>
	</div>
</body>
</html>