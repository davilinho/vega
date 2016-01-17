package com.vega.app.net;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.vega.app.constants.AppConstants;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * Clase que se encarga de preparar todos los parámetros necesarios para preparar una llamada del tipo OKHttpClient.
 *
 * @author dcaballero
 */
public final class OkHttpClientFactory {

    /**
     * Objeto que gestiona el KeyStore.
     */
    private static KeyManagerFactory keyManagerFactory;

    /**
     * Objeto que gestiona el KrustStore.
     */
    private static TrustManagerFactory trustManagerFactory;

    /**
     * Objeto que gestiona el contexto de SSL.
     */
    private static SSLContext sslContext;

    /**
     * Constructor por defecto.
     */
    private OkHttpClientFactory() {
    }

    /**
     * Método que prepara toda llamada segura mediante cliente OKHttpClient, hacia nuestros servidores.
     *
     * @param context Contexto de la aplicación.
     * @return Cliente securizado tipo OKHttpClient.
     * @throws NoSuchAlgorithmException  Excepción en caso de error tipo NoSuchAlgorithmException.
     * @throws KeyStoreException         Excepción en caso de error tipo KeyStoreException.
     * @throws IOException               Excepción en caso de error tipo IOException.
     * @throws CertificateException      Excepción en caso de error tipo CertificateException.
     * @throws UnrecoverableKeyException Excepción en caso de error tipo UnrecoverableKeyException.
     * @throws KeyManagementException    Excepción en caso de error tipo KeyManagementException.
     */
    public static OkHttpClient createSecuredClient(Context context) throws NoSuchAlgorithmException,
            KeyStoreException, IOException, CertificateException, UnrecoverableKeyException, KeyManagementException {

        OkHttpClient client = new OkHttpClient();

        prepareTrustStore(context);
        prepareKeyStore(context);
        prepareSslContext();

        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        client.setCookieHandler(cookieManager);
        client.setSslSocketFactory(sslContext.getSocketFactory());
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                /**
                 * TODO - Interceptor implementation
                 *
                 *      - Aplicar gestión token!!!!
                 *
                 */

                Request request = chain.request();
                Response response = null;

                long t1 = System.nanoTime();

                Log.i("RETROFIT INTERCEPTOR", String.format("Sending request %s on %s%n%s",
                        request.url(), chain.connection(), request.headers()));

                try {

                    response = chain.proceed(request);

                    long t2 = System.nanoTime();

                    Log.i("RETROFIT INTERCEPTOR", String.format("Received response for %s in %.1fms%n%s",
                            response.request().url(), (t2 - t1) / 1e6d, response.headers()));

                } catch (IOException e) {

                    Log.e("RETROFIT INTERCEPTOR", "ERROR: No network connection.");

                } finally {

                    return response;

                }

            }
        });

        return client;

    }

    /**
     * Prepara el contexto SSL.
     *
     * @throws NoSuchAlgorithmException Excepción en caso de error tipo NoSuchAlgorithmException.
     * @throws KeyManagementException   Excepción en caso de error tipo KeyManagementException.
     */
    private static void prepareSslContext() throws NoSuchAlgorithmException, KeyManagementException {

        sslContext = SSLContext.getInstance(AppConstants.TLS_PROTOCOL);
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

    }

    /**
     * Prepara la configuración del TrustStore.
     *
     * @param context Contexto de la aplicación.
     * @throws KeyStoreException        Excepción en caso de error tipo KeyStoreException.
     * @throws NoSuchAlgorithmException Excepción en caso de error tipo NoSuchAlgorithmException.
     * @throws IOException              Excepción en caso de error tipo IOException.
     * @throws CertificateException     Excepción en caso de error tipo CertificateException.
     */
    private static void prepareTrustStore(Context context) throws KeyStoreException, NoSuchAlgorithmException,
            IOException, CertificateException {

        KeyStore trustStore = KeyStore.getInstance(AppConstants.BOUNCY_CASTLE_KS);
        trustManagerFactory = TrustManagerFactory.getInstance(AppConstants.CERTIF_TYPE_X509);

        InputStream inputTrustStore = context.getAssets().open(AppConstants.APP_TRUSTSTORE_FILE);
        trustStore.load(inputTrustStore, AppConstants.TRUSTSTORE_PASSWORD.toCharArray());
        inputTrustStore.close();

        trustManagerFactory.init(trustStore);

    }

    /**
     * Prepara la configuración de KeyStore.
     *
     * @param context Contexto de la aplicación.
     * @throws KeyStoreException         Excepción en caso de error tipo KeyStoreException.
     * @throws NoSuchAlgorithmException  Excepción en caso de error tipo NoSuchAlgorithmException.
     * @throws CertificateException      Excepción en caso de error tipo CertificateException.
     * @throws IOException               Excepción en caso de error tipo IOException.
     * @throws UnrecoverableKeyException Excepción en caso de error tipo UnrecoverableKeyException.
     */
    private static void prepareKeyStore(Context context) throws KeyStoreException, NoSuchAlgorithmException,
            CertificateException, IOException, UnrecoverableKeyException {

        KeyStore keyStore = KeyStore.getInstance(AppConstants.BOUNCY_CASTLE_KS);
        keyManagerFactory = KeyManagerFactory.getInstance(AppConstants.CERTIF_TYPE_X509);

        InputStream inputKeyStore = context.getAssets().open(AppConstants.APP_KEYSTORE_FILE);
        keyStore.load(inputKeyStore, AppConstants.KEYSTORE_CERTIF_PASSWORD.toCharArray());
        inputKeyStore.close();

        keyManagerFactory.init(keyStore, AppConstants.KEYSTORE_CERTIF_PASSWORD.toCharArray());

    }

}
