
import { FormGroup, ValidationErrors, AbstractControl, ValidatorFn } from "@angular/forms";

export class PacienteValidators {

    static validarNumero(control: AbstractControl) : ValidationErrors | null {
        let cns = control.value;


        cns = cns?.replace(/\D/g, '');

        if (cns?.length !== 15) {
            return { customCns: true };
        }

        const soma =
            cns
                .split('')
                .reduce(
                    (somaParcial: number, atual: string, posicao: number) =>
                        somaParcial + parseInt(atual, 10) * (15 - posicao),
                    0,
                ) % 11;

        return soma % 11 === 0 ? null : { customCns: true };
    }

    static nomeDoResponsavelAndObservacaoRequired(control: AbstractControl): ValidationErrors | null {
        if (
            control.parent &&
            (control.parent.get('nomeDoResponsavel').value ||
                control.parent.get('observacao').value) &&
            !control.value
        ) {
            return { 'required': true };
        }

        return null;
    }

    static validateResponsavel(group: FormGroup): ValidationErrors | null {
        if (group.get('nomeDoResponsavel').value || group.get('observacao').value) {
            group.markAsDirty();
            return { required: true };
        }
        return null;
    }

    static numeroDaIdentidadeRequired(control: AbstractControl): ValidationErrors | null {
        if (control.parent && control.parent.get('numeroDaIdentidade').value && !control.value) {
            return { required: true };
        } else {
            return null;
        }
    }

    static cnhRequired(control: AbstractControl): ValidationErrors | null {
        if (control.parent && control.parent.get('cnh').value && !control.value) {
            return { required: true };
        }

        return null;
    }

    static validateDocumento(group: FormGroup): ValidationErrors | null {
        if (group.get('numeroDaIdentidade').value) {
            group.markAsDirty();
            return { required: true };
        } else {
            return null;
        }
    }

    static validateDocumentoCNH(group: FormGroup): ValidationErrors | null {
        if (group.get('cnh').value) {
            group.markAsDirty();
            return { required: true };
        }
        return null;
    }

}
