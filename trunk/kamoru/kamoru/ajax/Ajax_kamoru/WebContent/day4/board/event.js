
/*Event Ŭ���� ��*/ 
var Event = {};

/*�̺�Ʈ �ڵ鷯 �߰�
element : �̺�Ʈ �߻� ��ü 
eventName : �̺�Ʈ�� (IE������ on; f���Ѵ� ��:"click")
handler : �̺�Ʈ ó�� �ڵ鷯 
capture : �̺�Ʈ ĸó  
*/ 
Event.addListener = function (element, eventName, handler, capture) {
  //capture ���ڸ� ���ϸ� �⺻ ��8�� false�� ��d�Ѵ�.
  capture = capture || false;
  
  if (element.attachEvent) {
    element.attachEvent("on" + eventName, handler);
  } else if (element.addEventListener) {
    element.addEventListener(eventName, handler, capture);
  }
} 

/*�̺�Ʈ �ڵ鷯 f�� 
element : �̺�Ʈ �߻� ��ü 
eventName : �̺�Ʈ�� (IE������ on; f���Ѵ� ��:"click")
handler : �̺�Ʈ ó�� �ڵ鷯 
capture : �̺�Ʈ ĸó  
*/ 
Event.removeListener = function(element, eventName, handler, capture) {
  //capture ���ڸ� ���ϸ� �⺻ ��8�� false�� ��d�Ѵ�.
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
  
  //JSON ǥ���; �̿��Ͽ� ��; �����Ѵ�.
  return {x : mouseX, y : mouseY};
}

Event.isLeftButton = function(event) {
  
  if (event.which) {
    //���̾� �� ������ 
    return (event.which == 1 && event.button == 0);
  } else {
    //IE %������ 
    if (event.type == "click") {
       //click �̺�Ʈ �߻��� ��� 
       return event.button == 0;
    } else {
       //mousedown, mouseup �̺�Ʈ �߻��� ��� 
       return event.button == 1;
 	}
  }
  return false;
}

Event.isRightButton = function(event) {
  return event.button == 2;
}

//////////////////////////////////////////////

//�̺�Ʈ �߻� ��ü�� ��´�.
Event.target = function(event) {
  if (event.target) {
    return event.target;
  } else if (event.srcElement) {
    return event.srcElement;
  }
  return null;
}


//�̺�Ʈ ����; �����Ѵ�.
Event.stopPropagation = function (event) {
  if (event.stopPropagation) {
    event.stopPropagation();
  } else {
    event.cancelBubble = true;
  }
}

//�̺�Ʈ�� ���� �⺻��8�� ó���Ǵ� �۾�; �����Ѵ�
Event.preventDefault = function (event) {
  if (event.preventDefault) {
    event.preventDefault();
  } else {
    event.defaultValue = false;
  }
}

//�̺�Ʈ�� ����'ġ���� �����
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
    //����� Ű�̸� �����Ѵ�.
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
    //10��� �Է��̸� �����Ѵ�.
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

//�̺�Ʈ �ڵ鷯���� Ŭ���� �޼ҵ带 ȣ���� �� �ְ� ���ִ� �Լ�
 Event.bindAsListener = function (obj, handler) {
  return function() {
    handler.apply(obj, arguments);
  }
 }
 