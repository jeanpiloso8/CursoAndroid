<?php
$servername = "localhost";
$username = "id16124965_codbarraguayaquil";
$password = "Telconet2021.";
$database = "id16124965_dbcodigo";

try {
    $conn = new PDO("mysql:host=$servername;dbname=$database", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    echo "Connected successfully"; 
    } catch(PDOException $e) {    
    echo "Connection failed: " . $e->getMessage();
    }
?>