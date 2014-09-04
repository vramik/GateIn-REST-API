package org.gatein.rest.constants;

import static org.gatein.rest.constants.RestConstants.*;

public class ConstantsService {

    public String createPageEntity(String pageName, String siteType, String siteName) {
        return PAGE_STRING_ENTITY.replace("${pageName}", pageName).replace("${siteType}", siteType).replace("${siteName}", siteName);
    }

    public String createSiteEntity(String siteName, String siteType) {
        return SITE_STRING_ENTITY.replace("${siteName}", siteName).replace("${siteType}", siteType);
    }

    public String createNavigationNodeUrl(String siteType, String siteName, String nodeName) {
        return NAVIGATION_URL.replace("${siteType}", siteType).replace("${siteName}", siteName).replace("${nodeName}", nodeName);
    }

    public String createSiteUrl(String siteType, String siteName) {
        return SITE_URL.replace("${siteType}", siteType).replace("${siteName}", siteName);
    }

    public String createPageUrl(String siteType, String siteName, String pageName) {
        return PAGE_URL.replace("${siteType}", siteType).replace("${siteName}", siteName).replace("${pageName}", pageName);
    }
}
