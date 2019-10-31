import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class contactServiceService {
  REST_API_URL: string = "http://localhost:80/contacts";
  constructor(private http: HttpClient) {

  }

  createContact(contactData:any) {
    // 1. get the data from the component
    console.log(contactData);

    //2.Send the above data t rest API
    //2.1identify the rest api url
    //2.2 send the data using POST method via REST API Client
    let promise = new Promise((resolve, reject) => {
      this.http.post(this.REST_API_URL, contactData)
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
  getContacts() {
    return this.http.get(this.REST_API_URL)
      .pipe(map(res => {  //3.get res from rest api
        //console.log(res);
        return res;     //Send it back to component
      }));
  }

  

  deleteContact(id){
    console.log(id);
    let _url=this.REST_API_URL + "/" + id;
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
}