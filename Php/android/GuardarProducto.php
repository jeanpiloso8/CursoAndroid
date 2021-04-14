<?php
    try{

        require_once __DIR__ . '/Database.php';

    $consulta = "insert into tblProducto (descripcionProducto,fechaCompra,cantidaEstrrella,vendedor) 
                VALUES (:descripcionProducto,
                        :fechaCompra,
                        :cantidaEstrrella,
                        :vendedor) ";
    
        //Preparar sentencia
        $comando = Database::getInstance()->getDb()->prepare($consulta);
        $comando->bindParam(":descripcionProducto", $_GET['descripcionProducto']);
        $comando->bindParam(":fechaCompra", $_GET["fechaCompra"]);
        $comando->bindParam(":cantidaEstrrella", $_GET["cantidaEstrrella"]);
        $comando->bindParam(":vendedor", $_GET["vendedor"]);
        //Enviar por parámetro el usuario
        if ($comando->execute()) {
            $arrayRespuesta = array('status'     => 200,
                                'mensaje'    => "Insert correcto!!!!!");
          } else {
            echo "Unable to create record";
          }
        
        echo json_encode($arrayRespuesta);
    } catch (PDOException $e) {
        // Aquí puedes clasificar el error dependiendo de la excepción
        // para presentarlo en la respuesta Json
        return -1;
        print_r($e->getMessage());
        echo $e->getMessage();
    } catch(Exception $ex) {
        echo $ex->getMessage();
    }
?>