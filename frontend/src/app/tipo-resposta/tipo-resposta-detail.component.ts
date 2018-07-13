import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { TipoResposta } from './tipo-resposta.model';
import { TipoRespostaService } from './tipo-resposta.service';

@Component({
  selector: 'jhi-tipo-resposta-detail',
  templateUrl: './tipo-resposta-detail.component.html'
})
export class TipoRespostaDetailComponent implements OnInit, OnDestroy {

  tipoResposta: TipoResposta;
  private subscription: Subscription;

  constructor(
    private tipoRespostaService: TipoRespostaService,
    private route: ActivatedRoute,
    private breadcrumbService: BreadcrumbService
  ) {}

  ngOnInit() {
    this.subscription = this.route.params.subscribe((params) => {
      this.load(params['id']);
    });
    this.breadcrumbService.setItems([
      { label: 'Tipo Respostas', routerLink: '/tipoResposta' },
      { label: 'Visualizar' }
    ]);
  }

  load(id) {
    this.tipoRespostaService.find(id).subscribe((tipoResposta) => {
      this.tipoResposta = tipoResposta;
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
    this.breadcrumbService.reset();
  }
}
