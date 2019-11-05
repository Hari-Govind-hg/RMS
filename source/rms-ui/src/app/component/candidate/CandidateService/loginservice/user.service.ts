import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { User } from '../models';
import { concat } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class UserService {
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<User[]>(`/users`);
    }

    register(user: User) {
        console.log("Inside the register()")
        console.log(user)
        user.role="CANDIDATE"
        user.answer=user.questionList.concat(user.answer)
        console.log(user.answer);
        return this.http.post("http://localhost:80/register", user);
    }

    delete(id: number) {
        return this.http.delete(`/users/${id}`);
    }

    forgotPassword(user:User)
    {
        console.log("Inside fp")
        user.answer=user.questionList.concat(user.answer)
        return this.http.post("http://localhost:80/forgotpassword", user);
       
    }

    resetPassword(user:User,uname:string)
    {
        console.log("Inside rp")
        user.username=uname;
        console.log(user);
        return this.http.put("http://localhost:80/resetpassword", user);
  
    }
}