package org.gatein.rest.service.api;

import java.util.Map;

/**
 *
 * @author mgottval
 */
public interface RestServiceApi {
    
    void addSite(String site, String sitesName);
    
    String getSite(String siteName, String siteType);
    
    void AddPageToSite(String pagename, String sitname, String sitesName);
    
    String getPage(String pagename, String siteName, String siteType);
    
    String getAllSites(String sitesType);
    
    String getSitePages(String siteName, String siteType);
    
    void deleteSite(String siteName, String sitesType);
    
    void deletePage(String sitename, String pageName, String siteType);
    
    void updateSite(Map<String, String> attributeMap);
    
    void updatePage(Map<String, String> attributeMap, String siteType);
    
    void updatePage(String json, String siteType, String siteName, String pageName);
    
    String getNavigation(String siteType, String sitename, Boolean scope);
    
    String getNavigationNode(String siteType, String sitename, String nodePath);
    
    String getNode(String nodeName, String siteName, String siteType);
    
    void createNode(String nodeName, String siteName, String siteType);
    
    void deleteNode(String nodeName, String siteName, String siteType);
    
    void updateNode(Map<String, String> attributeMap, String siteName, String siteType);
    
    String getEmptySites(String siteType);
    
    void updateNodesPage(String nodesJSonPage, String siteName, String siteType, String nodeName);
    
    void updateSite(String json, String name, String type);
    
    void updateNode(String entityUpdate, String siteName, String siteType, String nodeName);
}
