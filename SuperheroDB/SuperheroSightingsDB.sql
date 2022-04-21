DROP DATABASE IF EXISTS SuperheroSightings;
CREATE DATABASE SuperheroSightings;
USE SuperheroSightings;

CREATE TABLE Hero (
	heroId INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    description VARCHAR(200) NOT NULL,
    superpower VARCHAR(100) NOT NULL
);

CREATE TABLE Location (
	locationId INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(30) NOT NULL,
    description VARCHAR(100) NOT NULL,
    address VARCHAR(50) NOT NULL,
    city VARCHAR(30) NOT NULL,
	state VARCHAR(2) NOT NULL,
    zip VARCHAR(5) NOT NULL,
    latitude DECIMAL(8, 6) NOT NULL,
    longitude DECIMAL(9, 6) NOT NULL
);

CREATE TABLE Organization (
	orgId INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
    description VARCHAR(100) NOT NULL,
    address VARCHAR(50) NOT NULL,
	city VARCHAR(30),
	state VARCHAR(2),
    zip VARCHAR(5),
    contact VARCHAR(50) NOT NULL
);

CREATE TABLE Sighting (
	sightingId INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(100),
    dateOfSighting DATE NOT NULL,
    locationId INT NOT NULL,
    
	FOREIGN KEY fk_SightingLocation(locationID)
        REFERENCES Location(locationId)
);

CREATE TABLE Hero_Org (
	heroId INT NOT NULL,
    orgId INT NOT NULL,
    
    PRIMARY KEY pk_HeroOrg(heroId, orgId),
    FOREIGN KEY fk1_HeroOrg(heroId) 
		REFERENCES Hero(heroId),
	FOREIGN KEY fk2_HeroOrg(orgId)
		REFERENCES Organization(orgId)
);

CREATE TABLE Hero_Sighting (
	heroId INT NOT NULL,
    sightingId INT NOT NULL,
    
   PRIMARY KEY pk_HeroSighting(heroId, sightingId),
	FOREIGN KEY fk1_HeroSighting(heroId) 
		REFERENCES Hero(heroId),
	FOREIGN KEY fk2_HeroSighting_Sighting(sightingId) references Sighting(sightingId)
);

INSERT INTO Organization(name, description, address, city, state, zip, contact) 
	VALUES("Meowsters", "The strongest cats in town!", "2345 Meow Headquarters Avenue", "Dallas", "TX", "75035", "123-456-7890");
    
INSERT INTO Organization(name, description, address, city, state, zip, contact) 
	VALUES("No Organization Yet", "N/A", "N/A", "N/A", "NA", "N/A", "N/A");

INSERT INTO Location(name, description, address, city, state, zip, latitude, longitude)
	VALUES("Fuzzy Catpost", "A slightly worn down catpost.", "7000 Forest Avenue", "Dallas", "TX", "75035", 45.000001, 52.000002);

INSERT INTO Location(name, description, address, city, state, zip, latitude, longitude)
	VALUES("No Location Yet", "N/A", "N/A", "NA", "TX", "N/A", 00.000000, 01.000002);

INSERT INTO Sighting(name, description, dateOfSighting, locationId)
	VALUES ("No Sighting Yet", "N/A", "2022-01-01", (SELECT locationId FROM Location WHERE name="No Location Yet"));
