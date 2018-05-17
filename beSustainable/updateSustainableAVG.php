<?PHP 
	include 'conn.php';

	$idproduct = $_POST["idproduct"];
	
	// Create connection
	$conn = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
	// Check connection
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	} 

	$sql = "update product set sustainableAVG = (select AVG(sustainableAVG) from rate where idproduct = ".$idproduct." group by idproduct) where idproduct = ".$idproduct;
	$sqlEconomy = "update product set economyAVG = (select AVG(economyAVG) from rate where idproduct = ".$idproduct." group by idproduct) where idproduct = ".$idproduct;
	$sqlSatisfaction = "update product set satisfactionAVG = (select AVG(satisfactionAVG) from rate where idproduct = ".$idproduct." group by idproduct) where idproduct = ".$idproduct;


	mysqli_query($conn, $sql);
	mysqli_query($conn, $sqlEconomy);
	mysqli_query($conn, $sqlSatisfaction);

	mysqli_close($conn);

?>