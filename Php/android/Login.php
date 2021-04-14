<?php
    try{
    require 'Database.php';

    $consulta = "select * from tblusuario where usuario = ? ";
    
        //Preparar sentencia
        $comando = Database::getInstance()->getDb()->prepare($consulta);
        //Enviar por parámetro el usuario
        $comando->execute(array($_GET["usuario"]));
        // Capturar la primera fila del resultado
        $row = $comando->fetch(PDO::FETCH_ASSOC);
        if(empty($row['usuario']))
        {
            $arrayRespuesta = array('status'     => 500,
                                    'mensaje'    => "No existe en la base de datos");
        } else {
            $arrayRespuesta = array('status'     => 200,
                                    'mensaje'    => "Login correcto!!!!!");
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