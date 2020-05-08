import { CLASSIFICACAO_RISCO } from './../../../../models/radioButton/classificacao-de-risco';
import { BreadcrumbService } from '../../../../../breadcrumb/breadcrumb.service';
import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
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
    selectedValue: String;

    handleClick() {}

    constructor(private breadcrumbService: BreadcrumbService, private fb: FormBuilder) {}

    ngOnInit() {}
}
