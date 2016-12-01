<?php
define('HOST','localhost');
define('USER','root');
define('PASS','1234');
define('DB','Persons');
$con = mysqli_connect(HOST,USER,PASS,DB);
if (!$con) 
		{
		die("Connection failed: " . mysqli_connect_error());
		}
//echo "Connected successfully";

  $name = $_POST['name'];
  $phone = $_POST['phone'];
  $address = $_POST['address'];
 
   $sql = "delete from persons where nume = '$name' and telefon = '$phone' and adresa = '$address'";
   if(mysqli_query($con,$sql)){
    echo 'success';
  }
  else{
    echo 'failure';
  }
  
  mysqli_close($con);
  
?>