LOAD CSV WITH HEADERS FROM "file:///CompanyDataAmericans.csv" AS row
MATCH (c:Company {companyNumber: row.` CompanyNumber`})
SET c.name = row.CompanyName,
c.mortgagesOutstanding = toInteger(row.`Mortgages.NumMortOutstanding`),
c.incorporationDate = Date(Datetime({epochSeconds: apoc.date.parse(row.IncorporationDate,'s','dd/MM/yyyy')})),
c.SIC = row.`SICCode.SicText_1`,
c.countryOfOrigin = row.CountryOfOrigin,
c.status = row.CompanyStatus,
c.category = row.CompanyCategory;
