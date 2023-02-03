package com.example.jobbox.apple;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class TokenResponse {

    private String access_token;
    private String token_type;
    private Long expires_in;
    private String refresh_token;
    private String id_token;
    private String error;

    @Override
    public String toString() {
        return "TokenResponse [access_token=" + access_token + ", expires_in=" + expires_in + ", id_token=" + id_token
                + ", refresh_token=" + refresh_token + ", token_type=" + token_type + ", error=" + error + "]";
    }
}
