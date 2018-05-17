<?PHP 
include 'conn.php';

	
	// Get POST Values
	$idconsumer = $_POST["idconsumer"];
	$idproduct = $_POST["idproduct"];
	
	// Create connection
	$conn = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
	// Check connection
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	} 

	$sql = "delete from rate where idconsumer = ".$idconsumer." AND idproduct = ".$idproduct;

	if (mysqli_query($conn, $sql)) {
    	//echo "Record updated successfully";
	} else {
	    //echo "Error updating record: " . mysqli_error($conn);
	}

	mysqli_close($conn);

?>