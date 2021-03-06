/*
*
*/

package org.openiam.idm.srvc.org.service;

import org.openiam.idm.srvc.org.dto.Organization;
import org.openiam.idm.srvc.org.dto.OrganizationAttribute;
import org.openiam.idm.srvc.org.dto.OrganizationAttributeMapAdapter;
import org.openiam.idm.srvc.org.dto.UserAffiliation;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.List;
import java.util.Map;


/*
*
*/


@WebService(targetNamespace = "urn:idm.openiam.org/srvc/org/service", name = "OrganizationDataService")
//@XmlSeeAlso({org.openiam.idm.srvc.user.dto.ObjectFactory.class,org.openiam.idm.srvc.continfo.dto.ObjectFactory.class,org.openiam.idm.srvc.org.dto.ObjectFactory.class,org.openiam.idm.srvc.org.types.ObjectFactory.class,org.openiam.idm.srvc.meta.dto.ObjectFactory.class})

public interface OrganizationDataService {

/*
 * 
 */

    @RequestWrapper(localName = "updateOrganization", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.UpdateOrganization")
    @ResponseWrapper(localName = "updateOrganizationResponse", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.UpdateOrganizationResponse")
    @WebMethod
    public void updateOrganization(
            @WebParam(name = "organization", targetNamespace = "")
            org.openiam.idm.srvc.org.dto.Organization organization
    );

/*
 * 
 */

    @RequestWrapper(localName = "getTopLevelOrganizations", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.GetTopLevelOrganizations")
    @ResponseWrapper(localName = "getTopLevelOrganizationsResponse", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.GetTopLevelOrganizationsResponse")
    @WebMethod
    public java.util.List<org.openiam.idm.srvc.org.dto.Organization> getTopLevelOrganizations();


    @RequestWrapper(localName = "getOrganizationList", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.getOrganizationList")
    @ResponseWrapper(localName = "getOrganizationListResponse", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.getOrganizationListResponse")
    @WebMethod
    public java.util.List<org.openiam.idm.srvc.org.dto.Organization> getOrganizationList(
            @WebParam(name = "parentOrgId", targetNamespace = "")
            String parentOrgId,
            @WebParam(name = "status", targetNamespace = "")
            String status);

/*
*
*/

    @RequestWrapper(localName = "subOrganizations", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.SubOrganizations")
    @ResponseWrapper(localName = "subOrganizationsResponse", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.SubOrganizationsResponse")
    @WebMethod
    public java.util.List<org.openiam.idm.srvc.org.dto.Organization> subOrganizations(
            @WebParam(name = "orgId", targetNamespace = "")
            java.lang.String orgId
    );

/*
 * 
 */

    @RequestWrapper(localName = "removeAttribute", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.RemoveAttribute")
    @ResponseWrapper(localName = "removeAttributeResponse", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.RemoveAttributeResponse")
    @WebMethod
    public void removeAttribute(
            @WebParam(name = "organizationAttribute", targetNamespace = "")
            org.openiam.idm.srvc.org.dto.OrganizationAttribute organizationAttribute
    );

/*
 * 
 */

    @RequestWrapper(localName = "getOrganization", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.GetOrganization")
    @ResponseWrapper(localName = "getOrganizationResponse", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.GetOrganizationResponse")
    @WebMethod
    public org.openiam.idm.srvc.org.dto.Organization getOrganization(
            @WebParam(name = "orgId", targetNamespace = "")
            java.lang.String orgId
    );

/*
 * 
 */

    @RequestWrapper(localName = "search", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.Search")
    @ResponseWrapper(localName = "searchResponse", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.SearchResponse")
    @WebMethod
    public java.util.List<org.openiam.idm.srvc.org.dto.Organization> search(
            @WebParam(name = "name", targetNamespace = "")
            java.lang.String name,
            @WebParam(name = "type", targetNamespace = "")
            java.lang.String type,
            @WebParam(name = "classification", targetNamespace = "")
            java.lang.String classification,
            @WebParam(name = "internalOrgId", targetNamespace = "")
            java.lang.String internalOrgId
    );

/*
 * 
 */

    @RequestWrapper(localName = "addOrganization", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.AddOrganization")
    @ResponseWrapper(localName = "addOrganizationResponse", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.AddOrganizationResponse")
    @WebMethod
    public Organization addOrganization(
            @WebParam(name = "organization", targetNamespace = "")
            Organization organization
    );

/*
 * 
 */

    @RequestWrapper(localName = "getAttribute", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.GetAttribute")
    @ResponseWrapper(localName = "getAttributeResponse", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.GetAttributeResponse")
    @WebMethod
    public OrganizationAttribute getAttribute(
            @WebParam(name = "attributeId", targetNamespace = "")
            java.lang.String attributeId
    );

/*
 * 
 */

    @RequestWrapper(localName = "addAttribute", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.AddAttribute")
    @ResponseWrapper(localName = "addAttributeResponse", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.AddAttributeResponse")
    @WebMethod
    public void addAttribute(
            @WebParam(name = "organizationAttribute", targetNamespace = "")
            org.openiam.idm.srvc.org.dto.OrganizationAttribute organizationAttribute
    );

/*
 * 
 */

    @RequestWrapper(localName = "updateAttribute", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.UpdateAttribute")
    @ResponseWrapper(localName = "updateAttributeResponse", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.UpdateAttributeResponse")
    @WebMethod
    public void updateAttribute(
            @WebParam(name = "organizationAttribute", targetNamespace = "")
            org.openiam.idm.srvc.org.dto.OrganizationAttribute organizationAttribute
    );

/*
 * 
 */

    @RequestWrapper(localName = "containsChildren", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.ContainsChildren")
    @ResponseWrapper(localName = "containsChildrenResponse", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.ContainsChildrenResponse")
    @WebMethod
    public boolean containsChildren(
            @WebParam(name = "orgId", targetNamespace = "")
            java.lang.String orgId
    );

/*
 * 
 */

    @RequestWrapper(localName = "isRootOrganization", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.IsRootOrganization")
    @ResponseWrapper(localName = "isRootOrganizationResponse", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.IsRootOrganizationResponse")
    @WebMethod
    public boolean isRootOrganization(
            @WebParam(name = "orgId", targetNamespace = "")
            java.lang.String orgId
    );

/*
 * 
 */

    @RequestWrapper(localName = "removeOrganization", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.RemoveOrganization")
    @ResponseWrapper(localName = "removeOrganizationResponse", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.RemoveOrganizationResponse")
    @WebMethod
    public void removeOrganization(
            @WebParam(name = "orgId", targetNamespace = "")
            java.lang.String orgId
    );

/*
 * 
 */

    @RequestWrapper(localName = "removeAllAttributes", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.RemoveAllAttributes")
    @ResponseWrapper(localName = "removeAllAttributesResponse", targetNamespace = "urn:idm.openiam.org/srvc/org/types", className = "org.openiam.idm.srvc.org.types.RemoveAllAttributesResponse")
    @WebMethod
    public void removeAllAttributes(
            @WebParam(name = "orgId", targetNamespace = "")
            java.lang.String orgId
    );

    /*
    *
    */
    // Note: do not include response wrapper if we plan to use Map and Adapter class
    @WebMethod
    @XmlJavaTypeAdapter(OrganizationAttributeMapAdapter.class)
    public Map<String, org.openiam.idm.srvc.org.dto.OrganizationAttribute> getAllAttributes(
            @WebParam(name = "orgId", targetNamespace = "")
            java.lang.String orgId
    );

    /**
     * Returns a list of all organizations based on a metadataType. The parentId parameter can be used to get
     * values that are nested further in the hierarchy. If parentId is null, the method will search only on the typeId and parentId
     * will be ignored.
     *
     * @param typeId
     * @param parentId
     * @return
     */
    @WebMethod
    List<Organization> getOrganizationByType(
            @WebParam(name = "typeId", targetNamespace = "")
            String typeId,
            @WebParam(name = "parentId", targetNamespace = "")
            String parentId);

    /**
     * Returns all entries in the organization table that are classified as Department.
     *
     * @param parentId
     * @return
     */
    @WebMethod
    List<Organization> allDepartments(
            @WebParam(name = "parentId", targetNamespace = "")
            String parentId);

    /**
     * Returns all entries in the organization table that are classified as Divisions.
     *
     * @param parentId
     * @return
     */
    @WebMethod
    List<Organization> allDivisions(
            @WebParam(name = "parentId", targetNamespace = "")
            String parentId);

    @WebMethod
    List<Organization> getAllOrganizations();

    /* User Affiliation */

    /**
     * Adds a user to a org using the UserOrg object.
     */
    @WebMethod
    public void assocUserToOrg(
            @WebParam(name = "userorg", targetNamespace = "")
            UserAffiliation userorg);


    @WebMethod
    public void updateUserOrgAssoc(
            @WebParam(name = "userorg", targetNamespace = "")
            UserAffiliation userorg);

    @WebMethod
    public List<Organization> getOrganizationsForUser(
            @WebParam(name = "userId", targetNamespace = "")
            String userId);

    @WebMethod
    public void addUserToOrg(
            @WebParam(name = "orgId", targetNamespace = "")
            String orgId,
            @WebParam(name = "userId", targetNamespace = "")
            String userId);

    @WebMethod
    public boolean isUserAffilatedWithOrg(
            @WebParam(name = "orgId", targetNamespace = "")
            String orgId,
            @WebParam(name = "userId", targetNamespace = "")
            String userId);

    @WebMethod
    public void removeUserFromOrg(
            @WebParam(name = "orgId", targetNamespace = "")
            String orgId,
            @WebParam(name = "userId", targetNamespace = "")
            String userId);

}
