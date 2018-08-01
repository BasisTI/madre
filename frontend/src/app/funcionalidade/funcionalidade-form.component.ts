import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { Observable, Subscription } from 'rxjs/Rx';
import { SelectItem } from 'primeng/primeng';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { PageNotificationService } from '@basis/angular-components';
import { Funcionalidade } from './funcionalidade.model';
import { FuncionalidadeService } from './funcionalidade.service';

@Component({
  selector: 'jhi-funcionalidade-form',
  templateUrl: './funcionalidade-form.component.html'
})
export class FuncionalidadeFormComponent implements OnInit, OnDestroy {
  funcionalidade: Funcionalidade;
  isSaving: boolean;
  isEdit = false;
  private routeSub: Subscription;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private breadcrumbService: BreadcrumbService,
    private pageNotificationService: PageNotificationService,
    private funcionalidadeService: FuncionalidadeService,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.routeSub = this.route.params.subscribe(params => {
      let title = 'Cadastrar';
      this.funcionalidade = new Funcionalidade();
      if (params['id']) {
        this.isEdit = true;
        this.funcionalidadeService.find(params['id']).subscribe(funcionalidade => this.funcionalidade = funcionalidade);
        title = 'Editar';
      }
      this.breadcrumbService.setItems([
        { label: 'Funcionalidades', routerLink: '/funcionalidade' },
        { label: title }
      ]);
    });
  }

  save() {
    this.isSaving = true;
    if (this.funcionalidade.id !== undefined) {
      this.subscribeToSaveResponse(this.funcionalidadeService.update(this.funcionalidade));
    } else {
      this.subscribeToSaveResponse(this.funcionalidadeService.create(this.funcionalidade));
    }
  }

  private subscribeToSaveResponse(result: Observable<Funcionalidade>) {
    result.subscribe((res: Funcionalidade) => {
      this.isSaving = false;
      this.router.navigate(['/funcionalidade']);
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
