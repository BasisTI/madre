import { Component, OnInit } from '@angular/core';
import { RequisicaoMaterialService } from '@suprimentos/services/requisicao-material.service';

@Component({
    selector: 'app-requisicao-material',
    templateUrl: './requisicao-material.component.html',
})
export class RequisicaoMaterialComponent implements OnInit {
    public url: string;

    constructor(private requisicaoMaterialService: RequisicaoMaterialService) {}

    ngOnInit(): void {
        this.url = this.requisicaoMaterialService.getResource();
    }
}
