<?php
    try{

        require_once __DIR__ . '/Database.php';

    $consulta = "select * from tblParametroProducto ";
    
        //Preparar sentencia
        $comando = Database::getInstance()->getDb()->prepare($consulta);
        //Enviar por parámetro el usuario
        $comando->execute();
        // Capturar la primera fila del resultado
        $row = $comando->fetchAll(PDO::FETCH_ASSOC);
        if(empty($row))
        {
            $arrayRespuesta = array('status'     => 500,
                                    'mensaje'    => "No existe en la base de datos");
        } else {
            /*$arrayRespuesta = array('status'     => 200,
                                    'mensaje'    => "Login correcto!!!!!");*/
            $arrayRespuesta = array('paramIniciales' => $row,
                                    'usuarioVendedor' => 'epiloso');
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