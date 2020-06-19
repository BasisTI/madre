import { BreadcrumbService } from '@nuvem/primeng-components';
import { Component, OnInit, OnDestroy } from '@angular/core';

@Component({
    selector: 'app-lista-prescricao',
    templateUrl: './lista-prescricao.component.html',
    styleUrls: ['./lista-prescricao.component.css']
})
export class ListaPrescricaoComponent implements OnInit, OnDestroy {

    constructor(
        private breadcrumbService: BreadcrumbService
    ) { }

    ngOnInit(): void {
        this.breadcrumbService.setItems([
            { label: 'Prescrição Médica', routerLink: 'prescricao-medica' },
            { label: 'Prescrições' }
        ]);
    }

    ngOnDestroy(): void {
        this.breadcrumbService.reset();
    }

}
