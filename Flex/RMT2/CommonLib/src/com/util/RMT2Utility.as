package com.util {
	
	import com.components.RMT2CheckBox;
	import com.components.list.RMT2SelectComboBox;
	
	import mx.controls.DateChooser;
	import mx.controls.DateField;
	import mx.controls.Label;
	import mx.controls.List;
	import mx.controls.NumericStepper;
	import mx.controls.RadioButtonGroup;
	import mx.controls.Text;
	import mx.controls.TextArea;
	import mx.controls.TextInput;
	import mx.formatters.PhoneFormatter;
	import mx.utils.StringUtil;
	
	
	public class RMT2Utility {
		
		public function RMT2Utility() {
			return;
		}
		
		
		/**
		 * Clears all properties of a given dynamic instance by assigning "" to value of each property.
		 * 
		 * @param map
		 */
		public static function clearProperties(map : Object) : int {
			var count : int = 0;
			for (var prop : String in map) {
				map[prop] = "";
				count++;
			}
			return count;
		}
		
		
		/**
		 * Populate a XML model with data from controls of a given component.  In order for this method 
		 * to work using introspection, the properties of <i>model</i> and <i>component</i> must contain 
		 * the same names.   
		 * <p>
		 * The following controls types are accounted for when gathering the data: Label, Text, TextInput, 
		 * TextArea, DateField, RadioButtonGroup, RMT2CheckBox, NumericStepper, DateChooser, 
		 * RMT2SelectComboBox, and List.
		 * 
		 * @param model
		 *         the MXML Model used to capture the component's data.
		 * @param component
		 *         the UI component that will serve as the source of data.
		 */
		public static function getFormData(model : Object, component : Object) : void {
			for (var prop : String in model) {
				var value : Object = model[prop];
				if (component.hasOwnProperty(prop)) {
					if (component[prop] is Label || component[prop] is Text ||component[prop] is TextInput || component[prop] is TextArea || component[prop] is DateField) {
						model[prop] = component[prop].text;	
					}
					else if (component[prop] is RadioButtonGroup) {
						model[prop] = component[prop].selectedValue;	
					}
					else if (component[prop] is RMT2CheckBox) {
						model[prop] = component[prop].value;	
					}
					else if (component[prop] is NumericStepper) {
						model[prop] = component[prop].value;	
					}
					else if (component[prop] is DateChooser) {
						var dt : Date = component[prop].selectedDate;
						if (dt == null) {
							continue;
						}
						var dtStr : String = dt.getFullYear() + "/" + (dt.getMonth() + 1) + "/" + dt.getDate();
						model[prop] = dtStr;
					}
					else if (component[prop] is RMT2SelectComboBox) {
						var val : String;
						var obj : Object = component[prop].selectedItem;
						if (obj is XML) {
							val = obj.child(component[prop].keyField);
						}
						else if (obj is Object) {
							val = obj[component[prop].keyField];
						}
						model[prop] = val;	
					}
					else if (component[prop] is List) {
						var data : Object = component[prop].selectedItems;
						var items : Array = new Array(data.length);
						for (var ndx : int = 0; ndx < data.length; ndx++) {
							items[ndx] = data[ndx].data;
						}
						model[prop] = items;		
					}					
				}
			}
			return;
		} 
		
		/**
		 * Copies the contents of one dynamic instance to another. In order for this method 
		 * to work using introspection, the properties of <i>dest</i> and <i>src</i> 
		 * must contain the same names.
		 * 
		 * @param src
		 *         the source of the copy operation.  This must be a dynamic instance.
		 * @param dest
		 *         the destination of the copy operation.  This must be a dynamic instance.
		 */
		public static function copyDynamObject(src : Object, dest : Object) : void {
			for (var prop : String in src) {
				var value : Object = src[prop];
				if (prop in dest) {
					dest[prop] = value;	
				}
			}
			return;
		}
		
		
		/**
		 *Capitalizes the first character of a given String value. 
		 * 
		 * @param src
		 */
		public static function wordCap(src : String) : String {
			var startChar : String = src.substr(0, 1);
			var remainChars : String = src.substr(1);
			startChar = startChar.toUpperCase();
			var results : String = startChar + remainChars;
			return results;
		}
		
		/**
		 * Strips various special characters from a phone number String.  The characters 
		 * targeted are: '-', '(', ')', '.', and ' '.
		 * 
		 * @param src
		 *          the phone number to strip.
		 * 
		 * @return String
		 *          the phone number without special characters or null if the phone number 
		 *          could not be formatted.
		 * @throws Error
		 *          when src is null;
		 */
		public static function formatPhoneNumber(src : String) : String {
			if (src == null) {
				var error : Error = new Error("Invalid or null phone number was found");
				throw error;
			}
			
			var exp : RegExp = new RegExp("[- ().]", "g");
			var result : String = src.replace(exp, "");
			var formatter : PhoneFormatter = new PhoneFormatter();
			formatter.formatString = "(###) ###-####";
			result = formatter.format(result);
			if (!result) {
				return src;
			}
			return result;
		}
		
		/**
		 * Determines if a String value is equal to all spaces.
		 * <p>
		 * 
		 * @parm src
		 *            The String to test
		 * @return Boolean
		 *              true when <i>src</i> is equal all spaces.  Returns false otherwise.
		 * @throws Error
		 *              When <i>src</i> is null or invalid.
		 */
		public static function isAllSpaces(src : String) : Boolean {
			if (src == null) {
				throw new Error("Unable to determined is source String is all spaces due an invalid or null value");
			}
			var max : int = src.length;
			var spaceCount : int = 0;
			
			for (var ndx : int = 0 ; ndx < max; ndx++)	{
			    if (StringUtil.isWhitespace(src.charAt(ndx))) {
			        spaceCount++;
			    }
			}
			return (max == spaceCount);
		}
		
		
		
	}
}