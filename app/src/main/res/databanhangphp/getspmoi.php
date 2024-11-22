<?php
include "connect.php";

// Truy vấn dữ liệu từ bảng 'sanpham'
$query = "SELECT * FROM `sanphammoi` ORDER BY id DESC";
$data = mysqli_query($conn, $query);
// Tạo một mảng để lưu kết quả
$result = array();
// Kiểm tra lỗi truy vấn
//if (!$data) {
  //  die("Lỗi truy vấn: " . mysqli_error($conn));
//}



// Lặp qua các hàng dữ liệu và thêm vào mảng kết quả
while ($row = mysqli_fetch_assoc($data)) {
    $result[] = $row;
}

// Kiểm tra nếu kết quả không rỗng
if (!empty($result)) {
    $arr = [
        'success' => true,
        'message' => "thanh cong",
        'result' => $result
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "khong thanh cong",
        'result' => $result
    ];
}

// In ra kết quả dưới dạng JSON
print_r(json_encode($arr));
?>