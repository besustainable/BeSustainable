<?PHP 
include 'conn.php';

$json=array();
	
	// Create connection
	$conn = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
	// Check connection
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	} 

	$sql = "select idpos, idcity, name from pointofsale";

	$resultado = mysqli_query($conn, $sql);

	while($registro = mysqli_fetch_array($resultado)){
	
		$result["idpos"] = $registro['idpos'];
		$result["idcity"] = $registro['idcity'];
		$result["name"] = utf8_encode($registro['name']);
		$json['pointofsale'][] = $result;

	}
	mysqli_close($conn);
	echo json_encode($json);

?>