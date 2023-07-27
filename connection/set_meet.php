<?php
require "conn.php";

$patient_id=$_POST["patient_id"];
$doctor_id=$_POST["doctor_id"];
$work_days_id=$_POST["work_days_id"];


/*$patient_id="20303140102388";
$doctor_id="8";
$work_days_id="138";*/

if($conn){
	

$sql_Schedule="INSERT INTO `patient_time`  (`patient_id`,`doctor_id`,`work_days_id`) VALUES('$patient_id','$doctor_id','$work_days_id')";

if(mysqli_query($conn,$sql_Schedule)){
echo "successfully Scheduled";
}
else{
echo "failed to Schedule";
}}
?>
