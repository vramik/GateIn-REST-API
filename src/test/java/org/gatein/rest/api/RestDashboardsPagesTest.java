package org.gatein.rest.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gatein.rest.entity.Page;
import org.gatein.rest.helper.JSonParser;
import org.gatein.rest.service.impl.RestService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.gatein.rest.constants.RestConstants.*;
import org.json.simple.parser.ParseException;

/**
 *
 * @author mgottval
 */
public class RestDashboardsPagesTest {

    private RestService restService;
    private final JSonParser jSonParser = new JSonParser();

    @Before
    public void before() {
        restService = new RestService();
    }

    @Test
    public void testAddPageToSite() throws ParseException {
        System.out.println("**testAddPageToSite**");
        restService.addSite("newDashboard", "dashboard");
        restService.AddPageToSite("addedPage", "newDashboard", "dashboard");
        restService.AddPageToSite("addedPage2", "newDashboard", "dashboard");
        restService.AddPageToSite("addedPage3", "newDashboard", "dashboard");
        String classicPages = restService.getSitePages("newDashboard", "dashboard");
        List<Page> pages = jSonParser.pagesParser(classicPages);
        int addedPages = 0;
        for (Page page : pages) {
            if (page.getName().equals("addedPage") || page.getName().equals("addedPage2") || page.getName().equals("addedPage3")) {
                addedPages = addedPages + 1;
            }
        }
        assertEquals(3, addedPages);

        String page = restService.getPage("addedPage", "newDashboard", "dashboard");
        Page homepage = jSonParser.pageParser(page);
        assertEquals("addedPage", homepage.getName());
        assertEquals("addedPage", homepage.getDisplayName());
        assertEquals("null", homepage.getDescription());
        assertEquals("[\"Everyone\"]", homepage.getAccessPermissions());
        assertEquals("[\"*:\\/platform\\/administrators\"]", homepage.getEditPermissions());
        restService.deletePage("newDashboard", "addedPage", "dashboard");
        restService.deletePage("newDashboard", "addedPage2", "dashboard");
        restService.deletePage("newDashboard", "addedPage3", "dashboard");
        restService.deleteSite("newDashboard", "dashboard");
    }

    @Test
    public void testGetDashboardsPages() throws ParseException {
        System.out.println("**testGetDashboardsPages**");
        restService.addSite("newDashboard", "dashboard");
        restService.AddPageToSite("addedPage", "newDashboard", "dashboard");
        restService.AddPageToSite("addedPage2", "newDashboard", "dashboard");
        restService.AddPageToSite("addedPage3", "newDashboard", "dashboard");
        String classicPages = restService.getSitePages("newDashboard", "dashboard");
        List<Page> pages = jSonParser.pagesParser(classicPages);
        assertEquals("Tab_Default", pages.get(0).getName());
        assertEquals("newDashboard", pages.get(0).getSiteName());
        assertEquals("dashboard", pages.get(0).getSiteType());
        assertEquals(REST_API_URL + "/dashboards/newDashboard/pages/Tab_Default", pages.get(0).getURL());
        assertEquals("addedPage", pages.get(1).getName());
        assertEquals("newDashboard", pages.get(1).getSiteName());
        assertEquals("dashboard", pages.get(1).getSiteType());
        assertEquals(REST_API_URL + "/dashboards/newDashboard/pages/addedPage", pages.get(1).getURL());
        assertEquals("addedPage2", pages.get(2).getName());
        assertEquals("newDashboard", pages.get(2).getSiteName());
        assertEquals("dashboard", pages.get(2).getSiteType());
        assertEquals(REST_API_URL + "/dashboards/newDashboard/pages/addedPage2", pages.get(2).getURL());
        assertEquals("addedPage3", pages.get(3).getName());
        assertEquals("newDashboard", pages.get(3).getSiteName());
        assertEquals("dashboard", pages.get(3).getSiteType());
        assertEquals(REST_API_URL + "/dashboards/newDashboard/pages/addedPage3", pages.get(3).getURL());
        
        restService.deletePage("newDashboard", "addedPage", "dashboard");
        restService.deletePage("newDashboard", "addedPage2", "dashboard");
        restService.deletePage("newDashboard", "addedPage3", "dashboard");
        restService.deleteSite("newDashboard", "dashboard");
    }

    @Test
    public void testGetPage() throws ParseException {
        System.out.println("**testGetPage**");
        restService.addSite("newDashboard", "dashboard");
        restService.AddPageToSite("addedPage", "newDashboard", "dashboard");
        restService.AddPageToSite("addedPage2", "newDashboard", "dashboard");
        restService.AddPageToSite("addedPage3", "newDashboard", "dashboard");
        String defaultTab = restService.getPage("addedPage3", "newDashboard", "dashboard");
        Page addedPage3 = jSonParser.pageParser(defaultTab);
        assertEquals("addedPage3", addedPage3.getName());
        assertEquals("addedPage3", addedPage3.getDisplayName());
        assertEquals("null", addedPage3.getDescription());
        assertEquals("[\"Everyone\"]", addedPage3.getAccessPermissions());

        restService.deletePage("newDashboard", "addedPage", "dashboard");
        restService.deletePage("newDashboard", "addedPage2", "dashboard");
        restService.deletePage("newDashboard", "addedPage3", "dashboard");
        restService.deleteSite("newDashboard", "dashboard");
    }

    @Test
    public void testGetNonExistingPage() {
        System.out.println("**testGetNonExistingPage**");
        String getResult = restService.getPage("nonPage", "root", "dashboard");
        assertTrue(getResult.contains("does not exist"));
    }

    @Test
    public void testUpdatePage() throws ParseException {
        System.out.println("**testUpdatePage**");
        restService.addSite("newDashboard", "dashboard");
        restService.AddPageToSite("addedPage", "newDashboard", "dashboard");
        restService.AddPageToSite("addedPage2", "newDashboard", "dashboard");
        restService.AddPageToSite("addedPage3", "newDashboard", "dashboard");
        
        Map<String, String> attributeMap = new HashMap<>();
        attributeMap.put("name", "addedPage");
        attributeMap.put("siteName", "newDashboard");
        attributeMap.put("description", "Updated description");
        attributeMap.put("displayName", "UpdatedNewPage");
        attributeMap.put("access-permissions", "[\"*:/platform/guests\"]");
        attributeMap.put("edit-permissions", "[\"manager:/platform/users\"]");
        restService.updatePage(attributeMap, "dashboard");
        String page = restService.getPage("addedPage", "newDashboard", "dashboard");
        System.out.println(page);
        Page addedPage = jSonParser.pageParser(page);
        assertEquals("addedPage", addedPage.getName());
        assertEquals("UpdatedNewPage", addedPage.getDisplayName());
        assertEquals("Updated description", addedPage.getDescription());
        assertEquals("[\"*:\\/platform\\/guests\"]", addedPage.getAccessPermissions());
        assertEquals("[\"manager:\\/platform\\/users\"]", addedPage.getEditPermissions());
        
        attributeMap.clear();
        attributeMap.put("name", "Tab_Default");
        attributeMap.put("siteName", "newDashboard");
        attributeMap.put("description", "Tab_Default updated description");
        attributeMap.put("displayName", "UpdatedTab_Default");
        attributeMap.put("access-permissions", "[\"*:/platform/guests\"]");
        attributeMap.put("edit-permissions", "[\"*:/platform/administrators\"]");
        restService.updatePage(attributeMap, "dashboard");
        String tabDef = restService.getPage("Tab_Default", "newDashboard", "dashboard");
        Page homepage = jSonParser.pageParser(tabDef);
        assertEquals("Tab_Default", homepage.getName());
        assertEquals("UpdatedTab_Default", homepage.getDisplayName());
        assertEquals("Tab_Default updated description", homepage.getDescription());
        assertEquals("[\"*:\\/platform\\/guests\"]", homepage.getAccessPermissions());
        assertEquals("[\"*:\\/platform\\/administrators\"]", homepage.getEditPermissions());
        
        attributeMap.clear();
        attributeMap.put("name", "Tab_Default");
        attributeMap.put("siteName", "newDashboard");
        attributeMap.put("description", "null");
        attributeMap.put("displayName", "Tab_Default");
        attributeMap.put("access-permissions", "[\"*:/platform/users\"]");
        attributeMap.put("edit-permissions", "[\"*:/platform/administrators\"]");
        restService.updatePage(attributeMap, "dashboard");
        String tabDefRestore = restService.getPage("Tab_Default", "newDashboard", "dashboard");
        Page homepageRestore = jSonParser.pageParser(tabDefRestore);
        assertEquals("Tab_Default", homepageRestore.getName());
        assertEquals("Tab_Default", homepageRestore.getDisplayName());
        assertEquals("null", homepageRestore.getDescription());
        assertEquals("[\"*:\\/platform\\/users\"]", homepageRestore.getAccessPermissions());
        assertEquals("[\"*:\\/platform\\/administrators\"]", homepageRestore.getEditPermissions());
        restService.deletePage("newDashboard", "addedPage", "dashboard");
        restService.deletePage("newDashboard", "addedPage2", "dashboard");
        restService.deletePage("newDashboard", "addedPage3", "dashboard");
        restService.deleteSite("newDashboard", "dashboard");
    }

    @Test
    public void testDeletePages() throws ParseException {
        System.out.println("**testDeletePages**");
        restService.addSite("newDashboard", "dashboard");
        restService.AddPageToSite("addedPage", "newDashboard", "dashboard");
        restService.AddPageToSite("addedPage2", "newDashboard", "dashboard");
        restService.AddPageToSite("addedPage3", "newDashboard", "dashboard");
        restService.deletePage("newDashboard", "addedPage", "dashboard");
        restService.deletePage("newDashboard", "addedPage2", "dashboard");
        restService.deletePage("newDashboard", "addedPage3", "dashboard");
        String classicPages = restService.getSitePages("newDashboard", "dashboard");
        List<Page> pages = jSonParser.pagesParser(classicPages);
        int addedPages = 0;
        for (Page page : pages) {
            if (page.getName().equals("addedPage") || page.getName().equals("addedPage2") || page.getName().equals("addedPage3")) {
                addedPages = addedPages + 1;
            }
        }
        assertEquals(0, addedPages);
        restService.deleteSite("newDashboard", "dashboard");
    }
}
