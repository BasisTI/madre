import { CLASSIFICACAO_RISCO } from 'src/app/pacientes/models/radioButton/classificacao-risco';
import { BreadcrumbService } from '@nuvem/primeng-components';
import { Component, OnInit, Input, Output } from '@angular/core';
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
    stateOptions: any[];
    justifyOptions: any[];
    checked: string;

    formControlName: string;
    value: any;

    constructor(private breadcrumbService: BreadcrumbService, private fb: FormBuilder) {
        this.risk = [];
    }

    ngOnInit() { 
        this.formTriagem.patchValue({classificacaoDeRisco: CLASSIFICACAO_RISCO[2].value});

        // this.formTriagem.controls.classificacaoDeRisco.valueChanges.subscribe((value) => {
        //     console.log(value);
        // })
        
    }



    /* toggle = true;
    status = 'Enable';

    enableDisableRule(job) {
        this.toggle = !this.toggle;
        this.status = this.toggle ? 'Enable' : 'Disable';
    } */
}
