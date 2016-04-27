import data.Attribute;
import data.AttributeCollection;
import data.AttributeDefinition;
import data.AttributeDefinition.TYPE;
import data.TSFReader;
import helper.kFoldCrossValidation;


public class Main {

	public static void main(String[] args) {
		// take definitions and match with data.
		AttributeDefinition[] adc = {
				new AttributeDefinition("AGE", 		  	  TYPE.CONTINUOUS),
				new AttributeDefinition("WORK_CLASS", new String[]{
						"Private", "Self-emp-not-inc", "Self-emp-inc", "Federal-gov",
						"Local-gov", "State-gov", "Without-pay", "Never-worked"
						} ,TYPE.DISCRETE ),
				new AttributeDefinition("FNLWGT", 		  TYPE.CONTINUOUS ),
				new AttributeDefinition("EDUCATION", new String[]{
						"Bachelors", "Some-college", "11th", "HS-grad",
						"Prof-school", "Assoc-acdm", "Assoc-voc", "9th",
						"7th-8th", "12th", "Masters", "1st-4th", "10th",
						"Doctorate", "5th-6th", "Preschool"
						}, TYPE.DISCRETE ),
				new AttributeDefinition("EDUCATION_NUM",  TYPE.CONTINUOUS ),
				new AttributeDefinition("MARITAL_STATUS", new String[]{
						"Married-civ-spouse", "Divorced", "Never-married", "Separated",
						"Widowed", "Married-spouse-absent", "Married-AF-spouse"
						}, TYPE.DISCRETE ),
				new AttributeDefinition("OCCUPATION", 	  new String[]{
						"Tech-support", "Craft-repair", "Other-service", "Sales",
						"Exec-managerial", "Prof-specialty", "Handlers-cleaners",
						"Machine-op-inspct", "Adm-clerical", "Farming-fishing",
						"Transport-moving", "Priv-house-serv", "Protective-serv", "Armed-Forces"
				},TYPE.DISCRETE ),
				new AttributeDefinition("RELATIONSHIP",   new String[]{
						"Wife", "Own-child", "Husband", "Not-in-family", "Other-relative", "Unmarried"
				}, TYPE.DISCRETE ),
				new AttributeDefinition("RACE", 		  new String[]{
						"White", "Asian-Pac-Islander", "Amer-Indian-Eskimo", "Other", "Black"
				}, TYPE.DISCRETE   ),
				new AttributeDefinition("SEX", 			  new String[]{
						"Female", "Male"
				}, TYPE.DISCRETE ),
				new AttributeDefinition("CAPITAL_GAIN",   TYPE.CONTINUOUS ),
				new AttributeDefinition("CAPITAL_LOSS",   TYPE.CONTINUOUS ),
				new AttributeDefinition("HOURS_PER_WEEK", TYPE.CONTINUOUS ),
				new AttributeDefinition("COUNTRY", 		  new String[]{
						"United-States", "Cambodia", "England", "Puerto-Rico", "Canada", "Germany", "Outlying-US(Guam-USVI-etc)", "India", "Japan", "Greece", "South", "China", "Cuba", "Iran", "Honduras", "Philippines", "Italy", "Poland", "Jamaica", "Vietnam", "Mexico", "Portugal", "Ireland", "France", "Dominican-Republic", "Laos", "Ecuador", "Taiwan", "Haiti", "Columbia", "Hungary", "Guatemala", "Nicaragua", "Scotland", "Thailand", "Yugoslavia", "El-Salvador", "Trinadad&Tobago", "Peru", "Hong", "Holand-Netherlands"
				}, TYPE.DISCRETE ),
				new AttributeDefinition("TARGET", 		  TYPE.CONTINUOUS )
		};

		
//		AttributeDefinition[] adc = new AttributeDefinition[]{
//				new AttributeDefinition("OUTLOOK" , new String[]{"rainy","overcast", "sunny"}, TYPE.DISCRETE),
//				new AttributeDefinition("TEMP"    , new String[]{"hot","mild", "cool"}, 	   TYPE.DISCRETE),
//				new AttributeDefinition("HUMIDITY", new String[]{"high","normal"},  		   TYPE.DISCRETE),
//				new AttributeDefinition("WINDY"   , new String[]{"TRUE","FALSE"}, 			   TYPE.DISCRETE),
//				new AttributeDefinition("TARGET"  , new String[]{"yes","no"}, 			   	   TYPE.DISCRETE)
//		};

		// get attribute data
		Attribute[][] attributes = TSFReader.getData("data.tsv", adc);
		AttributeCollection attributeCollection = new AttributeCollection(attributes);
		
		// cross validate
		kFoldCrossValidation.crossValidate(attributeCollection, 5);

	}

}