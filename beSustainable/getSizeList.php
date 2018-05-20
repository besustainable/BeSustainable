<?PHP 
include 'conn.php';

$json=array();

	$satisfaction = $_POST["satisfaction"];
	$economics = $_POST["economics"];
	$totalvote = $_POST["totalvote"];
	$city = $_POST["city"];
	$name = $_POST["name"];
	$category = $_POST["category"];

	/* TEST
	$satisfaction = "";
	$economics = "";
	$totalvote = "";
	$city = "";
	$name = "";
	$category = "";
	*/


	$conn = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);

	
	$sql = "select COUNT(*) ".
				"FROM product p JOIN category c JOIN plant pl JOIN headquarter hq JOIN city cty ".
				"WHERE p.idcategory = c.idcategory ". 
			    "AND p.idplant = pl.idplant ".
				"AND pl.idhq = hq.idhq ".
				"AND cty.idcity = hq.idcity ";

	filters();

	$result = mysqli_query($conn, $sql);
	$data = mysqli_fetch_array($result);
	
	$json['size'] = $data[0];


	mysqli_close($conn);
	echo json_encode($json);


	function filters(){

		if($GLOBALS['satisfaction'] != ""):
			$GLOBALS['sql'] .= "AND p.satisfactionAVG = ".$GLOBALS['satisfaction']." ";
		endif;
		
		if ($GLOBALS['economics'] != ""):
			$GLOBALS['sql'] .= "AND p.EconomyAVG = ".$GLOBALS['economics']." ";
		endif;
		
		if ($GLOBALS['totalvote'] != ""):
			$GLOBALS['sql'] .= "AND p.sustainableAVG = ".$GLOBALS['totalvote']." ";
		endif;
		
		if ($GLOBALS['city'] != ""):
			$GLOBALS['sql'] .= "AND cty.idcity = ".$GLOBALS['city']." ";
		endif;

		if ($GLOBALS['name'] != ""):
			$GLOBALS['sql'] .= "AND p.name LIKE '%".$GLOBALS['name']."%' ";
		endif;

		if ($GLOBALS['category'] != ""):
			$GLOBALS['sql'] .= "AND c.idcategory = ".$GLOBALS['category']." ";
		endif;

	}

?>
