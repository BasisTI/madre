import { DarAltaAoPaciente } from '@internacao/models/dar-alta-ao-paciente';
import { ConvenioDeSaudeService } from '@internacao/services/convenio-de-saude.service';
import { EspecialidadeService } from '@internacao/services/especialidade.service';
import { UnidadeFuncionalService } from 'src/app/exames/services/unidade-funcional.service';
import { LeitoService } from '@internacao/services/leito.service';
import { FormularioDarAltaAoPacienteService } from './../../services/formulario-dar-alta-ao-paciente.service';
import { ConvenioDeSaude } from './../../models/convenio-de-saude';
import { Especialidade } from './../../models/especialidade';
import { UnidadeFuncional } from './../../models/unidade-funcional';
import { PacienteService } from './../../services/paciente.service';
import { Component, OnInit } from '@angular/core';
import { Paciente} from '@internacao/models/paciente';
import { Leito } from '@internacao/models/leito';

@Component({
  selector: 'app-listar-dar-alta-pacientes',
  templateUrl: './listar-dar-alta-pacientes.component.html',
  styleUrls: ['./listar-dar-alta-pacientes.component.css']
})
export class ListarDarAltaPacientesComponent implements OnInit {

    id: string = '';
    pacienteId: string = '';
    leitosId: string = '';
    unidadeFuncionalId: string = '';
    especialidadeId: string = '';
    convenidoSaudeId: string = '';
    results = [];

    prontuario: number;

    darALtaPacientes: DarAltaAoPaciente[];
    pacientes: Paciente[] = [];
    leitos: Leito[] = [];
    unidadeFuncionais: UnidadeFuncional[] = [];
    especialidades: Especialidade[] = [];
    conveniosSaude: ConvenioDeSaude[] = [];


    seachUrl: string= '';
    constructor(private darAltaPAcienteService: FormularioDarAltaAoPacienteService,
        private pacienteService: PacienteService,
        private leitoService: LeitoService,
        private unidadeFuncionalService: UnidadeFuncionalService,
        private especialidadeService: EspecialidadeService,
        private convenioDeSaudeService: ConvenioDeSaudeService) { }

  ngOnInit(): void {

      this.unidadeFuncionalService.getUnidades().subscribe((response) => {
          this.unidadeFuncionais = response;
      })

      this.leitoService.obterLeitosOcupados().subscribe((response) => {
          this.leitos = response;
      })

      this.especialidadeService.getEspecialidades().subscribe((response) => {
          this.especialidades = response;
      })

      this.convenioDeSaudeService.getConveniosDeSaude().subscribe((response) => {
          this.conveniosSaude = response;
      })

      this.pacienteService.obterPacientePorProntuario(this.prontuario).subscribe((response) => {

      })

      this.lista();
  }

  lista(){
      this.darAltaPAcienteService.getDarAltaAoPaciente(this.id, this.pacienteId, this.leitosId,
        this.unidadeFuncionalId, this.especialidadeId, this.convenidoSaudeId)
        .subscribe((response) => {
            this.darALtaPacientes = response;
        });
  }
}
