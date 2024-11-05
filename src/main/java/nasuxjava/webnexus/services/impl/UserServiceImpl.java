package nasuxjava.webnexus.services.impl;

import nasuxjava.webnexus.dto.UserDto;
import nasuxjava.webnexus.entity.Role;
import nasuxjava.webnexus.entity.User;
import nasuxjava.webnexus.repository.RoleRepository;
import nasuxjava.webnexus.repository.UserRepository;
import nasuxjava.webnexus.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    private final String PathUpload = "src/main/resources/static/uploads/";
    // private static final String[] ALLOWED_IMAGE_TYPES = { "image/jpeg",
    // "image/png", "image/gif" };
    // private static final long MAX_FILE_SIZE = 1024 * 1024 * 5; // 5MB

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    @Override
    public UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setPassword(user.getPassword());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());
        userDto.setAddress(user.getAddress());
        userDto.setImageName(user.getImage());
        userDto.setNote(user.getNote());
        userDto.setPhone(user.getPhone());

        return userDto;
    }

    @Override
    public List<UserDto> convertListEntityToDto(List<User> users) {
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public User convertDtoToEntity(UserDto userdDto) {
        User user = new User();
        user.setId(userdDto.getId());
        user.setFullName(userdDto.getFullName());
        user.setEmail(userdDto.getEmail());
        user.setAddress(userdDto.getAddress());
        user.setPassword(userdDto.getPassword());

        if (!userdDto.getImage().isEmpty()) {
            user.setImage(userdDto.getImage().getOriginalFilename());
            try {
                importImage(userdDto.getImage());
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        user.setNote(userdDto.getNote());
        user.setPhone(userdDto.getPhone());

        return user;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = convertDtoToEntity(userDto);
        user.setDatecreate(Date.from(Instant.now()));
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(List.of(role));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public void importImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("File is Empty");
        }
        if (!Files.exists(Paths.get(PathUpload))) {
            Files.createDirectories(Paths.get(PathUpload));
        }
        String fileName = file.getOriginalFilename();
        String filePath = PathUpload + fileName;
        try {
            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }

            throw new RuntimeException(e.getMessage());
        }
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    @Override
    public void saveUserRegister(UserDto userDto) {
        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        // encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setDatecreate(Date.from(Instant.now()));
        Role role = roleRepository.findByName("ROLE_CLIENT");
        if (role == null) {
            role = checkRoleRegisterExist();
        }
        user.setRoles(List.of(role));
        try {
            userRepository.save(user);
        } catch (Exception e) {

        }
    }

    private Role checkRoleRegisterExist() {
        Role role = new Role();
        role.setName("ROLE_CLIENT");
        return roleRepository.save(role);
    }

    @Override
    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public User updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow();
        user.getPassword();
        userDto.getPassword();
        if (!passwordEncoder.matches(user.getPassword(), userDto.getPassword())) {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        user = convertDtoToEntity(userDto);
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public Page<User> findPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }
    // @Override
    // public String getUserlongin() {
    // Authentication authentication =
    // SecurityContextHolder.getContext().getAuthentication();
    // String username = authentication.getName(); // Tên người dùng

    // return username;
    // }
}

// @GetMapping
// public String listUsers(@RequestParam(value = "page", defaultValue = "0") int
// page,
// @RequestParam(value = "size", defaultValue = "10") int size,Model model) {
// List<UserDto> users = userService.getAllUsers();
// UserFilterDto userFilterDto = new UserFilterDto();
// model.addAttribute("userFilterDto", userFilterDto);
// model.addAttribute("users", users);
// model.addAttribute("title", "List Users");
// model.addAttribute("content", PATH + "/list.html");
// return LAYOUT;
// }