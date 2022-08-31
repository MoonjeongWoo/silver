<?php 
    $con = mysqli_connect("localhost", "wlsdn3411", "dkssud412!", "wlsdn3411");
    mysqli_query($con,'SET NAMES utf8');
    
    $userName = $_POST["userName"];
    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    $userEmailAdr = $_POST["userEmailAdr"];
    $userPhoneNumber = $_POST["userPhoneNumber"];
    $userRegion = $_POST["userRegion"];
    $userSex = $_POST["userSex"];
    
    $statement = mysqli_prepare($con, "INSERT INTO AppUser VALUES (?,?,?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "sssssss", $userName, $userID, $userPassword, $userEmailAdr, $userPhoneNumber, $userRegion, $userSex);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;
    
    echo json_encode($response);
    

?>