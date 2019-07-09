package com.util {
	
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.system.ApplicationDomain;
	
	import mx.events.ModuleEvent;
	import mx.modules.IModuleInfo;
	import mx.modules.ModuleLoader;
	import mx.modules.ModuleManager;
	
	
	/**
	 *Helper class for managing the loading and unloading of modules.
	 *  
	 * @author rterrell
	 * 
	 */	
	public class RMT2ModuleUtility {
		
		public static const UNLOAD_URL : String = "";
		
		private var parent : DisplayObjectContainer;
		
		
		/**
		 * Default constructor.
		 * 
		 */		
		public function RMT2ModuleUtility() {
			return;
		}
		
		
	   /**
         * Adds output to the log and/or debug console.
         **/
        protected function addToLog (msg : String)  :  void {
            trace(msg);
        }


		/**
		 * This method loads a module from across the network into memory cache but does not create an instance of the module.
		 * 
		 * @param url
		 *               The  location where the module swf file resides on the network
		 * @param parentObj
		 *                A dreived instance of DisplayObjectContainer that will manage the module within its display list.
		 * 
		 */		
		public function preloadModule(url : String, parentObj : DisplayObjectContainer) : void {
			this.parent = parentObj;
			var info : IModuleInfo = ModuleManager.getModule(url);
			info = ModuleManager.getModule(url);
			if (info.ready) {
				return;
			}
			info.addEventListener(ModuleEvent.READY, onModuleLoadReady);
			info.load();
		}
		
		
		/**
		 * Uses the ModuleLoader API to load a module into an application.
		 * 
		 * @param mod
		 *               An instance of the ModuleLoader that is to be loaded.
		 * @param newURL
		 *               The location of the .swf file representing the module to be loaded 
		 * @param container
		 *                The container UI Component that will maintain the loaded module within its display list.
		 * 
		 */					
		public function loadModule(mod : ModuleLoader, newURL : String, container : DisplayObjectContainer = null) : void {
			if (mod ==null) {
				return;
			}
			if (newURL ==null) {
				newURL = "";
			}
			// Abort if already loaded
			if (mod.url == newURL) {
				return;
			}
			this.parent = container;
			mod.addEventListener(ModuleEvent.READY, onModuleLoadReady);
			mod.loadModule(newURL);
		}
		
		
        /**
         * Uses the ModuleLoader API to unload a module from the application.
         * 
         * @param mod
         *               An instance of the ModuleLoader that is to be unloaded.
         * @param container
         *               The container UI Component whose display list contains a reference of the ModlueLoader to be unloaded.
         * 
         */            
        public function unloadRemoveModule(mod : ModuleLoader, container : DisplayObjectContainer) : void {
			this.parent = container;
			var info : IModuleInfo = ModuleManager.getModule(mod.url);
			info.addEventListener(ModuleEvent.UNLOAD, onModuleUnload);
			info.unload();
			mod.url = "";
		}


        /**
         * The handler for the READY module event.
         * 
         * @param e
         * 				An instance of ModuleEvent.
         */     
        private function onModuleLoadReady (e : ModuleEvent) : void {
        	var info : IModuleInfo = e.module;
            addToLog ("ModuleEvent.READY received for module: " + info.url);
            if (this.parent != null) {
            	this.parent.addChild(info.factory.create() as DisplayObject);	
            }
        }
                    
       /**
         * The handler for the UNLOAD module event.
         * 
         * @param e
         * 				An instance of ModuleEvent.
         */     
        public function onModuleUnload (e : ModuleEvent) : void {
        	var info : IModuleInfo = e.module;
        	addToLog ("ModuleEvent.UNLOAD received for module, " + info.url);
        } 
        
        
        /**
         * The handler for the PROGRESS module event.
         * 
         * @param e
         * 				An instance of ModuleEvent.
         */     
        private function onModuleProgress (e : ModuleEvent) : void {
        	var info : IModuleInfo = e.module;
            addToLog ("ModuleEvent.PROGRESS received for module " + info.url + ": " + e.bytesLoaded + " of " + e.bytesTotal + " loaded.");
        }
        
        /**
         * The handler for the SETUP module event.
         * 
         * @param e
         * 				An instance of ModuleEvent.
         */     
        private function onModuleSetup (e : ModuleEvent) : void {
            var moduleInfo : IModuleInfo = e.module;
            addToLog ("ModuleEvent.SETUP received for module: " + moduleInfo.url);
            addToLog ("Reporting info from IModuleInfo.factory");
            // grab the info and display information about it
            var info:Object = moduleInfo.factory.info();
            for (var ndx : String in info) {
                addToLog ("     " + ndx + " = " + info[ndx]);
            }
        }
       
            
	}
}