-- MySQL dump 10.13  Distrib 5.7.12, for osx10.9 (x86_64)
--
-- Host: 127.0.0.1    Database: lms
-- ------------------------------------------------------
-- Server version	5.6.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Approvers`
--

DROP TABLE IF EXISTS `Approvers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Approvers` (
  `idApprovers` int(11) NOT NULL AUTO_INCREMENT,
  `idEmployee` int(11) NOT NULL,
  `idApprover` int(11) NOT NULL,
  PRIMARY KEY (`idApprovers`),
  KEY `fk_emp_id_idx` (`idEmployee`),
  KEY `fk_approver_id_idx` (`idApprover`),
  CONSTRAINT `fk_approver_id` FOREIGN KEY (`idApprover`) REFERENCES `Employee` (`idEmployee`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_emp_id` FOREIGN KEY (`idEmployee`) REFERENCES `Employee` (`idEmployee`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Approvers`
--

LOCK TABLES `Approvers` WRITE;
/*!40000 ALTER TABLE `Approvers` DISABLE KEYS */;
INSERT INTO `Approvers` VALUES (1,1,2),(2,1,3),(3,7,2),(4,7,1),(5,8,3),(6,9,2),(7,9,3),(36,10,3),(37,11,4),(38,11,3),(41,12,2),(42,12,3),(43,2,3);
/*!40000 ALTER TABLE `Approvers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Color`
--

DROP TABLE IF EXISTS `Color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Color` (
  `idColor` int(11) NOT NULL AUTO_INCREMENT,
  `colorHex` varchar(45) DEFAULT NULL,
  `colorDescription` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idColor`),
  UNIQUE KEY `colorHex_UNIQUE` (`colorHex`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Color`
--

LOCK TABLES `Color` WRITE;
/*!40000 ALTER TABLE `Color` DISABLE KEYS */;
INSERT INTO `Color` VALUES (1,'#2C8FC9','Blue'),(2,'#9CB703','Green'),(3,'#F5BB00','Yellow'),(4,'#FF4A32','Red'),(5,'#B56CE2','Purple'),(6,'#45A597','Teal'),(7,'#006400','Dark Green'),(8,'#800000','Maroon'),(9,'#FFDAB9','Peach'),(10,'#00FFFF','Cyan'),(11,'#FF00FF','Magenta'),(12,'#654321','Brown'),(13,'#000080','Dark Blue/Navy Blue');
/*!40000 ALTER TABLE `Color` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CompanyAccount`
--

DROP TABLE IF EXISTS `CompanyAccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CompanyAccount` (
  `idCompanyAccount` int(11) NOT NULL,
  `companyName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idCompanyAccount`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CompanyAccount`
--

LOCK TABLES `CompanyAccount` WRITE;
/*!40000 ALTER TABLE `CompanyAccount` DISABLE KEYS */;
INSERT INTO `CompanyAccount` VALUES (1,'Mondia Media');
/*!40000 ALTER TABLE `CompanyAccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Department`
--

DROP TABLE IF EXISTS `Department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Department` (
  `idDepartment` int(11) NOT NULL,
  `deptName` varchar(45) NOT NULL,
  PRIMARY KEY (`idDepartment`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Department`
--

LOCK TABLES `Department` WRITE;
/*!40000 ALTER TABLE `Department` DISABLE KEYS */;
INSERT INTO `Department` VALUES (1,'Management'),(2,'Business Development'),(3,'IT'),(4,'Finance'),(5,'Marketing'),(6,'HR');
/*!40000 ALTER TABLE `Department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Employee`
--

DROP TABLE IF EXISTS `Employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Employee` (
  `idEmployee` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `emailId` varchar(45) NOT NULL,
  `departmentId` int(11) NOT NULL,
  `isAdmin` tinyint(1) NOT NULL DEFAULT '0',
  `lastLogin` datetime DEFAULT NULL,
  `isDeleted` tinyint(1) NOT NULL DEFAULT '0',
  `idCompanyAccount` int(11) NOT NULL,
  `idColor` int(11) DEFAULT NULL,
  PRIMARY KEY (`idEmployee`),
  KEY `fk_companyacct_id_idx` (`idCompanyAccount`),
  KEY `fk_dept_id_idx` (`departmentId`),
  KEY `fk_color_idx` (`idColor`),
  CONSTRAINT `fk_color` FOREIGN KEY (`idColor`) REFERENCES `Color` (`idColor`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_companyacct_id` FOREIGN KEY (`idCompanyAccount`) REFERENCES `CompanyAccount` (`idCompanyAccount`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_dept_id` FOREIGN KEY (`departmentId`) REFERENCES `Department` (`idDepartment`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Employee`
--

LOCK TABLES `Employee` WRITE;
/*!40000 ALTER TABLE `Employee` DISABLE KEYS */;
INSERT INTO `Employee` VALUES (1,'Maaz','Hurzuk','maaz123','maaz.hurzuk@mondiamedia.com',3,0,NULL,0,1,1),(2,'Iyad','Farah','iyad123','maaz.mh@gmail.com',3,0,NULL,0,1,2),(3,'Scott','Weeman','scott123','maaz.mh@gmail.com',1,0,NULL,0,1,6),(4,'Shabeer','Ellath','shabeer123','maaz.mh@gmail.com',3,0,NULL,0,1,3),(7,'Dua','dd',NULL,'dd@dd.com',3,0,NULL,0,1,4),(8,'Loretta','ff',NULL,'ff@ff.com',4,0,NULL,0,1,7),(9,'Arsalan','mm',NULL,'mm@mm.com',5,0,NULL,0,1,8),(10,'Ria','hh',NULL,'hh@hr.com',6,0,NULL,0,1,9),(11,'Sami','jj',NULL,'jj@jj.com',2,0,NULL,0,1,10),(12,'Kareem','kk',NULL,'kk@kk.com',3,0,NULL,1,1,5);
/*!40000 ALTER TABLE `Employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EmployeeFiscalYearLeaves`
--

DROP TABLE IF EXISTS `EmployeeFiscalYearLeaves`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EmployeeFiscalYearLeaves` (
  `idEmployeeFiscalYearLeaves` int(11) NOT NULL AUTO_INCREMENT,
  `employeeId` int(11) NOT NULL,
  `fiscalYearId` int(11) NOT NULL,
  `leavesAllocated` int(11) DEFAULT NULL,
  `leavesCarriedForward` int(11) DEFAULT NULL,
  PRIMARY KEY (`idEmployeeFiscalYearLeaves`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EmployeeFiscalYearLeaves`
--

LOCK TABLES `EmployeeFiscalYearLeaves` WRITE;
/*!40000 ALTER TABLE `EmployeeFiscalYearLeaves` DISABLE KEYS */;
INSERT INTO `EmployeeFiscalYearLeaves` VALUES (1,1,1,22,2),(3,7,1,20,2),(4,8,1,24,7),(5,9,1,23,2),(34,10,1,28,8),(35,11,1,20,2),(37,12,1,23,3),(38,2,1,22,10);
/*!40000 ALTER TABLE `EmployeeFiscalYearLeaves` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FiscalYear`
--

DROP TABLE IF EXISTS `FiscalYear`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FiscalYear` (
  `idFiscalYear` int(11) NOT NULL,
  `dtFrom` date DEFAULT NULL,
  `dtTo` date DEFAULT NULL,
  PRIMARY KEY (`idFiscalYear`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FiscalYear`
--

LOCK TABLES `FiscalYear` WRITE;
/*!40000 ALTER TABLE `FiscalYear` DISABLE KEYS */;
INSERT INTO `FiscalYear` VALUES (1,'2016-01-01','2016-12-31'),(2,'2017-01-01','2017-12-31'),(3,'2018-01-01','2018-12-31');
/*!40000 ALTER TABLE `FiscalYear` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LeaveApprovals`
--

DROP TABLE IF EXISTS `LeaveApprovals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LeaveApprovals` (
  `idLeaveApprovals` int(11) NOT NULL AUTO_INCREMENT,
  `leaveId` int(11) DEFAULT NULL,
  `approverId` int(11) DEFAULT NULL,
  `isApproved` tinyint(1) DEFAULT NULL,
  `notes` varchar(500) DEFAULT NULL,
  `dtUpdated` datetime DEFAULT NULL,
  PRIMARY KEY (`idLeaveApprovals`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LeaveApprovals`
--

LOCK TABLES `LeaveApprovals` WRITE;
/*!40000 ALTER TABLE `LeaveApprovals` DISABLE KEYS */;
INSERT INTO `LeaveApprovals` VALUES (1,2,2,1,NULL,NULL),(2,1,2,1,NULL,'2016-08-31 01:06:33'),(4,3,2,0,NULL,'2016-08-31 01:08:12'),(9,5,2,1,NULL,'2016-09-01 01:22:29'),(10,6,2,0,NULL,'2016-09-01 01:28:49'),(11,1,3,1,NULL,NULL),(12,2,3,1,NULL,NULL);
/*!40000 ALTER TABLE `LeaveApprovals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LeaveType`
--

DROP TABLE IF EXISTS `LeaveType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LeaveType` (
  `idLeaveType` int(11) NOT NULL,
  `leaveType` varchar(45) NOT NULL,
  PRIMARY KEY (`idLeaveType`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LeaveType`
--

LOCK TABLES `LeaveType` WRITE;
/*!40000 ALTER TABLE `LeaveType` DISABLE KEYS */;
INSERT INTO `LeaveType` VALUES (1,'Vacation'),(2,'Sick'),(3,'Un-Paid');
/*!40000 ALTER TABLE `LeaveType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Leaves`
--

DROP TABLE IF EXISTS `Leaves`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Leaves` (
  `idLeaves` int(11) NOT NULL AUTO_INCREMENT,
  `employee_id` int(11) DEFAULT NULL,
  `fiscalYearId` int(11) DEFAULT NULL,
  `dtFrom` date DEFAULT NULL,
  `dtTo` date DEFAULT NULL,
  `dtAppliedOn` date DEFAULT NULL,
  `leaveType` int(11) DEFAULT NULL,
  `leaveDescription` varchar(45) DEFAULT NULL,
  `isDeleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`idLeaves`),
  KEY `fk_employee_id_idx` (`employee_id`),
  KEY `fk_leave_type_idx` (`leaveType`),
  KEY `index_isDeleted` (`isDeleted`),
  KEY `index_dtFrom` (`dtFrom`),
  KEY `index_dtTo` (`dtTo`),
  KEY `fk_fiscal_year_idx` (`fiscalYearId`),
  CONSTRAINT `fk_employee_id` FOREIGN KEY (`employee_id`) REFERENCES `Employee` (`idEmployee`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_fiscal_year` FOREIGN KEY (`fiscalYearId`) REFERENCES `FiscalYear` (`idFiscalYear`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_leave_type` FOREIGN KEY (`leaveType`) REFERENCES `LeaveType` (`idLeaveType`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Leaves`
--

LOCK TABLES `Leaves` WRITE;
/*!40000 ALTER TABLE `Leaves` DISABLE KEYS */;
INSERT INTO `Leaves` VALUES (1,1,1,'2016-01-03','2016-01-07','2016-01-01',1,'first',0),(2,1,1,'2016-02-03','2016-02-03','2016-01-01',1,'test',0),(3,1,1,'2016-04-03','2016-04-06','2016-01-01',1,'test',0),(5,1,1,'2016-08-15','2016-08-18','2016-08-29',1,NULL,0),(6,1,1,'2016-08-28','2016-08-30','2016-08-29',1,'333',0),(9,1,1,'2016-09-06','2016-09-06','2016-08-30',1,'',0),(10,1,1,'2016-09-01','2016-09-01','2016-08-30',1,'ss',0),(11,1,1,'2016-04-28','2016-04-28','2016-09-15',1,'',0),(12,1,1,'2016-04-30','2016-04-30','2016-09-15',1,'',0),(13,4,1,'2016-01-06','2016-01-06','2016-09-17',1,'annual',0),(14,2,1,'2016-02-11','2016-02-11','2016-09-17',1,'',0),(15,2,1,'2016-02-25','2016-02-25','2016-09-17',1,'test',0);
/*!40000 ALTER TABLE `Leaves` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-17  3:34:20
