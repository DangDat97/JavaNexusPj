package nasuxjava.webnexus.model;

import java.util.Collection;

public class UserLoginInfo {
    private String email;
    private Collection<String> roles;

    public UserLoginInfo(String email, Collection<String> roles) {
        this.email = email;
        this.roles = roles;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }
}