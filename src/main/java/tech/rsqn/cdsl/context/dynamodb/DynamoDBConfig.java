package tech.rsqn.cdsl.context.dynamodb;

import tech.rsqn.cdsl.exceptions.CdslException;
import tech.rsqn.cdsl.model.dynamodb.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import com.amazonaws.regions.Regions;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import tech.rsqn.cdsl.context.*;

//@Configuration
//@EnableDynamoDBRepositories(dynamoDBMapperConfigRef = "dynamoDBMapperConfig",
//    basePackages = "tech.rsqn.cdsl")
public class DynamoDBConfig {
   // @Bean
    //public DynamoDBMapperConfig dynamoDBMapperConfig() {
   //     DynamoDBMapperConfig.Builder builder = new DynamoDBMapperConfig.Builder();
    //    builder.setTableNameResolver(new YourCustomTableNameResolverHere());
    //    return builder.build();
    //}
}