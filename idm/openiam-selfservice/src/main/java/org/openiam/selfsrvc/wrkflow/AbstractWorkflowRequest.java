package org.openiam.selfsrvc.wrkflow;

import com.thoughtworks.xstream.XStream;
import org.openiam.base.ws.PropertyMap;
import org.openiam.idm.srvc.mngsys.dto.ApproverAssociation;
import org.openiam.idm.srvc.mngsys.service.ManagedSystemDataService;
import org.openiam.idm.srvc.msg.service.MailService;
import org.openiam.idm.srvc.msg.service.MailTemplateParameters;
import org.openiam.idm.srvc.msg.ws.NotificationRequest;
import org.openiam.idm.srvc.org.service.OrganizationDataService;
import org.openiam.idm.srvc.prov.request.dto.ProvisionRequest;
import org.openiam.idm.srvc.prov.request.dto.RequestApprover;
import org.openiam.idm.srvc.prov.request.dto.RequestUser;
import org.openiam.idm.srvc.prov.request.ws.RequestWebService;
import org.openiam.idm.srvc.res.dto.Resource;
import org.openiam.idm.srvc.res.service.ResourceDataService;
import org.openiam.idm.srvc.user.dto.DelegationFilterSearch;
import org.openiam.idm.srvc.user.dto.Supervisor;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.user.ws.UserDataWebService;
import org.openiam.provision.dto.ProvisionUser;
import org.springframework.web.servlet.mvc.AbstractController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public abstract class AbstractWorkflowRequest extends AbstractController {

    public static final String NEW_PENDING_REQUEST_NOTIFICATION = "NEW_PENDING_REQUEST";
    protected UserDataWebService userManager = null;
    protected OrganizationDataService orgManager = null;
    protected ResourceDataService resourceDataService;
    protected ManagedSystemDataService managedSysService;
    protected MailService mailService;
    protected RequestWebService provRequestService;


    protected ProvisionRequest createRequest(WorkflowRequest wrkFlowRequest) {
        ProvisionRequest req = new ProvisionRequest();
        Date curDate = new Date(System.currentTimeMillis());


        String workflowResourceId = wrkFlowRequest.getWorkflowResId();
        String personId = wrkFlowRequest.getPersonId();
        String requestorId = wrkFlowRequest.getRequestorId();

        Resource wrkflowResource = resourceDataService.getResource(workflowResourceId);

        User userData = userManager.getUserWithDependent(personId, true).getUser();

        // put the user information into a consistent object that we can serialize
        ProvisionUser pUser = new ProvisionUser(userData);
        String userAsXML = toXML(pUser);

        User requestor = userManager.getUserWithDependent(requestorId,false).getUser();

        // build the request object

        req.setRequestId(null);
        req.setStatus("PENDING");
        req.setStatusDate(curDate);
        req.setRequestDate(curDate);
        req.setRequestType(workflowResourceId);
        req.setWorkflowName(wrkflowResource.getName());
        req.setRequestorId(requestorId);
        req.setRequestorFirstName(requestor.getFirstName());
        req.setRequestorLastName(requestor.getLastName());


        req.setRequestTitle(wrkflowResource.getDescription() + " FOR:" + userData.getFirstName() + " " + userData.getLastName());
        req.setRequestReason(wrkFlowRequest.getDescription());


        req.setRequestXML(userAsXML);

        // add a user to the request - this is the person that we are terminating
        Set<RequestUser> reqUserSet = req.getRequestUsers();
        RequestUser reqUser = new RequestUser();
        reqUser.setFirstName(userData.getFirstName());
        reqUser.setLastName(userData.getLastName());
        reqUser.setUserId(userData.getUserId());
        reqUser.setDeptCd(userData.getDeptCd());
        reqUserSet.add(reqUser);


        RequestApprover reqApprover = getApprover(workflowResourceId, userData);
        req.getRequestApprovers().add(reqApprover);

        notifyApprover(req, reqUser, requestorId, userData);

        return req;


    }

    protected String getUserName(ProvisionRequest req ) {
        Set<RequestUser> requestUserSet = req.getRequestUsers();
        if (requestUserSet != null) {
            for ( RequestUser user :requestUserSet ) {

              return  user.getFirstName() + " " + user.getLastName();


            }
        }
        return null;

    }

    private RequestApprover getApprover(String workflowResourceId, User userData) {

        String approverId = null;
        int applyDelegationFilter = 0;

        // get the approvers for this request
        List<ApproverAssociation> apList = managedSysService.getApproverByRequestType(workflowResourceId, 1);

        if (apList != null) {

            for (ApproverAssociation ap : apList) {
                String approverType;
                String roleDomain = null;

                if (ap != null) {
                    approverType = ap.getAssociationType();


                    if (ap.getAssociationType().equalsIgnoreCase("SUPERVISOR")) {
                        Supervisor supVisor = userData.getSupervisor();
                        approverId = supVisor.getSupervisor().getUserId();


                    } else if (ap.getAssociationType().equalsIgnoreCase("ROLE")) {
                        approverId = ap.getApproverRoleId();
                        roleDomain = ap.getApproverRoleDomain();

                        if (ap.getApplyDelegationFilter() != null) {

                            applyDelegationFilter = ap.getApplyDelegationFilter().intValue();
                        }


                    } else {
                        approverId = ap.getApproverUserId();
                    }


                    RequestApprover reqApprover = new RequestApprover(approverId, ap.getApproverLevel(), ap.getAssociationType(), "PENDING");
                    reqApprover.setApproverType(approverType);
                    reqApprover.setRoleDomain(roleDomain);
                    reqApprover.setApplyDelegationFilter(applyDelegationFilter);

                    return reqApprover;
                }

            }
        }
        return null;

    }


    private void notifyApprover(ProvisionRequest pReq, RequestUser reqUser, String requestorId,
                                User usr) {

        // requestor information
        //  User approver = userMgr.getUserWithDependent(approverUserId, false).getUser();

        Set<RequestApprover> approverList = pReq.getRequestApprovers();
        for (RequestApprover ra : approverList) {

            User requestor = userManager.getUserWithDependent(requestorId, false).getUser();

            if (!"ROLE".equalsIgnoreCase(ra.getApproverType())) {
                // approver type is either User or Supervisor

                HashMap<String, String> mailParameters = new HashMap<String, String>();
                mailParameters.put(MailTemplateParameters.USER_ID.value(), ra.getApproverId());
                mailParameters.put(MailTemplateParameters.REQUEST_ID.value(), pReq.getRequestId());
                mailParameters.put(MailTemplateParameters.REQUESTER.value(), requestor.getFirstName() + " " + requestor.getLastName());
                mailParameters.put(MailTemplateParameters.REQUEST_REASON.value(), pReq.getRequestTitle());
                mailParameters.put(MailTemplateParameters.TARGET_USER.value(), reqUser.getFirstName() + " " + reqUser.getLastName());

                mailService.sendNotificationRequest(new NotificationRequest(NEW_PENDING_REQUEST_NOTIFICATION,mailParameters));
            } else {


                // approver type is role
                DelegationFilterSearch search = new DelegationFilterSearch();

                // when working with role - the approver ID is the role
                search.setRole(ra.getApproverId());
                if ( usr.getCompanyId() != null) {
                    search.setDelAdmin(ra.getApplyDelegationFilter());
                    search.setOrgFilter("%" + usr.getCompanyId() + "%");
                }

                List<User> roleApprovers = userManager.searchByDelegationProperties(search).getUserList();

                System.out.println("List of approvers for Role: " + roleApprovers);

                if (roleApprovers != null && !roleApprovers.isEmpty()) {
                    for (User u : roleApprovers) {

                        HashMap<String, String> mailParameters = new HashMap<String, String>();
                        mailParameters.put(MailTemplateParameters.USER_ID.value(), u.getUserId());
                        mailParameters.put(MailTemplateParameters.REQUEST_ID.value(), pReq.getRequestId());
                        mailParameters.put(MailTemplateParameters.REQUESTER.value(), usr.getFirstName() + " " + usr.getLastName());
                        mailParameters.put(MailTemplateParameters.REQUEST_REASON.value(), pReq.getRequestReason());
                        mailParameters.put(MailTemplateParameters.TARGET_USER.value(), reqUser.getFirstName() + " " + reqUser.getLastName());

                        mailService.sendNotificationRequest(new NotificationRequest(NEW_PENDING_REQUEST_NOTIFICATION,mailParameters));
                    }

                }
            }
        }


    }

    private String toXML(ProvisionUser pUser) {
        XStream xstream = new XStream();
        return xstream.toXML(pUser);

    }

    public UserDataWebService getUserManager() {
        return userManager;
    }

    public void setUserManager(UserDataWebService userManager) {
        this.userManager = userManager;
    }

    public OrganizationDataService getOrgManager() {
        return orgManager;
    }

    public void setOrgManager(OrganizationDataService orgManager) {
        this.orgManager = orgManager;
    }

    public ResourceDataService getResourceDataService() {
        return resourceDataService;
    }

    public void setResourceDataService(ResourceDataService resourceDataService) {
        this.resourceDataService = resourceDataService;
    }

    public ManagedSystemDataService getManagedSysService() {
        return managedSysService;
    }

    public void setManagedSysService(ManagedSystemDataService managedSysService) {
        this.managedSysService = managedSysService;
    }

    public MailService getMailService() {
        return mailService;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    public RequestWebService getProvRequestService() {
        return provRequestService;
    }

    public void setProvRequestService(RequestWebService provRequestService) {
        this.provRequestService = provRequestService;
    }
}
