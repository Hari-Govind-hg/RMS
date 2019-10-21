import { Component, OnInit } from '@angular/core';
import { NavService } from '../../nav/nav.service';

@Component({
  selector: 'app-login-landing',
  templateUrl: './login-landing.component.html',
  styleUrls: ['./login-landing.component.css']
})
export class LoginLandingComponent implements OnInit {

  constructor(public nav: NavService) { }

  ngOnInit() {
    this.nav.hide();
  }

}
