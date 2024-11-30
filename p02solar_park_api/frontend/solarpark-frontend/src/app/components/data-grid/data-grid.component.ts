import { Component, EventEmitter, Input, Output } from "@angular/core";

export type DataGridHeader = {
  column: string,
  value: string,
}

@Component({
  standalone: true,
  selector: 'cc-data-grid',
  templateUrl: './data-grid.component.html',
  styleUrl: './data-grid.component.css'
})

export class DataGridComponent {
  @Input() public inputHeaderConfig: DataGridHeader[] = [];
  @Input() public inputDataSource: any;
  @Input() public isNavigatable = false;
  @Input() public isEditable  = false;
  @Input() public isRemovable  = false;

  @Output() public onNavigate =new EventEmitter();
  @Output() public onEdit = new EventEmitter();
  @Output() public onRemove = new EventEmitter();
}
