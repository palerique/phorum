import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from '../shared/auth/authentication.service';

@Component({
  moduleId: module.id,
  templateUrl: 'login.component.html'
})

export class LoginComponent implements OnInit {
  user: any = {};

  constructor(private router: Router,
              private authenticationService: AuthenticationService) {
  }

  ngOnInit() {
    this.authenticationService.logout();
  }

  login() {
    this.authenticationService.login(this.user.username, this.user.password)
    .subscribe(response => {
      // login successful if there's a jwt token in the response
      const token = response && response.id_token;
      if (token) {
        this.authenticationService.getAuthenticatedUserId(token).subscribe(user => {
          this.authenticationService.saveAuthData(user, token);
          window.location.reload();
          this.router.navigate(['/']);
        });
      } else {
        this.authenticationService.logout();
        this.router.navigate(['/login']);
      }
    });
  }
}
