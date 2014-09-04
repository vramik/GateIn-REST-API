package org.gatein.rest.api;

import java.util.HashMap;
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
public class RestNodesDashboardsTest {

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
    public void testAddNode() throws ParseException {
        restService.addSite("mary", "dashboard");
        restService.createNode("newNode", "mary", "dashboard");
        restService.createNode("newNode2", "mary", "dashboard");
        restService.createNode("newNode3", "mary", "dashboard");
        String node = restService.getNode("newNode", "mary", "dashboard");
        assertNotNull(node);
        Node newNode = jSonParser.nodeParser(node);
        assertTrue((newNode.getName()).equals("newNode"));
        assertTrue((newNode.getUri()).equals("/portal/u/mary/newNode"));
        assertTrue((newNode.getIsVisible()).equals("true"));
        assertTrue((newNode.getVisibility()).equals("VISIBLE"));
        assertTrue((newNode.getIconName()).equals("null"));
        assertTrue((newNode.getDisplayName()).equals("newNode"));
        assertTrue((newNode.getChildren()) == null);
        assertNull(newNode.getPage());

        String jSonNavigation = restService.getNavigation("dashboard", "mary", false);
        Navigation navigationAdded = jSonParser.navigationParser(jSonNavigation);
        int nodesCount = navigationAdded.getNodes().size();
        assertEquals(4, nodesCount);
        restService.deleteNode("newNode", "mary", "dashboard");
        restService.deleteNode("newNode2", "mary", "dashboard");
        restService.deleteNode("newNode3", "mary", "dashboard");
        restService.deleteSite("mary", "dashboard");
    }

    @Test
    public void testGetNode() throws ParseException {
        restService.addSite("mary", "dashboard");
        String node1 = restService.getNode("Tab_Default", "mary", "dashboard");
        assertNotNull(node1);
        Node site = jSonParser.nodeParser(node1);
        assertTrue((site.getName()).equals("Tab_Default"));
        assertTrue((site.getUri()).equals("/portal/u/mary/Tab_Default"));
        assertTrue((site.getIsVisible()).equals("true"));
        assertTrue((site.getVisibility()).equals("VISIBLE"));
        assertTrue((site.getIconName()).equals("null"));
        assertTrue((site.getDisplayName()).equals("Tab_Default"));
        assertNull(site.getChildren());
        Page page = site.getPage();
        assertTrue(page.getName().equals("Tab_Default"));
        assertTrue(page.getSiteName().equals("mary"));
        assertTrue(page.getSiteType().equals("dashboard"));
        assertTrue(page.getURL().equals(REST_API_URL + "/dashboards/mary/pages/Tab_Default"));
        restService.deleteSite("mary", "dashboard");
    }

    @Test
    public void testNonExistingNode() {
        restService.addSite("mary", "dashboard");
        String node = restService.getNode("non", "mary", "dashboard");
        assertTrue(node.contains("Node /non does not exist"));
        restService.deleteSite("mary", "dashboard");
    }

    @Test
    public void testGetNavigation() throws ParseException {
        restService.addSite("mary", "dashboard");
        restService.createNode("newNode", "mary", "dashboard");
        restService.createNode("newNode2", "mary", "dashboard");
        restService.createNode("newNode3", "mary", "dashboard");
        String navigationString = restService.getNavigation("dashboard", "mary", false);
        Navigation navigation = jSonParser.navigationParser(navigationString);
        assertTrue(navigation.getPriority().equals("3"));
        assertTrue(navigation.getSiteName().equals("mary"));
        assertTrue(navigation.getSiteType().equals("dashboard"));
        Object[] pagesArray = navigation.getNodes().toArray();
        assertTrue(((Node) pagesArray[0]).getName().equals("Tab_Default"));
        assertTrue(((Node) pagesArray[0]).getUri().equals(REST_API_URL + "/dashboards/mary/navigation/Tab_Default"));

        assertTrue(((Node) pagesArray[1]).getName().equals("newNode"));
        assertTrue(((Node) pagesArray[1]).getUri().equals(REST_API_URL + "/dashboards/mary/navigation/newNode"));

        assertTrue(((Node) pagesArray[2]).getName().equals("newNode2"));
        assertTrue(((Node) pagesArray[2]).getUri().equals(REST_API_URL + "/dashboards/mary/navigation/newNode2"));

        assertTrue(((Node) pagesArray[3]).getName().equals("newNode3"));
        assertTrue(((Node) pagesArray[3]).getUri().equals(REST_API_URL + "/dashboards/mary/navigation/newNode3"));
        restService.deleteNode("newNode", "mary", "dashboard");
        restService.deleteNode("newNode2", "mary", "dashboard");
        restService.deleteNode("newNode3", "mary", "dashboard");
        restService.deleteSite("mary", "dashboard");
    }

    @Test
    public void testUpdateNode() throws ParseException {
        restService.addSite("mary", "dashboard");
        restService.createNode("newNode", "mary", "dashboard");
        restService.createNode("newNode2", "mary", "dashboard");
        restService.createNode("newNode3", "mary", "dashboard");
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("name", "newNode");
        attributes.put("type", "dashboard");

        //     attributes.put("visibility", "{\"status\" : \"INVISIBLE\"}");
        attributes.put("iconName", "Ticket");
        attributes.put("displayName", "Updated Node");

        restService.updateNode(attributes, "mary", "dashboard");
        String node = restService.getNode("newNode", "mary", "dashboard");
        Node homeNode = jSonParser.nodeParser(node);

        assertTrue((homeNode.getName()).equals("newNode"));
        assertTrue((homeNode.getUri()).equals("/portal/u/mary/newNode"));
        assertTrue((homeNode.getIsVisible()).equals("true"));
        assertTrue((homeNode.getVisibility()).equals("VISIBLE"));
        assertTrue((homeNode.getIconName()).equals("Ticket"));
        assertTrue((homeNode.getDisplayName()).equals("Updated Node"));
        assertNull(homeNode.getChildren());
        assertNull(homeNode.getPage());


        attributes.clear();
        attributes.put("name", "Tab_Default");
        attributes.put("type", "dashboard");

        //     attributes.put("visibility", "{\"status\" : \"INVISIBLE\"}");
        attributes.put("iconName", "Ticket");
        attributes.put("displayName", "Updated Default Node");

        restService.updateNode(attributes, "mary", "dashboard");
        String defNode = restService.getNode("Tab_Default", "mary", "dashboard");
        Node dNode = jSonParser.nodeParser(defNode);

        assertTrue((dNode.getName()).equals("Tab_Default"));
        assertTrue((dNode.getUri()).equals("/portal/u/mary/Tab_Default"));
        assertTrue((dNode.getIsVisible()).equals("true"));
        assertTrue((dNode.getVisibility()).equals("VISIBLE"));
        assertTrue((dNode.getIconName()).equals("Ticket"));
        assertTrue((dNode.getDisplayName()).equals("Updated Default Node"));
        assertNull(dNode.getChildren());
        Page page = dNode.getPage();
        assertTrue(page.getName().equals("Tab_Default"));
        assertTrue(page.getSiteName().equals("mary"));
        assertTrue(page.getSiteType().equals("dashboard"));
        assertTrue(page.getURL().equals(REST_API_URL + "/dashboards/mary/pages/Tab_Default"));
        restService.deleteNode("newNode", "mary", "dashboard");
        restService.deleteNode("newNode2", "mary", "dashboard");
        restService.deleteNode("newNode3", "mary", "dashboard");
        restService.deleteSite("mary", "dashboard");
    }

    @Test
    public void testDeleteNodes() throws ParseException {
        restService.addSite("mary", "dashboard");
        restService.createNode("newNode", "mary", "dashboard");
        restService.createNode("newNode2", "mary", "dashboard");
        restService.createNode("newNode3", "mary", "dashboard");
        restService.deleteNode("newNode", "mary", "dashboard");
        restService.deleteNode("newNode2", "mary", "dashboard");
        restService.deleteNode("newNode3", "mary", "dashboard");

        String navigationString = restService.getNavigation("dashboard", "mary", false);
        Navigation navigation = jSonParser.navigationParser(navigationString);
        assertTrue(navigation.getPriority().equals("3"));
        assertTrue(navigation.getSiteName().equals("mary"));
        assertTrue(navigation.getSiteType().equals("dashboard"));
        Object[] pagesArray = navigation.getNodes().toArray();

        assertEquals(pagesArray.length, 1);

        restService.deleteSite("mary", "dashboard");
    }
}
