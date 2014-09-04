package org.gatein.rest.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gatein.rest.constants.ConstantsService;
import org.gatein.rest.constants.RestConstants;
import org.gatein.rest.entity.Site;
import org.gatein.rest.helper.JSonParser;
import org.gatein.rest.service.api.HelpingServiceApi;
import org.gatein.rest.service.impl.HelpingService;
import org.gatein.rest.service.impl.RestService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.json.simple.parser.ParseException;

/**
 *
 * @author mgottval
 */
public class RestSpacesTest {

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
    public void testGetSpaces() throws ParseException {
        System.out.println("**testGetSpaces**");
        String spaces = restService.getAllSites("space");
        List<Site> spaceSites = jSonParser.sitesParser(spaces);
        Object[] pagesArray = spaceSites.toArray();
        assertEquals(pagesArray.length, 4);
        assertTrue(((Site) pagesArray[0]).getName().equals("/platform/guests"));
        assertTrue(((Site) pagesArray[0]).getType().equals("space"));
        assertTrue(((Site) pagesArray[0]).getNavigation().equals("" + RestConstants.REST_API_URL + "/spaces/platform/guests"));
        assertTrue(((Site) pagesArray[1]).getName().equals("/organization/management/executive-board"));
        assertTrue(((Site) pagesArray[1]).getType().equals("space"));
        assertTrue(((Site) pagesArray[1]).getNavigation().equals("" + RestConstants.REST_API_URL + "/spaces/organization/management/executive-board"));
        assertTrue(((Site) pagesArray[2]).getName().equals("/platform/administrators"));
        assertTrue(((Site) pagesArray[2]).getType().equals("space"));
        assertTrue(((Site) pagesArray[2]).getNavigation().equals("" + RestConstants.REST_API_URL + "/spaces/platform/administrators"));
        assertTrue(((Site) pagesArray[3]).getName().equals("/platform/users"));
        assertTrue(((Site) pagesArray[3]).getType().equals("space"));
        assertTrue(((Site) pagesArray[3]).getNavigation().equals("" + RestConstants.REST_API_URL + "/spaces/platform/users"));
    }

    @Test
    public void testGetGuestsSite() throws ParseException {
        System.out.println("**testGetGuestsSite**");
        String site = restService.getSite("/platform/guests", "space");
        assertNotNull(site);
        Site guests = jSonParser.siteParser(site);
        assertTrue((guests.getName()).equals("/platform/guests"));
        assertTrue((guests.getType()).equals("space"));
        assertTrue((guests.getDisplayName()).equals("null"));
        assertTrue((guests.getDescription()).equals("null"));
        assertTrue((guests.getSkin()).equals("Default"));
        assertTrue((guests.getLocale()).equals("en"));
        assertTrue((guests.getAccessPermissions()).equals("[\"*:\\/platform\\/guests\"]"));
        assertTrue((guests.getEditPermissions()).equals("[\"manager:\\/platform\\/guests\"]"));
        assertTrue((guests.getPages()).equals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/guests\\/pages\"}"));
        assertTrue((guests.getNavigation()).equals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/guests\\/navigation\"}"));
    }

    @Test
    public void testGetExecutiveBoardSite() throws ParseException {
        System.out.println("**testGetExecutiveBoardSite**");
        String site = restService.getSite("/organization/management/executive-board", "space");
        assertNotNull(site);
        Site executive = jSonParser.siteParser(site);
        assertTrue((executive.getName()).equals("/organization/management/executive-board"));
        assertTrue((executive.getType()).equals("space"));
        assertTrue((executive.getDisplayName()).equals("null"));
        assertTrue((executive.getDescription()).equals("null"));
        assertTrue((executive.getSkin()).equals("Default"));
        assertTrue((executive.getLocale()).equals("en"));
        assertTrue((executive.getAccessPermissions()).equals("[\"*:\\/organization\\/management\\/executive-board\"]"));
        assertTrue((executive.getEditPermissions()).equals("[\"manager:\\/organization\\/management\\/executive-board\"]"));
        assertTrue((executive.getPages()).equals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/organization\\/management\\/executive-board\\/pages\"}"));
        assertTrue((executive.getNavigation()).equals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/organization\\/management\\/executive-board\\/navigation\"}"));
    }

    @Test
    public void testGetAdministratorsSite() throws ParseException {
        System.out.println("**testGetAdministratorsSite**");
        String site = restService.getSite("/platform/administrators", "space");
        assertNotNull(site);
        Site administrators = jSonParser.siteParser(site);
        assertTrue((administrators.getName()).equals("/platform/administrators"));
        assertTrue((administrators.getType()).equals("space"));
        assertTrue((administrators.getDisplayName()).equals("null"));
        assertTrue((administrators.getDescription()).equals("null"));
        assertTrue((administrators.getSkin()).equals("Default"));
        assertTrue((administrators.getLocale()).equals("en"));
        assertTrue((administrators.getAccessPermissions()).equals("[\"*:\\/platform\\/administrators\"]"));
        assertTrue((administrators.getEditPermissions()).equals("[\"manager:\\/platform\\/administrators\"]"));
        assertTrue((administrators.getPages()).equals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/administrators\\/pages\"}"));
        assertTrue((administrators.getNavigation()).equals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/administrators\\/navigation\"}"));

    }

    @Test
    public void testGetUsersSite() throws ParseException {
        System.out.println("**testGetUsersSite**");
        String site = restService.getSite("/platform/users", "space");
        assertNotNull(site);
        Site users = jSonParser.siteParser(site);
        assertTrue((users.getName()).equals("/platform/users"));
        assertTrue((users.getType()).equals("space"));
        assertTrue(users.getDisplayName().equals("null"));
        assertTrue((users.getDescription()).equals("null"));
        assertTrue((users.getSkin()).equals("Default"));
        assertTrue((users.getLocale()).equals("en"));
        assertTrue((users.getAccessPermissions()).equals("[\"*:\\/platform\\/users\"]"));
        assertTrue((users.getEditPermissions()).equals("[\"manager:\\/platform\\/users\"]"));
        assertTrue((users.getPages()).equals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/users\\/pages\"}"));
        assertTrue((users.getNavigation()).equals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/users\\/navigation\"}"));

    }

    @Test
    public void testAddSpaces() throws ParseException {
        System.out.println("**testAddSpaces**");
        restService.addSite("/platform/members", "space");
        restService.addSite("/platform/managers", "space");
        String dashboards = restService.getEmptySites("space");
        List<Site> spaceSites = jSonParser.sitesParser(dashboards);
        int dashboardsCount = 0;
        for (Site site : spaceSites) {
            if (site.getName().equals("/platform/members") || site.getName().equals("/platform/managers")) {
                dashboardsCount = dashboardsCount + 1;
            }
        }
        assertEquals(dashboardsCount, 2);
        String addedSite = restService.getSite("/platform/members", "space");
        Site newSpace = jSonParser.siteParser(addedSite);
        assertTrue(newSpace.getName().equals("/platform/members"));
        assertTrue(newSpace.getType().equals("space"));
        assertTrue(newSpace.getDisplayName().equals("null"));
        assertTrue(newSpace.getDescription().equals("null"));
        assertTrue(newSpace.getSkin().equals("Default"));
        assertTrue(newSpace.getLocale().equals("en"));
        assertTrue(newSpace.getAccessPermissions().equals("[\"*:\\/platform\\/members\"]"));
        assertTrue(newSpace.getEditPermissions().equals("[\"manager:\\/platform\\/members\"]"));
        assertTrue(newSpace.getPages().equals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/members\\/pages\"}"));
        assertTrue(newSpace.getNavigation().equals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/members\\/navigation\"}"));
        restService.deleteSite("/platform/members", "space");
        restService.deleteSite("/platform/managers", "space");
    }

    @Test
    public void getNonExistingSpace() {
        System.out.println("**getNonExistingSpace**");
        String nonSite = restService.getSite("nonSpace", "space");
        assertTrue(nonSite.contains("Site not found"));
    }

    @Test
    public void testUpdateSpace() throws ParseException {
        System.out.println("**testUpdateSpace**");
        restService.addSite("/platform/members", "space");
        restService.addSite("/platform/managers", "space");
        Site usersSpace;
        Map<String, String> attributes = new HashMap<>();
        attributes.put("name", "/platform/users");
        attributes.put("type", "space");
        attributes.put("displayName", "UpdatedUsersSpace");
        attributes.put("description", "Description of Users Space Updated");
        attributes.put("locale", "fr");
        attributes.put("access-permissions", "[\"member:/platform/users\"]");
        attributes.put("edit-permissions", "[\"manager:/platform/administrators\"]");
        restService.updateSite(attributes);
        String site = restService.getSite("/platform/users", "space");
        System.out.println(site + "----------------");
        usersSpace = jSonParser.siteParser(site);
        assertTrue((usersSpace.getName()).equals("/platform/users"));
        assertTrue((usersSpace.getType()).equals("space"));
        assertTrue((usersSpace.getDisplayName()).equals("UpdatedUsersSpace"));
        assertTrue((usersSpace.getDescription()).equals("Description of Users Space Updated"));
        assertTrue((usersSpace.getSkin()).equals("Default"));
        assertTrue((usersSpace.getLocale()).equals("fr"));
        assertTrue((usersSpace.getAccessPermissions()).equals("[\"member:\\/platform\\/users\"]"));
        assertTrue((usersSpace.getEditPermissions()).equals("[\"manager:\\/platform\\/administrators\"]"));
        assertTrue((usersSpace.getPages()).equals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/users\\/pages\"}"));
        assertTrue((usersSpace.getNavigation()).equals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/users\\/navigation\"}"));

        attributes.clear();
        attributes.put("name", "/platform/users");
        attributes.put("type", "space");
        attributes.put("displayName", "null");
        attributes.put("description", "null");
        attributes.put("access-permissions", "[\"*:/platform/users\"]");
        attributes.put("edit-permissions", "[\"manager:/platform/users\"]");
        attributes.put("locale", "en");
        restService.updateSite(attributes);
        usersSpace = jSonParser.siteParser(restService.getSite("/platform/users", "space"));
        assertTrue((usersSpace.getName()).equals("/platform/users"));
        assertTrue((usersSpace.getType()).equals("space"));
        assertTrue((usersSpace.getDisplayName()).equals("null"));
        assertTrue((usersSpace.getDescription()).equals("null"));
        assertTrue((usersSpace.getSkin()).equals("Default"));
        assertTrue((usersSpace.getLocale()).equals("en"));
        assertTrue((usersSpace.getAccessPermissions()).equals("[\"*:\\/platform\\/users\"]"));
        assertTrue((usersSpace.getEditPermissions()).equals("[\"manager:\\/platform\\/users\"]"));
        assertTrue((usersSpace.getPages()).equals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/users\\/pages\"}"));
        assertTrue((usersSpace.getNavigation()).equals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/users\\/navigation\"}"));

        attributes.clear();
        attributes.put("name", "/platform/members");
        attributes.put("type", "space");
        attributes.put("displayName", "MemberSpaceUpdated");
        attributes.put("description", "Member Space Description Updated");
        attributes.put("access-permissions", "[\"*:/platform/users\"]");
        attributes.put("edit-permissions", "[\"*:/platform/administrators\"]");
        attributes.put("locale", "it");
        restService.updateSite(attributes);
        usersSpace = jSonParser.siteParser(restService.getSite("/platform/members", "space"));
        assertTrue((usersSpace.getName()).equals("/platform/members"));
        assertTrue((usersSpace.getType()).equals("space"));
        assertTrue((usersSpace.getDisplayName()).equals("MemberSpaceUpdated"));
        assertTrue((usersSpace.getDescription()).equals("Member Space Description Updated"));
        assertTrue((usersSpace.getSkin()).equals("Default"));
        assertTrue((usersSpace.getLocale()).equals("it"));
        assertTrue((usersSpace.getAccessPermissions()).equals("[\"*:\\/platform\\/users\"]"));
        assertTrue((usersSpace.getEditPermissions()).equals("[\"*:\\/platform\\/administrators\"]"));
        assertTrue((usersSpace.getPages()).equals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/members\\/pages\"}"));
        assertTrue((usersSpace.getNavigation()).equals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/members\\/navigation\"}"));

        attributes.clear();
        attributes.put("name", "/platform/members");
        attributes.put("type", "space");
        attributes.put("displayName", "null");
        attributes.put("description", "null");
        attributes.put("access-permissions", "[\"*:/platform/members\"]");
        attributes.put("edit-permissions", "[\"manager:/platform/members\"]");
        attributes.put("locale", "en");
        restService.updateSite(attributes);
        usersSpace = jSonParser.siteParser(restService.getSite("/platform/members", "space"));
        assertTrue((usersSpace.getName()).equals("/platform/members"));
        assertTrue((usersSpace.getType()).equals("space"));
        assertTrue((usersSpace.getDisplayName()).equals("null"));
        assertTrue((usersSpace.getDescription()).equals("null"));
        assertTrue((usersSpace.getSkin()).equals("Default"));
        assertTrue((usersSpace.getLocale()).equals("en"));
        assertTrue((usersSpace.getAccessPermissions()).equals("[\"*:\\/platform\\/members\"]"));
        assertTrue((usersSpace.getEditPermissions()).equals("[\"manager:\\/platform\\/members\"]"));
        assertTrue((usersSpace.getPages()).equals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/members\\/pages\"}"));
        assertTrue((usersSpace.getNavigation()).equals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/members\\/navigation\"}"));
        restService.deleteSite("/platform/members", "space");
        restService.deleteSite("/platform/managers", "space");
    }

    @Test
    public void testDeleteSpaces() {
        System.out.println("**testDeleteSpaces**");
        restService.addSite("/platform/members", "space");
        restService.addSite("/platform/managers", "space");
        restService.deleteSite("/platform/members", "space");
        restService.deleteSite("/platform/managers", "space");
    }
}
