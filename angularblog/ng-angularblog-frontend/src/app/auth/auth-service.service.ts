import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {RegisterPayload} from './register-payload';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {
  private url = 'http://localhost:8080/';
  // private httpClient: any;

  constructor(private httpClient: HttpClient) { }
  register(registerPayload: RegisterPayload): Observable<any> {
   return this.httpClient.post(this.url + 'signup', registerPayload );
  }


}
