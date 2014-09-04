package org.gatein.rest.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gatein.rest.constants.ConstantsService;
import org.gatein.rest.entity.Navigation;
import org.gatein.rest.entity.Node;
import org.gatein.rest.entity.Page;
import org.gatein.rest.helper.JSonParser;
import org.gatein.rest.service.api.HelpingServiceApi;
import org.gatein.rest.service.impl.HelpingService;
import org.gatein.rest.service.impl.RestService;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import static org.gatein.rest.constants.RestConstants.*;
import org.json.simple.parser.ParseException;

/**
 *
 * @author mgottval
 */
public class RestNodesTest {

    private HelpingServiceApi helpingService;
    private RestService restService;
    private ConstantsService constantsService;
    private final JSonParser jSonParser = new JSonParser();

    @Before
    public void before() {
        helpingService = new HelpingService();
        constantsService = new ConstantsService();
        restService = new RestService(helpingService, constantsService);

    }

    @Test
    public void testGetNode() throws ParseException {
        System.out.println("**testGetNode**");
        restService.AddPageToSite("assinedPage", "classic", "site");
        String siteMap = restService.getNode("sitemap", "classic", "site");
        assertNotNull(siteMap);
        Node site = jSonParser.nodeParser(siteMap);
        assertTrue((site.getName()).equals("sitemap"));
        assertTrue((site.getUri()).equals("/portal/classic/sitemap"));
        assertTrue((site.getIsVisible()).equals("true"));
        assertTrue((site.getVisibility()).equals("VISIBLE"));
        assertTrue((site.getIconName()).equals("null"));
        assertTrue((site.getDisplayName()).equals("SiteMap"));
        assertTrue((site.getChildren()) == null);
        assertTrue((site.getPage().getName()).equals("sitemap"));
        assertTrue((site.getPage().getSiteName()).equals("classic"));
        assertTrue((site.getPage().getSiteType()).equals("site"));
        assertTrue((site.getPage().getURL()).equals(REST_API_URL + "/sites/classic/pages/sitemap"));

        String homeNode = restService.getNode("home", "classic", "site");
        assertNotNull(homeNode);
        Node home = jSonParser.nodeParser(homeNode);
        assertTrue((home.getName()).equals("home"));
        assertTrue((home.getUri()).equals("/portal/classic/home"));
        assertTrue((home.getIsVisible()).equals("true"));
        assertTrue((home.getVisibility()).equals("VISIBLE"));
        assertTrue((home.getIconName()).equals("null"));
        assertTrue((home.getDisplayName()).equals("Home"));
        assertTrue((home.getChildren()) == (null));
        assertTrue((home.getPage().getName()).equals("homepage"));
        assertTrue((home.getPage().getSiteName()).equals("classic"));
        assertTrue((home.getPage().getSiteType()).equals("site"));
        assertTrue((home.getPage().getURL()).equals(REST_API_URL + "/sites/classic/pages/homepage"));

    }

    @Test
    public void testGetNodeOfAddedSite() throws ParseException {
        System.out.println("**testGetNodeOfAddedSite**");
        restService.addSite("newSite", "site");
        String siteMap = restService.getNode("home", "newSite", "site");
        assertNotNull(siteMap);
        Node site = jSonParser.nodeParser(siteMap);
        assertTrue((site.getName()).equals("home"));
        assertTrue((site.getUri()).equals("/portal/newSite/home"));
        assertTrue((site.getIsVisible()).equals("true"));
        assertTrue((site.getVisibility()).equals("VISIBLE"));
        assertTrue((site.getIconName()).equals("null"));
        assertTrue((site.getDisplayName()).equals("Home"));
        assertTrue((site.getChildren()) == (null));
        assertTrue((site.getPage().getName()).equals("homepage"));
        assertTrue((site.getPage().getSiteName()).equals("newSite"));
        assertTrue((site.getPage().getSiteType()).equals("site"));
        assertTrue((site.getPage().getURL()).equals(REST_API_URL + "/sites/newSite/pages/homepage"));
        restService.deleteSite("newSite", "site");
    }

    @Test
    public void testNonExistingNode() {
        System.out.println("**testNonExistingNode**");
        String node = restService.getNode("non", "classic", "site");
        assertTrue(node.contains("Node /non does not exist"));
    }

    @Test
    public void testGetNavigation() throws ParseException {
        System.out.println("**testGetNavigation**");
        String navigationString = restService.getNavigation("site", "classic", false);
        Navigation navigation = jSonParser.navigationParser(navigationString);
        assertTrue(navigation.getPriority().equals("1"));
        assertTrue(navigation.getSiteName().equals("classic"));
        assertTrue(navigation.getSiteType().equals("site"));
        Object[] pagesArray = navigation.getNodes().toArray();
        assertTrue(((Node) pagesArray[0]).getName().equals("home"));
        assertTrue(((Node) pagesArray[0]).getUri().equals(REST_API_URL + "/sites/classic/navigation/home"));
        assertTrue(((Node) pagesArray[1]).getName().equals("sitemap"));
        assertTrue(((Node) pagesArray[1]).getUri().equals(REST_API_URL + "/sites/classic/navigation/sitemap"));

    }

    @Test
    public void testGetNavigationOfAddedSite() throws ParseException {
        System.out.println("**testGetNavigationOfAddedSite**");
        restService.addSite("newSite", "site");
        String navigationString = restService.getNavigation("site", "newSite", false);
        Navigation navigation = jSonParser.navigationParser(navigationString);
        assertTrue(navigation.getPriority().equals("1"));
        assertTrue(navigation.getSiteName().equals("newSite"));
        assertTrue(navigation.getSiteType().equals("site"));
        Object[] pagesArray = navigation.getNodes().toArray();
        assertTrue(((Node) pagesArray[0]).getName().equals("home"));
        assertTrue(((Node) pagesArray[0]).getUri().equals(REST_API_URL + "/sites/newSite/navigation/home"));
        restService.deleteSite("newSite", "site");
    }

    @Test
    public void testAddNode() throws ParseException {
        System.out.println("**testAddNode**");
        restService.createNode("newNode", "classic", "site");
        restService.createNode("newNode2", "classic", "site");
        restService.createNode("newNode3", "classic", "site");
        String navigationString = restService.getNavigation("site", "classic", false);
        Navigation navigation = jSonParser.navigationParser(navigationString);

        String node = restService.getNode("newNode", "classic", "site");
        assertNotNull(node);
        Node newNode = jSonParser.nodeParser(node);
        assertTrue((newNode.getName()).equals("newNode"));
        assertTrue((newNode.getUri()).equals("/portal/classic/newNode"));
        assertTrue((newNode.getIsVisible()).equals("true"));
        assertTrue((newNode.getVisibility()).equals("VISIBLE"));
        assertTrue((newNode.getIconName()).equals("null"));
        assertTrue((newNode.getDisplayName()).equals("newNode"));
        assertTrue((newNode.getChildren()) == (null));
        assertNull(newNode.getPage());

        String jSonNavigation = restService.getNavigation("site", "classic", false);
        Navigation navigationAdded = jSonParser.navigationParser(jSonNavigation);
        List<Node> nodes = navigationAdded.getNodes();
        int addedPagesCount = 0;
        for (Node node1 : nodes) {
            if (node1.getName().equals("newNode") || node1.getName().equals("newNode2") || node1.getName().equals("newNode3")) {
                addedPagesCount = addedPagesCount + 1;
            }
        }
        assertEquals(3, addedPagesCount);
        restService.deleteNode("newNode", "classic", "site");
        restService.deleteNode("newNode2", "classic", "site");
        restService.deleteNode("newNode3", "classic", "site");

    }

    @Test
    public void testGetNavigationOfAddedNodes() throws ParseException {
        System.out.println("**testGetNavigationOfAddedNodes**");
        restService.createNode("newNode", "classic", "site");
        restService.createNode("newNode2", "classic", "site");
        restService.createNode("newNode3", "classic", "site");
        String navigationString = restService.getNavigation("site", "classic", false);
        Navigation navigation = jSonParser.navigationParser(navigationString);
        assertTrue(navigation.getPriority().equals("1"));
        assertTrue(navigation.getSiteName().equals("classic"));
        assertTrue(navigation.getSiteType().equals("site"));
        Object[] pagesArray = navigation.getNodes().toArray();
        assertTrue(((Node) pagesArray[0]).getName().equals("home"));
        assertTrue(((Node) pagesArray[0]).getUri().equals(REST_API_URL + "/sites/classic/navigation/home"));
        assertTrue(((Node) pagesArray[1]).getName().equals("sitemap"));
        assertTrue(((Node) pagesArray[1]).getUri().equals(REST_API_URL + "/sites/classic/navigation/sitemap"));
        assertTrue(((Node) pagesArray[2]).getName().equals("newNode"));
        assertTrue(((Node) pagesArray[2]).getUri().equals(REST_API_URL + "/sites/classic/navigation/newNode"));
        assertTrue(((Node) pagesArray[3]).getName().equals("newNode2"));
        assertTrue(((Node) pagesArray[3]).getUri().equals(REST_API_URL + "/sites/classic/navigation/newNode2"));
        assertTrue(((Node) pagesArray[4]).getName().equals("newNode3"));
        assertTrue(((Node) pagesArray[4]).getUri().equals(REST_API_URL + "/sites/classic/navigation/newNode3"));
        restService.deleteNode("newNode", "classic", "site");
        restService.deleteNode("newNode2", "classic", "site");
        restService.deleteNode("newNode3", "classic", "site");

    }

    @Test
    public void testAssignPageToNode() throws ParseException {
        System.out.println("**testAssignPageToNode**");
        restService.createNode("newNode", "classic", "site");
        restService.createNode("newNode2", "classic", "site");
        restService.createNode("newNode3", "classic", "site");
        String node = restService.getNode("newNode", "classic", "site");
        Node newNode = jSonParser.nodeParser(node);
        assertNull(newNode.getPage());

        restService.updateNodesPage("{\"page\" : {\n"
                + "        \"pageName\" : \"sitemap\",\n"
                + "        \"siteName\" : \"classic\",\n"
                + "        \"siteType\" : \"site\"\n"
                + "   }}", "classic", "site", "newNode");

        String nodeUpdated = restService.getNode("newNode", "classic", "site");
        Node newNodeUpdated = jSonParser.nodeParser(nodeUpdated);
        Page page = newNodeUpdated.getPage();
        assertTrue(page.getName().equals("sitemap"));
        assertTrue(page.getSiteName().equals("classic"));
        assertTrue(page.getSiteType().equals("site"));
        assertTrue(page.getURL().equals(REST_API_URL + "/sites/classic/pages/sitemap"));

        restService.updateNodesPage("{\"page\" : null}", "classic", "site", "newNode");
        String nodeNull = restService.getNode("newNode", "classic", "site");
        Node newNodeNull = jSonParser.nodeParser(nodeNull);
        assertNull(newNodeNull.getPage());
        restService.deleteNode("newNode", "classic", "site");
        restService.deleteNode("newNode2", "classic", "site");
        restService.deleteNode("newNode3", "classic", "site");

    }

    @Test
    public void testUpdateNode() throws ParseException {
        System.out.println("**testUpdateNode**");
        Map<String, String> attributes = new HashMap<>();
        attributes.put("name", "home");
        attributes.put("type", "site");
        attributes.put("isVisible", "false");
        attributes.put("visibility", "{\"status\" : \"HIDDEN\"}");
        attributes.put("iconName", "StarPage");
        attributes.put("displayName", "home Updated Node");

        restService.updateNode(attributes, "classic", "site");
        String node = restService.getNode("home", "classic", "site");
        Node homeNode = jSonParser.nodeParser(node);
        assertTrue((homeNode.getName()).equals("home"));
        assertTrue((homeNode.getUri()).equals("/portal/classic/home"));
        assertTrue((homeNode.getIsVisible()).equals("false"));
        assertTrue((homeNode.getVisibility()).equals("HIDDEN"));
        assertTrue((homeNode.getIconName()).equals("StarPage"));
        assertTrue((homeNode.getDisplayName()).equals("home Updated Node"));
        assertTrue((homeNode.getChildren()) == (null));
        assertTrue((homeNode.getPage().getName()).equals("homepage"));
        assertTrue((homeNode.getPage().getSiteName()).equals("classic"));
        assertTrue((homeNode.getPage().getSiteType()).equals("site"));
        assertTrue((homeNode.getPage().getURL()).equals(REST_API_URL + "/sites/classic/pages/homepage"));

        attributes.clear();
        attributes.put("name", "home");
        attributes.put("type", "site");
        attributes.put("isVisible", "true");
        attributes.put("visibility", "{\"status\" : \"VISIBLE\"}");
        attributes.put("iconName", "null");
        attributes.put("displayName", "Home");

        restService.updateNode(attributes, "classic", "site");
        String restoredNode = restService.getNode("home", "classic", "site");
        Node restoredHomeNode = jSonParser.nodeParser(restoredNode);
        assertTrue((restoredHomeNode.getName()).equals("home"));
        assertTrue((restoredHomeNode.getUri()).equals("/portal/classic/home"));
        assertTrue((restoredHomeNode.getIsVisible()).equals("true"));
        assertTrue((restoredHomeNode.getVisibility()).equals("VISIBLE"));
        assertTrue((restoredHomeNode.getIconName()).equals("null"));
        assertTrue((restoredHomeNode.getDisplayName()).equals("Home"));
        assertTrue((restoredHomeNode.getChildren()) == (null));
        assertTrue((restoredHomeNode.getPage().getName()).equals("homepage"));
        assertTrue((restoredHomeNode.getPage().getSiteName()).equals("classic"));
        assertTrue((restoredHomeNode.getPage().getSiteType()).equals("site"));
        assertTrue((restoredHomeNode.getPage().getURL()).equals(REST_API_URL + "/sites/classic/pages/homepage"));
    }

    @Test
    public void testDeleteSites() throws ParseException {
        System.out.println("**testDeleteSites**");
        restService.createNode("newNode", "classic", "site");
        restService.createNode("newNode2", "classic", "site");
        restService.createNode("newNode3", "classic", "site");
        restService.deleteNode("newNode", "classic", "site");
        restService.deleteNode("newNode2", "classic", "site");
        restService.deleteNode("newNode3", "classic", "site");

        String navigationString = restService.getNavigation("site", "classic", false);
        Navigation navigation = jSonParser.navigationParser(navigationString);
        assertTrue(navigation.getPriority().equals("1"));
        assertTrue(navigation.getSiteName().equals("classic"));
        assertTrue(navigation.getSiteType().equals("site"));
        Object[] pagesArray = navigation.getNodes().toArray();

        assertEquals(pagesArray.length, 2);
        restService.addSite("newSite", "site");
        restService.deleteSite("newSite", "site");
    }
}
