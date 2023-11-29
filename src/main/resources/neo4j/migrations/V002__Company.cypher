LOAD CSV WITH HEADERS FROM "file:///PSCAmericans.csv" AS row
MERGE (c:Company {companyNumber: row.company_number})
RETURN COUNT(*);
