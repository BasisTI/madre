import { Component, OnInit, OnDestroy, TRANSLATIONS_FORMAT } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { Observable, Subscription } from 'rxjs/Rx';
import { SelectItem, CheckboxModule, CHECKBOX_VALUE_ACCESSOR } from 'primeng/primeng';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { PageNotificationService } from '@basis/angular-components';
import { Perfil } from './perfil.model';
import { PerfilService } from './perfil.service';
import {PickListModule} from 'primeng/picklist';
import { AcaoService } from '../acao/acao.service';

@Component({
  selector: 'jhi-perfil-form',
  templateUrl: './perfil-form.component.html'
})
export class PerfilFormComponent implements OnInit, OnDestroy {
  pega: string[];
  coloca: string[];

  
  isSaving: boolean;
  isEdit = false;
  private routeSub: Subscription;
  perfil: Perfil;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private breadcrumbService: BreadcrumbService,
    private pageNotificationService: PageNotificationService,
    private perfilService: PerfilService,
    private acaoService: AcaoService,
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.routeSub = this.route.params.subscribe(params => {
      let title = 'Cadastrar';
      this.perfil = new Perfil();

      this.perfil.pesquisarUS = ["UnidadeSaude gg", "MAteus"];
      this.perfil.incluirUS = "Unidade de Saude - Incluir";
      this.perfil.alterarUS = "Unidade de Saude - Alterar";
      this.coloca = [];
      this.pega = [];
      this.populaDropdown();
      if (params['id']) {
        this.isEdit = true;
        this.perfilService.find(params['id']).subscribe(perfil => this.perfil = perfil);
        title = 'Editar';
      }
      this.breadcrumbService.setItems([
        { label: 'Perfils', routerLink: '/perfil' },
        { label: title }
      ]);
    });
  }

  save() {
    this.isSaving = true;
    if (this.perfil.id !== undefined) {
      this.subscribeToSaveResponse(this.perfilService.update(this.perfil));
    } else {
      this.subscribeToSaveResponse(this.perfilService.create(this.perfil));
    }
  }

  private subscribeToSaveResponse(result: Observable<Perfil>) {
    result.subscribe((res: Perfil) => {
      this.isSaving = false;
      this.router.navigate(['/perfil']);
      this.addConfirmationMessage();
    }, (res: Response) => {
      this.isSaving = false;
      this.pageNotificationService.addErrorMessage('Registro já cadastrado!')
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

  populaDropdown() {
    this.acaoService.getAllAcaos().subscribe( res => {
      res.forEach( item => {
        this.pega.push(item.cd_acao);
      });
    })
  }
}
