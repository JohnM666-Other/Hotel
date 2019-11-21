package com.hotel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
    private String clientId = "client";
    private String clientSecret = "secret-key";
    private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEpAIBAAKCAQEAvr0tL9tc39ZotcxRKqRdKebkWAIxC46EUrr55lUhGqLlg/Db\n" +
            "HO8EfSnVZZzj1/3lVEs0sFF6edTfnDBrCRUwg80q09sAsWo6uREgU6Xnf68iwn86\n" +
            "OMdnsX/7XkV3xojsMwPtmXTz6MGw5hkBZVfh/tw2c4DAPLtIf6ztJqAA8S+oXK1r\n" +
            "F+04xLela1t5JzVz8BSkunbolCpw6tVF85epXZVFMdYTNtQoM2vyH+To9wMfvRu2\n" +
            "Wxea7ddW5jEBYb2LgFLaqPH1mbWHxREWRkXlLY2VYqg3Hjg0HoTDJZvRTGG+tkfS\n" +
            "bgNLw6ZukEiuEnL6hs1lO9r7TluwQwk65iOBpwIDAQABAoIBAEZV7l7SboogP5ai\n" +
            "lF+8Ipq8CQInNLzFPihfyJ1WURHIdghUfghKX4ZkMcv5sc9JUhLmdLR8J0Tb6eVn\n" +
            "uWCtDqnbs2pSPQwvVx49oYUXDan5BEFity6O4b8DqccWqNduUGNzlF9dUEBJhJkl\n" +
            "KW2cnbrkq7OmC72Zdl+WfhEbOZ22SHq5xLv46thRxzREGio7iHBp5efOPRnRYaCr\n" +
            "X14K/DqGdONZ6WbFSI2Jrim4+he7fqVF3VWdRC8+gACjR8Xfirqvi5kFjXrpgooU\n" +
            "iLxe1zNTW1JLa3OPgAsdsm3MRnFBOeUifmoJWtNXVGeKNyGkB4QygqNwUhCvl+Ba\n" +
            "LjyfjjkCgYEA9HUYwHOXRRQzmFt/rwuXKiMJ3GN8jYBInJ6nYb+pq5nJqn1KvxfQ\n" +
            "9pJ2NkpG0b0q7vR/xeh3+yf0Ng1wTAGxHPDYt90J5PZz+uBeqvMyf+egdtQD4+uS\n" +
            "JoJ5lON5uWTIo04sYMVSi4iGFadiyEQQYtQjSFxUMr2io9yJ2rDZ9SsCgYEAx77A\n" +
            "unh2ohQwXygqiHBHyigINIHxA4a501f61T2+3vSBTQorU8ZicTsTCXn/YN8LyOwR\n" +
            "pwBviMJ6yPoFsjIXzWWUO+VT+FqOg5YjiGSBc0vlD9YwgRjrOrfqn22hiOIwRw5x\n" +
            "vS5di3BatOPJIXTnm07Fyz6ObR3C10mx2FLi33UCgYEAvCA9SyXpnQS97BtGOOy/\n" +
            "iXLrjMqXOMInnzhNceEw7tlsJCNF2ae3bXorNP6WX6SnnLtWdXTAmO0XeF2BNUz8\n" +
            "A1yqO10E10p+2OvAjuiXzWzwxWHGGKnakH5KmLUyoOUZrutjJZnVO4J2RcXKNHqE\n" +
            "7bl56Z78QQxFVLtM75q4MX8CgYAlSkRay6CCM7iwGySNokCUyhJ2Ewg2u1E31oX6\n" +
            "h3N51jXJdyuNkUzRjNDKBR+bKyjdEs1c2yT3aC9aE+s6UEXHT75zzCV4CRfwEviB\n" +
            "Pxo3tySFZ1xYzZkkti80Ilh04J1YtqafReeq34ffm2LyPwv6H6OnhP87+cFlRdnQ\n" +
            "4/B8/QKBgQDW3/BuydI0RFWXR8tpfm9ll/i+RX/mHsJ4EIq0NSHZFpK9Gw021Ujj\n" +
            "EnmGEjRzYgOZIH12tCRJPDkbfOriy3zptXOBJKsY7g4gmlFfGtb+8ggZKELLeNg5\n" +
            "LlBQAvMSC8GjwU8G5qAlnGep2aKX/EPXTC7fNwvJXjtszdySTl6Aqw==\n" +
            "-----END RSA PRIVATE KEY-----\n";
    private String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvr0tL9tc39ZotcxRKqRd\n" +
            "KebkWAIxC46EUrr55lUhGqLlg/DbHO8EfSnVZZzj1/3lVEs0sFF6edTfnDBrCRUw\n" +
            "g80q09sAsWo6uREgU6Xnf68iwn86OMdnsX/7XkV3xojsMwPtmXTz6MGw5hkBZVfh\n" +
            "/tw2c4DAPLtIf6ztJqAA8S+oXK1rF+04xLela1t5JzVz8BSkunbolCpw6tVF85ep\n" +
            "XZVFMdYTNtQoM2vyH+To9wMfvRu2Wxea7ddW5jEBYb2LgFLaqPH1mbWHxREWRkXl\n" +
            "LY2VYqg3Hjg0HoTDJZvRTGG+tkfSbgNLw6ZukEiuEnL6hs1lO9r7TluwQwk65iOB\n" +
            "pwIDAQAB\n" +
            "-----END PUBLIC KEY-----\n";

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);
        converter.setVerifierKey(publicKey);
        return converter;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient(clientId).secret(clientSecret).scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(20000)
                .refreshTokenValiditySeconds(20000);

    }
}
