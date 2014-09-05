package org.gatein.rest.update;

import java.util.List;
import org.gatein.rest.entity.Site;
import org.gatein.rest.helper.JSonParser;
import org.gatein.rest.service.impl.RestService;
import org.json.simple.parser.ParseException;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author mgottval
 */
public class AddDashboards {
    
    private RestService restService;
    private final JSonParser jSonParser = new JSonParser();

    @Before
    public void before() {
        restService = new RestService();
        
        restService.addSite("mary", "dashboard");
        restService.addSite("john", "dashboard");
        restService.addSite("demo", "dashboard");

    }
    
    @Test
    public void addDashboards() throws ParseException {
        String allSites = restService.getAllSites("dashboard");
        List<Site> dashboardList = jSonParser.sitesParser(allSites);
        assertEquals(4, dashboardList.size());
    }
}
