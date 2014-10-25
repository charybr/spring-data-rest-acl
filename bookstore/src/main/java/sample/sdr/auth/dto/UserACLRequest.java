package sample.sdr.auth.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="UserACLRequest")
public class UserACLRequest {
	String entityClassName;
	String entityId;
	String userName;
	Integer permission;

	public Integer getPermission() {
		return permission;
	}
	public void setPermission(Integer permission) {
		this.permission = permission;
	}
	public String getEntityClassName() {
		return entityClassName;
	}
	public void setEntityClassName(String entityClassName) {
		this.entityClassName = entityClassName;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "UserACLRequest [entityClassName=" + entityClassName
				+ ", entityId=" + entityId + ", userName=" + userName
				+ ", permission=" + permission + "]";
	}
}
