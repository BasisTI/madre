import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { Observable, Subscription } from 'rxjs/Rx';
import { SelectItem } from 'primeng/primeng';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { PageNotificationService } from '@basis/angular-components';
import { Perfil_funcionalidade_acao } from './perfil-funcionalidade-acao.model';
import { Perfil_funcionalidade_acaoService } from './perfil-funcionalidade-acao.service';

@Component({
  selector: 'jhi-perfil-funcionalidade-acao-form',
  templateUrl: './perfil-funcionalidade-acao-form.component.html'
})
export class Perfil_funcionalidade_acaoFormComponent implements OnInit, OnDestroy {
  perfil_funcionalidade_acao: Perfil_funcionalidade_acao;
  isSaving: boolean;
  isEdit = false;
  private routeSub: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private breadcrumbService: BreadcrumbService,
    private pageNotificationService: PageNotificationService,
    private perfil_funcionalidade_acaoService: Perfil_funcionalidade_acaoService,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.routeSub = this.route.params.subscribe(params => {
      let title = 'Cadastrar';
      this.perfil_funcionalidade_acao = new Perfil_funcionalidade_acao();
      if (params['id']) {
        this.isEdit = true;
        this.perfil_funcionalidade_acaoService.find(params['id']).subscribe(perfil_funcionalidade_acao => this.perfil_funcionalidade_acao = perfil_funcionalidade_acao);
        title = 'Editar';
      }
      this.breadcrumbService.setItems([
        { label: 'Perfil Funcionalidade Acaos', routerLink: '/perfil_funcionalidade_acao' },
        { label: title }
      ]);
    });
  }

  save() {
    this.isSaving = true;
    if (this.perfil_funcionalidade_acao.id !== undefined) {
      this.subscribeToSaveResponse(this.perfil_funcionalidade_acaoService.update(this.perfil_funcionalidade_acao));
    } else {
      this.subscribeToSaveResponse(this.perfil_funcionalidade_acaoService.create(this.perfil_funcionalidade_acao));
    }
  }

  private subscribeToSaveResponse(result: Observable<Perfil_funcionalidade_acao>) {
    result.subscribe((res: Perfil_funcionalidade_acao) => {
      this.isSaving = false;
      this.router.navigate(['/perfil_funcionalidade_acao']);
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
