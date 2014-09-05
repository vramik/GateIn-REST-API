package org.gatein.rest.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gatein.rest.entity.Page;
import org.gatein.rest.helper.JSonParser;
import org.gatein.rest.service.impl.RestService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import static org.gatein.rest.constants.RestConstants.*;
import org.json.simple.parser.ParseException;

/**
 *
 * @author mgottval
 */
public class RestSpacesPagesTest {

    private RestService restService;
    private final JSonParser jSonParser = new JSonParser();

    @Before
    public void before() {
        restService = new RestService();
    }

    @Test
    public void testGetSpacePages() throws ParseException {
        System.out.println("**testGetSpacePages**");
        String classicPages = restService.getSitePages("/platform/guests", "space");
        
        List<Page> pages = jSonParser.pagesParser(classicPages);
        assertEquals("link", pages.get(0).getName());
        assertEquals("/platform/guests", pages.get(0).getSiteName());
        assertEquals("space", pages.get(0).getSiteType());
        assertEquals(REST_API_URL + "/spaces/platform/guests/pages/link", pages.get(0).getURL());
        assertEquals("sitemap", pages.get(1).getName());
        assertEquals("/platform/guests", pages.get(1).getSiteName());
        assertEquals("space", pages.get(1).getSiteType());
        assertEquals(REST_API_URL + "/spaces/platform/guests/pages/sitemap", pages.get(1).getURL());
        
        String adminPages = restService.getSitePages("/platform/administrators", "space");
        pages.clear();
        pages = jSonParser.pagesParser(adminPages);

        assertEquals(7, pages.size());
        for (Page page : pages) {
            assertEquals("/platform/administrators", page.getSiteName());
            assertEquals("space", page.getSiteType());
            
            switch (page.getName()) {
                case "newAccount":
                    assertEquals(REST_API_URL + "/spaces/platform/administrators/pages/newAccount", page.getURL());
                    break;
                case "communityManagement":
                    assertEquals(REST_API_URL + "/spaces/platform/administrators/pages/communityManagement", page.getURL());
                    break;
                case "registry":
                    assertEquals(REST_API_URL + "/spaces/platform/administrators/pages/registry", page.getURL());
                    break;
                case "pageManagement":
                    assertEquals(REST_API_URL + "/spaces/platform/administrators/pages/pageManagement", page.getURL());
                    break;
                case "servicesManagement":
                    assertEquals(REST_API_URL + "/spaces/platform/administrators/pages/servicesManagement", page.getURL());
                    break;
                case "siteRedirects":
                    assertEquals(REST_API_URL + "/spaces/platform/administrators/pages/siteRedirects", page.getURL());
                    break;
                case "wsrpConfiguration":
                    assertEquals(REST_API_URL + "/spaces/platform/administrators/pages/wsrpConfiguration", page.getURL());
                    break;
            }
        }

        String execPages = restService.getSitePages("/organization/management/executive-board", "space");
        pages.clear();
        pages = jSonParser.pagesParser(execPages);

        assertEquals("newStaff", pages.get(0).getName());
        assertEquals("/organization/management/executive-board", pages.get(0).getSiteName());
        assertEquals("space", pages.get(0).getSiteType());
        assertEquals(REST_API_URL + "/spaces/organization/management/executive-board/pages/newStaff", pages.get(0).getURL());
        assertEquals("management", pages.get(1).getName());
        assertEquals("/organization/management/executive-board", pages.get(1).getSiteName());
        assertEquals("space", pages.get(1).getSiteType());
        assertEquals(REST_API_URL + "/spaces/organization/management/executive-board/pages/management", pages.get(1).getURL());

        String usersPages = restService.getSitePages("/platform/users", "space");
        pages.clear();
        pages = jSonParser.pagesParser(usersPages);

        assertEquals("mylink-blog", pages.get(0).getName());
        assertEquals("/platform/users", pages.get(0).getSiteName());
        assertEquals("space", pages.get(0).getSiteType());
        assertEquals(REST_API_URL + "/spaces/platform/users/pages/mylink-blog", pages.get(0).getURL());
        assertEquals("management", pages.get(1).getName());
        assertEquals("/platform/users", pages.get(1).getSiteName());
        assertEquals("space", pages.get(1).getSiteType());
        assertEquals(REST_API_URL + "/spaces/platform/users/pages/mylink-fedora", pages.get(1).getURL());
    }

    @Test//here
    public void testGetPage() throws ParseException {
        System.out.println("**testGetPage**");
        String page = restService.getPage("communityManagement", "platform/administrators", "space");
        Page comunityManagement = jSonParser.pageParser(page);
        assertEquals("communityManagement", comunityManagement.getName());
        assertEquals("Community Management", comunityManagement.getDisplayName());
        assertEquals("null", comunityManagement.getDescription());
        assertEquals("[\"manager:\\/platform\\/administrators\"]", comunityManagement.getAccessPermissions());
        assertEquals("[\"manager:\\/platform\\/administrators\"]", comunityManagement.getEditPermissions());

        String staffPage = restService.getPage("newStaff", "/organization/management/executive-board", "space");
        Page newStaff = jSonParser.pageParser(staffPage);

        assertEquals("newStaff", newStaff.getName());
        assertEquals("New Staff", newStaff.getDisplayName());
        assertEquals("null", newStaff.getDescription());
        assertEquals("[\"*:\\/organization\\/management\\/executive-board\"]", newStaff.getAccessPermissions());
        assertEquals("[\"manager:\\/organization\\/management\\/executive-board\"]", newStaff.getEditPermissions());
    }

    @Test
    public void testAddPageToClassicSite() throws ParseException {
        restService.AddPageToSite("newPage4", "classic", "site");
        restService.AddPageToSite("newPage5", "classic", "site");
        restService.AddPageToSite("newPage3", "classic", "site");
        String classicPages = restService.getSitePages("classic", "site");
        List<Page> pages = jSonParser.pagesParser(classicPages);
        int addedPages = 0;
        for (Page page : pages) {
            if (page.getName().equals("newPage4") || page.getName().equals("newPage5") || page.getName().equals("newPage3")) {
                addedPages++;
            }
        }
        assertEquals(3, addedPages);

        String page = restService.getPage("newPage4", "classic", "site");
        Page newPage4 = jSonParser.pageParser(page);
        
        assertEquals("newPage4", newPage4.getName());
        assertEquals("newPage4", newPage4.getDisplayName());
        assertEquals("null", newPage4.getDescription());
        assertEquals("[\"Everyone\"]", newPage4.getAccessPermissions());
        assertEquals("[\"*:/platform/administrators\"]", newPage4.getEditPermissions());
        
        restService.deletePage("classic", "newPage4", "site");
        restService.deletePage("classic", "newPage5", "site");
        restService.deletePage("classic", "newPage3", "site");
    }
    
    @Test
    public void testAddPageToAdminSite() throws ParseException {
        System.out.println("**testAddPageToAdminSite**");
        restService.AddPageToSite("newPage4", "platform/administrators", "space");
        restService.AddPageToSite("newPage5", "platform/administrators", "space");
        restService.AddPageToSite("newPage3", "platform/administrators", "space");
        String adminPages = restService.getSitePages("platform/administrators", "space");
        List<Page> pages = jSonParser.pagesParser(adminPages);
        int addedPages = 0;
        for (Page page : pages) {
            if (page.getName().equals("newPage4") || page.getName().equals("newPage5") || page.getName().equals("newPage3")) {
                addedPages++;
            }
        }
        assertEquals(3, addedPages);
        String page = restService.getPage("newPage4", "platform/administrators", "space");
        Page newPage4 = jSonParser.pageParser(page);
        
        assertEquals("newPage4", newPage4.getName());
        assertEquals("newPage4", newPage4.getDisplayName());
        assertEquals("null", newPage4.getDescription());
        assertEquals("[\"Everyone\"]", newPage4.getAccessPermissions());
        assertEquals("[\"*:/platform/administrators\"]", newPage4.getEditPermissions());

        restService.deletePage("platform/administrators", "newPage4", "space");
        restService.deletePage("platform/administrators", "newPage5", "space");
        restService.deletePage("platform/administrators", "newPage3", "space");
    }

    @Test
    public void testGetNonExistingPage() {
        System.out.println("**testGetNonExistingPage**");
        String getResult = restService.getPage("nonPage", "platform/administrators", "space");
        assertTrue(getResult.contains("does not exist"));
    }

    @Test
    public void testUpdatePage() throws ParseException {
        System.out.println("**testUpdatePage**");
        restService.AddPageToSite("newPage4", "platform/administrators", "space");
        Map<String, String> attributeMap = new HashMap<>();
        attributeMap.put("name", "newPage4");
        attributeMap.put("siteName", "platform/administrators");
        attributeMap.put("description", "Updated description");
        attributeMap.put("displayName", "UpdatedNewPage");
        attributeMap.put("access-permissions", "[\"*:/platform/administrators\"]");
        attributeMap.put("edit-permissions", "[\"member:/platform/users\"]");
        restService.updatePage(attributeMap, "space");
        String page = restService.getPage("newPage4", "platform/administrators", "space");
        Page newPage4 = jSonParser.pageParser(page);

        assertEquals("newPage4", newPage4.getName());
        assertEquals("UpdatedNewPage", newPage4.getDisplayName());
        assertEquals("Updated description", newPage4.getDescription());
        assertEquals("[\"*:\\/platform\\/administrators\"]", newPage4.getAccessPermissions());
        assertEquals("[\"member:\\/platform\\/users\"]", newPage4.getEditPermissions());

        attributeMap.clear();
        attributeMap.put("name", "newPage4");
        attributeMap.put("siteName", "platform/administrators");
        attributeMap.put("description", "null");
        attributeMap.put("displayName", "newPage4");
        attributeMap.put("access-permissions", "[\"member:/platform/users\"]");
        attributeMap.put("edit-permissions", "[\"*:/platform/administrators\"]");
        restService.updatePage(attributeMap, "space");
        String revPage = restService.getPage("newPage4", "platform/administrators", "space");
        Page revNewPage = jSonParser.pageParser(revPage);
        
        assertEquals("newPage4", revNewPage.getName());
        assertEquals("newPage4", revNewPage.getDisplayName());
        assertEquals("null", revNewPage.getDescription());
        assertEquals("[\"member:\\/platform\\/users\"]", revNewPage.getAccessPermissions());
        assertEquals("[\"*:\\/platform\\/administrators\"]", revNewPage.getEditPermissions());

        attributeMap.clear();
        attributeMap.put("name", "newAccount");
        attributeMap.put("siteName", "platform/administrators");
        attributeMap.put("description", "NewAccount updated description");
        attributeMap.put("displayName", "UpdatedNewAccount");
        attributeMap.put("access-permissions", "[\"manager:/platform/administrators\"]");
        attributeMap.put("edit-permissions", "[\"manager:/platform/administrators\"]");
        restService.updatePage(attributeMap, "space");
        String home = restService.getPage("newAccount", "platform/administrators", "space");
        Page homepage = jSonParser.pageParser(home);
        
        assertEquals("newAccount", homepage.getName());
        assertEquals("UpdatedNewAccount", homepage.getDisplayName());
        assertEquals("NewAccount updated description", homepage.getDescription());
        assertEquals("[\"manager:\\/platform\\/administrators\"]", homepage.getAccessPermissions());
        assertEquals("[\"manager:\\/platform\\/administrators\"]", homepage.getEditPermissions());

        attributeMap.clear();
        attributeMap.put("name", "newAccount");
        attributeMap.put("siteName", "platform/administrators");
        attributeMap.put("description", "null");
        attributeMap.put("displayName", "New Account");
        attributeMap.put("access-permissions", "[\"*:/platform/users\"]");
        attributeMap.put("edit-permissions", "[\"*:/platform/guests\"]");
        restService.updatePage(attributeMap, "space");
        String homeRestore = restService.getPage("newAccount", "platform/administrators", "space");
        Page homepageRestore = jSonParser.pageParser(homeRestore);
        
        assertEquals("newAccount", homepageRestore.getName());
        assertEquals("New Account", homepageRestore.getDisplayName());
        assertEquals("null", homepageRestore.getDescription());
        assertEquals("[\"*:\\/platform\\/users\"]", homepageRestore.getAccessPermissions());
        assertEquals("[\"*:\\/platform\\/guests\"]", homepageRestore.getEditPermissions());

        restService.deletePage("platform/administrators", "newPage4", "space");
    }

    @Test
    public void testDeletePages() throws ParseException {
        System.out.println("**testDeletePages**");
        restService.AddPageToSite("newPage4", "platform/administrators", "space");
        restService.AddPageToSite("newPage5", "platform/administrators", "space");
        restService.AddPageToSite("newPage3", "platform/administrators", "space");
        restService.deletePage("platform/administrators", "newPage4", "space");
        restService.deletePage("platform/administrators", "newPage5", "space");
        restService.deletePage("platform/administrators", "newPage3", "space");
        String classicPages = restService.getSitePages("platform/administrators", "space");
        List<Page> pages = jSonParser.pagesParser(classicPages);
        int addedPages = 0;
        for (Page page : pages) {
            if (page.getName().equals("newPage4") || page.getName().equals("newPage5") || page.getName().equals("newPage3")) {
                addedPages++;
            }
        }
        assertEquals(0, addedPages);
    }
}
