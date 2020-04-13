import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';

@Component({
    selector: 'app-responsavel',
    templateUrl: './responsavel.component.html',
    styles: [
        `
            div {
                margin: 3px;
            }
        `,
    ],
})
export class ResponsavelComponent {
<<<<<<< HEAD
    @Input() responsavel: FormGroup;
=======
    responsavel: FormGroup = this.fb.group(
        {
            nomeDoResponsavel: ['', [this.customRequired]],
            grauDeParentesco: ['', [this.customRequired]],
            ddd: ['', [this.customRequired]],
            telefone: ['', [this.customRequired]],
            observacao: ['', [this.customRequired]],
        },
        { updateOn: 'blur', validators: this.validateGroup },
    );
>>>>>>> 81ac57c75e9a9f5ca2f456eb3fc383ef469fe000

    grausDeParentesco = [
        { label: 'Selecione', value: null },
        { label: 'Cônjuge', value: 'conjuge' },
        { label: 'Neto', value: 'neto' },
        { label: 'Neta', value: 'neta' },
        { label: 'Tio', value: 'tio' },
        { label: 'Tia', value: 'tia' },
        { label: 'Sobrinho', value: 'sobrinho' },
        { label: 'Sobrinha', value: 'sobrinha' },
        { label: 'Pai', value: 'pai' },
        { label: 'Mãe', value: 'mae' },
        { label: 'Irmão', value: 'irmao' },
        { label: 'Irmã', value: 'irma' },
        { label: 'Primo', value: 'primo' },
        { label: 'Prima', value: 'prima' },
        { label: 'Cliente', value: 'cliente' },
        { label: 'Filho', value: 'filho' },
        { label: 'Filha', value: 'filha' },
        { label: 'Amigo', value: 'amigo' },
        { label: 'Amiga', value: 'amiga' },
        { label: 'Responsável Legal', value: 'responsavelLegal' },
        { label: 'Desconhecido', value: 'desconhecido' },
        { label: 'Não Informado', value: 'naoInformado' },
    ];

<<<<<<< HEAD
=======
    customRequired(control: AbstractControl): { [key: string]: boolean } | null {
        if (
            control.parent &&
            (control.parent.get('nomeDoResponsavel').value ||
                control.parent.get('observacao').value) &&
            !control.value
        ) {
            return { required: true };
        }

        return null;
    }

    validateGroup(group: FormGroup): { [key: string]: boolean } | null {
        if (group.get('nomeDoResponsavel').value || group.get('observacao').value) {
            group.markAsDirty();
            return { required: true };
        }

        return null;
    }

>>>>>>> 81ac57c75e9a9f5ca2f456eb3fc383ef469fe000
    constructor(private fb: FormBuilder) {}
}
