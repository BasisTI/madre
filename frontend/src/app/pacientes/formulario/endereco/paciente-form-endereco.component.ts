import { Component, Input } from "@angular/core";
import { FormBuilder, Validators, FormArray } from "@angular/forms";
import { CepService } from "./cep.service";
import { UfService } from "../municipio/uf.service";
import { MunicipioService } from "../municipio/municipio.service";
import { UF } from "../../models/dropdowns/types/uf";
import { MunicipioUF } from "../../models/dropdowns/types/municipio-uf";
import { CEP } from "./cep.model";

import { OPCOES_DE_TIPO_DE_TELEFONE } from '../../models/dropdowns/opcoes-de-tipo-de-endereco';

@Component({
    selector: 'paciente-form-endereco',
    templateUrl: './paciente-form-endereco.component.html',
})
export class PacienteEnderecoFormComponent {

    @Input()
    public enderecos: FormArray;

    endereco = this.fb.group({
        id: [null],
        cep: [null, Validators.required],
        logradouro: [null, Validators.required],
        numero: [null, Validators.required],
        complemento: [null],
        bairro: [null, Validators.required],
        correspondencia: [null, Validators.required],
        tipoDoEndereco: [null, Validators.required],
        municipioId: [null],
        uf: [null],
    });

    opcoesDeTipoDeEndereco = OPCOES_DE_TIPO_DE_TELEFONE;

    ufs: UF[] = [];
    municipios: MunicipioUF[] = [];
    ceps: CEP[] = [];

    constructor(
        private fb: FormBuilder,
        public municipioService: MunicipioService,
        public ufService: UfService,
        public cepService: CepService,
    ) { }

    ngOnInit() {
        this.ufService.getListaDeUF().subscribe((res) => (this.ufs = res));
    }

    consultaCEP() {
        const cep = this.endereco.value.cep;
        if (cep != null && cep !== '') {
            this.cepService.buscarCEP(cep)
                .subscribe(dados => this.populaDadosForm(dados));
        }
    }

    populaDadosForm(dados) {
        this.endereco.patchValue({
            logradouro: dados.logradouro,
            bairro: dados.bairro,
        });
        const municipioId = dados.municipioId;
        if (municipioId != null && municipioId !== '') {
            this.municipioService.getMunicipioById(municipioId).subscribe(dados => this.populaMunicipio(dados));
        }
        console.log(dados);
    }

    populaMunicipio(dados) {
        this.endereco.patchValue({
            municipioId: dados
        });
        const ufId = dados.ufId;
        if (ufId != null && ufId != '') {
            this.ufService.getUfById(ufId).subscribe(dados => this.populaUF(dados));
        }
    }

    populaUF(dados) {
        this.endereco.patchValue({
            uf: dados
        });
        console.log(dados);
    }

    aoSelecionarUF() {
        this.endereco.controls.municipioId.setValue(null);
        this.municipioService
            .getListaDeMunicipiosUF(this.endereco.value.uf.id, '')
            .subscribe((res) => (this.municipios = res));
    }

    searchUnidade(event) {
        this.municipioService
            .getListaDeMunicipiosUF(this.endereco.value.uf.id, event.query)
            .subscribe((res) => {
                this.municipios = res;
            });
    }

    adicionarEnderecoALista() {
        if (this.endereco.valid) {
            this.enderecos.push(this.endereco);
            this.endereco = this.fb.group({
                id: [null],
                cep: [null, Validators.required],
                logradouro: [null, Validators.required],
                numero: [null, Validators.required],
                complemento: [null],
                bairro: [null, Validators.required],
                correspondencia: [null, Validators.required],
                tipoDoEndereco: [null, Validators.required],
                municipioId: [null],
                uf: [null],
            });
            this.endereco.reset();
        }
    }

}
