package sample.sdr.auth.bean;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@XmlRootElement(name="UserEntity")
public class UserEntity extends Person {
	private static final long serialVersionUID = 1L;

	/**
	 * converts to lower case, to make it case-insensitive
	 */
	@Column(unique=true, nullable=false)	
	private String username;

	/**
	 * encoded password
	 */
	@Column(nullable=false)	
	private String password;
	
	private boolean enabled;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private Set<RoleEntity> roles = new HashSet<RoleEntity>();

	public void setPassword(String password) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		this.password = encoder.encode(password);
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username.toLowerCase(Locale.ENGLISH);
	}

	public String getPassword() {
		return password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		UserEntity other = (UserEntity) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
