import { Component, ViewChild, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { ConfirmationService } from 'primeng/primeng';
import { DatatableComponent, DatatableClickEvent } from '@basis/angular-components';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { PageNotificationService } from '@basis/angular-components';
import { environment } from '../../environments/environment';
import { Especialidade } from './especialidade.model';
import { EspecialidadeService } from './especialidade.service';

import { ElasticQuery } from '../shared/elastic-query';

@Component({
  selector: 'jhi-especialidade',
  templateUrl: './especialidade.component.html'
})
export class EspecialidadeComponent implements OnInit, OnDestroy {

  @ViewChild(DatatableComponent) datatable: DatatableComponent;

  searchUrl: string = this.especialidadeService.searchUrl;

  elasticQuery: ElasticQuery = new ElasticQuery();

  constructor(
    private router: Router,
    private especialidadeService: EspecialidadeService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private pageNotificationService: PageNotificationService
  ) {}

  ngOnInit() {
    this.breadcrumbService.setItems([{ label: 'Especialidades' }]);
  }

  datatableClick(event: DatatableClickEvent) {
    if (!event.selection) {
      return;
    }
    switch (event.button) {
      case 'edit':
        this.router.navigate(['/especialidade', event.selection.id, 'edit']);
        break;
      case 'delete':
        this.confirmDelete(event.selection.id);
        break;
      case 'view':
        this.router.navigate(['/especialidade', event.selection.id]);
        break;
    }
  }

  confirmDelete(id: any) {
    this.confirmationService.confirm({
      message: 'Tem certeza que deseja excluir o registro?',
      accept: () => {
        this.especialidadeService.delete(id).subscribe(() => {
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
