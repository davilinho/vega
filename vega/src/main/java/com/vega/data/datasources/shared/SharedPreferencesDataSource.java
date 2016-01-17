package com.vega.data.datasources.shared;

import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Component created on 30/11/2015.
 *
 * @author dmartin
 */
public class SharedPreferencesDataSource {

    private SharedPreferences preferences = null;
    private Gson gson = null;

    public SharedPreferencesDataSource(SharedPreferences preferences, Gson gson) {
        this.preferences = preferences;
        this.gson = gson;
    }

    /**
     * Se establece un objeto en la store, a partir de una clave.
     * @param key Clave del objeto a establecer.
     * @param objectToPut Objeto a establecer.
     */
    public void put(String key, Object objectToPut) {

        synchronized (this) {
            if (objectToPut == null) {
                preferences.edit().remove(key).apply();
            } else {
                preferences.edit().putString(key, gson.toJson(objectToPut)).apply();
            }
        }

    }

    /**
     * Obtiene un objeto de la store, a partir de una clave.
     * @param key Clave del objeto a recuperar.
     * @param clazz Clase a la que pertenece el objeto a recuperar.
     * @return Objeto recuperado.
     */
    public Object get(String key, Class<?> clazz) {

        synchronized (this) {
            if (preferences.contains(key)) {
                return gson.fromJson(preferences.getString(key, ""), clazz);
            }
        }

        return null;

    }

    /**
     * Elimina un valor de la store, a partir de su clave.
     * @param key Clave del dato.
     */
    public void removeValue(String key) {

        synchronized (this) {
            preferences.edit().remove(key).apply();
        }

    }

    /**
     * Método que limpia la store.
     */
    public void clear() {

        synchronized (this) {
            preferences.edit().clear().apply();
        }

    }

}
