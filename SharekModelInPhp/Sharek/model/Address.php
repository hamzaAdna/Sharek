<?php
    class Address{
      private  $conn;
      private  $table="address";
      public  $place;
      public  $address_id;
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
        
  public function create()
  {
    // Create Query
    $query = 'INSERT INTO ' .
      $this->table . '
        SET
          place = :place
          ';

    // Prepare Statement
    $stmt = $this->conn->prepare($query);

    // Clean data
    $this->place = htmlspecialchars(strip_tags($this->place));

    // Bind data
    $stmt->bindParam(':place', $this->place);

    // Execute query
    try {
      if ($stmt->execute()) {
        return true;
      }
    } catch (Exception $e) {
      //printf("Error: $s.\n", $stmt->error);
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
          place = :place,
        
          WHERE
          address_id = :address_id';

    // Prepare Statement
    $stmt = $this->conn->prepare($query);


    // Clean data
    $this->place = htmlspecialchars(strip_tags($this->place));
    $this->address_id = htmlspecialchars(strip_tags($this->address_id));

    // Bind data
    $stmt->bindParam(':place', $this->place);

    $stmt->bindParam(':address_id', $this->address_id);
  
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


  public function delete()
  {
    // Create query
    $query = 'DELETE FROM ' . $this->table . ' WHERE address_id = :address_id';

    // Prepare Statement
    $stmt = $this->conn->prepare($query);

    // clean data
    $this->address_id = htmlspecialchars(strip_tags($this->address_id));

    // Bind Data
    $stmt->bindParam(':address_id', $this->address_id);

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

