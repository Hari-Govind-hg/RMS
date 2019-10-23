import { Component, OnInit, OnDestroy } from '@angular/core';
import { AuthenticationService } from '../../authentication/authentication.service';
import { NavService } from '../../nav/nav.service';
import { Router, ActivatedRoute } from '@angular/router';
import { User } from '../CandidateService/models/user';
import { Subscription } from 'rxjs/internal/Subscription';


@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css']
})
export class LandingComponent implements OnInit,OnDestroy {
  currentUserSubscription: Subscription;
  currentUser:User
  constructor(private authenticationService: AuthenticationService,
    public nav: NavService,private router:Router,private route:ActivatedRoute) 
    { 
      this.currentUserSubscription = this.authenticationService.currentUser.subscribe(user => {
      this.currentUser = user;
  });
}

  ngOnInit() {
    this.nav.show();
  }

  ngOnDestroy() {
    // unsubscribe to ensure no memory leaks
    this.currentUserSubscription.unsubscribe();
}

}
