import { Component, OnInit } from '@angular/core';
import { ListaServidor } from '../../models/dropdowns/lista-servidor';
import { ServidorService } from '../../services/servidor.service';

@Component({
  selector: 'app-graduacoes-de-servidores',
  templateUrl: './graduacoes-de-servidores.component.html',
  styleUrls: ['./graduacoes-de-servidores.component.css']
})
export class GraduacoesDeServidoresComponent implements OnInit {

  listaServidor = new Array<ListaServidor>();

  constructor(
    private servidorService: ServidorService,
  ) { }

  ngOnInit(): void {
  }

  buscaServidor(event) {
    this.servidorService.getResultServidor(event.query).subscribe((data) => {
      this.listaServidor = data.content;
    });
  }
}
