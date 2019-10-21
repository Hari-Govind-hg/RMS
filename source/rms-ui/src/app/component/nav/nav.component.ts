import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../HR/HRservice/loginservice';
import { User } from '../HR/HRservice/models';
import { NavService } from './nav.service';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styles: ['./nav.component.css']
})
export class NavComponent implements OnInit {
  currentUser: User;
  constructor(
    private router: Router,
    public nav:NavService,
    private authenticationService: AuthenticationService
  ) 
  {this.authenticationService.currentUser.subscribe(x => this.currentUser = x); }

  ngOnInit() {
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/hrlogin']);
}
}
