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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author mgottval
 */
public class RestSpacesTest {

    private RestService restService;
    private final JSonParser jSonParser = new JSonParser();

    @Before
    public void before() {
        restService = new RestService();
    }

    @Test
    public void testGetSpaces() throws ParseException {
        System.out.println("**testGetSpaces**");
        String spaces = restService.getAllSites("space");
        List<Site> spaceSites = jSonParser.sitesParser(spaces);

        assertEquals(4, spaceSites.size());
        assertEquals("/platform/guests", spaceSites.get(0).getName());
        assertEquals("space", spaceSites.get(0).getName());
        assertEquals(REST_API_URL + "/spaces/platform/guests", spaceSites.get(0).getNavigation());
        assertEquals("/organization/management/executive-board", spaceSites.get(1).getName());
        assertEquals("space", spaceSites.get(1).getName());
        assertEquals(REST_API_URL + "/spaces/organization/management/executive-board", spaceSites.get(1).getNavigation());
        assertEquals("/platform/administrators", spaceSites.get(2).getName());
        assertEquals("space", spaceSites.get(2).getName());
        assertEquals(REST_API_URL + "/spaces/platform/administrators", spaceSites.get(2).getNavigation());
        assertEquals("/platform/users", spaceSites.get(3).getName());
        assertEquals("space", spaceSites.get(3).getName());
        assertEquals(REST_API_URL + "/spaces/platform/users", spaceSites.get(3).getNavigation());
    }

    @Test
    public void testGetGuestsSite() throws ParseException {
        System.out.println("**testGetGuestsSite**");
        String site = restService.getSite("/platform/guests", "space");
        assertNotNull(site);
        Site guests = jSonParser.siteParser(site);
        
        assertEquals("/platform/guests", guests.getName());
        assertEquals("space", guests.getType());
        assertEquals("null", guests.getDisplayName());
        assertEquals("null", guests.getDescription());
        assertEquals("Default", guests.getSkin());
        assertEquals("en", guests.getLocale());
        assertEquals("[\"*:\\/platform\\/guests\"]", guests.getAccessPermissions());
        assertEquals("[\"manager:\\/platform\\/guests\"]", guests.getEditPermissions());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/guests\\/pages\"}", guests.getPages());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/guests\\/navigation\"}", guests.getNavigation());
    }

    @Test
    public void testGetExecutiveBoardSite() throws ParseException {
        System.out.println("**testGetExecutiveBoardSite**");
        String site = restService.getSite("/organization/management/executive-board", "space");
        assertNotNull(site);
        Site executive = jSonParser.siteParser(site);
        
        assertEquals("/organization/management/executive-board", executive.getName());
        assertEquals("space", executive.getType());
        assertEquals("null", executive.getDisplayName());
        assertEquals("null", executive.getDescription());
        assertEquals("Default", executive.getSkin());
        assertEquals("en", executive.getLocale());
        assertEquals("[\"*:\\/organization\\/management\\/executive-board\"]", executive.getAccessPermissions());
        assertEquals("[\"manager:\\/organization\\/management\\/executive-board\"]", executive.getEditPermissions());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/organization\\/management\\/executive-board\\/pages\"}", executive.getPages());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/organization\\/management\\/executive-board\\/navigation\"}", executive.getNavigation());
    }

    @Test
    public void testGetAdministratorsSite() throws ParseException {
        System.out.println("**testGetAdministratorsSite**");
        String site = restService.getSite("/platform/administrators", "space");
        assertNotNull(site);
        Site administrators = jSonParser.siteParser(site);
        
        assertEquals("/platform/administrators", administrators.getName());
        assertEquals("space", administrators.getType());
        assertEquals("null", administrators.getDisplayName());
        assertEquals("null", administrators.getDescription());
        assertEquals("Default", administrators.getSkin());
        assertEquals("en", administrators.getLocale());
        assertEquals("[\"*:\\/platform\\/administrators\"]", administrators.getAccessPermissions());
        assertEquals("[\"manager:\\/platform\\/administrators\"]", administrators.getEditPermissions());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/administrators\\/pages\"}", administrators.getPages());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/administrators\\/navigation\"}", administrators.getNavigation());
    }

    @Test
    public void testGetUsersSite() throws ParseException {
        System.out.println("**testGetUsersSite**");
        String site = restService.getSite("/platform/users", "space");
        assertNotNull(site);
        Site users = jSonParser.siteParser(site);
        
        assertEquals("/platform/users", users.getName());
        assertEquals("space", users.getType());
        assertEquals("null", users.getDisplayName());
        assertEquals("null", users.getDescription());
        assertEquals("Default", users.getSkin());
        assertEquals("en", users.getLocale());
        assertEquals("[\"*:\\/platform\\/users\"]", users.getAccessPermissions());
        assertEquals("[\"manager:\\/platform\\/users\"]", users.getEditPermissions());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/users\\/pages\"}", users.getPages());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/users\\/navigation\"}", users.getNavigation());
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
                dashboardsCount++;
            }
        }
        assertEquals(2, dashboardsCount);
        String addedSite = restService.getSite("/platform/members", "space");
        Site newSpace = jSonParser.siteParser(addedSite);
        
        assertEquals("/platform/members", newSpace.getName());
        assertEquals("space", newSpace.getType());
        assertEquals("null", newSpace.getDisplayName());
        assertEquals("null", newSpace.getDescription());
        assertEquals("Default", newSpace.getSkin());
        assertEquals("en", newSpace.getLocale());
        assertEquals("[\"*:\\/platform\\/members\"]", newSpace.getAccessPermissions());
        assertEquals("[\"manager:\\/platform\\/members\"]", newSpace.getEditPermissions());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/members\\/pages\"}", newSpace.getPages());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/members\\/navigation\"}", newSpace.getNavigation());

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
        Site usersSpace = jSonParser.siteParser(site);
        
        assertEquals("/platform/users", usersSpace.getName());
        assertEquals("space", usersSpace.getType());
        assertEquals("UpdatedUsersSpace", usersSpace.getDisplayName());
        assertEquals("Description of Users Space Updated", usersSpace.getDescription());
        assertEquals("Default", usersSpace.getSkin());
        assertEquals("fr", usersSpace.getLocale());
        assertEquals("[\"member:\\/platform\\/users\"]", usersSpace.getAccessPermissions());
        assertEquals("[\"manager:\\/platform\\/administrators\"]", usersSpace.getEditPermissions());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/users\\/pages\"}", usersSpace.getPages());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/users\\/navigation\"}", usersSpace.getNavigation());
        
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
        
        assertEquals("/platform/members", usersSpace.getName());
        assertEquals("space", usersSpace.getType());
        assertEquals("null", usersSpace.getDisplayName());
        assertEquals("null", usersSpace.getDescription());
        assertEquals("Default", usersSpace.getSkin());
        assertEquals("en", usersSpace.getLocale());
        assertEquals("[\"*:\\/platform\\/users\"]", usersSpace.getAccessPermissions());
        assertEquals("[\"manager:\\/platform\\/users\"]", usersSpace.getEditPermissions());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/users\\/pages\"}", usersSpace.getPages());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/users\\/navigation\"}", usersSpace.getNavigation());
        
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
        
        assertEquals("/platform/members", usersSpace.getName());
        assertEquals("space", usersSpace.getType());
        assertEquals("MemberSpaceUpdated", usersSpace.getDisplayName());
        assertEquals("Member Space Description Updated", usersSpace.getDescription());
        assertEquals("Default", usersSpace.getSkin());
        assertEquals("it", usersSpace.getLocale());
        assertEquals("[\"*:\\/platform\\/users\"]", usersSpace.getAccessPermissions());
        assertEquals("[\"manager:\\/platform\\/administrators\"]", usersSpace.getEditPermissions());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/members\\/pages\"}", usersSpace.getPages());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/members\\/navigation\"}", usersSpace.getNavigation());

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
        
        assertEquals("/platform/members", usersSpace.getName());
        assertEquals("space", usersSpace.getType());
        assertEquals("null", usersSpace.getDisplayName());
        assertEquals("null", usersSpace.getDescription());
        assertEquals("Default", usersSpace.getSkin());
        assertEquals("en", usersSpace.getLocale());
        assertEquals("[\"*:\\/platform\\/members\"]", usersSpace.getAccessPermissions());
        assertEquals("[\"manager:\\/platform\\/members\"]", usersSpace.getEditPermissions());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/members\\/pages\"}", usersSpace.getPages());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/spaces\\/platform\\/members\\/navigation\"}", usersSpace.getNavigation());
        
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
