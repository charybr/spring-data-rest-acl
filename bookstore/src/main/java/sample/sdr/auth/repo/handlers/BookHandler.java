package sample.sdr.auth.repo.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;

import sample.sdr.auth.bean.AbstractSecuredEntity;
import sample.sdr.auth.bean.Book;
import sample.sdr.auth.dao.SecurityACLDAO;
import sample.sdr.auth.security.SecurityUtil;

@RepositoryEventHandler(Book.class)
public class BookHandler {
	private static Logger logger = LoggerFactory.getLogger(BookHandler.class);

	@Autowired
	private SecurityACLDAO securityACLDAO;
	
	@HandleAfterCreate
	public void afterCreate(Book book) {
		logger.debug("afterCreate:{}", book.toString());
		addACL(book);
	}

	@HandleAfterSave
	public void handleAfterSave(Book book) {
		logger.debug("afterSave:{}", book.toString());
	}
	
	@HandleAfterDelete
	public void handleAfterDelete(Book book) {
		removeACL(book);
	}
	
	private void addACL(AbstractSecuredEntity type) {
		if(type != null) {
			securityACLDAO.addPermission(type, new PrincipalSid(SecurityUtil.getUsername()), BasePermission.ADMINISTRATION);
			securityACLDAO.addPermission(type, new PrincipalSid(SecurityUtil.getUsername()), BasePermission.READ);
			securityACLDAO.addPermission(type, new PrincipalSid(SecurityUtil.getUsername()), BasePermission.WRITE);
			securityACLDAO.addPermission(type, new PrincipalSid(SecurityUtil.getUsername()), BasePermission.DELETE);
		
			securityACLDAO.addPermission(type, new GrantedAuthoritySid("ROLE_ADMIN"), BasePermission.ADMINISTRATION);
		}		
	}

	private void removeACL(AbstractSecuredEntity type) {
		//TBD
	}
}
