import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Customer } from "../models/customer.model";
import { environment } from "../environments/environment";

@Injectable({
    providedIn: "root"
})
export class CustomerService {
    private httpClient = inject(HttpClient);
    private baseUrl = `${environment.baseUrl}/customers`;

    public getAllCustomers() {
        return this.httpClient.get(this.baseUrl);
    }

    public updateCustomer(customer: Customer) {
        return this.httpClient.put(this.baseUrl, customer);
    }

    public createCustomer(customer: Customer) {
        return this.httpClient.post(this.baseUrl, customer);
    }

    public deleteCustomer(id: number) {
        return this.httpClient.delete(`${this.baseUrl}/${id}`);
    }

    public getCustomerProjects(id: number) {
      return this.httpClient.get(`${this.baseUrl}/${id}/projects`);
    }
}
