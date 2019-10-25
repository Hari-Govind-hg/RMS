import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { User } from '../candidate/CandidateService/models';

@Injectable({ providedIn: 'root' })
export class AuthenticationService {
    private currentUserSubject: BehaviorSubject<User>;
    public currentUser: Observable<User>;
    public loggedIn = new BehaviorSubject<boolean>(false);

    constructor(private http: HttpClient) {
        this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
        this.currentUser = this.currentUserSubject.asObservable();
    }

    public get currentUserValue(): User {
        return this.currentUserSubject.value;
    }

    Candidatelogin(username: string, password: string) {
        const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(username + ':' + password) });
        let credentials=btoa(username + ':' + password)
        return this.http.post<User>(`http://localhost:80/authenticate`, { username, password },{headers})
            .pipe(map(user => {
                // login successful if there's a jwt token in the response
                if (user) {
                    let userObj = JSON.parse(JSON.stringify(user));
                    console.log(userObj)
                    if(userObj.authorities[0].authority==="ROLE_CANDIDATE")
                    {
                                        // store user details and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser', JSON.stringify(user));
                    let temp = JSON.parse(localStorage.getItem('currentUser'));
                    
                    console.log("Making localstorage...")
                    this.currentUserSubject.next(user);
                    this.loggedIn.next(true);
                }
                }

                return user;
            }));
    }


    Hrlogin(username: string, password: string) {
        const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(username + ':' + password) });
        return this.http.post<User>(`http://localhost:80/authenticate`, { username, password },{headers})
            .pipe(map(user => {
                // login successful if there's a jwt token in the response
                if (user) {
                    let userObj = JSON.parse(JSON.stringify(user));
                    console.log(userObj)
                    if(userObj.authorities[0].authority==="ROLE_HR")
                    {
                    console.log(user)
                    // store user details and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser', JSON.stringify(user));
                    console.log("Making localstorage...")
                    this.currentUserSubject.next(user);
                    this.loggedIn.next(true);
                }
                else
                {
                    
                }
            }
                return user;
            }));
    }




    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentUser');
        this.currentUserSubject.next(null);
        this.loggedIn.next(false);
    }
}