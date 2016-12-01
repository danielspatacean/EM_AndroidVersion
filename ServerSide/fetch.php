<?php
define('HOST','localhost');
define('USER','root');
define('PASS','1234');
define('DB','Persons');
 
$con = mysqli_connect(HOST,USER,PASS,DB);
 
$sql = "select * from Persons";
 
$res = mysqli_query($con,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,
array('name'=>$row[1],
'phone'=>$row[2],
'address'=>$row[3]
));
}
 
echo json_encode(array("result"=>$result));
 
mysqli_close($con);
 
?>