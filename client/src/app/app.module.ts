import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';

import {TopicService} from './shared/topic/topic.service';
import {HttpClientModule} from '@angular/common/http';
import {TopicListComponent} from './topic-list/topic-list.component';

@NgModule({
  declarations: [
    AppComponent,
    TopicListComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [TopicService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
