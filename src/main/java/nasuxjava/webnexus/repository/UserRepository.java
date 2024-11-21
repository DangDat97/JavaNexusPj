package nasuxjava.webnexus.repository;

import nasuxjava.webnexus.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long> {
    User findByEmail(String email);
    // Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByRole(String role);

    Page<User> findByFullNameContainingAndEmailContainingAndPhoneContaining(String fullName, String email,
            String phone, Pageable pageable);

    long count();
}