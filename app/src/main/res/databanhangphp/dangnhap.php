<?php
include "connect.php";

// Lấy dữ liệu từ POST
$email = $_POST['email'];
$pass = $_POST['pass'];

// Sử dụng chuẩn bị câu lệnh để bảo vệ chống SQL Injection
$query = 'SELECT * FROM `user` WHERE email = ? AND pass = ?';
$stmt = $conn->prepare($query);

// Kiểm tra xem câu lệnh có được chuẩn bị thành công không
if ($stmt === false) {
    die('Prepare failed: ' . $conn->error);
}

$stmt->bind_param("ss", $email, $pass);
$stmt->execute();
$data = $stmt->get_result();

// Tạo một mảng để lưu kết quả
$result = array();

// Lặp qua các hàng dữ liệu và thêm vào mảng kết quả
while ($row = $data->fetch_assoc()) {
    $result[] = $row;
}

// Kiểm tra nếu kết quả không rỗng
if (!empty($result)) {
    $arr = [
        'success' => true,
        'message' => "Thành công",
        'result' => $result
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "Không thành công",
        'result' => $result
    ];
}

// In ra kết quả dưới dạng JSON
header('Content-Type: application/json');
echo json_encode($arr);

// Đóng câu lệnh và kết nối
$stmt->close();
$conn->close();
?>
