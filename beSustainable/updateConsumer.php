<?PHP 
$hostname_localhost ="localhost";
$database_localhost ="besustainable";
$username_localhost ="root";
$password_localhost ="";

$json=array();

	
	$idconsumer = $_POST["idconsumer"];
	$nick = utf8_encode($_POST["nick"]);
	$description = utf8_encode($_POST["description"]);
	//$img = "";
	$email = $_POST["email"];
	$birthday = $_POST["birthday"];
	$gender = $_POST["gender"];
	
	/* TEST
	$idconsumer = "3";
	$nick = "test";
	$description = "TESSSSTTTT";
	//$img = "";
	$email = "test@gmail.com";
	$birthday = "1998-01-20";
	$gender = "male";
	*/
	


	// Create connection
	$conn = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
	// Check connection
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	} 

	$sql = "update consumer SET nick = '".$nick."', ".
								"description = '".$description."', ".
								"email = '".$email."', ".
								"birthday = '".$birthday."', ".
								"gender = '".$gender."' ".
			"where idconsumer = ".$idconsumer;

	if (mysqli_query($conn, $sql)) {
    	//echo "Record updated successfully";
    	$result['message'] =  'Record updated successfully';
    	$json['request'][] = $result;
	} else {
	    //echo "Error updating record: " . mysqli_error($conn);
    	$result['message'] =  'Error updating record.'/*. mysqli_error($conn)*/;
    	$json['request'][] = $result;
	}

	mysqli_close($conn);
	echo json_encode($json);

?>