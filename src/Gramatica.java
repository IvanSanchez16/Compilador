
public class Gramatica {
	
	private String[] class_declaration = {"<modifier>", "class", "{", "field_declaration", "statement","}"};
	private String[] field_declaration = {"variable_declaration", ";"};
	private String[] variable_declaration = {"<modifier>","type","variable_declarator / identifier"};
	private String[] variable_declarator = {"identifier", "=", "integer_literal / boolean_literal"};
	private String expression = "testing_expression";
	private String[] testing_expression = {"integer_literal / identifier","relational_operator","integer_literal / identifier"};
	private String statement  = "variable_declaration / if_statement / while_statement";
	private String[] while_statement =  {"while", "(", "expression", ")", "{", "statement", "}"};
	private String type  = "type_specifier";
	private String type_specifier =   "boolean / int";
	private String modifier =   "public / private";
	private String integer_literal = "1..9  / 0..9";
	private String boolean_literal = "true / false";
	private String identifier = "a..z / 1..9";          //(a-z)+ (1-9)+
	private String[] aritmetica_expression = {"identifier", "=", "integer_literal", "aritmetic_operator", "integer_literal", ";"};

	public String declaracionDeClase(String[] lineas,int num){
		String error="";
		return "";
	}

	public String modificador(String token,int numLin){
		String error="";
		if(!token.equals("public") && !token.equals("private")){
			error="Error en la línea "+numLin+" se espera un public ó private";
		}
		return error;
	}
}
