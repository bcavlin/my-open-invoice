DROP SCHEMA INVOICE;
CREATE SCHEMA IF NOT EXISTS INVOICE;

CREATE TABLE IF NOT EXISTS INVOICE.USERS (
  USER_ID     INT          NOT NULL IDENTITY,
  USERNAME    VARCHAR2(50) NOT NULL,
  PASSWORD    VARCHAR2(200),
  LAST_LOGGED TIMESTAMP,
  ENABLED     BOOLEAN      NOT NULL DEFAULT FALSE,
  UNIQUE (USERNAME)
);

CREATE TABLE IF NOT EXISTS INVOICE.ROLES (
  ROLE_ID   INT          NOT NULL PRIMARY KEY,
  ROLE_NAME VARCHAR2(50) NOT NULL,
  UNIQUE (ROLE_NAME)
);

CREATE TABLE IF NOT EXISTS INVOICE.USER_ROLE (
  USER_ROLE_ID  INT       NOT NULL IDENTITY,
  USER_ID       INT       NOT NULL,
  ROLE_ID       INT       NOT NULL,
  DATE_ASSIGNED TIMESTAMP NOT NULL DEFAULT SYSTIMESTAMP,
  FOREIGN KEY (USER_ID) REFERENCES INVOICE.USERS (USER_ID),
  FOREIGN KEY (ROLE_ID) REFERENCES INVOICE.ROLES (ROLE_ID),
  UNIQUE (USER_ID, ROLE_ID)
);

CREATE TABLE IF NOT EXISTS INVOICE.COMPANIES (
  COMPANY_ID      INT           NOT NULL IDENTITY,
  COMPANY_NAME    VARCHAR2(255) NOT NULL,
  SHORT_NAME      VARCHAR2(10)  NOT NULL
  COMMENT 'Used for the invoice prefix',
  ADDRESS_LINE1   VARCHAR2(500) NOT NULL,
  ADDRESS_LINE2   VARCHAR2(500),
  PHONE1          VARCHAR2(20),
  OWNED_BY_ME     BOOLEAN       NOT NULL DEFAULT TRUE,
  BUSINESS_NUMBER VARCHAR2(30),
  CONTENT         BLOB COMMENT 'Company logo',
  UNIQUE (COMPANY_NAME),
  UNIQUE (SHORT_NAME)
);

CREATE TABLE IF NOT EXISTS INVOICE.CONTACTS (
  CONTACT_ID    INT           NOT NULL IDENTITY,
  FIRST_NAME    VARCHAR2(100) NOT NULL,
  LAST_NAME     VARCHAR2(100),
  MIDDLE_NAME   VARCHAR2(100),
  EMAIL         VARCHAR2(255) NOT NULL,
  ADDRESS_LINE1 VARCHAR2(500),
  ADDRESS_LINE2 VARCHAR2(500),
  PHONE1        VARCHAR2(20),
  USER_ID       INT,
  FOREIGN KEY (USER_ID) REFERENCES INVOICE.USERS (USER_ID),
  UNIQUE (EMAIL)
);

CREATE TABLE IF NOT EXISTS INVOICE.COMPANY_CONTACT (
  COMPANY_CONTACT_ID INT NOT NULL IDENTITY,
  CONTACT_ID         INT NOT NULL,
  COMPANY_ID         INT NOT NULL,
  FOREIGN KEY (CONTACT_ID) REFERENCES INVOICE.CONTACTS (CONTACT_ID),
  FOREIGN KEY (COMPANY_ID) REFERENCES INVOICE.COMPANIES (COMPANY_ID),
  UNIQUE (CONTACT_ID, COMPANY_ID)
);

CREATE TABLE IF NOT EXISTS INVOICE.CURRENCY (
  CCY_ID      INT           NOT NULL IDENTITY,
  NAME        VARCHAR2(10)  NOT NULL,
  DESCRIPTION VARCHAR2(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS INVOICE.CONTRACTS (
  CONTRACT_ID                      INT          NOT NULL IDENTITY,
  CONTRACT_IS_FOR                  INT          NOT NULL,
  CONTRACT_SIGNED_WITH             INT          NOT NULL
  COMMENT 'This indicates to which company we are billing to and signing contract with',
  CONTRACT_SIGNED_WITH_SUBCONTRACT INT COMMENT 'This indicates to which company we are actually doing work with',
  RATE                             DECIMAL      NOT NULL,
  RATE_UNIT                        VARCHAR2(10) NOT NULL DEFAULT 'HOUR',
  CCY_ID                           INT          NOT NULL,
  VALID_FROM                       DATE         NOT NULL,
  VALID_TO                         DATE,
  DESCRIPTION                      VARCHAR2(100),
  FOREIGN KEY (CONTRACT_IS_FOR) REFERENCES INVOICE.COMPANY_CONTACT (COMPANY_CONTACT_ID),
  FOREIGN KEY (CONTRACT_SIGNED_WITH) REFERENCES INVOICE.COMPANIES (COMPANY_ID),
  FOREIGN KEY (CONTRACT_SIGNED_WITH_SUBCONTRACT) REFERENCES INVOICE.COMPANIES (COMPANY_ID),
  FOREIGN KEY (CCY_ID) REFERENCES INVOICE.CURRENCY (CCY_ID)
);

CREATE TABLE IF NOT EXISTS INVOICE.TAX (
  TAX_ID     INT          NOT NULL IDENTITY,
  PERCENT    DECIMAL      NOT NULL,
  IDENTIFIER VARCHAR2(50) NOT NULL,
  UNIQUE (IDENTIFIER)
);

CREATE TABLE IF NOT EXISTS INVOICE.INVOICE (
  INVOICE_ID           INT           NOT NULL IDENTITY,
  COMPANY_TO           INT           NOT NULL,
  COMPANY_CONTACT_FROM INT           NOT NULL,
  FROM_DATE            DATE          NOT NULL,
  TO_DATE              DATE          NOT NULL,
  CREATED_DATE         DATE          NOT NULL DEFAULT SYSTIMESTAMP,
  TITLE                VARCHAR2(255) NOT NULL DEFAULT 'Invoice',
  DUE_DATE             DATE          NOT NULL,
  TAX_PERCENT          DECIMAL       NOT NULL,
  NOTE                 VARCHAR2(2000),
  PAID_DATE            DATE,
  RATE                 DECIMAL,
  RATE_UNIT            VARCHAR2(10),
  CCY_ID               INT           NOT NULL,
  FOREIGN KEY (COMPANY_TO) REFERENCES INVOICE.COMPANIES (COMPANY_ID),
  FOREIGN KEY (COMPANY_CONTACT_FROM) REFERENCES INVOICE.COMPANY_CONTACT (COMPANY_CONTACT_ID),
  FOREIGN KEY (CCY_ID) REFERENCES INVOICE.CURRENCY (CCY_ID)
);

CREATE TABLE IF NOT EXISTS INVOICE.ATTACHMENT (
  ATTACHMENT_ID INT           NOT NULL IDENTITY,
  INVOICE_ID    INT           NOT NULL,
  CONTENT       BLOB          NOT NULL
  COMMENT 'File content',
  FILENAME      VARCHAR2(255) NOT NULL,
  FOREIGN KEY (INVOICE_ID) REFERENCES INVOICE.INVOICE (INVOICE_ID),
  UNIQUE (FILENAME)
);

CREATE TABLE IF NOT EXISTS INVOICE.INVOICE_ITEMS (
  INVOICE_ITEM_ID INT           NOT NULL IDENTITY,
  INVOICE_ID      INT           NOT NULL,
  DESCRIPTION     VARCHAR2(255) NOT NULL,
  CODE            VARCHAR2(255),
  TOTAL           DECIMAL       NOT NULL,
  FOREIGN KEY (INVOICE_ID) REFERENCES INVOICE.INVOICE (INVOICE_ID)
);

CREATE TABLE IF NOT EXISTS INVOICE.TIME_SHEET (
  TIMESHEET_ID    INT     NOT NULL IDENTITY,
  INVOICE_ITEM_ID INT     NOT NULL,
  ITEM_DATE       DATE    NOT NULL,
  HOURS_WORKED    DECIMAL NOT NULL,
  FOREIGN KEY (INVOICE_ITEM_ID) REFERENCES INVOICE.INVOICE_ITEMS (INVOICE_ITEM_ID)
);


INSERT INTO INVOICE.TAX (PERCENT, IDENTIFIER) VALUES (0.13, 'HST');
INSERT INTO INVOICE.TAX (PERCENT, IDENTIFIER) VALUES (0.0, 'No Tax');

INSERT INTO INVOICE.CURRENCY (NAME, DESCRIPTION) VALUES ('CAD', 'Canadian Dollar');
INSERT INTO INVOICE.CURRENCY (NAME, DESCRIPTION) VALUES ('USD', 'USA Dollar');

INSERT INTO INVOICE.ROLES (ROLE_ID, ROLE_NAME) VALUES (1, 'ROLE_ADMIN');
INSERT INTO INVOICE.ROLES (ROLE_ID, ROLE_NAME) VALUES (2, 'ROLE_USER');

INSERT INTO INVOICE.USERS (USER_ID, USERNAME, PASSWORD, LAST_LOGGED, ENABLED)
VALUES (1, 'bcavlin', '$2a$10$2VfGSFh/GPiQ44TiENuYgO7A7jz7C6Kog6Gg3sB07cQfW884nP52O', NULL, TRUE);
INSERT INTO INVOICE.USERS (USER_ID, USERNAME, PASSWORD, LAST_LOGGED, ENABLED)
VALUES (2, 'jane', '$2a$10$2VfGSFh/GPiQ44TiENuYgO7A7jz7C6Kog6Gg3sB07cQfW884nP52O', NULL, TRUE);

INSERT INTO INVOICE.CONTACTS (FIRST_NAME, LAST_NAME, MIDDLE_NAME, EMAIL, ADDRESS_LINE1, ADDRESS_LINE2, PHONE1, USER_ID)
VALUES ('John', 'Smith', NULL, 'bcavlin@hotmail.com', 'Address1', 'Address2', '905-234-2345', 1);

INSERT INTO INVOICE.USER_ROLE (USER_ID, ROLE_ID, DATE_ASSIGNED) VALUES (1, 1, SYSTIMESTAMP);

INSERT INTO INVOICE.COMPANIES (COMPANY_NAME, SHORT_NAME, ADDRESS_LINE1, ADDRESS_LINE2, PHONE1, OWNED_BY_ME, BUSINESS_NUMBER, CONTENT)
VALUES ('Test name', 'TN', 'Address1', 'Address2', '905-234-2344', TRUE, 'BNF0012301230213', NULL);

CREATE SEQUENCE INVOICE.INVOICE_COUNTER_SEQ START WITH 1;

COMMIT;