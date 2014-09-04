package org.gatein.rest.api;

/**
 *
 * @author mgottval
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gatein.rest.constants.ConstantsService;
import org.gatein.rest.helper.JSonParser;
import org.gatein.rest.entity.Site;
import org.gatein.rest.service.impl.HelpingService;
import org.gatein.rest.service.impl.RestService;
import org.gatein.rest.service.api.HelpingServiceApi;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

public class RestSitesTest {

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
    public void testGetClassicSite() throws ParseException {
        System.out.println("**testGetClassicSite**");
        String site = restService.getSite("classic", "site");
        assertNotNull(site);
        System.out.println(site);
        Site classic = jSonParser.siteParser(site);
        assertTrue((classic.getName()).equals("classic"));
        assertTrue((classic.getType()).equals("site"));
        assertTrue((classic.getDisplayName()).equals("Classic"));
        assertTrue((classic.getDescription()).equals("JBoss Portal default portal"));
        assertTrue((classic.getSkin()).equals("JppSkin"));
        assertTrue((classic.getLocale()).equals("en"));
        assertTrue((classic.getAccessPermissions()).equals("[\"Everyone\"]"));
        assertTrue((classic.getEditPermissions()).equals("[\"*:\\/platform\\/administrators\"]"));
        assertTrue((classic.getPages()).equals("{\"url\":\"" + REST_API_URL + "\\/sites\\/classic\\/pages\"}"));
        assertTrue((classic.getNavigation()).equals("{\"url\":\"" + REST_API_URL + "\\/sites\\/classic\\/navigation\"}"));

    }

    @Test
    public void testGetMobileSite() throws ParseException {
        System.out.println("**testGetMobileSite**");
        String site = restService.getSite("mobile", "site");
        assertNotNull(site);
        Site mobile = jSonParser.siteParser(site);
        assertTrue((mobile.getName()).equals("mobile"));
        assertTrue((mobile.getType()).equals("site"));
        assertTrue((mobile.getDisplayName()).equals("Mobile"));
        assertTrue((mobile.getDescription()).equals("JBoss Portal default mobile portal"));
        assertTrue((mobile.getSkin()).equals("ResponsiveSkin"));
        assertTrue((mobile.getLocale()).equals("en"));
        assertTrue((mobile.getAccessPermissions()).equals("[\"Everyone\"]"));
        assertTrue((mobile.getEditPermissions()).equals("[\"*:\\/platform\\/administrators\"]"));
        assertTrue((mobile.getPages()).equals("{\"url\":\"" + REST_API_URL + "\\/sites\\/mobile\\/pages\"}"));
        assertTrue((mobile.getNavigation()).equals("{\"url\":\"" + REST_API_URL + "\\/sites\\/mobile\\/navigation\"}"));
    }

    @Test
    public void testNonExistingSite() {
        System.out.println("**testNonExistingSite**");
        String site = restService.getSite("nonSite", "site");
        assertTrue(site.contains("Site not found for Site.Id[type=site, name=nonSite]"));
    }

    @Test
    public void testAddSite() throws ParseException {
        System.out.println("**testAddSite**");
        restService.addSite("newSite", "site");
        String site = restService.getSite("newSite", "site");
        assertNotNull(site);
        Site newSite = jSonParser.siteParser(site);
        assertTrue((newSite.getName()).equals("newSite"));
        assertTrue((newSite.getType()).equals("site"));
        assertTrue((newSite.getDisplayName()).equals("Basic Portal"));
        assertTrue((newSite.getDescription()).equals("This is basic portal template"));
        assertTrue((newSite.getSkin()).equals("Default"));
        assertTrue((newSite.getLocale()).equals("en"));
        assertTrue((newSite.getAccessPermissions()).equals("[\"Everyone\"]"));
        assertTrue((newSite.getEditPermissions()).equals("[\"*:\\/platform\\/administrators\"]"));
        assertTrue((newSite.getPages()).equals("{\"url\":\"" + REST_API_URL + "\\/sites\\/newSite\\/pages\"}"));
        assertTrue((newSite.getNavigation()).equals("{\"url\":\"" + REST_API_URL + "\\/sites\\/newSite\\/navigation\"}"));
        restService.deleteSite("newSite", "site");
    }

    @Test
    public void testGetAllSites() throws ParseException {
        System.out.println("**testGetAllSites**");
        String sites = restService.getAllSites("site");
        List<Site> sitesList = jSonParser.sitesParser(sites);

        Assert.assertEquals(2, sitesList.size());

        restService.addSite("site1", "site");
        restService.addSite("site2", "site");
        restService.addSite("site3", "site");
        restService.addSite("site4", "site");
        sites = restService.getAllSites("site");
        sitesList = jSonParser.sitesParser(sites);
        Assert.assertEquals(6, sitesList.size());
        
        restService.deleteSite("site1", "site");
        restService.deleteSite("site2", "site");
        restService.deleteSite("site3", "site");
        restService.deleteSite("site4", "site");
    }

    @Test
    public void testUpdateSite() throws ParseException {
        System.out.println("**testUpdateSite**");
        Site classic;
        Map<String, String> attributes = new HashMap<>();
        attributes.put("name", "classic");
        attributes.put("type", "site");
        attributes.put("displayName", "ClassicUpdated");
        attributes.put("description", "Description of Classic Updated");
        attributes.put("locale", "fr");
        attributes.put("access-permissions", "[\"*:/platform/administrators\"]");
        attributes.put("edit-permissions", "[\"*:/platform/guests\"]");
        restService.updateSite(attributes);
        String site = restService.getSite("classic", "site");
        classic = jSonParser.siteParser(site);
        assertTrue((classic.getName()).equals("classic"));
        assertTrue((classic.getType()).equals("site"));
        assertTrue((classic.getDisplayName()).equals("ClassicUpdated"));
        assertTrue((classic.getDescription()).equals("Description of Classic Updated"));
        assertTrue((classic.getSkin()).equals("JppSkin"));
        assertTrue((classic.getLocale()).equals("fr"));
        assertTrue((classic.getAccessPermissions()).equals("[\"*:\\/platform\\/administrators\"]"));
        assertTrue((classic.getEditPermissions()).equals("[\"*:\\/platform\\/guests\"]"));
        assertTrue((classic.getPages()).equals("{\"url\":\"" + REST_API_URL + "\\/sites\\/classic\\/pages\"}"));
        assertTrue((classic.getNavigation()).equals("{\"url\":\"" + REST_API_URL + "\\/sites\\/classic\\/navigation\"}"));

        attributes.clear();
        attributes.put("name", "classic");
        attributes.put("type", "site");
        attributes.put("displayName", "Classic");
        attributes.put("description", "JBoss Portal default portal");
        attributes.put("locale", "en");
        attributes.put("access-permissions", "[\"Everyone\"]");
        attributes.put("edit-permissions", "[\"*:/platform/administrators\"]");
        restService.updateSite(attributes);
        String siteRestore = restService.getSite("classic", "site");
        Site classicRestore = jSonParser.siteParser(siteRestore);
        assertTrue((classicRestore.getName()).equals("classic"));
        assertTrue((classicRestore.getType()).equals("site"));
        assertTrue((classicRestore.getDisplayName()).equals("Classic"));
        assertTrue((classicRestore.getDescription()).equals("JBoss Portal default portal"));
        assertTrue((classicRestore.getSkin()).equals("JppSkin"));
        assertTrue((classicRestore.getLocale()).equals("en"));
        assertTrue((classicRestore.getAccessPermissions()).equals("[\"Everyone\"]"));
        assertTrue((classicRestore.getEditPermissions()).equals("[\"*:\\/platform\\/administrators\"]"));
        assertTrue((classicRestore.getPages()).equals("{\"url\":\"" + REST_API_URL + "\\/sites\\/classic\\/pages\"}"));
        assertTrue((classicRestore.getNavigation()).equals("{\"url\":\"" + REST_API_URL + "\\/sites\\/classic\\/navigation\"}"));

    }

    @Test
    public void testDeleteSites() {
        System.out.println("**testDeleteSites**");
        restService.addSite("site1", "site");
        restService.addSite("site2", "site");
        restService.addSite("site3", "site");
        restService.addSite("site4", "site");
        restService.deleteSite("site1", "site");
        restService.deleteSite("site2", "site");
        restService.deleteSite("site3", "site");
        restService.deleteSite("site4", "site");
    }
}
