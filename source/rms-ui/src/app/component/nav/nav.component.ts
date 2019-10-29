import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
// import { AuthenticationService } from '../HR/HRservice/loginservice';
import { AuthenticationService } from '../authentication/authentication.service';
import { User } from '../HR/HRservice/models';
import { NavService } from './nav.service';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styles: ['./nav.component.css']
})
export class NavComponent implements OnInit {
  currentUser: User;
  constructor(
    public translate: TranslateService,
    private router: Router,
    public nav: NavService,
    private authenticationService: AuthenticationService
  ) {
    translate.addLangs(['en', 'fr', 'de', 'es']);
    translate.setDefaultLang('en');
    const browserLang = translate.getBrowserLang();
    translate.use(browserLang.match(/en|de/) ? browserLang : 'en');
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
  }

  ngOnInit() {
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/']);
  }
}
