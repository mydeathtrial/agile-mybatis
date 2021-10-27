drop table SYS_API;
create table SYS_API
(
    SYS_API_ID    INT not null,
    NAME          VARCHAR2,
    BUSINESS_NAME VARCHAR2,
    BUSINESS_CODE VARCHAR2,
    REMARKS       TEXT,
    TYPE          VARCHAR,
    constraint SYS_API_PK
        primary key (SYS_API_ID)
);

comment on table SYS_API is '系统api信息表';

