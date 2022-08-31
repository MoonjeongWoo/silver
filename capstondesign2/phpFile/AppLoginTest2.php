<?php
    $con = mysqli_connect("localhost", "wlsdn3411", "dkssud412!", "wlsdn3411");
    mysqli_query($con,'SET NAMES utf8');

    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];

    $statement = mysqli_prepare($con, "SELECT * FROM AppUser WHERE userID = ? AND userPassword = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID,$userPassword);
    mysqli_stmt_execute($statement);


    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userName, $userID, $userPassword, $userEmailAdr, $userPhoneNumber, $userRegion, $userSex);

    $response = array();
    $response["success"] = false;
 
    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["userName"] = $userName;
        $response["userID"] = $userID;
        $response["userPassword"] = $userPassword;
        $response["userEmailAdr"] = $userEmailAdr;
        $response["userPhoneNumber"] = $userPhoneNumber;
        $response["userRegion"] = $userRegion;
        $response["userSex"] = $userSex;        
    }

    echo json_encode($response);

?>
