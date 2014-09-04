package org.gatein.rest.service.api;

import java.io.File;
import org.apache.http.impl.client.CloseableHttpClient;


/**
 *
 * @author mgottval
 */
public interface HelpingServiceApi {      
    
    CloseableHttpClient httpClientAuthenticationAny(String login, String password);
    
    CloseableHttpClient httpClientAuthenticationRootAny();   
    
    String getHttpRequest(String httpUrl, CloseableHttpClient httpClient);
    
    void deleteHttpRequest(String httpUrl, CloseableHttpClient httpClient);
    
    void addHttpRequest(String httpUrl, CloseableHttpClient httpClient);
    
    void updateHttpRequest(String httpUrl, CloseableHttpClient httpClient, String entityUpdate);
    
    void updateHttpRequestAllSitesUpdateAtOnce(String httpUrl, CloseableHttpClient httpClient, File file);
}
