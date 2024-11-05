package nasuxjava.webnexus.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import nasuxjava.webnexus.entity.User;

@Repository
public interface UserFilterResposotory {

    Page<User> findFilteredUsers(String fullName, String email, String phone, Pageable pageable);
}
