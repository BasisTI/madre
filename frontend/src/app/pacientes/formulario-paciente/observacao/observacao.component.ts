import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
    selector: 'app-observacao',
    templateUrl: './observacao.component.html',
    styles: [
        `
            textarea {
                font-family: sans-serif;
                font-size: 16px;
            }
        `,
    ],
})
export class ObservacaoComponent {
    observacao = new FormGroup({
        valor: new FormControl(''),
    });
}
