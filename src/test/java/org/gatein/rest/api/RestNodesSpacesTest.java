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
public class RestNodesSpacesTest {

    private RestService restService;
    private final JSonParser jSonParser = new JSonParser();

    @Before
    public void before() {
        restService = new RestService();
    }

    @Test
    public void testGetNode() throws ParseException {
        System.out.println("**testGetNode**");
        String mylink = restService.getNode("mylink", "/platform/users", "space");
        assertNotNull(mylink);
        Node site = jSonParser.nodeParser(mylink);
        
        assertEquals("mylink", site.getName());
        assertEquals("/portal/g/:platform:users/mylink", site.getUri());
        assertEquals("true", site.getIsVisible());
        assertEquals("VISIBLE", site.getVisibility());
        assertEquals("null", site.getIconName());
        assertEquals("My Link", site.getDisplayName());
        assertNull(site.getPage());

        List<Page> children = site.getChildren();
        assertEquals("blog", children.get(0).getName());
        assertEquals(REST_API_URL + "/spaces/platform/users/navigation/mylink/blog", children.get(0).getURL());
        assertEquals("fedora", children.get(1).getName());
        assertEquals(REST_API_URL + "/spaces/platform/users/navigation/mylink/fedora", children.get(1).getURL());
    }

    @Test
    public void testNonExistingNode() {
        System.out.println("**testNonExistingNode**");
        String node = restService.getNode("non", "/platform/users", "space");
        assertTrue(node.contains("Node /non does not exist"));
    }

    @Test
    public void testGetNavigation() throws ParseException {
        System.out.println("**testGetNavigation**");
        String navigationString = restService.getNavigation("space", "/platform/users", false);
        Navigation navigation = jSonParser.navigationParser(navigationString);
        
        assertEquals("8", navigation.getPriority());
        assertEquals("/platform/users", navigation.getSiteName());
        assertEquals("space", navigation.getSiteType());

        
        List<Node> nodes = navigation.getNodes();
        assertEquals("mylink", nodes.get(0).getName());
        assertEquals(REST_API_URL + "/spaces/platform/users/navigation/mylink", nodes.get(0).getUri());
    }

    @Test
    public void testAddNode() throws ParseException {
        System.out.println("**testAddNode**");
        restService.createNode("newNode", "/platform/users", "space");
        restService.createNode("newNode2", "/platform/users", "space");
        restService.createNode("newNode3", "/platform/users", "space");
        String node = restService.getNode("newNode", "/platform/users", "space");
        assertNotNull(node);
        Node newNode = jSonParser.nodeParser(node);
        
        assertEquals("newNode", newNode.getName());
        assertEquals("/portal/g/:platform:users/newNode", newNode.getUri());
        assertEquals("true", newNode.getIsVisible());
        assertEquals("VISIBLE", newNode.getVisibility());
        assertEquals("null", newNode.getIconName());
        assertEquals("newNode", newNode.getDisplayName());
        assertNull(newNode.getChildren());
        assertNull(newNode.getPage());

        String jSonNavigation = restService.getNavigation("space", "/platform/users", false);
        Navigation navigationAdded = jSonParser.navigationParser(jSonNavigation);
        List<Node> nodes = navigationAdded.getNodes();
        int addedPagesCount = 0;
        for (Node node1 : nodes) {
            if (node1.getName().equals("newNode") || node1.getName().equals("newNode2") || node1.getName().equals("newNode3")) {
                addedPagesCount = addedPagesCount + 1;
            }
        }
        assertEquals(3, addedPagesCount);
        restService.deleteNode("newNode", "/platform/users", "space");
        restService.deleteNode("newNode2", "/platform/users", "space");
        restService.deleteNode("newNode3", "/platform/users", "space");
    }

    @Test
    public void testGetNavigationOfAddedNodes() throws ParseException {
        System.out.println("**testGetNavigationOfAddedNodes**");
        restService.createNode("newNode", "/platform/users", "space");
        restService.createNode("newNode2", "/platform/users", "space");
        restService.createNode("newNode3", "/platform/users", "space");
        String navigationString = restService.getNavigation("space", "/platform/users", false);
        Navigation navigation = jSonParser.navigationParser(navigationString);
        
        assertEquals("8", navigation.getPriority());
        assertEquals("/platform/users", navigation.getSiteName());
        assertEquals("space", navigation.getSiteType());

        
        List<Node> nodes = navigation.getNodes();
        assertEquals("mylink", nodes.get(0).getName());
        assertEquals(REST_API_URL + "/spaces/platform/users/navigation/mylink", nodes.get(0).getUri());
        assertEquals("newNode", nodes.get(1).getName());
        assertEquals(REST_API_URL + "/spaces/platform/users/navigation/newNode", nodes.get(1).getUri());
        assertEquals("newNode2", nodes.get(2).getName());
        assertEquals(REST_API_URL + "/spaces/platform/users/navigation/newNode2", nodes.get(2).getUri());
        assertEquals("newNode3", nodes.get(3).getName());
        assertEquals(REST_API_URL + "/spaces/platform/users/navigation/newNode3", nodes.get(3).getUri());
        
        restService.deleteNode("newNode", "/platform/users", "space");
        restService.deleteNode("newNode2", "/platform/users", "space");
        restService.deleteNode("newNode3", "/platform/users", "space");
    }

    @Test
    public void testUpdateNode() throws ParseException {
        System.out.println("**testUpdateNode**");
        Map<String, String> attributes = new HashMap<>();
        attributes.put("name", "administration");
        attributes.put("type", "space");
        attributes.put("iconName", "Ticket");
        attributes.put("displayName", "Admin Updated Node");

        restService.updateNode(attributes, "/platform/administrators", "space");
        String node = restService.getNode("administration", "/platform/administrators", "space");
        Node homeNode = jSonParser.nodeParser(node);

        assertEquals("administration", homeNode.getName());
        assertEquals("/portal/g/:platform:administrators/administration", homeNode.getUri());
        assertEquals("true", homeNode.getIsVisible());
        assertEquals("VISIBLE", homeNode.getVisibility());
        assertEquals("Ticket", homeNode.getIconName());
        assertEquals("Admin Updated Node", homeNode.getDisplayName());

        List<Page> children = homeNode.getChildren();
        assertEquals("registry", children.get(0).getName());
        assertEquals(REST_API_URL + "/spaces/platform/administrators/navigation/administration/registry", children.get(0).getURL());
        assertEquals("pageManagement", children.get(1).getName());
        assertEquals(REST_API_URL + "/spaces/platform/administrators/navigation/administration/pageManagement", children.get(1).getURL());
        assertEquals("servicesManagement", children.get(2).getName());
        assertEquals(REST_API_URL + "/spaces/platform/administrators/navigation/administration/servicesManagement", children.get(2).getURL());
        assertEquals("siteRedirects", children.get(3).getName());
        assertEquals(REST_API_URL + "/spaces/platform/administrators/navigation/administration/siteRedirects", children.get(3).getURL());
        assertNull(homeNode.getPage());
    }

    @Test
    public void testDeleteNodes() throws ParseException {
        System.out.println("**testDeleteNodes**");
        restService.createNode("newNode", "/platform/users", "space");
        restService.createNode("newNode2", "/platform/users", "space");
        restService.createNode("newNode3", "/platform/users", "space");
        restService.deleteNode("newNode", "/platform/users", "space");
        restService.deleteNode("newNode2", "/platform/users", "space");
        restService.deleteNode("newNode3", "/platform/users", "space");

        String navigationString = restService.getNavigation("space", "/platform/users", false);
        
        Navigation navigation = jSonParser.navigationParser(navigationString);
        
        assertEquals("8", navigation.getPriority());
        assertEquals("/platform/users", navigation.getSiteName());
        assertEquals("space", navigation.getSiteType());
        assertTrue(navigation.getPriority().equals("8"));
        assertTrue(navigation.getSiteName().equals("/platform/users"));
        assertTrue(navigation.getSiteType().equals("space"));

        assertEquals(1, navigation.getNodes().size());
    }
}
