import { Component, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { DatatableParams } from "@nuvem/primeng-components";
import { ElasticQuery } from '@shared/elastic-query';
import { DatatableComponent, DatatableClickEvent, PageNotificationService } from '@nuvem/primeng-components';
import { AfterContentInit } from "@angular/core";
import { ConfirmationService } from "primeng/api";
import { ActivatedRoute, Router } from "@angular/router";
import { PacienteService } from "./paciente.service";

@Component({
    selector: 'paciente-list',
    templateUrl: './paciente-list.component.html',
})
export class PacienteListComponent {

    elasticQuery: ElasticQuery = new ElasticQuery();
    
    @ViewChild(DatatableComponent) datatable: DatatableComponent;

    paciente: FormGroup = this.fb.group({
        nome: [''],
        prontuario: []
    });

    constructor(
        private fb: FormBuilder,
        private confirmationService: ConfirmationService,
        private route: ActivatedRoute,
        private router: Router,
        private service: PacienteService
    ) { }

    searchUrl:string = 'pacientes/api/_search/pacientes';

    rowsPerPageOptions: number[] = [5,10,20]

    pesquisar(){
        this.datatable.refresh(this.elasticQuery.query);
    }

    ngAfterViewInit(): void {
        this.pesquisar();
    }
    
    datatableClick(event: DatatableClickEvent) {
        if (event.selection) {
            switch (event.button) {
                case "edit":
                case "view":
                    this.router.navigate([`../${event.button}`, event.selection.id], { relativeTo: this.route });
                    break;
                case "delete":
                    this.confirmationService.confirm({
                        message: 'Você tem certeza que deseja excluir o registro?',
                        accept: () => this.service.delete(event.selection.id)
                            .subscribe(() => this.pesquisar()),
                    });
                    break;
            }
        }
    }

}