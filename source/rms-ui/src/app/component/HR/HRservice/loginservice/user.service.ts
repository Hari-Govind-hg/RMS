import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { User } from '../models';

@Injectable({ providedIn: 'root' })
export class UserService {
    constructor(private http: HttpClient) { }

    getAll() {
        return this.http.get<User[]>(`/users`);
    }

    register(user: User) {
        user.role="HR"
        
        return this.http.post("http://localhost:80/register", user);
    }

    delete(id: number) {
        return this.http.delete(`/users/${id}`);
    }
}