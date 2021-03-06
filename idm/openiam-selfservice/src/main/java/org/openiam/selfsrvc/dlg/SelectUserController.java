package org.openiam.selfsrvc.dlg;


import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.user.ws.UserDataWebService;
import org.openiam.idm.srvc.user.ws.UserListResponse;
import org.openiam.idm.srvc.user.dto.UserSearch;
import org.openiam.idm.srvc.user.dto.User;



public class SelectUserController extends AbstractController {


	private static final Log log = LogFactory.getLog(SelectUserController.class);
	protected String successView;
	protected UserDataWebService userMgr;
	protected Integer maxResultSize;

	
	
	public SelectUserController() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub


		
		request.getSession().removeAttribute("supervisorList");
		request.getSession().removeAttribute("result");
		request.getSession().removeAttribute("msg");
		
		ModelAndView mav = new ModelAndView(new RedirectView(successView, true));
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		
		UserSearch search = new UserSearch();
		if (firstName != null && firstName.length() > 0) {
			search.setFirstName(firstName + "%");
		}
		if (lastName != null && lastName.length() > 0) {
			search.setLastName(lastName + "%");
		}
		search.setStatus("ACTIVE");
		search.setMaxResultSize(maxResultSize);
		UserListResponse resp = userMgr.search(search);
		if (resp.getStatus().equals(ResponseStatus.SUCCESS)) {
			List<User> userList = userMgr.search(search).getUserList();
	        if (userList != null) {
	            request.getSession().setAttribute("supervisorList", userList );
	        }
		}else {
			request.getSession().setAttribute("msg", "No matches found");
		}
		request.getSession().setAttribute("result", "1");
		
		return mav;
	}





	public String getSuccessView() {
		return successView;
	}


	public void setSuccessView(String successView) {
		this.successView = successView;
	}

	public UserDataWebService getUserMgr() {
		return userMgr;
	}

	public void setUserMgr(UserDataWebService userMgr) {
		this.userMgr = userMgr;
	}

	public Integer getMaxResultSize() {
		return maxResultSize;
	}

	public void setMaxResultSize(Integer maxResultSize) {
		this.maxResultSize = maxResultSize;
	}



	

}
