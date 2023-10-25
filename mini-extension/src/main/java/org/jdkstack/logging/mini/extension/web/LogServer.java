package org.jdkstack.logging.mini.extension.web;

import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManagerFactory;

public class LogServer {

    public static void main(String[] args) throws Exception {
        final char[] passphrase = "mypass".toCharArray();
        final KeyStore ks = KeyStore.getInstance("JKS");
        final InputStream fileInputStream = LogServer.class.getResourceAsStream("/mytest.jks");
        ks.load(fileInputStream, passphrase);
        final KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, passphrase);
        final TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ks);
        final SSLContext ssl = SSLContext.getInstance("TLS");
        ssl.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        final HttpsServer servers = HttpsServer.create(new InetSocketAddress(8080), 10);
        servers.createContext("/", new RestHandler());
        final ExecutorService executor = new ThreadPoolExecutor(10, 200, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1024), new ThreadPoolExecutor.AbortPolicy());
        servers.setExecutor(executor);
        servers.setHttpsConfigurator(new HttpsConfigurator(ssl) {
            public void configure(HttpsParameters params) {

                // get the remote address if needed
                InetSocketAddress remote = params.getClientAddress();

                SSLContext c = getSSLContext();

                // get the default parameters
                SSLParameters sslparams = c.getDefaultSSLParameters();
     /*   if (remote.equals(...) ){
          // modify the default set for client x
        }*/
                params.setSSLParameters(sslparams);
            }
        });
        servers.start();
    }
}
