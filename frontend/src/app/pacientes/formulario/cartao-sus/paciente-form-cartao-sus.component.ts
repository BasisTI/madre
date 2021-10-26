import { Component, Input, OnInit } from '@angular/core';
import { JustificativaService } from './justificativa.service';
import { MotivoDoCadastroService } from './motivo-do-cadastro.service';
import { FormGroup } from '@angular/forms';

import { OPCOES_DE_DOCUMENTO_DE_REFERENCIA } from '../../models/dropdowns/opcoes-de-documento-de-referencia';

@Component({
    selector: 'paciente-form-cartao-sus',
    templateUrl: './paciente-form-cartao-sus.component.html',
})
export class PacienteCartaoSusFormComponent implements OnInit {
    @Input()
    public formGroup: FormGroup;

    opcoesDeDocumentoDeReferencia = OPCOES_DE_DOCUMENTO_DE_REFERENCIA;

    constructor(
        public justificativaService: JustificativaService,
        public motivoDoCadastroService: MotivoDoCadastroService,
    ) {}

    ngOnInit(): void {
        if (this.formGroup.get('cartaoSUS').get('justificativaId').value != null) {
            this.justificativaService
                .find(this.formGroup.get('cartaoSUS').get('justificativaId').value)
                .subscribe((res) => {
                    this.formGroup.patchValue({ cartaoSUS: { justificativaId: res } });
                });
        }

        if (this.formGroup.get('cartaoSUS').get('motivoDoCadastroId').value != null) {
            this.motivoDoCadastroService
                .find(this.formGroup.get('cartaoSUS').get('motivoDoCadastroId').value)
                .subscribe((res) => {
                    this.formGroup.patchValue({ cartaoSUS: { motivoDoCadastroId: res } });
                });
        }
    }
}
