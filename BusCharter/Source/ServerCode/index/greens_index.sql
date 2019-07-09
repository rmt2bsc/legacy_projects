CREATE  INDEX ix_busnumber
        ON bus_detail
        (bus_number );

CREATE  INDEX ix_drivername
        ON bus_detail
        (driver_name );

CREATE  INDEX ix_quotecharterparty
        ON quote
        (charter_party );

CREATE  INDEX ix_quotesinage
        ON quote
        (signage );

CREATE  INDEX ix_quotebuscount
        ON quote
        (bus_count );

CREATE  INDEX ix_departdate
        ON quote
        (depart_date );

CREATE  INDEX ix_dpartspottime
        ON quote
        (depart_spottime );

CREATE  INDEX ix_quotereturndate
        ON quote
        (return_date );

CREATE  INDEX ix_quotereturndroptime
        ON quote
        (return_droptime );

CREATE  INDEX ix_clfname
        ON client
        (contact_fname );

CREATE  INDEX ix_cllname
        ON client
        (contact_lname );

CREATE  INDEX ix_clbillfname
        ON client
        (billing_fname );

CREATE  INDEX ix_clbillname
        ON client
        (billing_lname );

CREATE  INDEX ix_email
        ON client
        (email );

CREATE  INDEX ix_billingexact
        ON client
        (isbillingexact );

CREATE  INDEX ix_website
        ON client
        (website );

CREATE  INDEX ix_amount
        ON transaction
        (amount );

CREATE  INDEX ix_transxadate
        ON transaction
        (trans_date );

CREATE  INDEX ix_transxchechnumber
        ON transaction
        (check_no );

CREATE  INDEX ix_ordersstatus
        ON quote
        (status );
