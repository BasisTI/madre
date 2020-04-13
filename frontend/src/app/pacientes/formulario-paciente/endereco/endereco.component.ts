import { Component, Input } from '@angular/core';

import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
    selector: 'app-endereco',
    templateUrl: './endereco.component.html',
    styles: [
        `
            div {
                margin: 3px;
            }
        `,
    ],
})
export class EnderecoComponent {
<<<<<<< HEAD
    @Input() enderecos: FormGroup;
=======
>>>>>>> 81ac57c75e9a9f5ca2f456eb3fc383ef469fe000
    opcao = [
        { label: 'selecione' },
        { label: 'residencial', value: 'residencial' },
        { label: 'comercial', value: 'comercial' },
        { label: 'outros', value: 'outros' },
    ];
    UF = [
        { label: 'selecione' },
        { label: 'RJ', value: 'RJ' },
        { label: 'SP', value: 'SP' },
        { label: 'BA', value: 'BA' },
        { label: 'DF', value: 'DF' },
    ];
    municipios = [
        { label: 'selecione' },
        { label: 'São Paulo', value: 'São Paulo' },
        { label: 'Rio de Janeiro', value: 'Rio de Janeiro' },
        { label: 'Goias', value: 'Goias' },
        { label: 'Rio Grande do Sul', value: 'Rio Grande do Sul' },
    ];

<<<<<<< HEAD
    fakeData = [];
    adicionar() {
        this.fakeData.push(this.enderecos.value);
    }

=======
    enderecos: Array<any>;
    endereco: FormGroup = this.fb.group({
        municipio: ['', Validators.required],
        CEP: ['', Validators.required],
        UF: ['', Validators.required],
        logradouro: ['', Validators.required],
        Numero: ['', Validators.required],
        Complemento: [''],
        Bairro: ['', Validators.required],
        tipo: ['', Validators.required],
        corespondencia: ['', Validators.required],
    });

    fakeData = [];
    adicionar() {
        this.fakeData.push(this.endereco.value);
    }

>>>>>>> 81ac57c75e9a9f5ca2f456eb3fc383ef469fe000
    constructor(private fb: FormBuilder) {}
}
