<?php
include "connect.php";

$tensp = $_POST['tensp'];
$gia = $_POST['gia'];
$hinhanh = $_POST['hinhanh'];
$mota = $_POST['mota'];
$loai = $_POST['loai'];
$id = $_POST['id'];

// Escape các giá trị để tránh SQL Injection
$tensp = mysqli_real_escape_string($conn, $tensp);
$gia = mysqli_real_escape_string($conn, $gia);
$hinhanh = mysqli_real_escape_string($conn, $hinhanh);
$mota = mysqli_real_escape_string($conn, $mota);
$loai = mysqli_real_escape_string($conn, $loai);
$id = mysqli_real_escape_string($conn, $id);

// Câu lệnh SQL để cập nhật dữ liệu
$query = "UPDATE `sanphammoi` 
          SET `tensp`='$tensp', `giasp`='$gia', `hinhanh`='$hinhanh', `mota`='$mota', `loai`='$loai' 
          WHERE `id`='$id'";

$data = mysqli_query($conn, $query);

if ($data == true) {
    $arr = [
        'success' => true,
        'message' => "Thành công",
    ];
} else {
    $arr = [
        'success' => false,
        'message' => "Không thành công: " . mysqli_error($conn),
    ];
}

// In ra kết quả dưới dạng JSON
echo json_encode($arr);
?>
