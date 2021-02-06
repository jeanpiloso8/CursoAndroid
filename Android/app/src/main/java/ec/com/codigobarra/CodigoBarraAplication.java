package ec.com.codigobarra;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CodigoBarraAplication extends Application {

    private static CodigoBarraAplication mInstance;
    private Retrofit restAdapter;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        restAdapter = retrofitCodigoBarra();
    }

    private Retrofit retrofitCodigoBarra() {
        Retrofit client = new Retrofit.Builder()
                                    .baseUrl(getString(R.string.server_url))
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
        return client;
    }

    public static CodigoBarraAplication getmInstance() {
        return mInstance;
    }

    public Retrofit getRestAdapter() {
        return restAdapter;
    }
}
