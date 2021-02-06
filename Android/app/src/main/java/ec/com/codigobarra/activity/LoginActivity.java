package ec.com.codigobarra.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import ec.com.codigobarra.CodigoBarraAplication;
import ec.com.codigobarra.R;
import ec.com.codigobarra.interfaces.ICodigoBarra;
import ec.com.codigobarra.response.UsuarioResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnIngresar;
    TextInputEditText edtUsuario;
    TextInputEditText edtPassword;
    private Call<UsuarioResponse> callLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        btnIngresar = findViewById(R.id.btn_ingresar);
        edtUsuario = findViewById(R.id.edtUsuario);
        edtPassword = findViewById(R.id.edtContrasena);
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarUsuario();
            }
        });
    }

    private void validarUsuario() {
        if(edtUsuario.getText().toString().length() < 1) {
            AlertDialog.Builder alertar = new AlertDialog.Builder(this);
            alertar.setTitle("Validación de formulario");
            alertar.setMessage("Ingrese por favor un usuario");
            alertar.setPositiveButton("Ok", null);
            alertar.show();
            return;
        }
        if(edtPassword.getText().toString().length() < 1){
            AlertDialog.Builder _alertar = new AlertDialog.Builder(this);
            _alertar.setTitle("Validación de formulario");
            _alertar.setMessage("Ingrese por favor una contraseña");
            _alertar.setPositiveButton("Ok", null);
            _alertar.show();
            return;
        }
        ICodigoBarra iCodigoBarra = CodigoBarraAplication.getmInstance().getRestAdapter().create(ICodigoBarra.class);
        callLogin = iCodigoBarra.doLogin(edtUsuario.getText().toString());
        callLogin.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                if(response.isSuccessful()){
                    UsuarioResponse usuarioResponse = response.body();
                    if(usuarioResponse.getStatus() == 200){
                        llamarALaOtraActividad();
                    } else {
                        existeError(usuarioResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {

            }
        });
    }

    private void existeError(UsuarioResponse usuarioResponse) {
        AlertDialog.Builder _alertar = new AlertDialog.Builder(this);
        _alertar.setTitle("ERROR");
        _alertar.setMessage(usuarioResponse.getMensaje());
        _alertar.setPositiveButton("Ok", null);
        _alertar.show();
    }

    private void llamarALaOtraActividad() {
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}