import { Component, OnInit, OnDestroy, TRANSLATIONS_FORMAT } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Response } from '@angular/http';
import { Observable, Subscription } from 'rxjs/Rx';
import { SelectItem, CheckboxModule, CHECKBOX_VALUE_ACCESSOR } from 'primeng/primeng';

import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { PageNotificationService } from '@basis/angular-components';
import { Perfil } from './perfil.model';
import { PerfilService } from './perfil.service';
import { PickListModule } from 'primeng/picklist';
import { AcaoService } from '../acao/acao.service';
import { FuncionalidadeService, Funcionalidade } from '../funcionalidade';
import { element } from 'protractor';
import { Acao } from '../acao';
import { AcaoTemp } from '../acao/acao-temp.model';

@Component({
  selector: 'jhi-perfil-form',
  templateUrl: './perfil-form.component.html'
})
export class PerfilFormComponent implements OnInit, OnDestroy {
  pega: AcaoTemp[];
  coloca: AcaoTemp[];

  // listaAcao: string[];
  listaFunc: Funcionalidade[];

  isSaving: boolean;
  isEdit = false;
  private routeSub: Subscription;
  perfil: Perfil;
  acaoTemp: AcaoTemp;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private breadcrumbService: BreadcrumbService,
    private pageNotificationService: PageNotificationService,
    private perfilService: PerfilService,
    private acaoService: AcaoService,
    private funcionalidadeservice: FuncionalidadeService,
  ) { }

  ngOnInit() {
    this.isSaving = false;
    this.routeSub = this.route.params.subscribe(params => {
      let title = 'Cadastrar';

      this.acaoTemp = new AcaoTemp();
      this.perfil = new Perfil();

      this.coloca = [];
      this.pega = [];

      // this.listaAcao = [];
      this.listaFunc = [];

      this.perfil.acaoTemp = [];

      this.populaListaFuncionalidade().then(resolve => {
        this.listaFunc.forEach(item => {
          item.acaos.forEach(abaco => {
            this.acaoTemp.id = abaco.id;
            this.acaoTemp.id_funcionalidade = item.id;
            this.acaoTemp.nm_funcionalidade = item.nm_funcionalidade;
            this.acaoTemp.cd_funcionalidade = item.cd_funcionalidade;
            this.acaoTemp.nm_acao = abaco.nm_acao;
            this.acaoTemp.cd_acao = abaco.cd_acao;
            this.pega.push(Object.assign({}, this.acaoTemp));
          });
        });
      });


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
    this.coloca.forEach(elementos => {
      this.perfil.acaoTemp.push(elementos);
    });

    this.isSaving = true;
    if (this.perfil.id !== undefined) {
      // console.log("ID perfil: "+this.perfil.id);
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

  // populaListaAcao() {
  //   const that = this;
  //   return new Promise(resolve => {
  //     this.acaoService.getAllAcaos().subscribe(res => {
  //       res.forEach((item, index) => {
  //         this.listaAcao.push(item.nm_acao);
  //       });
  //       return resolve(true);
  //     });
  //   });
  // }

  populaListaFuncionalidade() {
    const that = this;
    return new Promise(resolve => {
      this.funcionalidadeservice.getAllFuncionalidades().subscribe(res => {
        res.forEach(item => {
          this.listaFunc.push(item);
        });
        return resolve(true);
      });
    });
  }

}
