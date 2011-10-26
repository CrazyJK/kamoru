//CheckButtonModel 클래스를 정의한다.
CheckButtonModel = function(checked, id, label, rank, size) {
	//상태 정보가 변경될 경우 옵저버에게 통보하기 위한 리스너 객체 저장배열
	this.listeners = [];
	this.checked   = checked;
	this.id        = id;
	this.label     = name;
	this.rank      = rank;
	this.size      = size;
};

//클래스의 메소드를 정의한다.
CheckButtonModel.prototype = {
	//옵저버를 등록 리스너에 등록한다.
	addListener: function(listener) {
    	for (var i=0;i<this.listeners.length;i++) {
    		//이미 옵저버가 등록된었다면 리턴한다.
    		if (this.listeners[i] == listener) {
    			return ;
    		}
    	}
    	this.listeners[this.listeners.length] = listener;
  	},
  	removeListener: function(listener) {
  		if (this.listeners.length == 0) return;

  		//임시 배열 객체를 생성한다.
  		var newListeners = [];
  		for (var i = 0 ; i < this.listeners.length ; i++) {
  				//배열에 있는 옵저버 객체가 삭제할 대상이 아니면 임시 배열에 등록한다.
  			if (this.listeners[i] != listener) {
  				newListeners[newListeners.length] = this.listeners[i];
  			}
  		}
  		//임시 배열에 등록된 리스너를 리스너 변수에 대입한다.
  		this.listeners = newListeners;
  	},
  	//Model의 자료가 변경되었다는 것을 옵저버에게 통보한다.
  	notify : function() {
  		for (var i = 0 ; i < this.listeners.length ; i++) {
  			this.listeners[i].changed(this);
  		}
  	},
  	//Model의 check 상태를 변경하는 메소드
  	setChecked: function(checked) {
  		this.checked = checked;
  		this.notify();
  	},
  	//Model의 현재 상태를 토글한다
  	toggle: function() {
  		if (this.checked) {
  			this.checked = false;
  		} else {
  			this.checked = true;
  		}
  		//this.checked = !this.checked;
  		this.notify();
  	},
  	//Model의 check 상태를 얻는다.
  	isChecked: function() {
  		return this.checked;
  	},
  	//Model의 레이블을 얻는다.
  	getLabel: function() {
  		return this.label;
  	},
  	//Model의 ID을 얻는다.
  	getID: function() {
  		return this.id;
  	},
  	//Model의 레이블을 얻는다.
  		setLabel: function(label) {
  		this.label = label;
  		this.notify();
  	}
}

//CheckButtonCtrl 클래스를 정의한다.
CheckButtonCtrl = function(model, view) {
	this.model = model;
	this.view = view;

	//model에 controller를 등록하는 부분
	this.model.addListener(this);
	//view에 controller를 등록하는 부분
	this.view.setCheckButtonCtrl(this);

	//view의 UI 화면을 그린다.
	this.view.render ();
}

//ChekcButton 메소드를 정의한다.
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
  		var html = "<span id=' + this.c + ' src='";
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
  		this.element.innerHTML += html;

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

CheckButton = function(elementId, checked, id, label, rank, size) {
	this.model = new CheckButtonModel(checked, id, label, rank, size);
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



