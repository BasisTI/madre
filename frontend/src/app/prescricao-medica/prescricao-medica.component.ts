import { BreadcrumbService } from './../breadcrumb/breadcrumb.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { PrescricaoMedicaService } from './prescricao-medica.service';

@Component({
    selector: 'app-prescricao-medica',
    templateUrl: './prescricao-medica.component.html',
    styleUrls: ['./prescricao-medica.component.css']
})
export class PrescricaoMedicaComponent implements OnInit, OnDestroy {

    pacientes: [];

    searchUrl = 'prescricao/api/pacientes/listarPacientes';

    constructor(public prescricaoMedicaService: PrescricaoMedicaService, private breadcrumbService: BreadcrumbService) { }


    ngOnInit(
    ) {
        this.breadcrumbService.setItems([{ label: 'Prescrição Médica' }]);
        this.listar();
    }

    listar() {
        this.prescricaoMedicaService.listarPacientes();
    }
    ngOnDestroy() {
        this.breadcrumbService.reset();
    }

}
