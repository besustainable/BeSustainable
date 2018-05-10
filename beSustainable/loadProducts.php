<?PHP 
$hostname_localhost ="localhost";
$database_localhost ="besustainable";
$username_localhost ="root";
$password_localhost ="";

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
	
	$minProduct = $_POST["minProduct"];
	$maxProduct = $_POST["maxProduct"];
	$satisfaction = $_POST["satisfaction"];
	$economics = $_POST["economics"];
	$totalvote = $_POST["totalvote"];
	$city = $_POST["city"];
	$name = $_POST["name"];
	$category = $_POST["category"];
	
	

	$conn = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);

	
	$sql = "select p.*, c.name AS NAME_CATEGORY, c.measure AS MEASURE, pl.name AS PLANT_NAME, hq.name AS NAME_HQ ".
				"FROM product p JOIN category c JOIN plant pl JOIN headquarter hq JOIN city cty ".
				"WHERE p.idcategory = c.idcategory ". 
			    "AND p.idplant = pl.idplant ".
				"AND pl.idhq = hq.idhq ".
				"AND cty.idcity = hq.idcity ";

	filters();

	$sql .=		"order by p.idproduct ".
				"limit ".$minProduct.", ".$maxProduct;

	$resultado = mysqli_query($conn, $sql);

	while($registro = mysqli_fetch_array($resultado)){
		
		$result["idproduct"] = $registro['idproduct'];
		$result["idplant"] = $registro['idplant'];
		$result["idcategory"] = $registro['idcategory'];
		$result["name"] = utf8_encode($registro['name']);
		$result["description"] = utf8_encode($registro['description']); 
		$result["weight"] = $registro['weight']; 
		$result["pvp"] = $registro['pvp']; 
		$result["img"] = base64_encode($registro['img']); 
		$result["sustainableAVG"] = $registro['sustainableAVG']; 
		$result["EconomyAVG"] = $registro['EconomyAVG']; 
		$result["satisfactionAVG"] = $registro['satisfactionAVG']; 
		$result["fairtrade"] = $registro['fairtrade']; 
		$result["ecology"] = $registro['ecology']; 
		$result["local"] = $registro['local']; 
		$result["besustainable"] = $registro['besustainable']; 
		$result["origin"] = utf8_encode($registro['origin']); 
		$result["Category_Name"] = utf8_encode($registro['NAME_CATEGORY']); 
		$result["Measure"] = utf8_encode($registro['MEASURE']); 
		$result["Plant_Name"] = utf8_encode($registro['PLANT_NAME']); 
		$result["Hq_Name"] = utf8_encode($registro['NAME_HQ']); 
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
