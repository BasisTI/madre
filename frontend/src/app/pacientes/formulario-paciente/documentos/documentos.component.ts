import { ptBR } from './../../../shared/calendar.pt-br.locale';
import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { OrgaoEmissorService } from '../../services/orgao-emissor.service';
import { UfService } from '../../services/uf.service';

@Component({
    selector: 'app-documentos',
    templateUrl: './documentos.component.html',
    styles: [
        `
            div {
                margin: 3px;
            }
        `,
    ],
})
export class DocumentosComponent {
    @Input() documentos: FormGroup;
    localizacao = ptBR;
    maxDate = new Date();
    yearRange = `1900:${this.maxDate.getFullYear()}`;
    validade = ptBR;
    yearValidade = '2010:2030';

    constructor(
        private fb: FormBuilder,
        public orgaoEmissorService: OrgaoEmissorService,
        public ufService: UfService,
    ) {}
}
