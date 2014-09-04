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
    
    String getHttpRequest(String httpUrl);
    
    void deleteHttpRequest(String httpUrl);
    
    void postHttpRequest(String httpUrl);
    
    void postHttpRequest(String httpUrl, String entityUpdate);
    
    void postHttpRequestAllSitesUpdateAtOnce(String httpUrl, File file);
}
