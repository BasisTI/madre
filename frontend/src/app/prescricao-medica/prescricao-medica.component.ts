import { Router } from '@angular/router';
import { BreadcrumbService } from '@nuvem/primeng-components';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { PrescricaoMedicaService } from './prescricao-medica.service';
import { DatatableClickEvent } from '@nuvem/primeng-components';

@Component({
    selector: 'app-prescricao-medica',
    templateUrl: './prescricao-medica.component.html',
    styleUrls: ['./prescricao-medica.component.css']
})
export class PrescricaoMedicaComponent implements OnInit, OnDestroy {

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

        this.pesquisar;
        this.listar;
    }

    listar() {
        this.prescricaoMedicaService.listarPacientes();
    }

    pesquisar() {
        this.prescricaoMedicaService
            .pesquisaPaciente(this.nome)
            .subscribe((dados) => (this.pacientes = dados.content));
        console.log(this.nome);
    }

    btnClick(event: DatatableClickEvent) {
        console.log(event);

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
        }

    }

    ngOnDestroy() {
        this.breadcrumbService.reset();
    }


}
