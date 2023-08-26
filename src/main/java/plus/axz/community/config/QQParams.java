package plus.axz.community.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "qq")
@Data
public class QQParams {
    private String client_id;
    private String redirect_uri;
    private String client_secret;
}
