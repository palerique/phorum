import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {AuthenticationService} from '../auth/authentication.service';

@Injectable()
export class CategoryService {

  public API = '//localhost:8080/api';
  public CATEGORIES_API = this.API + '/categories';

  private _options = {
    headers: new HttpHeaders({'Authorization': 'Bearer ' + this.authenticationService.token})
  };

  constructor(private http: HttpClient,
              private authenticationService: AuthenticationService) {
  }

  getAll(): Observable<any> {
    // add authorization header with jwt token
    return this.http.get(this.CATEGORIES_API, this._options);
  }

  get(id: string) {
    return this.http.get(this.CATEGORIES_API + '/' + id, this._options);
  }

  save(category: any): Observable<any> {
    let result: Observable<Object>;
    if (category['href']) {
      result = this.http.put(category.href, category, this._options);
    } else {
      result = this.http.post(this.CATEGORIES_API, category, this._options);
    }
    return result;
  }

  remove(href: string) {
    return this.http.delete(href, this._options);
  }
}
