package nasuxjava.webnexus.services;

import java.util.List;
import java.util.Optional;

import nasuxjava.webnexus.dto.UserDto;
import nasuxjava.webnexus.entity.User;

public interface UserService {

    User findUserByEmail(String email);

    List<UserDto> getAllUsers();

    Optional<User> getUserById(Long id);

    void saveUser(UserDto userDto);

    void saveUserRegister(UserDto userDto);

    void deleteUser(Long id);

    User updateUser(User user);

    Optional<User> getUserByEmail(String email);

    boolean existsByEmail(String email);

    List<User> getUsersByRole(String role);

    boolean authenticateUser(String email, String password);

}