LOAD CSV WITH HEADERS FROM "file:///PSCAmericans.csv" AS row
MATCH (c:Company {companyNumber: row.company_number})
MATCH (p:Person {name: row.`data.name`, birthYear: row.`data.date_of_birth.year`, birthMonth: row.`data.date_of_birth.month`})
MERGE (p)-[r:HAS_CONTROL]->(c)
SET r.nature = split(replace(replace(replace(row.`data.natures_of_control`, "[",""),"]",""),  '"', ""), ",")
RETURN COUNT(*);
