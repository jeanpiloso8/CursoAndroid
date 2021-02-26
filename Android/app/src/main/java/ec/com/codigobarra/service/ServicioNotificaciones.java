package ec.com.codigobarra.service;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import ec.com.codigobarra.activity.MainActivity;
import ec.com.codigobarra.utils.NotificationUtils;

public class ServicioNotificaciones extends FirebaseMessagingService {

    String TAG = getClass().getSimpleName();
    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d(TAG, "From   " + remoteMessage.getFrom());
        if (remoteMessage == null) {
            return;
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Notiication Body:    "+remoteMessage.getNotification().getBody());
            tratamientoMensaje(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if(remoteMessage.getData().size() > 0){
            Log.d(TAG, "Datos recibidos:   "+remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                procesamientoDatos(json);
            } catch (JSONException e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(">>>>>>", "Token Generado   "+token);

    }

    private void procesamientoDatos(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());
        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);

            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
            resultIntent.putExtra("title",title);
            resultIntent.putExtra("message",message);

            // check for image attachment
            if (TextUtils.isEmpty(imageUrl)) {
                showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
            } else {
                // image is present, show notification with image
                showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timestamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.mostrarNotificacion(title, message, timestamp, intent, imageUrl);
    }

    private void showNotificationMessage(Context context, String title, String message, String timestamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationUtils.mostrarNotificacion(title, message, timestamp, intent);
    }

    private void tratamientoMensaje(String body) {
        if(!NotificationUtils.isAppIsInBackground(getApplicationContext())){
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        } else {
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }
    }
}