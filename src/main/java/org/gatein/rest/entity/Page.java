package org.gatein.rest.entity;

import java.io.Serializable;

/**
 *
 * @author mgottval
 */
public class Page implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String siteType;
    private String siteName;
    private String displayName;
    private String description;
    private String accessPermissions;
    private String editPermissions;
    private String URL;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public String getAccessPermissions() {
        return accessPermissions;
    }

    public void setAccessPermissions(String accessPermissions) {
        this.accessPermissions = accessPermissions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEditPermissions() {
        return editPermissions;
    }

    public void setEditPermissions(String editPermissions) {
        this.editPermissions = editPermissions;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Page)) {
            return false;
        }
        Page other = (Page) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.gatein.rest.entity.Page[ id=" + id + " ]";
    }
}
