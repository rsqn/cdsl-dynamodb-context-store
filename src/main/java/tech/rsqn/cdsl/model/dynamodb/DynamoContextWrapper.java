 package tech.rsqn.cdsl.model.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import java.util.Date;
import tech.rsqn.cdsl.context.*;
import org.springframework.beans.BeanUtils;

import org.apache.commons.lang3.builder.ToStringBuilder;

@DynamoDBDocument
public class DynamoContextWrapper extends CdslContext { 
   
    public DynamoContextWrapper from (CdslContext src) {
        BeanUtils.copyProperties(this,src);
        
          
        System.out.println("xFROMA " + ToStringBuilder.reflectionToString(src));
        System.out.println("xFROMB " + ToStringBuilder.reflectionToString(this));

        return this;
    }   
    
    public CdslContext to () {
        CdslContext ret = new CdslContext();
        BeanUtils.copyProperties(ret,this);
     
        System.out.println("xTOA " + ToStringBuilder.reflectionToString(ret));
        System.out.println("xTOB " + ToStringBuilder.reflectionToString(ret));

        return ret;
    }
};