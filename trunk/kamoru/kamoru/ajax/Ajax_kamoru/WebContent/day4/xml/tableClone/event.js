
/*Event 클래스 생성*/ 
var Event = {};

/*이벤트 핸들러 추가
element : 이벤트 발생 객체 
eventName : 이벤트명 (IE에서도 on을 제거한다 예:"click")
handler : 이벤트 처리 핸들러 
capture : 이벤트 캡처  
*/ 
Event.addListener = function (element, eventName, handler, capture) {
  //capture 인자를 생략하면 기본 값으로 false를 설정한다.
  capture = capture || false;
  
  if (element.attachEvent) {
    element.attachEvent("on" + eventName, handler);
  } else if (element.addEventListener) {
    element.addEventListener(eventName, handler, capture);
  }
} 

/*이벤트 핸들러 제거 
element : 이벤트 발생 객체 
eventName : 이벤트명 (IE에서도 on을 제거한다 예:"click")
handler : 이벤트 처리 핸들러 
capture : 이벤트 캡처  
*/ 
Event.removeListener = function(element, eventName, handler, capture) {
  //capture 인자를 생략하면 기본 값으로 false를 설정한다.
  capture = capture || false;
  
  if (element.deattachEvent) {
    element.deattachEvent("on" + eventName, handler);
  } else if (element.removeEventListener) {
    element.removeEventListener(eventName, handler, capture);
  }
}  

Event.getMousePos = function(event) {
  var mouseX = event.clientX;
  var mouseY = event.clientY;
  
  if (document.documentElement) {
    mouseX += document.documentElement.scrollLeft;
    mouseY += document.documentElement.scrollTop;
  } else if(document.body) {
    mouseX += document.body.scrollLeft;
    mouseY += document.body.scrollTop;
  }
  
  //JSON 표기법을 이용하여 값을 리턴한다.
  return {x : mouseX, y : mouseY};
}

Event.isLeftButton = function(event) {
  
  if (event.which) {
    //파이어 폭스 브라우저 
    return (event.which == 1 && event.button == 0);
  } else {
    //IE 웹브라우저 
    if (event.type == "click") {
       //click 이벤트 발생일 경우 
       return event.button == 0;
    } else {
       //mousedown, mouseup 이벤트 발생일 경우 
       return event.button == 1;
 	}
  }
  return false;
}

Event.isRightButton = function(event) {
  return event.button == 2;
}

//////////////////////////////////////////////

//이벤트 발생 객체를 얻는다.
Event.target = function(event) {
  if (event.target) {
    return event.target;
  } else if (event.srcElement) {
    return event.srcElement;
  }
  return null;
}


//이벤트 전파을 중지한다.
Event.stopPropagation = function (event) {
  if (event.stopPropagation) {
    event.stopPropagation();
  } else {
    event.cancelBubble = true;
  }
}

//이벤트에 의해 기본적으로 처리되는 작업을 중지한다
Event.preventDefault = function (event) {
  if (event.preventDefault) {
    event.preventDefault();
  } else {
    event.defaultValue = false;
  }
}

//이벤트를 현재위치에서 멈춘다
Event.stopEvent = function(event) {
  Event.stopPropagation(event);
  Event.preventDefault(event);
}


Event.isEditingKey = function(event) {
    switch (event.keyCode) {
      case 8:  // backspace
      case 9:  // tab key
      case 37: // arrow left
      case 39: // arrow right 
      case 46: // del key 
        return true;
    }
    return false;
  }

 Event.isDigit10 = function(event) {
    //편집용 키이면 리턴한다.
    if (Event.isEditingKey(event) == true) return true;
    
    switch (event.keyCode) {
      case 0x30: // 0
      case 0x31: // 1
      case 0x32: // 2 
      case 0x33: // 3 
      case 0x34: // 4 
      case 0x35: // 5 
      case 0x36: // 6 
      case 0x37: // 7 
      case 0x38: // 8 
      case 0x39: // 9 
        return true;
    }
    return false;
  }

Event.isDigit16 = function(event) {
    //10진수 입력이면 리턴한다.
    if (Event.isDigit10(event) == true) return true;
    
    switch (event.keyCode) {
      case 0x41://A
      case 0x42://B
      case 0x43://C
      case 0x44://D
      case 0x45://E
      case 0x61://a
      case 0x62://b
      case 0x63://c
      case 0x64://d
      case 0x65://e
      return true;
    }
    return false;
  }

//이벤트 핸들러에서 클래스 메소드를 호출할 수 있게 해주는 함수
 Event.bindAsListener = function (obj, handler) {
  return function() {
    handler.apply(obj, arguments);
  }
 }
 