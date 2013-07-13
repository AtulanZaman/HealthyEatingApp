-- Create syntax for TABLE 'Comments'
CREATE TABLE `Comments` (
  `text` varchar(100) DEFAULT NULL,
  `logID` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `commenterName` varchar(20) DEFAULT NULL,
  KEY `logID` (`logID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Create syntax for TABLE 'FoodItemFoodLog'
CREATE TABLE `FoodItemFoodLog` (
  `logID` int(11) NOT NULL,
  `foodName` varchar(20) NOT NULL,
  PRIMARY KEY (`logID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Create syntax for TABLE 'FoodItems'
CREATE TABLE `FoodItems` (
  `foodName` varchar(20) NOT NULL,
  `calories` int(11) NOT NULL,
  PRIMARY KEY (`foodName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Create syntax for TABLE 'FoodLog'
CREATE TABLE `FoodLog` (
  `logID` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `title` varchar(20) NOT NULL,
  `userID` int(11) DEFAULT NULL,
  PRIMARY KEY (`logID`),
  KEY `userID` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Create syntax for TABLE 'Login'
CREATE TABLE `Login` (
  `userName` varchar(10) NOT NULL,
  `password` varchar(16) NOT NULL,
  `userID` int(11) NOT NULL,
  PRIMARY KEY (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Create syntax for TABLE 'PointsEarned'
CREATE TABLE `PointsEarned` (
  `value` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  KEY `userID` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Create syntax for TABLE 'User'
CREATE TABLE `User` (
  `userID` int(11) NOT NULL,
  `firstName` varchar(20) NOT NULL,
  `lastName` varchar(20) NOT NULL,
  `type` varchar(20) DEFAULT NULL,
  `userName` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`userID`),
  KEY `userName` (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;