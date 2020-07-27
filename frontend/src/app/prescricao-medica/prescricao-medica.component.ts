import { Router } from '@angular/router';
import { BreadcrumbService, DatatableComponent, DatatableClickEvent } from '@nuvem/primeng-components';
import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { PrescricaoMedicaService } from './prescricao-medica.service';

@Component({
    selector: 'app-prescricao-medica',
    templateUrl: './prescricao-medica.component.html',
    styleUrls: ['./prescricao-medica.component.css']
})
export class PrescricaoMedicaComponent implements OnInit, OnDestroy {

    @ViewChild('datatable') datatable: DatatableComponent;
    pacienteSelecionado: any;

    pacientes: [];

    nome: string;

    searchUrl = 'prescricao/api/pacientes';

    constructor(

        public prescricaoMedicaService: PrescricaoMedicaService,
        private breadcrumbService: BreadcrumbService,
        private router: Router,

    ) { }


    ngOnInit(): void {
        this.breadcrumbService.setItems([{ label: 'Prescrição Médica' }]);

    }

    listar() {
        this.prescricaoMedicaService.listarPacientes();
    }

    pesquisar() {
        this.datatable.refresh({ nome: this.nome })
    }

    btnClick(event: DatatableClickEvent) {

        if (!event.selection) {
            return;
        }
        switch (event.button) {
            case 'prescrever-dieta':
                this.router.navigate(['/prescricao-medica/dieta', event.selection.id]);
                break;
            case 'prescrever-medicamento':
                this.router.navigate(['/prescricao-medica/medicamento', event.selection.id]);
                break;

            case 'prescrever-procedimento':
                this.router.navigate(['/prescricao-medica/procedimento-especial', event.selection.id]);
                break;

            case 'prescrever-diagnostico':
                this.router.navigate(['/prescricao-medica/diagnostico', event.selection.id]);
                break;
            case 'listar-prescricoes':
                this.router.navigate(['/prescricao-medica/lista', event.selection.id]);
                break;

        }

    }

    ngOnDestroy() {
        this.breadcrumbService.reset();
    }


}
