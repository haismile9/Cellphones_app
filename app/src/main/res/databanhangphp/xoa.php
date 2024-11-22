<?php
include "connect.php";

$id = $_POST['id'];

// Kiểm tra và làm sạch dữ liệu đầu vào
$id = mysqli_real_escape_string($conn, $id);

// Câu lệnh SQL để xóa dữ liệu
$query = "DELETE FROM `sanphammoi` WHERE `id` = $id";
$data = mysqli_query($conn, $query);

if ($data) {
    $arr = [
        'success' => true,
        'message' => "Xóa thành công",
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "Xóa không thành công",
    ];
}

// In ra kết quả dưới dạng JSON
echo json_encode($arr);
?>
