import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { User } from './models';
import { Candidate } from '../CandidateService/models/candidate';
import { Subscription } from 'rxjs';
import { AuthenticationService } from './loginservice';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class CandidateService {
  currentUser:User;
  candidateSubscription:Subscription;
  currentUserSubscription: Subscription;
  dataList:any;
  REST_API_URL_DETAILS: string = "http://localhost:80/candidates/detail";
  REST_API_URL: string = "http://localhost:80/candidates";
  constructor(private http: HttpClient,private authenticationService: AuthenticationService,private router:Router) { 
  //   this.currentUserSubscription = this.authenticationService.currentUser.subscribe(user => {
  //     this.currentUser = user;
  // });  
  

  }

  createCandidate(candidateData: any) {
    // 1. get the data from the component
   
    let currentUser = JSON.parse(localStorage.getItem('currentUser'));
    
    candidateData.username=currentUser.principal.username;
    

    //2.Send the above data t rest API
    //2.1identify the rest api url
    //2.2 send the data using POST method via REST API Client
    let promise = new Promise((resolve, reject) => {
      this.http.post(this.REST_API_URL, candidateData)
        .toPromise()
        .then((res) => {            //3. Get the resp from rest api
         
          resolve(res);
        })
        .catch((err) => {           // Get the err from rest api
         
          reject(err);
        })
        .finally(() => {
          
        });

    });
    return promise;
    //4: Return response from server
  }

  getAllJobsCandidate() {
    return this.http.get(this.REST_API_URL+"/applynewjob")
      .pipe(map(res => {  //3.get res from rest api
        return res;     //Send it back to component
      }));
  }

  getJobsAppliedByCandidate(id) {
    
    return this.http.get(this.REST_API_URL + "/" + id + "/appliedjobs")
      .pipe(map(res => {
        return res;
      }));
  }

  getJobsBySkillCandidate(id) {
    
    return this.http.get(this.REST_API_URL + "/" + id + "/applynewjobbypreference")
      .pipe(map(res => {
        
        return res;
      }));
  }
  updateCandidate(candidateData){
      let _url = this.REST_API_URL + "/" + candidateData.cId +"/profile";
     
      let promise = new Promise((resolve, reject) => {
        this.http.put(_url, candidateData)
          .toPromise()
          .then((res) => {            //3. Get the resp from rest api
            
            resolve(res);
          })
          .catch((err) => {           // Get the err from rest api
            
            reject(err);
          })
          .finally(() => {
            
          });
      });
      return promise;
    }

  
  getCandidate(username){
  
    let name=username;
    let promise = new Promise((resolve, reject) => {
    let temp = this.http.post(this.REST_API_URL_DETAILS,name)
    .toPromise()
    .then((res) => {            //3. Get the resp from rest api
      
      resolve(res);
    })
    .catch((err) => {           // Get the err from rest api
      
      reject(err);
    })
    .finally(() => {
      
    });
  });
    return promise;
    }

  applyForJobCandidate(id,jid){
    
    return this.http.get(this.REST_API_URL + "/" + id + "/applyjob?jid="+jid)
      .pipe(map(res => { 
        return res;
      }));
  }


  
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    // const currentUser = this.authenticationService.currentUserValue;
    let currentUser = JSON.parse(localStorage.getItem('currentUser'));
    
    if (currentUser.authorities[0].authority=="ROLE_CANDIDATE") {
        // authorised so return true
        return true;
    }
    else
    {
      alert("You are already logged in as HR")
       this.router.navigate(['/login']);
       return false;
      }
  }
  
}
