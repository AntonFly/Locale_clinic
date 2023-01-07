import { Directive, HostBinding, HostListener } from '@angular/core';

@Directive({
  selector: '[appAnimation]'
})
export class AnimationDirective {
  @HostBinding("class.refresh-list-button-animation") isAnimated: boolean;
  constructor() { }
  @HostListener('click', ['$event']) onClick(event) {
    this.isAnimated = true;
    setTimeout(() => { this.isAnimated = false }, 1100)
  } 
}
