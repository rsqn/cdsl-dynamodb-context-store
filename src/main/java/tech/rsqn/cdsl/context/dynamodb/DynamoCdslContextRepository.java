package tech.rsqn.cdsl.context.dynamodb;

import tech.rsqn.cdsl.model.dynamodb.*;

import java.util.Date;
import com.amazonaws.regions.Regions;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import tech.rsqn.cdsl.context.*;

public class DynamoCdslContextRepository implements CdslContextRepository {

    private AmazonDynamoDB dynamoDBClient = new AmazonDynamoDBClient();
    private DynamoDBMapper mapper = new DynamoDBMapper(dynamoDBClient);
    private DynamoDBMapperConfig mapperConfig = null;
    
    public DynamoCdslContextRepository() {
        Regions usWest2 = Regions.AP_SOUTHEAST_2;
        
        dynamoDBClient = AmazonDynamoDBClientBuilder.standard().withRegion(usWest2).build();
        mapper = new DynamoDBMapper(dynamoDBClient);
        //mapperConfig = DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
    }
    
    public void withTableNameResolver(DynamoDBMapperConfig.TableNameResolver resolver) {
        DynamoDBMapperConfig.Builder builder = new DynamoDBMapperConfig.Builder();
        builder.setTableNameResolver(resolver);
        // DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
        
        mapper = new DynamoDBMapper(dynamoDBClient, builder.build());
    }
    
    @Override
    public CdslContext getContext(String contextId) {
       return getContext(null,contextId);
    }

    @Override
    public CdslContext getContext(String transactionId, String contextId) {
        DynamoCdslContext item = mapper.load(DynamoCdslContext.class, contextId, mapperConfig); 
                
        if ( item != null ) {
            return item.getContext().to();
        }
        return null;
    }

    public void saveContext(String transactionId, CdslContext context) {
        /*
          if (openTxn != null) {
            if (openTxn.equals(transactionId)) {
                contexts.put(context.getId(), context);
                openTransactions.remove(context.getId());
            } else {
                throw new CdslException("mismatched transaction id " + transactionId + " vs open txn " + openTxn);
            }
        } else {
            contexts.put(context.getId(), context);
        }
        */
        DynamoCdslContext item = mapper.load(DynamoCdslContext.class, context.getId(), mapperConfig); 
        
        if ( item != null ) {
            // update the intern    
            // check stuff
        } else {
            item = new DynamoCdslContext();
        }
        
        item.setId(context.getId());
        item.setContextId(context.getId());
        item.setContext(new DynamoCdslContextWrapper().from(context));
        item.setModifiedTs(new Date());
        
        mapper.save(item,mapperConfig);
    }
    
    public int deleteContext(String contextId) {
        
        DynamoCdslContext item = mapper.load(DynamoCdslContext.class, contextId, mapperConfig); 
        
        if ( item != null  ) {
            mapper.delete(item);
            return 1;
        }
        return 0;
    }
}
