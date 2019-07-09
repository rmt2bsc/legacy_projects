package com.valueobject {
	
	import mx.collections.ArrayCollection;
	
	/**
	 * Tracks detail information about the user's single sign on state.
	 */ 
	public class UserTokenVO {
		
		private var _loginId : String;
		
		private var _firstName : String;
		
		private var _lastName : String;
		
		private var _roles : ArrayCollection;
		
		private var _loginRequired : Boolean;
		
		
		
		public function UserTokenVO(loginId : String) : void {
			this._loginId = loginId;
		}
		
		public function get loginId() : String {
			return this._loginId;
		}
		
		public function set loginId(value : String) : void {
			this._loginId = value;
		}
		
		public function get firstName() : String {
			return this._firstName;
		}
		
		public function set firstName(value : String) : void {
			this._firstName = value;
		}
		
		public function get lastName() : String {
			return this._lastName;
		}
		
		public function set lastName(value : String) : void {
			this._lastName = value;
		}
		
		public function get roles() : ArrayCollection {
			if (this._roles == null) {
				this._roles = new ArrayCollection();	
			}
			return this._roles;
		}
		
		public function set roles(values : ArrayCollection) {
			this._roles = values;	
		}
		
		public function get loginRequired () : Boolean {
			return  this._loginRequired
		}		
		
		public function set loginRequired (flag : Boolean) : void {
			this._loginRequired = flag;
		}	
	}
}