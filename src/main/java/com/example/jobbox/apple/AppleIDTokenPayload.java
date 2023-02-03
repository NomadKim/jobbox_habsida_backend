package com.example.jobbox.apple;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppleIDTokenPayload {

    private String iss;
    private String aud;
    private Long exp;
    private Long iat;
    private String sub;// users unique id
    private String c_hash;
    private Long auth_time;
    private Boolean nonce_supported;
    private Boolean email_verified;
    private String email;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((c_hash == null) ? 0 : c_hash.hashCode());
        result = prime * result + ((aud == null) ? 0 : aud.hashCode());
        result = prime * result + ((auth_time == null) ? 0 : auth_time.hashCode());
        result = prime * result + ((exp == null) ? 0 : exp.hashCode());
        result = prime * result + ((iat == null) ? 0 : iat.hashCode());
        result = prime * result + ((iss == null) ? 0 : iss.hashCode());
        result = prime * result + ((sub == null) ? 0 : sub.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AppleIDTokenPayload other = (AppleIDTokenPayload) obj;
        if (c_hash == null) {
            if (other.c_hash != null)
                return false;
        } else if (!c_hash.equals(other.c_hash))
            return false;
        if (aud == null) {
            if (other.aud != null)
                return false;
        } else if (!aud.equals(other.aud))
            return false;
        if (auth_time == null) {
            if (other.auth_time != null)
                return false;
        } else if (!auth_time.equals(other.auth_time))
            return false;
        if (exp == null) {
            if (other.exp != null)
                return false;
        } else if (!exp.equals(other.exp))
            return false;
        if (iat == null) {
            if (other.iat != null)
                return false;
        } else if (!iat.equals(other.iat))
            return false;
        if (iss == null) {
            if (other.iss != null)
                return false;
        } else if (!iss.equals(other.iss))
            return false;
        if (sub == null) {
            if (other.sub != null)
                return false;
        } else if (!sub.equals(other.sub))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AppleIDTokenPayload [at_hash=" + c_hash + ", aud=" + aud + ", auth_time=" + auth_time + ", email="
                + email + ", email_verified=" + email_verified + ", exp=" + exp + ", iat=" + iat + ", iss=" + iss
                + ", nonce_supported=" + nonce_supported + ", sub=" + sub + "]";
    }
}
