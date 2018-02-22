 package tech.rsqn.cdsl.model.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import tech.rsqn.cdsl.context.*;
import org.springframework.beans.BeanUtils;

import org.apache.commons.lang3.builder.ToStringBuilder;

@DynamoDBDocument
public class DynamoCdslContextWrapper extends CdslContext {

    private String lcl = "mm";

    public String getLcl() {
        return lcl;
    }

    public void setLcl(String lcl) {
        this.lcl = lcl;
    }

    public DynamoCdslContextWrapper from (CdslContext src) {
        BeanUtils.copyProperties(src,this);
        
          
        System.out.println("xFROMA " + ToStringBuilder.reflectionToString(src));
        System.out.println("xFROMB " + ToStringBuilder.reflectionToString(this));

        return this;
    }   
    
    public CdslContext to () {
        CdslContext ret = new CdslContext();
        BeanUtils.copyProperties(this,ret);
     
        System.out.println("xTOA " + ToStringBuilder.reflectionToString(this));
        System.out.println("xTOB " + ToStringBuilder.reflectionToString(ret));

        return ret;
    }
};