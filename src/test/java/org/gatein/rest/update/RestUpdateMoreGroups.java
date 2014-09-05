package org.gatein.rest.update;

import java.util.ArrayList;
import java.util.List;
import org.gatein.rest.entity.Navigation;
import org.gatein.rest.entity.Node;
import org.gatein.rest.entity.Page;
import org.gatein.rest.helper.JSonParser;
import org.gatein.rest.service.impl.RestService;
import org.json.simple.parser.ParseException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author mgottval
 */
public class RestUpdateMoreGroups {

    private RestService restService;
    private final JSonParser jSonParser = new JSonParser();

    @Before
    public void before() {
        restService = new RestService();

    }

    @Test
    public void testUpdateAllGroups() throws ParseException {
        //check /organization/management/executive-board updated
        String executiveBoardPagesString = restService.getSitePages("organization/management/executive-board", "space");
        List<Page> executiveBoardPages = jSonParser.pagesParser(executiveBoardPagesString);
        List<String> executiveBoardPagesNames = new ArrayList<>();
        for (Page page : executiveBoardPages) {
            executiveBoardPagesNames.add(page.getName());
        }
        assertTrue(executiveBoardPagesNames.contains("petrucci"));
        assertTrue(executiveBoardPagesNames.contains("wikipedia"));

        checkPetruciWIkiPages("organization/management/executive-board");

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
        assertTrue(usersPagesNames.contains("petrucci"));
        assertTrue(usersPagesNames.contains("wikipedia"));

        checkPetruciWIkiPages("platform/users");

        String administratorsPagesString = restService.getSitePages("platform/administrators", "space");
        List<Page> administratorsPages = jSonParser.pagesParser(administratorsPagesString);
        List<String> administratorsPagesNames = new ArrayList<>();
        for (Page page : administratorsPages) {
            administratorsPagesNames.add(page.getName());
        }
        assertTrue(administratorsPagesNames.contains("petrucci"));
        assertTrue(administratorsPagesNames.contains("wikipedia"));

        checkPetruciWIkiPages("platform/administrators");

        String guestsPagesString = restService.getSitePages("platform/guests", "space");
        List<Page> guestsPages = jSonParser.pagesParser(guestsPagesString);
        List<String> guestsPagesNames = new ArrayList<>();
        for (Page page : guestsPages) {
            guestsPagesNames.add(page.getName());
        }
        assertTrue(guestsPagesNames.contains("petrucci"));
        assertTrue(guestsPagesNames.contains("wikipedia"));

        checkPetruciWIkiPages("platform/guests");
    }

    @Test
    public void testUpdate2GroupsExecBoardAndPlatformUsers() throws ParseException {
        //check /organization/management/executive-board updated
        String executiveBoardPagesString = restService.getSitePages("organization/management/executive-board", "space");
        List<Page> executiveBoardPages = jSonParser.pagesParser(executiveBoardPagesString);
        List<String> executiveBoardPagesNames = new ArrayList<>();
        for (Page page : executiveBoardPages) {
            executiveBoardPagesNames.add(page.getName());
        }
        assertTrue(executiveBoardPagesNames.contains("petrucci"));
        assertTrue(executiveBoardPagesNames.contains("wikipedia"));

        checkPetruciWIkiPages("organization/management/executive-board");

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
        assertTrue(usersPagesNames.contains("petrucci"));
        assertTrue(usersPagesNames.contains("wikipedia"));

        checkPetruciWIkiPages("platform/users");

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

    public void checkPetruciWIkiPages(String spaceName) throws ParseException {
        String pettruciPageString = restService.getPage("petrucci", spaceName, "space");
        Page pettruciPage = jSonParser.pageParser(pettruciPageString);

        assertEquals("petrucci", pettruciPage.getName());
        assertEquals("Petrucci Music Library", pettruciPage.getDisplayName());
        assertEquals("[\"*:\\/platform\\/users\"]", pettruciPage.getAccessPermissions());
        assertEquals("[\"*:\\/" + spaceName.replaceAll("/", "\\\\/") + "\"]", pettruciPage.getEditPermissions());
        

        String wikipediaPageString = restService.getPage("wikipedia", spaceName, "space");
        Page wikipediaPage = jSonParser.pageParser(wikipediaPageString);

        assertEquals("wikipedia", wikipediaPage.getName());
        assertEquals("Wikipedia", wikipediaPage.getDisplayName());
        assertEquals("[\"*:\\/platform\\/users\"]", wikipediaPage.getAccessPermissions());
        assertEquals("[\"*:\\/" + spaceName.replaceAll("/", "\\\\/") + "\"]", wikipediaPage.getEditPermissions());

        String execBoardNavigationString = restService.getNavigation("space", spaceName, false);
        Navigation execBoardNavigation = jSonParser.navigationParser(execBoardNavigationString);
        List<Node> execBoardNavigationNodes = execBoardNavigation.getNodes();
        List<String> execBoardNavigationNodeNames = new ArrayList<>();
        for (Node node : execBoardNavigationNodes) {
            execBoardNavigationNodeNames.add(node.getName());
        }
        assertTrue(execBoardNavigationNodeNames.contains("newlinks"));
    }
}
