import { Component, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ElasticQuery } from '@shared/elastic-query';
import { DatatableComponent, DatatableClickEvent } from '@nuvem/primeng-components';
import { ConfirmationService } from 'primeng/api';
import { PacienteService } from './paciente.service';
import { Router } from '@angular/router';

@Component({
    selector: 'paciente-list',
    templateUrl: './paciente-list.component.html',
})
export class PacienteListComponent {
    
    elasticQuery: ElasticQuery = new ElasticQuery();

    view: boolean = false;

    pacienteView: any;
   
    @ViewChild(DatatableComponent) datatable: DatatableComponent;

    paciente: FormGroup = this.fb.group({
        nome: [''],
        prontuario: [],
    });

    constructor(
        private fb: FormBuilder,
        private confirmationService: ConfirmationService,
        private service: PacienteService,
        private router: Router
    ) {}

    searchUrl: string = 'pacientes/api/_search/pacientes';

    rowsPerPageOptions: number[] = [5, 10, 20];

    pesquisar() {
        this.datatable.refresh(this.elasticQuery.query);
    }

    ngAfterViewInit(): void {
        this.pesquisar();
    }

    datatableClick(event: DatatableClickEvent) {
        if (event.selection) {
            this.pacienteView = event.selection;
            console.log(this.pacienteView)


            switch (event.button) {
                case 'edit':
                    this.router.navigate(['/paciente/edit/',event.selection.id]);
                    break;
                case 'view':
                    this.view = true;
                    break;
                case 'delete':
                    this.confirmationService.confirm({
                        message: 'Você tem certeza que deseja excluir o registro?',
                        accept: () =>
                            this.service
                                .delete(event.selection.id)
                                .subscribe(() => this.pesquisar()),
                    });
                    break;
            }
        }
    }
}
