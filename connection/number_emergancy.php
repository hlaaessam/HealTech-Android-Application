<?php
require "conn.php";


if($conn){
	
	
$sql_emergency = $conn->prepare("SELECT `national_id`,`first_emergancy_number` FROM `patient_users`");

$sql_emergency->execute();

 //binding results to the query 
 $sql_emergency->bind_result($national_id,$first_emergancy_number);
 
 $number = array(); 
 
 //traversing through all the result 
 while($sql_emergency->fetch()){
 $data = array();
 $data['national_id'] = $national_id; 
 $data['first_emergancy_number'] = $first_emergancy_number;

 array_push($number, $data);
 }
 
 //displaying the result in json format 
 echo json_encode(array('number'=>$number));
}
?>