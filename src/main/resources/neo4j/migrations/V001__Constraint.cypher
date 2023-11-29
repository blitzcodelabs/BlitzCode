CREATE CONSTRAINT FOR (c:Company) REQUIRE c.companyNumber IS UNIQUE;
//Constraint for a node key is a Neo4j Enterprise feature only - run on an instance with enterprise
//CREATE CONSTRAINT ON (p:Person) ASSERT (p.birthMonth, p.birthYear, p.name) IS NODE KEY
CREATE CONSTRAINT FOR (p:Person) REQUIRE (p.birthMonth, p.birthYear, p.name) IS UNIQUE;
CREATE CONSTRAINT FOR (p:Property) REQUIRE p.titleNumber IS UNIQUE;
