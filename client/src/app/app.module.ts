import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';

import {TopicService} from './shared/topic/topic.service';
import {CategoryService} from './shared/category/category.service';
import {HttpClientModule} from '@angular/common/http';
import {CommentDialogComponent, TopicListComponent} from './topic-list/topic-list.component';

import {GiphyService} from './shared/giphy/giphy.service';

import {
  MatButtonModule,
  MatCardModule,
  MatDialogModule,
  MatExpansionModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatSelectModule,
  MatToolbarModule
} from '@angular/material';

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {TopicEditComponent} from './topic-edit/topic-edit.component';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';

import {FroalaEditorModule, FroalaViewModule} from 'angular-froala-wysiwyg';
import {LoginComponent} from './login/login.component';
import {AuthGuard} from './guard/auth.guard';
import {AuthenticationService} from './shared/auth/authentication.service';

const appRoutes: Routes = [
  {path: 'login', component: LoginComponent},
  {
    path: '',
    redirectTo: '/topic-list',
    pathMatch: 'full',
    canActivate: [AuthGuard]
  },
  {
    path: 'topic-list',
    component: TopicListComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'topic-add',
    component: TopicEditComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'topic-edit/:id',
    component: TopicEditComponent,
    canActivate: [AuthGuard]
  },
  // otherwise redirect to home
  {path: '**', redirectTo: ''}
];

@NgModule({
  declarations: [
    AppComponent,
    TopicListComponent,
    TopicEditComponent,
    CommentDialogComponent,
    LoginComponent
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
    MatSelectModule,
    MatGridListModule,
    MatExpansionModule,
    MatDialogModule,
    MatIconModule,
    FormsModule,
    RouterModule.forRoot(appRoutes),
    FroalaEditorModule.forRoot(),
    FroalaViewModule.forRoot()
  ],
  entryComponents: [
    CommentDialogComponent
  ],
  providers: [
    AuthGuard,
    AuthenticationService,
    TopicService,
    CategoryService,
    GiphyService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
