<?php
include "connect.php";

$email = $_POST['email'];
$pass = $_POST['pass'];
$username = $_POST['username'];
$mobile = $_POST['mobile'];

// Check data
$query = "SELECT * FROM `user` WHERE `email` = '$email'";
$data = mysqli_query($conn, $query);
$numrow = mysqli_num_rows($data);

// Remove the echo statement
// echo $numrow;

if ($numrow > 0) {
    $arr = [
        'success' => false,
        'message' => "Email đã tồn tại"
    ];
} else {
    // Truy vấn dữ liệu từ bảng 'user'
    $query = "INSERT INTO `user` (`email`, `pass`, `username`, `mobile`) VALUES ('$email', '$pass', '$username', '$mobile')";
    $data = mysqli_query($conn, $query);

    // Kiểm tra nếu kết quả không rỗng
    if ($data == true) {
        $arr = [
            'success' => true,
            'message' => "Thành công",
        ];
    } else {
        $arr = [
            'success' => false,
            'message' => "Không thành công",
        ];
    }
}

// In ra kết quả dưới dạng JSON
echo json_encode($arr);
?>
