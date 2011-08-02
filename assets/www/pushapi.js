(function () {

	var global = window;
	
	var PushSource = global.PushSource = function (url) {
		// listener to overload
		this.url = url;
		this.onopen = null;
		this.onmessage = null;
		this.onerror = null;
		
		this._handler = PushSourceFactory.getNew(url);
		PushSource.registry[this._handler.getIdentifier()] = this;
		
		this.readyState = 1;
	};
	
	PushSource.registry = {};
	
	PushSource.triggerEvent = function (evt) {
		PushSource.__open
		PushSource.registry[evt.target]['on' + evt.type].call(global, evt);
	}
	
	PushSource.prototype.close = function () {
		this._handler.close();
		this.readyState = 2;
	}	

})();