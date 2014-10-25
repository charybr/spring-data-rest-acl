package sample.sdr.auth.bean;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name = "RoleEntity")
public class RoleEntity extends AbstractSecuredEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * converts to upper case, to make it case-insensitive
	 */
	@Column(unique = true, nullable = false)
	private String authority;

	@ManyToMany(mappedBy = "roles")
	private Set<UserEntity> users = new HashSet<UserEntity>();

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority.toUpperCase(Locale.ENGLISH);
	}

	public Set<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(Set<UserEntity> users) {
		this.users = users;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((authority == null) ? 0 : authority.hashCode());
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
		RoleEntity other = (RoleEntity) obj;
		if (authority == null) {
			if (other.authority != null)
				return false;
		} else if (!authority.equals(other.authority))
			return false;
		return true;
	}
}
