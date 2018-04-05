
package tech.rsqn.cdsl.context.dynamodb;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.model.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tech.rsqn.cdsl.context.CdslContext;
import tech.rsqn.cdsl.context.CdslContextAuditorUnitTestSupport;
import tech.rsqn.cdsl.context.CdslRuntime;

import java.util.ArrayList;

@Test
//@ContextConfiguration(locations = {"classpath:/spring/test-registry-integration-ctx.xml"})
public class CdslDynamoIntegrationTest {
    
    private DynamoCdslContextRepository repo;
    private static String testTableName = "CdslContextIntegrationTest";
    @BeforeMethod
    public void setup() {
        repo = new DynamoCdslContextRepository();

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();

        ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
        keySchema.add(new KeySchemaElement().withAttributeName("id").withKeyType(KeyType.HASH)); // Partition
        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
        attributeDefinitions
                .add(new AttributeDefinition().withAttributeName("id").withAttributeType("S"));


        CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(testTableName)
                .withKeySchema(keySchema)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(10L)
                        .withWriteCapacityUnits(10L));
        createTableRequest.setAttributeDefinitions(attributeDefinitions);
        client.createTable(createTableRequest);

        repo.withClient(client);

        // /TableNameOverride ??
        repo.withTableNameResolver( new DynamoDBMapperConfig.TableNameResolver()  {
                public String getTableName(Class clazz, DynamoDBMapperConfig config) {
                    return testTableName;
                }
            }
            );
            
        repo.deleteContext("shouldCrudContext");
    }
    
       
    @Test(groups="integration")
    public void shouldCrudContext() {
        CdslContext ctx = repo.getContext("shouldCrudContext");    
        Assert.assertNull(ctx);

        CdslRuntime rt = new CdslRuntime();
        rt.setAuditor(new CdslContextAuditorUnitTestSupport());
        ctx = new CdslContext();
        ctx.setRuntime(rt);

        ctx.setId("shouldCrudContext");
        ctx.setCurrentFlow("okeydokey");
        ctx.putVar("butter","chicken");
        
        repo.saveContext("1",ctx);
        
        ctx = repo.getContext("shouldCrudContext");    
        Assert.assertNotNull(ctx);
        
        System.out.println("xxx" + ToStringBuilder.reflectionToString(ctx));
        
        Assert.assertEquals(ctx.getCurrentFlow(),"okeydokey");
    }
}