package com.br.loja.virtual.loja_virtual_spring.service.ws;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
import lombok.Data;
import org.glassfish.jersey.media.multipart.internal.MultiPartWriter;

import javax.net.ssl.*;
import java.io.Serializable;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class HostIgnoreClient implements Serializable {

    private String hostName;

    public HostIgnoreClient(String hostName) {
        this.hostName = hostName;
    }

    public Client hostIgnoringCliente() throws Exception {

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
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new SecureRandom());

        Set<String> hostNameList = new HashSet<>();
        hostNameList.add(this.hostName);
        HttpsURLConnection.setDefaultHostnameVerifier(new IgnoreHostNameSSL(hostNameList));

        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        DefaultClientConfig clientConfig = new DefaultClientConfig();

        Map<String, Object> properties = clientConfig.getProperties();
        HTTPSProperties httpsProperties = new HTTPSProperties(new HostnameVerifier() {

            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        }, sslContext);

        properties.put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, httpsProperties);
        clientConfig.getClasses().add(JacksonJsonProvider.class);
        clientConfig.getClasses().add(MultiPartWriter.class);

        return Client.create(clientConfig);
    }
}
