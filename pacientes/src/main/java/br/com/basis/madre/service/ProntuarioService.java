package br.com.basis.madre.service;

import br.com.basis.madre.repository.PacienteRepository;
import org.springframework.stereotype.Service;


@Service
public class ProntuarioService {

    private final PacienteRepository pacienteRepository;

    public ProntuarioService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Long gerarProntuario(){
       Long prontuario=pacienteRepository.gerarProntuario();
       Integer dv = calculoDV(prontuario.toString());
       return Long.valueOf(prontuario.toString() + dv);
    }

    public int calculoDV(String num){
        int soma = 0;
        int resto = 0;
        int dv = 0;
        String[] numeros = getStrings(num);
        for(int i = numeros.length; i > 0 ; i--){
            if(numeros[i-1] != null){
                soma += Integer.valueOf(numeros[i-1]);
            }
        }
        resto = soma%11;
        dv = 11 - resto;
        return dv;
    }

    private String[] getStrings(String num) {
        String[] numeros = new String[num.length()+1];
        int multiplicador = 2;
        for (int i = num.length(); i > 0; i--) {
            if(multiplicador > 9){
                multiplicador = 2;
                numeros[i] = String.valueOf(Integer.valueOf(num.substring(i-1,i))*multiplicador);
                multiplicador++;
            }else{
                numeros[i] = String.valueOf(Integer.valueOf(num.substring(i-1,i))*multiplicador);
                multiplicador++;
            }
        }
        return numeros;
    }
}
