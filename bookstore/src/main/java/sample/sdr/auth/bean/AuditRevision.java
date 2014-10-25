package sample.sdr.auth.bean;

import java.text.DateFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.envers.DefaultTrackingModifiedEntitiesRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import sample.sdr.auth.audit.AuditingRevisionListener;

@XmlRootElement(name="AuditRevision")
@Entity
@Table(name="AuditRevision")
@RevisionEntity(AuditingRevisionListener.class)
public class AuditRevision extends DefaultTrackingModifiedEntitiesRevisionEntity {
	
	private static final long serialVersionUID = 1L;

	private String username;

	private String ipAddress;
	
	public String toString() {
	    return "AuditRevision(user = " + username + ", id = " + getId()
	    		+ ", ipaddress = " + ipAddress
	    		+ ", revisionDate = " + DateFormat.getDateTimeInstance().format(getRevisionDate()) + ")";
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((ipAddress == null) ? 0 : ipAddress.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuditRevision other = (AuditRevision) obj;
		if (ipAddress == null) {
			if (other.ipAddress != null)
				return false;
		} else if (!ipAddress.equals(other.ipAddress))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}