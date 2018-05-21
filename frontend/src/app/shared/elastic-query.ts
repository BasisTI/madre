export class ElasticQuery {

    private static readonly wildcard = '*';

    value: string ;
    
    get query(): string {
        return this.value ? "*" + this.value + "*" : ElasticQuery.wildcard;
    }

    reset() {
        this.value = '';
    }

}
// resolver