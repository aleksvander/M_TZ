package ru.novotelnovad.classes;

/**
 * Класс для прочих чекеров...
 * @author Новотельнов А.Д.
 * @version 1.0
 */
public final class OtherCheckers {
	
	public static boolean checkNullable(String value) {
		if (value == null || value == "")
			return true;
		return false;
	}
	
	public static boolean checkNullable(Integer value) {
		if (value == null || value == 0)
			return true;
		return false;
	}
}
