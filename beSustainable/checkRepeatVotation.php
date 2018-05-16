<?PHP 
include 'conn.php';

	// If give back an EmailErrorMessage Exception
	// If give back an PasswordErrorMessage Exception
	// Else give back all data of the consumer


$json=array();

	// Get POST Values
	$idconsumer = $_POST["idconsumer"];
	$idproduct = $_POST["idproduct"];
	

	/* TEST
	$idconsumer = "1";
	$idproduct = "22";
	*/
	
	

	// Create connection
	$conn = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
	// Check connection
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	}

	// Query
	$sql = "select EXISTS(select * from rate where idconsumer = ".$idconsumer." AND idproduct = ".$idproduct.")";

	$exist = mysqli_query($conn, $sql);
	$data = mysqli_fetch_array($exist);

	$message = "";
		
	if($data[0] == 1){
		$message = array('message' => 'true');
	}else{
		$message = array('message' => 'false');
	}

	echo json_encode($message, JSON_FORCE_OBJECT);

		


?>