import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { JustificativaService } from './justificativa.service';
import { MotivoDoCadastroService } from './motivo-do-cadastro.service';
import { OPCOES_DE_DOCUMENTO_DE_REFERENCIA } from '../../../models/dropdowns/opcoes-de-documento-de-referencia';
import { OPCAO_SELECIONE } from '../../../models/dropdowns/opcao-selecione';

@Component({
    selector: 'app-cartao-sus',
    templateUrl: './cartao-sus.component.html',
})
export class CartaoSusComponent {
    @Input() cartaoSUS: FormGroup;
    opcoesDeDocumentoDeReferencia = OPCOES_DE_DOCUMENTO_DE_REFERENCIA;
    localizacao = CALENDAR_LOCALE;
    maxDate = new Date();
    yearRange = `1900:${this.maxDate.getFullYear()}`;
    formatoDeData = 'dd/mm/yy';

    constructor(
        private fb: FormBuilder,
        public justificativaService: JustificativaService,
        public motivoDoCadastroService: MotivoDoCadastroService,
    ) {}
}
