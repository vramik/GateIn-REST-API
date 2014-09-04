package org.gatein.rest.helper;

import java.util.ArrayList;
import java.util.List;
import org.gatein.rest.entity.Page;
import org.gatein.rest.entity.Site;

import org.gatein.rest.entity.Attribute;
import org.gatein.rest.entity.Navigation;
import org.gatein.rest.entity.Node;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author mgottval
 */
public class JSonParser {

    public Site siteParser(String jSonSite) throws ParseException {
        Site site = new Site();
        List<Attribute> attributes = null;
        JSONObject json = (JSONObject) new JSONParser().parse(jSonSite);
        site.setName(json.get("name").toString());
        site.setType(json.get("type").toString());
        if ((json.get("displayName")) == null) {
            site.setDisplayName("null");
        } else {
            site.setDisplayName(json.get("displayName").toString());
        }
        if (json.get("description") == null) {
            site.setDescription("null");
        } else {
            site.setDescription(json.get("description").toString());
        }
        site.setSkin(json.get("skin").toString());
        site.setLocale(json.get("locale").toString());
        site.setAccessPermissions(json.get("access-permissions").toString());
        site.setEditPermissions(json.get("edit-permissions").toString());
        if (json.get("attributes") != null) {
            String nodes = json.get("attributes").toString().replace("[", "").replace("]", "");
            String[] attributesArray = nodes.split("(?<=}),");
            attributes = new ArrayList<>();
            Attribute attribute;
            for (int i = 0; i < attributesArray.length; i++) {
                JSONObject jsonAttr = (JSONObject) new JSONParser().parse(attributesArray[i]);
                attribute = new Attribute();
                attribute.setKey(jsonAttr.get("key").toString());
                attribute.setValue(jsonAttr.get("value").toString());
                attributes.add(attribute);
            }
        }
        site.setAttributes(attributes);
        site.setPages(json.get("pages").toString());
        site.setNavigation(json.get("navigation").toString().toString());

        return site;
    }

    public Page pageParser(String jSonPage) throws ParseException {
        Page page = new Page();
        JSONObject json = (JSONObject) new JSONParser().parse(jSonPage);
        page.setName(json.get("name").toString());
        page.setDisplayName(json.get("displayName").toString());
        if (json.get("description") == null) {
            page.setDescription("null");
        } else {
            page.setDescription(json.get("description").toString());
        }
        page.setAccessPermissions(json.get("access-permissions").toString());
        if (json.containsKey("edit-permissions")) {
            page.setEditPermissions(json.get("edit-permissions").toString());
        }

        return page;
    }

    public List<Page> pagesParser(String jSonPages) throws ParseException {
        List<Page> pagesList = new ArrayList<Page>();
        Page page;
        JSONObject json;

        String[] sitesArray = jSonPages.replace("[", "").replace("]", "").split("(?<=}),");
        for (int i = 0; i < sitesArray.length; i++) {
            json = (JSONObject) new JSONParser().parse(sitesArray[i]);
            page = new Page();
            page.setName(json.get("name").toString());
            page.setSiteType(json.get("siteType").toString());
            page.setSiteName(json.get("siteName").toString());
            page.setURL(json.get("url").toString());
            pagesList.add(page);
        }

        return pagesList;
    }

    public List<Site> sitesParser(String jSonSites) throws ParseException {
        List<Site> sitesList = new ArrayList<Site>();
        Site site;
        JSONObject json;

        String[] sitesArray = jSonSites.replace("[", "").replace("]", "").split("(?<=}),");
        for (int i = 0; i < sitesArray.length; i++) {
            json = (JSONObject) new JSONParser().parse(sitesArray[i]);
            site = new Site();
            site.setName(json.get("name").toString());
            site.setType(json.get("type").toString());
            site.setNavigation(json.get("url").toString());
            sitesList.add(site);
        }

        return sitesList;
    }

    public Node nodeParser(String jSonNode) throws ParseException {
        Node node = new Node();
        Page pageEntity = new Page();
        List<Page> children = new ArrayList<>();
        List<String> displayNames = new ArrayList<>();
        JSONObject json = (JSONObject) new JSONParser().parse(jSonNode);

        node.setName(json.get("name").toString());
        node.setUri(json.get("uri").toString());
        node.setIsVisible(String.valueOf(json.get("isVisible")).toString());
        JSONObject visibility = (JSONObject) json.get("visibility");

        node.setVisibility(visibility.get("status").toString());

        if (json.get("iconName") == null) {
            node.setIconName("null");
        } else {
            node.setIconName(json.get("iconName").toString());
        }
        node.setDisplayName(json.get("displayName").toString());
        if (json.get("page") == null) {
            node.setPage(null);
        } else {
            JSONObject nodePage = (JSONObject) json.get("page");
            pageEntity.setName(nodePage.get("pageName").toString());
            pageEntity.setSiteName(nodePage.get("siteName").toString());
            pageEntity.setSiteType(nodePage.get("siteType").toString());
            pageEntity.setURL(nodePage.get("url").toString());
            node.setPage(pageEntity);
        }

        if (json.get("children") != null) {
            String[] sitesArray = json.get("children").toString().replace("[", "").replace("]", "").split("(?<=}),");
            for (int i = 0; i < sitesArray.length; i++) {
                json = (JSONObject) new JSONParser().parse(sitesArray[i].replaceAll("\\s", ""));
                Page page = new Page();
                page.setName(json.get("name").toString());
                page.setURL(json.get("url").toString());
                children.add(page);
                node.setChildren(children);
            }
        } else {
            node.setChildren(null);
        }

        if (json.containsKey("displayNames")) {
            String nodes = json.get("displayNames").toString().replace("[", "").replace("]", "");
            String[] nodesArray = nodes.split("(?<=}),");
            if (!nodesArray[0].equals("")) {
                for (int i = 0; i < nodesArray.length; i++) {
                    json = (JSONObject) new JSONParser().parse(nodesArray[i]);
//                    json = new JSONObject(nodesArray[i] + "}");
                    displayNames.add(json.get("value").toString());
                }
            }
        }
        node.setDisplayNames(displayNames);

        return node;
    }

    public Navigation navigationParser(String jSonNavigation) throws ParseException {
        Navigation navigation = new Navigation();
        List<Node> nodesList = new ArrayList<>();
        JSONObject json = (JSONObject) new JSONParser().parse(jSonNavigation);
        navigation.setPriority(json.get("priority").toString());
        navigation.setSiteType(json.get("siteType").toString());
        navigation.setSiteName(json.get("siteName").toString());
        if (json.get("nodes") != null) {
            String nodes = json.get("nodes").toString().replace("[", "").replace("]", "");
            String[] nodesArray = nodes.split("(?<=}),");
            if (!nodesArray[0].equals("")) {
                Node node;
                for (int i = 0; i < nodesArray.length; i++) {
                    json = (JSONObject) new JSONParser().parse(nodesArray[i]);
                    node = new Node();
                    node.setName(json.get("name").toString());
                    node.setUri(json.get("url").toString());
                    nodesList.add(node);
                }
            }
        }
        navigation.setNodes(nodesList);
        return navigation;
    }
}
