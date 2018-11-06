SELECT
  DS1.*,
  min(TS.ITEM_DATE) AS FROM_TS_DATE,
  max(TS.ITEM_DATE) AS TO_TS_DATE
FROM (
       SELECT
         INV.INVOICE_ID,
         INV.FROM_DATE,
         INV.TO_DATE,
         INV.CREATED_DATE,
         INV.TITLE,
         INV.DUE_DATE,
         INV.TAX_PERCENT,
         INV.NOTE,
         TO.COMPANY_NAME                               AS TO_NAME,
         TO.ADDRESS_LINE1 || ' ' || TO.ADDRESS_LINE2   AS TO_ADDRESS,
         TO.PHONE1                                     AS TO_PHONE,
         CFR.COMPANY_NAME                              AS FROM_NAME,
         CFR.ADDRESS_LINE1 || ' ' || CFR.ADDRESS_LINE2 AS FROM_ADDRESS,
         CFR.PHONE1                                    AS FROM_PHONE,
         CFR.CONTENT                                   AS FROM_LOGO,
         CFR.BUSINESS_NUMBER                           AS FROM_BN,
         CC.NAME                                       AS CCY
       FROM INVOICE.INVOICE INV
         LEFT JOIN INVOICE.COMPANY TO ON INV.COMPANY_TO = TO.COMPANY_ID
         LEFT JOIN INVOICE.COMPANY_CONTACT FR ON INV.COMPANY_CONTACT_FROM = FR.COMPANY_CONTACT_ID
         LEFT JOIN INVOICE.COMPANY CFR ON FR.COMPANY_ID = CFR.COMPANY_ID
         LEFT JOIN INVOICE.CURRENCY CC ON INV.CCY_ID = CC.CCY_ID
       WHERE INV.INVOICE_ID = 1
     ) DS1
  LEFT JOIN INVOICE.INVOICE_ITEMS II ON II.INVOICE_ID = DS1.INVOICE_ID
  LEFT JOIN INVOICE.TIME_SHEET TS ON II.INVOICE_ITEM_ID = TS.INVOICE_ITEM_ID;

SELECT
  rownum() RN,
  *
FROM (
  SELECT
    II.CODE,
    II.DESCRIPTION,
    II.QUANTITY,
    II.UNIT,
    CASE WHEN II.UNIT = 'HOUR'
      THEN INV.RATE
    ELSE NULL END AS RATE
  FROM INVOICE.INVOICE INV
    LEFT JOIN INVOICE.INVOICE_ITEMS II ON INV.INVOICE_ID = II.INVOICE_ID
    LEFT JOIN INVOICE.TIME_SHEET TS ON II.INVOICE_ITEM_ID = TS.INVOICE_ITEM_ID
  WHERE INV.INVOICE_ID = 1
  GROUP BY II.CODE,
    II.DESCRIPTION,
    II.QUANTITY,
    II.UNIT);

