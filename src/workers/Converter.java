package workers;

public class Converter {
	
	public static String converToCamelCase (String text) {
		if(!text.contains(" ")) {
			return text;
		}
		boolean shouldConvertNext = false;
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < text.length(); i++) {
		    char currentChar = text.charAt(i);
		    if (currentChar == ' ') {
		        shouldConvertNext = false;
		    } else if (shouldConvertNext == true) {
		        builder.append(Character.toLowerCase(currentChar));
		    } else {
		        builder.append(Character.toUpperCase(currentChar));
		        shouldConvertNext = true;
		    }
		}
		
		return builder.toString();
	}
}
