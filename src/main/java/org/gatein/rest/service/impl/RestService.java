package org.gatein.rest.service.impl;

import java.util.Map;
import org.apache.http.impl.client.CloseableHttpClient;
import org.gatein.rest.constants.ConstantsService;
import static org.gatein.rest.constants.RestConstants.REST_API_URL;
import org.gatein.rest.service.api.HelpingServiceApi;
import org.gatein.rest.service.api.RestServiceApi;

/**
 *
 * @author mgottval
 */
public class RestService implements RestServiceApi {

    private HelpingServiceApi helpingService;
    private ConstantsService constantsService;
    private CloseableHttpClient httpclient;

    public RestService() {
    }

    public RestService(CloseableHttpClient httpclient, HelpingServiceApi helpingService, ConstantsService constantsService) {
        this.httpclient = httpclient;
        this.helpingService = helpingService;
        this.constantsService = constantsService;
    }

    @Override
    public void addSite(String siteName, String siteType) {
//        httpclient = helpingService.httpClientAuthenticationRootAny();
        helpingService.addHttpRequest(constantsService.createSiteUrl(siteType, siteName), httpclient);
    }

    @Override
    public String getSite(String siteName, String siteType) {
//        helpingService.httpClientAuthenticationRootAny();
        return helpingService.getHttpRequest(constantsService.createSiteUrl(siteType, siteName), httpclient);

    }

    @Override
    public void AddPageToSite(String pageName, String siteName, String siteType) {

//        helpingService.httpClientAuthenticationRootAny();
        helpingService.addHttpRequest(constantsService.createPageUrl(siteType, siteName, pageName), httpclient);
    }

    @Override
    public String getPage(String pagename, String siteName, String siteType) {
//        helpingService.httpClientAuthenticationRootAny();
        return helpingService.getHttpRequest(constantsService.createPageUrl(siteType, siteName, pagename), httpclient);
    }

    @Override
    public String getAllSites(String siteType) {
//        helpingService.httpClientAuthenticationRootAny();
        return helpingService.getHttpRequest(REST_API_URL + "/" + siteType + "s", httpclient);
    }

    @Override
    public String getEmptySites(String siteType) {
        // helpingService.httpClientAuthenticationRootAny(httpclient);
        return helpingService.getHttpRequest(REST_API_URL + "/" + siteType + "s?emptySites=true&limit=30", httpclient);
    }

    @Override
    public String getSitePages(String siteName, String siteType) {
        // helpingService.httpClientAuthenticationRootAny(httpclient);
        return helpingService.getHttpRequest(constantsService.createPageUrl(siteType, siteName, ""), httpclient);

    }

    @Override
    public void deleteSite(String siteName, String siteType) {
        // helpingService.httpClientAuthenticationRootAny(httpclient);
        helpingService.deleteHttpRequest(constantsService.createSiteUrl(siteType, siteName), httpclient);
    }

    @Override
    public void deletePage(String sitename, String pageName, String siteType) {
        // helpingService.httpClientAuthenticationRootAny(httpclient);
        helpingService.deleteHttpRequest(constantsService.createPageUrl(siteType, sitename, pageName), httpclient);
    }

    @Override
    public void updateSite(Map<String, String> attributeMap) {
        String entityUpdate = "{";
        String siteName = null;
        String siteType = null;
        for (Map.Entry<String, String> entry : attributeMap.entrySet()) {
            if (entry.getKey().equals("name")) {
                siteName = entry.getValue();
            }
            if (entry.getKey().equals("type")) {
                siteType = entry.getValue();
            }
            if (!entry.getKey().equals("access-permissions") && !entry.getKey().equals("edit-permissions")) {
                entityUpdate = entityUpdate + "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\",";
            }
            if (entry.getKey().equals("access-permissions")) {
                entityUpdate = entityUpdate + "\"" + entry.getKey() + "\":" + entry.getValue() + ",";
            }
            if (entry.getKey().equals("edit-permissions")) {
                entityUpdate = entityUpdate + "\"" + entry.getKey() + "\":" + entry.getValue() + ",";
            }

        }
        entityUpdate = entityUpdate.substring(0, entityUpdate.length() - 1) + "}";
        // helpingService.httpClientAuthenticationRootAny(httpclient);
        helpingService.updateHttpRequest(constantsService.createSiteUrl(siteType, siteName), httpclient, entityUpdate);
    }

    @Override
    public void updateSite(String entityUpdate, String siteName, String siteType) {
        // helpingService.httpClientAuthenticationRootAny(httpclient);
        helpingService.updateHttpRequest(constantsService.createSiteUrl(siteType, siteName), httpclient, entityUpdate);
    }

    @Override
    public void updateNode(String entityUpdate, String siteName, String siteType, String nodeName) {
        // helpingService.httpClientAuthenticationRootAny(httpclient);
        helpingService.updateHttpRequest(constantsService.createNavigationNodeUrl(siteType, siteName, nodeName), httpclient, entityUpdate);
    }

    @Override
    public void updatePage(Map<String, String> attributeMap, String siteType) {
        String entityUpdate = "{";
        String siteName = null;
        String pageName = null;
        for (Map.Entry<String, String> entry : attributeMap.entrySet()) {
            if (entry.getKey().equals("siteName")) {
                siteName = entry.getValue();
            }
            if (entry.getKey().equals("name")) {
                pageName = entry.getValue();
            }
            if (!entry.getKey().equals("access-permissions") && !entry.getKey().equals("edit-permissions")) {
                entityUpdate = entityUpdate + "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\",";
            }
            if (entry.getKey().equals("access-permissions")) {
                entityUpdate = entityUpdate + "\"" + entry.getKey() + "\":" + entry.getValue() + ",";
            }
            if (entry.getKey().equals("edit-permissions")) {
                entityUpdate = entityUpdate + "\"" + entry.getKey() + "\":" + entry.getValue() + ",";
            }

        }
        entityUpdate = entityUpdate.substring(0, entityUpdate.length() - 1) + "}";

        // helpingService.httpClientAuthenticationRootAny(httpclient);
        helpingService.updateHttpRequest(constantsService.createPageUrl(siteType, siteName, pageName), httpclient, entityUpdate);
    }

    @Override
    public String getNavigation(String siteType, String siteName, Boolean scope) {
        // helpingService.httpClientAuthenticationRootAny(httpclient);
//            String append;
//            if (scope) {
//                append = "?scope=1";
//            } else {
//                append = "";
//            }
        return helpingService.getHttpRequest(constantsService.createNavigationNodeUrl(siteType, siteName, ""), httpclient);

    }

    @Override
    public String getNavigationNode(String siteType, String siteName, String node) {
        return helpingService.getHttpRequest(constantsService.createNavigationNodeUrl(siteType, siteName, node), httpclient);
    }

//
    @Override
    public String getNode(String nodeName, String siteName, String siteType) {
        // helpingService.httpClientAuthenticationRootAny(httpclient);
        return helpingService.getHttpRequest(constantsService.createNavigationNodeUrl(siteType, siteName, nodeName), httpclient);

    }

    @Override
    public void createNode(String nodeName, String siteName, String siteType) {
        // helpingService.httpClientAuthenticationRootAny(httpclient);
        helpingService.addHttpRequest(constantsService.createNavigationNodeUrl(siteType, siteName, nodeName), httpclient);
    }

    @Override
    public void deleteNode(String nodeName, String siteName, String siteType) {
        // helpingService.httpClientAuthenticationRootAny(httpclient);
        helpingService.deleteHttpRequest(constantsService.createNavigationNodeUrl(siteType, siteName, nodeName), httpclient);
    }

    @Override
    public void updateNode(Map<String, String> attributeMap, String siteName, String siteType) {
        String entityUpdate = "{";
        String nodeName = null;
        for (Map.Entry<String, String> entry : attributeMap.entrySet()) {
            if (entry.getKey().equals("name")) {
                nodeName = entry.getValue();
            }
            if (!entry.getKey().equals("access-permissions") && !entry.getKey().equals("edit-permissions") && !entry.getKey().equals("visibility")) {
                entityUpdate = entityUpdate + "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\",";
            }
            if (entry.getKey().equals("access-permissions")) {
                entityUpdate = entityUpdate + "\"" + entry.getKey() + "\":" + entry.getValue() + ",";
            }
            if (entry.getKey().equals("edit-permissions")) {
                entityUpdate = entityUpdate + "\"" + entry.getKey() + "\":" + entry.getValue() + ",";
            }
            if (entry.getKey().equals("visibility")) {
                entityUpdate = entityUpdate + "\"" + entry.getKey() + "\":" + entry.getValue() + ",";
            }

        }

        entityUpdate = entityUpdate.substring(0, entityUpdate.length() - 1) + "}";

        // helpingService.httpClientAuthenticationRootAny(httpclient);
        helpingService.updateHttpRequest(constantsService.createNavigationNodeUrl(siteType, siteName, nodeName), httpclient, entityUpdate);
    }

    @Override
    public void updateNodesPage(String nodesJSonPage, String siteName, String siteType, String nodeName) {

        // helpingService.httpClientAuthenticationRootAny(httpclient);
        helpingService.updateHttpRequest(constantsService.createNavigationNodeUrl(siteType, siteName, nodeName), httpclient, nodesJSonPage);
    }

    @Override
    public void updatePage(String json, String siteType, String siteName, String pageName) {

        // // helpingService.httpClientAuthenticationRootAny(httpclient);
        //  helpingService.updateHttpRequest(constantsService.createPageUrl(siteType, siteName, pageName), httpclient, entity, json);
        helpingService.updateHttpRequest(REST_API_URL + "/" + siteType + "s/" + siteName + "/pages/" + pageName, httpclient, json);
    }
}
