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
        String mylink = restService.getNode("mylink", "/platform/users", "space");
        assertNotNull(mylink);
        Node site = jSonParser.nodeParser(mylink);
        assertTrue((site.getName()).equals("mylink"));
        assertTrue((site.getUri()).equals("/portal/g/:platform:users/mylink"));
        assertTrue((site.getIsVisible()).equals("true"));
        assertTrue((site.getVisibility()).equals("VISIBLE"));
        assertTrue((site.getIconName()).equals("null"));
        assertTrue((site.getDisplayName()).equals("My Link"));
        Object[] children = site.getChildren().toArray();
        assertTrue(((Page) children[0]).getName().equals("blog"));
        assertTrue(((Page) children[0]).getURL().equals(REST_API_URL + "/spaces/platform/users/navigation/mylink/blog"));
        assertTrue(((Page) children[1]).getName().equals("fedora"));
        assertTrue(((Page) children[1]).getURL().equals(REST_API_URL + "/spaces/platform/users/navigation/mylink/fedora"));


        assertTrue(site.getPage() == null);
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
        assertTrue(navigation.getPriority().equals("8"));
        assertTrue(navigation.getSiteName().equals("/platform/users"));
        assertTrue(navigation.getSiteType().equals("space"));
        Object[] pagesArray = navigation.getNodes().toArray();
        assertTrue(((Node) pagesArray[0]).getName().equals("mylink"));
        assertTrue(((Node) pagesArray[0]).getUri().equals(REST_API_URL + "/spaces/platform/users/navigation/mylink"));
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
        assertTrue((newNode.getName()).equals("newNode"));
        assertTrue((newNode.getUri()).equals("/portal/g/:platform:users/newNode"));
        assertTrue((newNode.getIsVisible()).equals("true"));
        assertTrue((newNode.getVisibility()).equals("VISIBLE"));
        assertTrue((newNode.getIconName()).equals("null"));
        assertTrue((newNode.getDisplayName()).equals("newNode"));
        assertTrue((newNode.getChildren()) == null);
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
        assertTrue(navigation.getPriority().equals("8"));
        assertTrue(navigation.getSiteName().equals("/platform/users"));
        assertTrue(navigation.getSiteType().equals("space"));
        Object[] pagesArray = navigation.getNodes().toArray();
        assertTrue(((Node) pagesArray[0]).getName().equals("mylink"));
        assertTrue(((Node) pagesArray[0]).getUri().equals(REST_API_URL + "/spaces/platform/users/navigation/mylink"));
        assertTrue(((Node) pagesArray[1]).getName().equals("newNode"));
        assertTrue(((Node) pagesArray[1]).getUri().equals(REST_API_URL + "/spaces/platform/users/navigation/newNode"));
        assertTrue(((Node) pagesArray[2]).getName().equals("newNode2"));
        assertTrue(((Node) pagesArray[2]).getUri().equals(REST_API_URL + "/spaces/platform/users/navigation/newNode2"));
        assertTrue(((Node) pagesArray[3]).getName().equals("newNode3"));
        assertTrue(((Node) pagesArray[3]).getUri().equals(REST_API_URL + "/spaces/platform/users/navigation/newNode3"));
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

        //     attributes.put("visibility", "{\"status\" : \"INVISIBLE\"}");
        attributes.put("iconName", "Ticket");
        attributes.put("displayName", "Admin Updated Node");

        restService.updateNode(attributes, "/platform/administrators", "space");
        String node = restService.getNode("administration", "/platform/administrators", "space");
        Node homeNode = jSonParser.nodeParser(node);

        assertTrue((homeNode.getName()).equals("administration"));
        assertTrue((homeNode.getUri()).equals("/portal/g/:platform:administrators/administration"));
        assertTrue((homeNode.getIsVisible()).equals("true"));
        assertTrue((homeNode.getVisibility()).equals("VISIBLE"));
        assertTrue((homeNode.getIconName()).equals("Ticket"));
        assertTrue((homeNode.getDisplayName()).equals("Admin Updated Node"));
        Object[] children = homeNode.getChildren().toArray();
        assertTrue(((Page) children[0]).getName().equals("registry"));
        assertTrue(((Page) children[0]).getURL().equals(REST_API_URL + "/spaces/platform/administrators/navigation/administration/registry"));
        assertTrue(((Page) children[1]).getName().equals("pageManagement"));
        assertTrue(((Page) children[1]).getURL().equals(REST_API_URL + "/spaces/platform/administrators/navigation/administration/pageManagement"));
        assertTrue(((Page) children[2]).getName().equals("servicesManagement"));
        assertTrue(((Page) children[2]).getURL().equals(REST_API_URL + "/spaces/platform/administrators/navigation/administration/servicesManagement"));
        assertTrue(((Page) children[3]).getName().equals("siteRedirects"));
        assertTrue(((Page) children[3]).getURL().equals(REST_API_URL + "/spaces/platform/administrators/navigation/administration/siteRedirects"));
        assertTrue(homeNode.getPage() == null);

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
        
        assertTrue(navigation.getPriority().equals("8"));
        assertTrue(navigation.getSiteName().equals("/platform/users"));
        assertTrue(navigation.getSiteType().equals("space"));

        assertEquals(navigation.getNodes().size(), 1);
    }
}
