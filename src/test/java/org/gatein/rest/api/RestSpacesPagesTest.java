package org.gatein.rest.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gatein.rest.constants.ConstantsService;
import org.gatein.rest.entity.Page;
import org.gatein.rest.helper.JSonParser;
import org.gatein.rest.service.api.HelpingServiceApi;
import org.gatein.rest.service.impl.HelpingService;
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
    public void testGetSpacePages() throws ParseException {
        String classicPages = restService.getSitePages("/platform/guests", "space");
        List<Page> pages = jSonParser.pagesParser(classicPages);
        Object[] pagesArray = pages.toArray();
        assertTrue(((Page) pagesArray[1]).getName().equals("sitemap"));
        assertTrue(((Page) pagesArray[1]).getSiteName().equals("/platform/guests"));
        assertTrue(((Page) pagesArray[1]).getSiteType().equals("space"));
        assertTrue(((Page) pagesArray[1]).getURL().equals(REST_API_URL + "/spaces/platform/guests/pages/sitemap"));
        assertTrue(((Page) pagesArray[0]).getName().equals("link"));
        assertTrue(((Page) pagesArray[0]).getSiteName().equals("/platform/guests"));
        assertTrue(((Page) pagesArray[0]).getSiteType().equals("space"));
        assertTrue(((Page) pagesArray[0]).getURL().equals(REST_API_URL + "/spaces/platform/guests/pages/link"));

        String adminPages = restService.getSitePages("/platform/administrators", "space");
        pages.clear();
        pages = jSonParser.pagesParser(adminPages);
        pagesArray = pages.toArray();
        List<Object> pagesList = Arrays.asList(pagesArray);
        assertTrue(pagesList.size() == 7);
        for (Object object : pagesList) {

            switch (((Page) object).getName()) {
                case "newAccount":
                    assertTrue(((Page) object).getSiteName().equals("/platform/administrators"));
                    assertTrue(((Page) object).getSiteType().equals("space"));
                    assertTrue(((Page) object).getURL().equals(REST_API_URL + "/spaces/platform/administrators/pages/newAccount"));
                    break;
                case "communityManagement":
                    assertTrue(((Page) object).getSiteName().equals("/platform/administrators"));
                    assertTrue(((Page) object).getSiteType().equals("space"));
                    assertTrue(((Page) object).getURL().equals(REST_API_URL + "/spaces/platform/administrators/pages/communityManagement"));
                    break;
                case "registry":
                    assertTrue(((Page) object).getSiteName().equals("/platform/administrators"));
                    assertTrue(((Page) object).getSiteType().equals("space"));
                    assertTrue(((Page) object).getURL().equals(REST_API_URL + "/spaces/platform/administrators/pages/registry"));
                    break;
                case "pageManagement":
                    assertTrue(((Page) object).getSiteName().equals("/platform/administrators"));
                    assertTrue(((Page) object).getSiteType().equals("space"));
                    assertTrue(((Page) object).getURL().equals(REST_API_URL + "/spaces/platform/administrators/pages/pageManagement"));
                    break;
                case "servicesManagement":
                    assertTrue(((Page) object).getSiteName().equals("/platform/administrators"));
                    assertTrue(((Page) object).getSiteType().equals("space"));
                    assertTrue(((Page) object).getURL().equals(REST_API_URL + "/spaces/platform/administrators/pages/servicesManagement"));
                    break;
                case "siteRedirects":
                    assertTrue(((Page) object).getSiteName().equals("/platform/administrators"));
                    assertTrue(((Page) object).getSiteType().equals("space"));
                    assertTrue(((Page) object).getURL().equals(REST_API_URL + "/spaces/platform/administrators/pages/siteRedirects"));
                    break;
                case "wsrpConfiguration":
                    assertTrue(((Page) object).getSiteName().equals("/platform/administrators"));
                    assertTrue(((Page) object).getSiteType().equals("space"));
                    assertTrue(((Page) object).getURL().equals(REST_API_URL + "/spaces/platform/administrators/pages/wsrpConfiguration"));
                    break;
            }
        }

        String execPages = restService.getSitePages("/organization/management/executive-board", "space");
        pages.clear();
        pages = jSonParser.pagesParser(execPages);
        pagesArray = pages.toArray();
        assertTrue(((Page) pagesArray[0]).getName().equals("newStaff"));
        assertTrue(((Page) pagesArray[0]).getSiteName().equals("/organization/management/executive-board"));
        assertTrue(((Page) pagesArray[0]).getSiteType().equals("space"));
        assertTrue(((Page) pagesArray[0]).getURL().equals(REST_API_URL + "/spaces/organization/management/executive-board/pages/newStaff"));
        assertTrue(((Page) pagesArray[1]).getName().equals("management"));
        assertTrue(((Page) pagesArray[1]).getSiteName().equals("/organization/management/executive-board"));
        assertTrue(((Page) pagesArray[1]).getSiteType().equals("space"));
        assertTrue(((Page) pagesArray[1]).getURL().equals(REST_API_URL + "/spaces/organization/management/executive-board/pages/management"));

        String usersPages = restService.getSitePages("/platform/users", "space");
        pages.clear();
        pages = jSonParser.pagesParser(usersPages);
        pagesArray = pages.toArray();
        assertTrue(((Page) pagesArray[0]).getName().equals("mylink-blog"));
        assertTrue(((Page) pagesArray[0]).getSiteName().equals("/platform/users"));
        assertTrue(((Page) pagesArray[0]).getSiteType().equals("space"));
        assertTrue(((Page) pagesArray[0]).getURL().equals(REST_API_URL + "/spaces/platform/users/pages/mylink-blog"));
        assertTrue(((Page) pagesArray[1]).getName().equals("mylink-fedora"));
        assertTrue(((Page) pagesArray[1]).getSiteName().equals("/platform/users"));
        assertTrue(((Page) pagesArray[1]).getSiteType().equals("space"));
        assertTrue(((Page) pagesArray[1]).getURL().equals(REST_API_URL + "/spaces/platform/users/pages/mylink-fedora"));

    }
//    

    @Test
    public void testGetPage() throws ParseException {
        String page = restService.getPage("communityManagement", "platform/administrators", "space");
        Page comunityManagement = jSonParser.pageParser(page);
        assertTrue(comunityManagement.getName().equals("communityManagement"));
        assertTrue(comunityManagement.getDisplayName().equals("Community Management"));
        assertTrue(comunityManagement.getDescription().equals("null"));
        assertTrue(comunityManagement.getAccessPermissions().equals("[\"manager:\\/platform\\/administrators\"]"));
        assertTrue(comunityManagement.getEditPermissions().equals("[\"manager:\\/platform\\/administrators\"]"));

        String staffPage = restService.getPage("newStaff", "/organization/management/executive-board", "space");
        Page newStaff = jSonParser.pageParser(staffPage);
        assertTrue(newStaff.getName().equals("newStaff"));
        assertTrue(newStaff.getDisplayName().equals("New Staff"));
        assertTrue(newStaff.getDescription().equals("null"));
        assertTrue(newStaff.getAccessPermissions().equals("[\"*:\\/organization\\/management\\/executive-board\"]"));
        assertTrue(newStaff.getEditPermissions().equals("[\"manager:\\/organization\\/management\\/executive-board\"]"));
    }

//    @Test
//    public void testAddPageToClassicSite() {
//        restService.AddPageToSite("newPage4", "classic", "site");
//        restService.AddPageToSite("newPage5", "classic", "site");
//        restService.AddPageToSite("newPage3", "classic", "site");
//        String classicPages = restService.getSitePages("classic", "site");
//        List<Page> pages = jSonParser.pagesParser(classicPages);
//        int addedPages = 0;
//        for (Page page : pages) {
//            if (page.getName().equals("newPage4") || page.getName().equals("newPage5") || page.getName().equals("newPage3")) {
//                addedPages = addedPages + 1;
//            }
//        }
//        assertEquals(addedPages, 3);
//
//        String page = restService.getPage("newPage4", "classic", "site");
//        Page homepage = jSonParser.pageParser(page);
//        assertTrue(homepage.getName().equals("newPage4"));
//        assertTrue(homepage.getDisplayName().equals("newPage4"));
//        assertTrue(homepage.getDescription().equals("null"));
//        assertTrue(homepage.getAccessPermissions().equals("[\"Everyone\"]"));
//        assertTrue(homepage.getEditPermissions().equals("[\"*:/platform/administrators\"]"));
//    }
    //addPagetoaddessites todo
    @Test
    public void testAddPageToAdminSite() throws ParseException {
        restService.AddPageToSite("newPage4", "platform/administrators", "space");
        restService.AddPageToSite("newPage5", "platform/administrators", "space");
        restService.AddPageToSite("newPage3", "platform/administrators", "space");
        String adminPages = restService.getSitePages("platform/administrators", "space");
        List<Page> pages = jSonParser.pagesParser(adminPages);
        int addedPages = 0;
        for (Page page : pages) {
            if (page.getName().equals("newPage4") || page.getName().equals("newPage5") || page.getName().equals("newPage3")) {
                addedPages = addedPages + 1;
            }
        }
        assertEquals(addedPages, 3);
        addedPages = 0;
        String page = restService.getPage("newPage4", "platform/administrators", "space");
        Page homepage = jSonParser.pageParser(page);
        assertTrue(homepage.getName().equals("newPage4"));
        assertTrue(homepage.getDisplayName().equals("newPage4"));
        assertTrue(homepage.getDescription().equals("null"));
        assertTrue(homepage.getAccessPermissions().equals("[\"Everyone\"]"));
        assertTrue(homepage.getEditPermissions().equals("[\"*:\\/platform\\/administrators\"]"));
        restService.deletePage("platform/administrators", "newPage4", "space");
        restService.deletePage("platform/administrators", "newPage5", "space");
        restService.deletePage("platform/administrators", "newPage3", "space");
    }

    @Test
    public void testGetNonexistingPage() {
        String getResult = restService.getPage("nonPage", "platform/administrators", "space");
        assertTrue(getResult.contains("does not exist"));

    }

    @Test
    public void testUpdatePage() throws ParseException {
        restService.AddPageToSite("newPage4", "platform/administrators", "space");
        Map<String, String> attributeMap = new HashMap<String, String>();
        attributeMap.put("name", "newPage4");
        attributeMap.put("siteName", "platform/administrators");
        attributeMap.put("description", "Updated description");
        attributeMap.put("displayName", "UpdatedNewPage");
        attributeMap.put("access-permissions", "[\"*:/platform/administrators\"]");
        attributeMap.put("edit-permissions", "[\"member:/platform/users\"]");
        restService.updatePage(attributeMap, "space");
        String page = restService.getPage("newPage4", "platform/administrators", "space");
        Page newPage = jSonParser.pageParser(page);
        assertTrue(newPage.getName().equals("newPage4"));
        assertTrue(newPage.getDisplayName().equals("UpdatedNewPage"));
        assertTrue(newPage.getDescription().equals("Updated description"));
        assertTrue(newPage.getAccessPermissions().equals("[\"*:\\/platform\\/administrators\"]"));
        assertTrue(newPage.getEditPermissions().equals("[\"member:\\/platform\\/users\"]"));

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
        assertTrue(revNewPage.getName().equals("newPage4"));
        assertTrue(revNewPage.getDisplayName().equals("newPage4"));
        assertTrue(revNewPage.getDescription().equals("null"));
        assertTrue(revNewPage.getAccessPermissions().equals("[\"member:\\/platform\\/users\"]"));
        assertTrue(revNewPage.getEditPermissions().equals("[\"*:\\/platform\\/administrators\"]"));

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
        assertTrue(homepage.getName().equals("newAccount"));
        assertTrue(homepage.getDisplayName().equals("UpdatedNewAccount"));
        assertTrue(homepage.getDescription().equals("NewAccount updated description"));
        assertTrue(homepage.getAccessPermissions().equals("[\"manager:\\/platform\\/administrators\"]"));
        assertTrue(homepage.getEditPermissions().equals("[\"manager:\\/platform\\/administrators\"]"));

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
        assertTrue(homepageRestore.getName().equals("newAccount"));
        assertTrue(homepageRestore.getDisplayName().equals("New Account"));
        assertTrue(homepageRestore.getDescription().equals("null"));
        assertTrue(homepageRestore.getAccessPermissions().equals("[\"*:\\/platform\\/users\"]"));
        assertTrue(homepageRestore.getEditPermissions().equals("[\"*:\\/platform\\/guests\"]"));
        restService.deletePage("platform/administrators", "newPage4", "space");
    }

    @Test
    public void testDeletePages() throws ParseException {
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
                addedPages = addedPages + 1;
            }
        }
        assertEquals(addedPages, 0);

    }
}
