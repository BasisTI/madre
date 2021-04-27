import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FarmaciaService } from '../farmacia.service';
import { Prescricaos } from '../dispensacao/prescricao';

@Component({
  selector: 'app-dispensacao-detail',
  templateUrl: './dispensacao-detail.component.html',
  styleUrls: ['./dispensacao-detail.component.css']
})
export class DispensacaoDetailComponent implements OnInit {

  public prescricao: Prescricaos = new Prescricaos;

  constructor(
    private service: FarmaciaService,
    private route: ActivatedRoute,
  ) {
  }

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.load(params['id']);
    });
  }

  load(id) {
    this.service.findP(id).subscribe((prescricao) => {
      this.prescricao = prescricao;
    })
  }

}
