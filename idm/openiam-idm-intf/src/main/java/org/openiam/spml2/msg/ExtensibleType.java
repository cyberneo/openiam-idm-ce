
package org.openiam.spml2.msg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.*;
import javax.xml.namespace.QName;

import org.openiam.provision.type.ExtensibleObject;
import org.openiam.spml2.msg.CapabilitiesListType;
import org.openiam.spml2.msg.CapabilityDataType;
import org.openiam.spml2.msg.CapabilityType;
import org.openiam.spml2.msg.ModificationType;
import org.openiam.spml2.msg.NamespacePrefixMappingType;
import org.openiam.spml2.msg.QueryClauseType;
import org.openiam.spml2.msg.RequestType;
import org.openiam.spml2.msg.ResponseType;
import org.openiam.spml2.msg.SchemaEntityRefType;
import org.openiam.spml2.msg.SchemaType;
import org.openiam.spml2.msg.TargetType;
import org.w3c.dom.Element;
import org.openiam.provision.type.ExtensibleUser;
import org.openiam.provision.type.ExtensibleGroup;

/**
 * <p>Java class for ExtensibleType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExtensibleType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;any/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExtensibleType", propOrder = {
    "any",
    "errorMessage"
})
@XmlSeeAlso({
    ResponseType.class,
    CapabilityDataType.class,
    SchemaEntityRefType.class,
    RequestType.class,
    IdentifierType.class,
    ModificationType.class,
    CapabilitiesListType.class,
    PSOType.class,
    SchemaType.class,
    CapabilityType.class,
    NamespacePrefixMappingType.class,
    QueryClauseType.class,
    TargetType.class,
    ExtensibleUser.class,
    ExtensibleGroup.class
})
public class ExtensibleType implements java.io.Serializable  {


	@XmlAnyElement(lax = true)
	protected List<ExtensibleObject> any = new ArrayList<ExtensibleObject>();
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    @XmlAttribute
    protected String errorMessage;

    /**
     * Gets the value of the any property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the any property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * {@link Element }
     * 
     * 
     */
 /*   public List<Object> getAny() {
        if (any == null) {
            any = new ArrayList<Object>();
        }
        return this.any;
    }
*/
    
    public List<ExtensibleObject> getAny() {
        if (any == null) {
            any = new ArrayList<ExtensibleObject>();
        }
        return this.any;
    }
    
    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }
    
/*    public void addAttribute(ExtensibleAttribute attr) {
    	if (attr == null) {
    		throw new NullPointerException("Extensible attribute is null");
    	}
    	any.add(attr);
    	
    }
*/
    public void addObject(ExtensibleObject obj) {
    	if (obj == null) {
    		throw new NullPointerException("Extensible object is null");
    	}
    	any.add(obj);
    	
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
