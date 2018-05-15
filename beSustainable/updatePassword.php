<?PHP 
$hostname_localhost ="localhost";
$database_localhost ="besustainable";
$username_localhost ="root";
$password_localhost ="";

$json=array();

	
	$idconsumer = $_POST["idconsumer"];
	$oldpassword = utf8_encode($_POST["oldpassword"]);
	$newpassword = utf8_encode($_POST["newpassword"]);
	
	/*
	$idconsumer = "1";
	$oldpassword = "rafagc";
	$newpassword = "root";
	*/


	// Create connection
	$conn = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
	// Check connection
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	} 

	if(isValidPassword()){

		$sql = "update consumer SET password = MD5('".$newpassword."') ".
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

	}else{
		$result['message'] =  'Old Password Error...'/*. mysqli_error($conn)*/;
	    $json['request'][] = $result;
	}


	
	mysqli_close($conn);
	echo json_encode($json);

	function isValidPassword(){

		$passwordValidation = false;

		// Query
		$sql = "select EXISTS(select idconsumer from consumer where password = MD5('".$GLOBALS['oldpassword']."'))";

		$exist = mysqli_query($GLOBALS['conn'], $sql);
		$data = mysqli_fetch_array($exist);
		
		if($data[0] == 1)
			$passwordValidation = true;

		return $passwordValidation;


	}


?>