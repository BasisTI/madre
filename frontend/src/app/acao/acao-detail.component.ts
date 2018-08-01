import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { Acao } from './acao.model';
import { AcaoService } from './acao.service';

@Component({
  selector: 'jhi-acao-detail',
  templateUrl: './acao-detail.component.html'
})
export class AcaoDetailComponent implements OnInit, OnDestroy {

  acao: Acao;
  private subscription: Subscription;

  constructor(
    private acaoService: AcaoService,
    private route: ActivatedRoute,
    private breadcrumbService: BreadcrumbService
  ) {}

  ngOnInit() {
    this.subscription = this.route.params.subscribe((params) => {
      this.load(params['id']);
    });
    this.breadcrumbService.setItems([
      { label: 'Acaos', routerLink: '/acao' },
      { label: 'Visualizar' }
    ]);
  }

  load(id) {
    this.acaoService.find(id).subscribe((acao) => {
      this.acao = acao;
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
    this.breadcrumbService.reset();
  }
}
