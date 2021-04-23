import { DatatableClickEvent, DatatableComponent } from '@nuvem/primeng-components';
import { Router } from '@angular/router';
import { TriagemService } from './triagem.service';
import { Component, OnInit, OnDestroy, ViewChild, EventEmitter } from '@angular/core';
import { BreadcrumbService } from '@nuvem/primeng-components';
import { Table } from 'primeng';
import { TriagemModel } from '../../models/triagem-model';

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
            this.triagens.forEach(obj => {
                switch(obj.classificacaoDeRisco){
                    case 'EMERGENCIA':{
                        obj.classificacaoDeRisco = 'A - EMERGENCIA';
                        break;
                    }
                    case 'MUITO_URGENTE':{
                        obj.classificacaoDeRisco = 'B - MUITO_URGENTE';
                        break;
                    }
                    case 'URGENTE':{
                        obj.classificacaoDeRisco = 'C - URGENTE';
                        break;
                    }
                    case 'POUCO_URGENTE':{
                        obj.classificacaoDeRisco = 'D - POUCO_URGENTE';
                        break;
                    }
                    case 'NAO_URGENTE':{
                        obj.classificacaoDeRisco = 'E - NAO_URGENTE';
                        break;
                    }
                }
            })
            this.triagens.sort((a, b) => {
                if(a.classificacaoDeRisco == b.classificacaoDeRisco){
                    if(a.dataHoraDoAtendimento < b.dataHoraDoAtendimento){
                        return -1;
                    }
                    else if(a.dataHoraDoAtendimento > b.dataHoraDoAtendimento){
                        return 1;
                    }
                    else{
                        return 0;
                    }
                }
                else if(a.classificacaoDeRisco < b.classificacaoDeRisco){
                    return -1;
                }
                else{
                    return 1;
                }
            })

            console.log(this.triagens);
        })

    }

}
