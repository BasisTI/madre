import { style } from '@angular/animations';
import { BreadcrumbService } from './../../../../breadcrumb/breadcrumb.service';
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
    selectedType: boolean;

    @Input() nivel: number;

    getCor() {
        if (this.nivel === 1) {
            return 'red';
        } else if (this.nivel === 2) {
            return 'orange';
        } else if (this.nivel === 3) {
            return 'yellow';
        } else if (this.nivel === 4) {
            return 'green';
        } else if (this.nivel === 5) {
            return 'blue';
        }
    }

    constructor(private breadcrumbService: BreadcrumbService, private fb: FormBuilder) {}
    ngOnInit() {
        this.types = [
            { label: 'red', value: '1' },
            { label: 'orange', value: '2' },
            { label: 'yellow', value: '3' },
            { label: 'green', value: '4' },
            { label: 'blue', value: '5' },
        ];
    }
}
