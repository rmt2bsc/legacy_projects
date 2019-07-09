package com.components.application {
	import com.components.security.CommonSecurityEvent;
	import com.valueobject.UserTokenVO;
	
	
	public class SingleSignOnCheckEvent extends CommonSecurityEvent 	{
		
		public static const EVENT_NAME : String = "verifyUserSingleSignOn";
		
		public function SingleSignOnCheckEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) 	{
			super(type, bubbles, cancelable);
		}

	}
}