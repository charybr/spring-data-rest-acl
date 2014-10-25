package sample.sdr.auth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.Permission;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sample.sdr.auth.bean.AbstractSecuredEntity;
import sample.sdr.auth.bean.Book;
import sample.sdr.auth.bean.UserEntity;
import sample.sdr.auth.dao.SecurityACLDAO;
import sample.sdr.auth.dto.UserACLRequest;
import sample.sdr.auth.dto.UserACLRequestSet;
import sample.sdr.auth.repositories.UserEntityRepo;
import sample.sdr.auth.util.Constants;

@Controller
public class ACLController implements BeanFactoryAware {
	private static Logger logger = LoggerFactory.getLogger(ACLController.class);

	private static HashMap<String, String> classToRepoMap = new HashMap<String, String>();
	
	static {
		classToRepoMap.put(Book.class.getSimpleName(), Constants.BOOK_REPOSITORY);
	}

	private BeanFactory beanFactory;

	@Autowired
	private UserEntityRepo userEntityRepo;
	
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
    
    public static Permission getPermissionFromNumber(int permNum) {
    	switch(permNum) {
    	case 0: return BasePermission.READ; //1
    	case 1: return BasePermission.WRITE; //2
    	case 2: return BasePermission.CREATE; //4
    	case 3: return BasePermission.DELETE; //8
    	case 4: return BasePermission.ADMINISTRATION; //16
    	default: return null;
    	}
    }
	
	private CrudRepository getRepo(String repoName) {
		return (CrudRepository) beanFactory.getBean(repoName);
	}
    
	@RequestMapping(method = RequestMethod.POST, value = "/acl/user/generic/")
	public @ResponseBody HttpEntity<List<String>> addACLUserGeneric(
			@RequestBody UserACLRequestSet aclSet) {
		List<String> result = new ArrayList<String>();
		try {
			SecurityACLDAO securityACLDAO = beanFactory.getBean(
					Constants.SECURITYACL_DAO, SecurityACLDAO.class);

			logger.debug("entityList size:" + aclSet.getAclList().size());
			String entityId = null;
			for (UserACLRequest acl : aclSet.getAclList()) {
				entityId = acl.getEntityId();
				String repoName = classToRepoMap.get(acl.getEntityClassName());
				CrudRepository repo = getRepo(repoName);
				AbstractSecuredEntity securedEntity = (AbstractSecuredEntity) repo
						.findOne(Long.parseLong(entityId));
				if (securedEntity == null) {
					result.add("Entity of type " + acl.getEntityClassName()
							+ " with id " + acl.getEntityId() + " not found");
					continue;
				}
				UserEntity userEntity = userEntityRepo.findByUsername(acl
						.getUserName());
				if (userEntity == null) {
					result.add("User " + acl.getUserName() + " not found");
					continue;
				}
				boolean res = securityACLDAO.addAccessControlEntry(
						securedEntity,
						new PrincipalSid(userEntity.getUsername()),
						getPermissionFromNumber(acl.getPermission()));
				if (res) {
					// on sucess don't add msg to result list
					logger.debug("Added ACL for:" + acl.toString());
				} else {
					result.add("Failed to add ACL for:" + acl.toString());
				}
			}
		} catch (Exception e) {
			logger.warn("", e);
			result.add("Exception:" + e.getMessage());
		}
		if (result.isEmpty()) {
			result.add("Successfully added all ACLs");
			return new ResponseEntity<List<String>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<String>>(result, HttpStatus.OK);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/acl/user/generic/")
	public @ResponseBody HttpEntity<List<String>> removeACLUserGeneric(
			@RequestBody UserACLRequestSet aclSet) {
		List<String> result = new ArrayList<String>();
		try {
			SecurityACLDAO securityACLDAO = beanFactory.getBean(
					Constants.SECURITYACL_DAO, SecurityACLDAO.class);

			logger.debug("entityList size:" + aclSet.getAclList().size());
			String entityId = null;
			for (UserACLRequest acl : aclSet.getAclList()) {
				entityId = acl.getEntityId();
				String repoName = classToRepoMap.get(acl.getEntityClassName());
				CrudRepository repo = getRepo(repoName);
				AbstractSecuredEntity securedEntity = (AbstractSecuredEntity) repo
						.findOne(Long.parseLong(entityId));
				if (securedEntity == null) {
					result.add("Entity of type " + acl.getEntityClassName()
							+ " with id " + acl.getEntityId() + " not found");
					continue;
				}
				UserEntity userEntity = userEntityRepo.findByUsername(acl
						.getUserName());
				if (userEntity == null) {
					result.add("User " + acl.getUserName() + " not found");
					continue;
				}
				boolean res = securityACLDAO.deleteAccessControlEntry(
						securedEntity,
						new PrincipalSid(userEntity.getUsername()),
						getPermissionFromNumber(acl.getPermission()));
				if (res) {
					// on sucess don't add msg to result list
					logger.debug("Deleted ACL for:" + acl.toString());
				} else {
					result.add("Failed to add ACL for:" + acl.toString());
				}
			}
		} catch (Exception e) {
			logger.warn("", e);
			result.add("Exception:" + e.getMessage());
		}
		if (result.isEmpty()) {
			result.add("Successfully deleted all ACLs");
			return new ResponseEntity<List<String>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<String>>(result, HttpStatus.OK);
		}
	}
}
