<html>
<head>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<!------ Include the above in your HEAD tag ---------->
<link rel="stylesheet"
	href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/login.css">
<link rel="shortcut icon" href="favicon.ico" />
<title>Login</title>
</head>

<body>
	<div class="main">
		<div class="container">
			<center>
				<div class="middle">
					<div id="login">

						<form action="/cbers/login" method="post" role="form">

							<fieldset class="clearfix">

								<p>
									<span class="fa fa-user"></span><input id="emai" name="email" type="text"
										Placeholder="Email" required>
								</p>
								<!-- JS because of IE support; better: placeholder="Username" -->
								<p>
									<span class="fa fa-lock"></span><input id="password" name="password" type="password"
										Placeholder="Password" required>
								</p>
								<!-- JS because of IE support; better: placeholder="Password" -->

								<div>
									<span
										style="width: 48%; text-align: left; display: inline-block;"></span> <span
										style="width: 50%; text-align: right; display: inline-block;"><input
										type="submit" value="Sign In"></span>
								</div>

							</fieldset>
							<div class="clearfix"></div>
						</form>

						<div class="clearfix"></div>

					</div>
					<!-- end login -->
					<div class="logo">
						CBERS

						<div class="clearfix"></div>
					</div>

				</div>
			</center>
		</div>
	</div>
</body>
</html>
