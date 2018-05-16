<?PHP 
include 'conn.php';

$json=array();
	
	// Create connection
	$conn = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
	// Check connection
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	} 

	$sql = "select city.idcity AS id_city, city.name AS name_city from plant join city ".
		   "ON plant.idcity = city.idcity";

	$resultado = mysqli_query($conn, $sql);

	while($registro = mysqli_fetch_array($resultado)){
	
		$result["id_city"] = $registro['id_city'];
		$result["name_city"] = utf8_encode($registro['name_city']);
		$json['cities'][] = $result;

	}
	mysqli_close($conn);
	echo json_encode($json);

?>