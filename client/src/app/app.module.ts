import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';

import {TopicService} from './shared/topic/topic.service';
import {HttpClientModule} from '@angular/common/http';
import {TopicListComponent} from './topic-list/topic-list.component';

import {GiphyService} from './shared/giphy/giphy.service';

import {
  MatButtonModule,
  MatCardModule,
  MatInputModule,
  MatListModule,
  MatToolbarModule
} from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {TopicEditComponent} from './topic-edit/topic-edit.component';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';

const appRoutes: Routes = [
  {path: '', redirectTo: '/topic-list', pathMatch: 'full'},
  {
    path: 'topic-list',
    component: TopicListComponent
  },
  {
    path: 'topic-add',
    component: TopicEditComponent
  },
  {
    path: 'topic-edit/:id',
    component: TopicEditComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    TopicListComponent,
    TopicEditComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCardModule,
    MatInputModule,
    MatListModule,
    MatToolbarModule,
    FormsModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [TopicService, GiphyService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
