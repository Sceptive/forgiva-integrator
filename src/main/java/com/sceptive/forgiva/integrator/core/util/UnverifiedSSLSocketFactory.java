package com.sceptive.forgiva.integrator.core.util;


import java.io.*;
import java.net.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.*;
import javax.net.ssl.*;

public class UnverifiedSSLSocketFactory  extends SSLSocketFactory {

    private SSLSocketFactory socketFactory;

    public UnverifiedSSLSocketFactory() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[]{ new DummyTrustManager()}, new SecureRandom());
            socketFactory = ctx.getSocketFactory();
        } catch ( Exception ex )
        {
            throw new IllegalArgumentException(ex);
        }
    }

    public static SocketFactory getDefault() { return new UnverifiedSSLSocketFactory(); }

    @Override public String[] getDefaultCipherSuites() { return socketFactory.getDefaultCipherSuites(); }
    @Override public String[] getSupportedCipherSuites() { return socketFactory.getSupportedCipherSuites(); }

    @Override public Socket createSocket(Socket socket, String string, int i, boolean bln) throws IOException {
        return socketFactory.createSocket(socket, string, i, bln);
    }
    @Override public Socket createSocket(String string, int i) throws IOException, UnknownHostException {
        return socketFactory.createSocket(string, i);
    }
    @Override public Socket createSocket(String string, int i, InetAddress ia, int i1) throws IOException, UnknownHostException {
        return socketFactory.createSocket(string, i, ia, i1);
    }
    @Override public Socket createSocket(InetAddress ia, int i) throws IOException {
        return socketFactory.createSocket(ia, i);
    }
    @Override public Socket createSocket(InetAddress ia, int i, InetAddress ia1, int i1) throws IOException {
        return socketFactory.createSocket(ia, i, ia1, i1);
    }
    }

    class DummyTrustManager implements X509TrustManager {
        @Override public void checkClientTrusted(X509Certificate[] xcs, String str) {
            // do nothing
        }
        @Override public void checkServerTrusted(X509Certificate[] xcs, String str) {
                /*System.out.println("checkServerTrusted for authType: " + str); // RSA
                for(int idx=0; idx<xcs.length; idx++) {
                    X509Certificate cert = xcs[idx];
                    System.out.println("X500Principal: " + cert.getSubjectX500Principal().getName());
                }*/
        }
        @Override public X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[0];
        }
}