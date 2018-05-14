import { Headers, RequestOptions, ResponseContentType } from '@angular/http';
import { HttpService } from '@basis/angular-components';
import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { filter } from 'rxjs/operators';

export class ExportacaoUtilService {

    static CONTENT_TYPE_PDF = 'application/pdf';
    static CONTENT_TYPE_CSV = 'text/csv';
    static CONTENT_TYPE_EXCEL = 'application/vnd.ms-excel';
    static PDF = 'pdf';
    static EXCEL = 'xls';
    static CSV = 'csv';
    headers: Headers;
    options: RequestOptions;

    static getContentType(tipoRelatorio: string): any {
        if (tipoRelatorio === this.PDF) {
            return ExportacaoUtilService.CONTENT_TYPE_PDF;
        }
        if (tipoRelatorio === this.EXCEL) {
            return ExportacaoUtilService.CONTENT_TYPE_EXCEL;
        }
        if (tipoRelatorio === this.CSV) {
            return ExportacaoUtilService.CONTENT_TYPE_CSV;
        }
        return null;
    }

    private static getOptions(): RequestOptions {
        const headers: Headers = new Headers();
        headers.append('Content-Type', 'application/json');

        /* headers.append('Authorization', AutenticacaoService.AUTORIZATION()); */
        const options: RequestOptions = new RequestOptions({ headers: headers });
        options.responseType = ResponseContentType.Blob;
        return options;
    }

    static exportarRelatorio(tipoRelatorio: string, resourceUrl: string, http: HttpService, query: string) {
        if(query == undefined){
            query = "*";
        }
        else{
            query = "*" + query +"*";
        }
        return ExportacaoUtilService.gerar(
            `${resourceUrl}/exportacao/` + tipoRelatorio + '?query=' + query,
            ExportacaoUtilService.getContentType(tipoRelatorio),
            http
        );
    }

    static gerar(endpoint: string, tipo: string, http: HttpService): any {
        return http.get(endpoint, ExportacaoUtilService.getOptions())
            .map((res: any) => {
                const file = new Blob([res.blob()], { type: tipo });
                return URL.createObjectURL(file);
            });
    }

    static getExtension(tipoRelatorio: string): string {
        if (tipoRelatorio === this.PDF) {
            return '.pdf';
        }
        if (tipoRelatorio === this.EXCEL) {
            return '.xls';
        }
        if (tipoRelatorio === this.CSV) {
            return '.csv';
        }
        return null;
    }
}
