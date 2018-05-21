<?PHP 
include 'conn.php';

$json=array();

	
	$idconsumer = $_POST["idconsumer"];
	$newsletter = strtolower(utf8_encode($_POST["newsletter"]));
	
	/*
	$idconsumer = "1";
	$newsletter = strtolower(utf8_encode("true"));
    */
	
	// Create connection
	$conn = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
	// Check connection
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	} 

	switch($newsletter){
		case "true":
		$newsletter = 1;
		break;
		case "false":
		$newsletter = 0;
		break;
	}

	$sql = "update consumer SET newsletter = ".$newsletter.
		   " where idconsumer = ".$idconsumer;

	if (mysqli_query($conn, $sql)) {
    	//echo "Record updated successfully";
    	$json['message'] = 'Record updated successfully';
	} else {
	    //echo "Error updating record: " . mysqli_error($conn);
    	$json['message'] = 'Error updating...';
	}

	mysqli_close($conn);
	echo json_encode($json);

?>