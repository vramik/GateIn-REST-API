package org.gatein.rest.update;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.impl.client.CloseableHttpClient;
import org.gatein.rest.constants.ConstantsService;
import org.gatein.rest.entity.Site;
import org.gatein.rest.helper.JSonParser;
import org.gatein.rest.service.api.HelpingServiceApi;
import org.gatein.rest.service.impl.HelpingService;
import org.gatein.rest.service.impl.RestService;
import org.json.simple.parser.ParseException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author mgottval
 */
public class AddDashboards {
    
    private CloseableHttpClient httpClient;
    private CloseableHttpClient httpClientMary;
    private HelpingServiceApi helpingService;
    private RestService restService;
    private ConstantsService constantsService;
    private final JSonParser jSonParser = new JSonParser();

    @Before
    public void before() {
        helpingService = new HelpingService();
        httpClientMary = helpingService.httpClientAuthenticationAny("mary", "gtn");
        httpClient = helpingService.httpClientAuthenticationRootAny();
        constantsService = new ConstantsService();
        restService = new RestService(httpClient, helpingService, constantsService);
        
        restService.addSite("mary", "dashboard");
        restService.addSite("john", "dashboard");
        restService.addSite("demo", "dashboard");

    }
    
    @Test
    public void addDashboards() throws ParseException {
        String allSites = restService.getAllSites("dashboard");
        List<Site> dashboardList = jSonParser.sitesParser(allSites);
        assertEquals(4, dashboardList.size());
    }
    
    @After
    public void after() {
        try {
            httpClient.close();
        } catch (IOException ex) {
            Logger.getLogger(AddDashboards.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
