<?php
class User
{
  private  $conn;
  private $table = "user";
  public  $user_id;
  public $name;
  public $email;
  public $password;
  public  $phone;
  public  $role;
  public function __construct($db)
  {
    $this->conn = $db;
  }

  public function read()
  {
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
          name = :full_name,
          email = :email,
          password = :password,
          phone = :phone,
          role=:role_name
          ';

    // Prepare Statement
    $stmt = $this->conn->prepare($query);

    // Clean data
    $this->name = htmlspecialchars(strip_tags($this->name));
    $this->email = htmlspecialchars(strip_tags($this->email));
    $this->password = htmlspecialchars(strip_tags($this->password));
    $this->phone = htmlspecialchars(strip_tags($this->phone));
    $this->role = htmlspecialchars(strip_tags($this->role));
    // Bind data
    $stmt->bindParam(':full_name', $this->name);
    $stmt->bindParam(':email', $this->email);
    $stmt->bindParam(':password', $this->password);
    $stmt->bindParam(':phone', $this->phone);
    $stmt->bindParam(':role_name', $this->role);
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
          name = :full_name,
          email = :email,
          password = :password,
          phone = :phone,
          role=:role_name
          WHERE
          user_id = :id';

    // Prepare Statement
    $stmt = $this->conn->prepare($query);


    // Clean data
    $this->name = htmlspecialchars(strip_tags($this->name));
    $this->email = htmlspecialchars(strip_tags($this->email));
    $this->password = htmlspecialchars(strip_tags($this->password));
    $this->phone = htmlspecialchars(strip_tags($this->phone));
    $this->user_id = htmlspecialchars(strip_tags($this->user_id));
    $this->role = htmlspecialchars(strip_tags($this->role));

    // Bind data
    $stmt->bindParam(':full_name', $this->name);
    $stmt->bindParam(':email', $this->email);
    $stmt->bindParam(':password', $this->password);
    $stmt->bindParam(':phone', $this->phone);
    $stmt->bindParam(':id', $this->user_id);
    $stmt->bindParam(':role_name', $this->role);
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
    $query = 'DELETE FROM ' . $this->table . ' WHERE user_id = :id';

    // Prepare Statement
    $stmt = $this->conn->prepare($query);

    // clean data
    $this->user_id = htmlspecialchars(strip_tags($this->user_id));

    // Bind Data
    $stmt->bindParam(':id', $this->user_id);

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
  public function read_by_email_and_password()
  {
    // Create query
    $query = 'SELECT user_id,name,email,role FROM ' . $this->table . ' WHERE email =:email && password=:password' ;
    // Prepare statement
    $stmt = $this->conn->prepare($query);
    $this->email = htmlspecialchars(strip_tags($this->email));
    $this->password = htmlspecialchars(strip_tags($this->password));

    // Bind Data
    $stmt->bindParam(':email', $this->email);
    $stmt->bindParam(':password', $this->password);
    // Execute query
    $stmt->execute();
    return $stmt;
  }

  public function read_user_by_user_id()
  {
    // Create query
    $query = 'SELECT user.USER_ID,user.NAME,user.PHONE,user.EMAIL FROM ' . $this->table . ' WHERE user.USER_ID =:user_id' ;
    // Prepare statement
    $stmt = $this->conn->prepare($query);
    $this->user_id = htmlspecialchars(strip_tags($this->user_id));
    // Bind Data
    $stmt->bindParam(':user_id', $this->user_id);
    // Execute query
    $stmt->execute();
    return $stmt;
  }

}

?>
