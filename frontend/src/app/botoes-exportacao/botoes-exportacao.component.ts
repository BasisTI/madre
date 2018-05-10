import { MessageUtil } from './../util/message.util';
import { ExportacaoUtil } from './../util/exportacao.util';
import { ExportacaoUtilService } from './../util/service/exportacao-util.service';
import { Component, OnInit, EventEmitter, Input } from '@angular/core';
import { MenuItem, DataTable } from 'primeng/primeng';
import { HttpService } from '@basis/angular-components';
import { environment } from '../../environments/environment';
import { ToastrService } from 'ngx-toastr';
import { NgBlockUI, BlockUI } from 'ng-block-ui';
import { PageNotificationService } from '@basis/angular-components';
import {PanelMenuModule} from 'primeng/panelmenu';

@Component({
  selector: 'app-botoes-exportacao',
  templateUrl: 'botoes-exportacao.component.html'
})
export class BotoesExportacaoComponent implements OnInit {
  @Input() resourceName: string;
  @BlockUI() blockUI: NgBlockUI;
  
  tiposExportacao: MenuItem[] = [];

  ngOnInit() {
    this.getTiposExportacao();
  }

  constructor(
    private toastrService: ToastrService,
    private http: HttpService,
    private pageNotificationService: PageNotificationService
  ) {}

  getTiposExportacao() {
    this.tiposExportacao = [
      { label: 'PDF', icon: '', command: () => { this.exportar(ExportacaoUtilService.PDF); } },
      { label: 'EXCEL', icon: '', command: () => { this.exportar(ExportacaoUtilService.EXCEL); } },
      { label: 'IMPRIMIR', icon: '', command: () => { this.imprimir(ExportacaoUtilService.PDF); } },
    ];
  }

  exportar(tipoRelatorio: string) {
    ExportacaoUtilService.exportarRelatorio(tipoRelatorio, environment.apiUrl + '/' + this.resourceName, this.http).subscribe(
      downloadUrl => {
       // downloadUrl = `${downloadUrl}?query="SELECT NOME FROM UNIDADEHOSPITALARS WHERE NOME  = 'aa'"`;
        ExportacaoUtil.download(downloadUrl,
          this.resourceName + ExportacaoUtilService.getExtension(tipoRelatorio));
          this.blockUI.stop();
      },
      err => {
        this.addErrorMessage(false);
      }
    );
  }

  imprimir(tipoRelatorio: string) {
    ExportacaoUtilService.exportarRelatorio(tipoRelatorio, environment.apiUrl + '/' + this.resourceName, this.http).subscribe(
      downloadUrl => {
        ExportacaoUtil.imprimir(downloadUrl);
        this.blockUI.stop();
      },
      err => {
        // this.toastrService.error(MessageUtil.ERRO_IMPRIMIR_ARQUIVO);
        // this.blockUI.stop();
        this.addErrorMessage(true);
      }
    );
  }

  // private addConfirmationMessage() {
  //   this.pageNotificationService.addCreateMsg('Exportado com sucesso!');
  // }
  private addErrorMessage(isImprimir: boolean) {
    if (isImprimir)
      this.pageNotificationService.addErrorMessage('Erro ao imprimir pesquisa!');
    else
      this.pageNotificationService.addErrorMessage('Erro ao exportar pesquisa!');
  }
}

export class PanelMenuDemo {
  
  items: MenuItem[];

  ngOnInit() {
      this.items = [
          {
              label: 'File',
              icon: 'fa-file-o',
              items: [{
                      label: 'New', 
                      icon: 'fa-plus',
                      items: [
                          {label: 'Project'},
                          {label: 'Other'},
                      ]
                  },
                  {label: 'Open'},
                  {label: 'Quit'}
              ]
          },
          {
              label: 'Edit',
              icon: 'fa-edit',
              items: [
                  {label: 'Undo', icon: 'fa-mail-forward'},
                  {label: 'Redo', icon: 'fa-mail-reply'}
              ]
          },
          {
              label: 'Help',
              icon: 'fa-question',
              items: [
                  {
                      label: 'Contents'
                  },
                  {
                      label: 'Search', 
                      icon: 'fa-search', 
                      items: [
                          {
                              label: 'Text', 
                              items: [
                                  {
                                      label: 'Workspace'
                                  }
                              ]
                          },
                          {
                              label: 'File'
                          }
                  ]}
              ]
          },
          {
              label: 'Actions',
              icon: 'fa-gear',
              items: [
                  {
                      label: 'Edit',
                      icon: 'fa-refresh',
                      items: [
                          {label: 'Save', icon: 'fa-save'},
                          {label: 'Update', icon: 'fa-save'},
                      ]
                  },
                  {
                      label: 'Other',
                      icon: 'fa-phone',
                      items: [
                          {label: 'Delete', icon: 'fa-minus'}
                      ]
                  }
              ]
          }
      ];
  }
}