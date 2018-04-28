<?PHP 
$hostname_localhost ="localhost";
$database_localhost ="besustainable";
$username_localhost ="root";
$password_localhost ="";

// Obtain the values for the insert POST way 
	
	$idproduct = $_POST["idproduct"];
	$idconsumer = $_POST["idconsumer"];
	$sustainableAVG = $_POST["sustainableAVG"];
	$economyAVG = $_POST["economyAVG"];
	$satisfactionAVG = $_POST["satisfactionAVG"];
	
	// Create connection
	$conn = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
	// Check connection
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	} 

	$sql = "insert into rate (idproduct, idconsumer, sustainableAVG, economyAVG, satisfactionAVG) ".
		   "values (".$idproduct.", ".$idconsumer.", ".$sustainableAVG.", ".$economyAVG.", ".$satisfactionAVG.")";

	if ($conn->query($sql) === TRUE) {
	    echo "New record created successfully";
	} else {
	    echo "Error: " . $sql . "<br>" . $conn->error;
	}

	$conn->close();

?>