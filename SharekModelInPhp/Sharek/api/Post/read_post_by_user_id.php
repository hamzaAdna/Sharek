<?php
if ($_SERVER['REQUEST_METHOD'] === 'GET') { 
  // Headers
  header('Access-Control-Allow-Origin: *');
  header('Content-Type: application/json');

  include_once '../../config/Database.php';
  include_once '../../model/Post.php';
  include_once '../../config/Constants.php';
  // Instantiate DB & connect
  $database = new Database();
  $db = $database->connect();

  // Instantiate blog post object
  $post = new Post($db);
   
  $post->user_id=$_GET['userId'];
  // Blog post query
  $result = $post->read_post_by_user_id();
  // Get row count

  $num = $result->rowCount();
  $posts_arr = array();
  $posts_arr['data'] = array();
  $posts_arr['status']="";
  // Check if any posts
  if($num > 0) {
    // Post array
   
    while($row = $result->fetch(PDO::FETCH_ASSOC)) {
      extract($row);
      $post_item = array(
         'postId'=> $POST_ID,
         'note'=> $NOTE,
         'fromPlace'=> $from_place,
         'toPlace'=> $to_place,
         'avaliableSeat'=> $AVALIABLE_SEAT ,
         'price'=> $PRICE,
         'createdDate'=> $CREATED_DATE,
         'departureDate'=> $DEPARTURE_DATE,
      
      
      );

      // Push to "data"
      array_push($posts_arr['data'], $post_item);
     
      // array_push($posts_arr['data'], $post_item);
    }
    $posts_arr[Constants::$nameOfstateMessge]=Constants::$successOfstateMessge;
  
    // Turn to JSON & outpu    

  } 
  else {
    // No Posts
    $posts_arr[Constants::$nameOfstateMessge]=Constants::$errorOfstateMessge;
  }

  echo json_encode($posts_arr,JSON_UNESCAPED_UNICODE);
 } else {echo "not found";}

 ?> 