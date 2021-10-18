import { Component, OnInit } from '@angular/core';
import { LeitoService } from '@internacao/services/leito.service';
import { ListaLeito } from '@internacao/models/leito-model';

@Component({
  selector: 'app-formulario-transferir-paciente',
  templateUrl: './formulario-transferir-paciente.component.html',
  styleUrls: ['./formulario-transferir-paciente.component.css']
})
export class FormularioTransferirPacienteComponent implements OnInit{


  listarLeito: ListaLeito[];
  
  constructor( private leitoService:LeitoService, ) {  }

  listarGrade(){
    this.leitoService.listarLeitos().subscribe((response) => {
      this.listarLeito = response;
    });
  }
  
  ngOnInit(): void { this.listarGrade(); }
}
