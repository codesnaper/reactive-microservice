package org.security.modal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AuthenticationProps {

    @Value("${authentication.path.pattern:/**}")
    private List<String> pathPattern;
    @Value("${authentication.open.path.pattern}")
    private List<String> allowPathPattern;
    @Value("${authentication.token.path:/token}")
    private String tokenPath;
    @Value("${authentication.default.user:user}")
    private String defaultUser;
    @Value("${authentication.default.password:password}")
    private String defaultPassword;
    @Value("${authentication.default.user:password}")
    private String defaultRole;

    public List<String> getPathPattern() {
        return pathPattern;
    }

    public void setPathPattern(List<String> pathPattern) {
        this.pathPattern = pathPattern;
    }

    public List<String> getAllowPathPattern() {
        return allowPathPattern;
    }

    public void setAllowPathPattern(List<String> allowPathPattern) {
        this.allowPathPattern = allowPathPattern;
    }

    public String getTokenPath() {
        return tokenPath;
    }

    public void setTokenPath(String tokenPath) {
        this.tokenPath = tokenPath;
    }

    public String getDefaultUser() {
        return defaultUser;
    }

    public void setDefaultUser(String defaultUser) {
        this.defaultUser = defaultUser;
    }

    public String getDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    public String getDefaultRole() {
        return defaultRole;
    }

    public void setDefaultRole(String defaultRole) {
        this.defaultRole = defaultRole;
    }
}
