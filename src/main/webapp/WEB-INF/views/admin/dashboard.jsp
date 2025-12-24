<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title>관리자 대시보드</title>

<style>
body {
	font-family: Arial;
	background: #111;
	color: white;
	margin: 0;
}

.header {
	padding: 20px;
	font-size: 28px;
	font-weight: bold;
	background: #181818;
}

.container {
	padding: 20px;
}

.grid {
	display: grid;
	grid-template-columns: repeat(4, 1fr);
	gap: 18px;
}

.card {
	background: #1f1f1f;
	padding: 20px;
	border-radius: 12px;
	text-align: center;
	box-shadow: 0 0 12px rgba(0, 0, 0, .4);
}

.card h2 {
	margin: 0;
	font-size: 16px;
	color: #aaa;
}

.card p {
	margin: 10px 0 0 0;
	font-size: 28px;
}

.chart-box {
	margin-top: 40px;
	background: #1f1f1f;
	padding: 20px;
	border-radius: 12px;
}
</style>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

</head>
<body>

	<div class="header">관리자 대시보드</div>

	<div class="container">

		<div class="grid">
			<div class="card">
				<h2>전체 사용자</h2>
				<p>${totalUsers}</p>
			</div>
			<div class="card">
				<h2>FREE 사용자</h2>
				<p>${freeUsers}</p>
			</div>
			<div class="card">
				<h2>PREMIUM 사용자</h2>
				<p>${premiumUsers}</p>
			</div>
			<div class="card">
				<h2>오늘 가입</h2>
				<p>${todayJoin}</p>
			</div>
		</div>

		<div class="chart-box">
			<canvas id="userChart"></canvas>
		</div>

		<div class="chart-box">
			<canvas id="premiumChart"></canvas>
		</div>

	</div>

	<script>
		const total = $
		{
			totalUsers
		};
		const free = $
		{
			freeUsers
		};
		const premium = $
		{
			premiumUsers
		};
		const activePremium = $
		{
			activePremium
		};

		new Chart(document.getElementById("userChart"), {
			type : "bar",
			data : {
				labels : [ "전체", "FREE", "PREMIUM" ],
				datasets : [ {
					label : "User Stats",
					backgroundColor : [ "#00c853", "#00b0ff", "#ffd600" ],
					data : [ total, free, premium ]
				} ]
			}
		});

		new Chart(document.getElementById("premiumChart"), {
			type : "doughnut",
			data : {
				labels : [ "활성 Premium", "나머지" ],
				datasets : [ {
					backgroundColor : [ "#ff1744", "#424242" ],
					data : [ activePremium, total - activePremium ]
				} ]
			}
		});
	</script>

</body>
</html>
