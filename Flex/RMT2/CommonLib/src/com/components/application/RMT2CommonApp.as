package com.components.application {
	 
	import flash.events.Event;
	import flash.utils.Dictionary;
	
	import mx.core.Application;
	import mx.core.UIComponent;
	import mx.resources.IResourceManager;
	import mx.resources.ResourceManager;
	


	
	
	[ResourceBundle("CommonConfig")]
	
	/**
	 * 
	 * @author appdev
	 * 
	 */	
	public class RMT2CommonApp extends UIComponent	{
		
		/**
		 * 
		 */		
		public static const CONFIG_COMMON : String = "CommonConfig";
		
				
		protected var r : IResourceManager;
		
		protected var appParms : Dictionary;

		/**
		 * 
		 * 
		 */		
		public function RMT2CommonApp() {
			super();
			this.r = ResourceManager.getInstance();
            this.addEventListener("creationComplete", createCompletListener);  
		}
		
		/**
		 * 
		 * @param e
		 * 
		 */		
		public  function createCompletListener(e : Event) : void {
			this.fetchApplicationParms();	
		}
		
		private function fetchApplicationParms() : void {
			this.appParms = new Dictionary();
			
			for (var key : String in Application.application.parameters) {
				var val : String =  Application.application.parameters[key];
				this.appParms[key] = val;
			}
		}
		
		/**
		 * Obtains the value of the input parameter name stored in the appParms Dictionary collection.
		 * 
		 * @parm name
		 *               the name of the parameter to retrieve value
		 * @return String
		 *               the value of <i>name</i> or null if <i>name</i> does not exist
		 */ 
		public function getParm(name : String) : String {
			var val : String = this.appParms[name];
			return val;
		}
		
	}
}