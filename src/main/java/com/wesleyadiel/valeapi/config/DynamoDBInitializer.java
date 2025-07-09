package com.wesleyadiel.valeapi.config;

import com.wesleyadiel.valeapi.enums.TableName;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

@Component
public class DynamoDBInitializer {

    private final DynamoDbClient dynamoDbClient;

    public DynamoDBInitializer(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    @PostConstruct
    public void init() {
        createUserTableIfNotExists();
    }

    private void createUserTableIfNotExists() {
        System.out.println("Verificando tabelas...");
        ListTablesResponse listTables = dynamoDbClient.listTables();
        if (!listTables.tableNames().contains(TableName.USER.value())) {
            System.out.println("Tabela 'users' não encontrada. Criando...");

            dynamoDbClient.createTable(CreateTableRequest.builder()
                    .tableName(TableName.USER.value())
                    .keySchema(KeySchemaElement.builder()
                            .attributeName("id")
                            .keyType(KeyType.HASH)
                            .build())
                    .attributeDefinitions(AttributeDefinition.builder()
                            .attributeName("id")
                            .attributeType(ScalarAttributeType.S)
                            .build())
                    .billingMode(BillingMode.PAY_PER_REQUEST)
                    .build());
        }

        updateTableToStandardIA(TableName.USER.value());
        System.out.println("Tabelas criadas com sucesso!");
    }

    private void updateTableToStandardIA(String tableName) {
        try {
            DescribeTableResponse describeResponse = dynamoDbClient.describeTable(
                    DescribeTableRequest.builder().tableName(tableName).build());

            TableClassSummary currentClass = describeResponse.table().tableClassSummary();

            boolean alreadyIA = currentClass != null &&
                    TableClass.STANDARD_INFREQUENT_ACCESS.toString().equals(currentClass.tableClassAsString());

            if (alreadyIA) {
                return;
            }

            dynamoDbClient.updateTable(UpdateTableRequest.builder()
                    .tableName(tableName)
                    .tableClass(TableClass.STANDARD_INFREQUENT_ACCESS)
                    .build());

        } catch (DynamoDbException e) {
            System.err.println("Erro ao atualizar tabela para Standard-IA: " + e.getMessage());
        }
    }
}
