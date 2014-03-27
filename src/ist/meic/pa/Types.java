package ist.meic.pa;


/**
 *Enum for type checking. Contains the primitive name, 
 *the class name, the class object, the regex codification for matching 
 *and a string used for generate the class using forName
 */




public enum Types {
	/*		name		WrapperName		method			class				regexDetection		forName*/
    INTEGER("int",		"Integer",		"parseInt",		Integer.class, 		"[0-9]+", 			"Integer"), 
    BOOLEAN("boolean",	"Boolean",		"parseBoolean",	Boolean.class, 		"true|false", 		"Boolean"),
    DOUBLE("double",	"Double",		"parseDouble",	Double.class, 		"[0-9]+\\.[0-9]+",  "Double"),
    SHORT("short",		"Short",		"parseShort",	Short.class,		"[0-9]+",			"Short"),
    BYTE("byte",		"Byte",			"parseByte", 	Byte.class,			"[0-9]+", 			"Byte"),
    LONG("long",		"Long",			"parseLong", 	TypeValidator.class,"[0-9]+L", 			"Long"),
    FLOAT("float",		"Float",		"parseFloat", 	Float.class,		"[0-9]+f",			"Float"),
    STRING("String",	"notDefined",	"parseString",	TypeValidator.class,"\"(.+?)*\"",		"String"),
    CHAR("char", 		"Character", 	"parseChar", 	TypeValidator.class,"\'.\'", 			"Character");

    protected String name;
    protected String WrapperName;
    protected String method;
    protected Class<?> clss;
    protected String regexDetection;
    protected String forName;
   
    
    
    private Types(String name, String WrapperName, String method, Class<?> cls, String regex, String forName) {
            this.name = name;
            this.WrapperName=WrapperName;
            this.method= method;
            this.clss = cls;
            this.regexDetection = regex;
            this.forName = forName;
    }
};
