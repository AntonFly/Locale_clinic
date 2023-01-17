import { Injectable, Inject } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import { Client } from '../../_models/Client';
import { Order } from 'src/app/_models/Order';
import { map } from 'rxjs/operators';

@Injectable()
export class ScientistService {
  
  baseUrl:string;

  constructor(private http: HttpClient, @Inject('BASE_URL') baseUrl: string) { 
    this.baseUrl = baseUrl;                 
  }
     

//   getSupportBySpecializId(spec: string){
//     let body = "?name="+spec;
//     return this.http.get<Order[]>(this.baseUrl+"medic/get_scenarios_by_spec/"+body);
//   }

  getAll(){
    return this.http.get<Client[]>(this.baseUrl+"engineer/get_all_clients");
  }

  getClient(pass: number) {
    let body = "?passport=" + pass;
    return this.http.get<Client>(this.baseUrl+"scientist/get_client_by_passport/" + body);                
  }

  getOrdersByPassport(id: number) {
    let body = "?passport="+id;
    return this.http.get<Order[]>(this.baseUrl+"scientist/get_all_orders_by_passport/"+body);
  }

  registerScenario(spec, order, mods:number[])
  {
    var body = {"spec_id": spec, "order_id": order, "mods": mods}
    return this.http.post(this.baseUrl+'scientist/create_scenario', body)
  }

  updateScenario(scen_id, mods){
    var body = {"scenario_id":scen_id, "mods": mods}
    
    return this.http.put(this.baseUrl+'scientist/update_scenario', body)
  }

  uploadGenome(file: File, orderId){
    var formData = new FormData()
    formData.append('file', file)
    return this.http.post(this.baseUrl+'engineer/uploadGenome/'+orderId, formData);
}

downloadFile(url, filename:string){
    return this.http.get(
        this.baseUrl+url,
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