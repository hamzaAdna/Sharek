<?php
if ($_SERVER['REQUEST_METHOD'] === 'POST') { 
  // Headers
  header('Access-Control-Allow-Origin: *');
  header('Content-Type: application/json');
  header('Access-Control-Allow-Methods: POST');
  header('Access-Control-Allow-Headers: Access-Control-Allow-Headers,Content-Type,Access-Control-Allow-Methods, Authorization, X-Requested-With');

  include_once '../../config/Database.php';
  include_once '../../model/Post.php';
  include_once '../../config/Constants.php';
  // Instantiate DB & connect
  $database = new Database();
  $db = $database->connect();

  // Instantiate blog post object
  $post = new Post($db);
  

  // Get raw posted data
  $data = json_decode(file_get_contents("php://input"));//http body
  $post->note = $data->note;
  $post->from=$data->from;
  $post->to=$data->to;
  $post->avaliable_seat=$data->avaliableSeat;
  $post->price=$data->price;
  $post->departure_date=$data->departureDate;
  $post->user_id=$data->userId;


//POST_ID 	NOTE 	FROM 	TO 	AVALIABLE_SEAT 	PRICE 	CREATED_DATE 	DEPARTURE_DATE 	USER_ID//
 
  if($post->create()) {
    echo json_encode(
      array(Constants::$nameOfstateMessge=>Constants::$successOfstateMessge)
    );
  } else {
    echo json_encode(
      array(Constants::$nameOfstateMessge=>Constants::$errorOfstateMessge)
    );
  }
  }
 else{ echo "not found"; }
