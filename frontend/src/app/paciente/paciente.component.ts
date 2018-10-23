import { Component, ViewChild, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { ConfirmationService } from 'primeng/primeng';
import { DatatableComponent, DatatableClickEvent, HttpService } from '@basis/angular-components';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { PageNotificationService } from '@basis/angular-components';
import { environment } from '../../environments/environment';
import { Paciente } from './paciente.model';
import { PacienteService } from './paciente.service';
import { ElasticQuery } from '../shared/elastic-query';
import { ajaxGetJSON } from 'rxjs/observable/dom/AjaxObservable';
import {ResponseWrapper} from "../shared";

@Component({
  selector: 'jhi-paciente',
  templateUrl: './paciente.component.html'
})
export class PacienteComponent implements OnInit, OnDestroy {

  @ViewChild(DatatableComponent) datatable: DatatableComponent;

  searchUrl: string = this.pacienteService.searchUrl;

  elasticQuery: ElasticQuery = new ElasticQuery();

  paciente: Paciente;

  total: number;

  valueFiltroCampo: string;

  constructor(
    private router: Router,
    private pacienteService: PacienteService,
    private confirmationService: ConfirmationService,
    private breadcrumbService: BreadcrumbService,
    private pageNotificationService: PageNotificationService,
    private http: HttpService
  ) { }

  ngOnInit() {


    this.breadcrumbService.setItems([{ label: 'Pacientes' }]);
    this.getSizePacientes();
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
        this.router.navigate(['/paciente', event.selection.id, 'edit']);
        break;
      case 'delete':
        this.confirmDelete(event.selection.id);
        break;
      case 'view':
        this.router.navigate(['/paciente', event.selection.id]);
        break;
    }
  }

  confirmDelete(id: any) {
    this.confirmationService.confirm({
      message: 'Tem certeza que deseja excluir o registro?',
      accept: () => {
        this.pacienteService.delete(id).subscribe(() => {
          this.datatable.refresh(undefined);
          this.pageNotificationService.addDeleteMsg();
        });
      }
    });
  }

    getSizePacientes(){
        this.pacienteService.getSizePacientes().subscribe((res:  Paciente) => {
          this.total = res.total;
        });
    }
  ngOnDestroy() {
    this.breadcrumbService.reset();
  }
}
