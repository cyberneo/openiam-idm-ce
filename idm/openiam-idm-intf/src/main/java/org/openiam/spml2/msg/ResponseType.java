package org.openiam.spml2.msg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * <p>
 * Java class for ResponseType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:oasis:names:tc:SPML:2:0}ExtensibleType">
 *       &lt;sequence>
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="status" use="required" type="{urn:oasis:names:tc:SPML:2:0}StatusCodeType" />
 *       &lt;attribute name="requestID" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="error" type="{urn:oasis:names:tc:SPML:2:0}ErrorCode" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseType", propOrder = {})
@XmlSeeAlso({ AddResponseType.class, ListTargetsResponseType.class,
		ModifyResponseType.class, LookupResponseType.class, ArrayList.class})
public class ResponseType extends ExtensibleType {

	@XmlAttribute(required = true)
	protected StatusCodeType status;
	@XmlAttribute
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String requestID;
	@XmlAttribute
	protected ErrorCode error;


	public void addErrorMessage(String msg) {
		errorMessage = msg;

	}

	public String getErrorCodeAsStr() {
		if (error == null) {
			return null;
		}

		return error.value();
	}

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link StatusCodeType }
	 * 
	 */
	public StatusCodeType getStatus() {
		return status;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link StatusCodeType }
	 * 
	 */
	public void setStatus(StatusCodeType value) {
		this.status = value;
	}

	/**
	 * Gets the value of the requestID property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRequestID() {
		return requestID;
	}

	/**
	 * Sets the value of the requestID property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRequestID(String value) {
		this.requestID = value;
	}

	/**
	 * Gets the value of the error property.
	 * 
	 * @return possible object is {@link ErrorCode }
	 * 
	 */
	public ErrorCode getError() {
		return error;
	}

	/**
	 * Sets the value of the error property.
	 * 
	 * @param value
	 *            allowed object is {@link ErrorCode }
	 * 
	 */
	public void setError(ErrorCode value) {
		this.error = value;
	}

	@Override
	public String toString() {
		return "ResponseType{" + "status=" + status + ", requestID='"
				+ requestID + '\'' + ", error=" + error + '}';
	}
}
