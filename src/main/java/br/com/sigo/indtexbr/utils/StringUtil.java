package br.com.sigo.indtexbr.utils;

public class StringUtil {

	private static Boolean isNullOrEmpty(final String string) {
		return string == null || string.isEmpty();
	}

	public static boolean isValid(final String string) {
		return !isNullOrEmpty(string);
	}

	public static String toUpper(final String string) {
		return isValid(string) ? string.trim().toUpperCase() : null;
	}

	public static String toLower(final String string) {
		return string.trim().toLowerCase();
	}

	public static String getMensagemCampoObrigatorio(String campo) {
		return new StringBuilder("O campo '").append(campo.trim()).append("' é de preenchimento obrigatório")
				.toString();
	}

}
