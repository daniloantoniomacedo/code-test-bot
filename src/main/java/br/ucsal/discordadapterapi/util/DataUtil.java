package br.ucsal.discordadapterapi.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DataUtil {
	
	public DataUtil() {
		throw new UnsupportedOperationException("Classe utilitaria!");
	}
	
	public static final String DATA_HORA_BR_FORMAT = "dd/MM/yyyy HH:mm:ss";
	
	public static String formatarLocalDateTime(LocalDateTime dateTime, String format) {
		
		if(Objects.isNull(dateTime)) return "";
		
		DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern(format);
		return dateTime.format(formatoDataHora);
	}

}
