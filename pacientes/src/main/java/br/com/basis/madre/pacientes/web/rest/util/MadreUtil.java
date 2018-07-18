package br.com.basis.madre.pacientes.web.rest.util;

import br.com.basis.madre.pacientes.domain.Paciente;
import br.com.basis.madre.pacientes.repository.PacienteRepository;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class MadreUtil {


    //utils para madre
    public static final String REPORT_LOGO_PATH = "/images/logoFunasa.png";

    private MadreUtil() {

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

    public static String removeCaracteresEmBranco(String str) {

        String removerStr = str;
        if (StringUtils.isNotEmpty(removerStr)) {
            removerStr = (removerStr.trim());
        }
        return removerStr;
    }


    public static boolean verificaProntuario(PacienteRepository pacienteRepository, Paciente paciente) {
        if ((pacienteRepository.findOneByProntuario(MadreUtil.removeCaracteresEmBranco(paciente.getProntuario())).isPresent())) {
            int pacienteResult = Integer.parseInt(paciente.getProntuario());
            pacienteResult = pacienteResult + 1;
            paciente.setProntuario(String.valueOf(pacienteResult));
            return true;
        } else {
            return false;
        }
    }




}

