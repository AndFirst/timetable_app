import {html} from '@polymer/polymer/polymer-element.js';
import {FullCalendar} from './vaadin-full-calendar/full-calendar.ts';

export class MyFullCalendar extends FullCalendar {
    createInitOptions() {
        var options = super.createInitOptions();
        options.eventContent = function(arg) {
            console.log(arg.event);

            let containerEl = document.createElement('div');

            // Time
            let timeEl = document.createElement('div');
            timeEl.classList.add('fc-event-time');
            timeEl.textContent = arg.event.start.toLocaleTimeString(['pl-PL'], {timeStyle: 'short'}) + ' - ' + arg.event.end.toLocaleTimeString(['pl-PL'], {timeStyle: 'short'});
            containerEl.appendChild(timeEl);

            // Title
            let titleEl = document.createElement('p');
            titleEl.textContent = arg.event.title;
            containerEl.appendChild(titleEl);

            // Description
            let descriptionEl = document.createElement('p');
            descriptionEl.textContent = arg.event.extendedProps.customProperties.description;
            containerEl.appendChild(descriptionEl);

            // Location and teacher
            let locationEl = document.createElement('p');
            locationEl.innerHTML = 'Location: <span>' + arg.event.extendedProps.customProperties.location + '</span><br>Teacher: <span>' + arg.event.extendedProps.customProperties.teacher + '</span>';
            containerEl.appendChild(locationEl);

            let arrayOfDomNodes = [containerEl];
            return { domNodes: arrayOfDomNodes };
        }
        return options;
    }
}

customElements.define('my-full-calendar', MyFullCalendar);