///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//     Function: enableContactControls
//    Prototype: _form - The form object that contains personal and business contact data.
//                    __contactType = Indicates if Business (1) or Person (2)
//      Returns: n/a
//  Description: This function senables/disables input controls which represent business  or personal contact data.    All personal data items are enabled and business data
//                    items are disabled if when _contactType equals "2".   Likewise, all business data items are enabled and all personal data items are disabled when
//                     _contactType equals "1".    When _contactType = "" then all data items are disabled.
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function enableContactControls(_form, _contactType) {

	// Enable/Disable Personal Controls
	_form.Title.disabled = (_contactType == "2" ? false : true);
	_form.Firstname.disabled = (_contactType == "2" ? false : true);
	_form.Midname.disabled = (_contactType == "2" ? false : true);
	_form.Lastname.disabled = (_contactType == "2" ? false : true);
	_form.Maidenname.disabled = (_contactType == "2" ? false : true);
	_form.Generation.disabled = (_contactType == "2" ? false : true);
	_form.Ssn.disabled = (_contactType == "2" ? false : true);
	_form.BirthDate.disabled = (_contactType == "2" ? false : true);
	_form.GenderId.disabled = (_contactType == "2" ? false : true);
	_form.MaritalStatus.disabled = (_contactType == "2" ? false : true);
	_form.RaceId.disabled = (_contactType == "2" ? false : true);
	_form.Email.disabled = (_contactType == "2" ? false : true);
	
	// Enable/Disable Business Controls
	_form.Longname.disabled = (_contactType == "1" ? false : true);
	_form.BusType.disabled = (_contactType == "1" ? false : true);
	_form.ServType.disabled = (_contactType == "1" ? false : true);
	_form.TaxId.disabled = (_contactType == "1" ? false : true);
	_form.ContactFirstname.disabled = (_contactType == "1" ? false : true);
	_form.ContactLastname.disabled = (_contactType == "1" ? false : true);
	_form.ContactPhone.disabled = (_contactType == "1" ? false : true);
	_form.ContactExt.disabled = (_contactType == "1" ? false : true);										
}

function enableCombinedContactControls(_form, _contactType) {  
	// Enable/Disable Personal Controls
	_form.PerTitle.disabled = (_contactType == "2" ? false : true);
	_form.PerFirstname.disabled = (_contactType == "2" ? false : true);
	_form.PerMidname.disabled = (_contactType == "2" ? false : true);
	_form.PerLastname.disabled = (_contactType == "2" ? false : true);
	_form.PerMaidenname.disabled = (_contactType == "2" ? false : true);
	_form.PerGeneration.disabled = (_contactType == "2" ? false : true);
	_form.PerSsn.disabled = (_contactType == "2" ? false : true);
	_form.PerBirthDate.disabled = (_contactType == "2" ? false : true);
	_form.PerGenderId.disabled = (_contactType == "2" ? false : true);
	_form.PerMaritalStatus.disabled = (_contactType == "2" ? false : true);
	_form.PerRaceId.disabled = (_contactType == "2" ? false : true);
	_form.PerEmail.disabled = (_contactType == "2" ? false : true);
	
	// Enable/Disable Business Controls
	_form.BusLongname.disabled = (_contactType == "1" ? false : true);
	_form.BusType.disabled = (_contactType == "1" ? false : true);
	_form.BusServType.disabled = (_contactType == "1" ? false : true);
	_form.BusTaxId.disabled = (_contactType == "1" ? false : true);
	_form.BusContactFirstname.disabled = (_contactType == "1" ? false : true);
	_form.BusContactLastname.disabled = (_contactType == "1" ? false : true);
	_form.BusContactPhone.disabled = (_contactType == "1" ? false : true);
}

function enableCustomerCriteriaControls(_form, _customerType) {     
	// Enable/Disable Personal Controls
	_form.qry_Firstname.disabled = (_customerType == "2" ? false : true);
	_form.qry_Midname.disabled = (_customerType == "2" ? false : true);
	_form.qry_Lastname.disabled = (_customerType == "2" ? false : true);
	_form.qry_Ssn.disabled = (_customerType == "2" ? false : true);
	_form.qry_BirthDate.disabled = (_customerType == "2" ? false : true);
	_form.qry_Email.disabled = (_customerType == "2" ? false : true);
	
	// Enable/Disable Business Controls
	_form.qry_Longname.disabled = (_customerType == "1" ? false : true);
	_form.qry_Website.disabled = (_customerType == "1" ? false : true);
	_form.qry_TaxId.disabled = (_customerType == "1" ? false : true);
	_form.qry_ContactFirstname.disabled = (_customerType == "1" ? false : true);
	_form.qry_ContactLastname.disabled = (_customerType == "1" ? false : true);
	_form.qry_ContactPhone.disabled = (_customerType == "1" ? false : true);
}
