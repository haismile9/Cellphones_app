<?php
include "connect.php";

// Lấy dữ liệu từ POST request
$sdt = $_POST['sdt'];
$email = $_POST['email'];
$tien = $_POST['tongtien'];
$iduser = $_POST['iduser'];
$diachi = $_POST['diachi'];
$soluong = $_POST['soluong'];
$chitiet = $_POST['chitiet'];

// Truy vấn dữ liệu để chèn vào bảng 'donhang'
$query = "INSERT INTO `donhang`(`iduser`, `diachi`, `sodienthoai`, `email`, `soluong`, `tongtien`) VALUES ('$iduser', '$diachi', '$sdt', '$email', '$soluong', '$tien')";

// Thực hiện truy vấn
$data = mysqli_query($conn, $query);

// Tạo một mảng để lưu kết quả
$result = array();

if ($data) {
    // Lấy ID của đơn hàng vừa chèn
    $iddonghang = mysqli_insert_id($conn);

    if ($iddonghang) {
        // Xử lý chi tiết đơn hàng
        $chitiet = json_decode($chitiet, true);
        foreach ($chitiet as $item) {
            $idsp = $item['idsp'];
            $soluong = $item['soluong'];
            $gia = $item['giasp'];

            $truyvan = "INSERT INTO `chitietdonhang`(`iddonhang`, `idsp`, `soluong`, `gia`) VALUES ($iddonghang, $idsp, $soluong, $gia)";
            $data = mysqli_query($conn, $truyvan);

            if (!$data) {
                $result = [
                    'success' => false,
                    'message' => "Thêm chi tiết đơn hàng không thành công",
                    'error' => mysqli_error($conn)
                ];
                break; // Dừng vòng lặp nếu có lỗi
            }
        }

        if (empty($result)) {
            $result = [
                'success' => true,
                'message' => "Thêm đơn hàng và chi tiết thành công"
            ];
        }
    } else {
        $result = [
            'success' => false,
            'message' => "Không thể lấy ID đơn hàng",
            'error' => mysqli_error($conn)
        ];
    }
} else {
    $result = [
        'success' => false,
        'message' => "Thêm đơn hàng không thành công",
        'error' => mysqli_error($conn)
    ];
}

// In ra kết quả dưới dạng JSON
echo json_encode($result);

// Đóng kết nối
mysqli_close($conn);
?>
