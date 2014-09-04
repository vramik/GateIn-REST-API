package org.gatein.rest.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gatein.rest.constants.ConstantsService;
import org.gatein.rest.entity.Site;
import org.gatein.rest.helper.JSonParser;
import org.gatein.rest.service.impl.HelpingService;
import org.gatein.rest.service.impl.RestService;
import org.gatein.rest.service.api.HelpingServiceApi;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.json.simple.parser.ParseException;

/**
 *
 * @author mgottval
 */
public class RestDashboardsTest {

    private HelpingServiceApi helpingService;
    private RestService restService;
    private ConstantsService constantsService;
    private final JSonParser jSonParser = new JSonParser();
    private static final String REST_API_URL = "http:\\/\\/localhost:8080\\/rest\\/private\\/managed-components\\/api";

    @Before
    public void before() {
        helpingService = new HelpingService();
        constantsService = new ConstantsService();
        restService = new RestService(helpingService, constantsService);
    }

    @Test
    public void testAddDashboards() throws ParseException {
        restService.addSite("userDashboard", "dashboard");
        restService.addSite("userDashboard2", "dashboard");
        restService.addSite("userDashboard3", "dashboard");
        restService.addSite("userDashboard4", "dashboard");
        String dashboards = restService.getAllSites("dashboard");
        List<Site> dashSites = jSonParser.sitesParser(dashboards);
        int dashboardsCount = 0;
        for (Site site : dashSites) {
            if (site.getName().equals("userDashboard") || site.getName().equals("userDashboard2") || site.getName().equals("userDashboard3") || site.getName().equals("userDashboard4")) {
                dashboardsCount = dashboardsCount + 1;
            }
        }
        assertEquals(dashboardsCount, 4);
        String addedSite = restService.getSite("userDashboard", "dashboard");
        Site userDashboard = jSonParser.siteParser(addedSite);
        assertTrue(userDashboard.getName().equals("userDashboard"));
        assertTrue(userDashboard.getType().equals("dashboard"));
        assertTrue((userDashboard.getDisplayName()).equals("null"));
        assertTrue(userDashboard.getDescription().equals("null"));
        assertTrue(userDashboard.getSkin().equals("Default"));
        assertTrue(userDashboard.getLocale().equals("en"));
        assertTrue(userDashboard.getAccessPermissions().equals("[\"Everyone\"]"));
        assertTrue(userDashboard.getEditPermissions().equals("[\"userDashboard\"]"));
        assertTrue(userDashboard.getPages().equals("{\"url\":\"" + REST_API_URL + "\\/dashboards\\/userDashboard\\/pages\"}"));
        assertTrue(userDashboard.getNavigation().equals("{\"url\":\"" + REST_API_URL + "\\/dashboards\\/userDashboard\\/navigation\"}"));
        restService.deleteSite("userDashboard", "dashboard");
        restService.deleteSite("userDashboard2", "dashboard");
        restService.deleteSite("userDashboard3", "dashboard");
        restService.deleteSite("userDashboard4", "dashboard");
    }

    @Test
    public void testGetDashboard() throws ParseException {
        restService.addSite("userDashboard", "dashboard");
        restService.addSite("userDashboard2", "dashboard");
        restService.addSite("userDashboard3", "dashboard");
        restService.addSite("userDashboard4", "dashboard");
        String userDashboard3Site = restService.getSite("userDashboard3", "dashboard");
        Site userDashboard3 = jSonParser.siteParser(userDashboard3Site);
        assertTrue(userDashboard3.getName().equals("userDashboard3"));
        assertTrue(userDashboard3.getType().equals("dashboard"));
        assertTrue(userDashboard3.getDisplayName().equals("null"));
        assertTrue(userDashboard3.getDescription().equals("null"));
        assertTrue(userDashboard3.getSkin().equals("Default"));
        assertTrue(userDashboard3.getLocale().equals("en"));
        assertTrue(userDashboard3.getAccessPermissions().equals("[\"Everyone\"]"));
        assertTrue(userDashboard3.getEditPermissions().equals("[\"userDashboard3\"]"));
        assertTrue(userDashboard3.getPages().equals("{\"url\":\"" + REST_API_URL + "\\/dashboards\\/userDashboard3\\/pages\"}"));
        assertTrue(userDashboard3.getNavigation().equals("{\"url\":\"" + REST_API_URL + "\\/dashboards\\/userDashboard3\\/navigation\"}"));

        String userDashboardSite = restService.getSite("userDashboard2", "dashboard");
        Site userDashboard = jSonParser.siteParser(userDashboardSite);
        assertTrue(userDashboard.getName().equals("userDashboard2"));
        assertTrue(userDashboard.getType().equals("dashboard"));
//        assertNull(userDashboard.getDisplayName());
        assertTrue(userDashboard.getDescription().equals("null"));
        assertTrue(userDashboard.getSkin().equals("Default"));
        assertTrue(userDashboard.getLocale().equals("en"));
        assertTrue(userDashboard.getAccessPermissions().equals("[\"Everyone\"]"));
        assertTrue(userDashboard.getEditPermissions().equals("[\"userDashboard2\"]"));
        assertTrue(userDashboard.getPages().equals("{\"url\":\"" + REST_API_URL + "\\/dashboards\\/userDashboard2\\/pages\"}"));
        assertTrue(userDashboard.getNavigation().equals("{\"url\":\"" + REST_API_URL + "\\/dashboards\\/userDashboard2\\/navigation\"}"));
        restService.deleteSite("userDashboard", "dashboard");
        restService.deleteSite("userDashboard2", "dashboard");
        restService.deleteSite("userDashboard3", "dashboard");
        restService.deleteSite("userDashboard4", "dashboard");

    }

    @Test
    public void getNonexistingDashboard() {
        String rootSite = restService.getSite("nonDashboard", "dashboard");
        assertTrue(rootSite.contains("Site not found"));
    }

    @Test
    public void testUpdateDashboard() throws ParseException {
        restService.addSite("userDashboard", "dashboard");
        restService.addSite("userDashboard2", "dashboard");
        restService.addSite("userDashboard3", "dashboard");
        restService.addSite("userDashboard4", "dashboard");
        Site rootDashboard;
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("name", "userDashboard");
        attributes.put("type", "dashboard");
        attributes.put("displayName", "UpdatedUserDashboard");
        attributes.put("description", "Description of userDashboard Updated");
        attributes.put("locale", "fr");
        attributes.put("access-permissions", "[\"member:/platform/users\"]");
        attributes.put("edit-permissions", "[\"*:/platform/administrators\"]");
        restService.updateSite(attributes);
        String site = restService.getSite("userDashboard", "dashboard");
        rootDashboard = jSonParser.siteParser(site);
        assertTrue((rootDashboard.getName()).equals("userDashboard"));
        assertTrue((rootDashboard.getType()).equals("dashboard"));
        assertTrue((rootDashboard.getDisplayName()).equals("UpdatedUserDashboard"));
        assertTrue((rootDashboard.getDescription()).equals("Description of userDashboard Updated"));
        assertTrue((rootDashboard.getSkin()).equals("Default"));
        assertTrue((rootDashboard.getLocale()).equals("fr"));
        assertTrue((rootDashboard.getAccessPermissions()).equals("[\"member:\\/platform\\/users\"]"));
        assertTrue((rootDashboard.getEditPermissions()).equals("[\"*:\\/platform\\/administrators\"]"));
        assertTrue((rootDashboard.getPages()).equals("{\"url\":\"" + REST_API_URL + "\\/dashboards\\/userDashboard\\/pages\"}"));
        assertTrue((rootDashboard.getNavigation()).equals("{\"url\":\"" + REST_API_URL + "\\/dashboards\\/userDashboard\\/navigation\"}"));
        restService.deleteSite("userDashboard", "dashboard");
        restService.deleteSite("userDashboard2", "dashboard");
        restService.deleteSite("userDashboard3", "dashboard");
        restService.deleteSite("userDashboard4", "dashboard");
    }

    @Test
    public void testDeleteDashboards() {
        restService.addSite("userDashboard", "dashboard");
        restService.addSite("userDashboard2", "dashboard");
        restService.addSite("userDashboard3", "dashboard");
        restService.addSite("userDashboard4", "dashboard");
        restService.deleteSite("userDashboard", "dashboard");
        restService.deleteSite("userDashboard2", "dashboard");
        restService.deleteSite("userDashboard3", "dashboard");
        restService.deleteSite("userDashboard4", "dashboard");
    }
}
