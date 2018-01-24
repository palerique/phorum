import {Injectable} from '@angular/core';
import 'rxjs/add/operator/map';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class AuthenticationService {
  public token: string;

  constructor(private http: HttpClient) {
    // set token if saved in local storage
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.token = currentUser && currentUser.token;
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post('//localhost:8080/api/authenticate', {
      username: username,
      password: password
    });
  }

  logout(): void {
    // clear token remove user from local storage to log user out
    this.token = null;
    localStorage.removeItem('currentUser');
  }

  getCurrentUserId(): any {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    if (currentUser && currentUser.user && currentUser.user.id) {
      return currentUser.user.id;
    }
    this.logout();
  }

  isAuthenticated(): boolean {
    return !!(this.token && localStorage.getItem('currentUser'));
  }

  getAuthenticatedUserId(token: string): Observable<any> {
    return this.http.get('//localhost:8080/api/users/authenticated', {
      headers: new HttpHeaders({'Authorization': 'Bearer ' + token})
    });
  }

  saveAuthData(user: any, token: string) {
    localStorage.setItem('currentUser', JSON.stringify({user: user, token: token}));
  }
}
