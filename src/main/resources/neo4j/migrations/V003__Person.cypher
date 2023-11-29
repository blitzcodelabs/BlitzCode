LOAD CSV WITH HEADERS FROM "file:///PSCAmericans.csv" AS row
MERGE (p:Person {name: row.`data.name`, birthYear: row.`data.date_of_birth.year`, birthMonth: row.`data.date_of_birth.month`})
  ON CREATE SET p.nationality = row.`data.nationality`,
  p.countryOfResidence = row.`data.country_of_residence`
RETURN COUNT(*);
