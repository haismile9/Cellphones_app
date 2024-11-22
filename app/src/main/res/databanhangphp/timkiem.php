<?php
include "connect.php";

$search = $_POST['search'];
if (empty($search)) {
        $arr = [
        'success' => false,
        'message' => "Không thành công",
    ];
}else{


// Truy vấn dữ liệu từ bảng với cột tensp
$query = "SELECT * FROM `sanphammoi` WHERE `tensp` LIKE '%" . $search . "%'";
$data = mysqli_query($conn, $query);

// Tạo một mảng để lưu kết quả
$result = array();

// Lặp qua các hàng dữ liệu và thêm vào mảng kết quả
while ($row = mysqli_fetch_assoc($data)) {
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
}

// In ra kết quả dưới dạng JSON
echo json_encode($arr);
?>
