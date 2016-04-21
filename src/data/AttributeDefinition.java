package data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * This class is used to define an Attribute and its values.
 * @author v-equispe
 *
 */
public class AttributeDefinition {
	
	private final HashMap<String, String[]> attributeValueMap = new HashMap<>();
	private TYPE type;
	private int attributeIndex = -1;
	public static int attributeCount = 0;
	
	private AttributeDefinition(){}
	
	/**
	 * use when the attribute is continuous.
	 * Values will be made later.
	 * @param name attribute
	 * @param continuous attribute values
	 */
	public AttributeDefinition(String name, TYPE type) {
		this(name, null, type);
	}
		
	/**
	 * use when attribute is discrete.
	 * @param name attribute
	 * @param values attribute values
	 */
	public AttributeDefinition(String name, String[] values, TYPE type) {
		attributeValueMap.put(name, values);
		attributeIndex = attributeCount++;
		this.type = type;
	}
	
	public int getAttributeIndex() {
		return attributeIndex;
	}
	
	public TYPE attributeType() {
		return type;
	}
	
	public static enum TYPE{
		CONTINUOUS,
		DISCRETE		
	}
}