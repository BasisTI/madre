import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

import { FieldFormNotification, ErrorProvider, FormNotificationService } from '@nuvem/angular-base';

/**
 * Provedor de error para criação de erros de notificação.
 * @class
 */
@Injectable({
    providedIn: 'root',
})
export class FormNotificationErrorProvider implements ErrorProvider {
    /**
     * Método construtor
     * @param {FormtNotificationService} formNotificationService
     * @constructor
     */
    constructor(private formNotificationService: FormNotificationService) {}

    /**
     * Método responsável pela identificação de erros
     * @public
     * @param {Error | HttpErrorResponse} error
     * @returns Boolean
     */
    shouldHandle(error: Error | HttpErrorResponse): Boolean {
        return (
            error instanceof HttpErrorResponse &&
            error.headers.get('Content-Type') === 'application/problem+json' &&
            error.error &&
            (error.error.violations || error.error.fieldErrors)
        );
    }

    /**
     * Método para emissão de erros
     * @public
     * @param {HttpErrorResponse} error
     * @returns void
     */
    handle(error: HttpErrorResponse): void {
        if (error.error.violations) {
            this.violation(error.url, error.error.violations);
        }
        if (error.error.fieldErrors) {
            this.violation(error.url, error.error.fieldErrors);
        }
    }

    /**
     * Método iteração de violações e emissão de erros
     * @param values
     */
    violation(form: string, values: { field: string; message: string }[]) {
        values.forEach((violation) =>
            this.formNotificationService.notify(
                new FieldFormNotification(form, violation.field, violation.message),
            ),
        );
    }
}
