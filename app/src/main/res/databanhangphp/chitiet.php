<?php
include "connect.php";

$page = $_POST['page'];
$total = 5; // số lượng sản phẩm trên một trang
$pos = ($page-1)*$total; // vị trí bắt đầu của sản phẩm

$loai = $_POST['loai'];

// Truy vấn dữ liệu từ bảng 'sanphammoi'
$query = "SELECT * FROM `sanphammoi` WHERE `loai` = '$loai' LIMIT $pos, $total";
$data = mysqli_query($conn, $query);

// Tạo một mảng để lưu kết quả
$result = array();

// Lặp qua các hàng dữ liệu và thêm vào mảng kết quả
while ($row = mysqli_fetch_assoc($data)) {
    $result[] = ($row);
}

// Kiểm tra nếu kết quả không rỗng
if (!empty($result)) {
    $arr = [
        'success' => true,
        'message' => "thành công",
        'result' => $result
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "không thành công",
        'result' => $result
    ];
}

// In ra kết quả dưới dạng JSON
print_r(json_encode($arr));
?>
