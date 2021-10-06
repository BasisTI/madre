import { Component } from '@angular/core';
import { FormularioTransferirPaciente } from '@internacao/models/transferir-paciente';
import { FormularioTransferirPacienteService } from '@internacao/services/formulario-transferir-paciente.service';

@Component({
  selector: 'app-formulario-transferir-paciente',
  templateUrl: './formulario-transferir-paciente.component.html',
  styleUrls: ['./formulario-transferir-paciente.component.css']
})
export class FormularioTransferirPacienteComponent{

  constructor(
    private formularioTransferirPacienteService: FormularioTransferirPacienteService) {  }

  descricao: string;

  cadastrar(){
    let cadastro: FormularioTransferirPaciente = {
      descricao: this.descricao
    };

    this.formularioTransferirPacienteService.cadastrarTransferirPaciente(cadastro).subscribe();
    this.limparFormulario();
  }

  limparFormulario() {
    this.descricao = null;
  }

  validarFormulario() {
    if(this.descricao)
    return true;
  }
}
