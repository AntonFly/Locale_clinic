import { Output, Directive, HostBinding, HostListener, EventEmitter } from '@angular/core';
import { concat } from 'rxjs';

@Directive({
  selector: '[appDnd]'
})
export class DndDirective
{

  @HostBinding('class.fileover') fileOver: boolean;
  @Output() fileDropped = new EventEmitter<any>();

  constructor() { }

  @HostListener('dragover', ['$event']) onDragOver(evt)
  {
    evt.preventDefault();
    evt.stopPropagation();
    this.fileOver = true;

  }

  @HostListener('dragleave', ['$event']) onDragLeave(evt)
  {
    evt.preventDefault();
    evt.stopPropagation();
    this.fileOver = false;
  }

  @HostListener('drop', ['$event']) ondrop(evt)
  {
    evt.preventDefault();
    evt.stopPropagation();
    this.fileOver = false;
    const files = evt.dataTransfer.files;
    if (files.length > 0) 
      this.fileDropped.emit(files);    
  }

}
