/*==============================================================*/
/* Database name:  Model_RMT2_Base_CodeTables                   */
/* DBMS name:      Microsoft SQL Server 2000                    */
/* Created on:     2/26/2006 2:52:38 AM                         */
/*==============================================================*/


/*==============================================================*/
/* Table: zipcode                                               */
/*==============================================================*/
print  'creating table: zipcode'
create table zipcode (
   id                   int                  not null,
   zip                  varchar(5)           null,
   city                 varchar(35)          null,
   state                varchar(15)          null,
   area_code            varchar(20)          null,
   city_alias_name      varchar(35)          null,
   city_alias_abbr      varchar(13)          null,
   city_type            varchar(1)           null,
   county_name          varchar(25)          null,
   county_fips          varchar(3)           null,
   time_zone            int                  null,
   day_light_saving     varchar(1)           null,
   latitude             numeric(11,6)        null,
   longitude            numeric(11,6)        null,
   elevation            numeric(11,6)        null,
   msa                  numeric(11,6)        null,
   pmsa                 numeric(11,6)        null,
   cbsa                 numeric(11,6)        null,
   cbsa_div             numeric(11,6)        null,
   persons_per_household numeric(11,6)        null,
   zipcode_population   numeric(11,4)        null,
   counties_area        numeric(11,4)        null,
   households_per_zipcode numeric(11,1)        null,
   white_population     numeric(15,1)        null,
   black_population     numeric(15,1)        null,
   hispanic_population  numeric(15,1)        null,
   income_per_household numeric(15,1)        null,
   average_house_value  numeric(15,1)        null,
   constraint PK_ZIPCODE primary key  (id)
)
go


BULK INSERT zipcode
   FROM 'C:\projects\Common\Source\ServerCode\base\ddl\zipcode.dat'
GO
/*==============================================================*/
/* Index: zip_ndx_1                                             */
/*==============================================================*/
create   index zip_ndx_1 on zipcode (
zip
)
go


/*==============================================================*/
/* Index: zip_ndx_2                                             */
/*==============================================================*/
create   index zip_ndx_2 on zipcode (
state,
city
)
go


/*==============================================================*/
/* Index: zip_ndx_3                                             */
/*==============================================================*/
create   index zip_ndx_3 on zipcode (
area_code
)
go


/*==============================================================*/
/* Index: zip_ndx_4                                             */
/*==============================================================*/
create   index zip_ndx_4 on zipcode (
state,
city,
area_code
)
go


/*==============================================================*/
/* Index: zip_ndx_5                                             */
/*==============================================================*/
create   index zip_ndx_5 on zipcode (
state,
county_name
)
go


/*==============================================================*/
/* Index: zip_ndx_6                                             */
/*==============================================================*/
create   index zip_ndx_6 on zipcode (
state,
county_name,
area_code,
city,
zip
)
go


/*==============================================================*/
/* Index: zip_ndx_7                                             */
/*==============================================================*/
create   index zip_ndx_7 on zipcode (
time_zone,
state
)
go


/*==============================================================*/
/* Index: zip_ndx_8                                             */
/*==============================================================*/
create   index zip_ndx_8 on zipcode (
zip,
area_code
)
go


