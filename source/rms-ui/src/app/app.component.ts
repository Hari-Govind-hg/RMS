import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AuthenticationService } from './component/HR/HRservice/loginservice';
import { User } from './component/HR/HRservice/models';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'rms-ui';
  currentUser: User;

  constructor(
    public translate: TranslateService,
    private router: Router,
    private authenticationService: AuthenticationService
  ) {
    translate.addLangs(['en', 'fr', 'de', 'es']);
    translate.setDefaultLang('en');
    const browserLang = translate.getBrowserLang();
    translate.use(browserLang.match(/en|de/) ? browserLang : 'en');
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }
}
