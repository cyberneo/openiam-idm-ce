/*
 * Copyright 2009, OpenIAM LLC 
 * This file is part of the OpenIAM Identity and Access Management Suite
 *
 *   OpenIAM Identity and Access Management Suite is free software: 
 *   you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License 
 *   version 3 as published by the Free Software Foundation.
 *
 *   OpenIAM is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   Lesser GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with OpenIAM.  If not, see <http://www.gnu.org/licenses/>. *
 */

/**
 *
 */
package org.openiam.idm.srvc.orgpolicy.ws;

import org.openiam.base.ws.Response;
import org.openiam.base.ws.ResponseStatus;
import org.openiam.idm.srvc.orgpolicy.dto.OrgPolicy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Response object for a web service operation that returns a role.
 *
 * @author suneet
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrgPolicyAcceptanceResponse", propOrder = {
        "acceptanceMsg"
})
public class OrgPolicyResponse extends Response {

    OrgPolicy acceptanceMsg;

    public OrgPolicyResponse() {
        super();
    }

    public OrgPolicyResponse(ResponseStatus s) {
        super(s);

    }

    public OrgPolicy getAcceptanceMsg() {
        return acceptanceMsg;
    }

    public void setAcceptanceMsg(OrgPolicy acceptanceMsg) {
        this.acceptanceMsg = acceptanceMsg;
    }


}
