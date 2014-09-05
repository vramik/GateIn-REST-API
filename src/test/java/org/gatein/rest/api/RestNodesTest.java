package org.gatein.rest.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gatein.rest.entity.Navigation;
import org.gatein.rest.entity.Node;
import org.gatein.rest.entity.Page;
import org.gatein.rest.helper.JSonParser;
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

    private RestService restService;
    private final JSonParser jSonParser = new JSonParser();

    @Before
    public void before() {
        restService = new RestService();
    }

    @Test
    public void testGetNode() throws ParseException {
        System.out.println("**testGetNode**");
        restService.AddPageToSite("assinedPage", "classic", "site");
        String siteMap = restService.getNode("sitemap", "classic", "site");
        assertNotNull(siteMap);
        Node site = jSonParser.nodeParser(siteMap);
        assertEquals("sitemap", site.getName());
        assertEquals("/portal/classic/sitemap", site.getUri());
        assertEquals("true", site.getIsVisible());
        assertEquals("VISIBLE", site.getVisibility());
        assertEquals("null", site.getIconName());
        assertEquals("SiteMap", site.getDisplayName());
        assertNull(site.getChildren());
        assertEquals("sitemap", site.getPage().getName());
        assertEquals("classic", site.getPage().getSiteName());
        assertEquals("site", site.getPage().getSiteType());
        assertEquals(REST_API_URL + "/sites/classic/pages/sitemap", site.getPage().getURL());

        String homeNode = restService.getNode("home", "classic", "site");
        assertNotNull(homeNode);
        Node home = jSonParser.nodeParser(homeNode);
        assertEquals("home", home.getName());
        assertEquals("/portal/classic/home", home.getUri());
        assertEquals("true", home.getIsVisible());
        assertEquals("VISIBLE", home.getVisibility());
        assertEquals("null", home.getIconName());
        assertEquals("Home", home.getDisplayName());
        assertNull(home.getChildren());
        assertEquals("homepage", home.getPage().getName());
        assertEquals("classic", home.getPage().getSiteName());
        assertEquals("site", home.getPage().getSiteType());
        assertEquals(REST_API_URL + "/sites/classic/pages/homepage", home.getPage().getURL());
    }

    @Test
    public void testGetNodeOfAddedSite() throws ParseException {
        System.out.println("**testGetNodeOfAddedSite**");
        restService.addSite("newSite", "site");
        String siteMap = restService.getNode("home", "newSite", "site");
        assertNotNull(siteMap);
        Node site = jSonParser.nodeParser(siteMap);
        assertEquals("home", site.getName());
        assertEquals("/portal/newSite/home", site.getUri());
        assertEquals("true", site.getIsVisible());
        assertEquals("VISIBLE", site.getVisibility());
        assertEquals("null", site.getIconName());
        assertEquals("Home", site.getDisplayName());
        assertNull(site.getChildren());
        assertEquals("homepage", site.getPage().getName());
        assertEquals("newSite", site.getPage().getSiteName());
        assertEquals("site", site.getPage().getSiteType());
        assertEquals(REST_API_URL + "/sites/newSite/pages/homepage", site.getPage().getURL());
        
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
        assertEquals("1", navigation.getPriority());
        assertEquals("classic", navigation.getSiteName());
        assertEquals("site", navigation.getSiteType());

        List<Node> nodes = navigation.getNodes();
        assertEquals("home", nodes.get(0).getName());
        assertEquals(REST_API_URL + "/sites/classic/navigation/home", nodes.get(0).getUri());
        assertEquals("sitemap", nodes.get(1).getName());
        assertEquals(REST_API_URL + "/sites/classic/navigation/sitemap", nodes.get(1).getUri());
    }

    @Test
    public void testGetNavigationOfAddedSite() throws ParseException {
        System.out.println("**testGetNavigationOfAddedSite**");
        restService.addSite("newSite", "site");
        String navigationString = restService.getNavigation("site", "newSite", false);
        Navigation navigation = jSonParser.navigationParser(navigationString);
        assertEquals("1", navigation.getPriority());
        assertEquals("newSite", navigation.getSiteName());
        assertEquals("site", navigation.getSiteType());
        
        List<Node> nodes = navigation.getNodes();
        assertEquals("home", nodes.get(0).getName());
        assertEquals(REST_API_URL + "/sites/newSite/navigation/home", nodes.get(0).getUri());
        restService.deleteSite("newSite", "site");
    }

    @Test
    public void testAddNode() throws ParseException {
        System.out.println("**testAddNode**");
        restService.createNode("newNode", "classic", "site");
        restService.createNode("newNode2", "classic", "site");
        restService.createNode("newNode3", "classic", "site");

        String node = restService.getNode("newNode", "classic", "site");
        assertNotNull(node);
        Node newNode = jSonParser.nodeParser(node);
        assertEquals("newNode", newNode.getName());
        assertEquals("/portal/classic/newNode", newNode.getUri());
        assertEquals("true", newNode.getIsVisible());
        assertEquals("VISIBLE", newNode.getVisibility());
        assertEquals("null", newNode.getIconName());
        assertEquals("newNode", newNode.getDisplayName());
        assertNull(newNode.getChildren());
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
        
        assertEquals("1", navigation.getPriority());
        assertEquals("classic", navigation.getSiteName());
        assertEquals("site", navigation.getSiteType());
        
        List<Node> nodes = navigation.getNodes();
        assertEquals("home", nodes.get(0).getName());
        assertEquals(REST_API_URL + "/sites/classic/navigation/home", nodes.get(0).getUri());
        assertEquals("sitemap", nodes.get(1).getName());
        assertEquals(REST_API_URL + "/sites/classic/navigation/sitemap", nodes.get(1).getUri());
        assertEquals("newNode", nodes.get(2).getName());
        assertEquals(REST_API_URL + "/sites/classic/navigation/newNode", nodes.get(2).getUri());
        assertEquals("newNode2", nodes.get(3).getName());
        assertEquals(REST_API_URL + "/sites/classic/navigation/newNode2", nodes.get(3).getUri());
        assertEquals("newNode3", nodes.get(4).getName());
        assertEquals(REST_API_URL + "/sites/classic/navigation/newNode3", nodes.get(4).getUri());

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
        assertEquals("sitemap", page.getName());
        assertEquals("classic", page.getSiteName());
        assertEquals("site", page.getSiteType());
        assertEquals(REST_API_URL + "/sites/classic/pages/sitemap", page.getURL());

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
        assertEquals("home", homeNode.getName());
        assertEquals("/portal/classic/home", homeNode.getUri());
        assertEquals("false", homeNode.getIsVisible());
        assertEquals("HIDDEN", homeNode.getVisibility());
        assertEquals("StarPage", homeNode.getIconName());
        assertEquals("home Updated Node", homeNode.getDisplayName());
        assertNull(homeNode.getChildren());
        assertEquals("homepage", homeNode.getPage().getName());
        assertEquals("classic", homeNode.getPage().getSiteName());
        assertEquals("site", homeNode.getPage().getSiteType());
        assertEquals(REST_API_URL + "/sites/classic/pages/homepage", homeNode.getPage().getURL());

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
        assertEquals("home", restoredHomeNode.getName());
        assertEquals("/portal/classic/home", restoredHomeNode.getUri());
        assertEquals("true", restoredHomeNode.getIsVisible());
        assertEquals("VISIBLE", restoredHomeNode.getVisibility());
        assertEquals("null", restoredHomeNode.getIconName());
        assertEquals("Home", restoredHomeNode.getDisplayName());
        assertNull(restoredHomeNode.getChildren());
        assertEquals("homepage", restoredHomeNode.getPage().getName());
        assertEquals("classic", restoredHomeNode.getPage().getSiteName());
        assertEquals("site", restoredHomeNode.getPage().getSiteType());
        assertEquals(REST_API_URL + "/sites/classic/pages/homepage", restoredHomeNode.getPage().getURL());
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
        assertEquals("1", navigation.getPriority());
        assertEquals("classic", navigation.getSiteName());
        assertEquals("site", navigation.getSiteType());
        assertEquals(2, navigation.getNodes().size());
        restService.addSite("newSite", "site");
        restService.deleteSite("newSite", "site");
    }
}
