package ec.com.codigobarra.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import ec.com.codigobarra.R;
import ec.com.codigobarra.utils.SharedPreferencesManager;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.topicGlobal));
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(">>>>>", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.d(">>>>>>", token);
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String usuario = SharedPreferencesManager.getValorEsperado(MainActivity.this, getString(R.string.nombre_preferemcia), "usuario");
                Intent intent;
                if(usuario == null || usuario.length() < 1){
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(MainActivity.this, MenuActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}