<?php
define('HOST','localhost');
define('USER','root');
define('PASS','1234');
define('DB','Persons');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
 
$oldName = $_POST['oldName'];
$oldPhone = $_POST['oldPhone'];
$newName = $_POST['newName'];
$newPhone = $_POST['newPhone'];
$newAddress = $_POST['newAddress'];
 
$sql = "update persons SET nume='$newName', telefon='$newPhone', adresa='$newAddress' where nume='$oldName' and telefon = '$oldPhone'";
 
$res = mysqli_query($con,$sql);
 
$check = mysqli_fetch_array($res);
 
if(isset($check)){
echo 'success';
}else{
echo 'failure';
}
 
mysqli_close($con);
?>