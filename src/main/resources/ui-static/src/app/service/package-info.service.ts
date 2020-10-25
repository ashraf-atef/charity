import {Injectable} from '@angular/core';
import {PackageInfo} from "../domain/packageInfo";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PackageInfoService {

  constructor(private httpClient: HttpClient) {
  }

  save(packageInfo: PackageInfo): Observable<PackageInfo> {
    return this.httpClient.post<PackageInfo>("/api/package-info", packageInfo);
  }
}
