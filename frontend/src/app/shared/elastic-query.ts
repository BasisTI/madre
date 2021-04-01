import { ListarConsultasComponent } from "../consulta/components/listar-consultas/listar-consultas.component";

export class ElasticQuery {
 
    private static readonly wildcard = '*';

    value: string = "";
    grade: string = "";

    get query(): string {
        return this.value ? `*${this.value}*`  : ElasticQuery.wildcard;
    }

    get gradee():string{
        return this.value ? `*${this.value}*`  : ElasticQuery.wildcard;
    }

    reset() {
        this.value = '';
    }

}
