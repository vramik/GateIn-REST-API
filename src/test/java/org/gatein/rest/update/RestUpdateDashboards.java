/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gatein.rest.update;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author mgottval
 */
public class RestUpdateDashboards {

    private CloseableHttpClient httpClient;
    private CloseableHttpClient httpClientMary;
    private HelpingServiceApi helpingService;
    private RestService restService;
    private ConstantsService constantsService;
    private final JSonParser jSonParser = new JSonParser();

    @Before
    public void before() {
        helpingService = new HelpingService();
        httpClient = helpingService.httpClientAuthenticationRootAny();
        httpClientMary = helpingService.httpClientAuthenticationAny( "mary", "gtn");
        constantsService = new ConstantsService();
        restService = new RestService(httpClient, helpingService, constantsService);
    }
  
    @Test
    public void updateWildcardDashboards() throws ParseException {
        String allSites = restService.getAllSites("dashboard");
        List<Site> dashboardList = jSonParser.sitesParser(allSites);
        assertEquals(4, dashboardList.size());       

        String maryNavigationString = restService.getNavigation("dashboard", "mary", false);
        Navigation maryNavigation = jSonParser.navigationParser(maryNavigationString);
        assertEquals(4, maryNavigation.getNodes().size());
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
        assertEquals(4, demoNavigation.getNodes().size());
        List<Node> demosNodeList = demoNavigation.getNodes();
        List<String> demosNodesNameList = new ArrayList<>();
        for (Node node : demosNodeList) {
            demosNodesNameList.add(node.getName());
        }
        assertTrue(demosNodesNameList.contains("Tab_Default"));
        assertTrue(demosNodesNameList.contains("javascript"));
        assertTrue(demosNodesNameList.contains("jquery"));
        assertTrue(demosNodesNameList.contains("raphael"));
        
         String rootNavigationString = restService.getNavigation("dashboard", "root", false);
        Navigation rootNavigation = jSonParser.navigationParser(rootNavigationString);
        assertEquals(0, rootNavigation.getNodes().size());
        
         String johnNavigationString = restService.getNavigation("dashboard", "john", false);
        Navigation johnNavigation = jSonParser.navigationParser(johnNavigationString);
        assertEquals(1, johnNavigation.getNodes().size());

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
    }

   
    @Test
    public void testUserAllUpdate() throws ParseException {
        String allSites = restService.getAllSites("dashboard");
        System.out.println(allSites);
        List<Site> dashboardList = jSonParser.sitesParser(allSites);
        assertEquals(4, dashboardList.size());
        
        //check navigation
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

        String rootNavigationString = restService.getNavigation("dashboard", "root", false);
        Navigation rootNavigation = jSonParser.navigationParser(rootNavigationString);
        List<Node> rootsNodeList = rootNavigation.getNodes();
        List<String> rootsNodesNameList = new ArrayList<>();
        for (Node node : rootsNodeList) {
            rootsNodesNameList.add(node.getName());
        }
        assertTrue(rootsNodesNameList.contains("Tab_Default"));
        assertTrue(rootsNodesNameList.contains("javascript"));
        assertTrue(rootsNodesNameList.contains("jquery"));
        assertTrue(rootsNodesNameList.contains("raphael"));

        String johnNavigationString = restService.getNavigation("dashboard", "john", false);
        Navigation johnNavigation = jSonParser.navigationParser(johnNavigationString);
        List<Node> johnsNodeList = johnNavigation.getNodes();
        List<String> johnsNodesNameList = new ArrayList<>();
        for (Node node : johnsNodeList) {
            johnsNodesNameList.add(node.getName());
        }
        assertTrue(johnsNodesNameList.contains("Tab_Default"));
        assertTrue(johnsNodesNameList.contains("javascript"));
        assertTrue(johnsNodesNameList.contains("jquery"));
        assertTrue(johnsNodesNameList.contains("raphael"));

        //check pages
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
        
        String dashboardRootPages = restService.getSitePages("root", "dashboard");
        List<Page> rootPages = jSonParser.pagesParser(dashboardRootPages);
        assertEquals(4, rootPages.size());
        List<String> pagesrootNames = new ArrayList<>();

        for (Page page : rootPages) {
            pagesrootNames.add(page.getName());

        }
        assertTrue(pagesrootNames.contains("Tab_Default"));
        assertTrue(pagesrootNames.contains("javascript"));
        assertTrue(pagesrootNames.contains("jquery"));
        assertTrue(pagesrootNames.contains("raphael"));
        
        String dashboardJohnPages = restService.getSitePages("john", "dashboard");
        List<Page> johnPages = jSonParser.pagesParser(dashboardJohnPages);
        assertEquals(4, johnPages.size());
        List<String> pagesJohnNames = new ArrayList<>();

        for (Page page : johnPages) {
            pagesJohnNames.add(page.getName());

        }
        assertTrue(pagesJohnNames.contains("Tab_Default"));
        assertTrue(pagesJohnNames.contains("javascript"));
        assertTrue(pagesJohnNames.contains("jquery"));
        assertTrue(pagesJohnNames.contains("raphael"));
       

    }
    @After
    public void after() {
        try {
            httpClient.close();
        } catch (IOException ex) {
            Logger.getLogger(RestUpdateDashboards.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
