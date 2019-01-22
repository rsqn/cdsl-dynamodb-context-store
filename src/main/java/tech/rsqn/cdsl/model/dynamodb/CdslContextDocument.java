package tech.rsqn.cdsl.model.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import org.springframework.beans.BeanUtils;
import tech.rsqn.cdsl.context.CdslContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DynamoDBDocument
// The content of this is much more readable as a dynamodb document - and I dont want dynamo dependencies in cdsl
public class CdslContextDocument {
    private String id;
    private CdslContext.State state;
    private String currentFlow;
    private String currentStep;
    private Map<String, String> vars = new HashMap();
    private List<String> transitions = new ArrayList<>();

    public CdslContextDocument with(CdslContext ctx) {
        BeanUtils.copyProperties(ctx, this);
        return this;
    }

    public CdslContext convert() {
        CdslContext ret = new CdslContext();
        BeanUtils.copyProperties(this, ret);
        return ret;
    }

    @DynamoDBAttribute(attributeName="id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName="state")
    @DynamoDBTypeConvertedEnum
    public CdslContext.State getState() {
        return state;
    }

    public void setState(CdslContext.State state) {
        this.state = state;
    }

    @DynamoDBAttribute(attributeName="currentFlow")
    public String getCurrentFlow() {
        return currentFlow;
    }

    public void setCurrentFlow(String currentFlow) {
        this.currentFlow = currentFlow;
    }

    @DynamoDBAttribute(attributeName="currentStep")
    public String getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(String currentStep) {
        this.currentStep = currentStep;
    }

    @DynamoDBAttribute(attributeName="vars")
    public Map<String, String> getVars() {
        return vars;
    }

    public void setVars(Map<String, String> vars) {
        this.vars = vars;
    }

    @DynamoDBAttribute(attributeName="transitions")
    public List<String> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<String> transitions) {
        this.transitions = transitions;
    }
}

