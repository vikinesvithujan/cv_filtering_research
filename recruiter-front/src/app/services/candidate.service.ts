import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CandidateService {

  URL = environment.BACK_SERVER + "api/v1/candidate";
  
  constructor(private http: HttpClient) { }

  addCandidate(candidate: any): Observable<any> {
    return this.http.post<any>(this.URL, candidate);
  }

  getCandidateList(): Observable<any> {
    return this.http.get<any>(this.URL);
  }

  getCandidateByID(candidateId: string): Observable<any> {
    return this.http.get<any>(this.URL + '/' + candidateId);
  } 

  getCandidateBySkill(data: any): Observable<any> {
    return this.http.post<any>(this.URL + '/skills', data);
  } 
}
