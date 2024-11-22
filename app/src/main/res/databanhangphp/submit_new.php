<?php
include "connect.php";



if(isset($_POST['submit_password']) && $_POST['email'])
{
  $email=$_POST['email'];
  $pass=$_POST['password'];
  // Truy vấn dữ liệu từ bảng user với MD5 mã hóa
    $query = "update user set pass='$pass' where email='$email'";
   // print_r($query);
    $data = mysqli_query($conn, $query);
      if($data == true) {
        echo "Thanh cong";
      }



}
?>