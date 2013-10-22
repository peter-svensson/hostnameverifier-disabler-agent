package com.sparetimecoders.hostnameverifier;

import com.sun.tools.classfile.ClassWriter;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.logging.Logger;

public class HostnameVerificationDisablerAgent {

    private static final String LOG_MSG = "Disabling Hostname Verification";
    private static final String SSL_FACTORY_CLASS_NAME = "org.apache.http.conn.ssl.SSLSocketFactory";


    public static void premain(String args, Instrumentation inst) {
        System.out.println(LOG_MSG);
        System.err.println(LOG_MSG);
        Logger.getLogger("HostnameVerificationDisablerAgent").warning(LOG_MSG);
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });

        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                if (className.equals(SSL_FACTORY_CLASS_NAME.replace('.', '/'))) {
                    tryToDisableApacheHttpClient(classBeingRedefined);
                    System.out.println("PETER");
                }
                return null;
            }

        });
    }

    private static void tryToDisableApacheHttpClient(Class<?> socketFactory) {
        try {
            new ClassWriter().write();
            final Method getSocketFactoryMethod = socketFactory.getDeclaredMethod("getSocketFactory", null);
            final Object theSocketFactory = getSocketFactoryMethod.invoke(socketFactory, null);
            final Method setHostnameVerifierMethod = theSocketFactory.getClass().getMethod("setHostnameVerifier");
            setHostnameVerifierMethod.invoke(theSocketFactory, socketFactory.getDeclaredField("ALLOW_ALL_HOSTNAME_VERIFIER"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
