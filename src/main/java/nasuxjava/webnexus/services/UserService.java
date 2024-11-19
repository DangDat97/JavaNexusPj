package nasuxjava.webnexus.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import nasuxjava.webnexus.dto.UserDto;
import nasuxjava.webnexus.entity.User;

public interface UserService {

    User findUserByEmail(String email);

    List<UserDto> getAllUsers();

    Optional<User> getUserById(Long id);

    void saveUser(UserDto userDto);

    void saveUserRegister(UserDto userDto);

    User saveUserOrder(User user, String password);

    User updateUserDetails(User user);

    void deleteUser(Long id);

    User updateUser(UserDto userDto);

    UserDto convertEntityToDto(User user);

    List<UserDto> convertListEntityToDto(List<User> user);

    User convertDtoToEntity(UserDto userDto);

    Optional<User> getUserByEmail(String email);

    boolean existsByEmail(String email);

    List<User> getUsersByRole(String role);

    boolean authenticateUser(String email, String password);

    Page<User> findPaginated(int page, int size);

    Page<User> findPaginatedAndFiltered(int page, int size, String fullname, String email, String phone);

    long countUser();
}