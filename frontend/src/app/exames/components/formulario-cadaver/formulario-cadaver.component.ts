import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {RacaDropdown} from "../../models/dropdowns/raca.dropdown";
import {GrupoSanguineoDropdown} from "../../models/dropdowns/grupo-sanguineo.dropdown";
import {ConvenioDeSaude} from "../../models/convenioDeSaude.model";
import {CadaverModel} from "../../models/subjects/cadaver-model";
import {CadaverService} from "../../services/cadaver.service";
import {Hospital} from "@internacao/models/hospital";
import * as moment from "moment";
import {MessageService} from "primeng/api";


@Component({
  selector: 'app-formulario-cadaver',
  templateUrl: './formulario-cadaver.component.html',
  styleUrls: ['./formulario-cadaver.component.css'],
})

export class FormularioCadaverComponent implements OnInit{

    hospitais: Hospital[] = [];
    conveniosDeSaude: ConvenioDeSaude[] = [];
    raca = RacaDropdown;
    sanguineo = GrupoSanguineoDropdown;
    dataNascimento: Date;

    ngOnInit(): void {
      this.listarConvenios();

      this.listarHospitais();
    }

    constructor(private fb: FormBuilder,
                public readonly cadaverService: CadaverService,
                private msg: MessageService) {}


    cadastrarObito = this.fb.group({
        nome: ['', Validators.required],
        raca: ['', Validators.required],
        grupoSanguineo:['', Validators.required],
        dataRemocao: ['', Validators.required],
        causaObito:['', Validators.required],
        realizadoPor:['', Validators.required],
        lidoPor: ['', Validators.required],
        procedenciaId: ['', Validators.required],
        retiradaId: ['', Validators.required],
        codigoPlano: ['', Validators.required],
        observacao:['', Validators.required],

    });

    cadastro(){
      let cadastroObito = this.cadastrarObito.value;

      let cadastro: CadaverModel= {
        nome: cadastroObito.nome,
            dataNascimento: this.dataNascimento,
            raca: cadastroObito.raca,
            grupoSanguineo: cadastroObito.grupoSanguineo,
            dataRemocao: cadastroObito.dataRemocao,
            causaObito: cadastroObito.causaObito,
            realizadoPor: cadastroObito.realizadoPor,
            lidoPor: cadastroObito.lidoPor,
            procedenciaId: cadastroObito.procedenciaId,
            retiradaId: cadastroObito.retiradaId,
            codigoPlano:cadastroObito.codigoPlano,
            observacao: cadastroObito.observacao,
          }

          if (moment(this.dataNascimento).isAfter(cadastroObito.dataRemocao)){
            this.msg.add({
              severity:'error', summary:'Erro no preenchimento',
              detail:'Data de remoção deve ser após a data de nascimento'
            })
            return;
          }

          this.cadaverService.cadastrarObito(cadastro).subscribe();
          this.limparFormulario();
        }

        listarConvenios() {
          this.cadaverService.getConvenioDeSaude().subscribe((response) => {
            this.conveniosDeSaude = response;
        });
        }

        listarHospitais() {
          this.cadaverService.getHospitais().subscribe((response) => {
            this.hospitais = response;
        });
        }

        validarFormulario(): boolean {
          return this.cadastrarObito.valid;
      }

        limparFormulario() {
            this.cadastrarObito.reset()
            this.dataNascimento = null;
        }
}
