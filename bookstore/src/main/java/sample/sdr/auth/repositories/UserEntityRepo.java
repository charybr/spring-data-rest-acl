package sample.sdr.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import sample.sdr.auth.bean.UserEntity;

@RepositoryRestResource(path = "user")
public interface UserEntityRepo extends JpaRepository<UserEntity, Long> {

	UserEntity findByUsername(String username);

}
