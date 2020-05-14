import { CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { OrgaoEmissorService } from './orgao-emissor.service';
import { UfService } from './uf.service';

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
    localizacao = CALENDAR_LOCALE;
    maxDate = new Date();
    yearRange = `1900:${this.maxDate.getFullYear()}`;
    yearValidade = '2010:2030';

    constructor(
        private fb: FormBuilder,
        public orgaoEmissorService: OrgaoEmissorService,
        public ufService: UfService,
    ) {}
}
