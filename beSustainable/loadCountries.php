<?PHP 
include 'conn.php';

$json=array();
	
	// Create connection
	$conn = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
	// Check connection
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	} 

	$sql = "select idcountry FROM country";

	$resultado = mysqli_query($conn, $sql);

	while($registro = mysqli_fetch_array($resultado)){
	
		$result["idcountry"] = $registro['idcountry'];
		$json['countries'][] = $result;

	}
	mysqli_close($conn);
	echo json_encode($json);

?>