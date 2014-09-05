package org.gatein.rest.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.gatein.rest.constants.RestConstants.REST_API_URL;
import org.gatein.rest.entity.Site;
import org.gatein.rest.helper.JSonParser;
import org.gatein.rest.service.impl.RestService;
import org.json.simple.parser.ParseException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author mgottval
 */
public class RestDashboardsTest {

    private RestService restService;
    private final JSonParser jSonParser = new JSonParser();

    @Before
    public void before() {
        restService = new RestService();
    }

    @Test
    public void testAddDashboards() throws ParseException {
        System.out.println("**testAddDashboards**");
        restService.addSite("userDashboard", "dashboard");
        restService.addSite("userDashboard2", "dashboard");
        restService.addSite("userDashboard3", "dashboard");
        restService.addSite("userDashboard4", "dashboard");
        String dashboards = restService.getAllSites("dashboard");
        List<Site> dashSites = jSonParser.sitesParser(dashboards);
        int dashboardsCount = 0;
        for (Site site : dashSites) {
            if (site.getName().equals("userDashboard") || site.getName().equals("userDashboard2") || site.getName().equals("userDashboard3") || site.getName().equals("userDashboard4")) {
                dashboardsCount++;
            }
        }
        assertEquals(4, dashboardsCount);
        String addedSite = restService.getSite("userDashboard", "dashboard");
        Site userDashboard = jSonParser.siteParser(addedSite);
        assertEquals("userDashboard", userDashboard.getName());
        assertEquals("dashboard", userDashboard.getType());
        assertEquals("null", userDashboard.getDisplayName());
        assertEquals("null", userDashboard.getDescription());
        assertEquals("Default", userDashboard.getSkin());
        assertEquals("en", userDashboard.getLocale());
        assertEquals("[\"Everyone\"]", userDashboard.getAccessPermissions());
        assertEquals("[\"userDashboard\"]", userDashboard.getEditPermissions());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/dashboards\\/userDashboard\\/pages\"}", userDashboard.getPages());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/dashboards\\/userDashboard\\/navigation\"}", userDashboard.getNavigation());
        restService.deleteSite("userDashboard", "dashboard");
        restService.deleteSite("userDashboard2", "dashboard");
        restService.deleteSite("userDashboard3", "dashboard");
        restService.deleteSite("userDashboard4", "dashboard");
    }

    @Test
    public void testGetDashboard() throws ParseException {
        System.out.println("**testGetDashboard**");
        restService.addSite("userDashboard", "dashboard");
        restService.addSite("userDashboard2", "dashboard");
        restService.addSite("userDashboard3", "dashboard");
        restService.addSite("userDashboard4", "dashboard");
        
        String userDashboard3Site = restService.getSite("userDashboard3", "dashboard");
        Site userDashboard3 = jSonParser.siteParser(userDashboard3Site);
        
        assertEquals("userDashboard3", userDashboard3.getName());
        assertEquals("dashboard", userDashboard3.getType());
        assertEquals("null", userDashboard3.getDisplayName());
        assertEquals("null", userDashboard3.getDescription());
        assertEquals("Default", userDashboard3.getSkin());
        assertEquals("en", userDashboard3.getLocale());
        assertEquals("[\"Everyone\"]", userDashboard3.getAccessPermissions());
        assertEquals("[\"userDashboard3\"]", userDashboard3.getEditPermissions());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/dashboards\\/userDashboard3\\/pages\"}", userDashboard3.getPages());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/dashboards\\/userDashboard3\\/navigation\"}", userDashboard3.getNavigation());

        String userDashboardSite = restService.getSite("userDashboard2", "dashboard");
        Site userDashboard2 = jSonParser.siteParser(userDashboardSite);
        
        assertEquals("userDashboard2", userDashboard2.getName());
        assertEquals("dashboard", userDashboard2.getType());
        assertEquals("null", userDashboard2.getDisplayName());
        assertEquals("null", userDashboard2.getDescription());
        assertEquals("Default", userDashboard2.getSkin());
        assertEquals("en", userDashboard2.getLocale());
        assertEquals("[\"Everyone2\"]", userDashboard2.getAccessPermissions());
        assertEquals("[\"userDashboard2\"]", userDashboard2.getEditPermissions());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/dashboards\\/userDashboard2\\/pages\"}", userDashboard2.getPages());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/dashboards\\/userDashboard2\\/navigation\"}", userDashboard2.getNavigation());
        
        restService.deleteSite("userDashboard", "dashboard");
        restService.deleteSite("userDashboard2", "dashboard");
        restService.deleteSite("userDashboard3", "dashboard");
        restService.deleteSite("userDashboard4", "dashboard");
    }

    @Test
    public void getNonExistingDashboard() {
        System.out.println("**getNonExistingDashboard**");
        String rootSite = restService.getSite("nonDashboard", "dashboard");
        assertTrue(rootSite.contains("Site not found"));
    }

    @Test
    public void testUpdateDashboard() throws ParseException {
        System.out.println("**testUpdateDashboard**");
        restService.addSite("userDashboard", "dashboard");
        restService.addSite("userDashboard2", "dashboard");
        restService.addSite("userDashboard3", "dashboard");
        restService.addSite("userDashboard4", "dashboard");

        Map<String, String> attributes = new HashMap<>();
        attributes.put("name", "userDashboard");
        attributes.put("type", "dashboard");
        attributes.put("displayName", "UpdatedUserDashboard");
        attributes.put("description", "Description of userDashboard Updated");
        attributes.put("locale", "fr");
        attributes.put("access-permissions", "[\"member:/platform/users\"]");
        attributes.put("edit-permissions", "[\"*:/platform/administrators\"]");
        restService.updateSite(attributes);

        String site = restService.getSite("userDashboard", "dashboard");
        Site rootDashboard = jSonParser.siteParser(site);
        
        assertEquals("userDashboard", rootDashboard.getName());
        assertEquals("dashboard", rootDashboard.getType());
        assertEquals("UpdatedUserDashboard", rootDashboard.getDisplayName());
        assertEquals("Description of userDashboard Updated", rootDashboard.getDescription());
        assertEquals("Default", rootDashboard.getSkin());
        assertEquals("fr", rootDashboard.getLocale());
        assertEquals("[\"member:\\/platform\\/users\"]", rootDashboard.getAccessPermissions());
        assertEquals("[\"*:\\/platform\\/administrators\"]", rootDashboard.getEditPermissions());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/dashboards\\/userDashboard\\/pages\"}", rootDashboard.getPages());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/dashboards\\/userDashboard\\/navigation\"}", rootDashboard.getNavigation());

        restService.deleteSite("userDashboard", "dashboard");
        restService.deleteSite("userDashboard2", "dashboard");
        restService.deleteSite("userDashboard3", "dashboard");
        restService.deleteSite("userDashboard4", "dashboard");
    }

    @Test
    public void testDeleteDashboards() {
        System.out.println("**testDeleteDashboards**");
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
