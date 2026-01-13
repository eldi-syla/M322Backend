package ch.bbw.er.backend.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppConfiguration {

    private String[] allowedUrls;
    private String[] authUrls;
    private String[] allowedOrigins;

    public String[] getAllowedUrls() {
        return allowedUrls;
    }

    public void setAllowedUrls(String[] allowedUrls) {
        this.allowedUrls = allowedUrls;
    }

    public String[] getAuthUrls() {
        return authUrls;
    }

    public void setAuthUrls(String[] authUrls) {
        this.authUrls = authUrls;
    }

    public String[] getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(String[] allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }
}
