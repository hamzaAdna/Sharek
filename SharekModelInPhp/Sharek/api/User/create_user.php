<?php
if ($_SERVER['REQUEST_METHOD'] === 'POST') { 
  // Headers
  header('Access-Control-Allow-Origin: *');
  header('Content-Type: application/json');
  header('Access-Control-Allow-Methods: POST');
  header('Access-Control-Allow-Headers: Access-Control-Allow-Headers,Content-Type,Access-Control-Allow-Methods, Authorization, X-Requested-With');

  include_once '../../config/Database.php';
  include_once '../../model/User.php';
  include_once '../../config/Constants.php';
  include_once '../../email/sendEmail.php';
  // Instantiate DB & connect
  $database = new Database();
  $db = $database->connect();

  // Instantiate blog post object
  $user = new User($db);
  
  // Get raw posted data
  $data = json_decode(file_get_contents("php://input"));//http body
  $user->name = $data->name;
  $user->phone=$data->phone;
  $user->password=$data->password;
  $user->email=$data->email;
  $user->role=$data->role;

 
  if($user->create()) {
    echo json_encode(
      array(Constants::$nameOfstateMessge=>Constants::$successOfstateMessge)
    );

    SendEmailRegister($user->email,$data->name);
  } else {
    echo json_encode(
      array(Constants::$nameOfstateMessge=>Constants::$errorOfstateMessge)
    );
  }
  }
 else{ echo "not found"; }
