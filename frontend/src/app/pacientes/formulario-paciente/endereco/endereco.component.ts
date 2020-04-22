import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MunicipioService } from './municipio.service';
import { OPCOES_DE_TIPO_DE_TELEFONE } from '../../models/dropdowns/opcoes-de-tipo-de-endereco';

@Component({
    selector: 'app-endereco',
    templateUrl: './endereco.component.html',
    styleUrls: ['./endereco.component.scss'],
})
export class EnderecoComponent {
    @Input() enderecos: FormGroup;
    opcoesDeTipoDeEndereco = OPCOES_DE_TIPO_DE_TELEFONE;
    listaDeEnderecos = [];

    constructor(private fb: FormBuilder, public municipioService: MunicipioService) {}

    adicionarEnderecoALista() {
        this.listaDeEnderecos.push(this.enderecos.value);
    }
}
