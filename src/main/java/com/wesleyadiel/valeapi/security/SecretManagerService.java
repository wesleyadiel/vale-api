package com.wesleyadiel.valeapi.security;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SecretManagerService {

    private final SecretsManagerClient client;

    public SecretManagerService() {
        this.client = SecretsManagerClient.builder()
                .region(Region.SA_EAST_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    public String getSecret(String secretName, String key) {
        var request = GetSecretValueRequest.builder()
                .secretId(secretName)
                .build();

        var response = client.getSecretValue(request);
        var json = response.secretString();

        Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new RuntimeException("Chave '" + key + "' não encontrada no segredo.");
    }
}
