package ec.com.codigobarra.interfaces;

import ec.com.codigobarra.response.UsuarioResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ICodigoBarra {

    @GET("Login.php")
    Call<UsuarioResponse> doLogin(@Query("usuario") String usuario);
}
