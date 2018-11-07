drop schema if exists invoice;
create schema if not exists invoice;

create table if not exists invoice.users (
  user_id     int          not null identity,
  username    varchar2(50) not null,
  password    varchar2(200),
  last_logged timestamp,
  enabled     boolean      not null default false,
  unique (username)
);

create table if not exists invoice.roles (
  role_id   int          not null primary key,
  role_name varchar2(50) not null,
  unique (role_name)
);

create table if not exists invoice.user_role (
  user_role_id  int       not null identity,
  user_id       int       not null,
  role_id       int       not null,
  date_assigned timestamp not null default systimestamp,
  foreign key (user_id) references invoice.user (user_id),
  foreign key (role_id) references invoice.role (role_id),
  unique (user_id, role_id)
);

create table if not exists invoice.companies (
  company_id      int           not null identity,
  company_name    varchar2(255) not null,
  short_name      varchar2(10)  not null
  comment 'used for the invoice prefix',
  address_line1   varchar2(500) not null,
  address_line2   varchar2(500),
  phone1          varchar2(20),
  owned_by_me     boolean       not null default true,
  business_number varchar2(30),
  content         blob comment 'company logo',
  unique (company_name),
  unique (short_name)
);

create table if not exists invoice.contacts (
  contact_id    int           not null identity,
  first_name    varchar2(100) not null,
  last_name     varchar2(100),
  middle_name   varchar2(100),
  email         varchar2(255) not null,
  address_line1 varchar2(500),
  address_line2 varchar2(500),
  phone1        varchar2(20),
  user_id       int,
  foreign key (user_id) references invoice.user (user_id),
  unique (email)
);

create table if not exists invoice.company_contact (
  company_contact_id int not null identity,
  contact_id         int not null,
  company_id         int not null,
  foreign key (contact_id) references invoice.contact (contact_id),
  foreign key (company_id) references invoice.company (company_id),
  unique (contact_id, company_id)
);

create table if not exists invoice.currency (
  ccy_id      int           not null identity,
  name        varchar2(10)  not null,
  description varchar2(100) not null
);

create table if not exists invoice.contracts (
  contract_id                      int          not null identity,
  contract_is_for                  int          not null,
  contract_signed_with             int          not null
  comment 'this indicates to which company we are billing to and signing contract with',
  contract_signed_with_subcontract int comment 'this indicates to which company we are actually doing work with',
  rate                             decimal      not null,
  rate_unit                        varchar2(10) not null default 'hour',
  ccy_id                           int          not null,
  valid_from                       date         not null,
  valid_to                         date,
  description                      varchar2(100),
  foreign key (contract_is_for) references invoice.company_contact (company_contact_id),
  foreign key (contract_signed_with) references invoice.company (company_id),
  foreign key (contract_signed_with_subcontract) references invoice.company (company_id),
  foreign key (ccy_id) references invoice.currency (ccy_id)
);

create table if not exists invoice.tax (
  tax_id     int          not null identity,
  percent    decimal      not null,
  identifier varchar2(50) not null,
  unique (identifier)
);

create table if not exists invoice.invoice (
  invoice_id           int           not null identity,
  company_to           int           not null,
  company_contact_from int           not null,
  from_date            date          not null,
  to_date              date          not null,
  created_date         date          not null default systimestamp,
  title                varchar2(255) not null default 'invoice',
  due_date             date          not null,
  tax_percent          decimal       not null,
  note                 varchar2(2000),
  paid_date            date,
  rate                 decimal,
  rate_unit            varchar2(10),
  ccy_id               int           not null,
  foreign key (company_to) references invoice.company (company_id),
  foreign key (company_contact_from) references invoice.company_contact (company_contact_id),
  foreign key (ccy_id) references invoice.currency (ccy_id)
);

create table if not exists invoice.attachment (
  attachment_id int           not null identity,
  invoice_id    int           not null,
  content       blob          not null
  comment 'file content',
  filename      varchar2(255) not null,
  foreign key (invoice_id) references invoice.invoice (invoice_id),
  unique (filename)
);

create table if not exists invoice.invoice_items (
  invoice_item_id int           not null identity,
  invoice_id      int           not null,
  description     varchar2(255) not null,
  code            varchar2(255),
  quantity        decimal       not null,
  unit            varchar2(20)  not null default 'hour',
  foreign key (invoice_id) references invoice.invoice (invoice_id)
);

create table if not exists invoice.time_sheet (
  timesheet_id    int     not null identity,
  invoice_item_id int     not null,
  item_date       date    not null,
  hours_worked    decimal not null,
  foreign key (invoice_item_id) references invoice.invoice_items (invoice_item_id)
);

create table if not exists invoice.reports (
  report_id    int       not null identity,
  invoice_id   int       not null,
  content      blob      not null,
  date_created timestamp not null default systimestamp,
  report_name  varchar(255),
  foreign key (invoice_id) references invoice.invoice (invoice_id)
);

create sequence invoice.invoice_counter_seq
  start with 1;

alter table invoice.contract
  add contract_number varchar2(50);
alter table invoice.contract
  add content blob comment 'contract document';

--addon column for the purchase order
alter table invoice.contract
  add purchase_order varchar2(50);

--addon column for the week start
alter table invoice.company
  add week_start int default 1 not null;

--change link from company to active contract
alter table invoice.invoice
  add company_contract_to int;
alter table invoice.invoice
  add foreign key (company_contract_to) references invoice.contract (contract_id);
--to be added second after previous statement
alter table invoice.invoice
  alter column company_contract_to int not null;

--new from 0.2.0
create sequence invoice.contract_counter_seq
  start with 4
  increment by 2;

--new from 0.2.3
create table invoice.oauth_access_token
(
  token_id          varchar(256) default 'null',
  token             blob,
  authentication_id varchar(256) default 'null',
  user_name         varchar(256) default 'null',
  client_id         varchar(256) default 'null',
  authentication    blob,
  refresh_token     varchar(256) default 'null'
);


create table invoice.oauth_refresh_token
(
  token_id       varchar(256) default 'null',
  token          blob,
  authentication blob
);

create table invoice.oauth_code (
  code           varchar(255),
  authentication blob
);

create table invoice.oauth_client_details (
  client_id               varchar(255) primary key,
  resource_ids            varchar(255),
  client_secret           varchar(255),
  scope                   varchar(255),
  authorized_grant_types  varchar(255),
  web_server_redirect_uri varchar(255),
  authorities             varchar(255),
  access_token_validity   integer,
  refresh_token_validity  integer,
  additional_information  varchar(4096),
  autoapprove             varchar(255)
);