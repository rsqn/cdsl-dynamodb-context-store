package tech.rsqn.cdsl.context.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import tech.rsqn.cdsl.context.CdslContext;

public class CdslContextDynamoTypeConverter implements DynamoDBTypeConverter<String, CdslContext> {

    @Override
    public String convert(CdslContext object) {
        Gson gson = new GsonBuilder().create();

        String str = gson.toJson(object);
        return str;
    }

    @Override
    public CdslContext unconvert(String s) {
        Gson gson = new GsonBuilder().create();
        CdslContext context = gson.fromJson(s, CdslContext.class);
        return context;
    }
}
    