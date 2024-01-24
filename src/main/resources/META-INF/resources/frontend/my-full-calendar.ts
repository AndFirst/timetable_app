import {FullCalendar} from './vaadin-full-calendar/full-calendar.ts';

// LLR_110, LLR_120
export class MyFullCalendar extends FullCalendar {
    createInitOptions() {
        var options = super.createInitOptions();
        options.eventContent = function(arg) {
            console.log(arg);

            let containerEl = document.createElement('div');

            // Time
            let timeEl = document.createElement('div');
            timeEl.classList.add('fc-event-time');
            timeEl.innerText = arg.timeText;
            containerEl.appendChild(timeEl);

            // Title
            let titleEl = document.createElement('div');
            titleEl.innerText = arg.event.title;

            // Type
            let typeString = arg.event.extendedProps.customProperties.type;
            if (typeString) {
                titleEl.innerText += ` - ${typeString}`;
            }
            containerEl.appendChild(titleEl);

            // Description
            let descriptionEl = document.createElement('div');
            descriptionEl.innerText = arg.event.extendedProps.customProperties.description;
            containerEl.appendChild(descriptionEl);

            // Location and teacher
            let locationEl = document.createElement('div');
            locationEl.innerText = arg.event.extendedProps.customProperties.location;
            containerEl.appendChild(locationEl);

            let teacherEl = document.createElement('div');
            teacherEl.innerText = arg.event.extendedProps.customProperties.teacher;
            containerEl.appendChild(teacherEl);

            let arrayOfDomNodes = [containerEl];
            return { domNodes: arrayOfDomNodes };
        }
        return options;
    }
}

customElements.define('my-full-calendar', MyFullCalendar);