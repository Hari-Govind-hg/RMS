import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { JobServiceService } from '../../job-service.service';

@Component({
  selector: 'app-job',
  templateUrl: './job.component.html',
  styleUrls: ['./job.component.css']
})
export class JobComponent implements OnInit {
  jobList:any[];
  jobSubscription:Subscription;

  constructor(private jobService: JobServiceService) { }

  ngOnInit() {
    console.log("inside ngOnInit");
    this.jobSubscription= this.jobService.getJobs()
    .subscribe((res:any[])=>{
      console.log(res);
      this.jobList = res;
  });
  }

}
