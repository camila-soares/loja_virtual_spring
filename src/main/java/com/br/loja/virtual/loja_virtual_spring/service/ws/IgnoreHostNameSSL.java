package com.br.loja.virtual.loja_virtual_spring.service.ws;

import javax.net.ssl.*;
import java.io.Serializable;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Set;

public class IgnoreHostNameSSL implements HostnameVerifier, Serializable {
    private static final long serialVersionUID = 1L;


    private static final HostnameVerifier hostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
    private final Set<String> trustedHosts;

    public IgnoreHostNameSSL(Set<String> trustedHosts) {
        this.trustedHosts = trustedHosts;
    }

    @Override
    public boolean verify(String hostName, SSLSession session) {
           if (trustedHosts.contains(hostName)) {
               return true;
           }else {
               return hostnameVerifier.verify(hostName, session);
           }
    }

    public static HostnameVerifier getDefaultHostnameVerifier() throws Exception {

        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }
                }
        };

        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new SecureRandom());

        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {

            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
        return hostnameVerifier;
    }
}
