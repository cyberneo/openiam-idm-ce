package org.openiam.idm.srvc.user.dto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.base.AttributeOperationEnum;
import org.openiam.base.BaseConstants;
import org.openiam.dozer.DozerDTOCorrespondence;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.continfo.dto.Address;
import org.openiam.idm.srvc.continfo.dto.EmailAddress;
import org.openiam.idm.srvc.continfo.dto.Phone;
import org.openiam.idm.srvc.user.domain.UserEntity;
import org.openiam.idm.srvc.user.domain.ReconcileUserEntity;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.*;

/**
 * User domain object. This object is used to transfer data between the service
 * layer and the client layer.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "user", propOrder = {
		"addresses",
		"birthdate",
		"companyId",
		"companyOwnerId",
		"createDate",
		"createdBy",
		"deptCd",
		"deptName",
		"emailAddresses",
		"employeeId",
		"employeeType",
		// "expirationDate",
		"firstName", "jobCode", "lastName", "lastUpdate", "lastUpdatedBy",
		"locationCd", "locationName", "managerId", "metadataTypeId",
		"classification", "middleInit", "phones", "prefix", "sex", "status",
		"secondaryStatus", "suffix", "title", "userAttributes", "userId",
		"userTypeInd", "userNotes", "division", "costCenter", "startDate",
		"lastDate", "mailCode", "nickname", "maidenName", "passwordTheme",
		"country", "bldgNum", "streetDirection", "suite", "address1",
		"address2", "address3", "address4", "address5", "address6", "address7",
		"city", "state", "postalCd", "email", "showInSearch", "delAdmin",
		"areaCd", "countryCd", "phoneNbr", "phoneExt", "principalList",
		"supervisor", "alternateContactId", "securityDomain", "userOwnerId",
		"datePasswordChanged", "dateChallengeRespChanged" })
@XmlSeeAlso({ Login.class, Supervisor.class, UserNote.class, Phone.class,
		Address.class, EmailAddress.class, UserAttribute.class })
@DozerDTOCorrespondence(UserEntity.class)
public class User extends org.openiam.base.BaseObject {

	protected static final Log log = LogFactory.getLog(User.class);
	private static final long serialVersionUID = -6525727206388584686L;
	// Fields
	protected String userId;

	// protected AddressMap addresses; see below
	@XmlSchemaType(name = "dateTime")
	protected Date birthdate;

	protected String companyId;

	protected String companyOwnerId;

	@XmlSchemaType(name = "dateTime")
	protected Date createDate;

	protected String createdBy;

	protected String deptCd;

	protected String deptName;

	protected String employeeId;

	protected String employeeType;

	protected String firstName;

	protected String jobCode;

	protected String lastName;

	@XmlSchemaType(name = "dateTime")
	protected Date lastUpdate;

	protected String lastUpdatedBy;

	protected String locationCd;

	protected String locationName;

	protected String managerId;

	protected String metadataTypeId;

	protected String classification;

	protected String middleInit;

	protected String prefix;

	protected String sex;

	@Enumerated(EnumType.STRING)
	protected UserStatusEnum status;

	@Enumerated(EnumType.STRING)
	protected UserStatusEnum secondaryStatus;

	protected String suffix;

	protected String title;

	protected String userTypeInd;

	protected String division;

	protected String mailCode;

	protected String costCenter;

	@XmlSchemaType(name = "dateTime")
	protected Date startDate;

	@XmlSchemaType(name = "dateTime")
	protected Date lastDate;

	protected String nickname;

	protected String maidenName;

	protected String passwordTheme;

	protected String country;

	protected String bldgNum;

	protected String streetDirection;

	protected String suite;

	protected String address1;

	protected String address2;

	protected String address3;

	protected String address4;

	protected String address5;

	protected String address6;

	protected String address7;

	protected String city;

	protected String state;

	protected String postalCd;

	protected String email;

	protected String areaCd;

	protected String countryCd;

	protected String phoneNbr;

	protected String phoneExt;

	protected Integer showInSearch = new Integer(0);

	protected Integer delAdmin = new Integer(0);

	protected List<Login> principalList = new LinkedList<Login>();

	protected Supervisor supervisor;

	protected String alternateContactId;
	// @XmlElement(name="securityDomain", namespace =
	// "urn:idm.openiam.org/srvc/user/dto")

	protected String securityDomain;

	protected String userOwnerId;

	@XmlSchemaType(name = "dateTime")
	protected Date datePasswordChanged;

	@XmlSchemaType(name = "dateTime")
	protected Date dateChallengeRespChanged;

	@XmlJavaTypeAdapter(UserNoteSetAdapter.class)
	protected Set<UserNote> userNotes = new HashSet<UserNote>(0);
	// protected Set<Group> groups = new HashSet<Group>(0);

	@XmlJavaTypeAdapter(UserAttributeMapAdapter.class)
	protected HashMap<String, UserAttribute> userAttributes = new HashMap<String, UserAttribute>(
			0);

	protected Set<Address> addresses = new HashSet<Address>(0);

	protected Set<Phone> phones = new HashSet<Phone>(0);

	protected Set<EmailAddress> emailAddresses = new HashSet<EmailAddress>(0);

	public User(ReconcileUserEntity user) {
		birthdate = user.getBirthdate();
		companyId = user.getCompanyId();
		companyOwnerId = user.getCompanyOwnerId();
		createDate = user.getCreateDate();
		createdBy = user.getCreatedBy();
		if (user.getDeptCd() != null)
			deptCd = user.getDeptCd();
		deptName = user.getDeptName();
		employeeId = user.getEmployeeId();
		employeeType = user.getEmployeeType();
		if (user.getLogins() == null)
			this.principalList = new LinkedList<Login>();
		else
			this.principalList = new LinkedList<Login>(user.getLoginsDTO());
		firstName = user.getFirstName();
		jobCode = user.getJobCode();
		lastName = user.getLastName();
		lastUpdate = user.getLastUpdate();
		this.lastUpdatedBy = user.getLastUpdatedBy();
		this.locationCd = user.getLocationCd();
		this.locationName = user.getLocationName();
		this.managerId = user.getManagerId();
		this.metadataTypeId = user.getMetadataTypeId();
		this.classification = user.getClassification();
		this.middleInit = user.getMiddleInit();
		this.prefix = user.getPrefix();
		this.sex = user.getSex();
		this.status = user.getStatus();
		this.secondaryStatus = user.getSecondaryStatus();
		this.suffix = user.getSuffix();
		this.title = user.getTitle();
		this.userId = user.getUserId();
		this.userTypeInd = user.getUserTypeInd();
		this.division = user.getDivision();
		this.mailCode = user.getMailCode();
		this.costCenter = user.getCostCenter();
		this.startDate = user.getStartDate();
		this.lastDate = user.getLastDate();
		this.nickname = user.getNickname();
		this.maidenName = user.getMaidenName();
		this.passwordTheme = user.getPasswordTheme();
		this.country = user.getCountry();
		this.bldgNum = user.getBldgNum();
		this.streetDirection = user.getStreetDirection();
		this.address1 = user.getAddress1();
		this.address2 = user.getAddress2();
		this.address3 = user.getAddress3();
		this.address4 = user.getAddress4();
		this.address5 = user.getAddress5();
		this.address6 = user.getAddress6();
		this.address7 = user.getAddress7();
		this.city = user.getCity();
		this.state = user.getState();
		this.postalCd = user.getPostalCd();
		this.email = user.getEmail();
		this.areaCd = user.getAreaCd();
		this.countryCd = user.getCountryCd();
		this.phoneNbr = user.getPhoneNbr();
		this.phoneExt = user.getPhoneExt();
		this.showInSearch = user.getShowInSearch();
		this.delAdmin = user.getDelAdmin();
		this.alternateContactId = user.getAlternateContactId();

		this.createdBy = user.getCreatedBy();
		this.startDate = user.getStartDate();
		this.lastDate = user.getLastDate();

		this.userOwnerId = user.getUserOwnerId();
		this.dateChallengeRespChanged = user.getDateChallengeRespChanged();
		this.datePasswordChanged = user.getDatePasswordChanged();
	}

	// protected Set userAttributes = new HashSet(0);

	// Constructors

	/**
	 * default constructor
	 */
	public User() {
	}

	/**
	 * minimal constructor
	 */
	public User(String userId) {
		this.userId = userId;
	}

	// Property accessors
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {

		this.userId = userId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleInit() {
		return this.middleInit;
	}

	public void setMiddleInit(String middleInit) {
		this.middleInit = middleInit;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDeptCd() {
		return this.deptCd;
	}

	public void setDeptCd(String dept) {
		this.deptCd = dept;
	}

	public Date getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return this.suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getUserTypeInd() {
		return this.userTypeInd;
	}

	public void setUserTypeInd(String userTypeInd) {
		this.userTypeInd = userTypeInd;
	}

	public String getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeType() {
		return this.employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getLocationCd() {
		return this.locationCd;
	}

	public void setLocationCd(String locationId) {
		this.locationCd = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyOwnerId() {
		return this.companyOwnerId;
	}

	public void setCompanyOwnerId(String companyOwnerId) {
		this.companyOwnerId = companyOwnerId;
	}

	public String getManagerId() {
		return this.managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getJobCode() {
		return this.jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getCostCenter() {
		return this.costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getLastDate() {
		return this.lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public Set<UserNote> getUserNotes() {
		return userNotes;
	}

	public void setUserNotes(Set<UserNote> userNotes) {
		this.userNotes = userNotes;
	}

	/**
	 * Updates the underlying collection with the UserNote object that is being
	 * passed in. The note is added if its does note exist and updated if its
	 * does exist.
	 * 
	 * @param note
	 */
	public void saveNote(UserNote note) {
		userNotes.add(note);
	}

	/**
	 * Removes the note object from the underlying collection.
	 * 
	 * @param note
	 */
	public void removeNote(UserNote note) {
		userNotes.remove(note);
	}

	/**
	 * Returns the note object for the specified noteId.
	 * 
	 * @param noteId
	 * @return
	 */
	public UserNote getUserNote(String noteId) {
		UserNote nt = null;

		Iterator<UserNote> it = this.userNotes.iterator();
		while (it.hasNext()) {
			nt = it.next();
			if (nt.getUserNoteId().equals(noteId))
				return nt;
		}

		return nt;
	}

	public HashMap<String, UserAttribute> getUserAttributes() {
		return this.userAttributes;
	}

	public void setUserAttributes(HashMap<String, UserAttribute> userAttributes) {
		this.userAttributes = userAttributes;
	}

	/**
	 * Updates the underlying collection with the UserAttribute object that is
	 * being passed in. The attribute is added if its does not exist and updated
	 * if its does exist.
	 * 
	 * @param attr
	 */
	public void saveAttribute(UserAttribute attr) {
		userAttributes.put(attr.getName(), attr);
	}

	/**
	 * Removes the attribute object from the underlying collection.
	 * 
	 * @param attr
	 */
	public void removeAttributes(UserAttribute attr) {
		userAttributes.remove(attr.getName());
	}

	/**
	 * Returns the attribute object that is specified by the NAME parameter.
	 * 
	 * @param name
	 *            - The attribute map is keyed on the NAME property.
	 * @return
	 */
	public UserAttribute getAttribute(String name) {

		return userAttributes.get(name);

	}

	/**
	 * Returns a Set of addresses. Map is keyed on the Address.description
	 * value. This value should indicate the type of address; HOME, SHIPPING,
	 * BILLING, etc.
	 * 
	 * @return
	 */
	public Set<Address> getAddresses() {
		return addresses;
	}

	/**
	 * Sets a Set of addresses with a user. Map is keyed on the
	 * Address.description value.
	 * 
	 * @param addresses
	 */
	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public Address getAddressByName(String name) {
		Iterator<Address> addressIt = addresses.iterator();
		while (addressIt.hasNext()) {
			Address adr = addressIt.next();
			if (adr.getName() != null && adr.getName().equalsIgnoreCase(name)) {
				return adr;
			}
		}
		return null;
	}

	public Set<EmailAddress> getEmailAddresses() {
		return emailAddresses;
	}

	public void setEmailAddresses(Set<EmailAddress> emailAddresses) {
		this.emailAddresses = emailAddresses;
	}

	public EmailAddress getEmailByName(String name) {
		Iterator<EmailAddress> emailIt = emailAddresses.iterator();
		while (emailIt.hasNext()) {
			EmailAddress em = emailIt.next();
			if (em.getName() != null && em.getName().equalsIgnoreCase(name)) {
				return em;
			}
		}
		return null;
	}

	public Set<Phone> getPhones() {
		return phones;
	}

	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
	}

	public Phone getPhoneByName(String name) {
		Iterator<Phone> phoneIt = phones.iterator();
		while (phoneIt.hasNext()) {
			Phone ph = phoneIt.next();
			if (ph.getName() != null && ph.getName().equalsIgnoreCase(name)) {
				return ph;
			}
		}
		return null;
	}

	public Phone getPhoneById(String id) {
		Iterator<Phone> phoneIt = phones.iterator();
		while (phoneIt.hasNext()) {
			Phone ph = phoneIt.next();
			if (ph.getName() != null && ph.getName().equalsIgnoreCase(id)) {
				return ph;
			}
		}
		return null;
	}

	public Address getAddressById(String id) {
		Iterator<Address> addressIt = addresses.iterator();
		while (addressIt.hasNext()) {
			Address adr = addressIt.next();
			if (adr.getName() != null && adr.getName().equalsIgnoreCase(id)) {
				return adr;
			}
		}
		return null;
	}

	public EmailAddress getEmailAddressById(String id) {
		Iterator<EmailAddress> emailIt = emailAddresses.iterator();
		while (emailIt.hasNext()) {
			EmailAddress em = emailIt.next();
			if (em.getName() != null && em.getName().equalsIgnoreCase(id)) {
				return em;
			}
		}
		return null;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getMailCode() {
		return mailCode;
	}

	public void setMailCode(String mailCode) {
		this.mailCode = mailCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getBldgNum() {
		return bldgNum;
	}

	public void setBldgNum(String bldgNum) {
		this.bldgNum = bldgNum;
	}

	public String getStreetDirection() {
		return streetDirection;
	}

	public void setStreetDirection(String streetDirection) {
		this.streetDirection = streetDirection;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public String getAddress5() {
		return address5;
	}

	public void setAddress5(String address5) {
		this.address5 = address5;
	}

	public String getAddress6() {
		return address6;
	}

	public void setAddress6(String address6) {
		this.address6 = address6;
	}

	public String getAddress7() {
		return address7;
	}

	public void setAddress7(String address7) {
		this.address7 = address7;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalCd() {
		return postalCd;
	}

	public void setPostalCd(String postalCd) {
		this.postalCd = postalCd;
	}

	public String getAreaCd() {
		return areaCd;
	}

	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}

	public String getCountryCd() {
		return countryCd;
	}

	public void setCountryCd(String countryCd) {
		this.countryCd = countryCd;
	}

	public String getPhoneNbr() {
		return phoneNbr;
	}

	public void setPhoneNbr(String phoneNbr) {
		this.phoneNbr = phoneNbr;
	}

	public String getPhoneExt() {
		return phoneExt;
	}

	public void setPhoneExt(String phoneExt) {
		this.phoneExt = phoneExt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserStatusEnum getStatus() {
		return status;
	}

	public void setStatus(UserStatusEnum status) {
		this.status = status;
	}

	public UserStatusEnum getSecondaryStatus() {
		return secondaryStatus;
	}

	public void setSecondaryStatus(UserStatusEnum secondaryStatus) {
		this.secondaryStatus = secondaryStatus;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getMetadataTypeId() {
		return metadataTypeId;
	}

	public void setMetadataTypeId(String metadataTypeId) {
		this.metadataTypeId = metadataTypeId;
	}

	public String getPasswordTheme() {
		return passwordTheme;
	}

	public void setPasswordTheme(String passwordTheme) {
		this.passwordTheme = passwordTheme;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMaidenName() {
		return maidenName;
	}

	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}

	public Integer getShowInSearch() {
		return showInSearch;
	}

	public void setShowInSearch(Integer showInSearch) {
		this.showInSearch = showInSearch;
	}

	public List<Login> getPrincipalList() {
		return principalList;
	}

	public void setPrincipalList(List<Login> principalList) {
		this.principalList = principalList;
	}

	public Supervisor getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}

	public String getAlternateContactId() {
		return alternateContactId;
	}

	public void setAlternateContactId(String alternateContactId) {
		this.alternateContactId = alternateContactId;
	}

	public String getSecurityDomain() {
		return securityDomain;
	}

	public void setSecurityDomain(String securityDomain) {
		this.securityDomain = securityDomain;
	}

	public String getSuite() {
		return suite;
	}

	public void setSuite(String suite) {
		this.suite = suite;
	}

	public void updateUser(User newUser) {
		if (newUser.getAddress1() != null) {
			if (newUser.getAddress1().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.address1 = null;
			} else {
				this.address1 = newUser.getAddress1();
			}
		}
		if (newUser.getAddress2() != null) {
			if (newUser.getAddress2().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.address2 = null;
			} else {
				this.address2 = newUser.getAddress2();
			}
		}
		if (newUser.getAddress3() != null) {
			if (newUser.getAddress3().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.address3 = null;
			} else {
				this.address3 = newUser.getAddress3();
			}
		}
		if (newUser.getAddress4() != null) {
			if (newUser.getAddress4().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.address4 = null;
			} else {
				this.address4 = newUser.getAddress4();
			}
		}
		if (newUser.getAddress5() != null) {
			if (newUser.getAddress5().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.address5 = null;
			} else {
				this.address5 = newUser.getAddress5();
			}
		}
		if (newUser.getAddress6() != null) {
			if (newUser.getAddress6().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.address6 = null;
			} else {
				this.address6 = newUser.getAddress6();
			}
		}
		if (newUser.getAddress7() != null) {
			if (newUser.getAddress7().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.address7 = null;
			} else {
				this.address7 = newUser.getAddress7();
			}
		}
		if (newUser.getAreaCd() != null) {
			if (newUser.getAreaCd().equalsIgnoreCase(BaseConstants.NULL_STRING)) {
				this.areaCd = null;
			} else {
				this.areaCd = newUser.getAreaCd();
			}
		}
		if (newUser.getBirthdate() != null) {
			if (newUser.getBirthdate().equals(BaseConstants.NULL_DATE)) {
				this.birthdate = null;
			} else {
				this.birthdate = newUser.getBirthdate();
			}
		}
		if (newUser.getBldgNum() != null) {
			if (newUser.getBldgNum()
					.equalsIgnoreCase(BaseConstants.NULL_STRING)) {
				this.bldgNum = null;
			} else {
				this.bldgNum = newUser.getBldgNum();
			}
		}
		if (newUser.getCity() != null) {
			if (newUser.getCity().equalsIgnoreCase(BaseConstants.NULL_STRING)) {
				this.city = null;
			} else {
				this.city = newUser.getCity();
			}
		}
		if (newUser.getClassification() != null) {
			if (newUser.getClassification().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.classification = null;
			} else {
				this.classification = newUser.getClassification();
			}
		}
		if (newUser.getCompanyId() != null) {
			if (newUser.getCompanyId().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.companyId = null;
			} else {
				this.companyId = newUser.getCompanyId();
			}
		}
		if (newUser.getCostCenter() != null) {
			if (newUser.getCostCenter().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.costCenter = null;
			} else {
				this.costCenter = newUser.getCostCenter();
			}
		}
		if (newUser.getCountry() != null) {
			if (newUser.getCountry()
					.equalsIgnoreCase(BaseConstants.NULL_STRING)) {
				this.country = null;
			} else {
				this.country = newUser.getCountry();
			}
		}
		if (newUser.getCountryCd() != null) {
			if (newUser.getCountryCd().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.countryCd = null;
			} else {
				this.countryCd = newUser.getCountryCd();
			}
		}
		if (newUser.getDeptCd() != null) {
			if (newUser.getDeptCd().equalsIgnoreCase(BaseConstants.NULL_STRING)) {
				this.deptCd = null;
			} else {
				this.deptCd = newUser.getDeptCd();
			}
		}
		if (newUser.getDeptName() != null) {
			if (newUser.getDeptName().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.deptName = null;
			} else {
				this.deptName = newUser.getDeptName();
			}
		}
		if (newUser.getDivision() != null) {
			if (newUser.getDivision().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.division = null;
			} else {
				this.division = newUser.getDivision();
			}
		}
		if (newUser.getEmail() != null) {
			if (newUser.getEmail().equalsIgnoreCase(BaseConstants.NULL_STRING)) {
				this.email = null;
			} else {
				this.email = newUser.getEmail();
			}
		}

		if (newUser.getEmployeeId() != null) {
			if (newUser.getEmployeeId().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.employeeId = null;
			} else {
				this.employeeId = newUser.getEmployeeId();
			}
		}
		if (newUser.getEmployeeType() != null) {
			if (newUser.getEmployeeType().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.employeeType = null;
			} else {
				this.employeeType = newUser.getEmployeeType();
			}
		}
		if (newUser.getFirstName() != null) {
			if (newUser.getFirstName().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.firstName = null;
			} else {
				this.firstName = newUser.getFirstName();
			}
		}
		if (newUser.getJobCode() != null) {
			if (newUser.getJobCode()
					.equalsIgnoreCase(BaseConstants.NULL_STRING)) {
				this.jobCode = null;
			} else {
				this.jobCode = newUser.getJobCode();
			}
		}
		if (newUser.getLastName() != null) {
			if (newUser.getLastName().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.lastName = null;
			} else {
				this.lastName = newUser.getLastName();
			}
		}
		if (newUser.getLastDate() != null) {
			if (newUser.getLastDate().equals(BaseConstants.NULL_DATE)) {
				this.lastDate = null;
			} else {
				this.lastDate = newUser.getLastDate();
			}
		}
		if (newUser.getLocationCd() != null) {
			if (newUser.getLocationCd().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.locationCd = null;
			} else {
				this.locationCd = newUser.getLocationCd();
			}
		}
		if (newUser.getLocationName() != null) {
			if (newUser.getLocationName().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.locationName = null;
			} else {
				this.locationName = newUser.getLocationName();
			}
		}
		if (newUser.getMaidenName() != null) {
			if (newUser.getMaidenName().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.maidenName = null;
			} else {
				this.maidenName = newUser.getMaidenName();
			}
		}
		if (newUser.getMailCode() != null) {
			if (newUser.getMailCode().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.mailCode = newUser.getMailCode();
			} else {
				this.mailCode = null;
			}
		}
		if (newUser.getMetadataTypeId() != null) {
			if (newUser.getMetadataTypeId().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.metadataTypeId = null;
			} else {
				this.metadataTypeId = newUser.getMetadataTypeId();
			}
		}
		if (newUser.getMiddleInit() != null) {
			if (newUser.getMiddleInit().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.middleInit = null;
			} else {
				this.middleInit = newUser.getMiddleInit();
			}
		}
		if (newUser.getNickname() != null) {
			if (newUser.getNickname().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.nickname = null;
			} else {
				this.nickname = newUser.getNickname();
			}
		}
		if (newUser.getPasswordTheme() != null) {
			if (newUser.getPasswordTheme().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.passwordTheme = null;
			} else {
				this.passwordTheme = newUser.getPasswordTheme();
			}
		}
		if (newUser.getPhoneExt() != null) {
			if (newUser.getPhoneExt().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.phoneExt = null;
			} else {
				this.phoneExt = newUser.getPhoneExt();
			}
		}
		if (newUser.getPhoneNbr() != null) {
			if (newUser.getPhoneNbr().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.phoneNbr = null;
			} else {
				this.phoneNbr = newUser.getPhoneNbr();
			}
		}
		if (newUser.getPostalCd() != null) {
			if (newUser.getPostalCd().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.postalCd = null;
			} else {
				this.postalCd = newUser.getPostalCd();
			}
		}
		if (newUser.getPrefix() != null) {
			if (newUser.getPrefix().equalsIgnoreCase(BaseConstants.NULL_STRING)) {
				this.prefix = null;
			} else {
				this.prefix = newUser.getPrefix();
			}
		}
		if (newUser.getSecondaryStatus() != null) {
			this.secondaryStatus = newUser.getSecondaryStatus();
		}
		if (newUser.getSex() != null) {
			if (newUser.getSex().equalsIgnoreCase(BaseConstants.NULL_STRING)) {
				this.sex = null;
			} else {
				this.sex = newUser.getSex();
			}
		}
		if (newUser.getStartDate() != null) {
			if (newUser.getStartDate().equals(BaseConstants.NULL_DATE)) {
				this.startDate = null;
			} else {
				this.startDate = newUser.getStartDate();
			}
		}

		if (newUser.getStatus() != null) {
			this.status = newUser.getStatus();
		}
		if (newUser.getStreetDirection() != null) {
			if (newUser.getStreetDirection().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.streetDirection = null;
			} else {
				this.streetDirection = newUser.getStreetDirection();
			}
		}
		if (newUser.getState() != null) {
			if (newUser.getState().equalsIgnoreCase(BaseConstants.NULL_STRING)) {
				this.state = null;
			} else {
				this.state = newUser.getState();
			}
		}
		if (newUser.getSuffix() != null) {
			if (newUser.getSuffix().equalsIgnoreCase(BaseConstants.NULL_STRING)) {
				this.suffix = null;
			} else {
				this.suffix = newUser.getSuffix();
			}
		}
		if (newUser.getShowInSearch() != null) {
			if (newUser.getShowInSearch().equals(BaseConstants.NULL_INTEGER)) {
				this.showInSearch = 0;
			} else {
				this.showInSearch = newUser.getShowInSearch();
			}
		}
		if (newUser.getTitle() != null) {
			if (newUser.getTitle().equalsIgnoreCase(BaseConstants.NULL_STRING)) {
				this.title = null;
			} else {
				this.title = newUser.getTitle();
			}
		}
		if (newUser.getUserTypeInd() != null) {
			if (newUser.getUserTypeInd().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.userTypeInd = null;
			} else {
				this.userTypeInd = newUser.getUserTypeInd();
			}
		}
		if (newUser.getManagerId() != null) {
			if (newUser.getManagerId().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.managerId = null;
			} else {
				this.managerId = newUser.getManagerId();
			}
		}
		if (newUser.getAlternateContactId() != null) {
			if (newUser.getAlternateContactId().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.alternateContactId = null;
			} else {
				this.alternateContactId = newUser.getAlternateContactId();
			}
		}
		if (newUser.getDelAdmin() != null) {
			if (newUser.getDelAdmin().equals(BaseConstants.NULL_INTEGER)) {
				this.delAdmin = 0;
			} else {
				this.delAdmin = newUser.getDelAdmin();

			}
		}
		if (newUser.getUserOwnerId() != null) {
			if (newUser.getUserOwnerId().equalsIgnoreCase(
					BaseConstants.NULL_STRING)) {
				this.userOwnerId = null;
			} else {
				this.userOwnerId = newUser.getUserOwnerId();
			}
		}
		if (newUser.getDateChallengeRespChanged() != null) {
			if (newUser.getDateChallengeRespChanged().equals(
					BaseConstants.NULL_DATE)) {
				this.dateChallengeRespChanged = null;
			} else {
				this.dateChallengeRespChanged = newUser
						.getDateChallengeRespChanged();
			}
		}
		if (newUser.getDatePasswordChanged() != null) {
			if (newUser.getDatePasswordChanged()
					.equals(BaseConstants.NULL_DATE)) {
				this.datePasswordChanged = null;
			} else {
				this.datePasswordChanged = newUser.getDatePasswordChanged();
			}
		}

		// check the attributes
		if (newUser.getUserAttributes() != null) {
			log.debug("UserAttributes are NOT NULL in newUser object");
			updateAttributes(newUser.getUserAttributes());
		} else {
			log.debug("UserAttributes are NULL in newUser");
		}
	}

	protected void updateAttributes(Map<String, UserAttribute> attrMap) {
		if (attrMap == null || attrMap.isEmpty()) {
			return;
		}

		Set<String> keySet = attrMap.keySet();

		for (String s : keySet) {
			UserAttribute origAttr = userAttributes.get(s);
			UserAttribute newAttr = attrMap.get(s);
			if (newAttr.getOperation() == AttributeOperationEnum.NO_CHANGE) {
				log.debug("- updateAttributes: key=" + " " + s + " = NO_CHANGE");

			} else if (newAttr.getOperation() == AttributeOperationEnum.ADD) {
				log.debug("- updateAttributes: key=" + " " + s + " = ADD");
				userAttributes.put(newAttr.getName(), newAttr);

			} else if (newAttr.getOperation() == AttributeOperationEnum.DELETE) {
				log.debug("- updateAttributes: key=" + " " + s + " = DELETE");
				userAttributes.remove(origAttr.getName());

			} else if (newAttr.getOperation() == AttributeOperationEnum.REPLACE) {
				log.debug("- updateAttributes: key=" + " " + s + " = REPLACE");
				origAttr.setOperation(AttributeOperationEnum.REPLACE);
				origAttr.setValue(newAttr.getValue());
				userAttributes.put(origAttr.getName(), origAttr);

			} else {
				// Operation Attribute was not set
				if (origAttr == null && newAttr != null) {
					// new attribute
					log.debug("- updateAttributes: key=" + " " + s
							+ " = DETERMINED ADD");
					newAttr.setOperation(AttributeOperationEnum.ADD);
					userAttributes.put(newAttr.getName(), newAttr);
				} else {
					log.debug("- updateAttributes: key=" + " " + s
							+ " = DETERMINED REPLACE");
					origAttr.setOperation(AttributeOperationEnum.REPLACE);
					origAttr.setValue(newAttr.getValue());
					userAttributes.put(origAttr.getName(), origAttr);
				}
			}

		}
	}

	public Integer getDelAdmin() {
		return delAdmin;
	}

	public void setDelAdmin(Integer delAdmin) {
		this.delAdmin = delAdmin;
	}

	public String getUserOwnerId() {
		return userOwnerId;
	}

	public void setUserOwnerId(String userOwnerId) {
		this.userOwnerId = userOwnerId;
	}

	public Date getDatePasswordChanged() {
		return datePasswordChanged;
	}

	public void setDatePasswordChanged(Date datePasswordChanged) {
		this.datePasswordChanged = datePasswordChanged;
	}

	public Date getDateChallengeRespChanged() {
		return dateChallengeRespChanged;
	}

	public void setDateChallengeRespChanged(Date dateChallengeRespChanged) {
		this.dateChallengeRespChanged = dateChallengeRespChanged;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof User))
			return false;

		User user = (User) o;

		if (address1 != null ? !address1.equals(user.address1)
				: user.address1 != null)
			return false;
		if (address2 != null ? !address2.equals(user.address2)
				: user.address2 != null)
			return false;
		if (address3 != null ? !address3.equals(user.address3)
				: user.address3 != null)
			return false;
		if (address4 != null ? !address4.equals(user.address4)
				: user.address4 != null)
			return false;
		if (address5 != null ? !address5.equals(user.address5)
				: user.address5 != null)
			return false;
		if (address6 != null ? !address6.equals(user.address6)
				: user.address6 != null)
			return false;
		if (address7 != null ? !address7.equals(user.address7)
				: user.address7 != null)
			return false;
		if (alternateContactId != null ? !alternateContactId
				.equals(user.alternateContactId)
				: user.alternateContactId != null)
			return false;
		if (areaCd != null ? !areaCd.equals(user.areaCd) : user.areaCd != null)
			return false;
		if (birthdate != null ? !birthdate.equals(user.birthdate)
				: user.birthdate != null)
			return false;
		if (bldgNum != null ? !bldgNum.equals(user.bldgNum)
				: user.bldgNum != null)
			return false;
		if (city != null ? !city.equals(user.city) : user.city != null)
			return false;
		if (classification != null ? !classification
				.equals(user.classification) : user.classification != null)
			return false;
		if (companyId != null ? !companyId.equals(user.companyId)
				: user.companyId != null)
			return false;
		if (companyOwnerId != null ? !companyOwnerId
				.equals(user.companyOwnerId) : user.companyOwnerId != null)
			return false;
		if (costCenter != null ? !costCenter.equals(user.costCenter)
				: user.costCenter != null)
			return false;
		if (country != null ? !country.equals(user.country)
				: user.country != null)
			return false;
		if (countryCd != null ? !countryCd.equals(user.countryCd)
				: user.countryCd != null)
			return false;
		if (createDate != null ? !createDate.equals(user.createDate)
				: user.createDate != null)
			return false;
		if (createdBy != null ? !createdBy.equals(user.createdBy)
				: user.createdBy != null)
			return false;
		if (dateChallengeRespChanged != null ? !dateChallengeRespChanged
				.equals(user.dateChallengeRespChanged)
				: user.dateChallengeRespChanged != null)
			return false;
		if (datePasswordChanged != null ? !datePasswordChanged
				.equals(user.datePasswordChanged)
				: user.datePasswordChanged != null)
			return false;
		if (delAdmin != null ? !delAdmin.equals(user.delAdmin)
				: user.delAdmin != null)
			return false;
		if (deptCd != null ? !deptCd.equals(user.deptCd) : user.deptCd != null)
			return false;
		if (deptName != null ? !deptName.equals(user.deptName)
				: user.deptName != null)
			return false;
		if (division != null ? !division.equals(user.division)
				: user.division != null)
			return false;
		if (email != null ? !email.equals(user.email) : user.email != null)
			return false;
		if (employeeId != null ? !employeeId.equals(user.employeeId)
				: user.employeeId != null)
			return false;
		if (employeeType != null ? !employeeType.equals(user.employeeType)
				: user.employeeType != null)
			return false;
		if (firstName != null ? !firstName.equals(user.firstName)
				: user.firstName != null)
			return false;
		if (jobCode != null ? !jobCode.equals(user.jobCode)
				: user.jobCode != null)
			return false;
		if (lastDate != null ? !lastDate.equals(user.lastDate)
				: user.lastDate != null)
			return false;
		if (lastName != null ? !lastName.equals(user.lastName)
				: user.lastName != null)
			return false;
		if (lastUpdate != null ? !lastUpdate.equals(user.lastUpdate)
				: user.lastUpdate != null)
			return false;
		if (lastUpdatedBy != null ? !lastUpdatedBy.equals(user.lastUpdatedBy)
				: user.lastUpdatedBy != null)
			return false;
		if (locationCd != null ? !locationCd.equals(user.locationCd)
				: user.locationCd != null)
			return false;
		if (locationName != null ? !locationName.equals(user.locationName)
				: user.locationName != null)
			return false;
		if (maidenName != null ? !maidenName.equals(user.maidenName)
				: user.maidenName != null)
			return false;
		if (mailCode != null ? !mailCode.equals(user.mailCode)
				: user.mailCode != null)
			return false;
		if (managerId != null ? !managerId.equals(user.managerId)
				: user.managerId != null)
			return false;
		if (metadataTypeId != null ? !metadataTypeId
				.equals(user.metadataTypeId) : user.metadataTypeId != null)
			return false;
		if (middleInit != null ? !middleInit.equals(user.middleInit)
				: user.middleInit != null)
			return false;
		if (nickname != null ? !nickname.equals(user.nickname)
				: user.nickname != null)
			return false;
		if (passwordTheme != null ? !passwordTheme.equals(user.passwordTheme)
				: user.passwordTheme != null)
			return false;
		if (phoneExt != null ? !phoneExt.equals(user.phoneExt)
				: user.phoneExt != null)
			return false;
		if (phoneNbr != null ? !phoneNbr.equals(user.phoneNbr)
				: user.phoneNbr != null)
			return false;
		if (postalCd != null ? !postalCd.equals(user.postalCd)
				: user.postalCd != null)
			return false;
		if (prefix != null ? !prefix.equals(user.prefix) : user.prefix != null)
			return false;
		if (principalList != null ? !principalList.equals(user.principalList)
				: user.principalList != null)
			return false;
		if (secondaryStatus != user.secondaryStatus)
			return false;
		if (securityDomain != null ? !securityDomain
				.equals(user.securityDomain) : user.securityDomain != null)
			return false;
		if (sex != null ? !sex.equals(user.sex) : user.sex != null)
			return false;
		if (showInSearch != null ? !showInSearch.equals(user.showInSearch)
				: user.showInSearch != null)
			return false;
		if (startDate != null ? !startDate.equals(user.startDate)
				: user.startDate != null)
			return false;
		if (state != null ? !state.equals(user.state) : user.state != null)
			return false;
		if (status != user.status)
			return false;
		if (streetDirection != null ? !streetDirection
				.equals(user.streetDirection) : user.streetDirection != null)
			return false;
		if (suffix != null ? !suffix.equals(user.suffix) : user.suffix != null)
			return false;
		if (suite != null ? !suite.equals(user.suite) : user.suite != null)
			return false;
		if (supervisor != null ? !supervisor.equals(user.supervisor)
				: user.supervisor != null)
			return false;
		if (title != null ? !title.equals(user.title) : user.title != null)
			return false;
		if (!userId.equals(user.userId))
			return false;
		if (userOwnerId != null ? !userOwnerId.equals(user.userOwnerId)
				: user.userOwnerId != null)
			return false;
		if (userTypeInd != null ? !userTypeInd.equals(user.userTypeInd)
				: user.userTypeInd != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = userId.hashCode();
		result = 31 * result + (birthdate != null ? birthdate.hashCode() : 0);
		result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
		result = 31 * result
				+ (companyOwnerId != null ? companyOwnerId.hashCode() : 0);
		result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
		result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
		result = 31 * result + (deptCd != null ? deptCd.hashCode() : 0);
		result = 31 * result + (deptName != null ? deptName.hashCode() : 0);
		result = 31 * result + (employeeId != null ? employeeId.hashCode() : 0);
		result = 31 * result
				+ (employeeType != null ? employeeType.hashCode() : 0);
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (jobCode != null ? jobCode.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
		result = 31 * result
				+ (lastUpdatedBy != null ? lastUpdatedBy.hashCode() : 0);
		result = 31 * result + (locationCd != null ? locationCd.hashCode() : 0);
		result = 31 * result
				+ (locationName != null ? locationName.hashCode() : 0);
		result = 31 * result + (managerId != null ? managerId.hashCode() : 0);
		result = 31 * result
				+ (metadataTypeId != null ? metadataTypeId.hashCode() : 0);
		result = 31 * result
				+ (classification != null ? classification.hashCode() : 0);
		result = 31 * result + (middleInit != null ? middleInit.hashCode() : 0);
		result = 31 * result + (prefix != null ? prefix.hashCode() : 0);
		result = 31 * result + (sex != null ? sex.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		result = 31 * result
				+ (secondaryStatus != null ? secondaryStatus.hashCode() : 0);
		result = 31 * result + (suffix != null ? suffix.hashCode() : 0);
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result
				+ (userTypeInd != null ? userTypeInd.hashCode() : 0);
		result = 31 * result + (division != null ? division.hashCode() : 0);
		result = 31 * result + (mailCode != null ? mailCode.hashCode() : 0);
		result = 31 * result + (costCenter != null ? costCenter.hashCode() : 0);
		result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
		result = 31 * result + (lastDate != null ? lastDate.hashCode() : 0);
		result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
		result = 31 * result + (maidenName != null ? maidenName.hashCode() : 0);
		result = 31 * result
				+ (passwordTheme != null ? passwordTheme.hashCode() : 0);
		result = 31 * result + (country != null ? country.hashCode() : 0);
		result = 31 * result + (bldgNum != null ? bldgNum.hashCode() : 0);
		result = 31 * result
				+ (streetDirection != null ? streetDirection.hashCode() : 0);
		result = 31 * result + (suite != null ? suite.hashCode() : 0);
		result = 31 * result + (address1 != null ? address1.hashCode() : 0);
		result = 31 * result + (address2 != null ? address2.hashCode() : 0);
		result = 31 * result + (address3 != null ? address3.hashCode() : 0);
		result = 31 * result + (address4 != null ? address4.hashCode() : 0);
		result = 31 * result + (address5 != null ? address5.hashCode() : 0);
		result = 31 * result + (address6 != null ? address6.hashCode() : 0);
		result = 31 * result + (address7 != null ? address7.hashCode() : 0);
		result = 31 * result + (city != null ? city.hashCode() : 0);
		result = 31 * result + (state != null ? state.hashCode() : 0);
		result = 31 * result + (postalCd != null ? postalCd.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (areaCd != null ? areaCd.hashCode() : 0);
		result = 31 * result + (countryCd != null ? countryCd.hashCode() : 0);
		result = 31 * result + (phoneNbr != null ? phoneNbr.hashCode() : 0);
		result = 31 * result + (phoneExt != null ? phoneExt.hashCode() : 0);
		result = 31 * result
				+ (showInSearch != null ? showInSearch.hashCode() : 0);
		result = 31 * result + (delAdmin != null ? delAdmin.hashCode() : 0);
		result = 31 * result
				+ (principalList != null ? principalList.hashCode() : 0);
		result = 31 * result + (supervisor != null ? supervisor.hashCode() : 0);
		result = 31
				* result
				+ (alternateContactId != null ? alternateContactId.hashCode()
						: 0);
		result = 31 * result
				+ (securityDomain != null ? securityDomain.hashCode() : 0);
		result = 31 * result
				+ (userOwnerId != null ? userOwnerId.hashCode() : 0);
		result = 31
				* result
				+ (datePasswordChanged != null ? datePasswordChanged.hashCode()
						: 0);
		result = 31
				* result
				+ (dateChallengeRespChanged != null ? dateChallengeRespChanged
						.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "User{" + "userId='" + userId + '\'' + ", birthdate="
				+ birthdate + ", companyId='" + companyId + '\''
				+ ", companyOwnerId='" + companyOwnerId + '\''
				+ ", createDate=" + createDate + ", createdBy='" + createdBy
				+ '\'' + ", deptCd='" + deptCd + '\'' + ", deptName='"
				+ deptName + '\'' + ", employeeId='" + employeeId + '\''
				+ ", employeeType='" + employeeType + '\'' + ", firstName='"
				+ firstName + '\'' + ", jobCode='" + jobCode + '\''
				+ ", lastName='" + lastName + '\'' + ", lastUpdate="
				+ lastUpdate + ", lastUpdatedBy='" + lastUpdatedBy + '\''
				+ ", locationCd='" + locationCd + '\'' + ", locationName='"
				+ locationName + '\'' + ", managerId='" + managerId + '\''
				+ ", metadataTypeId='" + metadataTypeId + '\''
				+ ", classification='" + classification + '\''
				+ ", middleInit='" + middleInit + '\'' + ", prefix='" + prefix
				+ '\'' + ", sex='" + sex + '\'' + ", status=" + status
				+ ", secondaryStatus=" + secondaryStatus + ", suffix='"
				+ suffix + '\'' + ", title='" + title + '\''
				+ ", userTypeInd='" + userTypeInd + '\'' + ", division='"
				+ division + '\'' + ", mailCode='" + mailCode + '\''
				+ ", costCenter='" + costCenter + '\'' + ", startDate="
				+ startDate + ", lastDate=" + lastDate + ", nickname='"
				+ nickname + '\'' + ", maidenName='" + maidenName + '\''
				+ ", passwordTheme='" + passwordTheme + '\'' + ", country='"
				+ country + '\'' + ", bldgNum='" + bldgNum + '\''
				+ ", streetDirection='" + streetDirection + '\'' + ", suite='"
				+ suite + '\'' + ", address1='" + address1 + '\''
				+ ", address2='" + address2 + '\'' + ", address3='" + address3
				+ '\'' + ", address4='" + address4 + '\'' + ", address5='"
				+ address5 + '\'' + ", address6='" + address6 + '\''
				+ ", address7='" + address7 + '\'' + ", city='" + city + '\''
				+ ", state='" + state + '\'' + ", postalCd='" + postalCd + '\''
				+ ", email='" + email + '\'' + ", areaCd='" + areaCd + '\''
				+ ", countryCd='" + countryCd + '\'' + ", phoneNbr='"
				+ phoneNbr + '\'' + ", phoneExt='" + phoneExt + '\''
				+ ", showInSearch=" + showInSearch + ", delAdmin=" + delAdmin
				+ ", principalList=" + principalList + ", supervisor="
				+ supervisor + ", alternateContactId='" + alternateContactId
				+ '\'' + ", securityDomain='" + securityDomain + '\''
				+ ", userOwnerId='" + userOwnerId + '\''
				+ ", datePasswordChanged=" + datePasswordChanged
				+ ", dateChallengeRespChanged=" + dateChallengeRespChanged
				+ ", userNotes=" + userNotes + ", userAttributes="
				+ userAttributes + ", addresses=" + addresses + ", phones="
				+ phones + ", emailAddresses=" + emailAddresses + '}';
	}
}
