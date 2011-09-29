var Event = {}
Event.addEvent = function() {
	var element = arguments[0];
	var evt     = arguments[1];
	var handler = arguments[2];
	var capture = arguments[3];

	if(typeof(handler) == "function"){
		if (element.addEventListener) {
			element.addEventListener(evt, handler, capture); 
		} else if (element.attachEvent) {
			element.attachEvent( "on" + evt, handler);
		}
	}else if(typeof(handler) == "object"){
		var obj     = arguments[2];
		var handler = arguments[3];
		var capture = arguments[4];

		if (element.addEventListener) {
			element.addEventListener(evt, Event.bindAsListener(obj,handler), capture); 
		} else if (element.attachEvent) {
			element.attachEvent( "on" + evt, Event.bindAsListener(obj,handler));
		}
	}
}

Event.removeEvent  = function(element, evt, handler, capture) {

	if (element.removeEventListener) { 
		element.removeEventListener(evt, handler, capture); 
	} else if (element.detachEvent) {
		element.detachEvent( "on" + evt, handler);
	}
}

Event.stopPropagation = function(event) { 
	if (event.stopPropagation) {
		event.stopPropagation();
	} else {
		event.cancelBubble = true;
	}
}

Event.preventDefault = function(event) {
	if (event.preventDefault) {
		event.preventDefault();
	} else {
		event.returnValue = false;
	}
}

Event.stopEvent = function(event) {
	Event.stopPropagation(event);
	Event.preventDefault(event);
}

Event.getTarget = function(event) {
	return event.srcElement || event.target;
}

window.onload = function() {
	var  btn = document.f.btn;
	new ButtonClass(btn);
}

Event.bindAsListener = function (obj, handler) {
	return function () { handler.apply(obj, arguments)}
}

ButtonClass = function(element) {
	this.element = element;

	var localThis = this;
	Event.addEvent(this.element, "click", this, this.jsClick);
}

ButtonClass.prototype.jsClick = function(e) {
	this.element.style.backgroundColor = 'blue'; 
}
