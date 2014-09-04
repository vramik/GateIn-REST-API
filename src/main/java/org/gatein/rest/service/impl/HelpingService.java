package org.gatein.rest.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.gatein.rest.service.api.HelpingServiceApi;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author mgottval
 */
public class HelpingService implements HelpingServiceApi {

    @Override
    public CloseableHttpClient httpClientAuthenticationAny(String login, String password) {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials(login, password));

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();
        return httpClient;
    }

    @Override
    public CloseableHttpClient httpClientAuthenticationRootAny() {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials("root", "gtn"));

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();
        return httpClient;
    }

    @Override
    public String getHttpRequest(String httpUrl, CloseableHttpClient httpclient) {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials("root", "gtn"));

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();
        String stringEntity = null;
        try {
            HttpGet httpget = new HttpGet(httpUrl);

            System.out.println("Executing request " + httpget.getRequestLine());
            try (CloseableHttpResponse response = httpClient.execute(httpget)) {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                HttpEntity httpEntity = response.getEntity();
                stringEntity = EntityUtils.toString(httpEntity);
                EntityUtils.consume(httpEntity);
            }
        } catch (IOException ex) {
            Logger.getLogger(HelpingService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpClient.close();
            } catch (IOException ex) {
                Logger.getLogger(HelpingService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return stringEntity;
    }

    @Override
    public void deleteHttpRequest(String httpUrl, CloseableHttpClient httpclient) {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials("root", "gtn"));

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();
        try {
            HttpDelete httpDelete = new HttpDelete(httpUrl);

            System.out.println("Executing request " + httpDelete.getRequestLine());
            try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                HttpEntity httpEntity = response.getEntity();
                EntityUtils.consume(httpEntity);
            }
        } catch (IOException ex) {
            Logger.getLogger(HelpingService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                httpClient.close();
            } catch (IOException ex) {
                Logger.getLogger(HelpingService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void addHttpRequest(String httpUrl, CloseableHttpClient httpclient) {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials("root", "gtn"));

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();
        try {
            HttpPost httpPost = new HttpPost(httpUrl);

            System.out.println("Executing request " + httpPost.getRequestLine());
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                HttpEntity httpEntity = response.getEntity();
                EntityUtils.consume(httpEntity);
            }
        } catch (IOException ex) {
            Logger.getLogger(HelpingService.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                httpClient.close();
            } catch (IOException ex) {
                Logger.getLogger(HelpingService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void updateHttpRequest(String httpUrl, CloseableHttpClient httpclient, String entityUpdate) {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials("root", "gtn"));

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();
        StringEntity input;

        try {
            input = new StringEntity(entityUpdate);
            HttpPut httpPut = new HttpPut(httpUrl);
            input.setContentType("application/json");
            httpPut.setEntity(input);

            System.out.println("Executing request " + httpPut.getRequestLine());
            try (CloseableHttpResponse response = httpClient.execute(httpPut)) {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                HttpEntity httpEntity = response.getEntity();
                EntityUtils.consume(httpEntity);
            }
        } catch (IOException ex) {
            Logger.getLogger(HelpingService.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                httpClient.close();
            } catch (IOException ex) {
                Logger.getLogger(HelpingService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void updateHttpRequestAllSitesUpdateAtOnce(String httpUrl, CloseableHttpClient httpclient, File file) {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials("root", "gtn"));

        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();
        FileEntity input;
        try {
            input = new FileEntity(file);
            HttpPut httpPut = new HttpPut(httpUrl);
            input.setContentType("application/zip");
            httpPut.setEntity(input);

            System.out.println("Executing request " + httpPut.getRequestLine());
            try (CloseableHttpResponse response = httpClient.execute(httpPut)) {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                HttpEntity httpEntity = response.getEntity();
                EntityUtils.consume(httpEntity);
            }
        } catch (IOException ex) {
            Logger.getLogger(HelpingService.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                httpClient.close();
            } catch (IOException ex) {
                Logger.getLogger(HelpingService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
