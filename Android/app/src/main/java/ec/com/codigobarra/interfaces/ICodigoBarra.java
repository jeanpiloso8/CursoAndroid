package ec.com.codigobarra.interfaces;

import java.util.List;

import ec.com.codigobarra.response.ParametroResponse;
import ec.com.codigobarra.response.UsuarioResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ICodigoBarra {

    @GET("android/Login.php")
    Call<UsuarioResponse> doLogin(@Query("usuario") String usuario);

    @GET("android/ParametroProducto.php")
    Call<ParametroResponse> obtenerParametros();

    @GET("android/GuardarProducto.php")
    Call<UsuarioResponse> ingresarProducto(@Query("descripcionProducto") String descripcionProducto,
                                           @Query("fechaCompra") String fechaCompra,
                                           @Query("cantidaEstrrella") int cantidadEstrella,
                                           @Query("vendedor") String vendedor);
}
