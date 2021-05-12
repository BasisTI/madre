import { Router } from '@angular/router';
import { DatatableComponent, DatatableClickEvent, PageNotificationService } from '@nuvem/primeng-components';
import { TriagemService } from './triagem.service';
import { Component, OnInit, OnDestroy, ViewChild, EventEmitter } from '@angular/core';
import { TriagemModel } from '../../models/triagem-model';
import { ConfirmationService } from 'primeng';
import { ElasticQuery } from '@shared/elastic-query';

@Component({
    selector: 'app-triagem',
    templateUrl: './triagem.component.html',
    styleUrls: ['triagem.scss'],
})

export class TriagemComponent implements OnInit{
    
    constructor(
        private triagemService: TriagemService, 
        private triagemRouter: Router, 
        private confirmationService: ConfirmationService,
        private pageNotificationService: PageNotificationService
        ){
    }

    triagens: TriagemModel[] = [];

    rowsPerPageOptions: number[] = [5,10,20]

    elasticQuery: ElasticQuery = new ElasticQuery();

    @ViewChild(DatatableComponent) datatable: DatatableComponent;

    searchUrl:string = 'pacientes/api/_search/triagens';

    ngOnInit(): void {
        this.pesquisar();
    }

    onClick(event: DatatableClickEvent) {
        switch (event.button) {
            case 'edit': {
                this.abrirEditar(event.selection);
                break;
            }
            case 'view': {
                this.abrirVisualizar(event.selection);
                break;
            }
            case 'delete': {
                this.confirmarDelete(event.selection);
                break;
            }
            default: {
                break;
            }
        }
    }

    pesquisar(){
        this.datatable.refresh(this.elasticQuery.query);
    }

    public limparPesquisa() {
        this.elasticQuery.reset();
        this.datatable.refresh(this.elasticQuery.reset)
    }


    recarregarDatatable(){
        
            this.datatable.refresh(this.elasticQuery.query);
        
    }

    abrirEditar(triagem: TriagemModel) {
        this.triagemRouter.navigate(['/triagem/formulario/edit/',triagem.id]);
    }

    abrirVisualizar(triagem: TriagemModel) {
        this.triagemRouter.navigate(['/triagem/formulario/view/',triagem.id]);
    }

    confirmarDelete(triagem: TriagemModel){
        this.confirmationService.confirm({
            message: 'Você tem certeza que deseja excluir o registro?',
            accept: () => {
                this.triagemService.deletarTriagem(triagem.id).subscribe(() => {
                    this.datatable.refresh(this.elasticQuery.query);                
                    this.pageNotificationService.addSuccessMessage('Registro excluído com sucesso!');   
                });            
            }
        })
    }
    
}
