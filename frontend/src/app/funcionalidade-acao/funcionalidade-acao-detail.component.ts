import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { Funcionalidade_acao } from './funcionalidade-acao.model';
import { Funcionalidade_acaoService } from './funcionalidade-acao.service';

@Component({
  selector: 'jhi-funcionalidade-acao-detail',
  templateUrl: './funcionalidade-acao-detail.component.html'
})
export class Funcionalidade_acaoDetailComponent implements OnInit, OnDestroy {

  funcionalidade_acao: Funcionalidade_acao;
  private subscription: Subscription;

  constructor(
    private funcionalidade_acaoService: Funcionalidade_acaoService,
    private route: ActivatedRoute,
    private breadcrumbService: BreadcrumbService
  ) {}

  ngOnInit() {
    this.subscription = this.route.params.subscribe((params) => {
      this.load(params['id']);
    });
    this.breadcrumbService.setItems([
      { label: 'Funcionalidade Acaos', routerLink: '/funcionalidade_acao' },
      { label: 'Visualizar' }
    ]);
  }

  load(id) {
    this.funcionalidade_acaoService.find(id).subscribe((funcionalidade_acao) => {
      this.funcionalidade_acao = funcionalidade_acao;
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
    this.breadcrumbService.reset();
  }
}
