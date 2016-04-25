package data;

import java.util.HashMap;

/**
 * This class is used to define an Attribute and its values.
 * @author v-equispe
 *
 */
public class AttributeDefinition {

	public static HashMap<String, String[]> attributeValueMap = new HashMap<>();
	public static HashMap<String, Integer> attributeIndexMap = new HashMap<>();
	public static HashMap<Integer, String> attributeNameMap = new HashMap<>();

	private String name;
	private String[] values;
	private TYPE type;
	private int attributeIndex = -1;

	public static int attributeCount = 0;

	private AttributeDefinition(){}
	
	/**
	 * use when the attribute is continuous.
	 * Values will be made later.
	 * @param name attribute
	 * @param type attribute values
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
		this.name = name;
		this.type = type;
		this.values = values;

		attributeIndex = attributeCount++;

		attributeValueMap.put(name, values);
		attributeIndexMap.put(name, attributeIndex);
		attributeNameMap.put(attributeIndex, name);
	}

	public String getAttributeName(){
		return name;
	}

	public int getAttributeIndex() {
		return attributeIndex;
	}

	public TYPE attributeType() {
		return type;
	}

	public String[] getValues() {
		return values;
	}

	public static Integer getAttributeIndexFromName(String name){
		return attributeIndexMap.get(name);
	}

	public static String getAttributeNameFromIndex(Integer index){
		return attributeNameMap.get(index);
	}

	public static String[] getAttributeValues(String key) {
		return attributeValueMap.get(key);
	}

	public enum TYPE{
		CONTINUOUS,
		DISCRETE		
	}
}