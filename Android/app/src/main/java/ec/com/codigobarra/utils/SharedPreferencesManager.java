package ec.com.codigobarra.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Clase que servira para almacenar valores constantes en el dispositivo
 *
 * @author Walther Joao Gaibor C <mailto:wgaibor@telconet.ec>
 * @version 1.0 - 30/11/2016
 */
public class SharedPreferencesManager {
    private static final String APP_SETTINGS = "APP_SETTINGS";
    public static final String GCM_IDENTIFIER="GCM_SERIAL_ID";


    // properties
    private static final String SOME_STRING_VALUE = "SOME_STRING_VALUE";
    // other properties...

    /**
     * Constructor
     *
     * @author Walther Joao Gaibor C <mailto:wgaibor@telconet.ec>
     * @version 1.0 - 30/11/2016
     */
    private SharedPreferencesManager() {}

    /**
     * Constructor que retorna la instancia de una preferencia
     *
     * @param context
     * @param nomPreferencia
     * @return
     *
     * @author Walther Joao Gaibor C <mailto:wgaibor@telconet.ec>
     * @version 1.0 - 30/11/2016
     */
    private static SharedPreferences getSharedPreferences(Context context, String nomPreferencia) {
        return context.getSharedPreferences(nomPreferencia, Context.MODE_PRIVATE);
    }

    /**
     * Clase que obtiene el valor string de una preferencia almacenada en el dispositivo
     *
     * @param <@link Context/> context        : Context de la aplicacion
     * @param <@link String/> nomPreferencia  : Nombre de la preferencia
     * @param <@link String/> valueRecuperar  : Valor que se pretendera a recuperar
     * @return String
     *
     * @author Walther Joao Gaibor C <mailto:wgaibor@telconet.ec>
     * @version 1.0 - 30/11/2016
     */
    public static String getValorEsperado(Context context, String nomPreferencia, String valueRecuperar) {
        return getSharedPreferences(context,nomPreferencia).getString(valueRecuperar, null);
    }

    /**
     * Clase que obtiene el valor int de una preferencia almacenada en el dispositivo
     *
     * @param <@link Context/> context        : Context de la aplicacion
     * @param <@link String/> nomPreferencia  : Nombre de la preferencia
     * @param <@link String/> valueRecuperar  : Valor que se pretendera a recuperar
     * @return Int
     *
     * @author Walther Joao Gaibor C <mailto:wgaibor@telconet.ec>
     * @version 1.0 - 30/11/2016
     *
     * @author Walther Joao Gaibor C <mailto:wgaibor@telconet.ec>
     * @version 1.1 - 18/04/2017 - Valor defecto 0.
     */
    public static int getValorEsperadoInt(Context context, String nomPreferencia, String valueRecuperar) {
        return getSharedPreferences(context,nomPreferencia).getInt(valueRecuperar, 0);
    }

    /**
     * Clase que obtiene el valor Long de una preferencia almacenada en el dispositivo
     *
     * @param <@link Context/> context        : Context de la aplicacion
     * @param <@link String/> nomPreferencia  : Nombre de la preferencia
     * @param <@link String/> valueRecuperar  : Valor que se pretendera a recuperar
     * @return Long
     *
     * @author Walther Joao Gaibor C <mailto:wgaibor@telconet.ec>
     * @version 1.0 - 30/11/2016
     *
     * @author Walther Joao Gaibor C <mailto:wgaibor@telconet.ec>
     * @version 1.1 - 18/04/2017 - Valor defecto 0.
     */
    public static long getValorEsperadoLong(Context context, String nomPreferencia, String valueRecuperar) {
        return getSharedPreferences(context,nomPreferencia).getLong(valueRecuperar, 0);
    }

    /**
     * Clase que obtiene el valor boolean de una preferencia almacenada en el dispositivo
     *
     * @param <@link Context/> context        : Context de la aplicacion
     * @param <@link String/> nomPreferencia  : Nombre de la preferencia
     * @param <@link String/> valueRecuperar  : Valor que se pretendera a recuperar
     * @return Boolean
     *
     * @author Walther Joao Gaibor C <mailto:wgaibor@telconet.ec>
     * @version 1.0 - 30/11/2016
     */
    public static boolean getValorEsperadoBoolean(Context context, String nomPreferencia, String valueRecuperar) {
        return getSharedPreferences(context,nomPreferencia).getBoolean(valueRecuperar, false);
    }

    /**
     * Clase que obtiene el valor array de una preferencia almacenada en el dispositivo
     *
     * @param <@link Context/> context        : Context de la aplicacion
     * @param <@link String/> nomPreferencia  : Nombre de la preferencia
     * @param <@link String/> valueRecuperar  : Valor que se pretendera a recuperar
     * @return String[]
     *
     * @author Walther Joao Gaibor C <mailto:wgaibor@telconet.ec>
     * @version 1.0 - 30/11/2016
     */
    public static String[] getValorArray(Context context, String nomPreferencia, String valueRecuperar) {
        Set<String> stringHashSet = getSharedPreferences(context,nomPreferencia).getStringSet(valueRecuperar, null);
        String[] dias = new String[7];
        stringHashSet.toArray(dias);
        return dias;
    }

    /**
     * Clase que setea un valor String en la preferencia del dispositivo
     *
     * @param <@link Context/> context        : Context de la aplicacion
     * @param <@link String/> nomPreferencia  : Nombre de la preferencia
     * @param <@link String/> newValue        : Clave
     * @param <@link String/> newStringValue  : Valor
     * @return void
     *
     * @author Walther Joao Gaibor C <mailto:wgaibor@telconet.ec>
     * @version 1.0 - 30/11/2016
     */
    public static void setValor(Context context, String nomPreferencia, String newStringValue, String newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context,nomPreferencia).edit();
        editor.putString(newStringValue , newValue);
        editor.commit();
    }

    /**
     * Clase que setea un valor String[] en la preferencia del dispositivo
     *
     * @param <@link Context/> context        : Context de la aplicacion
     * @param <@link String/> nomPreferencia  : Nombre de la preferencia
     * @param <@link String/> newValue        : Clave
     * @param <@link String/> newStringValue  : Valor
     * @return void
     *
     * @author Walther Joao Gaibor C <mailto:wgaibor@telconet.ec>
     * @version 1.0 - 30/11/2016
     */
    public static void setValorArray(Context context, String nomPreferencia, String[] newValue, String newStringValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context,nomPreferencia).edit();
        for(int i=0;i<newValue.length;i++){
            editor.putString(newStringValue+i, newValue[i]);
        }
        editor.commit();
    }

    /**
     * Clase que setea un valor Int en la preferencia del dispositivo
     *
     * @param <@link Context/> context        : Context de la aplicacion
     * @param <@link String/> nomPreferencia  : Nombre de la preferencia
     * @param <@link String/> newValue        : Clave
     * @param <@link String/> newStringValue  : Valor
     * @return void
     *
     * @author Walther Joao Gaibor C <mailto:wgaibor@telconet.ec>
     * @version 1.0 - 30/11/2016
     */
    public static void setValorInt(Context context, String nomPreferencia, int newValue, String newStringValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context,nomPreferencia).edit();
        editor.putInt(newStringValue , newValue);
        editor.commit();
    }

    /**
     * Clase que setea un valor Long en la preferencia del dispositivo
     *
     * @param <@link Context/> context        : Context de la aplicacion
     * @param <@link String/> nomPreferencia  : Nombre de la preferencia
     * @param <@link String/> newValue        : Clave
     * @param <@link String/> newStringValue  : Valor
     * @return void
     *
     * @author Walther Joao Gaibor C <mailto:wgaibor@telconet.ec>
     * @version 1.0 - 30/11/2016
     */
    public static void setValorLong(Context context, String nomPreferencia, long newValue, String newStringValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context,nomPreferencia).edit();
        editor.putLong(newStringValue , newValue);
        editor.commit();
    }

    /**
     * Clase que setea un valor Boolean en la preferencia del dispositivo
     *
     * @param <@link Context/> context        : Context de la aplicacion
     * @param <@link String/> nomPreferencia  : Nombre de la preferencia
     * @param <@link String/> newValue        : Clave
     * @param <@link String/> newStringValue  : Valor
     * @return void
     *
     * @author Walther Joao Gaibor C <mailto:wgaibor@telconet.ec>
     * @version 1.0 - 30/11/2016
     */
    public static void setValorBoolean(Context context, String nomPreferencia, boolean newValue, String newStringValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context,nomPreferencia).edit();
        editor.putBoolean(newStringValue , newValue);
        editor.commit();
    }

    /**
     * Clase que elimina un valor de la preferencia del dispositivo
     *
     * @param <@link Context/> context        : Context de la aplicacion
     * @param <@link String/> nomPreferencia  : Nombre de la preferencia
     * @param <@link String/> newStringValue  : Clave a eliminar
     * @return void
     *
     * @author Walther Joao Gaibor C <mailto:wgaibor@telconet.ec>
     * @version 1.0 - 30/11/2016
     */
    public static void deleteValor(Context context, String nomPreferencia, String newStringValue){
        final SharedPreferences.Editor editor = getSharedPreferences(context,nomPreferencia).edit();
        editor.remove(newStringValue);
        editor.commit();
    }

    /**
     * Limpia las preferencia de la aplicación
     *
     * @author Walther Joao Gaibor <mailto:wgaibor@telconet.ec>
     * @version 1.0 - 18/04/2017
     *
     * @param context
     * @param nomPreferencia
     */
    public static void deletePreference(Context context, String nomPreferencia) {
        final SharedPreferences.Editor editor = getSharedPreferences(context,nomPreferencia).edit();
        editor.clear().commit();
    }

    // other getters/setters
}