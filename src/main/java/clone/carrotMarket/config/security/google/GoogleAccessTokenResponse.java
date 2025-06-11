package clone.carrotMarket.config.security.google;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GoogleAccessTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;
}
