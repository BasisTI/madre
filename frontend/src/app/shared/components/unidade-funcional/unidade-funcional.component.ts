import { Component, OnInit } from '@angular/core';
import { UnidadeFuncional } from '@internacao/models/unidade-funcional';
import { UnidadeFuncionalService } from 'src/app/exames/services/unidade-funcional.service';

@Component({
  selector: 'app-unidade-funcional',
  templateUrl: './unidade-funcional.component.html',
  styleUrls: ['./unidade-funcional.component.css']
})
export class UnidadeFuncionalComponent implements OnInit {
  
  unidadesFuncionais: UnidadeFuncional[];
  unidadeId: number;

  constructor(private unidadeFuncionalService: UnidadeFuncionalService) { }

  ngOnInit(): void {
    this.obterUnidades();
  }

  obterUnidades() {
    this.unidadeFuncionalService.getUnidades().subscribe((response) => {
      this.unidadesFuncionais = response;
    });
  }

  imprimirId() {
    console.log(this.unidadeId);
  }
}
