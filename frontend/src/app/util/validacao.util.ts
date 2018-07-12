export class ValidacaoUtil {


    //Validação Email
    public static validarEmail(email): boolean {
        let regexp = new RegExp('^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$');
        return regexp.test(email);
    }

    public static validarCNPJ(cnpj): boolean {
      
        cnpj = cnpj.replace(/[^\d]+/g,'');

        if(cnpj == '') return false;
            
        if (cnpj.length != 14)
            return false;

        // Elimina CNPJs invalidos conhecidos
        if (cnpj == "00000000000000" || 
            cnpj == "11111111111111" || 
            cnpj == "22222222222222" || 
            cnpj == "33333333333333" || 
            cnpj == "44444444444444" || 
            cnpj == "55555555555555" || 
            cnpj == "66666666666666" || 
            cnpj == "77777777777777" || 
            cnpj == "88888888888888" || 
            cnpj == "99999999999999")
            return false;
                
        // Valida DVs
        let tamanho = cnpj.length - 2;
        let numeros = cnpj.substring(0,tamanho);
        let digitos = cnpj.substring(tamanho);
        let soma = 0;
        let pos = tamanho - 7;
        for (let i = tamanho; i >= 1; i--) {
            soma += numeros.charAt(tamanho - i) * pos--;
            if (pos < 2)
                pos = 9;
        }
        let resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
        if (resultado != digitos.charAt(0))
            return false;
                
        tamanho = tamanho + 1;
        numeros = cnpj.substring(0,tamanho);
        soma = 0;
        pos = tamanho - 7;
        for (let i = tamanho; i >= 1; i--) {
            soma += numeros.charAt(tamanho - i) * pos--;
            if (pos < 2)
                pos = 9;
        }
        resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
        if (resultado != digitos.charAt(1))
                return false;
                
        return true;
    }


    //Valida CPF
    public static validaCpf(cpf: string): boolean {
        if (cpf == null) {
            return false;
        }
        if (cpf.length != 11) {
            return false;
        }
        if ((cpf == '00000000000') || (cpf == '11111111111') || (cpf == '22222222222')
            || (cpf == '33333333333') || (cpf == '44444444444') || (cpf == '55555555555')
            || (cpf == '66666666666') || (cpf == '77777777777') || (cpf == '88888888888') || (cpf == '99999999999')) {
            return false;
        }
        let numero = 0;
        let caracter = '';
        let numeros = '0123456789';
        let j = 10;
        let somatorio = 0;
        let resto = 0;
        let digito1 = 0;
        let digito2 = 0;
        let cpfAux = '';
        cpfAux = cpf.substring(0, 9);
        for (let i = 0; i < 9; i++) {
            caracter = cpfAux.charAt(i);
            if (numeros.search(caracter) == -1) {
                return false;
            }
            numero = Number(caracter);
            somatorio = somatorio + (numero * j);
            j--;
        }
        resto = somatorio % 11;
        digito1 = 11 - resto;
        if (digito1 > 9) {
            digito1 = 0;
        }
        j = 11;
        somatorio = 0;
        cpfAux = cpfAux + digito1;
        for (let i = 0; i < 10; i++) {
            caracter = cpfAux.charAt(i);
            numero = Number(caracter);
            somatorio = somatorio + (numero * j);
            j--;
        }
        resto = somatorio % 11;
        digito2 = 11 - resto;
        if (digito2 > 9) {
            digito2 = 0;
        }
        cpfAux = cpfAux + digito2;
        if (cpf != cpfAux) {
            return false;
        } else {
            return true;
        }
    }


    //Valida Telefone
    public static validaTelefone(tel){
            var exp = /[0-9]{2}[0-9]{8,9}/;
           
            if(exp.test(tel)){
                
               return true;
            }
                    
    }

    //Valida CEP
    public static validaCep(cep){
        var exp = /^[0-9]{5}[0-9]{3}$/
        if(exp.test(cep)){
           
            return true;     
        }
                     
        }



    
    // Validação CNS
public static  validaCNS(vlrCNS : string) {
    console.log("Validar CNS");
    console.log("Numero: "+vlrCNS);
    // Formulário que contem o campo CNS
    var soma : any ;
     var resto : any;
    var dv = new Number;
    var pis = new String;
    var resultado = new String;
    var tamCNS = vlrCNS.length;
    if ((tamCNS) != 15) {
        alert("Numero de CNS invalido");
        return false;
    }
    pis = vlrCNS.substring(0,11);
    soma = (((Number(pis.substring(0,1))) * 15) +   
            ((Number(pis.substring(1,2))) * 14) +
            ((Number(pis.substring(2,3))) * 13) +
            ((Number(pis.substring(3,4))) * 12) +
            ((Number(pis.substring(4,5))) * 11) +
            ((Number(pis.substring(5,6))) * 10) +
            ((Number(pis.substring(6,7))) * 9) +
            ((Number(pis.substring(7,8))) * 8) +
            ((Number(pis.substring(8,9))) * 7) +
            ((Number(pis.substring(9,10))) * 6) +
            ((Number(pis.substring(10,11))) * 5));
            console.log("oi");
            console.log("soma: "+soma);
     resto = soma % 11;
    console.log("resto: "+resto);
    dv = 11 - resto;
    console.log("dv: "+dv);
    if (dv == 11) {
        dv = 0;
    }
    if (dv == 10) {
        soma = (((Number(pis.substring(0,1))) * 15) +   
                ((Number(pis.substring(1,2))) * 14) +
                ((Number(pis.substring(2,3))) * 13) +
                ((Number(pis.substring(3,4))) * 12) +
                ((Number(pis.substring(4,5))) * 11) +
                ((Number(pis.substring(5,6))) * 10) +
                ((Number(pis.substring(6,7))) * 9) +
                ((Number(pis.substring(7,8))) * 8) +
                ((Number(pis.substring(8,9))) * 7) +
                ((Number(pis.substring(9,10))) * 6) +
                ((Number(pis.substring(10,11))) * 5) + 2);
        resto = soma % 11;
        dv = 11 - resto;
        resultado = pis + "001" + String(dv);
    } else { 
        resultado = pis + "000" + String(dv);
    }
    if (vlrCNS != resultado) {
      return false;
    } else {
       return true;
    }
}

 
}
