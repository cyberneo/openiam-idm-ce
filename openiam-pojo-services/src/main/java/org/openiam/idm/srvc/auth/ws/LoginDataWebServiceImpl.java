package org.openiam.idm.srvc.auth.ws;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.exception.AuthenticationException;
import org.openiam.exception.EncryptionException;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.auth.login.LoginDataService;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.dto.UserStatusEnum;
import org.openiam.idm.srvc.user.ws.UserResponse;

import java.util.*;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(endpointInterface = "org.openiam.idm.srvc.auth.ws.LoginDataWebService", 
		targetNamespace = "urn:idm.openiam.org/srvc/auth/service", 
		serviceName = "LoginDataWebService",
		portName = "LoginDataWebServicePort")
public class LoginDataWebServiceImpl implements LoginDataWebService {

	private LoginDataService loginDS;
	private static final Log log = LogFactory.getLog(LoginDataWebServiceImpl.class);
	
	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.auth.ws.LoginDataWebService#addLogin(org.openiam.idm.srvc.auth.dto.Login)
	 */
	public LoginResponse addLogin(Login principal) {
		LoginResponse resp = new LoginResponse(ResponseStatus.SUCCESS);
		Login lg = loginDS.addLogin(principal);
		if (lg == null ) {
			resp.setStatus(ResponseStatus.FAILURE);
		}else {
			resp.setPrincipal(lg); 
		}
		return resp;
		
	}

	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.auth.ws.LoginDataWebService#decryptPassword(java.lang.String)
	 */
	public Response decryptPassword(String password) {
		String pswd = null;
		
		Response resp = new Response(ResponseStatus.SUCCESS);
		try {
			pswd = loginDS.decryptPassword(password);
		}catch(EncryptionException e) {
			resp.setStatus(ResponseStatus.FAILURE);
			return resp;			
		}
		if (pswd == null) {
			resp.setStatus(ResponseStatus.FAILURE);
			return resp;
		}
		resp.setResponseValue(pswd);
		return resp;
	}

	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.auth.ws.LoginDataWebService#encryptPassword(java.lang.String)
	 */
	public Response encryptPassword(String password) {
		Response resp = new Response(ResponseStatus.SUCCESS);
		String pswd = null;
		try {
			pswd = loginDS.encryptPassword(password);
		}catch(EncryptionException e) {
			resp.setStatus(ResponseStatus.FAILURE);
			return resp;			
		}
		if (pswd == null) {
			resp.setStatus(ResponseStatus.FAILURE);
			return resp;
		}
		resp.setResponseValue(pswd);
		return resp;
	}

	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.auth.ws.LoginDataWebService#getLogin(java.lang.String, java.lang.String)
	 */
	public LoginResponse getLogin(String domainId, String principal)
			throws AuthenticationException {
		LoginResponse resp = new LoginResponse(ResponseStatus.SUCCESS);
		Login lg = loginDS.getLogin(domainId, principal);
		if (lg == null ) {
			resp.setStatus(ResponseStatus.FAILURE);
		}else {
			resp.setPrincipal(lg); 
		}
		return resp;
	}

	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.auth.ws.LoginDataWebService#getLoginByDomain(java.lang.String)
	 */
	public LoginListResponse getLoginByDomain(String domainId) {
		LoginListResponse resp = new LoginListResponse(ResponseStatus.SUCCESS);
		List<Login> lgList = loginDS.getLoginByDomain(domainId);
		if (lgList == null ) {
			resp.setStatus(ResponseStatus.FAILURE);
		}else {
			resp.setPrincipalList(lgList); 
		}
		return resp;
	}

	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.auth.ws.LoginDataWebService#getLoginByManagedSys(java.lang.String, java.lang.String, java.lang.String)
	 */
	public LoginResponse getLoginByManagedSys(String domainId, String principal,
			String sysId) {
		
		LoginResponse resp = new LoginResponse(ResponseStatus.SUCCESS);
		Login lg = loginDS.getLoginByManagedSys(domainId, principal, sysId);
		if (lg == null ) {
			resp.setStatus(ResponseStatus.FAILURE);
		}else {
			resp.setPrincipal(lg); 
		}
		return resp;
		

	}

    public LoginResponse getPrincipalByManagedSys(String principalName,
                                                  String managedSysId) {
        LoginResponse resp = new LoginResponse(ResponseStatus.SUCCESS);
		List<Login> lgList = loginDS.getLoginByManagedSys(principalName, managedSysId);
		if (lgList == null ) {
			resp.setStatus(ResponseStatus.FAILURE);
		}else {
			resp.setPrincipal(lgList.get(0));
		}
		return resp;
    }

    /* (non-Javadoc)
      * @see org.openiam.idm.srvc.auth.ws.LoginDataWebService#getLoginByUser(java.lang.String)
      */
	public LoginListResponse getLoginByUser(String userId) {
		
		log.info("getLoginByUser userId=" + userId);
		
		LoginListResponse resp = new LoginListResponse(ResponseStatus.SUCCESS);
		List<Login> lgList = loginDS.getLoginByUser(userId);
		if (lgList == null ) {
			resp.setStatus(ResponseStatus.FAILURE);
		}else {
			resp.setPrincipalList(lgList); 
		}
		return resp;
	}

	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.auth.ws.LoginDataWebService#getPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Response getPassword(String domainId, String principal,
			String managedSysId) {
		
		Response resp = new Response(ResponseStatus.SUCCESS);
		String pswd = loginDS.getPassword(domainId, principal, managedSysId);
		if (pswd == null) {
			resp.setStatus(ResponseStatus.FAILURE);
		}
		resp.setResponseValue(pswd);
		return resp;
		
	}

	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.auth.ws.LoginDataWebService#getUserByLogin(java.lang.String, java.lang.String, java.lang.String)
	 */
	public UserResponse getUserByLogin(String domainId, String principal,
			String managedSysId) {
		
		UserResponse resp = new UserResponse(ResponseStatus.SUCCESS);
		User user = loginDS.getUserByLogin(domainId, principal, managedSysId);
		if (user == null ) {
			resp.setStatus(ResponseStatus.FAILURE);
		}else {
			resp.setUser(user); 
		}
		return resp;
		
	}

	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.auth.ws.LoginDataWebService#lockLogin(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Response lockLogin(String domainId, String principal,
			String managedSysId) {
		
		Response resp = new Response(ResponseStatus.SUCCESS);
		loginDS.lockLogin(domainId, principal, managedSysId);
		return resp;
		
	}

	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.auth.ws.LoginDataWebService#bulkUnLock(org.openiam.idm.srvc.user.dto.UserStatusEnum)
	 */
	public Response bulkUnLock(UserStatusEnum status) {
		
		Response resp = new Response(ResponseStatus.SUCCESS);
		loginDS.bulkUnLock(status);
		return resp;
	}
	
	
	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.auth.ws.LoginDataWebService#removeLogin(java.lang.String, java.lang.String)
	 */
	public Response removeLogin(String domainId, String principal, String managedSysId) {
		Response resp = new Response(ResponseStatus.SUCCESS);
		loginDS.removeLogin(domainId, principal, managedSysId);
		return resp;
	}

	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.auth.ws.LoginDataWebService#resetPassword(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public Response resetPassword(String domainId, String principal,String managedSysId, String password) {
		
		Response resp = new Response(ResponseStatus.SUCCESS);
		boolean result = loginDS.resetPassword(domainId, principal, managedSysId, password);
		if (!result) {
			resp.setStatus(ResponseStatus.FAILURE);
		}
		return resp;
	}

	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.auth.ws.LoginDataWebService#setPassword(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public Response setPassword(String domainId, String principal,
			String managedSysId, String password) {
		
		Response resp = new Response(ResponseStatus.SUCCESS);
		boolean result = loginDS.setPassword(domainId, principal, managedSysId, password);
		if (!result) {
			resp.setStatus(ResponseStatus.FAILURE);
		}
		return resp;
		
	}

	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.auth.ws.LoginDataWebService#unLockLogin(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Response unLockLogin(String domainId, String principal,
			String managedSysId) {
		
		Response resp = new Response(ResponseStatus.SUCCESS);
		loginDS.unLockLogin(domainId, principal, managedSysId);
		return resp;
		
	}

	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.auth.ws.LoginDataWebService#updateLogin(org.openiam.idm.srvc.auth.dto.Login)
	 */
	public Response updateLogin(Login principal) {
		LoginResponse resp = new LoginResponse(ResponseStatus.SUCCESS);
		loginDS.updateLogin(principal);

		return resp;
	}
	
	public Response isPasswordEq( 
			String domainId, String principal, 
			String managedSysId ,  String newPassword) {
		
		Response resp = new Response(ResponseStatus.SUCCESS);
		boolean retval = loginDS.isPasswordEq(domainId, principal, managedSysId, newPassword);
		resp.setResponseValue(new Boolean(retval));
		if (!retval) {
			resp.setStatus(ResponseStatus.FAILURE);
		}
		return resp;
	}
	
	/**
	 * Checks to see if a login exists for a user - domain - managed system combination
	 * @param domainId
	 * @param principal
	 * @param sysId
	 * @return
	 */
	public Response loginExists( String domainId, String principal, String managedSysId ) {
		Response resp = new Response(ResponseStatus.SUCCESS);
		boolean retval = loginDS.loginExists(domainId, principal, managedSysId);
		resp.setResponseValue(new Boolean(retval));
		if (!retval) {
			resp.setStatus(ResponseStatus.FAILURE);
		}
		return resp;

	}

	public Response getLoginByDept(String managedSysId, String department, String div) {
		Response resp = new Response(ResponseStatus.SUCCESS);
		List loginList =  loginDS.getLoginByDept(managedSysId, department, div);
		if (loginList != null ) {
			resp.setResponseValue(loginList);
		}
		return resp;
	}
	

	public LoginDataService getLoginDS() {
		return loginDS;
	}

	public void setLoginDS(LoginDataService loginDS) {
		this.loginDS = loginDS;
	}

	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.auth.ws.LoginDataWebService#getPrimaryIdentity(java.lang.String)
	 */
	public LoginResponse getPrimaryIdentity(String userId) {
		LoginResponse resp = new LoginResponse(ResponseStatus.SUCCESS);
		Login lg = loginDS.getPrimaryIdentity(userId);
		if (lg == null ) {
			resp.setStatus(ResponseStatus.FAILURE);
		}else {
			resp.setPrincipal(lg); 
		}
		return resp;
	}

	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.auth.ws.LoginDataWebService#bulkResetPasswordChangeCount()
	 */
	public Response bulkResetPasswordChangeCount() {
		Response resp = new Response(ResponseStatus.SUCCESS);
		int rowCount =  loginDS.bulkResetPasswordChangeCount();
		resp.setResponseValue( new Integer(rowCount));
		return resp;
		
	}

	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.auth.ws.LoginDataWebService#getLockedUserSince(java.util.Date)
	 */
	public LoginListResponse getLockedUserSince(Date lastExecTime) {
		log.info("getLockedUserSince " );
		
		LoginListResponse resp = new LoginListResponse(ResponseStatus.SUCCESS);
		List<Login> lgList = loginDS.getLockedUserSince(lastExecTime);
		if (lgList == null ) {
			resp.setStatus(ResponseStatus.FAILURE);
		}else {
			resp.setPrincipalList(lgList); 
		}
		return resp;
	}

	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.auth.ws.LoginDataWebService#getInactiveUsers(int, int)
	 */
	public LoginListResponse getInactiveUsers(int startDays, int endDays) {
		
		LoginListResponse resp = new LoginListResponse(ResponseStatus.SUCCESS);
		List<Login> lgList = loginDS.getInactiveUsers(startDays, endDays);
		if (lgList == null ) {
			resp.setStatus(ResponseStatus.FAILURE);
		}else {
			resp.setPrincipalList(lgList); 
		}
		return resp;
		
	}

	/* (non-Javadoc)
	 * @see org.openiam.idm.srvc.auth.ws.LoginDataWebService#getUserNearPswdExpiration(int)
	 */
	public LoginListResponse getUserNearPswdExpiration(int expDays) {
		LoginListResponse resp = new LoginListResponse(ResponseStatus.SUCCESS);
		List<Login> lgList = loginDS.getUserNearPswdExpiration(expDays);
		if (lgList == null ) {
			resp.setStatus(ResponseStatus.FAILURE);
		}else {
			resp.setPrincipalList(lgList); 
		}
		return resp;
	}


	public Response changeIdentityName(
			String newPrincipalName, 
			String newPassword, 
			String userId, 
			String managedSysId,
			String domainId) {
		
		LoginResponse resp = new LoginResponse(ResponseStatus.SUCCESS);
		int retval = this.loginDS.changeIdentityName(newPrincipalName, newPassword, userId, managedSysId, domainId);
		if (retval > 0) {
			resp.setResponseValue(new Integer(retval));
		}else {
			resp.setStatus(ResponseStatus.FAILURE);
		}
		
		return resp;
	}


    public LoginListResponse getAllLoginByManagedSys(String managedSysId) {
        LoginListResponse resp = new LoginListResponse(ResponseStatus.SUCCESS);
		List<Login> lgList = loginDS.getAllLoginByManagedSys(managedSysId);
		if (lgList == null ) {
			resp.setStatus(ResponseStatus.FAILURE);
		}else {
			resp.setPrincipalList(lgList);
		}
		return resp;
    }
}
