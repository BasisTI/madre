import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { Observable, Subscription } from 'rxjs/Rx';
import { SelectItem } from 'primeng/primeng';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { PageNotificationService } from '@basis/angular-components';
import { TipoResposta } from './tipo-resposta.model';
import { TipoRespostaService } from './tipo-resposta.service';

@Component({
  selector: 'jhi-tipo-resposta-form',
  templateUrl: './tipo-resposta-form.component.html'
})
export class TipoRespostaFormComponent implements OnInit, OnDestroy {
  tipoResposta: TipoResposta;
  isSaving: boolean;
  isEdit = false;
  private routeSub: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private breadcrumbService: BreadcrumbService,
    private pageNotificationService: PageNotificationService,
    private tipoRespostaService: TipoRespostaService,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.routeSub = this.route.params.subscribe(params => {
      let title = 'Cadastrar';
      this.tipoResposta = new TipoResposta();
      if (params['id']) {
        this.isEdit = true;
        this.tipoRespostaService.find(params['id']).subscribe(tipoResposta => this.tipoResposta = tipoResposta);
        title = 'Editar';
      }
      this.breadcrumbService.setItems([
        { label: 'Tipo Respostas', routerLink: '/tipoResposta' },
        { label: title }
      ]);
    });
  }

  save() {
    this.isSaving = true;
    if (this.tipoResposta.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoRespostaService.update(this.tipoResposta));
    } else {
      this.subscribeToSaveResponse(this.tipoRespostaService.create(this.tipoResposta));
    }
  }

  private subscribeToSaveResponse(result: Observable<TipoResposta>) {
    result.subscribe((res: TipoResposta) => {
      this.isSaving = false;
      this.router.navigate(['/tipoResposta']);
      this.addConfirmationMessage();
    }, (res: Response) => {
      this.isSaving = false;
      this.pageNotificationService.addErrorMessage('Registro já cadastrado');
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
