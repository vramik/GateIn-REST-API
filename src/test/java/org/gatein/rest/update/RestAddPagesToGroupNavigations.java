package org.gatein.rest.update;

import java.io.File;
import org.gatein.rest.service.api.HelpingServiceApi;
import org.gatein.rest.service.impl.HelpingService;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author mgottval
 */
public class RestAddPagesToGroupNavigations {

    private HelpingServiceApi helpingService;

    @Before
    public void before() {
        helpingService = new HelpingService();
    }

    @Test
    public void AddPagesToGroupNavigationsAdminsUsers() {
        File groupTemplate = new File("src/main/resources/group.zip");
        helpingService.postHttpRequestAllSitesUpdateAtOnce("http://localhost:8080/rest/private/managed-components/template/group?importMode=merge&targetGroup=/platform/administrators&targetGroup=/platform/users", groupTemplate);
    }
}
