import data.AttributeDefinition;
import data.AttributeDefinition.TYPE;

public class Main {

	public static void main(String[] args) {
		
		AttributeDefinition[] ac = {
				new AttributeDefinition("AGE", TYPE.CONTINUOUS),
				new AttributeDefinition("WORK_CLASS", TYPE.DISCRETE ),
				new AttributeDefinition("FNLWGT", TYPE.CONTINUOUS ),
				new AttributeDefinition("EDUCATION", TYPE.CONTINUOUS ),
				new AttributeDefinition("EDUCATION_NUM", TYPE.CONTINUOUS ),
				new AttributeDefinition("MARITAL_STATUS", TYPE.CONTINUOUS ),
				new AttributeDefinition("OCCUPATION", TYPE.CONTINUOUS ),
				new AttributeDefinition("RELATIONSHIP", TYPE.CONTINUOUS ),
				new AttributeDefinition("RACE", TYPE.CONTINUOUS ),
				new AttributeDefinition("SEX", TYPE.CONTINUOUS ),
				new AttributeDefinition("CAPITAL_GAIN", TYPE.CONTINUOUS ),
				new AttributeDefinition("CAPITAL_LOSS", TYPE.CONTINUOUS ),
				new AttributeDefinition("HOURS_PER_WEEK", TYPE.CONTINUOUS ),
				new AttributeDefinition("COUNTRY", TYPE.CONTINUOUS ),
				new AttributeDefinition("TARGET", TYPE.CONTINUOUS )				
		};
		
//		Attribute[] attributeNames = new Attribute[]{
//                new Attribute("AGE", true, true),
//                new Attribute("WORK_CLASS", false, false),
//                new Attribute("FNLWGT", true, false), //use gainratio?
//                new Attribute("EDUCATION", false, false),
//                new Attribute("EDUCATION_NUM", true, false),
//                new Attribute("MARITAL_STATUS", false, false),
//                new Attribute("OCCUPATION", false, false),
//                new Attribute("RELATIONSHIP", false, false),
//                new Attribute("RACE", false, false),
//                new Attribute("SEX", false, false),
//                new Attribute("CAPITAL_GAIN", true, false),
//                new Attribute("CAPITAL_LOSS", true, false),
//                new Attribute("HOURS_PER_WEEK", true, false),
//                new Attribute("COUNTRY", false, false),
//                new Attribute("TARGET", false, false),
//        };
		
		

	}

}