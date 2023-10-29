<?php
if ($_SERVER['REQUEST_METHOD'] === 'GET') { 
  // Headers
  header('Access-Control-Allow-Origin: *');
  header('Content-Type: application/json');

  include_once '../../config/Database.php';
  include_once '../../model/Address.php';
  include_once '../../config/Constants.php';
  // Instantiate DB & connect
  $database = new Database();
  $db = $database->connect();

  // Instantiate blog post object
  $address = new address($db);

  // Blog post query
  $result = $address->read();
  // Get row count

  $num = $result->rowCount();
  $addresss_arr = array();
  $addresss_arr['data'] = array();
  $addresss_arr['status']="";
  // Check if any posts
  if($num > 0) {
    // Post array
   
    while($row = $result->fetch(PDO::FETCH_ASSOC)) {
      extract($row);
      $address_item = array(
        'addressId'=>$ADDRESS_ID,
        'place' => $PLACE,

      
      );

      // Push to "data"
      array_push($addresss_arr['data'], $address_item);
     
      // array_push($posts_arr['data'], $post_item);
    }
    $addresss_arr[Constants::$nameOfstateMessge]=Constants::$successOfstateMessge;
  
    // Turn to JSON & outpu    

  } 
  else {
    // No Posts
    $addresss_arr[Constants::$nameOfstateMessge]=Constants::$errorOfstateMessge;
  }

  echo json_encode($addresss_arr,JSON_UNESCAPED_UNICODE);
 } else {echo "not found";}

 ?> 