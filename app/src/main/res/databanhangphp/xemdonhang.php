<?php
include "connect.php";

$iduser = $_POST['iduser'];

// Truy vấn dữ liệu từ bảng 
$query = 'SELECT * FROM `donhang` WHERE `iduser` = '.$iduser.' ORDER BY id DESC ';
$data = mysqli_query($conn, $query);

// Tạo một mảng để lưu kết quả
$result = array();

// Lặp qua các hàng dữ liệu và thêm vào mảng kết quả
while ($row = mysqli_fetch_assoc($data)) {
    $truyvan = "SELECT * FROM `chitietdonhang` INNER JOIN sanphammoi ON chitietdonhang.idsp =sanphammoi.id WHERE chitietdonhang.iddonhang = ".$row['id'];
    $data1 = mysqli_query($conn, $truyvan);
    $item = array();
    while ($row1 = mysqli_fetch_assoc($data1)) {
        $item[] = $row1;
    }
    $row['item'] = $item;
    $result[] = $row;
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
