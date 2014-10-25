package sample.sdr.auth.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import sample.sdr.auth.bean.Book;

@RepositoryRestResource(path = "book")
public interface BookRepository extends CrudRepository<Book, Long> {
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#book, 'write')")
	<S extends Book> Book save(@P("book") Book book);

	@Override
    @PostFilter("hasPermission(filterObject, 'read') or hasPermission(filterObject, admin)")
	Iterable<Book> findAll();	
}
