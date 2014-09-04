package org.gatein.rest.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gatein.rest.constants.ConstantsService;
import org.gatein.rest.entity.Page;
import org.gatein.rest.helper.JSonParser;
import org.gatein.rest.service.api.HelpingServiceApi;
import org.gatein.rest.service.impl.HelpingService;
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
        assertEquals(addedPages, 3);

        String page = restService.getPage("addedPage", "newDashboard", "dashboard");
        Page homepage = jSonParser.pageParser(page);
        assertTrue(homepage.getName().equals("addedPage"));
        assertTrue(homepage.getDisplayName().equals("addedPage"));
        assertTrue(homepage.getDescription().equals("null"));
        assertTrue(homepage.getAccessPermissions().equals("[\"Everyone\"]"));
        System.out.println(homepage.getEditPermissions());
        assertTrue(homepage.getEditPermissions().equals("[\"*:\\/platform\\/administrators\"]"));
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
        Object[] pagesArray = pages.toArray();
        assertTrue(((Page) pagesArray[0]).getName().equals("Tab_Default"));
        assertTrue(((Page) pagesArray[0]).getSiteName().equals("newDashboard"));
        assertTrue(((Page) pagesArray[0]).getSiteType().equals("dashboard"));
        assertTrue(((Page) pagesArray[0]).getURL().equals(REST_API_URL + "/dashboards/newDashboard/pages/Tab_Default"));
        assertTrue(((Page) pagesArray[1]).getName().equals("addedPage"));
        assertTrue(((Page) pagesArray[1]).getSiteName().equals("newDashboard"));
        assertTrue(((Page) pagesArray[1]).getSiteType().equals("dashboard"));
        assertTrue(((Page) pagesArray[1]).getURL().equals(REST_API_URL + "/dashboards/newDashboard/pages/addedPage"));
        assertTrue(((Page) pagesArray[2]).getName().equals("addedPage2"));
        assertTrue(((Page) pagesArray[2]).getSiteName().equals("newDashboard"));
        assertTrue(((Page) pagesArray[2]).getSiteType().equals("dashboard"));
        assertTrue(((Page) pagesArray[2]).getURL().equals(REST_API_URL + "/dashboards/newDashboard/pages/addedPage2"));
        assertTrue(((Page) pagesArray[3]).getName().equals("addedPage3"));
        assertTrue(((Page) pagesArray[3]).getSiteName().equals("newDashboard"));
        assertTrue(((Page) pagesArray[3]).getSiteType().equals("dashboard"));
        assertTrue(((Page) pagesArray[3]).getURL().equals(REST_API_URL + "/dashboards/newDashboard/pages/addedPage3"));
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
        assertTrue(addedPage3.getName().equals("addedPage3"));
        assertTrue(addedPage3.getDisplayName().equals("addedPage3"));
        assertTrue(addedPage3.getDescription().equals("null"));
        assertTrue(addedPage3.getAccessPermissions().equals("[\"Everyone\"]"));
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
        assertTrue(addedPage.getName().equals("addedPage"));
        assertTrue(addedPage.getDisplayName().equals("UpdatedNewPage"));
        assertTrue(addedPage.getDescription().equals("Updated description"));
        assertTrue(addedPage.getAccessPermissions().equals("[\"*:\\/platform\\/guests\"]"));
        assertTrue(addedPage.getEditPermissions().equals("[\"manager:\\/platform\\/users\"]"));


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
        assertTrue(homepage.getName().equals("Tab_Default"));
        assertTrue(homepage.getDisplayName().equals("UpdatedTab_Default"));
        assertTrue(homepage.getDescription().equals("Tab_Default updated description"));
        assertTrue(homepage.getAccessPermissions().equals("[\"*:\\/platform\\/guests\"]"));
        assertTrue(homepage.getEditPermissions().equals("[\"*:\\/platform\\/administrators\"]"));

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
        assertTrue(homepageRestore.getName().equals("Tab_Default"));
        assertTrue(homepageRestore.getDisplayName().equals("Tab_Default"));
        assertTrue(homepageRestore.getDescription().equals("null"));
        assertTrue(homepageRestore.getAccessPermissions().equals("[\"*:\\/platform\\/users\"]"));
        assertTrue(homepageRestore.getEditPermissions().equals("[\"*:\\/platform\\/administrators\"]"));
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
        System.out.println(classicPages+"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        List<Page> pages = jSonParser.pagesParser(classicPages);
        int addedPages = 0;
        for (Page page : pages) {
            if (page.getName().equals("addedPage") || page.getName().equals("addedPage2") || page.getName().equals("addedPage3")) {
                addedPages = addedPages + 1;
            }
        }
        assertEquals(addedPages, 0);
        restService.deleteSite("newDashboard", "dashboard");
    }
}
