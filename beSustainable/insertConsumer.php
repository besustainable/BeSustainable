<?PHP 
include 'conn.php';

$json=array();

// Obtain the values for the insert POST way 
	
	$idcity = $_POST["idcity"];
	$nick = utf8_encode($_POST["nick"]);
	$description = utf8_encode($_POST["description"]);
	$email = utf8_encode($_POST["email"]);
	$password = $_POST["password"];
	$birthday = $_POST["birthday"];
	$gender = utf8_encode($_POST["gender"]);
	$newsletter = $_POST["newsletter"];
	
	
	/* TEST
	$idcity = "6359231";
	$nick = "test";
	$description = "test";
	$email = "test@test.com";
	$password = "test";
	$birthday = "2018-5-16";
	$gender = "male";
	$newsletter = "1";
	*/

	// Create connection
	$conn = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
	// Check connection
	if ($conn->connect_error) {
	    die("Connection failed: " . $conn->connect_error);
	} 

	$sql = "insert into consumer (idcity, nick, description, email, password, birthday, gender, newsletter) ".
		   "values (".$idcity.", '".$nick."', '".$description."', '".$email."', MD5('".$password."'), ".
		   "'".$birthday."', '".$gender."', ".$newsletter.") ";

	if ($conn->query($sql) === TRUE) {
    	//echo "Record updated successfully";
    	$result['message'] =  'Sign Up successfully';
    	$json['request'][] = $result;
	} else {
	    //echo "Error updating record: " . mysqli_error($conn);
    	$result['message'] =  'Error To Sign Up.'/*. mysqli_error($conn)*/;
    	$json['request'][] = $result;
	}

	$conn->close();
	echo json_encode($json);

?>