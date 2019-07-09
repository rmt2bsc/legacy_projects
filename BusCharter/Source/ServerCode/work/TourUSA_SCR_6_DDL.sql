
/*==============================================================*/
/* DBMS name:      Sybase AS Anywhere 9                         */
/* Created on:     1/15/2005 11:50:04 AM                        */
/*==============================================================*/


if exists(select 1 from sys.sysforeignkey where role='FK_ORDER_EX_REFERENCE_EXPENSES') then
    alter table order_expenses
       delete foreign key FK_ORDER_EX_REFERENCE_EXPENSES
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_ORDER_EX_REFERENCE_ORDERS') then
    alter table order_expenses
       delete foreign key FK_ORDER_EX_REFERENCE_ORDERS
end if;

if exists(
   select 1 from sys.sysindex i, sys.systable t
   where i.table_id=t.table_id 
     and i.index_name='oe_ndx_1'
     and t.table_name='order_expenses'
) then
   drop index order_expenses.oe_ndx_1
end if;

if exists(
   select 1 from sys.systable 
   where table_name='expenses'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table expenses
end if;

if exists(
   select 1 from sys.systable 
   where table_name='order_expenses'
     and table_type in ('BASE', 'GBL TEMP')
) then
    drop table order_expenses
end if;

/*==============================================================*/
/* Table: expenses                                              */
/*==============================================================*/
create table expenses 
(
    id                   integer                        not null default autoincrement,
    description          varchar(30),
    active               smallint,
    date_created         timestamp,
    date_updated         timestamp,
    user_id              varchar(10),
    constraint PK_EXPENSES primary key (id)
);

/*==============================================================*/
/* Table: order_expenses                                        */
/*==============================================================*/
create table order_expenses 
(
    id                   integer                        not null default autoincrement,
    order_id             integer                        not null,
    expense_id           integer                        not null,
    description          varchar(80),
    amount               numeric(9, 2),
    date_created         timestamp,
    date_updated         timestamp,
    user_id              varchar(10),
    constraint PK_ORDER_EXPENSES primary key (id)
);

/*==============================================================*/
/* Index: oe_ndx_1                                              */
/*==============================================================*/
create  index oe_ndx_1 on order_expenses (
order_id ASC,
expense_id ASC
);

alter table order_expenses
   add constraint FK_ORDER_EX_REFERENCE_EXPENSES foreign key (expense_id)
      references expenses (id)
      on update restrict
      on delete restrict;

alter table order_expenses
   add constraint FK_ORDER_EX_REFERENCE_ORDERS foreign key (order_id)
      references orders (id)
      on update restrict
      on delete restrict;





%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%  function:    sumExpenses
%  arguments:   integer  aorder_id
%  returns:     numeric
%  description: Adds up all expenses(+/-) for an order and returns the total transaction amount to the caller.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

create function sumExpenses(aorder_id integer) returns numeric(11,2)

begin
   declare tot  numeric(11,2);

   if aorder_id is null or aorder_id <= 0 then
     return 0;
   end if;

   set tot = 0;

   select sum(amount)
     into tot
     from order_expenses
     where order_id = aorder_id;
		
   return ifNull(tot, 0, tot);
end;
