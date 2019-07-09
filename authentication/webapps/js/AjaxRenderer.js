/**
 * Renders various HTML form elements using XML as the input source. This
 * library depends on AjaxRequestConfig.js and xpath.js libraries.
 */
function AjaxRenderer(reqConfig, xmlData) {
	var config = reqConfig;
	var data = xmlData;
	var context = new XPathContext();
	var parser = new XPathParser();
	context.expressionContextNode = data;

	/**
	 * Retrieve data value for a XML tag element using XPath expressions. The
	 * value is returned as an array in the event the propName occurs multiple
	 * times.
	 */
	this.getElementValue = function(propName) {
		var xpath = parser.parse(propName);
		var result = xpath.evaluate(context);
		var val = result.toArray();
		return val;
	}

	/**
	 * Builds the select options for a an input select control based on the XML
	 * data derived from the user's Ajax request.  The property names of the 
	 * "codeXmlName" and "textXmlName" are expected to be named exactly 
	 * as the target input control property name.
	 */
	this.buildSelectOptions = function(selObj, codeXmlName, textXmlName) {
		var codes;
		var text;
		codes = this.getElementValue(codeXmlName);
		text = this.getElementValue(textXmlName);
		if (codes.length != text.length) {
			return null;
		}
		this.removeSelectOptions(selObj);
		var total = codes.length;
		for ( var ndx = 0; ndx < total; ndx++) {
			selObj.options[ndx] = new Option(text[ndx].text, codes[ndx].text);
		}
		return;
	}

	/**
	 * Remove all options from the select control in reverse order. It is
	 * required to traverse the list in reverse order since the list is
	 * restructured every time an option is set to null.
	 */
	this.removeSelectOptions = function(selObj) {
		for (ndx = (selObj.length - 1); ndx >= 0; ndx--) {
			selObj.options[ndx] = null;
		}
		if (selObj.length > 0) {
			selObj.selectedIndex = (selIndex == 0 ? 0 : selIndex - 1);
		}
	}

	/**
	 * Created HTML data from the XML derived from the user's Ajax request.
	 */
	this.renderHtml = function() {
		if (!validate()) {
			throw "HTML Builder failed: the component that is to be rendered has an invalid reference";
		}
		// Produce plain text
		if (config.component == null) {
			renderPlainText();
		}
		if (config.component.type == "text") {
			renderText();
		}
		if (config.component.type == "checkbox") {
			renderCheckbox();
		}
		if (config.component.type == "radio") {
			renderRadio();
		}
		if (config.component.type == "textarea") {
			renderTextArea();
		}
		if (config.component.type == "select-one"
				|| config.component.type == "select-multiple") {
			renderSelect();
		}
	}

	var validate = function() {
		if (config == null) {
			return false;
		}
		if (config.component.type != "text"
				&& config.component.type != "checkbox"
				&& config.component.type != "radio"
				&& config.component.type != "textarea"
				&& config.component.type != "select-one"
				&& config.component.type != "select-multiple") {
			return false;
		}
		return true;
	}

	var renderPlainText = function() {
		return getPropertyValue(config.displayValueId);
		;
	}

	var renderText = function() {
		var propVal = getPropertyValue(config.displayValueId);
		config.component.value = propVal;
		return;
	}

	var renderCheckbox = function() {
		return;
	}

	var renderRadio = function() {
		return;
	}

	var renderTextArea = function() {
		return;
	}

	var renderSelect = function() {
		return;
	}

}
