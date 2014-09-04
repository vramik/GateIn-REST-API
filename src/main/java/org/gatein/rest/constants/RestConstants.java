package org.gatein.rest.constants;

public class RestConstants {
    public static final String HOST = System.getProperty("portal.host", "localhost");
    public static final String REST_API_URL = "http://" + HOST + ":8080/rest/private/managed-components/api";
    public static final String PAGE_STRING_ENTITY = "{\"name\":\"${pageName}\",\"siteType\":\"${siteType}\",\"siteName\":\"${siteName}\",\"url\":\"" + REST_API_URL + "/${siteType}s/${siteName}/pages/${pageName}\"}";
    public static final String SITE_STRING_ENTITY = "{\"name\":\"${siteName}\",\"type\":\"${siteType}\",\"url\":\"" + REST_API_URL + "/${siteType}s/${siteName}\"}";
    public static final String NAVIGATION_URL = REST_API_URL + "/${siteType}s/${siteName}/navigation/${nodeName}";
    public static final String SITE_URL =  REST_API_URL + "/${siteType}s/${siteName}";
    public static final String PAGE_URL = REST_API_URL + "/${siteType}s/${siteName}/pages/${pageName}";
}
