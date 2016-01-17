package com.vega.app.constants;

/**
 * Constantes para toda la app
 */
public class AppConstants {

    /** Define el tipo de configuración final de SSL aplicando protocolo TLS. */
    public static final String TLS_PROTOCOL = "TLS";

    /** Define el tipo de KeyStore propia de las APPs de Android. */
    public static final String BOUNCY_CASTLE_KS = "BKS";

    /** Define el tipo de KeyStore propia de las APPs de Android. */
    public static final String CERTIF_TYPE_X509 = "X509";

    /** TEMPORAL (fichero XML): Password del KeyStore de la APP y de su certificado cliente.*/
    public static final String KEYSTORE_CERTIF_PASSWORD = "secure";

    /** TEMPORAL (fichero XML): Password del TrustStore de la APP.*/
    public static final String TRUSTSTORE_PASSWORD = "secure";

    /** TEMPORAL (fichero XML): Define el nombre del fichero TrustStore del que hará uso la APP. */
    public static final String APP_TRUSTSTORE_FILE = "AndroidTruststore";

    /** TEMPORAL (fichero XML): Define el nombre del fichero KeyStore del que hará uso la APP. */
    public static final String APP_KEYSTORE_FILE = "AndroidKeystore";

}
