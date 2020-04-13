import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { Component, Input } from '@angular/core';

@Component({
    selector: 'app-telefone',
    templateUrl: './telefone.component.html',
    styles: [
        `
            div {
                margin: 3px;
            }
        `,
    ],
})
export class TelefoneComponent {
    constructor(private fb: FormBuilder) {}
<<<<<<< HEAD
    @Input() telefones: FormGroup;

    opcao = [
        { label: 'selecione', value: null },
        { label: 'celular', value: 'celular' },
        { label: 'residencial', value: 'residencial' },
        { label: 'comercial', value: 'comercial' },
        { label: 'emergencial', value: 'emergencial' },
    ];

    telefone: FormGroup = this.fb.group({
        tipo: [''],
        telefone: ['', Validators.required],
        observacao: [''],
        DDD: ['', Validators.required],
    });

    fakeData = [];
    adicionar() {
        this.fakeData.push(this.telefones.value);
=======
    opcao = [
        { label: 'selecione', value: null },
        { label: 'celular', value: 'celular' },
        { label: 'residencial', value: 'residencial' },
        { label: 'comercial', value: 'comercial' },
        { label: 'emergencial', value: 'emergencial' },
    ];

    telefone: FormGroup = this.fb.group({
        tipo: [''],
        telefone: ['', Validators.required],
        observacao: [''],
        DDD: ['', Validators.required],
    });

    fakeData = [];
    adicionar() {
        this.fakeData.push(this.telefone.value);
>>>>>>> 81ac57c75e9a9f5ca2f456eb3fc383ef469fe000
    }
}
