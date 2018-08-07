import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { Observable, Subscription } from 'rxjs/Rx';
import { SelectItem } from 'primeng/primeng';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { PageNotificationService } from '@basis/angular-components';
import { Funcionalidade_acao } from './funcionalidade-acao.model';
import { Funcionalidade_acaoService } from './funcionalidade-acao.service';

@Component({
  selector: 'jhi-funcionalidade-acao-form',
  templateUrl: './funcionalidade-acao-form.component.html'
})
export class Funcionalidade_acaoFormComponent implements OnInit, OnDestroy {
  funcionalidade_acao: Funcionalidade_acao;
  isSaving: boolean;
  isEdit = false;
  private routeSub: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private breadcrumbService: BreadcrumbService,
    private pageNotificationService: PageNotificationService,
    private funcionalidade_acaoService: Funcionalidade_acaoService,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.routeSub = this.route.params.subscribe(params => {
      let title = 'Cadastrar';
      this.funcionalidade_acao = new Funcionalidade_acao();
      if (params['id']) {
        this.isEdit = true;
        this.funcionalidade_acaoService.find(params['id']).subscribe(funcionalidade_acao => this.funcionalidade_acao = funcionalidade_acao);
        title = 'Editar';
      }
      this.breadcrumbService.setItems([
        { label: 'Funcionalidade Acaos', routerLink: '/funcionalidade_acao' },
        { label: title }
      ]);
    });
  }

  save() {
    this.isSaving = true;
    if (this.funcionalidade_acao.id !== undefined) {
      this.subscribeToSaveResponse(this.funcionalidade_acaoService.update(this.funcionalidade_acao));
    } else {
      this.subscribeToSaveResponse(this.funcionalidade_acaoService.create(this.funcionalidade_acao));
    }
  }

  private subscribeToSaveResponse(result: Observable<Funcionalidade_acao>) {
    result.subscribe((res: Funcionalidade_acao) => {
      this.isSaving = false;
      this.router.navigate(['/funcionalidade_acao']);
      this.addConfirmationMessage();
    }, (res: Response) => {
      this.isSaving = false;
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
