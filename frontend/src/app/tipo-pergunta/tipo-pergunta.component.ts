import { Component, ViewChild, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { ConfirmationService } from 'primeng/primeng';
import { DatatableComponent, DatatableClickEvent } from '@basis/angular-components';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { PageNotificationService } from '@basis/angular-components';
import { environment } from '../../environments/environment';
import { TipoPergunta } from './tipo-pergunta.model';
import { TipoPerguntaService } from './tipo-pergunta.service';
import { ElasticQuery } from '../shared/elastic-query';

@Component({
  selector: 'jhi-tipo-pergunta',
  templateUrl: './tipo-pergunta.component.html'
})
export class TipoPerguntaComponent implements OnInit, OnDestroy {

  @ViewChild(DatatableComponent) datatable: DatatableComponent;

  searchUrl: string = this.tipoPerguntaService.searchUrl;

  elasticQuery: ElasticQuery = new ElasticQuery();


  valueFiltroCampo: string;

  constructor(
    private router: Router,
    private tipoPerguntaService: TipoPerguntaService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private pageNotificationService: PageNotificationService
  ) {}

  ngOnInit() {
    this.breadcrumbService.setItems([{ label: 'Tipo Perguntas' }]);
  }


  valueFiltro(valuefiltro: string) {
    this.valueFiltroCampo = valuefiltro;
    this.datatable.refresh(valuefiltro);
  }


  datatableClick(event: DatatableClickEvent) {
    if (!event.selection) {
      return;
    }
    switch (event.button) {
      case 'edit':
        this.router.navigate(['/tipoPergunta', event.selection.id, 'edit']);
        break;
      case 'delete':
        this.confirmDelete(event.selection.id);
        break;
      case 'view':
        this.router.navigate(['/tipoPergunta', event.selection.id]);
        break;
    }
  }

  confirmDelete(id: any) {
    this.confirmationService.confirm({
      message: 'Tem certeza que deseja excluir o registro?',
      accept: () => {
        this.tipoPerguntaService.delete(id).subscribe(() => {
          this.datatable.refresh(undefined);
          this.pageNotificationService.addDeleteMsg();
        });
      }
    });
  }

  ngOnDestroy() {
    this.breadcrumbService.reset();
  }
}
