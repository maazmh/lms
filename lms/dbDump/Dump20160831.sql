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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Approvers`
--

LOCK TABLES `Approvers` WRITE;
/*!40000 ALTER TABLE `Approvers` DISABLE KEYS */;
INSERT INTO `Approvers` VALUES (1,1,2),(2,1,3);
/*!40000 ALTER TABLE `Approvers` ENABLE KEYS */;
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
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `emailId` varchar(45) NOT NULL,
  `allocatedLeaves` int(11) NOT NULL,
  `departmentId` int(11) NOT NULL,
  `isAdmin` tinyint(1) NOT NULL DEFAULT '0',
  `isDeleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idEmployee`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Employee`
--

LOCK TABLES `Employee` WRITE;
/*!40000 ALTER TABLE `Employee` DISABLE KEYS */;
INSERT INTO `Employee` VALUES (1,'Maaz','Hurzuk','maaz.hurzuk@mondiamedia.com','maaz123','maaz.hurzuk@mondiamedia.com',22,3,0,0),(2,'Iyad','Farah','maaz.mh@gmail.com','iyad123','maaz.mh@gmail.com',22,3,0,0),(3,'Scott','Weeman','maaz.mh@gmail.com','scott123','maaz.mh@gmail.com',30,1,0,0),(4,'Shabeer','Ellath','maaz.mh@gmail.com','shabeer123','maaz.mh@gmail.com',22,3,0,0);
/*!40000 ALTER TABLE `Employee` ENABLE KEYS */;
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
  `dtUpdated` datetime DEFAULT NULL,
  PRIMARY KEY (`idLeaveApprovals`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LeaveApprovals`
--

LOCK TABLES `LeaveApprovals` WRITE;
/*!40000 ALTER TABLE `LeaveApprovals` DISABLE KEYS */;
INSERT INTO `LeaveApprovals` VALUES (1,2,2,1,NULL),(2,1,2,1,'2016-08-31 01:06:33'),(4,3,2,0,'2016-08-31 01:08:12');
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
  `dtFrom` date DEFAULT NULL,
  `dtTo` date DEFAULT NULL,
  `dtAppliedOn` date DEFAULT NULL,
  `leaveType` int(11) DEFAULT NULL,
  `leaveDescription` varchar(45) DEFAULT NULL,
  `isDeleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`idLeaves`),
  KEY `fk_employee_id_idx` (`employee_id`),
  KEY `fk_leave_type_idx` (`leaveType`),
  CONSTRAINT `fk_employee_id` FOREIGN KEY (`employee_id`) REFERENCES `Employee` (`idEmployee`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_leave_type` FOREIGN KEY (`leaveType`) REFERENCES `LeaveType` (`idLeaveType`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Leaves`
--

LOCK TABLES `Leaves` WRITE;
/*!40000 ALTER TABLE `Leaves` DISABLE KEYS */;
INSERT INTO `Leaves` VALUES (1,1,'2016-01-03','2016-01-07','2016-01-01',1,'first',0),(2,1,'2016-02-03','2016-02-03','2016-01-01',1,'test',0),(3,1,'2016-04-03','2016-04-06','2016-01-01',1,'test',0),(5,1,'2016-08-15','2016-08-18','2016-08-29',1,NULL,0),(6,1,'2016-08-28','2016-08-30','2016-08-29',1,'333',0),(9,1,'2016-09-06','2016-09-06','2016-08-30',1,'',0),(10,1,'2016-09-01','2016-09-01','2016-08-30',1,'ss',0);
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

-- Dump completed on 2016-08-31  1:16:02
