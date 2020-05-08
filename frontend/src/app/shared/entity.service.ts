import { Observable } from 'rxjs';
import { HttpParams } from '@angular/common/http';

export interface EntityService {
    getResource<T>(params?: HttpParams): Observable<T>;
}
