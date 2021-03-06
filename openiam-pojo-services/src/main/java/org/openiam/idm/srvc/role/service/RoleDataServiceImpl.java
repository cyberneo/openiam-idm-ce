package org.openiam.idm.srvc.role.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.dozer.converter.GroupDozerConverter;
import org.openiam.dozer.converter.RoleAttributeDozerConverter;
import org.openiam.dozer.converter.RoleDozerConverter;
import org.openiam.dozer.converter.RolePolicyDozerConverter;
import org.openiam.dozer.converter.UserDozerConverter;
import org.openiam.dozer.converter.UserRoleDozerConverter;
import org.openiam.exception.data.ObjectNotFoundException;
import org.openiam.idm.srvc.grp.domain.GroupEntity;
import org.openiam.idm.srvc.grp.dto.Group;
import org.openiam.idm.srvc.grp.service.UserGroupDAO;
import org.openiam.idm.srvc.res.service.ResourceRoleDAO;
import org.openiam.idm.srvc.role.domain.RoleAttributeEntity;
import org.openiam.idm.srvc.role.domain.RoleEmbeddableId;
import org.openiam.idm.srvc.role.domain.RoleEntity;
import org.openiam.idm.srvc.role.domain.RolePolicyEntity;
import org.openiam.idm.srvc.role.domain.UserRoleEntity;
import org.openiam.idm.srvc.role.dto.Role;
import org.openiam.idm.srvc.role.dto.RoleAttribute;
import org.openiam.idm.srvc.role.dto.RoleConstant;
import org.openiam.idm.srvc.role.dto.RoleId;
import org.openiam.idm.srvc.role.dto.RolePolicy;
import org.openiam.idm.srvc.role.dto.RoleSearch;
import org.openiam.idm.srvc.role.dto.UserRole;
import org.openiam.idm.srvc.service.service.ServiceDAO;
import org.openiam.idm.srvc.user.domain.UserEntity;
import org.openiam.idm.srvc.user.domain.ReconcileUserEntity;
import org.openiam.idm.srvc.user.dto.User;

import org.openiam.idm.srvc.user.service.UserDataService;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//Note: as per spec serviceName goes in impl class and name goes in interface

public class RoleDataServiceImpl implements RoleDataService {

	private RoleDAO roleDao;
	private RoleAttributeDAO roleAttributeDAO;
	private UserDataService userManager;
	private UserRoleDAO userRoleDao;
	private UserGroupDAO userGroupDao;
	private RolePolicyDAO rolePolicyDao;
	private ResourceRoleDAO resRoleDao;
	private ServiceDAO serviceDao;

	private static final Log log = LogFactory.getLog(RoleDataServiceImpl.class);

	@Autowired
	private RoleDozerConverter roleDozerConverter;

	@Autowired
	private RoleAttributeDozerConverter roleAttributeDozerConverter;

	@Autowired
	private UserRoleDozerConverter userRoleDozerConverter;

	@Autowired
	private GroupDozerConverter groupDozerConverter;

	@Autowired
	private UserDozerConverter userDozerConverter;

	@Autowired
	private RolePolicyDozerConverter rolePolicyDozerConverter;

	public RoleDAO getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDAO roleDao) {
		this.roleDao = roleDao;
	}

	@Transactional
	public Role addRole(Role role) {
		if (role == null)
			throw new IllegalArgumentException("role object is null");
		RoleEntity entity = roleDozerConverter.convertToEntity(role, true);
		roleDao.add(entity);

		return roleDozerConverter.convertToDTO(entity, true);
	}

	@Transactional(readOnly = true)
	public Role getRole(String serviceId, String roleId) {
		if (roleId == null)
			throw new IllegalArgumentException("roleId is null");

		RoleEntity rl = roleDao
				.findById(new RoleEmbeddableId(serviceId, roleId));

		// if (!org.hibernate.Hibernate.isInitialized(rl.getUsers())) {
		// if (rl != null) {
		// org.hibernate.Hibernate.initialize(rl.getUsers());
		// org.hibernate.Hibernate.initialize(rl.getGroups());
		// }
		return roleDozerConverter.convertToDTO(rl, true);

	}

	@Transactional
	public void updateRole(Role role) {
		if (role == null)
			throw new IllegalArgumentException("role object is null");

		roleDao.update(roleDozerConverter.convertToEntity(role, true));

	}

	/**
	 * Returns a list of all Roles regardless of service The list is sorted by
	 * ServiceId, Role
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Role> getAllRoles() {

		List<RoleEntity> rolesEntities = roleDao.findAllRoles();

		return roleDozerConverter.convertToDTOList(rolesEntities, false);
	}

	@Transactional
	public int removeRole(String domainId, String roleId) {
		if (roleId == null)
			throw new IllegalArgumentException("roleId is null");
		if (domainId == null)
			throw new IllegalArgumentException("serviceId object is null");

		Role rl = new Role(new RoleId(domainId, roleId));

		try {
			this.roleAttributeDAO.deleteRoleAttributes(domainId, roleId);
			this.userRoleDao.removeAllUsersInRole(domainId, roleId);
			this.resRoleDao.removeResourceRole(domainId, roleId);
			this.roleDao.remove(roleDozerConverter.convertToEntity(rl, true));
		} catch (Exception e) {
			log.error(e.toString());
			return 0;
		}
		return 1;
	}

	@Transactional(readOnly = true)
	public List<Role> getRolesInDomain(String domainId) {
		long start = System.currentTimeMillis();

		List<RoleEntity> rlList = roleDao.findRolesInService(domainId);

		long end = System.currentTimeMillis();
		log.debug("findRolesInService: " + (end - start));

		if (rlList == null || rlList.size() == 0)
			return null;

		return roleDozerConverter.convertToDTOList(rlList, false);
	}

	/* ---------------------- RoleAttribute Methods --------------- */
	@Transactional
	public RoleAttribute addAttribute(RoleAttribute attribute) {
		if (attribute == null)
			throw new IllegalArgumentException("Attribute can not be null");

		if (attribute.getRoleId() == null) {
			throw new IllegalArgumentException(
					"Role has not been associated with this attribute.");
		}
		RoleAttributeEntity attributeEntity = roleAttributeDozerConverter
				.convertToEntity(attribute, true);
		roleAttributeDAO.add(attributeEntity);

		return roleAttributeDozerConverter.convertToDTO(attributeEntity, true);
	}

	@Transactional(readOnly = true)
	public RoleAttribute[] getAllAttributes(String serviceId, String roleId) {

		if (roleId == null) {
			throw new IllegalArgumentException("groupId is null");
		}

		RoleEntity role = roleDao.findById(new RoleEmbeddableId(serviceId,
				roleId));
		Set<RoleAttributeEntity> attrSet = role.getRoleAttributes();
		if (attrSet != null && attrSet.isEmpty())
			return null;
		return this.roleAttrSetToArray(attrSet);
	}

	@Transactional(readOnly = true)
	public RoleAttribute getAttribute(String attrId) {
		if (attrId == null) {
			throw new IllegalArgumentException("attrId is null");
		}
		RoleAttributeEntity roleAttributeEntity = roleAttributeDAO
				.findById(attrId);

		return roleAttributeDozerConverter.convertToDTO(roleAttributeEntity,
				true);
	}

	@Transactional
	public void removeAllAttributes(String serviceId, String roleId) {
		if (roleId == null) {
			throw new IllegalArgumentException("roleId is null");
		}
		this.roleAttributeDAO.deleteRoleAttributes(serviceId, roleId);

	}

	@Transactional
	public void removeAttribute(RoleAttribute attr) {
		if (attr == null) {
			throw new IllegalArgumentException("attr is null");
		}
		if (attr.getRoleAttrId() == null) {
			throw new IllegalArgumentException("attrId is null");
		}

		roleAttributeDAO.remove(roleAttributeDozerConverter.convertToEntity(
				attr, true));

	}

	@Transactional
	public void updateAttribute(RoleAttribute attribute) {
		if (attribute == null)
			throw new IllegalArgumentException("Attribute can not be null");
		if (attribute.getRoleAttrId() == null) {
			throw new IllegalArgumentException("Attribute id is null");
		}
		if (attribute.getRoleId() == null) {
			throw new IllegalArgumentException(
					"Role has not been associated with this attribute.");
		}

		roleAttributeDAO.update(roleAttributeDozerConverter.convertToEntity(
				attribute, true));
	}

	/* ------------- Group to Role Methods --------------------------------- */
	@Transactional
	public void addGroupToRole(String serviceId, String roleId, String groupId) {
		// TODO Auto-generated method stub
		if (roleId == null)
			throw new IllegalArgumentException("roleId is null");
		if (serviceId == null)
			throw new IllegalArgumentException("serviceId is null");
		if (groupId == null)
			throw new IllegalArgumentException("groupId is null");

		roleDao.addGroupToRole(serviceId, roleId, groupId);

	}

	@Transactional(readOnly = true)
	public Group[] getGroupsInRole(String serviceId, String roleId) {
		RoleEntity rl = roleDao
				.findById(new RoleEmbeddableId(serviceId, roleId));
		if (rl == null) {
			log.error("Role not found for roleId =" + roleId);
			throw new ObjectNotFoundException();
		}
		// org.hibernate.Hibernate.initialize(rl.getGroups());
		Set<GroupEntity> grpSetEntities = rl.getGroups();
		if (grpSetEntities == null || grpSetEntities.isEmpty()) {
			return null;
		}
		Set<Group> grpSet = new HashSet<Group>();
		for (GroupEntity groupEntity : grpSetEntities) {
			grpSet.add(groupDozerConverter.convertToDTO(groupEntity, true));
		}
		return grpSet.toArray(new Group[grpSet.size()]);

	}

	@Transactional(readOnly = true)
	public boolean isGroupInRole(String serviceId, String roleId, String groupId) {

		RoleEntity rl = roleDao
				.findById(new RoleEmbeddableId(serviceId, roleId));
		if (rl == null) {
			log.error("Role not found for roleId =" + roleId);
			throw new ObjectNotFoundException();
		}
		// org.hibernate.Hibernate.initialize(rl.getGroups());
		Set<GroupEntity> grpSet = rl.getGroups();
		if (grpSet == null || grpSet.isEmpty()) {
			return false;
		}
		Iterator<GroupEntity> it = grpSet.iterator();
		while (it.hasNext()) {
			GroupEntity g = it.next();
			if (g.getGrpId().equalsIgnoreCase(groupId)) {
				return true;
			}
		}
		return false;
	}

	@Transactional
	public void removeGroupFromRole(String serviceId, String roleId,
			String groupId) {
		if (roleId == null)
			throw new IllegalArgumentException("roleId is null");
		if (serviceId == null)
			throw new IllegalArgumentException("serviceId object is null");
		if (groupId == null)
			throw new IllegalArgumentException("groupId object is null");

		this.roleDao.removeGroupFromRole(serviceId, roleId, groupId);

	}

	@Transactional
	public void removeAllGroupsFromRole(String serviceId, String roleId) {
		if (roleId == null)
			throw new IllegalArgumentException("roleId is null");
		if (serviceId == null)
			throw new IllegalArgumentException("serviceId object is null");

		roleDao.removeAllGroupsFromRole(serviceId, roleId);
	}

	@Transactional(readOnly = true)
	public List<Role> getRolesInGroup(String groupId) {
		if (groupId == null)
			throw new IllegalArgumentException("groupid is null");

		List<RoleEntity> roleEntityList = roleDao.findRolesInGroup(groupId);
		if (roleEntityList == null || roleEntityList.isEmpty())
			return null;

		return roleDozerConverter.convertToDTOList(roleEntityList, false);

	}

	/* ------------- User to Role Methods --------------------------------- */

	/**
	 * Adds a user to a role using the UserRole object. Similar to
	 * addUserToRole, but allows you to update attributes likes start and end
	 * date.
	 */
	@Transactional
	public void assocUserToRole(UserRole ur) {
		if (ur.getRoleId() == null)
			throw new IllegalArgumentException("roleId is null");
		if (ur.getServiceId() == null)
			throw new IllegalArgumentException("domainId object is null");
		if (ur.getUserId() == null)
			throw new IllegalArgumentException("userId object is null");

		ur.setUserRoleId(null);
		userRoleDao.add(userRoleDozerConverter.convertToEntity(ur, true));
	}

	/**
	 * Updates the attributes in the user role object.
	 * 
	 * @param ur
	 */
	@Transactional
	public void updateUserRoleAssoc(UserRole ur) {
		if (ur.getRoleId() == null)
			throw new IllegalArgumentException("roleId is null");
		if (ur.getServiceId() == null)
			throw new IllegalArgumentException("domainId object is null");
		if (ur.getUserId() == null)
			throw new IllegalArgumentException("userId object is null");
		userRoleDao.update(userRoleDozerConverter.convertToEntity(ur, true));
	}

	@Transactional(readOnly = true)
	public UserRole getUserRoleById(String userRoleId) {
		if (userRoleId == null) {
			throw new IllegalArgumentException("userRoleId is null");
		}
		UserRoleEntity entity = userRoleDao.findById(userRoleId);
		return userRoleDozerConverter.convertToDTO(entity, true);
	}

	@Transactional(readOnly = true)
	public List<UserRole> getUserRolesForUser(String userId) {
		if (userId == null) {
			throw new IllegalArgumentException("userId is null");
		}
		List<UserRoleEntity> entityList = userRoleDao
				.findUserRoleByUser(userId);

		return userRoleDozerConverter.convertToDTOList(entityList, false);
	}

	@Transactional
	public void addUserToRole(String domainId, String roleId, String userId) {

		if (roleId == null)
			throw new IllegalArgumentException("roleId is null");
		if (domainId == null)
			throw new IllegalArgumentException("domainId object is null");
		if (userId == null)
			throw new IllegalArgumentException("userId object is null");

		UserRoleEntity ur = new UserRoleEntity(userId, domainId, roleId);

		this.userRoleDao.add(ur);

	}

	@Transactional(readOnly = true)
	public boolean isUserInRole(String serviceId, String roleId, String userId) {
		if (roleId == null)
			throw new IllegalArgumentException("roleId is null");
		if (serviceId == null)
			throw new IllegalArgumentException("serviceId object is null");
		if (userId == null)
			throw new IllegalArgumentException("userIdId object is null");

		// check if the user is directly linked to a role
		/*
		 * Role rl = roleDao.findDirectRoleForUser(serviceId, roleId, userId);
		 * log.info("findDirectRoleForUser = " + rl); if (rl != null) { return
		 * true; } // check if the user is linked to a role through a group.
		 * List<Role> roleList = roleDao.findIndirectUserRoles(userId);
		 * log.info("findInDirectUserRoles = " + roleList); if (roleList !=
		 * null) return true; return false;
		 */
		List<Role> userRoleList = this.getUserRolesAsFlatList(userId);
		if (userRoleList == null) {
			return false;
		}
		for (Role rl : userRoleList) {
			if (rl.getId().getRoleId().equalsIgnoreCase(roleId)
					&& rl.getId().getServiceId().equalsIgnoreCase(serviceId)) {
				return true;
			}
		}
		return false;
	}

	@Transactional
	public void removeUserFromRole(String domainId, String roleId, String userId) {
		if (roleId == null)
			throw new IllegalArgumentException("roleId is null");
		if (domainId == null)
			throw new IllegalArgumentException("domainId object is null");
		if (userId == null)
			throw new IllegalArgumentException("userId object is null");

		this.userRoleDao.removeUserFromRole(domainId, roleId, userId);

	}

	/**
	 * Returns the roles that are directly associated with a user; ie. Does not
	 * take into account roles that may be associated with a user becuase of a
	 * group relationship.
	 * 
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Role> getUserRolesDirect(String userId) {
		if (userId == null)
			throw new IllegalArgumentException("userIdId is null");

		List<RoleEntity> roleList = roleDao.findUserRoles(userId);
		if (roleList == null || roleList.size() == 0)
			return null;

		Set<RoleEntity> newRoleSet = new HashSet<RoleEntity>();

		if (roleList != null && !roleList.isEmpty()) {
			updateRoleAssociation(roleList, RoleConstant.DIRECT, newRoleSet);
		}
		if (roleList == null || roleList.size() == 0) {
			return null;
		}

		return roleDozerConverter.convertToDTOList(roleList, false);

	}

	/**
	 * Returns an array of roles that a user belongs to.
	 */
	/*
	 * public List<Role> getUserRoles(String userId) { if (userId == null) throw
	 * new IllegalArgumentException("userIdId is null");
	 * 
	 * List<Role> roleList = roleDao.findUserRoles(userId);
	 * 
	 * log.debug("getUserRoles for userId=" + userId);
	 * log.debug(" - findUserRoles = " + roleList);
	 * 
	 * 
	 * Set<Role> newRoleSet = new HashSet();
	 * 
	 * if (roleList != null && !roleList.isEmpty()) {
	 * updateRoleAssociation(roleList, RoleConstant.DIRECT, newRoleSet); }
	 * 
	 * roleList = roleDao.findIndirectUserRoles(userId);
	 * 
	 * log.debug(" - findIndirectUserRoles = " + roleList);
	 * 
	 * if (roleList != null && !roleList.isEmpty()) {
	 * updateRoleAssociation(roleList, RoleConstant.INDIRECT, newRoleSet); } if
	 * (newRoleSet == null || newRoleSet.size() == 0) { return null; }
	 * List<Role> newRoles = new ArrayList<Role>(newRoleSet); return newRoles;
	 * 
	 * 
	 * }
	 */
	@Transactional(readOnly = true)
	private RoleEntity getParentRole(RoleEntity rl) {
		RoleEmbeddableId id = new RoleEmbeddableId(rl.getRoleId()
				.getServiceId(), rl.getParentRoleId());
		RoleEntity pRole = this.roleDao.findById(id);
		log.info("Got parent role = " + pRole);
		if (pRole != null) {
			// add the child role to the parentRole
			pRole.getChildRoles().add(rl);
		}
		if (pRole.getParentRoleId() != null) {
			log.info("Found another parent role - make a recursive call. parentId ="
					+ pRole.getParentRoleId());
			return getParentRole(pRole);
		}
		return pRole;
	}

	/**
	 * Returns an array of Role objects that indicate the Roles a user is
	 * associated to.
	 * 
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Role> getUserRoles(String userId) {
		if (userId == null)
			throw new IllegalArgumentException("userIdId is null");

		List<RoleEntity> roleList = roleDao.findUserRoles(userId);

		log.debug("getUserRoles for userId=" + userId);
		log.debug(" - findUserRoles = " + roleList);

		Set<RoleEntity> newRoleSet = new HashSet<RoleEntity>();

		if (roleList != null && !roleList.isEmpty()) {
			updateRoleAssociation(roleList, RoleConstant.DIRECT, newRoleSet);
		}

		roleList = roleDao.findIndirectUserRoles(userId);

		log.debug(" - findIndirectUserRoles = " + roleList);

		if (roleList != null && !roleList.isEmpty()) {
			updateRoleAssociation(roleList, RoleConstant.INDIRECT, newRoleSet);
		}
		if (newRoleSet.size() == 0) {
			return null;
		}
		List<RoleEntity> newRoles = new LinkedList<RoleEntity>(newRoleSet);
		// for each of these roles, figure out if there are roles above it in
		// the hierarchy

		List<RoleEntity> newRoleList = new LinkedList<RoleEntity>();
		for (RoleEntity rl : newRoles) {
			log.info("Role id=" + rl.getRoleId() + " parentId="
					+ rl.getParentRoleId());
			if (rl.getParentRoleId() == null) {
				newRoleList.add(rl);
			} else {
				log.info("Get the parent role for parentId="
						+ rl.getParentRoleId());
				newRoleList.add(getParentRole(rl));
			}
		}

		return roleDozerConverter.convertToDTOList(newRoleList, true);

		// return newRoles;

	}

	@Transactional(readOnly = true)
	private List<RoleEntity> getParentRoleFlat(RoleEntity rl) {
		List<RoleEntity> roleList = new LinkedList<RoleEntity>();
		RoleEmbeddableId id = new RoleEmbeddableId(rl.getRoleId()
				.getServiceId(), rl.getParentRoleId());
		RoleEntity pRole = roleDao.findById(id);
		log.info("Got parent role = " + pRole);
		if (pRole != null) {
			// add the child role to the list of role
			roleList.add(pRole);
		}
		if (pRole.getParentRoleId() != null) {
			log.info("Found another parent role - make a recursive call. parentId ="
					+ pRole.getParentRoleId());
			roleList.addAll(getParentRoleFlat(pRole));
			return roleList;
		}
		return roleList;
	}

	/**
	 * Returns a list of roles that a user belongs to. Roles can be hierarchical
	 * and this operation traverses the tree to roles that are in the hierarchy.
	 * 
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Role> getUserRolesAsFlatList(String userId) {
		if (userId == null)
			throw new IllegalArgumentException("userIdId is null");

		List<RoleEntity> roleList = roleDao.findUserRoles(userId);

		log.debug("getUserRoles for userId=" + userId);
		log.debug(" - findUserRoles = " + roleList);

		Set<RoleEntity> newRoleSet = new HashSet<RoleEntity>();

		if (roleList != null && !roleList.isEmpty()) {
			updateRoleAssociation(roleList, RoleConstant.DIRECT, newRoleSet);
		}

		roleList = roleDao.findIndirectUserRoles(userId);

		log.debug(" - findIndirectUserRoles = " + roleList);

		if (roleList != null && !roleList.isEmpty()) {
			updateRoleAssociation(roleList, RoleConstant.INDIRECT, newRoleSet);
		}
		if (newRoleSet == null || newRoleSet.size() == 0) {
			return null;
		}
		List<RoleEntity> newRoles = new LinkedList<RoleEntity>(newRoleSet);
		// for each of these roles, figure out if there are roles above it in
		// the hierarchy

		// store the roles in sorted order
		Set<RoleEntity> roleSet = new TreeSet<RoleEntity>();
		// List<Role> newRoleList = new ArrayList<Role>();
		for (RoleEntity rl : newRoles) {

			log.debug("Role id=" + rl.getRoleId().getRoleId() + " parentId="
					+ rl.getParentRoleId());

			if (rl.getParentRoleId() == null) {
				roleSet.add(rl);
			} else {

				log.debug("Get the parent role for parentId="
						+ rl.getParentRoleId());

				roleSet.add(rl);
				roleSet.addAll(getParentRoleFlat(rl));
			}
		}

		List<Role> newRoleList = new ArrayList<Role>();
		for (RoleEntity entity : roleSet) {
			newRoleList.add(roleDozerConverter.convertToDTO(entity, true));
		}
		return newRoleList;
	}

	@Transactional(readOnly = true)
	public List<Role> getUserRolesByDomain(String domainId, String userId) {
		if (userId == null)
			throw new IllegalArgumentException("userIdId is null");

		List<RoleEntity> roleList = roleDao.findUserRolesByService(domainId,
				userId);
		if (roleList == null || roleList.size() == 0)
			return null;

		Set<RoleEntity> newRoleSet = new HashSet<RoleEntity>();

		if (!roleList.isEmpty()) {
			updateRoleAssociation(roleList, RoleConstant.DIRECT, newRoleSet);
		}

		roleList = roleDao.findIndirectUserRoles(userId);
		if (roleList != null && !roleList.isEmpty()) {
			updateRoleAssociation(roleList, RoleConstant.INDIRECT, newRoleSet);
		}
		if (roleList == null || roleList.size() == 0) {
			return null;
		}

		return roleDozerConverter.convertToDTOList(roleList, true);

	}

	@Override
	@Transactional(readOnly = true)
	public List<String> getUsersInRoleIds(String domainId, String roleId) {
		if (domainId == null)
			throw new IllegalArgumentException("domainId is null");
		if (roleId == null)
			throw new IllegalArgumentException("roleId is null");
		/* Get the users that are directly associated */
		Role rl = getRole(domainId, roleId);

		// System.out.println("in getUsersInRole: rl=" + rl);
		// System.out.println("in getUsersInRole: users =" + rl.getUsers());

		List<String> userList = userRoleDao.findUserIdsByRole(domainId, roleId);

		// No direct association, continue with indirect
		if (userList == null || userList.isEmpty()) {
			userList = new LinkedList<String>();
		}
		Set<String> newUserSet = new HashSet<String>(userList);

		/* Get the users that are linked through a group */
		Set<Group> groupSet = rl.getGroups();
		// ensure that we have a unique set of users.
		// iterate through the groups
		if (groupSet != null && !groupSet.isEmpty()) {
			for (Group grp : groupSet) {
				List<String> userLst = userGroupDao.findUserIdsByGroup(grp
						.getGrpId());
				if (userLst != null)
					newUserSet.addAll(userLst);
			}
		}

		int size = newUserSet.size();
		// no users found, return null
		if (size == 0) {
			return null;
		}

		log.debug("Count of users in getUsersInRole [domainId=  " + domainId
				+ ", roleId=" + roleId + "]");
		return new ArrayList<String>(newUserSet);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getUsersInRole(String domainId, String roleId) {
		if (domainId == null)
			throw new IllegalArgumentException("domainId is null");
		if (roleId == null)
			throw new IllegalArgumentException("roleId is null");

		/* Get the users that are directly associated */
		Role rl = getRole(domainId, roleId);

		// System.out.println("in getUsersInRole: rl=" + rl);
		// System.out.println("in getUsersInRole: users =" + rl.getUsers());

		List<UserEntity> userList = userRoleDao
				.findUserByRole(domainId, roleId);

		// No direct association, continue with indirect
		if (userList == null || userList.isEmpty()) {
			userList = new LinkedList<UserEntity>();
		}
		Set<UserEntity> newUserSet = new HashSet<UserEntity>(userList);

		/* Get the users that are linked through a group */
		Set<Group> groupSet = rl.getGroups();
		// ensure that we have a unique set of users.
		// iterate through the groups
		if (groupSet != null && !groupSet.isEmpty()) {
			for (Group grp : groupSet) {
				List<UserEntity> userLst = userGroupDao.findUserByGroup(grp
						.getGrpId());
				newUserSet.addAll(userLst);
			}
		}

		int size = newUserSet.size();
		// no users found, return null
		if (size == 0) {
			return null;
		}

		log.debug("Count of users in getUsersInRole [domainId=  " + domainId
				+ ", roleId=" + roleId + "]");

		List<UserEntity> users = new ArrayList<UserEntity>(newUserSet);

		return userDozerConverter.convertToDTOList(users, false);
	}

	/** **************** Helper Methods ***************************** */

	private RoleAttribute[] roleAttrSetToArray(Set<RoleAttributeEntity> attrSet) {

		int size = attrSet.size();
		RoleAttribute[] roleAttrAry = new RoleAttribute[size];
		Iterator<RoleAttributeEntity> it = attrSet.iterator();
		int ctr = 0;
		while (it.hasNext()) {
			RoleAttribute ra = roleAttributeDozerConverter.convertToDTO(
					it.next(), true);
			roleAttrAry[ctr++] = ra;
		}
		return roleAttrAry;

	}

	private Set<UserEntity> updateUserRoleAssociation(
			List<UserEntity> userList, int roleAssociationMethod) {

		Set<UserEntity> newUserSet = new HashSet();

		for (UserEntity u : userList) {
			newUserSet.add(u);
		}

		return newUserSet;

	}

	private void updateRoleAssociation(List<RoleEntity> roleList,
			int associationMethod, Set<RoleEntity> newRoleSet) {
		int size = roleList.size();
		for (int i = 0; i < size; i++) {
			RoleEntity rl = roleList.get(i);
			rl.setUserAssociationMethod(RoleConstant.DIRECT);
			newRoleSet.add(rl);
		}
		// return newRoleSet;
	}

	private void userSetToNewUserSet(List<UserEntity> userList,
			int roleAssociationMethod, Set<UserEntity> newUserSet) {

		for (UserEntity u : userList) {
			newUserSet.add(u);
		}

	}

	@Transactional(readOnly = true)
	public List<Role> search(RoleSearch search) {
		if (search == null) {
			throw new IllegalArgumentException("Search parameter is null");
		}
		List<RoleEntity> roleList = roleDao.search(search);
		if (roleList == null || roleList.isEmpty()) {
			return null;
		}
		if (roleList == null || roleList.size() == 0) {
			return null;
		}

		return roleDozerConverter.convertToDTOList(roleList, false);
	}

	public UserDataService getUserManager() {
		return userManager;
	}

	public void setUserManager(UserDataService userManager) {
		this.userManager = userManager;
	}

	public RoleAttributeDAO getRoleAttributeDAO() {
		return roleAttributeDAO;
	}

	public void setRoleAttributeDAO(RoleAttributeDAO roleAttributeDAO) {
		this.roleAttributeDAO = roleAttributeDAO;
	}

	public UserRoleDAO getUserRoleDao() {
		return userRoleDao;
	}

	public void setUserRoleDao(UserRoleDAO userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	public UserGroupDAO getUserGroupDao() {
		return userGroupDao;
	}

	public void setUserGroupDao(UserGroupDAO userGroupDao) {
		this.userGroupDao = userGroupDao;
	}

	/* Role Policies */
	@Transactional
	public RolePolicy addRolePolicy(RolePolicy rPolicy) {
		if (rPolicy == null) {
			throw new NullPointerException("rPolicy is null");
		}
		RolePolicyEntity rolePolicyEntity = rolePolicyDozerConverter
				.convertToEntity(rPolicy, true);
		rolePolicyDao.add(rolePolicyEntity);
		return rolePolicyDozerConverter.convertToDTO(rolePolicyEntity, true);
	}

	@Transactional
	public RolePolicy updateRolePolicy(RolePolicy rPolicy) {
		if (rPolicy == null) {
			throw new NullPointerException("rPolicy is null");
		}
		RolePolicyEntity rolePolicyEntity = rolePolicyDao
				.update(rolePolicyDozerConverter.convertToEntity(rPolicy, true));
		return rolePolicyDozerConverter.convertToDTO(rolePolicyEntity, true);
	}

	@Transactional(readOnly = true)
	public List<RolePolicy> getAllRolePolicies(String domainId, String roleId) {
		if (domainId == null) {
			throw new NullPointerException("domainId is null");
		}
		if (roleId == null) {
			throw new NullPointerException("roleId is null");
		}
		List<RolePolicyEntity> entityList = rolePolicyDao.findRolePolicies(
				domainId, roleId);

		return rolePolicyDozerConverter.convertToDTOList(entityList, false);
	}

	@Transactional(readOnly = true)
	public RolePolicy getRolePolicy(String rolePolicyId) {
		if (rolePolicyId == null) {
			throw new NullPointerException("rolePolicyId is null");
		}
		RolePolicyEntity policyEntity = rolePolicyDao.findById(rolePolicyId);

		return rolePolicyDozerConverter.convertToDTO(policyEntity, true);
	}

	@Transactional
	public void removeRolePolicy(RolePolicy rPolicy) {
		if (rPolicy == null) {
			throw new NullPointerException("rPolicy is null");
		}
		rolePolicyDao.remove(rolePolicyDozerConverter.convertToEntity(rPolicy,
				true));

	}

	public RolePolicyDAO getRolePolicyDao() {
		return rolePolicyDao;
	}

	public void setRolePolicyDao(RolePolicyDAO rolePolicyDao) {
		this.rolePolicyDao = rolePolicyDao;
	}

	public ResourceRoleDAO getResRoleDao() {
		return resRoleDao;
	}

	public void setResRoleDao(ResourceRoleDAO resRoleDao) {
		this.resRoleDao = resRoleDao;
	}

	public ServiceDAO getServiceDao() {
		return serviceDao;
	}

	public void setServiceDao(ServiceDAO serviceDao) {
		this.serviceDao = serviceDao;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ReconcileUserEntity> findUserWByRole(String domainId,
			String roleId) {
		if (domainId == null)
			throw new IllegalArgumentException("domainId is null");
		if (roleId == null)
			throw new IllegalArgumentException("roleId is null");

		List<ReconcileUserEntity> ids = userRoleDao.findUserWByRole(domainId,
				roleId);
		return ids;
	}
}
