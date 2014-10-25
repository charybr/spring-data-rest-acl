package sample.sdr.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import sample.sdr.auth.bean.RoleEntity;

@RepositoryRestResource(path = "role")
public interface RoleEntityRepo extends JpaRepository<RoleEntity, Long> {

}
