import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { Observable, Subscription } from 'rxjs/Rx';
import { SelectItem } from 'primeng/primeng';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { PageNotificationService } from '@basis/angular-components';
import { TipoPergunta } from './tipo-pergunta.model';
import { TipoPerguntaService } from './tipo-pergunta.service';
import { ValidacaoUtil } from '../util/validacao.util';

@Component({
  selector: 'jhi-tipo-pergunta-form',
  templateUrl: './tipo-pergunta-form.component.html'
})
export class TipoPerguntaFormComponent implements OnInit, OnDestroy {
  tipoPergunta: TipoPergunta;
  isSaving: boolean;
  isEdit = false;
  private routeSub: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private breadcrumbService: BreadcrumbService,
    private pageNotificationService: PageNotificationService,
    private tipoPerguntaService: TipoPerguntaService,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.routeSub = this.route.params.subscribe(params => {
      let title = 'Cadastrar';
      this.tipoPergunta = new TipoPergunta();
      if (params['id']) {
        this.isEdit = true;
        this.tipoPerguntaService.find(params['id']).subscribe(tipoPergunta => this.tipoPergunta = tipoPergunta);
        title = 'Editar';
      }
      this.breadcrumbService.setItems([
        { label: 'Tipo Perguntas', routerLink: '/tipoPergunta' },
        { label: title }
      ]);
    });
  }

  save() {

    //validar se o campo estiver vazio
    if(this.tipoPergunta.enunciadoPergunta == undefined || this.tipoPergunta.enunciadoPergunta == ""){
      return this.pageNotificationService.addErrorMessage('Dados inválidos');
    }else{
      console.log(this.tipoPergunta.enunciadoPergunta);
      this.isSaving = true;
      if (this.tipoPergunta.id !== undefined) {
        this.subscribeToSaveResponse(this.tipoPerguntaService.update(this.tipoPergunta));
      } else {
        this.subscribeToSaveResponse(this.tipoPerguntaService.create(this.tipoPergunta));
      }
    }
  
  }

  private subscribeToSaveResponse(result: Observable<TipoPergunta>) {
    result.subscribe((res: TipoPergunta) => {
      this.isSaving = false;
      this.router.navigate(['/tipoPergunta']);
      this.addConfirmationMessage();
    }, (res: Response) => {
      this.isSaving = false;
      this.pageNotificationService.addErrorMessage('Dados inválidos');
    });
  }

  private addConfirmationMessage() {
    if (this.isEdit) {
      this.pageNotificationService.addUpdateMsg();
    } else {
      this.pageNotificationService.addCreateMsg();
    }
  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
    this.breadcrumbService.reset();
  }
}
