<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>Document</title>
	</head>
	<style>
		*{
			margin: 0;
			padding: 0;
		}
		.aaa {
			height: 100vh;
			background: url(/static/image/beijing.png) no-repeat;
			background-size:100% 100%;
			background-attachment:fixed;
			display: flex;
			flex-direction: column;
			justify-content: center;
			padding-left: 15vw;
			.aaa-title{
				font-size: 24px;
				font-weight: 700;
				padding: 20px 0;
			}
			.aaa-item{
				color: #999;
			}
		}
	</style>
	<body>
		<div class="aaa">
			<div class="aaa-title">暂时不支持该内容的对比，具体原因：</div>
			<div class="aaa-item">${errorMsg}</div>
		</div>
	</body>
</html>
