import { UnidadeFuncional } from './../../models/unidade-funcional';
import { TipoDeUnidadeFuncionalService } from './../../services/tipo-de-unidade-funcional.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-tipo-de-unidade-funcional',
    templateUrl: './tipo-de-unidade-funcional.component.html',
    styleUrls: ['./tipo-de-unidade-funcional.component.css'],
})
export class TipoDeUnidadeFuncionalComponent implements OnInit {
    nome: string;

    constructor(
        private fb: FormBuilder,
        private tipoUnidadeFuncionalService: TipoDeUnidadeFuncionalService,
    ) {}

    cadastrarTipoUnidadeFuncional = this.fb.group({
        nome: [null, Validators.required],
    });

    ngOnInit(): void {}

    cadastrar() {
        let tipoUnidadeFuncional = this.cadastrarTipoUnidadeFuncional.value;

        let cadastro: UnidadeFuncional = {
            nome: tipoUnidadeFuncional.nome,
        };

        console.log(tipoUnidadeFuncional);

        this.tipoUnidadeFuncionalService.cadastrarTipoUnidadeFuncional(cadastro).subscribe();
        this.limparTipoUnidadeFuncional();
    }

    validarTipoUnidadeFuncional() {
        if (this.cadastrarTipoUnidadeFuncional.valid) return true;
    }

    limparTipoUnidadeFuncional() {
        this.cadastrarTipoUnidadeFuncional.reset();
    }
}
