import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import {NgxMaskModule} from 'ngx-mask';
import { BreadcrumbService } from '../breadcrumb/breadcrumb.service';
import { UnidadeHospitalar } from './unidade-hospitalar.model';
import { UnidadeHospitalarService } from './unidade-hospitalar.service';
import { UploadService } from '../upload/upload.service';
@Component({
  selector: 'jhi-unidade-hospitalar-detail',
  templateUrl: './unidade-hospitalar-detail.component.html'
})
export class UnidadeHospitalarDetailComponent implements OnInit, OnDestroy {

  unidadeHospitalar: UnidadeHospitalar;
  private subscription: Subscription;
  fileName: string;
  constructor(
    private unidadeHospitalarService: UnidadeHospitalarService,
    private route: ActivatedRoute,
    private breadcrumbService: BreadcrumbService,
    private uploadService: UploadService,
  ) {}

  ngOnInit() {
    this.subscription = this.route.params.subscribe((params) => {
      this.load(params['id']);
    });
    this.breadcrumbService.setItems([
      { label: 'Unidade de Saúde', routerLink: '/unidadeHospitalar' },
      { label: 'Visualizar' }
    ]);
  }

  load(id) {
    this.unidadeHospitalarService.find(id).subscribe((unidadeHospitalar) => {
      this.unidadeHospitalar = unidadeHospitalar;
      this.getFileInfo();
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
    this.breadcrumbService.reset();
  }

  getFileInfo() {
    let fileInfo;
    this.uploadService.getFileInfo(this.unidadeHospitalar.logoId).subscribe(response => {
      fileInfo = response;

      this.fileName = fileInfo["originalName"];
    });
  }
}
