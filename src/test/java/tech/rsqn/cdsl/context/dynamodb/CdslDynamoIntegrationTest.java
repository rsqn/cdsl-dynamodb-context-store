
package tech.rsqn.cdsl.context.dynamodb;
 
import tech.rsqn.cdsl.context.*;

import org.testng.annotations.*;
import org.testng.*;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Test
//@ContextConfiguration(locations = {"classpath:/spring/test-registry-integration-ctx.xml"})
public class CdslDynamoIntegrationTest {
    
    private DynamoCdslContextRepository repo;
    
    @BeforeMethod
    public void setup() {
        repo = new DynamoCdslContextRepository();
        
        // /TableNameOverride ??
        repo.withTableNameResolver( new DynamoDBMapperConfig.TableNameResolver()  {
                public String getTableName(Class clazz, DynamoDBMapperConfig config) {
                    return "CdslContextIntegrationTest";
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