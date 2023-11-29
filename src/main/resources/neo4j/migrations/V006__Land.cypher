LOAD CSV WITH HEADERS FROM "file:///LandOwnershipAmericans.csv" AS row
MATCH (c:Company {companyNumber: row.`Company Registration No. (1)`})
MERGE (p:Property {titleNumber: row.`Title Number`})
SET p.address = row.`Property Address`,
p.county  = row.County,
p.price   = toInteger(row.`Price Paid`),
p.district = row.District
MERGE (c)-[r:OWNS]->(p)
WITH row, c,r,p WHERE row.`Date Proprietor Added` IS NOT NULL
SET r.date = Date(Datetime({epochSeconds: apoc.date.parse(row.`Date Proprietor Added`,'s','dd-MM-yyyy')}));
CREATE INDEX FOR (c:Company) ON c.incorporationDate;
