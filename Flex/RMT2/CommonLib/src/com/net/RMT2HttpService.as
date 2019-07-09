package com.net  {
	import mx.rpc.AsyncToken;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.http.mxml.HTTPService;

//import mx.rpc.http.HTTPService;

	/**
	 * 
	 * @author appdev
	 * 
	 */		
	public class RMT2HttpService extends HTTPService {
		
		/**
		 * 
		 */		
		public static const CONCUR_MULTIPLE : String = "multiple";
		
		/**
		 * 
		 */		
		public static const CONCUR_SINGLE : String = "single";
		
		/**
		 * 
		 */		
		public static const CONCUR_LAST : String = "last";
		
		private var results : Object;
		
		private var error : Boolean;
		
		private var complete : Boolean;
		
		private var resultHandler : Function;
		
		private var faultHandler : Function;
		
		
		/**
		 * 
		 * @param rootURL
		 * @param concur
		 * @param showBusy
		 * @param destination
		 * @param useProxy
		 * @param outFormat
		 * 
		 */	
		public function RMT2HttpService(rootURL : String = null, concur : String = CONCUR_MULTIPLE, showBusy : Boolean = true, destination : String = null, useProxy : Boolean = false, outFormat : String = "e4x") {
			super(rootURL, destination);
			this.resultFormat = outFormat;
			this.method = "POST";
			this.useProxy = useProxy;
			this.concurrency = concur;
			this.showBusyCursor = showBusy;
		}
		
		
		/**
		 * 
		 * @param url
		 * @param baseURL
		 * @param resultHandler
		 * @param faultHandler
		 * @return 
		 * 
		 */		
		public function setupURL(url : String,  baseURL : String = null, resultHandler : Function = null, faultHandler : Function = null) : String {
			// register event handlers implemented by the caller for the capture phase
			this.resultHandler = resultHandler;
			this.faultHandler = faultHandler;
			if (resultHandler != null) {
				this.addEventListener(ResultEvent.RESULT, resultHandler);	
			}
			if (faultHandler != null) {
				this.addEventListener(FaultEvent.FAULT, faultHandler);	
			}
			
			if (this.useProxy) {
				// Flash will use rootURL to compute the relative URL
				this.url = url;
			}
			else {
				// We will be responsible for computing the URL which is generally absoute.
				if (baseURL != null) {
					this.rootURL = baseURL;
				}
				this.url = (this.rootURL == null || this.rootURL == "" ? url : this.rootURL + url);	
			}
			return this.url;
		}
		
		
		/**
		 * 
		 * @return 
		 * 
		 */		
		public function isError() : Boolean {
			return this.error;
		}
		
		/**
		 * 
		 * @return 
		 * 
		 */		
		public function getResults() : Object {
			return this.results;
		}
		
		/**
		 * 
		 * @return 
		 * 
		 */		
		public function isComplete() : Boolean {
			return this.complete;
		}
		
		/**
		 * 
		 * 
		 */		
		public function close() : void {
			if (this.resultHandler != null) {
				this.removeEventListener(ResultEvent.RESULT, this.resultHandler, true);	
			}
			if (this.faultHandler != null) {
				this.removeEventListener(FaultEvent.FAULT, this.faultHandler, true);	
			}
			this.resultHandler = null;
			this.faultHandler = null;	
		}
		
		
		/**
		 * 
		 * @param parameters
		 * @return 
		 * 
		 */		
		override public function send(parameters : Object = null) : AsyncToken {
			this.error = false;
			this.complete = false;
			
			return super.send(parameters);
		}
		
		/**
		 * 
		 * @param e
		 * 
		 */		
		private function bindResultsListener(e : ResultEvent) : void {
			this.results = e.result;
			this.complete = true;
			this.error = false;
		}
		
		/**
		 * 
		 * @param e
		 * 
		 */		
		private function bindFaultListener(e : FaultEvent) : void {
			this.results = e.fault;
			this.complete = true;
			this.error = true
		}
		
		
	}
}