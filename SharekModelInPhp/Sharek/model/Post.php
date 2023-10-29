<?php
    class Post{
      private  $conn;
      private  $table="post";
       public $post_id;
       public $created_date;
       public $from;
       public $to;
       public $avaliable_seat;
       public $note;
       public $price;
       public $departure_date;
       public $user_id;
      
        public function __construct($db) {
            $this->conn = $db;
          }
          
        public function read() {
            // Create query
            $query = 'SELECT * FROM ' . $this->table;
            // Prepare statement
            $stmt = $this->conn->prepare($query);
            // Execute query
            $stmt->execute();
            return $stmt;
        }



        public function read_post_join_user_join_address() {
          // Create query
          $query = 'SELECT user.USER_ID,user.NAME,user.PHONE
          ,post.POST_ID,post.NOTE,ad2.PLACE "from_place",ad.PLACE 
          "to_place" ,post.AVALIABLE_SEAT,post.PRICE,post.CREATED_DATE,post.DEPARTURE_DATE
           FROM post JOIN address ad on post.TO_PLACE=ad.ADDRESS_ID JOIN address ad2
            on post.FROM_PLACE=ad2.ADDRESS_ID join user on post.USER_ID=user.USER_ID;';
          // Prepare statement
          $stmt = $this->conn->prepare($query);
          // Execute query
          $stmt->execute();
          return $stmt;
      }


      public function read_post_by_user_id() {
        // Create query
        $query = 'SELECT post.POST_ID,post.NOTE,ad2.PLACE "from_place",ad.PLACE 
        "to_place" ,post.AVALIABLE_SEAT,post.PRICE,post.CREATED_DATE,post.DEPARTURE_DATE
         FROM post JOIN address ad on post.TO_PLACE=ad.ADDRESS_ID JOIN address ad2
          on post.FROM_PLACE=ad2.ADDRESS_ID WHERE post.USER_ID=:user_id';
        // Prepare statement
        $stmt = $this->conn->prepare($query);
        $this->user_id = htmlspecialchars(strip_tags($this->user_id));
        // Bind data
        $stmt->bindParam(':user_id', $this->user_id);
        // Execute query
        $stmt->execute();
        return $stmt;
    }


        
  public function create()
  {
    // Create Query
    $query = 'INSERT INTO ' .
      $this->table . '
        SET
         
          departure_date =:departure_date,
          note =:note,
          avaliable_seat=:avaliable_seat,
          from_place=:from,
          to_place=:to,
          price=:price,
          user_id=:user_id
          ';

    // Prepare Statement
    $stmt = $this->conn->prepare($query);

    // Clean data
    $this->price = htmlspecialchars(strip_tags($this->price));
    $this->note = htmlspecialchars(strip_tags($this->note));
    $this->avaliable_seat = htmlspecialchars(strip_tags($this->avaliable_seat));
    $this->departure_date = htmlspecialchars(strip_tags($this->departure_date));
    
    $this->from = htmlspecialchars(strip_tags($this->from));
    $this->to = htmlspecialchars(strip_tags($this->to));
    $this->user_id = htmlspecialchars(strip_tags($this->user_id));
    // Bind data
    $stmt->bindParam(':user_id', $this->user_id);
    $stmt->bindParam(':avaliable_seat', $this->avaliable_seat);
    $stmt->bindParam(':departure_date', $this->departure_date);
  
    $stmt->bindParam(':from', $this->from);
    $stmt->bindParam(':to', $this->to);
    $stmt->bindParam(':price', $this->price);
    $stmt->bindParam(':note', $this->note);
    // Execute query
    try {
      if ($stmt->execute()) {
        return true;
      }
    } catch (Exception $e) {
      printf("Error: $e.\n", $stmt->error);
    }
    // Print error if something goes wrong
    return false;
  }

  public function update()
  {
    // Create Query
    $query = 'UPDATE ' .
      $this->table . '
          SET
         
          departure_date =:departure_date,
          note =:note,
          avaliable_seat=:avaliable_seat,
          from_place=:from,
          to_place=:to,
          price=:price
          WHERE
          POST_ID =:post_id;';

    // Prepare Statement
    $stmt = $this->conn->prepare($query);

  
    $this->price = htmlspecialchars(strip_tags($this->price));
    $this->note = htmlspecialchars(strip_tags($this->note));
    $this->avaliable_seat = htmlspecialchars(strip_tags($this->avaliable_seat));
    $this->departure_date = htmlspecialchars(strip_tags($this->departure_date));
    $this->from = htmlspecialchars(strip_tags($this->from));
    $this->to = htmlspecialchars(strip_tags($this->to));
    $this->post_id = htmlspecialchars(strip_tags($this->post_id));
    // Bind data
    
    $stmt->bindParam(':avaliable_seat', $this->avaliable_seat);
    $stmt->bindParam(':departure_date', $this->departure_date);
  
    $stmt->bindParam(':from', $this->from);
    $stmt->bindParam(':to', $this->to);
    $stmt->bindParam(':price', $this->price);
    $stmt->bindParam(':note', $this->note);
    $stmt->bindParam(':post_id', $this->post_id);
    // Execute query
    try {
      if ($stmt->execute()) {
        return true;
      }
    } catch (Exception $e) {

      printf("Error: $e.\n", $stmt->error);
    }

    return false;
  }


  public function delete()
  {
    // Create query
    $query = 'DELETE FROM ' . $this->table . ' WHERE post_id = :post_id';

    // Prepare Statement
    $stmt = $this->conn->prepare($query);

    // clean data
    $this->user_id = htmlspecialchars(strip_tags($this->user_id));

    // Bind Data
    $stmt->bindParam(':post_id', $this->post_id);

    // Execute query
    try {
      if ($stmt->execute()) {
        return true;
      }
    } catch (Exception $e) {

      //printf("Error: $s.\n", $stmt->error);
    }
    return false;
  }
        
    }


?>

