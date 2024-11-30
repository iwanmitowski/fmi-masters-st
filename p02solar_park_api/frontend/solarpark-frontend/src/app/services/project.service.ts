import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Project } from "../models/project.model";
import { environment } from "../environments/environment";

@Injectable({
    providedIn: "root"
})
export class ProjectService {
    private httpClient = inject(HttpClient);
    private baseUrl = `${environment.baseUrl}/customers`;

    public getAllProjects() {
        return this.httpClient.get(this.baseUrl);
    }

    public updateProject(project: Project) {
        return this.httpClient.put(this.baseUrl, project);
    }

    public createProject(project: Project) {
        return this.httpClient.post(this.baseUrl, project);
    }

    public deleteProject(id: number) {
        return this.httpClient.delete(`${this.baseUrl}/${id}`);
    }
}
