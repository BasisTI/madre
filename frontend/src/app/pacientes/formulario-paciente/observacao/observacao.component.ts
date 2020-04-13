import { Component, Input } from '@angular/core';
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
<<<<<<< HEAD
    @Input() observacao: FormGroup;
=======
    observacao = new FormGroup({
        valor: new FormControl(''),
    });
>>>>>>> 81ac57c75e9a9f5ca2f456eb3fc383ef469fe000
}
