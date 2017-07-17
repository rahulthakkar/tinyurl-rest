<html>
<head>

<title>Simple TinyURL</title>
</head>
<body>
<div align="center">
	<h2>Simple TinyURL</h2><br/>
	<form action="TinyURLClientServlet" method="post">
		<input type="hidden" name="action" value="post"/>
		<b>Enter a long URL to make tiny:</b><br/>
		<textarea name="longURL" rows="6"></textarea>
		<input type="submit" value="Make TinyURL!">
	</form>
	<br/><br/>
	<form action="TinyURLClientServlet" method="get">
		<b>Enter the tinyURL hash get longURL:</b><br/>
		http://tinyurl.com/
		<input type="text" name="shortGetURLHash"></input>
		<input type="submit" value="Get LongURL!">
	</form>
	<form action="TinyURLClientServlet" method="post">
	 	<input type="hidden" name="action" value="delete"/>
		<b>Enter the tinyURL hash to Blocklist:</b><br/>
		http://tinyurl.com/
		<input type="text" name="shortDeleteURLHash"></input>
		<input type="submit" value="Blocklist TinyURL!">
	</form>
</div>
</body>
</html>
