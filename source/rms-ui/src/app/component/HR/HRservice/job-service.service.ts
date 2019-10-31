import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class JobServiceService {
  REST_API_URL: string = "http://localhost:80/jobs";
  constructor(private http: HttpClient,private router:Router) {

  }

  createJob(jobData: any) {
    // 1. get the data from the component
    console.log(jobData);

    //2.Send the above data t rest API
    //2.1identify the rest api url
    //2.2 send the data using POST method via REST API Client
    let promise = new Promise((resolve, reject) => {
      this.http.post(this.REST_API_URL, jobData)
        .toPromise()
        .then((res) => {            //3. Get the resp from rest api
          console.log(res);
          resolve(res);
        })
        .catch((err) => {           // Get the err from rest api
          console.log(err);
          reject(err);
        })
        .finally(() => {
          console.log("Ends");
        });

    });
    return promise;
    //4: Return response from server
  }
  getJobs() {
    return this.http.get(this.REST_API_URL)
      .pipe(map(res => {  //3.get res from rest api
        console.log(res);
        return res;     //Send it back to component
      }));
  }

  getJobById(id) {
    console.log("id is " + id);
    return this.http.get(this.REST_API_URL + "/" + id)
      .pipe(map(res => {
        console.log(res);
        return res;
      }));
  }

  getJobBySkill(skill){
    console.log("id is " + skill);
    return this.http.get(this.REST_API_URL + "/filterbyskill?skill="+ skill)
      .pipe(map(res => {
        console.log(res);
        return res;
      }));
  }
  searchJobsByExperience(experience){
    console.log("id is " + experience );
    return this.http.get(this.REST_API_URL + "/filterbyexperience?experience="+ experience)
      .pipe(map(res => {
        console.log(res);
        return res;
      }));
  }
  searchJobsBySkillExperience(skill,experience){
    console.log("id is " + skill );
    console.log("id is " + experience );
    return this.http.get(this.REST_API_URL + "/filterbyskillandexperience?skill=" +skill + "&experience="+ experience)
      .pipe(map(res => {
        console.log(res);
        return res;
      }));
  }
  scheduleInterview(job){
    return this.http.put(this.REST_API_URL + "/"+ job.jId +"/schedule",job)
      .pipe(map(res => {
        console.log(res);
        return res;
      }));

  }
  updateJob(jobData) {
    let _url = this.REST_API_URL + "/" + jobData.jId;
    console.log(jobData.jobId)
    let promise = new Promise((resolve, reject) => {
      this.http.put(_url, jobData)
        .toPromise()
        .then((res) => {            //3. Get the resp from rest api
          console.log(res);
          resolve(res);
        })
        .catch((err) => {           // Get the err from rest api
          console.log(err);
          reject(err);
        })
        .finally(() => {
          console.log("Ends...");
        });
    });
    return promise;
  }

  deleteJob(jobId){
    let _url=this.REST_API_URL + "/" + jobId;
    let promise = new Promise((resolve,reject) => {
      this.http.delete(_url)
      .toPromise()
      .then((res) => {            //3. Get the resp from rest api
        console.log(res);
        resolve(res);
      })
      .catch((err) => {           // Get the err from rest api
        console.log(err);
        reject(err);
      })
      .finally(() => {
        console.log("Ends...");
      });
  });
  return promise;
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    // const currentUser = this.authenticationService.currentUserValue;
    console.log("Inside job component")
    let currentUser = JSON.parse(localStorage.getItem('currentUser'));
    console.log(currentUser.authorities[0].authority);
  
    console.log(currentUser.role)
    if (currentUser.authorities[0].authority=="ROLE_HR") {
        // authorised so return true
        return true;
    }
    else
    {
      alert("You are already logged in as Candidate")
       this.router.navigate(['/login']);
       return false;
      }
  }

}