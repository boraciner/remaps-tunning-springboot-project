package mg.remaps.service.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mg.remaps.service.entity.MGUserDetails;
import mg.remaps.service.entity.User;

@Repository
public interface MGUserRepository extends CrudRepository<MGUserDetails, Long>  {
    public Optional<MGUserDetails> findByUsername(String username);
}