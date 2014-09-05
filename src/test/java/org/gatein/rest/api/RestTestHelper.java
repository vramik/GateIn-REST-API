package org.gatein.rest.api;

import org.gatein.rest.entity.Page;
import org.gatein.rest.entity.Site;
import static org.junit.Assert.assertEquals;


/**
 *
 * TODO
 * 
 * @author vramik
 */
public class RestTestHelper {
    public static void assertEqualsSite(Site expectedSite, Site actualSite) {
        assertEquals(expectedSite.getName(), actualSite.getName());
        assertEquals(expectedSite.getType(), actualSite.getType());
        assertEquals(expectedSite.getDisplayName(), actualSite.getDisplayName());
        assertEquals(expectedSite.getDescription(), actualSite.getDescription());
        assertEquals(expectedSite.getSkin(), actualSite.getSkin());
        assertEquals(expectedSite.getLocale(), actualSite.getLocale());
        assertEquals(expectedSite.getAccessPermissions(), actualSite.getAccessPermissions());
        assertEquals(expectedSite.getEditPermissions(), actualSite.getEditPermissions());
        assertEquals(expectedSite.getPages(), actualSite.getPages());
        assertEquals(expectedSite.getNavigation(), actualSite.getNavigation());
    }
    
    public static void assertEqualsPage(Page expectedPage, Page actualPage) {
            assertEquals(expectedPage.getSiteName(), actualPage.getSiteName());
            assertEquals(expectedPage.getSiteType(), actualPage.getSiteType());


    }
}
