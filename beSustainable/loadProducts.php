<?PHP 
$hostname_localhost ="localhost";
$database_localhost ="besustainable";
$username_localhost ="root";
$password_localhost ="";

$json=array();

	$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);

//RECUPERO EL PRODUCTO MINIMO Y MAXIMO 
	$minProduct = $_GET["minProduct"];
	$maxProduct = $_GET["maxProduct"];
	
	$consulta = "select p.*, c.name AS NAME_CATEGORY, c.measure AS MEASURE, pl.name AS PLANT_NAME, hq.name AS NAME_HQ ".
				"FROM product p JOIN category c JOIN plant pl JOIN headquarter hq ".
				"ON p.idcategory = c.idcategory ". 
			    "AND p.idplant = pl.idplant ".
				"AND pl.idhq = hq.idhq ".
				"order by p.idproduct ".
				"limit ".$minProduct.", ".$maxProduct;
	$resultado = mysqli_query($conexion, $consulta);

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
		mysqli_close($conexion);
		echo json_encode($json);

?>
