<?PHP 
$hostname_localhost ="localhost";
$database_localhost ="besustainable";
$username_localhost ="root";
$password_localhost ="";

$json=array();

	$idconsumer = $_POST["idconsumer"];;
	
	// Create connection
	$conn = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
	// Check connection
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	} 

	$sql = "select img from consumer where idconsumer = ".$idconsumer;

	$resultado = mysqli_query($conn, $sql);

	while($registro = mysqli_fetch_array($resultado)){
	
		$result["img"] = base64_encode($registro['img']);
		$json['image'][] = $result;

	}
	mysqli_close($conn);
	echo json_encode($json);

?>