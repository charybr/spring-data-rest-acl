package sample.sdr.auth.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="UserACLRequestSet")
public class UserACLRequestSet {
	List<UserACLRequest> aclList;

	public List<UserACLRequest> getAclList() {
		return aclList;
	}

	public void setAclList(List<UserACLRequest> aclList) {
		this.aclList = aclList;
	}
}