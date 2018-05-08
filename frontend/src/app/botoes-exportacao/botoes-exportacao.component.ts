import { MessageUtil } from './../util/message.util';
import { ExportacaoUtil } from './../util/exportacao.util';
import { ExportacaoUtilService } from './../util/service/exportacao-util.service';
import { Component, OnInit, EventEmitter, Input } from '@angular/core';
import { MenuItem, DataTable } from 'primeng/primeng';
import { HttpService } from '@basis/angular-components';
import { environment } from '../../environments/environment';
import { ToastrService } from 'ngx-toastr';
import { NgBlockUI, BlockUI } from 'ng-block-ui';

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
    private http: HttpService
  ) {}

  getTiposExportacao() {
    this.tiposExportacao = [
      { label: 'PDF', icon: '', command: () => { this.exportar(ExportacaoUtilService.PDF); } },
      { label: 'EXCEL', icon: '', command: () => { this.exportar(ExportacaoUtilService.EXCEL); } },
      { label: 'IMPRIMIR', icon: '', command: () => { this.imprimir(ExportacaoUtilService.PDF); } },
    ];
  }

  exportar(tipoRelatorio: string) {
      this.blockUI.start(MessageUtil.GERANDO_RELATORIO);
    ExportacaoUtilService.exportarRelatorio(tipoRelatorio, environment.apiUrl + '/' + this.resourceName, this.http).subscribe(
      downloadUrl => {
        //downloadUrl = `${downloadUrl}?query=`;
        ExportacaoUtil.download(downloadUrl,
          this.resourceName + ExportacaoUtilService.getExtension(tipoRelatorio));
          this.blockUI.stop();
      },
      err => {
        this.toastrService.error(MessageUtil.ERRO_EXPORTAR_ARQUIVO);
        this.blockUI.stop();
      }
    );
  }

  imprimir(tipoRelatorio: string) {
    this.blockUI.start(MessageUtil.GERANDO_IMPRESSAO_RELATORIO);
    ExportacaoUtilService.exportarRelatorio(tipoRelatorio, environment.apiUrl + '/' + this.resourceName, this.http).subscribe(
      downloadUrl => {
        ExportacaoUtil.imprimir(downloadUrl);
        this.blockUI.stop();
      },
      err => {
        this.toastrService.error(MessageUtil.ERRO_IMPRIMIR_ARQUIVO);
        this.blockUI.stop();
      }
    );
  }
}
