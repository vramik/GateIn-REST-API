package org.gatein.rest.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.apache.http.client.methods.HttpRequestBase;
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
        return httpClientAuthenticationAny("root", "gtn");
    }

    @Override
    public String getHttpRequest(String httpUrl) {
        return executeHttpRequest(new HttpGet(httpUrl));
    }

    @Override
    public void deleteHttpRequest(String httpUrl) {
        executeHttpRequest(new HttpDelete(httpUrl));
    }

    @Override
    public void postHttpRequest(String httpUrl) {
        executeHttpRequest(new HttpPost(httpUrl));
    }
    
    @Override
    public void postHttpRequest(String httpUrl, String entityUpdate) {
        executeHttpRequest(prepareHttpPut(httpUrl, entityUpdate));
    }
    
    @Override
    public void postHttpRequestAllSitesUpdateAtOnce(String httpUrl, File file) {
        executeHttpRequest(prepareHttpPut(httpUrl, file));
    }

    private String executeHttpRequest(HttpRequestBase httpRequest) {
        String stringEntity = null;
        System.out.println("Executing request " + httpRequest.getRequestLine());
        try (CloseableHttpResponse response = httpClientAuthenticationRootAny().execute(httpRequest)) {
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            HttpEntity httpEntity = response.getEntity();
            stringEntity = EntityUtils.toString(httpEntity);
            EntityUtils.consume(httpEntity);
        } catch (IOException ex) {
            Logger.getLogger(HelpingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stringEntity;
    }

    private HttpPut prepareHttpPut(String httpUrl, String entityUpdate) {
        HttpPut httpPut = null;
        try {
            StringEntity entity = new StringEntity(entityUpdate);
            entity.setContentType("application/json");
            httpPut = new HttpPut(httpUrl);
            httpPut.setEntity(entity);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(HelpingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return httpPut;
    }
    
    private HttpPut prepareHttpPut(String httpUrl, File file) {
        FileEntity input = new FileEntity(file);
        HttpPut httpPut = new HttpPut(httpUrl);
        input.setContentType("application/zip");
        httpPut.setEntity(input);
        return httpPut;
    }
}
