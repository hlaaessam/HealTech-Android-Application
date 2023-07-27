<?php
require "conn.php";


if($conn){
	
	$vitals = $conn->prepare("SELECT * FROM `patient_vitals` ORDER BY `time`, `date` ASC LIMIT 20");
 
 //executing the query 
 $vitals->execute();
 
 //binding results to the query 
 $vitals->bind_result($ID,$blood_pressure, $oxygen, $temperture, $heart_rate, $date, $time);
 
 $vital = array(); 
 
 //traversing through all the result 
 while($vitals->fetch()){
 $data = array();
 $data['ID'] = $ID;
 $data['blood_pressure'] = $blood_pressure;
$data['oxygen'] = $oxygen;
$data['temperature'] = $temperture;
$data['heart_rate'] = $heart_rate; 
 $data['date'] = $date; 
 $data['time'] = $time; 
 array_push($vital, $data);
 }
 
 //displaying the result in json format 
 echo json_encode(array('vital'=>$vital));
	
}
?>