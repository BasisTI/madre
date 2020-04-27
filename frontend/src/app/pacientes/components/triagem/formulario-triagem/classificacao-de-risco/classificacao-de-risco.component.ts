import { ClassificacaoDeRiscoService } from './classificacao-de-risco.service';
import { BreadcrumbService } from '../../../../../breadcrumb/breadcrumb.service';
import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { SelectItem } from 'primeng/api';

@Component({
    selector: 'app-classificacao-de-risco',
    templateUrl: './classificacao-de-risco.component.html',
    styleUrls: ['./classificacao-de-risco.component.scss'],
})
export class ClassificacaoDeRiscoComponent implements OnInit {
    types: SelectItem[];
    selectedValue: String;
    ClassificacaoDeRiscoService: any;
    classificacaoDeRiscoService: any;
    handleClick() {}

    constructor(private breadcrumbService: BreadcrumbService, private fb: FormBuilder) {}
    ngOnInit() {
        this.types = [
            { label: 'red', value: 'Emergência' },
            { label: 'orange', value: 'Muito Urgente' },
            { label: 'yellow', value: 'Urgente' },
            { label: 'green', value: 'Pouco Urgente' },
            { label: 'blue', value: 'Não Urgente' },
        ];
    }
    cadastrar(form: FormBuilder) {
        console.log(form);

        this.classificacaoDeRiscoService.cadastrarRisco(this.types.values);
    }
}
