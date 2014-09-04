/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gatein.rest.update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.impl.client.CloseableHttpClient;
import org.gatein.rest.constants.ConstantsService;
import org.gatein.rest.entity.Navigation;
import org.gatein.rest.entity.Node;
import org.gatein.rest.entity.Page;
import org.gatein.rest.helper.JSonParser;
import org.gatein.rest.service.api.HelpingServiceApi;
import org.gatein.rest.service.impl.HelpingService;
import org.gatein.rest.service.impl.RestService;
import org.json.simple.parser.ParseException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author mgottval
 */
public class RestEdit1Group {

    private CloseableHttpClient httpclient;
    private HelpingServiceApi helpingService;
    private RestService restService;
    private ConstantsService constantsService;
    private JSonParser jSonParser = new JSonParser();

    @Before
    public void before() {
        helpingService = new HelpingService();
        httpclient = helpingService.httpClientAuthenticationRootAny();
        constantsService = new ConstantsService();
        restService = new RestService(httpclient, helpingService, constantsService);
    }

    @Test
    public void testUpdateEcexBoardGroup() throws ParseException {
        //check /organization/management/executive-board updated
        String executiveBoardPagesString = restService.getSitePages("organization/management/executive-board", "space");
        List<Page> executiveBoardPages = jSonParser.pagesParser(executiveBoardPagesString);
        List<String> executiveBoardPagesNames = new ArrayList<>();
        for (Page page : executiveBoardPages) {
            executiveBoardPagesNames.add(page.getName());
        }
        assertTrue(executiveBoardPages.size() > 2);
        assertTrue(executiveBoardPagesNames.contains("petrucci"));
        assertTrue(executiveBoardPagesNames.contains("wikipedia"));

        String pettruciPageString = restService.getPage("petrucci", "organization/management/executive-board", "space");
        Page pettruciPage = jSonParser.pageParser(pettruciPageString);

        assertEquals("petrucci", pettruciPage.getName());
        assertEquals("Petrucci Music Library", pettruciPage.getDisplayName());
        assertEquals("[\"*:\\/platform\\/users\"]", pettruciPage.getAccessPermissions());
        assertEquals("[\"*:\\/organization\\/management\\/executive-board\"]", pettruciPage.getEditPermissions());

        String wikipediaPageString = restService.getPage("wikipedia", "organization/management/executive-board", "space");
        Page wikipediaPage = jSonParser.pageParser(wikipediaPageString);

        assertEquals("wikipedia", wikipediaPage.getName());
        assertEquals("Wikipedia", wikipediaPage.getDisplayName());
        assertEquals("[\"*:\\/platform\\/users\"]", wikipediaPage.getAccessPermissions());
        assertEquals("[\"*:\\/organization\\/management\\/executive-board\"]", wikipediaPage.getEditPermissions());

        String execBoardNavigationString = restService.getNavigation("space", "organization/management/executive-board", false);
        Navigation execBoardNavigation = jSonParser.navigationParser(execBoardNavigationString);
        List<Node> execBoardNavigationNodes = execBoardNavigation.getNodes();
        List<String> execBoardNavigationNodeNames = new ArrayList<>();
        for (Node node : execBoardNavigationNodes) {
            execBoardNavigationNodeNames.add(node.getName());
        }
        assertTrue(execBoardNavigationNodeNames.contains("newlinks"));

//        String nodeNewlinksString = restService.getNavigationNode("space", "/platform/users", "mylink");
//        Node newlinksNode = jSonParser.nodeParser(nodeNewlinksString);
//        assertEquals("newlinks",execBoardNavigationNodeNewlinks.getName());
//        assertEquals("New Links",execBoardNavigationNodeNewlinks.getDisplayName());
//        assertTrue(execBoardNavigationNodeNewlinks.getChildren().size()==2);
        
        // chec non changed groups
        String usersPagesString = restService.getSitePages("platform/users", "space");
        List<Page> usersPages = jSonParser.pagesParser(usersPagesString);
        List<String> usersPagesNames = new ArrayList<>();
        for (Page page : usersPages) {
            usersPagesNames.add(page.getName());
        }
        assertFalse(usersPagesNames.contains("petrucci"));
        assertFalse(usersPagesNames.contains("wikipedia"));
        
        String administratorsPagesString = restService.getSitePages("platform/administrators", "space");
        List<Page> administratorsPages = jSonParser.pagesParser(administratorsPagesString);
        List<String> administratorsPagesNames = new ArrayList<>();
        for (Page page : administratorsPages) {
            administratorsPagesNames.add(page.getName());
        }
        assertFalse(administratorsPagesNames.contains("petrucci"));
        assertFalse(administratorsPagesNames.contains("wikipedia"));
        
        String guestsPagesString = restService.getSitePages("platform/guests", "space");
        List<Page> guestsPages = jSonParser.pagesParser(guestsPagesString);
        List<String> guestsPagesNames = new ArrayList<>();
        for (Page page : guestsPages) {
            guestsPagesNames.add(page.getName());
        }
        assertFalse(guestsPagesNames.contains("petrucci"));
        assertFalse(guestsPagesNames.contains("wikipedia"));
    }

    @Test
    public void testOverwritePlatformUsersGroup() throws ParseException {
        String platformUsersPagesString = restService.getSitePages("platform/users", "space");
        List<Page> platformUsersPages = jSonParser.pagesParser(platformUsersPagesString);
        List<String> platformUsersPagesNames = new ArrayList<>();
        for (Page page : platformUsersPages) {
            platformUsersPagesNames.add(page.getName());
        }
        assertTrue(platformUsersPages.size() == 2);
        assertTrue(platformUsersPagesNames.contains("petrucci"));
        assertTrue(platformUsersPagesNames.contains("wikipedia"));

        String pettruciPageString = restService.getPage("petrucci", "platform/users", "space");
        Page pettruciPage = jSonParser.pageParser(pettruciPageString);

        assertEquals("petrucci", pettruciPage.getName());
        assertEquals("Petrucci Music Library", pettruciPage.getDisplayName());
        assertEquals("[\"*:\\/platform\\/users\"]", pettruciPage.getAccessPermissions());
        assertEquals("[\"*:\\/platform\\/users\"]", pettruciPage.getEditPermissions());

        String wikipediaPageString = restService.getPage("wikipedia", "platform/users", "space");
        Page wikipediaPage = jSonParser.pageParser(wikipediaPageString);

        assertEquals("wikipedia", wikipediaPage.getName());
        assertEquals("Wikipedia", wikipediaPage.getDisplayName());
        assertEquals("[\"*:\\/platform\\/users\"]", wikipediaPage.getAccessPermissions());
        assertEquals("[\"*:\\/platform\\/users\"]", wikipediaPage.getEditPermissions());

        String platformUsersNavigationString = restService.getNavigation("space", "platform/users", false);
        Navigation platformUsersNavigation = jSonParser.navigationParser(platformUsersNavigationString);
        List<Node> platformUsersNavigationNodes = platformUsersNavigation.getNodes();
        List<String> platformUsersNavigationNodeNames = new ArrayList<>();
        for (Node node : platformUsersNavigationNodes) {
            platformUsersNavigationNodeNames.add(node.getName());
        }
        assertTrue(platformUsersNavigationNodeNames.size()==1);
        assertTrue(platformUsersNavigationNodeNames.contains("newlinks"));

    }

    @After
    public void after() {
        try {
            httpclient.close();
        } catch (IOException ex) {
            Logger.getLogger(RestEdit1Group.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
