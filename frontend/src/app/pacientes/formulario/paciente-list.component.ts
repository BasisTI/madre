import { Component, ViewChild } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { DatatableParams } from "@nuvem/primeng-components";
import { ElasticQuery } from '@shared/elastic-query';
import { DatatableComponent, DatatableClickEvent, PageNotificationService } from '@nuvem/primeng-components';
import { AfterContentInit } from "@angular/core";

@Component({
    selector: 'paciente-list',
    templateUrl: './paciente-list.component.html',
})
export class PacienteListComponent implements AfterContentInit {



    elasticQuery: ElasticQuery = new ElasticQuery();
    
    @ViewChild(DatatableComponent) datatable: DatatableComponent;

    paciente: FormGroup = this.fb.group({
        nome: [],
        prontuario: []
    });

    datatableParams: DatatableParams = {
        rows: 10
    };

    constructor(private fb: FormBuilder) {
    }
    ngAfterContentInit(): void {
        throw new Error("Method not implemented.");
    }

    searchUrl:string = 'pacientes/api/_search/pacientes';

    rowsPerPageOptions: number[] = [5,10,20]

    pesquisar(){
        this.datatable.refresh(this.elasticQuery.query);
    }

    ngAfterViewInit(): void {
        this.pesquisar();
    }
    

}
