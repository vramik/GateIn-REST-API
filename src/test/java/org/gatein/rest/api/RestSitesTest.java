package org.gatein.rest.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.gatein.rest.constants.RestConstants.REST_API_URL;
import org.gatein.rest.entity.Site;
import org.gatein.rest.helper.JSonParser;
import org.gatein.rest.service.impl.RestService;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author vramik
 */
public class RestSitesTest {

    private RestService restService;
    private final JSonParser jSonParser = new JSonParser();

    @Before
    public void before() {
        restService = new RestService();
    }

    @Test
    public void testGetClassicSite() throws ParseException {
        System.out.println("**testGetClassicSite**");
        String site = restService.getSite("classic", "site");
        assertNotNull(site);
        Site classic = jSonParser.siteParser(site);
        assertEquals("classic", classic.getName());
        assertEquals("site", classic.getType());
        assertEquals("Classic", classic.getDisplayName());
        assertEquals("JBoss Portal default portal", classic.getDescription());
        assertEquals("JppSkin", classic.getSkin());
        assertEquals("en", classic.getLocale());
        assertEquals("[\"Everyone\"]", classic.getAccessPermissions());
        assertEquals("[\"*:\\/platform\\/administrators\"]", classic.getEditPermissions());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/sites\\/classic\\/pages\"}", classic.getPages());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/sites\\/classic\\/navigation\"}", classic.getNavigation());
    }

    @Test
    public void testGetMobileSite() throws ParseException {
        System.out.println("**testGetMobileSite**");
        String site = restService.getSite("mobile", "site");
        assertNotNull(site);
        Site mobile = jSonParser.siteParser(site);
        assertEquals("mobile", mobile.getName());
        assertEquals("site", mobile.getType());
        assertEquals("Mobile", mobile.getDisplayName());
        assertEquals("JBoss Portal default mobile portal", mobile.getDescription());
        assertEquals("ResponsiveSkin", mobile.getSkin());
        assertEquals("en", mobile.getLocale());
        assertEquals("[\"Everyone\"]", mobile.getAccessPermissions());
        assertEquals("[\"*:\\/platform\\/administrators\"]", mobile.getEditPermissions());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/sites\\/mobile\\/pages\"}", mobile.getPages());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/sites\\/mobile\\/navigation\"}", mobile.getNavigation());
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
        assertEquals("newSite", newSite.getName());
        assertEquals("site", newSite.getType());
        assertEquals("Basic Portal", newSite.getDisplayName());
        assertEquals("This is basic portal template", newSite.getDescription());
        assertEquals("Default", newSite.getSkin());
        assertEquals("en", newSite.getLocale());
        assertEquals("[\"Everyone\"]", newSite.getAccessPermissions());
        assertEquals("[\"*:\\/platform\\/administrators\"]", newSite.getEditPermissions());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/sites\\/newSite\\/pages\"}", newSite.getPages());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/sites\\/newSite\\/navigation\"}", newSite.getNavigation());
        restService.deleteSite("newSite", "site");
    }

    @Test
    public void testGetAllSites() throws ParseException {
        System.out.println("**testGetAllSites**");
        
        List<Site> sitesList = jSonParser.sitesParser(restService.getAllSites("site"));
        Assert.assertEquals(2, sitesList.size());

        restService.addSite("site1", "site");
        restService.addSite("site2", "site");
        restService.addSite("site3", "site");
        restService.addSite("site4", "site");
        
        sitesList = jSonParser.sitesParser(restService.getAllSites("site"));
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
        assertEquals("classic", classic.getName());
        assertEquals("site", classic.getType());
        assertEquals("ClassicUpdated", classic.getDisplayName());
        assertEquals("Description of Classic Updated", classic.getDescription());
        assertEquals("JppSkin", classic.getSkin());
        assertEquals("fr", classic.getLocale());
        assertEquals("[\"*:\\/platform\\/administrators\"]", classic.getAccessPermissions());
        assertEquals("[\"*:\\/platform\\/guests\"]", classic.getEditPermissions());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/sites\\/classic\\/pages\"}", classic.getPages());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/sites\\/classic\\/navigation\"}", classic.getNavigation());

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
        assertEquals("classic", classicRestore.getName());
        assertEquals("site", classicRestore.getType());
        assertEquals("Classic", classicRestore.getDisplayName());
        assertEquals("JBoss Portal default portal", classicRestore.getDescription());
        assertEquals("JppSkin", classicRestore.getSkin());
        assertEquals("en", classicRestore.getLocale());
        assertEquals("[\"Everyone\"]", classicRestore.getAccessPermissions());
        assertEquals("[\"*:\\/platform\\/administrators\"]", classicRestore.getEditPermissions());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/sites\\/classic\\/pages\"}", classicRestore.getPages());
        assertEquals("{\"url\":\"" + REST_API_URL + "\\/sites\\/classic\\/navigation\"}", classicRestore.getNavigation());

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
