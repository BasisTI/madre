import { TriagemService } from './triagem.service';
import { Component, OnInit, OnDestroy, ViewChild, EventEmitter } from '@angular/core';
import { Table } from 'primeng';
import { TriagemModel } from '../../models/triagem-model';
import { BreadcrumbService } from '@nuvem/primeng-components';

@Component({
    selector: 'app-triagem',
    templateUrl: './triagem.component.html',
    styleUrls: ['triagem.scss']
})

export class TriagemComponent implements OnInit{
    
    constructor(private triagemService: TriagemService){
    }

    triagens: TriagemModel[] = [];

    @ViewChild(Table) pDatatableComponent: Table;

    searchUrl:string = 'pacientes/api/triagens/listagem';

    ngOnInit(): void {

        this.triagemService.listarTriagem().subscribe(res => {
            this.triagens = res.content;
        })
    }
    
}
