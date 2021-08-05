<?php

$servername = "127.0.0.1";
$username = "root";
$password = "";
$database = "sunucuveritabani";
 
 

$conn = new mysqli($servername, $username, $password, $database);
 

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}


 $sorgu = "SELECT * FROM satistablosu";
  $kmt = mysqli_query($conn,$sorgu); 
 
  if($kmt){
	   $kmtDizisi['gelenVeriler'] = array();
	  
    while($geciciDizi = mysqli_fetch_array($kmt)){
		$hbr=array();
      
		$hbr["tc"] = $geciciDizi["tc"];
		$hbr["ad"] = $geciciDizi["ad"];
		$hbr["soyad"] = $geciciDizi["soyad"];
		$hbr["tel"] = $geciciDizi["tel"];
		$hbr["eMail"] = $geciciDizi["eMail"];
		$hbr["adres"] = $geciciDizi["adres"];
		$hbr["magazaAdi"] = $geciciDizi["magazaAdi"];
		 $hbr["urunKodu"] = $geciciDizi["urunKodu"];
        $hbr["urunAdi"] = $geciciDizi["urunAdi"];
        $hbr["urunFiyat"] = $geciciDizi["urunFiyat"];
        $hbr["urunTarih"] = $geciciDizi["urunTarih"];
		$hbr["garantiSure"] = $geciciDizi["garantiSure"];
		$hbr["nakitKart"] = $geciciDizi["nakitKart"];
		 $hbr["urunFoto"] = $geciciDizi["urunFoto"];
	   $hbr["garantiFoto"] = $geciciDizi["garantiFoto"];
		
		
		
		
		array_push($kmtDizisi['gelenVeriler'],$hbr);
    }    
    echo json_encode($kmtDizisi);  
  }

  mysqli_close($conn);
?>
 