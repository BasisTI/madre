import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { TipoPergunta } from './tipo-pergunta.model';
import { TipoPerguntaService } from './tipo-pergunta.service';

@Component({
  selector: 'jhi-tipo-pergunta-detail',
  templateUrl: './tipo-pergunta-detail.component.html'
})
export class TipoPerguntaDetailComponent implements OnInit, OnDestroy {

  tipoPergunta: TipoPergunta;
  private subscription: Subscription;

  constructor(
    private tipoPerguntaService: TipoPerguntaService,
    private route: ActivatedRoute,
    private breadcrumbService: BreadcrumbService
  ) {}

  ngOnInit() {
    this.subscription = this.route.params.subscribe((params) => {
      this.load(params['id']);
    });
    this.breadcrumbService.setItems([
      { label: 'Tipo Perguntas', routerLink: '/tipoPergunta' },
      { label: 'Visualizar' }
    ]);
  }

  load(id) {
    this.tipoPerguntaService.find(id).subscribe((tipoPergunta) => {
      this.tipoPergunta = tipoPergunta;
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
    this.breadcrumbService.reset();
  }
}
