<?php
define('HOST','localhost');
define('USER','root');
define('PASS','1234');
define('DB','Persons');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
 
$username = $_POST['username'];
$password = $_POST['password'];
 
$sql = "select * from users where username='$username' and password='$password'";
 
$res = mysqli_query($con,$sql);
 
$check = mysqli_fetch_array($res);
 
if(isset($check)){
echo 'success';
}else{
echo 'failure';
}
 
mysqli_close($con);
?>