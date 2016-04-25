package data;

public class Attribute {

    public String name;
    public String value;
    public AttributeDefinition definition;

    public Attribute(AttributeDefinition attributeDefinition, String value){
        this.name = attributeDefinition.getAttributeName();
        this.value = value;
        this.definition = attributeDefinition;
    }

}
