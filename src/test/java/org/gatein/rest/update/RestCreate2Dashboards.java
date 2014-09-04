/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gatein.rest.update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.gatein.rest.constants.ConstantsService;
import org.gatein.rest.entity.Navigation;
import org.gatein.rest.entity.Node;
import org.gatein.rest.entity.Page;
import org.gatein.rest.entity.Site;
import org.gatein.rest.helper.JSonParser;
import org.gatein.rest.service.api.HelpingServiceApi;
import org.gatein.rest.service.impl.HelpingService;
import org.gatein.rest.service.impl.RestService;
import org.json.simple.parser.ParseException;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author mgottval
 */
public class RestCreate2Dashboards {

   private CloseableHttpClient httpclient;
    private HelpingServiceApi helpingService;
    private RestService restService;
    private HttpEntity entity;
    private ConstantsService constantsService;
    private final JSonParser jSonParser = new JSonParser();

    @Before
    public void before() {
        
        helpingService = new HelpingService();
        httpclient = helpingService.httpClientAuthenticationRootAny();
        constantsService = new ConstantsService();
        restService = new RestService(httpclient, helpingService, constantsService);

    }

    @Test
    public void testCreate2UsersDashboards() throws ParseException {
        String allSites = restService.getAllSites("dashboard");
        List<Site> dashboardList = jSonParser.sitesParser(allSites);
        assertEquals(3, dashboardList.size());
        for (Site dashboard : dashboardList) {
            assertTrue(dashboard.getName().equals("mary") || dashboard.getName().equals("demo") || dashboard.getName().equals("root"));
        }

        String maryNavigationString = restService.getNavigation("dashboard", "mary", false);
        Navigation maryNavigation = jSonParser.navigationParser(maryNavigationString);
        List<Node> marysNodeList = maryNavigation.getNodes();
        List<String> nodesNameList = new ArrayList<>();
        for (Node node : marysNodeList) {
            nodesNameList.add(node.getName());
        }
        assertTrue(nodesNameList.contains("Tab_Default"));
        assertTrue(nodesNameList.contains("javascript"));
        assertTrue(nodesNameList.contains("jquery"));
        assertTrue(nodesNameList.contains("raphael"));

        String demoNavigationString = restService.getNavigation("dashboard", "demo", false);
        Navigation demoNavigation = jSonParser.navigationParser(demoNavigationString);
        List<Node> demosNodeList = demoNavigation.getNodes();
        List<String> demosNodesNameList = new ArrayList<>();
        for (Node node : demosNodeList) {
            demosNodesNameList.add(node.getName());
        }
        assertTrue(demosNodesNameList.contains("Tab_Default"));
        assertTrue(demosNodesNameList.contains("javascript"));
        assertTrue(demosNodesNameList.contains("jquery"));
        assertTrue(demosNodesNameList.contains("raphael"));

        String dashboardPages = restService.getSitePages("mary", "dashboard");
        List<Page> pages = jSonParser.pagesParser(dashboardPages);
        assertEquals(4, pages.size());
        List<String> pagesNames = new ArrayList<>();
        for (Page page : pages) {
            pagesNames.add(page.getName());

        }
        assertTrue(pagesNames.contains("Tab_Default"));
        assertTrue(pagesNames.contains("javascript"));
        assertTrue(pagesNames.contains("jquery"));
        assertTrue(pagesNames.contains("raphael"));

        String dashboardDemoPages = restService.getSitePages("demo", "dashboard");
        List<Page> demoPages = jSonParser.pagesParser(dashboardDemoPages);
        assertEquals(4, demoPages.size());
        List<String> pagesDemoNames = new ArrayList<>();

        for (Page page : demoPages) {
            pagesDemoNames.add(page.getName());

        }
        assertTrue(pagesDemoNames.contains("Tab_Default"));
        assertTrue(pagesDemoNames.contains("javascript"));
        assertTrue(pagesDemoNames.contains("jquery"));
        assertTrue(pagesDemoNames.contains("raphael"));

        String rootNavigationString = restService.getNavigation("dashboard", "root", false);
        Navigation rootNavigation = jSonParser.navigationParser(rootNavigationString);
        assertEquals(0, rootNavigation.getNodes().size());

    }

    @After
    public void after() {
       try {
           httpclient.close();
       } catch (IOException ex) {
           Logger.getLogger(RestCreate2Dashboards.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

}
