package br.com.basis.madre.service;

import org.springframework.stereotype.Service;

import java.util.InputMismatchException;

@Service
public class ProntuarioService {

    public static void main(String[]args){
        ProntuarioService prontuarioService = new ProntuarioService();
        String a = "1201611227",str;
        System.out.println("Número informado: "+a);
        int result = prontuarioService.calculoDV(a);
        System.out.println("Dígito verificador: "+result);
        str = a +""+"" +result;
        System.out.println(str);
    }


    public int calculoDV(String num){

        //variáveis de instancia
        int soma = 0;
        int resto = 0;
        int dv = 0;
        String[] numeros = new String[num.length()+1];
        int multiplicador = 2;

        for (int i = num.length(); i > 0; i--) {
            //Multiplica da direita pra esquerda, incrementando o multiplicador de 2 a 9
            //Caso o multiplicador seja maior que 9 o mesmo recomeça em 2
            if(multiplicador > 9){
                // pega cada numero isoladamente
                multiplicador = 2;
                numeros[i] = String.valueOf(Integer.valueOf(num.substring(i-1,i))*multiplicador);
                multiplicador++;
            }else{
                numeros[i] = String.valueOf(Integer.valueOf(num.substring(i-1,i))*multiplicador);
                multiplicador++;
            }
        }

        //Realiza a soma de todos os elementos do array e calcula o digito verificador
        //na base 11 de acordo com a regra.
        for(int i = numeros.length; i > 0 ; i--){
            if(numeros[i-1] != null){
                soma += Integer.valueOf(numeros[i-1]);
            }
        }
        resto = soma%11;
        dv = 11 - resto;

        //retorna o digito verificador
        return dv;
    }

}
