import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { BreadcrumbService } from '@nuvem/primeng-components';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'app-emergencia',
    templateUrl: './emergencia.component.html',
    styleUrls: ['./emergencia.component.css'],
})
export class EmergenciaComponent implements OnInit {
    constructor(private fb: FormBuilder, private breadcrumbService: BreadcrumbService) {}

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            {
                label: 'Consultas',
                routerLink: 'consulta',
            },
            {
                label: 'Emergência',
                routerLink: 'emergencia',
            },
        ]);
    }
}
