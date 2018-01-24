import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from './shared/auth/authentication.service';
import {TopicService} from './shared/topic/topic.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  topics: Array<any>;

  constructor(private topicService: TopicService,
              private authenticationService: AuthenticationService,
              private router: Router) {
  }

  ngOnInit() {
    this.topicService.getAll().subscribe(data => {
      this.topics = data;
    });
  }

  isAuthenticated(): boolean {
    return this.authenticationService.isAuthenticated();
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }
}
