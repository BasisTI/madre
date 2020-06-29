import { AbstractControl } from '@angular/forms';

export class CpfCnpjValidator {
    static pesoCPF = [11, 10, 9, 8, 7, 6, 5, 4, 3, 2];
    static pesoCNPJ = [6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2];

    static valid(control: AbstractControl): { [key: string]: boolean } | null {
        const value = control.value as string;

        if (value.length === 11) {
            if (CpfCnpjValidator.validateCpf(value)) {
                return null;
            }
        } else if (value.length === 14) {
            if (CpfCnpjValidator.validateCnpj(value)) {
                return null;
            }
        }

        return { cpfCnpj: true };
    }

    static validateCpf(cpf: string): boolean {
        cpf = cpf.trim().replace('.', '').replace('-', '');
        if (cpf == null || cpf.length != 11) return false;

        for (let j = 0; j < 10; j++)
            if (CpfCnpjValidator.padLeft(String(j), CpfCnpjValidator.forDigit(j, 10)) === cpf)
                return false;

        const digito1 = CpfCnpjValidator.calcularDigito(
            cpf.substring(0, 9),
            CpfCnpjValidator.pesoCPF,
        );
        const digito2 = CpfCnpjValidator.calcularDigito(
            cpf.substring(0, 9) + digito1,
            CpfCnpjValidator.pesoCPF,
        );
        return cpf === cpf.substring(0, 9) + digito1.toString() + digito2.toString();
    }

    static validateCnpj(cnpj: string): boolean {
        cnpj = cnpj.trim().replace('.', '').replace('-', '');
        if (cnpj == null || cnpj.length != 14) return false;

        const digito1 = CpfCnpjValidator.calcularDigito(
            cnpj.substring(0, 12),
            CpfCnpjValidator.pesoCNPJ,
        );
        const digito2 = CpfCnpjValidator.calcularDigito(
            cnpj.substring(0, 12) + digito1,
            CpfCnpjValidator.pesoCNPJ,
        );
        return cnpj === cnpj.substring(0, 12) + digito1.toString() + digito2.toString();
    }

    private static calcularDigito(str: string, peso: number[]): number {
        let soma = 0;

        for (let i = str.length - 1, digito; i >= 0; i--) {
            digito = Number(str.substring(i, i + 1));
            soma += digito * peso[peso.length - str.length + i];
        }

        soma = 11 - (soma % 11);
        return soma > 9 ? 0 : soma;
    }

    private static padLeft(text: string, character: string) {
        let output = '';

        for (let i = 0; i < 11; i++) {
            output += text;
        }

        const formatted = text.replace(' ', character);
        console.log(formatted);

        return formatted;
    }

    private static forDigit(digit: number, radix: number): string {
        if (digit < radix && digit >= 0) {
            if (radix >= 2 && radix <= 36) {
                return digit < 10 ? String(48 + digit) : String(87 + digit);
            } else {
                return '\u0000';
            }
        } else {
            return '\u0000';
        }
    }
}
