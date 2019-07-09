
create table project_master
(
    id                     integer               not null
        default autoincrement
        check (id >= 1000),
    description            varchar(30)                   ,
    begin_date             date                          ,
    end_date               date                          ,
    estimated_hours        decimal(8,2)                  ,
    actual_hours           decimal(8,2)                  ,
    build_no               integer                       ,
    major_version          integer                       ,
    minor_version          integer                       ,
    date_created           date                          ,
    date_updated           date                          ,
    user_id                varchar(8)                    ,
    primary key (id)
);

create table project_scr
(
    id                     integer               not null
        default autoincrement
        check (id >= 1),
    project_id             integer                       ,
    description            varchar(15)                   ,
    begin_date             date                          ,
    end_date               date                          ,
    est_compl_hours        decimal(8,2)                  ,
    actual_compl_hours     decimal(8,2)                  ,
    date_created           date                          ,
    date_updated           date                          ,
    user_id                varchar(8)                    ,
    primary key (id)
);

create table project_tasks
(
    id                     integer               not null
        default autoincrement
        check (id >= 1),
    role_id                integer                       ,
    scr_id                 integer                       ,
    date_worked            date                          ,
    hours_worked           decimal(5,2)                  ,
    billable               integer                       ,
    date_created           date                          ,
    date_updated           date                          ,
    user_id                varchar(8)                    ,
    primary key (id)
);

alter table project_scr
    add foreign key FK_PROJECT__REF_1264_PROJECT_ (project_id)
       references project_master (id) on update restrict on delete restrict;

alter table project_tasks
    add foreign key FK_PROJECT__REF_1260_GENERAL_ (role_id)
       references GENERAL_CODES (CODE_ID) on update restrict on delete restrict;

alter table project_tasks
    add foreign key FK_PROJECT__REF_1261_PROJECT_ (scr_id)
       references project_scr (id) on update restrict on delete restrict;
