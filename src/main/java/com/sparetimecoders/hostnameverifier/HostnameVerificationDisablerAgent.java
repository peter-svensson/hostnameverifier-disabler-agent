package com.sparetimecoders.hostnameverifier;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.lang.instrument.Instrumentation;

public class HostnameVerificationDisablerAgent {

    private static final String LOG_MSG = "Disabling Hostname Verification";

    public static void premain(String args, Instrumentation inst) {
        System.out.println(LOG_MSG);
        System.err.println(LOG_MSG);
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
    }
}
