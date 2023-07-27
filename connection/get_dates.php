<?php
require "conn.php";


if($conn){
	
	
$sql_Schedule = $conn->prepare("SELECT `id`,`date`,`time`,`doctor_id` FROM `work_days` ORDER BY `time` DESC, `date` ASC");

$sql_Schedule->execute();

 //binding results to the query 
 $sql_Schedule->bind_result($ID,$D_ID,$date, $time);
 
 $A = array(); 
 
 //traversing through all the result 
 while($sql_Schedule->fetch()){
 $data = array();
 
 $data['id'] = $ID; 
 $data['doctor_id'] = $D_ID;
 $data['date'] = $date;
 $data['time'] = $time;
 
 array_push($A, $data);
 }
 
 //displaying the result in json format 
 echo json_encode(array('A'=>$A));
}
?>
