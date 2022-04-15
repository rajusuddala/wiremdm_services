package com.aai.util;

import java.net.URLDecoder;

public class SpecialCharacterParser {
	
	public static String removeSpecialCharacters(String str) {
		str= str.replace('~', ' ');
		str= str.replace('`', ' ');
		str= str.replace('!', ' ');
		str= str.replace('@', ' ');
		str= str.replace('#', ' ');
		str= str.replace('$', ' ');
		str= str.replace('%', ' ');
		str= str.replace('^', ' ');
		str= str.replace('&', ' ');
		str= str.replace('*', ' ');
		str= str.replace('(', ' ');
		str= str.replace(')', ' ');
		str= str.replace('-', ' ');
		str= str.replace('_', ' ');
		str= str.replace('=', ' ');
		str= str.replace('+', ' ');
		str= str.replace('\\', ' ');
		str= str.replace('"', ' ');
		str= str.replace('\'', ' ');
		str= str.replace(';', ' ');
		str= str.replace(':', ' ');
		str= str.replace('?', ' ');
		str= str.replace('/', ' ');
		str= str.replace('.', ' ');
		str= str.replace('>', ' ');
		str= str.replace(',', ' ');
		str= str.replace('<', ' ');
		str = str.replace('|',' ');
		
		
		return str;
		
	}
	
	public static String replaceSpecialCharacterWithCharacter(String str,char specialCharacter,char replacementCharacter)
	{
		str = str.replace(specialCharacter,replacementCharacter);
		return str;
	}
	public static String decodeParameter(String requestparameter) 
	{
			
		try	{
		String decodedString = URLDecoder.decode(requestparameter, "UTF-8");
		return decodedString;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
		
	}

}
