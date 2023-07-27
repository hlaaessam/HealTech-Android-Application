<?php
require "conn.php";


if($conn){
	
	$sensor = $conn->prepare("SELECT `ID` , `blood_pressure` , `oxygen` , `temperture` , `heart_rate` FROM `patient_vitals` ORDER BY  `date` DESC , `time` DESC limit 1");


 
 //executing the query 
 $sensor->execute();

 //binding results to the query 
 $sensor->bind_result($ID,$blood_pressure, $oxygen, $temperture, $heart_rate);
 
 $Sensor = array(); 
 
 //traversing through all the result 
 while($sensor->fetch()){
 $data = array();
 $data['ID'] = $ID;
 $data['blood_pressure'] = $blood_pressure;
 $data['oxygen'] = $oxygen;
 $data['temperature'] = $temperture;
 $data['heart_rate'] = $heart_rate; 
 array_push($Sensor, $data);
 }
 
 //displaying the result in json format 
 echo json_encode(array('Sensor'=>$Sensor));
}
?>