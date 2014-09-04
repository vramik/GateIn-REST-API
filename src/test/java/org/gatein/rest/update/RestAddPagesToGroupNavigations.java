/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gatein.rest.update;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.impl.client.CloseableHttpClient;
import org.gatein.rest.service.api.HelpingServiceApi;
import org.gatein.rest.service.impl.HelpingService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author mgottval
 */
public class RestAddPagesToGroupNavigations {

    private CloseableHttpClient httpclient;
    private HelpingServiceApi helpingService;

    @Before
    public void before() {
        helpingService = new HelpingService();
        httpclient = helpingService.httpClientAuthenticationRootAny();
    }

    @Test
    public void AddPagesToGroupNavigationsAdminsUsers() {
        File groupTemplate = new File("src/main/resources/group.zip");
        helpingService.updateHttpRequestAllSitesUpdateAtOnce("http://localhost:8080/rest/private/managed-components/template/group?importMode=merge&targetGroup=/platform/administrators&targetGroup=/platform/users", httpclient, groupTemplate);
    }

    @After
    public void after() {
        try {
            httpclient.close();
        } catch (IOException ex) {
            Logger.getLogger(RestAddPagesToGroupNavigations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
