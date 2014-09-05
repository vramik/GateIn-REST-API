package org.gatein.rest.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gatein.rest.entity.Navigation;
import org.gatein.rest.entity.Node;
import org.gatein.rest.entity.Page;
import org.gatein.rest.helper.JSonParser;
import org.gatein.rest.service.impl.RestService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import static org.gatein.rest.constants.RestConstants.*;
import org.json.simple.parser.ParseException;

/**
 *
 * @author mgottval
 */
public class RestNodesDashboardsTest {

    private RestService restService;
    private final JSonParser jSonParser = new JSonParser();

    @Before
    public void before() {
        restService = new RestService();
    }

    @Test
    public void testAddNode() throws ParseException {
        System.out.println("**testAddNode**");
        restService.addSite("mary", "dashboard");
        restService.createNode("newNode", "mary", "dashboard");
        restService.createNode("newNode2", "mary", "dashboard");
        restService.createNode("newNode3", "mary", "dashboard");
        String node = restService.getNode("newNode", "mary", "dashboard");
        assertNotNull(node);
        Node newNode = jSonParser.nodeParser(node);
        
        assertEquals("newNode", newNode.getName());
        assertEquals("/portal/u/mary/newNode", newNode.getUri());
        assertEquals("true", newNode.getIsVisible());
        assertEquals("VISIBLE", newNode.getVisibility());
        assertEquals("null", newNode.getIconName());
        assertEquals("newNode", newNode.getDisplayName());
        assertNull(newNode.getChildren());
        assertNull(newNode.getPage());

        String jSonNavigation = restService.getNavigation("dashboard", "mary", false);
        Navigation navigationAdded = jSonParser.navigationParser(jSonNavigation);
        assertEquals(4, navigationAdded.getNodes().size());
        restService.deleteNode("newNode", "mary", "dashboard");
        restService.deleteNode("newNode2", "mary", "dashboard");
        restService.deleteNode("newNode3", "mary", "dashboard");
        restService.deleteSite("mary", "dashboard");
    }

    @Test
    public void testGetNode() throws ParseException {
        System.out.println("**testGetNode**");
        restService.addSite("mary", "dashboard");
        String node1 = restService.getNode("Tab_Default", "mary", "dashboard");
        assertNotNull(node1);
        Node site = jSonParser.nodeParser(node1);
        
        assertEquals("Tab_Default", site.getName());
        assertEquals("/portal/u/mary/Tab_Default", site.getUri());
        assertEquals("true", site.getIsVisible());
        assertEquals("VISIBLE", site.getVisibility());
        assertEquals("null", site.getIconName());
        assertEquals("Tab_Default", site.getDisplayName());
        assertNull(site.getChildren());

        Page page = site.getPage();
        assertEquals("Tab_Default", page.getName());
        assertEquals("mary", page.getSiteName());
        assertEquals("dashboard", page.getSiteType());
        assertEquals(REST_API_URL + "/dashboards/mary/pages/Tab_Default", page.getURL());
        
        restService.deleteSite("mary", "dashboard");
    }

    @Test
    public void testNonExistingNode() {
        System.out.println("**testNonExistingNode**");
        restService.addSite("mary", "dashboard");
        String node = restService.getNode("non", "mary", "dashboard");
        assertTrue(node.contains("Node /non does not exist"));
        restService.deleteSite("mary", "dashboard");
    }

    @Test
    public void testGetNavigation() throws ParseException {
        System.out.println("**testGetNavigation**");
        restService.addSite("mary", "dashboard");
        restService.createNode("newNode", "mary", "dashboard");
        restService.createNode("newNode2", "mary", "dashboard");
        restService.createNode("newNode3", "mary", "dashboard");
        String navigationString = restService.getNavigation("dashboard", "mary", false);
        Navigation navigation = jSonParser.navigationParser(navigationString);
        assertEquals("3", navigation.getPriority());
        assertEquals("mary", navigation.getSiteName());
        assertEquals("dashboard", navigation.getSiteType());
        
        List<Node> nodes = navigation.getNodes();
        assertEquals("Tab_Default", nodes.get(0).getName());
        assertEquals(REST_API_URL + "/dashboards/mary/navigation/Tab_Default", nodes.get(0).getUri());
        assertEquals("newNode", nodes.get(1).getName());
        assertEquals(REST_API_URL + "/dashboards/mary/navigation/newNode", nodes.get(1).getUri());
        assertEquals("newNode2", nodes.get(2).getName());
        assertEquals(REST_API_URL + "/dashboards/mary/navigation/newNode2", nodes.get(2).getUri());
        assertEquals("newNode3", nodes.get(3).getName());
        assertEquals(REST_API_URL + "/dashboards/mary/navigation/newNode3", nodes.get(3).getUri());
        
        restService.deleteNode("newNode", "mary", "dashboard");
        restService.deleteNode("newNode2", "mary", "dashboard");
        restService.deleteNode("newNode3", "mary", "dashboard");
        restService.deleteSite("mary", "dashboard");
    }

    @Test
    public void testUpdateNode() throws ParseException {
        System.out.println("**testUpdateNode**");
        restService.addSite("mary", "dashboard");
        restService.createNode("newNode", "mary", "dashboard");
        restService.createNode("newNode2", "mary", "dashboard");
        restService.createNode("newNode3", "mary", "dashboard");
        Map<String, String> attributes = new HashMap<>();
        attributes.put("name", "newNode");
        attributes.put("type", "dashboard");
        attributes.put("iconName", "Ticket");
        attributes.put("displayName", "Updated Node");

        restService.updateNode(attributes, "mary", "dashboard");
        String node = restService.getNode("newNode", "mary", "dashboard");
        Node homeNode = jSonParser.nodeParser(node);

        assertEquals("newNode", homeNode.getName());
        assertEquals("/portal/u/mary/newNode", homeNode.getUri());
        assertEquals("true", homeNode.getIsVisible());
        assertEquals("VISIBLE", homeNode.getVisibility());
        assertEquals("Ticket", homeNode.getIconName());
        assertEquals("Updated Node", homeNode.getDisplayName());
        assertNull(homeNode.getChildren());
        assertNull(homeNode.getPage());

        attributes.clear();
        attributes.put("name", "Tab_Default");
        attributes.put("type", "dashboard");
        attributes.put("iconName", "Ticket");
        attributes.put("displayName", "Updated Default Node");

        restService.updateNode(attributes, "mary", "dashboard");
        String defNode = restService.getNode("Tab_Default", "mary", "dashboard");
        Node dNode = jSonParser.nodeParser(defNode);

        assertEquals("Tab_Default", homeNode.getName());
        assertEquals("/portal/u/mary/Tab_Default", homeNode.getUri());
        assertEquals("true", homeNode.getIsVisible());
        assertEquals("VISIBLE", homeNode.getVisibility());
        assertEquals("Ticket", homeNode.getIconName());
        assertEquals("Updated Default Node", homeNode.getDisplayName());
        assertNull(dNode.getChildren());
        Page page = dNode.getPage();
        assertEquals("Tab_Default", page.getName());
        assertEquals("mary", page.getSiteName());
        assertEquals("dashboard", page.getSiteType());
        assertEquals(REST_API_URL + "/dashboards/mary/pages/Tab_Default", page.getURL());
        restService.deleteNode("newNode", "mary", "dashboard");
        restService.deleteNode("newNode2", "mary", "dashboard");
        restService.deleteNode("newNode3", "mary", "dashboard");
        restService.deleteSite("mary", "dashboard");
    }

    @Test
    public void testDeleteNodes() throws ParseException {
        System.out.println("**testDeleteNodes**");
        restService.addSite("mary", "dashboard");
        restService.createNode("newNode", "mary", "dashboard");
        restService.createNode("newNode2", "mary", "dashboard");
        restService.createNode("newNode3", "mary", "dashboard");
        restService.deleteNode("newNode", "mary", "dashboard");
        restService.deleteNode("newNode2", "mary", "dashboard");
        restService.deleteNode("newNode3", "mary", "dashboard");

        String navigationString = restService.getNavigation("dashboard", "mary", false);
        Navigation navigation = jSonParser.navigationParser(navigationString);
        
        assertEquals("3", navigation.getPriority());
        assertEquals("mary", navigation.getSiteName());
        assertEquals("dashboard", navigation.getSiteType());

        assertEquals(1, navigation.getNodes().size());

        restService.deleteSite("mary", "dashboard");
    }
}
