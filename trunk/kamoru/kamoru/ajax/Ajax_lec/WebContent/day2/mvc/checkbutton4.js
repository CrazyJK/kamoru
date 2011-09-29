//CheckButtonModel Ŭ������ �����Ѵ�.
CheckButtonModel = function(checked, label) {
  //���� ������ ����� ��� ���������� �뺸�ϱ� ���� ������ ��ü ����迭 
// this.listeners = new Array();
 this.listeners = [];
  this.checked = checked;
  this.label = label;
};

//Ŭ������ �޼ҵ带 �����Ѵ�.
CheckButtonModel.prototype = {
  //�������� ��� �����ʿ� ����Ѵ�.
  addListener: function(listener) {
    for (var i=0;i<this.listeners.length;i++) {
      //������ �������� ��ϵȾ��ٸ� �����Ѵ�.
      if (this.listeners[i] == listener) { 
        return ;
      }
    }
    this.listeners[this.listeners.length] = listener;          
  },                                                           
  removeListener: function(listener) {                         
    if (this.listeners.length == 0) return;                    
    
    //�ӽ� �迭 ��ü�� �����Ѵ�.
    var newListeners = [];                            
    for (var i = 0 ; i < this.listeners.length ; i++) {        
      //�迭�� �ִ� ������ ��ü�� ������ ����� �ƴϸ� �ӽ� �迭�� ����Ѵ�.
      if (this.listeners[i] != listener) {                     
        newListeners[newListeners.length] = this.listeners[i];                                   
      }                                                        
    }
    //�ӽ� �迭�� ��ϵ� �����ʸ� ������ ������ �����Ѵ�.
    this.listeners = newListeners;                             
  }, 
  
  //Model�� �ڷᰡ ����Ǿ��ٴ� ���� ���������� �뺸�Ѵ�.
  notify : function() {                                         
    for (var i = 0 ; i < this.listeners.length ; i++) {        
      this.listeners[i].changed(this);                         
    }                                                          
  }, 
  //Model�� check ���¸� �����ϴ� �޼ҵ� 
  setChecked: function(checked) {                              
    this.checked = checked;                                    
    this.notify();                                             
  }, 
  //Model�� ���� ���¸� ����Ѵ�
  toggle: function() {                                         
    if (this.checked) {                                        
      this.checked = false;                                    
    } else {                                                   
      this.checked = true;                                     
    }               
    //this.checked = !this.checked;
    this.notify();                                             
  },
  //Model�� check ���¸� ��´�.  
  isChecked: function() {                                      
    return this.checked;                                       
  },                                                           

  //Model�� ���̺��� ��´�.
  getLabel: function() {                                       
    return this.label;                                         
  }, 
  //Model�� ���̺��� ��´�.
  setLabel: function(label) {                                       
    this.label = label;                                         
    this.notify();                                             
  }   
}                                                              

//CheckButtonCtrl Ŭ������ �����Ѵ�.                                                               
CheckButtonCtrl = function(model, view) {                            
  this.model = model;                                          
  this.view = view;                                                
  
  //model�� controller�� ����ϴ� �κ�                                                            
  this.model.addListener(this);    
  //view�� controller�� ����ϴ� �κ� 
  this.view.setCheckButtonCtrl(this);                                

  //view�� UI ȭ���� �׸���.                                                                
  this.view.render ();                                            
} 

//ChekcButton �޼ҵ带 �����Ѵ�.
CheckButtonCtrl.prototype = {                                      
  setChecked: function(checked) {                              
    this.model.setChecked(checked);                            
  },                                                           
  toggle: function() {                                         
    this.model.toggle();                                       
  },                                                           
  isChecked: function() {                                      
    return this.model.isChecked();                             
  },                                                           
  getLabel: function() {                                       
    return this.model.getLabel();                              
  },                                                           
  changed: function() {                                        
    this.view.update();                                          
  }                                                            
}                                                              
                                
//                                                       
CheckButtonView = function(elementId) {                          
  this.element = document.getElementById(elementId);  
  this.ctrl = null;                                     
}  
                                                            
CheckButtonView.prototype = {                                    
  setCheckButtonCtrl : function(ctrl) {                      
    this.ctrl = ctrl;                            
  }, 
                                                            
  render  : function() {                                         
    var html = "<img src='";                                   
    if (this.ctrl.isChecked()) {                        
      html += "images/check_on.gif'>";                         
    } else {                                                   
      html += "images/check_off.gif'>";                        
    }                                                          
    html += "&nbsp; <span>"+this.ctrl.getLabel() + "</span>";              
    
    if (window.event) {                                                         
      this.element.style.cursor = 'hand';                        
    } else {
      this.element.style.cursor = 'pointer;hand';                        
    }                                                           
    this.element.innerHTML = html;                             
                                                               
    Event.addListener(this.element, "click", Event.bindAsListener(this, this.doClick));          
  },  
                                                           
  doClick: function(e) {                                       
    this.ctrl.toggle();                                 
  }, 
                                                            
  update: function() {                                         
    var img = this.element.getElementsByTagName("img")[0];
    if (this.ctrl.isChecked()) {                        
      img.src = "images/check_on.gif";                         
    } else {                                                   
      img.src = "images/check_off.gif";                        
    }
    var span = this.element.getElementsByTagName("span")[0];
    span.innerHTML = this.ctrl.getLabel();  
    
  }                                                            
}

//CheckButtonView2 Ŭ������ ���� �Ѵ�
CheckButtonView2 = function(elementId) {
  this.element = document.getElementById(elementId);
}

//CheckButtonView�� ���� ����� �޴´�.
CheckButtonView2.prototype = new CheckButtonView;
//���ڵ�� java�� extends �� ���� �ڵ��̴�
//class CheckButtonView2 extends CheckButtonView {
 

//*** �߿��� *** 
//����� �ϰ� �θ��� �޼ҵ带 �������� ���� 
//����� json ǥ����� ������� �ʰ� ���� �޼ҵ带
//�����ؾ� �Ѵ�.
//CheckButtonView2.prototype = {
//	render : function() {
//	},
//	
//	update : functin) {
//	}
//}
////Ŭ������ �޼ҵ带 �����Ѵ�.
CheckButtonView2.prototype.render = function() {
  var html = "<img src='";
  if (this.ctrl.isChecked()) {
    html += "images/check2_on.gif'>";
  } else {
    html += "images/check2_off.gif'>";
  }
  html += "&nbsp;"+this.ctrl.getLabel();
  
  this.element.style.cursor = 'hand';
  this.element.style.fontSize = "10px";
  this.element.innerHTML = html;
  
  Event.addListener(this.element, "click", Event.bindAsListener(this, this.doClick));
};

CheckButtonView2.prototype.update = function() {
  var img = this.element.getElementsByTagName("img").item(0);
  if (this.ctrl.isChecked()) {
    img.src = "images/check2_on.gif";
  } else {
    img.src = "images/check2_off.gif";
  }
};


CheckButton = function(elementId, label, checked) {
	this.model = new CheckButtonModel(checked, label);
	this.view = new CheckButtonView(elementId);
	this.ctrl = new CheckButtonCtrl(this.model, this.view);
};

CheckButton.prototype = {
	toggle : function() {
		this.model.toggle();
	} ,
	setLabel : function(label) {
		this.model.setLabel(label);
	}
}



