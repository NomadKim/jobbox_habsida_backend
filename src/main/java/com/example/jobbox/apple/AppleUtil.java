package com.example.jobbox.apple;

import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.security.PrivateKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class AppleUtil {

//    @Value("${apple.key.id}")
//    private String KEY_ID;
//    @Value("${apple.team.id}")
//    private String TEAM_ID;
//    @Value("${apple.key.path}")
//    private String KEY_PATH;
//    @Value("${apple.iss}")
//    private String ISS;
//
//    private PrivateKey getPrivateKey() throws Exception {
//        String path = new ClassPathResource(KEY_PATH).getFile().getAbsolutePath();
//        File file = ResourceUtils.getFile(path);
//
//        final PEMParser pemParser = new PEMParser(new FileReader(file));
//        final JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
//        final PrivateKeyInfo object = (PrivateKeyInfo) pemParser.readObject();
//        final PrivateKey pKey = converter.getPrivateKey(object);
//        pemParser.close();
//        return pKey;
//    }
//    public String generateJWT(String identiferFromApp) throws Exception {
//        PrivateKey pKey = getPrivateKey();
//
//        String token = Jwts.builder()
//                .setHeaderParam(JwsHeader.ALGORITHM, "ES256")
//                .setHeaderParam(JwsHeader.KEY_ID, KEY_ID)
//                .setIssuer(TEAM_ID)
//                .setAudience(ISS)
//                .setSubject(identiferFromApp)
//                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 5)))
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .signWith(SignatureAlgorithm.ES256, pKey)
//                .compact();
//        return token;
//    }

}
