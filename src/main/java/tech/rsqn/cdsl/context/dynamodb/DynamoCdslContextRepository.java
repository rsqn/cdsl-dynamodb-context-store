package tech.rsqn.cdsl.context.dynamodb;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.apache.commons.lang3.StringUtils;
import tech.rsqn.cdsl.context.CdslContext;
import tech.rsqn.cdsl.context.CdslContextRepository;
import tech.rsqn.cdsl.model.dynamodb.DynamoCdslContext;

import java.util.Date;

public class DynamoCdslContextRepository implements CdslContextRepository {

    private AmazonDynamoDB dynamoDBClient;
    private DynamoDBMapper mapper;
    private DynamoDBMapperConfig mapperConfig = null;

    private String region = null;
    private String endpoint = null;

    public DynamoCdslContextRepository() {

    }

    public void init() {
        Regions r = Regions.AP_SOUTHEAST_2;
        if ( StringUtils.isNotBlank(region)) {
            r = Regions.fromName(region);
        }

        if (StringUtils.isNotBlank(endpoint)) {
            dynamoDBClient = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                    new AwsClientBuilder.EndpointConfiguration(endpoint, r.getName())).build();
        } else {
            dynamoDBClient = AmazonDynamoDBClientBuilder.standard().withRegion(r).build();
        }

        mapper = new DynamoDBMapper(dynamoDBClient);
        //mapperConfig = DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
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
            return item.getContext();
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
        item.setContext(context);
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
