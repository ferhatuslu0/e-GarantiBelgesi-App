<?php
$servername = "127.0.0.1";
$username = "root";
$password = "";
$database = "sunucuveritabani";

$conn = new mysqli($servername, $username, $password, $database);


if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$urunkod = $_POST["iletiUrunKod"];
$urunad = $_POST["iletiUrunAd"];
$ileti = $_POST["iletiMesaj"];
$email = $_POST["iletiEmail"];
$mesajDurum = $_POST["durum"];

$qr = "INSERT INTO iletiler VALUES(null,'$urunkod','$urunad','$ileti','$email','$mesajDurum')";
$kmt = mysqli_query($conn,$qr); 

?>


