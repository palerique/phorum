import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';

import {TopicService} from './shared/topic/topic.service';
import {CategoryService} from './shared/category/category.service';
import {HttpClientModule} from '@angular/common/http';
import {DialogDataExampleDialog, TopicListComponent} from './topic-list/topic-list.component';

import {GiphyService} from './shared/giphy/giphy.service';

import {
  MatButtonModule,
  MatCardModule,
  MatDialogModule,
  MatExpansionModule,
  MatGridListModule,
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
    TopicEditComponent,
    DialogDataExampleDialog
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
    FormsModule,
    RouterModule.forRoot(appRoutes),
    FroalaEditorModule.forRoot(),
    FroalaViewModule.forRoot()
  ],
  entryComponents: [
    DialogDataExampleDialog
  ],
  providers: [
    TopicService,
    CategoryService,
    GiphyService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
