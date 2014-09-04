package org.gatein.rest.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gatein.rest.constants.ConstantsService;
import org.gatein.rest.helper.JSonParser;
import org.gatein.rest.entity.Page;
import org.gatein.rest.service.impl.HelpingService;
import org.gatein.rest.service.impl.RestService;
import org.gatein.rest.service.api.HelpingServiceApi;
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
public class RestSitesPagesTest {

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
    public void testGetSitesPages() throws ParseException {
        System.out.println("**testGetSitesPages**");
        String classicPages = restService.getSitePages("classic", "site");
        List<Page> pages = jSonParser.pagesParser(classicPages);
        Object[] pagesArray = pages.toArray();
        List<Object> pagesList = Arrays.asList(pagesArray);
        for (Object object : pagesList) {

            switch (((Page) object).getName()) {
                case "homepage":
                    assertTrue(((Page) object).getSiteName().equals("classic"));
                    assertTrue(((Page) object).getSiteType().equals("site"));
                    assertTrue(((Page) object).getURL().equals(REST_API_URL + "/sites/classic/pages/homepage"));
                    break;
                case "groupnavigation":
                    assertTrue(((Page) object).getSiteName().equals("classic"));
                    assertTrue(((Page) object).getSiteType().equals("site"));
                    assertTrue(((Page) object).getURL().equals(REST_API_URL + "/sites/classic/pages/groupnavigation"));
                    break;
                case "portalnavigation":
                    assertTrue(((Page) object).getSiteName().equals("classic"));
                    assertTrue(((Page) object).getSiteType().equals("site"));
                    assertTrue(((Page) object).getURL().equals(REST_API_URL + "/sites/classic/pages/portalnavigation"));
                    break;
                case "register":
                    assertTrue(((Page) object).getSiteName().equals("classic"));
                    assertTrue(((Page) object).getSiteType().equals("site"));
                    assertTrue(((Page) object).getURL().equals(REST_API_URL + "/sites/classic/pages/register"));
                    break;
                case "sitemap":
                    assertTrue(((Page) object).getSiteName().equals("classic"));
                    assertTrue(((Page) object).getSiteType().equals("site"));
                    assertTrue(((Page) object).getURL().equals(REST_API_URL + "/sites/classic/pages/sitemap"));
                    break;
            }
        }

        String mobilePages = restService.getSitePages("mobile", "site");
        pages.clear();
        pages = jSonParser.pagesParser(mobilePages);
        Object[] mobilePagesArray = pages.toArray();
        List<Object> mobilePagesList = Arrays.asList(mobilePagesArray);
        for (Object object : mobilePagesList) {

            switch (((Page) object).getName()) {
                case "homepage":
                    assertTrue(((Page) object).getSiteName().equals("mobile"));
                    assertTrue(((Page) object).getSiteType().equals("site"));
                    assertTrue(((Page) object).getURL().equals(REST_API_URL + "/sites/mobile/pages/homepage"));
                    break;
                case "groupnavigation":
                    assertTrue(((Page) object).getSiteName().equals("mobile"));
                    assertTrue(((Page) object).getSiteType().equals("site"));
                    assertTrue(((Page) object).getURL().equals(REST_API_URL + "/sites/mobile/pages/groupnavigation"));
                    break;
                case "portalnavigation":
                    assertTrue(((Page) object).getSiteName().equals("mobile"));
                    assertTrue(((Page) object).getSiteType().equals("site"));
                    assertTrue(((Page) object).getURL().equals(REST_API_URL + "/sites/mobile/pages/portalnavigation"));
                    break;
                case "register":
                    assertTrue(((Page) object).getSiteName().equals("mobile"));
                    assertTrue(((Page) object).getSiteType().equals("site"));
                    assertTrue(((Page) object).getURL().equals(REST_API_URL + "/sites/mobile/pages/register"));
                    break;
                case "features":
                    assertTrue(((Page) object).getSiteName().equals("mobile"));
                    assertTrue(((Page) object).getSiteType().equals("site"));
                    assertTrue(((Page) object).getURL().equals(REST_API_URL + "/sites/mobile/pages/features"));
                    break;
            }
        }
    }

    @Test
    public void testGetPage() throws ParseException {
        System.out.println("**testGetPage**");
        String page = restService.getPage("homepage", "classic", "site");
        Page homepage = jSonParser.pageParser(page);
        assertTrue(homepage.getName().equals("homepage"));
        assertTrue(homepage.getDisplayName().equals("Home Page"));
        assertTrue(homepage.getDescription().equals("null"));
        assertTrue(homepage.getAccessPermissions().equals("[\"Everyone\"]"));
        assertTrue(homepage.getEditPermissions().equals("[\"*:\\/platform\\/administrators\"]"));
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
        assertEquals(addedPages, 3);

        String page = restService.getPage("newPage", "classic", "site");
        Page homepage = jSonParser.pageParser(page);
        assertTrue(homepage.getName().equals("newPage"));
        assertTrue(homepage.getDisplayName().equals("newPage"));
        assertTrue(homepage.getDescription().equals("null"));
        assertTrue(homepage.getAccessPermissions().equals("[\"Everyone\"]"));
        assertTrue(homepage.getEditPermissions().equals("[\"*:\\/platform\\/administrators\"]"));

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
        assertTrue(homepage.getName().equals("newPage"));
        assertTrue(homepage.getDisplayName().equals("newPage"));
        assertTrue(homepage.getDescription().equals("null"));
        assertTrue(homepage.getAccessPermissions().equals("[\"Everyone\"]"));
        assertTrue(homepage.getEditPermissions().equals("[\"*:\\/platform\\/administrators\"]"));
        restService.deletePage("mobile", "newPage", "site");
        restService.deletePage("mobile", "newPage2", "site");
        restService.deletePage("mobile", "newPage3", "site");
    }

    @Test
    public void testGetNonExistingPage() {
        System.out.println("**testGetNonExistingPage**");
        String getResult = restService.getPage("nonPage", "classic", "site");
        assertTrue(getResult.contains("does not exist"));

        restService.deletePage("mobile", "newPage", "site");
        restService.deletePage("mobile", "newPage2", "site");
        restService.deletePage("mobile", "newPage3", "site");
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
        assertTrue(newPage.getName().equals("newPage"));
        assertTrue(newPage.getDisplayName().equals("UpdatedNewPage"));
        assertTrue(newPage.getDescription().equals("Updated description"));
        assertTrue(newPage.getAccessPermissions().equals("[\"manager:\\/platform\\/guests\"]"));
        assertTrue(newPage.getEditPermissions().equals("[\"*:\\/platform\\/administrators\"]"));

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
        assertTrue(homepage.getName().equals("homepage"));
        assertTrue(homepage.getDisplayName().equals("UpdatedHomepage"));
        assertTrue(homepage.getDescription().equals("Homepage updated description"));
        assertTrue(homepage.getAccessPermissions().equals("[\"*:\\/platform\\/users\"]"));
        assertTrue(homepage.getEditPermissions().equals("[\"member:\\/platform\\/guests\"]"));

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
        assertTrue(homepageRestore.getName().equals("homepage"));
        assertTrue(homepageRestore.getDisplayName().equals("Home Page"));
        assertTrue(homepageRestore.getDescription().equals("null"));
        assertTrue(homepageRestore.getAccessPermissions().equals("[\"Everyone\"]"));
        assertTrue(homepageRestore.getEditPermissions().equals("[\"*:\\/platform\\/administrators\"]"));
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
        assertEquals(addedPages, 0);

        String mobilePages = restService.getSitePages("mobile", "site");
        List<Page> mpages = jSonParser.pagesParser(mobilePages);
        addedPages = 0;
        for (Page page : mpages) {
            if (page.getName().equals("newPage") || page.getName().equals("newPage2") || page.getName().equals("newPage3")) {
                addedPages = addedPages + 1;
            }
        }
        assertEquals(addedPages, 0);

    }
}
