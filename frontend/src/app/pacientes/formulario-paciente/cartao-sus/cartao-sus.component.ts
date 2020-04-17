import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ptBR } from '../../../shared/calendar.pt-br.locale';
import { JustificativaService } from '../../services/justificativa.service';
import { MotivoDoCadastroService } from '../../services/motivo-do-cadastro.service';
import { OPCOES_DE_DOCUMENTO_DE_REFERENCIA } from '../../models/dropdowns/opcoes-de-documento-de-referencia';
import { OPCAO_SELECIONE } from '../../models/dropdowns/opcao-selecione';

@Component({
    selector: 'app-cartao-sus',
    templateUrl: './cartao-sus.component.html',
})
export class CartaoSusComponent {
    @Input() cartaoSUS: FormGroup;
    opcoesDeDocumentoDeReferencia = OPCOES_DE_DOCUMENTO_DE_REFERENCIA;
    localizacao = ptBR;
    maxDate = new Date();
    yearRange = `1900:${this.maxDate.getFullYear()}`;

    constructor(
        private fb: FormBuilder,
        private justificativaService: JustificativaService,
        private motivoDoCadastroService: MotivoDoCadastroService,
    ) {}
}
