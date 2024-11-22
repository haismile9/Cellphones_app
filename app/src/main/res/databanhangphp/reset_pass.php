<?php
include "connect.php";

if(isset($_GET['key']) && isset($_GET['reset'])) {
    $email = mysqli_real_escape_string($conn, $_GET['key']);
    $pass = mysqli_real_escape_string($conn, $_GET['reset']);

    // Truy vấn dữ liệu từ bảng user với MD5 mã hóa
    $query = "SELECT email, pass FROM user WHERE md5(email)='$email' AND md5(pass)='$pass'";
    print_r($query);
    $data = mysqli_query($conn, $query);

    if($data == true) {
        ?>
        <form method="post" action="submit_new.php">
            <input type="hidden" name="email" value="<?php echo htmlspecialchars($email); ?>">
            <p>Enter New password</p>
            <input type="password" name='password'>
            <input type="submit" name="submit_password">
        </form>
        <?php
    } else {
        echo "Invalid link or password has already been reset.";
    }
} else {
    echo "Invalid request.";
}
?>
