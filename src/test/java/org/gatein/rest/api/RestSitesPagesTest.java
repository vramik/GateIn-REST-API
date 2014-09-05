package org.gatein.rest.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gatein.rest.helper.JSonParser;
import org.gatein.rest.entity.Page;
import org.gatein.rest.service.impl.RestService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.gatein.rest.constants.RestConstants.*;
import org.json.simple.parser.ParseException;

/**
 *
 * @author vramik
 */
public class RestSitesPagesTest {

    private RestService restService;
    private final JSonParser jSonParser = new JSonParser();

    @Before
    public void before() {
        restService = new RestService();
    }

    @Test
    public void testGetSitesPages() throws ParseException {
        System.out.println("**testGetSitesPages**");
        String classicPages = restService.getSitePages("classic", "site");
        List<Page> pages = jSonParser.pagesParser(classicPages);
        for (Page page : pages) {
            assertEquals("classic", page.getSiteName());
            assertEquals("site", page.getSiteType());
            
            switch (page.getName()) {
                case "homepage":
                    assertEquals(REST_API_URL + "/sites/classic/pages/homepage", page.getURL());
                    break;
                case "groupnavigation":
                    assertEquals(REST_API_URL + "/sites/classic/pages/groupnavigation", page.getURL());
                    break;
                case "portalnavigation":
                    assertEquals(REST_API_URL + "/sites/classic/pages/portalnavigation", page.getURL());
                    break;
                case "register":
                    assertEquals(REST_API_URL + "/sites/classic/pages/register", page.getURL());
                    break;
                case "sitemap":
                    assertEquals(REST_API_URL + "/sites/classic/pages/sitemap", page.getURL());
                    break;
            }
        }

        String mobilePages = restService.getSitePages("mobile", "site");
        pages.clear();
        pages = jSonParser.pagesParser(mobilePages);
        for (Page page : pages) {
            assertEquals("mobile", page.getSiteName());
            assertEquals("site", page.getSiteType());
            
            switch (page.getName()) {
                case "homepage":
                    assertEquals(REST_API_URL + "/sites/mobile/pages/homepage", page.getURL());
                    break;
                case "groupnavigation":
                    assertEquals(REST_API_URL + "/sites/mobile/pages/groupnavigation", page.getURL());
                    break;
                case "portalnavigation":
                    assertEquals(REST_API_URL + "/sites/mobile/pages/portalnavigation", page.getURL());
                    break;
                case "register":
                    assertEquals(REST_API_URL + "/sites/mobile/pages/register", page.getURL());
                    break;
                case "sitemap":
                    assertEquals(REST_API_URL + "/sites/mobile/pages/sitemap", page.getURL());
                    break;
            }
        }
    }

    @Test
    public void testGetPage() throws ParseException {
        System.out.println("**testGetPage**");
        String page = restService.getPage("homepage", "classic", "site");
        Page homepage = jSonParser.pageParser(page);
        assertEquals("homepage", homepage.getName());
        assertEquals("Home Page", homepage.getDisplayName());
        assertEquals("null", homepage.getDescription());
        assertEquals("[\"Everyone\"]", homepage.getAccessPermissions());
        assertEquals("[\"*:\\/platform\\/administrators\"]", homepage.getEditPermissions());
    }

    @Test
    public void testAddPageToClassicSite() throws ParseException {
        System.out.println("**testAddPageToClassicSite**");
        restService.AddPageToSite("newPage", "classic", "site");
        restService.AddPageToSite("newPage2", "classic", "site");
        restService.AddPageToSite("newPage3", "classic", "site");
        String classicPages = restService.getSitePages("classic", "site");
        List<Page> pages = jSonParser.pagesParser(classicPages);
        int addedPages = 0;
        for (Page page : pages) {
            if (page.getName().equals("newPage") || page.getName().equals("newPage2") || page.getName().equals("newPage3")) {
                addedPages = addedPages + 1;
            }
        }
        assertEquals(3, addedPages);

        String page = restService.getPage("newPage", "classic", "site");
        Page homepage = jSonParser.pageParser(page);
        assertEquals("newPage", homepage.getName());
        assertEquals("newPage", homepage.getDisplayName());
        assertEquals("null", homepage.getDescription());
        assertEquals("[\"Everyone\"]", homepage.getAccessPermissions());
        assertEquals("[\"*:\\/platform\\/administrators\"]", homepage.getEditPermissions());

        restService.deletePage("classic", "newPage", "site");
        restService.deletePage("classic", "newPage2", "site");
        restService.deletePage("classic", "newPage3", "site");
    }

    @Test
    public void testAddPageToMobileSite() throws ParseException {
        System.out.println("**testAddPageToMobileSite**");
        restService.AddPageToSite("newPage", "mobile", "site");
        restService.AddPageToSite("newPage2", "mobile", "site");
        restService.AddPageToSite("newPage3", "mobile", "site");
        String classicPages = restService.getSitePages("mobile", "site");
        List<Page> pages = jSonParser.pagesParser(classicPages);
        int addedPages = 0;
        for (Page page : pages) {
            if (page.getName().equals("newPage") || page.getName().equals("newPage2") || page.getName().equals("newPage3")) {
                addedPages = addedPages + 1;
            }
        }
        assertEquals(addedPages, 3);

        String page = restService.getPage("newPage", "mobile", "site");
        Page homepage = jSonParser.pageParser(page);
        assertEquals("newPage", homepage.getName());
        assertEquals("newPage", homepage.getDisplayName());
        assertEquals("null", homepage.getDescription());
        assertEquals("[\"Everyone\"]", homepage.getAccessPermissions());
        assertEquals("[\"*:\\/platform\\/administrators\"]", homepage.getEditPermissions());
        restService.deletePage("mobile", "newPage", "site");
        restService.deletePage("mobile", "newPage2", "site");
        restService.deletePage("mobile", "newPage3", "site");
    }

    @Test
    public void testGetNonExistingPage() {
        System.out.println("**testGetNonExistingPage**");
        String getResult = restService.getPage("nonPage", "classic", "site");
        assertTrue(getResult.contains("does not exist"));
    }

    @Test
    public void testUpdatePage() throws ParseException {
        System.out.println("**testUpdatePage**");
        restService.AddPageToSite("newPage", "classic", "site");
        restService.AddPageToSite("newPage2", "classic", "site");
        restService.AddPageToSite("newPage3", "classic", "site");

        restService.AddPageToSite("newPage", "mobile", "site");
        restService.AddPageToSite("newPage2", "mobile", "site");
        restService.AddPageToSite("newPage3", "mobile", "site");

        Map<String, String> attributeMap = new HashMap<>();
        attributeMap.put("name", "newPage");
        attributeMap.put("siteName", "classic");
        attributeMap.put("description", "Updated description");
        attributeMap.put("displayName", "UpdatedNewPage");
        attributeMap.put("access-permissions", "[\"manager:/platform/guests\"]");
        attributeMap.put("edit-permissions", "[\"*:/platform/administrators\"]");
        restService.updatePage(attributeMap, "site");
        String page = restService.getPage("newPage", "classic", "site");
        Page newPage = jSonParser.pageParser(page);
        assertEquals("newPage", newPage.getName());
        assertEquals("UpdatedNewPage", newPage.getDisplayName());
        assertEquals("Updated description", newPage.getDescription());
        assertEquals("[\"manager:\\/platform\\/guests\"]", newPage.getAccessPermissions());
        assertEquals("[\"*:\\/platform\\/administrators\"]", newPage.getEditPermissions());

        attributeMap.clear();
        attributeMap.put("name", "homepage");
        attributeMap.put("siteName", "classic");
        attributeMap.put("description", "Homepage updated description");
        attributeMap.put("displayName", "UpdatedHomepage");
        attributeMap.put("access-permissions", "[\"*:/platform/users\"]");
        attributeMap.put("edit-permissions", "[\"member:/platform/guests\"]");
        restService.updatePage(attributeMap, "site");
        String home = restService.getPage("homepage", "classic", "site");
        Page homepage = jSonParser.pageParser(home);
        assertEquals("homepage", homepage.getName());
        assertEquals("UpdatedHomepage", homepage.getDisplayName());
        assertEquals("Homepage updated description", homepage.getDescription());
        assertEquals("[\"*:\\/platform\\/users\"]", homepage.getAccessPermissions());
        assertEquals("[\"member:\\/platform\\/guests\"]", homepage.getEditPermissions());

        attributeMap.clear();
        attributeMap.put("name", "homepage");
        attributeMap.put("siteName", "classic");
        attributeMap.put("description", "null");
        attributeMap.put("displayName", "Home Page");
        attributeMap.put("access-permissions", "[\"Everyone\"]");
        attributeMap.put("edit-permissions", "[\"*:/platform/administrators\"]");
        restService.updatePage(attributeMap, "site");
        String homeRestore = restService.getPage("homepage", "classic", "site");
        Page homepageRestore = jSonParser.pageParser(homeRestore);
        assertEquals("homepage", homepageRestore.getName());
        assertEquals("Home Page", homepageRestore.getDisplayName());
        assertEquals("null", homepageRestore.getDescription());
        assertEquals("[\"Everyone\"]", homepageRestore.getAccessPermissions());
        assertEquals("[\"*:\\/platform\\/administrators\"]", homepageRestore.getEditPermissions());
        restService.deletePage("classic", "newPage", "site");
        restService.deletePage("classic", "newPage2", "site");
        restService.deletePage("classic", "newPage3", "site");

        restService.deletePage("mobile", "newPage", "site");
        restService.deletePage("mobile", "newPage2", "site");
        restService.deletePage("mobile", "newPage3", "site");
    }

    @Test
    public void testDeletePages() throws ParseException {
        System.out.println("**testDeletePages**");
        restService.AddPageToSite("newPage", "classic", "site");
        restService.AddPageToSite("newPage2", "classic", "site");
        restService.AddPageToSite("newPage3", "classic", "site");

        restService.AddPageToSite("newPage", "mobile", "site");
        restService.AddPageToSite("newPage2", "mobile", "site");
        restService.AddPageToSite("newPage3", "mobile", "site");

        restService.deletePage("classic", "newPage", "site");
        restService.deletePage("classic", "newPage2", "site");
        restService.deletePage("classic", "newPage3", "site");

        restService.deletePage("mobile", "newPage", "site");
        restService.deletePage("mobile", "newPage2", "site");
        restService.deletePage("mobile", "newPage3", "site");

        String classicPages = restService.getSitePages("classic", "site");
        List<Page> pages = jSonParser.pagesParser(classicPages);
        int addedPages = 0;
        for (Page page : pages) {
            if (page.getName().equals("newPage") || page.getName().equals("newPage2") || page.getName().equals("newPage3")) {
                addedPages = addedPages + 1;
            }
        }
        assertEquals(0, addedPages);

        String mobilePages = restService.getSitePages("mobile", "site");
        List<Page> mpages = jSonParser.pagesParser(mobilePages);
        addedPages = 0;
        for (Page page : mpages) {
            if (page.getName().equals("newPage") || page.getName().equals("newPage2") || page.getName().equals("newPage3")) {
                addedPages = addedPages + 1;
            }
        }
        assertEquals(0, addedPages);
    }
}
