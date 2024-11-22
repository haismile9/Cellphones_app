<?php
include "connect.php";

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

require 'PHPMailer/src/Exception.php';
require 'PHPMailer/src/PHPMailer.php';
require 'PHPMailer/src/SMTP.php';

$email = mysqli_real_escape_string($conn, $_POST['email']); // Giả sử email được gửi qua phương thức POST

// Truy vấn dữ liệu từ bảng 'user'
$query = "SELECT * FROM `user` WHERE `email` ='$email'";
$data = mysqli_query($conn, $query);

// Tạo một mảng để lưu kết quả
$result = array();

// Lặp qua các hàng dữ liệu và thêm vào mảng kết quả
while ($row = mysqli_fetch_assoc($data)) {
    $result[] = $row;
}

if (empty($result)) {
    $arr = [
        'success' => false,
        'message' => "Mail không chính xác",
        'result'  => $result
    ];
} else {

    // send mail
    $email = $result[0]["email"];
    $pass = $result[0]["pass"];
    $link = "<a href='http://10.7.90.75/banhang/reset_pass.php?key=".$email."&reset=".$pass."'>Click To Reset Password</a>";
   
    $mail = new PHPMailer();
$mail->CharSet = "utf-8";
$mail->IsSMTP();
$mail->SMTPAuth = true;
$mail->Username = "110502nguyenhonghai@thptphanliem.bentre.edu.vn";  // Địa chỉ email mới
$mail->Password = "t a s h n j j f q v r y z s y s
"; // Thay đổi thành mật khẩu ứng dụng nếu bạn đang sử dụng xác thực hai yếu tố
$mail->Host = "smtp.gmail.com";
$mail->SMTPSecure = "tls";  // Thay đổi từ "ssl" thành "tls"
$mail->Port = 587;  // Thay đổi từ 465 thành 587
$mail->From = '110502nguyenhonghai@thptphanliem.bentre.edu.vn'; // Địa chỉ email người gửi
$mail->FromName = 'App bán hàng';
$mail->AddAddress($email, 'receiver_name');
$mail->Subject = 'Reset Password';
$mail->IsHTML(true);
$mail->Body = 'Click On This Link to Reset Password '.$link.'';


    if ($mail->Send()) {
        $arr = [
        'success' => true,
        'message' => "Vui lòng kiểm tra mail của bạn",
        'result' => $result
    ];
    } else {
        $arr = [
        'success' => false,
        'message' => "Gửi không thành công",
        'result' => $result
    ];
    }
}

print_r(json_encode($arr));
?>
