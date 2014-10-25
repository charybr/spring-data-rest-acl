package sample.sdr.auth.bean;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.envers.Audited;

@Audited
@XmlRootElement(name="AbstractSecuredEntity")
@MappedSuperclass
public abstract class AbstractSecuredEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractSecuredEntity other = (AbstractSecuredEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
