<?php
if ($_SERVER['REQUEST_METHOD'] === 'GET') { 
  // Headers
  header('Access-Control-Allow-Origin: *');
  header('Content-Type: application/json');

  include_once '../../config/Database.php';
  include_once '../../model/User.php';
  include_once '../../config/Constants.php';
  // Instantiate DB & connect
  $database = new Database();
  $db = $database->connect();

  // Instantiate blog post object
  $user = new User($db);

  // Blog post query
  $result = $user->read();
  // Get row count

  $num = $result->rowCount();
  $users_arr = array();
  $users_arr['data'] = array();
  $users_arr['status']="";
  // Check if any posts
  if($num > 0) {
    // Post array
   
    while($row = $result->fetch(PDO::FETCH_ASSOC)) {
      extract($row);
      $user_item = array(
        'userId'=>$USER_ID,
        'name' => $NAME,
        'password' => $PASSWORD,
        'phone' => $PHONE,
        'email' => $EMAIL,
        'role'=> $ROLE,
      
      );

      // Push to "data"
      array_push($users_arr['data'], $user_item);
     
      // array_push($posts_arr['data'], $post_item);
    }
    $users_arr[Constants::$nameOfstateMessge]=Constants::$successOfstateMessge;
  
    // Turn to JSON & outpu    

  } 
  else {
    // No Posts
    $users_arr[Constants::$nameOfstateMessge]=Constants::$errorOfstateMessge;
  }

  echo json_encode($users_arr,JSON_UNESCAPED_UNICODE);
 } else {echo "not found";}

 ?> 