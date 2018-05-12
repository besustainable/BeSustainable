<?PHP 
$hostname_localhost ="localhost";
$database_localhost ="besustainable";
$username_localhost ="root";
$password_localhost ="";

	// If give back an EmailErrorMessage Exception
	// If give back an PasswordErrorMessage Exception
	// Else give back all data of the consumer


$json=array();

	// Get POST Values
	
	
	$email = $_POST["email"];
	$password = $_POST["password"];
	

	/*
	$email = "r.gc@hotmail.es";
	$password = "rafagc";
	*/
	

	// Create connection
	$conn = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
	// Check connection
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	} 

	if(isValidEmail()){

		$sql = "select c.*, cty.name AS CITYNAME ".
			   "from consumer c JOIN city cty ".
			   "ON c.idcity = cty.idcity ". 
			   "where email = '".$email."' AND password = MD5('".$password."')";

		$resultado = mysqli_query($conn, $sql);

		while($registro = mysqli_fetch_array($resultado)){
		
			$result["idconsumer"] = $registro['idconsumer'];
			$result["idcity"] = $registro['idcity'];
			$result["cityname"] = utf8_encode($registro['CITYNAME']);
			$result["nick"] = utf8_encode($registro['nick']);
			$result["description"] = utf8_encode($registro['description']);
			$result["img"] = base64_encode($registro['img']); 
			$result["email"] = utf8_encode($registro['email']);
			$result["birthday"] = $registro['birthday'];
			$result["gender"] = utf8_encode($registro['gender']);
			if($registro["newsletter"] == 1)
				$result["newsletter"] = true;
			else
				$result["newsletter"] = false;

			$json['consumer'][] = $result;

		}

		// Password Control
		if(empty($json)){
			//Default
			$result["ErrorMessage"] = "PasswordErrorMessage";
			$json['consumer'][] = $result;
		}else{
			$correct["Correct"] = "CorrectMessage";
			$json['consumer'][] = $correct;
		}
	}else{

		$result["ErrorMessage"] = "EmailErrorMessage";
		$json['consumer'][] = $result;

	}	


	mysqli_close($conn);
	echo json_encode($json);

	// Function Email Validator

	function isValidEmail(){

		$emailValidation = false;

		// Query
		$sql = "select EXISTS(select idconsumer from consumer where email = '".$GLOBALS['email']."')";

		$exist = mysqli_query($GLOBALS['conn'], $sql);
		$data = mysqli_fetch_array($exist);
		
		if($data[0] == 1)
			$emailValidation = true;

		return $emailValidation;


	}

?>