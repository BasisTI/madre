import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { Perfil_funcionalidade_acao } from './perfil-funcionalidade-acao.model';
import { Perfil_funcionalidade_acaoService } from './perfil-funcionalidade-acao.service';

@Component({
  selector: 'jhi-perfil-funcionalidade-acao-detail',
  templateUrl: './perfil-funcionalidade-acao-detail.component.html'
})
export class Perfil_funcionalidade_acaoDetailComponent implements OnInit, OnDestroy {

  perfil_funcionalidade_acao: Perfil_funcionalidade_acao;
  private subscription: Subscription;

  constructor(
    private perfil_funcionalidade_acaoService: Perfil_funcionalidade_acaoService,
    private route: ActivatedRoute,
    private breadcrumbService: BreadcrumbService
  ) {}

  ngOnInit() {
    this.subscription = this.route.params.subscribe((params) => {
      this.load(params['id']);
    });
    this.breadcrumbService.setItems([
      { label: 'Perfil Funcionalidade Acaos', routerLink: '/perfil_funcionalidade_acao' },
      { label: 'Visualizar' }
    ]);
  }

  load(id) {
    this.perfil_funcionalidade_acaoService.find(id).subscribe((perfil_funcionalidade_acao) => {
      this.perfil_funcionalidade_acao = perfil_funcionalidade_acao;
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
    this.breadcrumbService.reset();
  }
}
