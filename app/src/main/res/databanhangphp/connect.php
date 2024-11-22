<?php
$host = "localhost";
$user = "root";
$pass = "";
$database = "dataonline";

// Kết nối với MySQLi
$conn = mysqli_connect($host, $user, $pass, $database);

// Kiểm tra kết nối
if (!$conn) {
    die("Không thể kết nối đến MySQL: " . mysqli_connect_error());
}

// Thiết lập bộ ký tự cho kết nối
mysqli_set_charset($conn, "utf8");
?>
