package br.com.basis.madre.cadastros.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class MadreUtil {
    //utils para madre

    private MadreUtil(){

    }
    public static String getReportFooter() {
        StringBuilder footer = new StringBuilder();
        //TODO Informar o nome do Usuário Logado
        footer.append("Gerado por  <NomeUsuario>  em ");
        footer.append(localDateTimeEmString(LocalDateTime.now()));

        return footer.toString();
    }

    public static String transformaLocalDateTimeEmString(LocalDate date) {
        if (date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return date.format(formatter);
        }
        return null;
    }

    public static String localDateTimeEmString(LocalDateTime date) {
        if (date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            return date.format(formatter);
        }
        return null;
    }
}
