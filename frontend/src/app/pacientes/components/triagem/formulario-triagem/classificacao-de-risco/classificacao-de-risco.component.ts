import { CLASSIFICACAO_RISCO } from 'src/app/pacientes/models/radioButton/classificacao-risco';
import { BreadcrumbService } from '@nuvem/primeng-components';
import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { SelectItem } from 'primeng/api';

@Component({
    selector: 'app-classificacao-de-risco',
    templateUrl: './classificacao-de-risco.component.html',
    styleUrls: ['./classificacao-de-risco.component.scss'],
})
export class ClassificacaoDeRiscoComponent implements OnInit {
    @Input() formTriagem: FormGroup;
    opcaoClassificacao = CLASSIFICACAO_RISCO;
    types: SelectItem[];
    selectedValue: string;
    searchUrl = 'pacientes/api/triagens/paciente/{id}';
    triagens: any[];
    selectedRisk: any;
    risk: { name: string; type: string }[];

    handleClick() {}

    constructor(private breadcrumbService: BreadcrumbService, private fb: FormBuilder) {
        this.risk = [
            { name: 'Não urgente', type: 'blue' },
            { name: 'Pouco urgente', type: 'grenn' },
            { name: 'Urgente', type: 'yellow' },
            { name: 'Muito urgente', type: 'orange' },
            { name: 'Emergência', type: 'red' },
        ];
    }

    ngOnInit() {}
}
