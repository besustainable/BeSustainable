<?PHP 
include 'conn.php';

$json=array();


//RECUPERO EL PRODUCTO MINIMO Y MAXIMO 
	// TEST //
	/*
	$minProduct = 0;
	$maxProduct = 20;
	$satisfaction = "";
	$economics = "";
	$totalvote = "";
	$city = "";
	*/
	
	$limit = $_POST["limit"];

	$conn = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);

	
	$sql = "select p.*, c.name AS NAME_CATEGORY, c.measure AS MEASURE, pl.name AS PLANT_NAME, hq.name AS NAME_HQ ".
				"FROM product p JOIN category c JOIN plant pl JOIN headquarter hq JOIN city cty ".
				"WHERE p.idcategory = c.idcategory ". 
			    "AND p.idplant = pl.idplant ".
				"AND pl.idhq = hq.idhq ".
				"AND cty.idcity = hq.idcity ";

	//filters();

	$sql .=		"order by p.idproduct ".
				"limit ".$limit.", 20";

	$resultado = mysqli_query($conn, $sql);

	while($registro = mysqli_fetch_array($resultado)){
		
		$result["name"] = utf8_encode($registro['name']);
		$json['product'][] = $result;
	}
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
