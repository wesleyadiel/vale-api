package com.wesleyadiel.valeapi.repository;

import com.wesleyadiel.valeapi.model.User;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
public class UserRepository {

    private final DynamoDbTable<User> userTable;

    public UserRepository(DynamoDbEnhancedClient enhancedClient) {
        // Configura a tabela 'users' com mapeamento para a classe User
        this.userTable = enhancedClient.table("vale.user", TableSchema.fromBean(User.class));
    }

    public void save(User user) {
        userTable.putItem(user);
    }

    public User findById(String id) {
        Key key = Key.builder().partitionValue(id).build();
        return userTable.getItem(key);
    }

    public Optional<User> findByEmail(String email) {
        DynamoDbIndex<User> emailIndex = userTable.index("email-index");
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(email).build());

        SdkIterable<Page<User>> results = emailIndex.query(queryConditional);

        return StreamSupport.stream(results.spliterator(), false)
                .flatMap(page -> page.items().stream())
                .findFirst();
    }

    public void delete(String id) {
        Key key = Key.builder().partitionValue(id).build();
        userTable.deleteItem(key);
    }
}
