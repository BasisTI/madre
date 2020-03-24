import { BreadcrumbService } from '../../breadcrumb/breadcrumb.service';
import { Component, OnInit, OnDestroy } from '@angular/core';

@Component({
    selector: 'app-prescricao-medica-dieta',
    templateUrl: './prescricao-medica-dieta.component.html',
    styleUrls: ['./prescricao-medica-dieta.component.css']
})

export class PrescricaoMedicaDietaComponent implements OnInit, OnDestroy {

    constructor(private breadcrumbService: BreadcrumbService) { }


    ngOnInit() {
        this.breadcrumbService.setItems([
            { label: 'Prescrição Médica', routerLink: 'prescricao-medica'},
            { label: 'Dieta', }
        ]);
    }

    ngOnDestroy() {
        this.breadcrumbService.reset();
    }

}
