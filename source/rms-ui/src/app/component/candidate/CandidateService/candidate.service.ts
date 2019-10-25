import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CandidateService {
  REST_API_URL: string = "http://localhost:80/candidates";
  constructor(private http: HttpClient) { 

  }

  createCandidate(candidateData: any) {
    // 1. get the data from the component
    console.log(candidateData);

    //2.Send the above data t rest API
    //2.1identify the rest api url
    //2.2 send the data using POST method via REST API Client
    let promise = new Promise((resolve, reject) => {
      this.http.post(this.REST_API_URL, candidateData)
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

  getAllJobsCandidate() {
    return this.http.get(this.REST_API_URL+"/applynewjob")
      .pipe(map(res => {  //3.get res from rest api
        console.log(res);
        return res;     //Send it back to component
      }));
  }

  getJobsAppliedByCandidate(id) {
    console.log("id is " + id);
    return this.http.get(this.REST_API_URL + "/" + id + "/appliedjobs")
      .pipe(map(res => {
        console.log(res);
        return res;
      }));
  }

  getJobsBySkillCandidate(id) {
    console.log("id is " + id);
    return this.http.get(this.REST_API_URL + "/" + id + "/applynewjobbypreference")
      .pipe(map(res => {
        console.log(res);
        return res;
      }));
  }

  applyForJobCandidate(id,jid){
    console.log("id is " + id);
    console.log("id is " + jid);
    return this.http.get(this.REST_API_URL + "/" + id + "/applyjob?jid" + jid)
      .pipe(map(res => {
        console.log(res);
        return res;
      }));
  }
}