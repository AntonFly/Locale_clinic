import { Injectable, Inject } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import { Client } from '../../_models/Client';
import { Order } from 'src/app/_models/Order';
import { map } from 'rxjs/operators';

@Injectable()
export class EngineerService {
  
  baseUrl:string;

  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string) { 
    this.baseUrl = baseUrl;                 
  }
     

//   getSupportBySpecializId(spec: string){
//     let body = "?name="+spec;
//     return this.http.get<Order[]>(this.baseUrl+"medic/get_scenarios_by_spec/"+body);
//   }

  getClient(pass: number) {
    let body = "?passport=" + pass;
    return this.http.get<Client>(this.baseUrl+"engineer/get_client_by_passport/" + body);                
  }

  getOrdersByPassport(id: number) {
    let body = "?passport="+id;
    return this.http.get<Order[]>(this.baseUrl+"engineer/get_orders_by_client_passport/"+body);
  }

  uploadGenome(file: File, orderId){
    var formData = new FormData()
    formData.append('file', file)
    return this.http.post(this.baseUrl+'engineer/uploadGenome/'+orderId, formData);
}

downloadGenome(oldName: string, filename:string){
    return this.http.get(
        this.baseUrl+'engineer/downloadGenome/?file='+oldName,
    { responseType: 'blob' as 'json' }
    )
      .pipe(map(
        (response: any) =>
        {
          if (response)
          {            
            let dataType = response.type;

            let binaryData = [];
            binaryData.push(response);

            let downloadLink = document.createElement('a');
            downloadLink.href = window.URL.createObjectURL(new Blob(binaryData, { type: dataType }));

            if (filename)
              downloadLink.setAttribute('download', filename);
            document.body.appendChild(downloadLink);
            downloadLink.click();

            downloadLink.parentNode.removeChild(downloadLink);

            return response;
          }
        },
        err => {console.log("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR")}
      ));
}
}