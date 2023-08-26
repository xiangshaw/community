package plus.axz.community.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "github")
@Data
public class GithubParams {
    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String token_uri;
    private String user_uri;
}
