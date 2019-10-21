import { Component, OnInit } from '@angular/core';
import { Subscription, Observable } from 'rxjs';
import { JobServiceService } from '../HRservice/job-service.service';
import { AuthenticationService, UserService } from '../HRservice/loginservice';
import { User } from '../HRservice/models';
import { NavService } from '../../nav/nav.service';

@Component({
  selector: 'app-job',
  templateUrl: './job.component.html',
  styleUrls: ['./job.component.css']
})
export class JobComponent implements OnInit {
  jobList:any[];
  jobSubscription:Subscription;
  currentUser: User;
  currentUserSubscription: Subscription;
  isLoggedIn$: Observable<boolean>;

  constructor(private jobService: JobServiceService,private authenticationService: AuthenticationService,
    private userService: UserService,public nav: NavService) 
    { this.currentUserSubscription = this.authenticationService.currentUser.subscribe(user => {
      this.currentUser = user;
  });
}

  ngOnInit() {
    this.nav.show();
    console.log("inside ngOnInit");
    this.isLoggedIn$ = this.authenticationService.loggedIn;
    console.log(this.isLoggedIn$);
    this.jobSubscription= this.jobService.getJobs()
    .subscribe((res:any[])=>{
      console.log(res);
      this.jobList = res;
  });
  }

  ngOnDestroy() {
    // unsubscribe to ensure no memory leaks
    this.currentUserSubscription.unsubscribe();
    this.isLoggedIn$ = this.authenticationService.loggedIn;
    console.log(this.isLoggedIn$);
}
}
